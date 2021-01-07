package com.liftoff.allaboard.models.data;

import com.liftoff.allaboard.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}
