package com.game.chess.common.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.game.chess.common.annotation.CommonAnnotationUtils;
import com.game.common.utils.JsonUtil;
import com.game.common.utils.StringUtil;

/**
 * 
 * @Description Controller 切面通知类
 *
 * @author YingJH
 * @Date 2018年3月9日
 * @version v1.1
 */
@Aspect
public class ControllerAspect {
	// 日志输出者
	protected final static Logger logger = LogManager.getLogger();

	// Controller层切点
	@Pointcut("execution(* com.game.chess.controller..*.*(..)) && @annotation(com.game.chess.common.annotation.CommonController)")
	public void controllerAspect() {
		System.out.println("初始化");
		logger.debug("初始化");
	}

	/**
	 * @Description: 环绕方法，对controller包里的所有action有效
	 * @author yingjianghua
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("controllerAspect()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();

		// 获取请求名称
		String requestName = CommonAnnotationUtils.getControllerMethodDescription(pjp);
		logger.debug("请求名称：{}", requestName);

		// 获取请求地址
		StringBuffer requestUrl = request.getRequestURL();
		logger.debug("请求地址：{}", requestUrl);

		// 获取请求参数
		Map<?, ?> inputParamMap = request.getParameterMap();
		logger.debug("请求参数：{}", JsonUtil.toJSONString(inputParamMap));

		// 执行被拦截的处理请求的方法，result的值就是被拦截方法的返回值
		Object result = pjp.proceed();

		// 打印应答内容
		logger.debug("请求结果：{}", JsonUtil.toJSONString(result));

		return result;
	}

	@SuppressWarnings("unused")
	private static String getJsonpCallback(Map<?, ?> inputParamMap) {
		if (inputParamMap == null) {
			return "defaultJsonpCallback";
		}
		String[] array = (String[]) inputParamMap.get("jsonpCallback");
		if (array == null || array.length <= 0) {
			return "defaultJsonpCallback";
		}

		return StringUtil.isEmpty(array[0]) ? "defaultJsonpCallback" : array[0];
	}

	/**
	 * 异常通知 用于拦截controller层记录异常日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		/* ==========记录本地异常日志========== */
		logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}",
				joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(),
				e.getMessage(), joinPoint.getArgs());
		logger.error("error:", e);
	}
}
