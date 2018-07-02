package org.wecancodeit.pantryplus2electricboogaloo.controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class PantryControllerMockTest {
	
	@InjectMocks
	private PantryController underTest;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldHaveDisplayUserFormReturnUserForm() {
		String templateName = "user-form";
		String actual = underTest.displayUserForm();
		assertThat(actual, is(templateName));
	}
}
