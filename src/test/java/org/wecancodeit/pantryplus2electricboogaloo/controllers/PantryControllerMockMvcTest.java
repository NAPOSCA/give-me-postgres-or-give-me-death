package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.wecancodeit.pantryplus2electricboogaloo.cart.CartRepository;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;
import org.wecancodeit.pantryplus2electricboogaloo.user.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PantryControllerMockMvcTest {
	
	@Resource
	private MockMvc mvc;
	
	@MockBean
	private CategoryRepository categoryRepo;
	
	@MockBean
	private UserRepository userRepo;
	
	@MockBean
	private CartRepository cartRepo;
	
	@MockBean
	private EntityManager entityManager;
	
	@Test
	public void shouldBeUnauthorizedToGetFormView() throws Exception {
		mvc.perform(get("/")).andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser
	public void shouldBeAuthorizedToGetFormView() throws Exception {
		mvc.perform(get("/")).andExpect(status().isOk());
	}
	
	@Ignore
	@Test
	@WithMockUser
	public void  shouldGetCartView() throws Exception {
		mvc.perform(get("/cart")).andExpect(status().isOk());
	}
	
}
