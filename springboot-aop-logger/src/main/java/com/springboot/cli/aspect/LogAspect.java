package com.springboot.cli.aspect;

import com.alibaba.fastjson.JSON;
import com.springboot.cli.annotation.CustomLog;
import com.springboot.cli.model.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.springboot.cli.annotation.CustomLog)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        SysLog sysLog = this.getMethodInfo(joinPoint);
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis();
        sysLog.setCreateTime(new Date())
                .setExecutionTime(end - start);
        log.info(JSON.toJSONString(sysLog));
        return proceed;
    }


    /**
     * 获取方法执行信息
     */
    private SysLog getMethodInfo(ProceedingJoinPoint joinPoint) {
        SysLog sysLog = new SysLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CustomLog customLog = method.getAnnotation(CustomLog.class);
        // 注解上的描述
        Optional.ofNullable(customLog).ifPresent(c -> sysLog.setDesc(c.value()));
        try {
            sysLog
                    .setMethod(joinPoint.getSignature().getName())
                    .setPackageName(joinPoint.getTarget().getClass().getName())
                    .setParams(JSON.toJSONString(this.getParameters(joinPoint)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysLog;
    }

    /**
     * 获取请求参数
     */
    private Map<String, Object> getParameters(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        Map<String, Object> paramsMap = new HashMap<>(2);
        for (int i = 0; i < parameterValues.length; i++) {
            try {
                Object s = parameterValues[i];
                paramsMap.put(parameterNames[i], s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return paramsMap;
    }

    /**
     * 获取 Request
     */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }
}
