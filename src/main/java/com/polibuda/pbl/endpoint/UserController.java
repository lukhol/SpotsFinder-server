package com.polibuda.pbl.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.polibuda.pbl.model.AccountRecover;
import com.polibuda.pbl.service.UserService;

import lombok.NonNull;

@Controller
public class UserController {

	private final UserService userService;
	
	@Autowired
	public UserController(@NonNull UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "/views/user/recover", method = RequestMethod.GET)
	public String index() {
		return "recoverAccount";
	}
	
	@RequestMapping(value = "/views/user/recover/{code}")
	public String resetPassword(@PathVariable String code, Model model) throws Exception {
		
		AccountRecover ar = userService
				.findOneByGuid(code)
				.orElseThrow(() -> new Exception("This link expired."));
		
		//Lub email w query stringu.
		
		model.addAttribute("email", ar.getEmail());
		model.addAttribute("code", code);
		return "resetPassword";
	}
}
