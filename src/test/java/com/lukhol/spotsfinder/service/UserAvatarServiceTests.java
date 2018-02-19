package com.lukhol.spotsfinder.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.repository.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserAvatarServiceTests {

	private UserAvatarService userAvatarService;
	
	@Mock
	UserRepository userRepository;
	
	@Before
	public void setUp() {
		userAvatarService = new UserAvatarServiceImpl(userRepository);
	}
	
	@Test
	public void canSetInternalAvatarUrl() {
		User user = User.builder().id(1l).build();
		
		Mockito
			.when(userRepository.save(user))
			.thenReturn(user);
		
		String urlFromService = userAvatarService.setInternalAvatarUrl(user);
		
		assert urlFromService.contains("user/avatar/1");
		
		Mockito
			.verify(userRepository, Mockito.times(1))
			.save(Mockito.isA(User.class));
	}
}
