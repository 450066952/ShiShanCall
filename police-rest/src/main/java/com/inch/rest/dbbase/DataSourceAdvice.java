package com.inch.rest.dbbase;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class DataSourceAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {
	// service方法执行之前被调用
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("切入点: " + target.getClass().getName() + "类中" + method.getName() + "方法");
		if(method.getName().startsWith("add") 
			|| method.getName().startsWith("create")
			|| method.getName().startsWith("save")
			|| method.getName().startsWith("edit")
			|| method.getName().startsWith("update")
			|| method.getName().startsWith("delete")
			|| method.getName().startsWith("remove")){
			System.out.println("切换到: master");
			
			DbContextHolder.setDbType("lrj");
			
		}
		else  {
			System.out.println("切换到: slave");
			
			DbContextHolder.setDbType("now");
		}
		
//		
//		if (method.isAnnotationPresent(DataSource.class))   
//        {  
//            DataSource datasource = method.getAnnotation(DataSource.class);  
//            DbContextHolder.setDbType(datasource.name());  
//        }  
//        else  
//        {  
//        	DbContextHolder.setDbType(SinoConstant.DataSourceType.unityDataSource.toString());  
//        }  
		
		
	}

	// service方法执行完之后被调用
	public void afterReturning(Object arg0, Method method, Object[] args, Object target) throws Throwable {
		DbContextHolder.clearDbType();  
	}

	// 抛出Exception之后被调用
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
		DbContextHolder.setDbType("now");
		System.out.println("出现异常,切换到: slave");
	}

}


