package com.game.chess.websocket.annotation;

import java.lang.annotation.*;


/**
 * 
 * @Description 消息类型
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WSMessageType {

    String value();

}
