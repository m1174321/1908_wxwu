package com.fh.spring.aop;

import com.fh.entity.Log;
import com.fh.mapper.LogMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class SpringAop {

    @Autowired
    private LogMapper logMapper;


    @Around("execution(* com.fh.controller.*.*(..))")
    public Object handle(ProceedingJoinPoint pointcut){
        Object os =null;
        //方法名称
        String methodName = pointcut.getSignature().getName();
        //类对象
        Class<?> aClass = pointcut.getTarget().getClass();
        //方法签名
        Signature signature = pointcut.getSignature();
        try {
            Class<?>[] parameterTypes = pointcut.getTarget().getClass().getMethod(methodName).getParameterTypes();
            String params = "";
            if(parameterTypes.equals(0)){
                 params = "此方法没有参数";
            }else{
                params = parameterTypes.toString();
            }


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        //获取方法
        String name = signature.getName();
        //判断方法上是否有注解
        try {
            os = pointcut.proceed();


        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }







        return os;
    }

}
