package com.in28minutes.rest.webservices.restfulwebservice.users;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query(value = "SELECT * FROM USER WHERE NAME = 'Irving'", nativeQuery = true)
	User findCity(String city);
	User findByName(String name);
}
