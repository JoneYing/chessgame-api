package com.game.chess.websocket.annotation;

import java.lang.annotation.*;

/**
 * 
 * @Description 用来映射 handlerAdapter
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WSTopic {

    String topic() ;
    /*
    * 默认推送
    * */
    String uri() default "/" ;

}
