package org.wecancodeit.pantryplus2electricboogaloo.currency;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {

	Optional<Currency> findByName(String currencyName);

}
