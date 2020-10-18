package com.in28minutes.rest.webservices.restfulwebservice.users;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByName(String name);
	List<Post> findByAddress(String address);
	List<Post> findByZpid(String zpid);
}
