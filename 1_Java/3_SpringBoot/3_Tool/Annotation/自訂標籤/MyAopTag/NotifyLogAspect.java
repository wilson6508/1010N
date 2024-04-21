package com.aop;
import com.annotation.MyAopTag;
import com.annotation.MyClassTag;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotifyLogAspect {

//    @Pointcut(value = "@annotation(com.annotation.MyAopTag)")
//    public void getPointcut() {}
//
//    @After("getPointcut()")
//    public void doAfterPointcut() {
//        System.out.println("---After---");
//    }
//
//    @Around("getPointcut()")
//    public Object doInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("---Before---");
//        Object result = joinPoint.proceed();
//        System.out.println("---After---");
//        return result;
//    }

//    @Before("@annotation(myAopTag)")
//    public void doBeforePointcut(MyAopTag myAopTag) {
//        System.out.println("---Before---");
//        System.out.println(myAopTag.name());
//        System.out.println(myAopTag.age());
//        System.out.println("---Before---");
//    }
//
//    @Around("@annotation(myAopTag)")
//    public Object doInterceptor(ProceedingJoinPoint joinPoint, MyAopTag myAopTag) throws Throwable {
//        System.out.println(myAopTag.name());
//        Object result = joinPoint.proceed();
//        System.out.println(myAopTag.age());
//        return result;
//    }

}
