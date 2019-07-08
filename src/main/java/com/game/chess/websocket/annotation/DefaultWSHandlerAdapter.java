package com.game.chess.websocket.annotation;

import java.lang.annotation.*;

/**
 * 
 * @Description 用来标识默认请求处理器
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DefaultWSHandlerAdapter {
}
