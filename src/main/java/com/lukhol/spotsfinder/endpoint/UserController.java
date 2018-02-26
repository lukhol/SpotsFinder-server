package com.lukhol.spotsfinder.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lukhol.spotsfinder.aspects.Informed;
import com.lukhol.spotsfinder.aspects.LogExecutionTime;
import com.lukhol.spotsfinder.model.AccountRecover;
import com.lukhol.spotsfinder.service.UserPasswordService;

import lombok.NonNull;

@Controller
public class UserController {

	@Value("${server.baseurl}")
	private String BASE_URL;
	
	private final UserPasswordService userPasswordService;
	
	@Autowired
	public UserController(@NonNull UserPasswordService userPasswordService) {
		this.userPasswordService = userPasswordService;
	}
	
	@Informed
	@LogExecutionTime
	@RequestMapping(value = "/views/user/recover", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("BASE_URL", BASE_URL);
		return "recoverAccountPage";
	}
	
	@RequestMapping(value = "/views/user/recover/{code}", method = RequestMethod.GET)
	public String resetPassword(@PathVariable String code, Model model) throws Exception {
		
		AccountRecover ar = userPasswordService
				.findOneByGuid(code)
				.orElseThrow(() -> new Exception("This link expired."));
		
		//Lub email w query stringu.
		
		model.addAttribute("email", ar.getEmail());
		model.addAttribute("code", code);
		model.addAttribute("BASE_URL", BASE_URL);
		return "resetPassword";
	}
}
