package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PantryControllerMockMvcTest {
	
	@Resource
	private MockMvc mvc;
	
	@MockBean
	private CategoryRepository categoryRepo;
	
	@Test
	public void shouldBeUnauthorizedToGetFormView() throws Exception {
		mvc.perform(get("/")).andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser()
	public void shouldBeAuthorizedToGetFormView() throws Exception {
		mvc.perform(get("/")).andExpect(status().isOk());
	}
	
}
