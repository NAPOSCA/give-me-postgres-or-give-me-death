package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PantryControllerMvcTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@WithAnonymousUser
	public void shouldViewIndexOk() throws Exception {
		mvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	@WithAnonymousUser
	public void shouldRedirectFromSettingsToLoginPage() throws Exception {
		mvc.perform(get("/settings")).andExpect(redirectedUrlPattern("**/oauth2/authorization/google"));
	}

	@Test
	@WithAnonymousUser
	public void shouldRedirectFromShoppingToLoginPage() throws Exception {
		mvc.perform(get("/shopping")).andExpect(redirectedUrlPattern("**/oauth2/authorization/google"));
	}

	@Test
	@WithAnonymousUser
	public void shouldRedirectFromCartToLoginPage() throws Exception {
		mvc.perform(get("/cart")).andExpect(redirectedUrlPattern("**/oauth2/authorization/google"));
	}

	@Ignore
	@Test
	@WithMockUser
	public void shouldViewSettingsPageOk() throws Exception {
		mvc.perform(get("/settings")).andExpect(status().isOk());
	}

}
