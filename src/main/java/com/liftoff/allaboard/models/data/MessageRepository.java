package com.liftoff.allaboard.models.data;


import com.liftoff.allaboard.models.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface MessageRepository extends CrudRepository<Message, Integer> {
}





