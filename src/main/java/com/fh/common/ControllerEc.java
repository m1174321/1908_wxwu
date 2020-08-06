package com.fh.common;

import com.fh.common.exception.CountException;
import com.fh.common.exception.NologinException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//统一异常捕获
@ControllerAdvice
public class ControllerEc {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonData handleException(Exception e){
        e.printStackTrace();
        return JsonData.errorJsonData(e.getMessage());
    }


    @ExceptionHandler(NologinException.class) //
    @ResponseBody
    public JsonData handleNoLoginException(NologinException e){
            return JsonData.getJsonError(10000,e.getMessage());
    }

    @ExceptionHandler(CountException.class) //
    @ResponseBody
    public JsonData handleCountException(CountException e){
        return JsonData.getJsonError(20000,e.getMessage());
    }

}
