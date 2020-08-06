package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.JsonData;
import com.fh.entity.User;
import com.fh.entity.Vip;
import com.fh.service.UserService;
import com.fh.util.JWT;
import com.fh.util.OSSUtil;
import com.fh.util.RedisPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;



    @ResponseBody
    @RequestMapping("smsPhoneCode")
    public JsonData smsPhoneCode(String phone){
        Vip vip = userService.findNamePhone(phone);
        if(vip!=null){
                //String coderr;Num = AliyunSmsUtils.smsUtil(vip.getUserPhone);
                    String codeNum = "1111";
                RedisPool.setexRedis(phone+"_wxwu",60*30,codeNum);
                return JsonData.successJsonData(codeNum);
        }
        return JsonData.errorJsonData("用户名或密码不存在");
    }


        @ResponseBody
        @RequestMapping("yanzhengLogin")
        public JsonData yanzhengLogin(String phone, String codeNum){
            String value = RedisPool.getexRedis(phone + "_wxwu");
            if(value.equals(codeNum)){
                Vip namePhone = userService.findNamePhone(phone);
                if(namePhone != null){
                        Map map = new HashMap();
                        Vip vip = new Vip();
                        vip.setId(namePhone.getId());
                        vip.setUserPhone(phone);
                        String sign = JWT.sign(vip, 1000 * 60 * 60);
                        String token = Base64.getEncoder().encodeToString((phone + "," + sign).getBytes());
                        RedisPool.setRedis(phone+"_wxwu",token);
                        map.put("code",200);
                        map.put("message","登陆成功");
                        map.put("token",token);
                        return JsonData.successJsonData(map);
                    }
                return JsonData.errorJsonData("手机号不正确");
            }
            return JsonData.errorJsonData("验证码不正确");
        }




    @RequestMapping("/findAreaList")
    @ResponseBody
    public JsonData findAreaList(){
        String areaList = RedisPool.getRedis("areaList");

        return JsonData.successJsonData(areaList);
    }

    @RequestMapping("addVip")
    @ResponseBody
    public JsonData addVip(Vip vip){
        Integer id = userService.addVip(vip);
        RedisPool.hsetRedis("vip_wxwu",id.toString(),vip);
        return JsonData.successJsonData("success");
    }


    //文件上传
    @RequestMapping("OSSuploadFile")
    @ResponseBody
    public Map OSSuploadFile( @RequestParam("img") MultipartFile img) {
        Map map = new HashMap();
        try {
            File file = OSSUtil.readFiles(img);
            String filePath = OSSUtil.uploadFile(file);
            map.put("filePath",filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
