package com.nand.sample.socialmedia.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nand.sample.socialmedia.jwt.model.JwtRequest;
import com.nand.sample.socialmedia.repository.UserRepository;
import com.nand.sample.socialmedia.service.JwtUserDetailsService;
import com.nand.sample.socialmedia.service.SocialMediaServiceImpl;
import com.nand.sample.socialmedia.testdata.UserTestData;
import com.nand.sample.socialmedia.utils.JwtTokenUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(JwtAuthenticationController.class)
public class JwtAuthenticationControllerTest {
	
	@MockBean
	private AuthenticationManager authenticationManager;
	
	@MockBean
	private JwtTokenUtil jwtTokenUtil;
	
	@MockBean
	private JwtUserDetailsService userDetailsService;
	
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
	public void testCreateAuthenticationToken() throws Exception {
		JwtRequest request = new JwtRequest("nand@xyz.com", "password");
		Mockito.when(userDetailsService.loadUserByUsername(request.getEmail())).thenReturn(new User("nand@xyz.com", "password",new ArrayList<>()));

		mockMvc.perform(post("/authenticate")
				.content(mapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testCreateAuthenticationToken_UNAUTHORIZED1() throws Exception {
		JwtRequest request = new JwtRequest("nand@xyz.com", "password");
		Mockito.when(userDetailsService.loadUserByUsername(request.getEmail())).thenReturn(new User("nand@xyz.com", "password",new ArrayList<>()));
		Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
			.thenThrow(new DisabledException("error injected"));
		mockMvc.perform(post("/authenticate")
				.content(mapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void testCreateAuthenticationToken_UNAUTHORIZED2() throws Exception {
		JwtRequest request = new JwtRequest("nand@xyz.com", "password");
		Mockito.when(userDetailsService.loadUserByUsername(request.getEmail())).thenReturn(new User("nand@xyz.com", "password",new ArrayList<>()));
		Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
			.thenThrow(new BadCredentialsException("error injected"));
		mockMvc.perform(post("/authenticate")
				.content(mapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}
}
