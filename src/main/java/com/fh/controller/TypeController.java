package com.fh.controller;

import com.fh.Annotion.LogAop;
import com.fh.common.JsonData;
import com.fh.entity.Type;
import com.fh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("type")
public class TypeController {

    @Autowired
    private TypeService typeService;


    @RequestMapping("findTypesAll")
    @ResponseBody
  //dddd  @CrossOrigin
    @LogAop(value = "查询类型")
    public JsonData findTypesAll(){
        List<Type> typeList = typeService.findTypesAll();
        return JsonData.successJsonData(typeList);
    }


}
