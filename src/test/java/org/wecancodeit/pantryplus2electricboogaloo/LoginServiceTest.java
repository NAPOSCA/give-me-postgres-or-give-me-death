package org.wecancodeit.pantryplus2electricboogaloo;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class LoginServiceTest {

	@InjectMocks
	private LoginService loginService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
}
