package com.springtest.jsondb.repository;

import com.springtest.jsondb.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
