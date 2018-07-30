package org.wecancodeit.pantryplus2electricboogaloo.product;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
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
public class ProductJpaTest {

	@Resource
	private ProductRepository productRepo;

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private CategoryRepository categoryRepo;

	@Test
	public void shouldSaveAndLoadProduct() {
		Category category = new Category("Category");
		category = categoryRepo.save(category);
		Product underTest = new Product("underTest", category, "");
		underTest = productRepo.save(underTest);
		long id = underTest.getId();
		entityManager.flush();
		entityManager.clear();
		Optional<Product> potentialProduct = productRepo.findById(id);
		boolean isPresent = potentialProduct.isPresent();
		assertThat(isPresent, is(true));
	}

	@Test
	public void shouldSaveManyProductsToOneCategory() {
		Category category = new Category("Category");
		category = categoryRepo.save(category);
		long categoryId = category.getId();
		Product product = productRepo.save(new Product("First Product", category, ""));
		Product anotherProduct = productRepo.save(new Product("Second Product", category, ""));
		entityManager.flush();
		entityManager.clear();
		category = categoryRepo.findById(categoryId).get();
		Collection<Product> products = category.getProducts();
		assertThat(products, containsInAnyOrder(product, anotherProduct));
	}
}
