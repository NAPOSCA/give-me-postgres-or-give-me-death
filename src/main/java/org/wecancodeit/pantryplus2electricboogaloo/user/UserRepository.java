package org.wecancodeit.pantryplus2electricboogaloo.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<PantryUser, Long> {

	Optional<PantryUser> findByGoogleName(String googleName);

}
