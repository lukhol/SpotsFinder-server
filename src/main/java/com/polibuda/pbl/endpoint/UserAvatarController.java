package com.polibuda.pbl.endpoint;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.polibuda.pbl.model.User;
import com.polibuda.pbl.service.UserService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user/avatar")
public class UserAvatarController {
	
	@Value("${user.avatar.path}")
	private String AVATARS_PATH;
	
	private final UserService userService;
	
	@Autowired
	public UserAvatarController(@NonNull UserService userService){
		this.userService = userService;
	}
	
	@GetMapping("/{userId}")
	public void getUserAvatar(HttpServletResponse response, @PathVariable long userId) throws IOException{

		File fileImage = new File(String.format("%s\\%d.jpg", AVATARS_PATH , userId));
		byte[] imageBytes;
		
		if(fileImage.exists()){
			imageBytes = Files.readAllBytes(fileImage.toPath());
		}
		else{
			File anonymousUserFile = new File(String.format("%s\\%s.jpg", AVATARS_PATH , "anonymous"));
			imageBytes = Files.readAllBytes(anonymousUserFile.toPath());
		}
		
		response.setHeader("Cache-Control", "no-store");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);
	    response.setContentType("image/jpg");
	    
	    ServletOutputStream responseOutputStream = response.getOutputStream();
	    responseOutputStream.write(imageBytes);
	    responseOutputStream.flush();
	    responseOutputStream.close();
	}
	
	@PostMapping("/{userId}")
	public @ResponseBody ResponseEntity<?> postAvatar(@PathVariable long userId, @RequestParam("avatar") MultipartFile inputMultipartFile) throws Exception{
		
		log.info("Started uploading image for user {}.", userId);
		
		Optional<User> userOptional = userService.findUserById(userId);
		
		if(!userOptional.isPresent())
			throw new Exception("User with this id does not exist.");
		
		userService.saveAvatar(inputMultipartFile.getBytes(), userId);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
