package com.strider.follow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import com.strider.BaseTestRestController;
import com.strider.StriderApplication;
import com.strider.post.Post;
import com.strider.user.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =StriderApplication.class)
@WebAppConfiguration
public class FollowControllerTest extends BaseTestRestController{

	Follow follow;
	User userFollowed;
	User userFollowing;
	
	@MockBean
	private FollowRepository repository;
	
	@Before
	public void setup() throws Exception {
		super.setup();
		
		userFollowed = new User();
		userFollowed.setId(1);
		userFollowed.setUsername("username");
		
		userFollowing = new User();
		userFollowing.setId(2);
		userFollowing.setUsername("username2");
		
		follow = new Follow();
		follow.setId(1);
		follow.setUserFollowed(userFollowed);
		follow.setUserFollowing(userFollowing);
		
		when(repository.save(Mockito.any(Follow.class))).thenReturn(follow);
		
	}
	
	@Test
	public void create() throws Exception {
		MvcResult result = mockMvc.perform(post("/api/v1/follow")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(follow)))
				.andExpect(status().isCreated())
				.andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}
	
	@Test
	public void createInvalid() throws Exception {
		userFollowing.setId(1);
		userFollowing.setUsername("username");
		
		MvcResult result = mockMvc.perform(post("/api/v1/follow")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(follow)))
				.andExpect(status().isNotAcceptable())
				.andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());
	}
}
