package com.game.chess.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Description 自定义注解 拦截Controller
 *
 * @author YingJH
 * @Date 2018年3月9日
 * @version v1.1
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented
public @interface CommonController{
	String description()  default "";    
}
