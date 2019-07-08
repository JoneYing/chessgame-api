package com.game.chess.common.enums;

/**
 * 
 * @Description 公共枚举
 *
 * @author YingJH
 * @Date 2018年3月9日
 * @version v1.1
 */
public class ChessGameEnum {
	
	public enum CookieName{
		SessionId("sessionId");//Cookie标识
		private String value;
		private CookieName(String value){
			this.value=value;
		}
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
	
}
