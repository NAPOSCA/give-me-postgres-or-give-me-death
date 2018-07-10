package org.wecancodeit.pantryplus2electricboogaloo.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{

	Optional<User> findByGoogleName(String googleName);

}
