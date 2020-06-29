package com.nand.sample.socialmedia.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nand.sample.socialmedia.exception.SocialMediaRuntimeException;
import com.nand.sample.socialmedia.model.Post;
import com.nand.sample.socialmedia.repository.UserRepository;
import com.nand.sample.socialmedia.service.SocialMediaServiceImpl;
import com.nand.sample.socialmedia.testdata.UserTestData;

@RunWith(SpringRunner.class)
@WebMvcTest(UserInterfaceController.class)
public class UserInterfaceControllerTest {

	@MockBean
	SocialMediaServiceImpl socialMediaService;

	@MockBean
	UserRepository userRepo;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testAddNewUser() throws Exception {
		Mockito.doNothing().when(socialMediaService).setusers(UserTestData.getUser1());

		mockMvc.perform(post("/inputNewUser").content(mapper.writeValueAsString(UserTestData.getUser1()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testAddNewUser_500error() throws Exception {
		Mockito.doThrow(new RuntimeException()).when(socialMediaService).setusers(UserTestData.getUser1());

		mockMvc.perform(post("/inputNewUser").content(mapper.writeValueAsString(UserTestData.getUser1()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError());
	}

	@Test
	public void testRemoveUser() throws Exception {
		Mockito.doNothing().when(socialMediaService).removeUser(1);

		mockMvc.perform(delete("/removeUser/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testRemoveUser_500error() throws Exception {
		Mockito.doThrow(new RuntimeException()).when(socialMediaService).removeUser(1);

		mockMvc.perform(delete("/removeUser/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
	}

	@Test
	public void testAddNewPost() throws Exception {
		when(socialMediaService.getUserByUserID(Mockito.any(Integer.class))).thenReturn(UserTestData.getUser1());
		Mockito.doNothing().when(socialMediaService).createPost(1, "Some content");

		mockMvc.perform(
				post("/inputNewPost/{userId}/{content}", 1, "Some conent").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testAddNewPost_500error() throws Exception {
		when(socialMediaService.getUserByUserID(Mockito.any(Integer.class))).thenReturn(UserTestData.getUser1());
		Mockito.doThrow(new SocialMediaRuntimeException()).when(socialMediaService).createPost(1, "Some content");

		mockMvc.perform(post("/inputNewPost/{userId}/{content}", 1, "Some content")
				.content("")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
	}

	@Test
	public void testRemovePost() throws Exception {
		when(socialMediaService.getPostByPostID(Mockito.any(Integer.class))).thenReturn(new Post());
		Mockito.doNothing().when(socialMediaService).removePost(1);

		mockMvc.perform(delete("/removePost/{postId}", 1)
				.content("")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testRemovePost_500error() throws Exception {
		when(socialMediaService.getPostByPostID(Mockito.any(Integer.class))).thenReturn(new Post());
		Mockito.doThrow(new SocialMediaRuntimeException()).when(socialMediaService).removePost(1);

		mockMvc.perform(delete("/removePost/{postId}",1)
				.content("")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
	}

	@Test
	public void testFollow() throws Exception {
		when(socialMediaService.getUserByUserID(Mockito.any(Integer.class))).thenReturn(UserTestData.getUser1(), UserTestData.getUser2());
		when(socialMediaService.follow(UserTestData.getUser1(), UserTestData.getUser2())).thenReturn(null);

		mockMvc.perform(post("/follow/{followeeId}/{followerId}", 1,2)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testFollow_500error() throws Exception {
		when(socialMediaService.getUserByUserID(Mockito.any(Integer.class))).thenReturn(UserTestData.getUser1(), UserTestData.getUser2());
		when(socialMediaService.follow(UserTestData.getUser1(), UserTestData.getUser2())).thenThrow(new SocialMediaRuntimeException());

		mockMvc.perform(post("/follow/{followeeId}/{followerId}", 1,2)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError());
	}

	@Test
	public void testUnfollow() throws Exception {
		when(socialMediaService.getUserByUserID(Mockito.any(Integer.class))).thenReturn(UserTestData.getUser1(), UserTestData.getUser2());
		when(socialMediaService.follow(UserTestData.getUser1(), UserTestData.getUser2())).thenReturn(null);

		mockMvc.perform(delete("/unfollow/{followeeId}/{followerId}", 1,2)
				.content("")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testUnfollow_500error() throws Exception {
		when(socialMediaService.getUserByUserID(Mockito.any(Integer.class))).thenReturn(UserTestData.getUser1(), UserTestData.getUser2());
		when(socialMediaService.unfollow(UserTestData.getUser1(), UserTestData.getUser2())).thenThrow(new SocialMediaRuntimeException());

		mockMvc.perform(delete("/unfollow/{followeeId}/{followerId}", 1,2)
				.content("")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError());
	}
}
