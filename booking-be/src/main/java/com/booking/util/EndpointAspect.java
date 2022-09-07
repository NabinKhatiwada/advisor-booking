package com.booking.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Order(1)
@Component
@ConditionalOnExpression("${endpoint.aspect.enabled:true}")
public class EndpointAspect {
	private static final Logger logger = LogManager.getLogger(EndpointAspect.class);
	
	 /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
        " || within(@org.springframework.stereotype.Service *)" +
        " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
	@Pointcut("within(com.booking.controller..*)" + " || within(com.booking.service..*)"
			+ " || within(com.booking.dao..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

	@Before(value = ("applicationPackagePointcut() && springBeanPointcut()"))
	public void endpointBefore(JoinPoint p) {
		if (logger.isTraceEnabled()) {
			logger.trace(p.getTarget().getClass().getSimpleName() + " " + p.getSignature().getName() + " START");
		}
	}

	@AfterReturning(value = ("applicationPackagePointcut() && springBeanPointcut()"), returning = "returnValue")
	public void endpointAfterReturning(JoinPoint p, Object returnValue) {
		if (logger.isTraceEnabled()) {
			logger.trace(p.getTarget().getClass().getSimpleName() + " " + p.getSignature().getName() + " END");
		}
	}

	@AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
	public void endpointAfterThrowing(JoinPoint p, Exception e) throws Exception {
		if (logger.isTraceEnabled()) {
			logger.error(
					p.getTarget().getClass().getSimpleName() + " " + p.getSignature().getName() + " " + e.getMessage());
			
			logger.error("Error: "+e.getMessage(),e);
		}
	}
}