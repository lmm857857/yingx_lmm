package com.baizhi.lmm.aspect;


import com.baizhi.lmm.annotation.AddLog;
import com.baizhi.lmm.dao.LogMapper;
import com.baizhi.lmm.entity.Admin;
import com.baizhi.lmm.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Aspect
@Configuration
public class LogAspect {

    @Resource
    HttpSession session;
    @Resource
    LogMapper logMapper;

    //@Around("execution(* com.baizhi.lmm.serviceImpl.*.*(..))")//环绕通知 （切入点表达式）
    public Object addLog(ProceedingJoinPoint joinPoint){

        //谁   时间    操作    是否成功
        Admin admin = (Admin) session.getAttribute("admin");

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formats = format.format(date);
        //操作 哪个方法
        String methodName = joinPoint.getSignature().getName();
        //放行方法
        try {
            Object proceed = joinPoint.proceed();
            String message="success";
            System.out.println(admin.getUsername()+formats+"进行了"+methodName+"然后"+message);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message="error";
            return null;
        }

    }
    @Around("@annotation(com.baizhi.lmm.annotation.AddLog)")//环绕通知 （切入点表达式）
    public Object addLogs(ProceedingJoinPoint joinPoint){

        //谁   时间    操作    是否成功
        Admin admin = (Admin) session.getAttribute("admin");


        //操作 哪个方法
        String methodName = joinPoint.getSignature().getName();
        //获取方法
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取方法上的注解
        AddLog addLog = method.getAnnotation(AddLog.class);
        //获取方法上的注解值
        String methodDes = addLog.value();

        //放行方法
        try {

            Object proceed = joinPoint.proceed();

            String message="success";

            Log log = new Log(UUID.randomUUID().toString(),admin.getUsername(),new Date(),methodDes+"("+methodName+")",message);
            System.out.println("数据库插入"+log);
            logMapper.insert(log);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message="error";
            Log log = new Log(UUID.randomUUID().toString(),admin.getUsername(),new Date(),methodDes+"("+methodName+")",message);
            //System.out.println("数据库插入"+log);
            logMapper.insert(log);
            return null;
        }

    }
}
