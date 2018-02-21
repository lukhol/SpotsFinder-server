package com.lukhol.spotsfinder.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeLogAspect {
	@Around("@annotation(LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint jointPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object proceed = jointPoint.proceed();
		long executionTime = System.currentTimeMillis() - start;
		
		System.out.println(jointPoint.getSignature() + " executed in: " + executionTime + " ms.");
		
		return proceed;
	}
}
