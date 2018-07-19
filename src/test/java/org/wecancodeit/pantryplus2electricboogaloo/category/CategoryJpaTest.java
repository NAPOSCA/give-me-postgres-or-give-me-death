package org.wecancodeit.pantryplus2electricboogaloo.category;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.wecancodeit.pantryplus2electricboogaloo.category.Category;
import org.wecancodeit.pantryplus2electricboogaloo.category.CategoryRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryJpaTest {
	
	@Resource
	private CategoryRepository categoryRepo;
	
	@Resource
	private TestEntityManager entityManager;

	@Test
	public void shouldSaveAndLoadCategory() {
		Category underTest = new Category("underTest");
		underTest = categoryRepo.save(underTest);
		long categoryId = underTest.getId();
		entityManager.flush();
		entityManager.clear();
		Optional<Category> actual = categoryRepo.findById(categoryId);
		assertThat(actual.isPresent(), is(true));
	}
}
