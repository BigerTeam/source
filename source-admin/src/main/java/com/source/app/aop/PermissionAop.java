package com.source.app.aop;

import java.lang.reflect.Method;

import javax.naming.NoPermissionException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.source.app.shiro.check.PermissionCheckManager;
import com.source.base.annotation.Permission;

@Aspect
@Component
public class PermissionAop {
	
	@Around("@annotation(com.source.base.annotation.Permission)")
    public Object doPermission(ProceedingJoinPoint point) throws Throwable {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Permission permission = method.getAnnotation(Permission.class);
        Object[] permissions = permission.value();
        if(permission==null||permissions.length==0){
            boolean result = PermissionCheckManager.checkAll();
            if(result){
            	return point.proceed();
            }else{
            	throw new NoPermissionException();
            }
        }else{
        	boolean result = PermissionCheckManager.check(permissions);
        	if(result){
            	return point.proceed();
            }else{
            	throw new NoPermissionException();
            }
        }
    }
 
}
