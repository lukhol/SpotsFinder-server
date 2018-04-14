package com.lukhol.spotsfinder.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InformedAspect {

	@Pointcut("execution(* com.lukhol.spotsfinder.service.user.UserPasswordService.recoverAccount(String)) && args(email)")
	public void recoverAccount(String email) {
		
	}
	
	@Before("@annotation(Informed)")
	public void inform(JoinPoint joinPoint) throws Throwable {
		String name = joinPoint.getSignature().getName();
		String className = joinPoint.getThis().getClass().getSimpleName();
		String message = new StringBuilder()
				.append("I'm informing from method: ")
				.append(name)
				.append(". ")
				.append("Inside class: ")
				.append(className)
				.append(". ")
				.toString();
		
		System.out.println(message);
	}
	
	@Before("recoverAccount(email)")
	public void informTwo(String email) {
		System.out.println("Recover account before aspect for email: " + email);
	}
}
