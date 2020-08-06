package com.fh.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.common.JsonData;
import com.fh.common.enums.PayStatusEnum;
import com.fh.common.exception.CountException;
import com.fh.entity.*;
import com.fh.mapper.CarMapper;
import com.fh.mapper.ProductMapper;
import com.fh.service.CarService;
import com.fh.util.RedisPool;
import com.github.wxpay.sdk.FeiConfig;
import com.github.wxpay.sdk.WXPay;
import org.apache.poi.ss.formula.functions.Count;
import org.apache.xmlbeans.impl.jam.mutable.MPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private HttpServletRequest request;

    @Override
    public long addCar(Integer id, Integer count) {
        //判读值不小于0
        if(count>0){
            //通过商品ID查询库存数量 进行比较
            Integer inventory = carMapper.findProInventory(id);
            if(count>inventory){
                return inventory - count;
            }
        }else{
            return 0;
        }
        //从request 取出来登录人的信息
        Vip loginUser_wxwu = (Vip) request.getAttribute("loginUser_wxwu");
        ProductCar productCar = carMapper.findCarIdByProId(id);
        productCar.setStatus(true);
        productCar.setProductcount(count);
        //只是一个转换类型
        BigDecimal multiply = productCar.getPrice().multiply(new BigDecimal(count));
        productCar.setMoney(multiply);
        //因为在redis存的是hash  所以可以根据 id查到所对应的数据 查看是否存在 不存在则是这是新数据 存在的话需要给购买数量 小计金额 进行重新赋值
        String hgetRedis = RedisPool.hgetRedis("car_"+loginUser_wxwu.getUserPhone()+"_gwc",id.toString());
        if(hgetRedis == null){
            RedisPool.hsetRedis("car_"+loginUser_wxwu.getUserPhone()+"_gwc",id+"",productCar);
            long length = RedisPool.hlenRedis("car_"+loginUser_wxwu.getUserPhone() + "_gwc");
            return length;
        }else {
            String s = RedisPool.hgetRedis("car_"+loginUser_wxwu.getUserPhone() + "_gwc", id.toString());
            ProductCar productCar1 = JSONObject.parseObject(s, ProductCar.class);
            Integer productcount = productCar1.getProductcount();
            productCar1.setProductcount(productcount + count);
            Integer inventory = carMapper.findProInventory(id) ;
            if(productCar1.getProductcount()> inventory){
                return  inventory - productCar1.getProductcount();
            }
            BigDecimal multiply1 = productCar1.getPrice().multiply(new BigDecimal(productCar1.getProductcount()));
            productCar1.setMoney(multiply1);
            RedisPool.hsetRedis("car_"+loginUser_wxwu.getUserPhone()+"_gwc",id+"",productCar1);
            long hlenRedis = RedisPool.hlenRedis("car_"+loginUser_wxwu.getUserPhone() + "_gwc");
            return hlenRedis;
        }
    }

    @Override
    public long delCar(Integer id) {

        Vip loginUser_wxwu = (Vip) request.getAttribute("loginUser_wxwu");
        Jedis jedis = RedisPool.getJedis();
        jedis.hdel("car_" + loginUser_wxwu.getUserPhone() + "_gwc", id.toString());
        long hlenRedis = RedisPool.hlenRedis("car_" + loginUser_wxwu.getUserPhone() + "_gwc");
        RedisPool.returnJedis(jedis);
        return hlenRedis;
    }

    @Override
    public void updateGwcStatusByIds(String ids) {
        Vip loginUser_wxwu = (Vip) request.getAttribute("loginUser_wxwu");
        List<String> hvals = RedisPool.hvals("car_" + loginUser_wxwu.getUserPhone() + "_gwc");
        for (int i = 0; i < hvals.size(); i++) {
            String gwc = hvals.get(i);
            ProductCar productCar = JSONObject.parseObject(gwc, ProductCar.class);
            Integer id = productCar.getId();
            if((","+ids).contains(","+id+",")!= true) {
                productCar.setStatus(false);
                RedisPool.hsetRedis("car_"+loginUser_wxwu.getUserPhone() + "_gwc",id.toString(),productCar);
            }else{
                productCar.setStatus(true);
                RedisPool.hsetRedis("car_"+loginUser_wxwu.getUserPhone() + "_gwc",id.toString(),productCar);
            }
        }

    }

    @Override
    public List<AddRess> findAddRess() {
        Vip loginUser_wxwu = (Vip) request.getAttribute("loginUser_wxwu");
        Integer id = loginUser_wxwu.getId();
        List<AddRess> addRessList = carMapper.findAddRess(id);
        for (int i = 0; i < addRessList.size(); i++) {
            String[] split = addRessList.get(i).getAreaIds().split(",");
            StringBuffer sb = new StringBuffer();
            for (int j = 1; j < split.length; j++) {
                String areaWxwu = RedisPool.hgetRedis("area_wxwu", split[j]);
                Area area = JSONObject.parseObject(areaWxwu, Area.class);
                sb.append(area.getName());
            }
            addRessList.get(i).setAreaIds(sb.toString());
        }
        return addRessList;
    }

    @Override
    public List<ProductCar> findProductGwcRedis() {
        Vip loginUser_wxwu = (Vip) request.getAttribute("loginUser_wxwu");
        List<String> hvals = RedisPool.hvals("car_" + loginUser_wxwu.getUserPhone() + "_gwc");
        List<ProductCar> productCarList = new ArrayList<>();
        for (int i = 0; i < hvals.size(); i++) {
            String s = hvals.get(i);
            ProductCar productCar = JSONObject.parseObject(s, ProductCar.class);
            boolean status = productCar.isStatus();
            if (status==true){
                productCarList.add(productCar);
            }
        }
        return productCarList;
    }



    @Override
    public Map addOrderAddRess(Integer addRessId, Integer payTypeId) throws CountException {
        //返回值为map
        Map map = new HashMap();
        //获取登录用户信息
        Vip loginUser_wxwu = (Vip) request.getAttribute("loginUser_wxwu");
        //准备一个放数据的集合
        List<OrderDetail> orderDetailList = new ArrayList<>();
        //添加数据到订单表中
        Order order = new Order();
        order.setAddressId(addRessId);
        order.setCreateDate(new Date());
        order.setPayStatus(PayStatusEnum.PAY_STATUS_ING.getStatus());
        order.setVipId(loginUser_wxwu.getId());
        //计算总金额
        BigDecimal moneyAll = new BigDecimal(0);
        order.setPayType(payTypeId);
        //商品类型的个数 这个数据在redis里 想从redis取出这个东西 库存需要足够 否则不可取 定义一个变量 如果够 则+1
        Integer proType = 0;
        //循环比遍历选中的购物车商品
        List<String> productGwcList = RedisPool.hvals("car_" + loginUser_wxwu.getUserPhone() + "_gwc");
        for (int i = 0; i < productGwcList.size(); i++) {
            //获取集合里面每一个对象
            String productGwcString = productGwcList.get(i);
            ProductCar productCar = JSONObject.parseObject(productGwcString, ProductCar.class);
            //判断是否被选中
            if(productCar.isStatus() == true){
                //查询商品的库存
                Product product = productMapper.selectProductById(productCar.getId());
                //判断商品库存多于 订单的商品数量
                if(product.getInventory() >= productCar.getProductcount()){
                    //给商品种类个数+1
                    proType++;
                    //计算每个商品小计
                    moneyAll = moneyAll.add(productCar.getMoney());
                    //把数据添加到订单详情表中  先放入到上面定义好的list集合里
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setCount(productCar.getProductcount());
                    orderDetail.setProductid(productCar.getId());
                    //没有订单ID
                    orderDetailList.add(orderDetail);
                    //现在订单已经放到要添加的List集合里面了  现在应该去把商品表中的库存字段进行跟新
                    product.setInventory(product.getInventory()-productCar.getProductcount());
                    //事务的特性  返回值为修改了多少条数据
                    int i1 = productMapper.updateInventory(product.getId(),productCar.getProductcount());
                    if(i1 == 0){
                        //库存不足
                        throw new CountException("商品编号为:"+productCar.getId()+"的库存不足   库存只有："+product.getInventory());
                    }
                }else{
                    //库存不足
                    throw new CountException("商品编号为:"+productCar.getId()+"的库存不足   库存只有："+product.getInventory());
                }
            }
        }
        order.setProTypeCount(proType);
        order.setTotalmoney(moneyAll);
        //进行添加订单表 并且返回主键自增ID 先去数据库查看表设计是否是Id自增
        carMapper.addOrder(order);
        Integer id = order.getId();
        //添加订单详情表 通过返回自增ID 给orderId属性赋值 先查看方法是否有事务  mybatis中foreach标签
        carMapper.batchOrderDetail(orderDetailList,id);
        //把所有数据维护完毕 删除redis中的gwc中选中商品信息 上面已经获取到gwc中的所有商品信息
        for (int i = 0; i < productGwcList.size(); i++) {
            //取出来每条数据
            String product = productGwcList.get(i);
            //转类型
            ProductCar productCar = JSONObject.parseObject(product, ProductCar.class);
            //判断每条数据中isCheck 字段为true  为已经购买的商品
            if(productCar.isStatus()==true){
                //删除选中redis中gwc里面的商品
                Jedis jedis = RedisPool.getJedis();
                jedis.hdel("car_" + loginUser_wxwu.getUserPhone() + "_gwc",productCar.getId().toString());
                RedisPool.returnJedis(jedis);
            }
        }
        //返回已经订单Id以及共计金额
        map.put("orderId",order.getId());
        map.put("moneyAll",moneyAll);
        return map;
    }

    @Override
    public Map payWechat(Integer orderId,BigDecimal moneyAll) throws Exception{
        Vip loginUser_wxwu = (Vip) request.getAttribute("loginUser_wxwu");
        Map map =new HashMap();
        // 微信支付  natvie   商户生成二维码
        //配置配置信息
        FeiConfig config = new FeiConfig();
        //得到微信支付对象
        WXPay wxpay = new WXPay(config);
        //设置请求参数
        Map<String, String> data = new HashMap<String, String>();
        //对订单信息描述
        data.put("body", "飞狐电商666-订单支付");
        //String payId = System.currentTimeMillis()+"";
        //设置订单号 （保证唯一 ）
        String mill = "weixin1_order_" + orderId + System.currentTimeMillis();
        data.put("out_trade_no",mill);
        //设置币种
        data.put("fee_type", "CNY");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        Date d=new Date();
        String dateStr = sdf.format(new Date(d.getTime() + 120000000));
        //设置二维码的失效时间
        data.put("time_expire", dateStr);
        //设置订单金额   单位分
        //Integer proInventory = carMapper.findProInventory(orderId);
        //int inventory = proInventory * 100;
        //data.put("total_fee", String.valueOf(Integer.valueOf(inventory)));
        data.put("total_fee","1");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        //设置支付方式
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        //不为空则已生成验证码
        String millRedis = RedisPool.getexRedis("mill");
        if(StringUtils.isEmpty(millRedis) != true){
            map.put("code",200);
            map.put("url",millRedis);
            map.put("mill",mill);
            return map;
        }
        Order order = carMapper.findOrderById(orderId);

        // 统一下单
        Map<String, String> resp = wxpay.unifiedOrder(data);
        System.out.println(resp);
        if("SUCCESS".equalsIgnoreCase(resp.get("return_code"))&&"SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
            map.put("code",200);
            map.put("url",resp.get("code_url"));
            map.put("mill",mill);
            map.put("moneyAll",moneyAll);
            //更新订单状态
            order.setPayStatus(1);
            order.setVipId(loginUser_wxwu.getId());
            carMapper.updateById(order);
            RedisPool.setexRedis(mill,30*60,resp.get("code_url"));
        }else {
            map.put("code",600);
            map.put("info",resp.get("return_msg"));
        }
        return map;
    }

    @Override
    public Integer queryOrderDetail(String mill, Integer orderId) throws Exception {
        FeiConfig config = new FeiConfig();
        Vip loginUser_wxwu = (Vip) request.getAttribute("loginUser_wxwu");
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no",mill);
        // 查询支付状态
        Map<String, String> resp = wxpay.orderQuery(data);
        System.out.println(resp);
        if("SUCCESS".equalsIgnoreCase(resp.get("return_code"))&&"SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
            if("SUCCESS".equalsIgnoreCase(resp.get("trade_state"))){//支付成功
                //更新订单状态
                Order order=new Order();
                order.setId(orderId);
                order.setPayStatus(2);
                order.setVipId(loginUser_wxwu.getId());
                carMapper.updateById(order);
                return 1;
            }else if("NOTPAY".equalsIgnoreCase(resp.get("trade_state"))){
                return 3;
            }else if("USERPAYING".equalsIgnoreCase(resp.get("trade_state"))){
                return 2;
            }
        }
        return 0;
    }
}
