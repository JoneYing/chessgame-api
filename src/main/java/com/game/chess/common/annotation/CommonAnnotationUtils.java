package com.game.chess.common.annotation;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;

/**
 * 
 * @Description 自定义注解工具类
 *
 * @author YingJH
 * @Date 2018年3月9日
 * @version v1.1
 */
public class CommonAnnotationUtils {
	
	/**
	 * 获取注解中对方法的描述信息 用于service层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String getServiceMthodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(CommonService.class).description();
					break;
				}
			}
		}
		return description;
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					CommonController cc = method.getAnnotation(CommonController.class);
					description = cc == null ? "未知名称" : cc.description();
					break;
				}
			}
		}
		return description;
	}
	
	/**
	 * 获取切点方法的方法名
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getMethodNameFromJoinPoint(JoinPoint joinPoint) throws Exception {
		return joinPoint.getSignature().getName();
	}
	
	/**
	 * 获取切点方法的入参列表
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static Object[] getArgsFromJoinPoint(JoinPoint joinPoint) throws Exception {
		return joinPoint.getArgs();
	}
}
