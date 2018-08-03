package org.wecancodeit.pantryplus2electricboogaloo.lineitem;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.wecancodeit.pantryplus2electricboogaloo.product.Product;
import org.wecancodeit.pantryplus2electricboogaloo.product.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LineItemJpaTest {

	@Resource
	private ProductRepository productRepo;
	
	@Resource
	private TestEntityManager entityManager;
	
	@Resource
	private LineItemRepository lineItemRepo;

	@Test
	public void shouldLoadLineItemFromProduct() {
		Product product = new Product("", null, "", false);
		product = productRepo.save(product);
		long productId = product.getId();
		LineItem lineItem = new LineItem(null, product);
		lineItemRepo.save(lineItem);
		entityManager.flush();
		entityManager.clear();
		product = productRepo.findById(productId).get();
	}
	
}
