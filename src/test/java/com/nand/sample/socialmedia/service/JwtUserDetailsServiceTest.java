package com.nand.sample.socialmedia.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nand.sample.socialmedia.repository.UserRepository;
import com.nand.sample.socialmedia.testdata.UserTestData;

@RunWith(MockitoJUnitRunner.Silent.class)
public class JwtUserDetailsServiceTest {
	
	@InjectMocks
	JwtUserDetailsService jwtUserDetailService;
	
	@Mock
	UserRepository userRepo;
	
	@Before
	public void init() {
	}
	
	@Test
	public void testLoadUserByUsername() {
		when(userRepo.findByEmail(Mockito.any(String.class))).thenReturn(UserTestData.getUser1());
		
		assertTrue(jwtUserDetailService.loadUserByUsername("anyEmail") instanceof UserDetails);
	}
	@Test(expected=UsernameNotFoundException.class)
	public void testLoadUserByUsernameExecption() {
		when(userRepo.findByEmail(Mockito.any(String.class))).thenReturn(null);
		
		jwtUserDetailService.loadUserByUsername("anyEmail");
	}

}
