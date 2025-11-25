package com.qsp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class WeatherReportAspect {
	
	@Before("execution(* com.qsp.serviceimplement.WeatherServiceImplementation.*(..))")
	public void beforeAPICallLog(JoinPoint joinpoint) {
		log.info("Class Name: "+joinpoint.getTarget().getClass().getName()+
				 ", Method called "+joinpoint.getSignature().getName());
	}
	
	@AfterReturning("execution(* com.qsp.serviceimplement.WeatherServiceImplementation.*(..))")
	public void afterAPICallLog(JoinPoint joinpoint) {
		log.info("Class Name: "+joinpoint.getTarget().getClass().getName()+
				", Method Returned "+joinpoint.getSignature().getName());
	}
	@AfterThrowing(pointcut = "execution(* com.qsp.serviceimplement.WeatherServiceImplementation.*(..))",
			       throwing = "ex")
	public void afterException(JoinPoint joinpoint, Throwable ex) {
		log.info("Class Name: "+joinpoint.getTarget().getClass().getName()+
				", Method Returned "+joinpoint.getSignature().getName()+ex.getMessage());
	}
	
	@Around("execution(* com.qsp.serviceimplement.WeatherServiceImplementation.*(..))")
	public Object executionTimeTaken(ProceedingJoinPoint joinPoint) throws Throwable {
		Long beforeExecution=System.currentTimeMillis();
		Object result = joinPoint.proceed();
		Long afterExecution = System.currentTimeMillis();
		log.info(joinPoint.getTarget().getClass().getName()+" "
				+joinPoint.getSignature().getName()+" takes "+
				(afterExecution-beforeExecution)+" ms");
	    return result;
	}
}
