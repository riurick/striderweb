package com.strider.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import com.strider.user.UserData;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =StriderApplication.class)
@WebAppConfiguration
public class PostControllerTest extends BaseTestRestController{

	Post post;
	UserData user;
	
	@MockBean
	private PostRepository repository;
	
	@Before
	public void setup() throws Exception {
		super.setup();
		
		user = new UserData();
		user.setId(1);
		user.setUsername("username");
		
		post = new Post();
		post.setId(1);
		post.setPubliDate(new Date());
		post.setText("test text");
		post.setUser(user);
		
		when(repository.findAll()).thenReturn(Arrays.asList(new Post[] {post}));
		when(repository.findFollowingPostsByUserId(Mockito.anyInt())).thenReturn(Arrays.asList(new Post[] {post}));
		when(repository.save(Mockito.any(Post.class))).thenReturn(post);
		when(repository.findByUserIdAndPubliDate(Mockito.anyInt(), Mockito.any(Date.class))).thenReturn(new ArrayList<>());
		
	}
	
	@Test
	public void create() throws Exception {
		MvcResult result = mockMvc.perform(post("/api/v1/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(post)))
				.andExpect(status().isCreated())
				.andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}
	
	@Test
	public void createInvalid() throws Exception {
		when(repository.findByUserIdAndPubliDate(Mockito.anyInt(), Mockito.any(Date.class))).thenReturn(Arrays.asList(new Post[] {post, post, post, post, post}));

		MvcResult result = mockMvc.perform(post("/api/v1/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(post)))
				.andExpect(status().isNotAcceptable())
				.andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());
	}
	
	@Test
	public void all() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/v1/post"))
				.andExpect(status().isOk())
				.andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void following() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/v1/post/following/1"))
				.andExpect(status().isOk())
				.andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}
}
