package com.game.chess.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;

import org.springframework.web.filter.CharacterEncodingFilter;

import com.game.common.constant.GameConstant;

/**
 * 
 * @Description 编码过滤器,用于统一设置编码
 *
 * @author YingJH
 * @Date 2018年3月9日
 * @version v1.1
 */
@WebFilter(urlPatterns={ "/*"})
public class Filter01Encoding extends CharacterEncodingFilter {

	public void initFilterBean() throws ServletException {
		this.setEncoding(GameConstant.GAME_ENCODE);
		this.setForceEncoding(true);
		super.initFilterBean();
	}
}
