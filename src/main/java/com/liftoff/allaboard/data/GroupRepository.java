package com.liftoff.allaboard.data;

import com.liftoff.allaboard.models.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group, Integer> {

   // Group findByGroupId(int groupId);

}
