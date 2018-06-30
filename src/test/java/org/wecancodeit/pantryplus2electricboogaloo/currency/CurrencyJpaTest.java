package org.wecancodeit.pantryplus2electricboogaloo.currency;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CurrencyJpaTest {
	
	@Resource
	private CurrencyRepository currencyRepo;
	
	@Resource
	private TestEntityManager entityManager;
	
	@Test
	public void shouldSaveAndLoad() {
		Currency currency = new Currency("Coupons");
		currency = currencyRepo.save(currency);
		long id = currency.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		boolean isPresent = currencyRepo.findById(id).isPresent();
		assertThat(isPresent, is(true));
	}
	
	@Test
	public void shouldSaveAndLoadTwoObjects() {
		currencyRepo.save(new Currency("First Currency"));
		currencyRepo.save(new Currency("Second Currency"));
	}
	
}
