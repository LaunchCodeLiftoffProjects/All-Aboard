package com.liftoff.allaboard.data;

import com.liftoff.allaboard.models.GameGroup;
import com.liftoff.allaboard.models.GroupMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends CrudRepository<GroupMember, Integer> {

}
