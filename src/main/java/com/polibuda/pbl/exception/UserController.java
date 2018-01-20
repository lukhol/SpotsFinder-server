package com.polibuda.pbl.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

	@RequestMapping(value = "/views/user/recover", method = RequestMethod.GET)
	public String index() {
		return "recoverAccount";
	}
}
