package com.interpark.assignment.aop;

import com.interpark.assignment.domain.SearchLog;
import com.interpark.assignment.repository.SearchLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class SearchLoggingAspect {

    private final SearchLogRepository searchLogRepository;

    @Pointcut(value = "execution(* com.interpark.assignment.controller.CityController.get(..))")
    public void pointCut() {

    }

    @After(value = "pointCut()")
    public void logSearch(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();

        Object[] args = joinPoint.getArgs();

        Map<String, Long> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            params.put(parameterNames[i], (Long) args[i]);
        }

        searchLogRepository.save(
                SearchLog.builder()
                        .cityId(params.get("cityId"))
                        .memberId(params.get("memberId"))
                        .build()
        );
    }
}
