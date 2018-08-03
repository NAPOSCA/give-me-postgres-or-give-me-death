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
import org.wecancodeit.pantryplus2electricboogaloo.product.LimitedProduct;
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;
import org.wecancodeit.pantryplus2electricboogaloo.product.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryJpaTest {

	@Resource
	private CategoryRepository categoryRepo;

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private ProductRepository productRepo;

	@Test
	public void shouldSaveAndLoadCategory() {
		Category underTest = new Category("underTest", false);
		underTest = categoryRepo.save(underTest);
		long categoryId = underTest.getId();
		entityManager.flush();
		entityManager.clear();
		Optional<Category> actual = categoryRepo.findById(categoryId);
		assertThat(actual.isPresent(), is(true));
	}

	@Test
	public void shouldHaveOneProductInCategory() {
		Category underTest = new Category("underTest", false);
		underTest = categoryRepo.save(underTest);
		long underTestId = underTest.getId();
		productRepo.save(new Product("", underTest, "", false));
		entityManager.flush();
		entityManager.clear();
		underTest = categoryRepo.findById(underTestId).get();
		int actual = underTest.numberOfProducts();
		assertThat(actual, is(1));
	}

	@Test
	public void shouldHaveTwoProductsInCategory() {
		Category underTest = new Category("underTest", false);
		underTest = categoryRepo.save(underTest);
		long underTestId = underTest.getId();
		productRepo.save(new Product("", underTest, "", false));
		productRepo.save(new LimitedProduct("", underTest, "", false, 1));
		entityManager.flush();
		entityManager.clear();
		underTest = categoryRepo.findById(underTestId).get();
		int actual = underTest.numberOfProducts();
		assertThat(actual, is(2));
	}

	@Test
	public void shouldDeleteProductsInsideCategoryWhenCategoryIsDeleted() {
		Category underTest = new Category("underTest", false);
		underTest = categoryRepo.save(underTest);
		long underTestId = underTest.getId();
		Product product = new Product("Product", underTest, "", false);
		product = productRepo.save(product);
		long productId = product.getId();
		entityManager.flush();
		entityManager.clear();
		categoryRepo.deleteById(underTestId);
		Optional<Product> potentialProduct = productRepo.findById(productId);
		boolean actual = potentialProduct.isPresent();
		assertThat(actual, is(false));
	}

}
