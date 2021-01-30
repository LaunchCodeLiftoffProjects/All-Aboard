package com.liftoff.allaboard.data;

import com.liftoff.allaboard.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);


    @Query("SELECT u from User u WHERE u.id = :userId")
    public User findById(@Param("id") Integer userId);
}
