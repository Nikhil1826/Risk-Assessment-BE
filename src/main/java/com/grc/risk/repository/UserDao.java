package com.grc.risk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.grc.risk.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	@Query(value = "select count(*) from user;", nativeQuery = true)
	Integer getUserCount();

	User findByUsernameAndPassword(String username, String password);
}
