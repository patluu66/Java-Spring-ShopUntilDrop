package com.in28minutes.rest.webservices.restfulwebservice.shopUntilDrop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemJpaRepository extends JpaRepository<Item, Long> {

}
