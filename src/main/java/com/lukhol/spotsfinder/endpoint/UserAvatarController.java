package com.lukhol.spotsfinder.endpoint;

import java.io.IOException;

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

import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.service.UserAvatarService;
import com.lukhol.spotsfinder.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user/avatar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserAvatarController {
	
	@Value("${user.avatar.path}")
	private String AVATARS_PATH;
	
	private final UserAvatarService userAvatarService;
	private final UserService userService;
	
	@GetMapping("/{userId}")
	public void getUserAvatar(HttpServletResponse response, @PathVariable long userId) throws IOException, NotFoundUserException{
		log.debug("GET /user/avatar/{}", userId);
		
		byte[] imageBytes = userAvatarService.getUserAvatar(userId);
		
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
	public @ResponseBody ResponseEntity<?> postAvatar(@PathVariable long userId, @RequestParam("avatar") MultipartFile inputMultipartFile) throws NotFoundUserException, IOException {
		log.info("POST /user/avatar/{} - Started uploading image.", userId);
		
		User user = userService
				.findUserById(userId)
				.orElseThrow(() -> new NotFoundUserException(userId));
		
		userAvatarService.saveAvatar(inputMultipartFile.getBytes(), userId);
		String avatarUrl = userAvatarService.setInternalAvatarUrl(user);
		
		return new ResponseEntity<String>(avatarUrl, HttpStatus.OK);
	}
}
