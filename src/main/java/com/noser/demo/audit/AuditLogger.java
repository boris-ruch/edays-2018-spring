//package com.noser.demo.audit;
//
//
//import lombok.extern.log4j.Log4j2;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//@Log4j2
//public class AuditLogger {
//
//    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
//    public void controller() {
//    }
//
//    @Pointcut("execution(* *.*(..))")
//    protected void allMethod() {
//    }
//
//
//    @Before("controller() && allMethod()")
//    public void logBefore(JoinPoint jp) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("[AUDIT] user '{}' calls '{}.{}' ",
//                authentication.getName(), jp.getSignature().getDeclaringTypeName(),
//                jp.getSignature().getName());
//    }
//
//}
