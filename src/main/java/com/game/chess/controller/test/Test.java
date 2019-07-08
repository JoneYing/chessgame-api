package com.game.chess.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.chess.common.annotation.CommonController;
import com.game.chess.common.session.UserSession;
import com.game.chess.controller.BaseController;
import com.game.chess.utils.SessionUtil;
import com.game.common.resp.Resp;

@Controller
@RequestMapping("/test")
public class Test extends BaseController{

	@ResponseBody
	@CommonController(description="测试")
	@RequestMapping(path="/tt",method=RequestMethod.GET)
	public Resp test(){
		System.out.println("=======================>test");
		UserSession session = new UserSession();
		session.setName("yingjianghua");
		session.setUserId(111);
		SessionUtil.setLogined(request, response, session);
		return new Resp();
	}
	
}
