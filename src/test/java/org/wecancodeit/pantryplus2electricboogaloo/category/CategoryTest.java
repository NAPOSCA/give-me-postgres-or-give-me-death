package org.wecancodeit.pantryplus2electricboogaloo.category;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.wecancodeit.pantryplus2electricboogaloo.user.PantryUser;

public class CategoryTest {

	@Mock
	private OAuth2User googleId;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldGetNameFoo() {
		String name = "foo";
		Category underTest = new Category(name, false);
		String actualName = underTest.getName();
		assertThat(actualName, is(name));
	}

	@Test
	public void shouldGetNameBar() {
		String name = "bar";
		Category underTest = new Category(name, false);
		String actualName = underTest.getName();
		assertThat(actualName, is(name));
	}

	@Test
	public void shouldUpdateNameToBar() {
		String name = "Bar";
		Category underTest = new Category("Foo", false);
		underTest.updateName(name);
		String actual = underTest.getName();
		assertThat(actual, is(name));
	}

	@Test
	public void shouldBeVisibleIfUserHasOneSchoolAgeChildAndHasRule() {
		boolean schoolAgeChildrenRequired = true;
		Category underTest = new Category("", schoolAgeChildrenRequired);
		PantryUser user = new PantryUser(googleId);
		user.updateSchoolAgeChildren(1);
		boolean actual = underTest.isVisibleTo(user);
		assertThat(actual, is(true));
	}

	@Test
	public void shouldNotBeVisibleIfUserHasNoSchoolAgeChildrenAndHasRule() {
		boolean schoolAgeChildrenRequired = true;
		Category underTest = new Category("", schoolAgeChildrenRequired);
		PantryUser user = new PantryUser(googleId);
		user.updateSchoolAgeChildren(0);
		boolean actual = underTest.isVisibleTo(user);
		assertThat(actual, is(false));
	}
	
	@Test
	public void shouldBeVisibleIfUserHasNoSchoolAgeChildrenAndDoesNotHaveRule() {
		boolean schoolAgeChildrenRequired = false;
		Category underTest = new Category("", schoolAgeChildrenRequired);
		PantryUser user = new PantryUser(googleId);
		user.updateSchoolAgeChildren(0);
		boolean actual = underTest.isVisibleTo(user);
		assertThat(actual, is(true));
	}
	
	@Test
	public void shouldBeVisibleIfUserHasOneSchoolAgeChildAndDoesNotHaveRule() {
		boolean schoolAgeChildrenRequired = false;
		Category underTest = new Category("", schoolAgeChildrenRequired);
		PantryUser user = new PantryUser(googleId);
		user.updateSchoolAgeChildren(1);
		boolean actual = underTest.isVisibleTo(user);
		assertThat(actual, is(true));
	}
	
	@Test
	public void shouldUpdateSchoolAgeChildrenRequiredAsTrue() {
		boolean schoolAgeChildrenRequired = true;
		Category underTest = new Category("", false);
		underTest.updateSchoolAgeChildrenRequired(schoolAgeChildrenRequired);
		boolean actual = underTest.getSchoolAgeChildrenRequired();
		assertThat(actual, is(schoolAgeChildrenRequired));
	}

}
