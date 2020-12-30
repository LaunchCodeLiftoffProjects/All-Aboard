package com.liftoff.helloworld.models.data;

import com.liftoff.helloworld.models.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface MessageRepository extends CrudRepository<Message, Integer> {
}





