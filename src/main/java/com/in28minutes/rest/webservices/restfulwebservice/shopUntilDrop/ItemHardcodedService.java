package com.in28minutes.rest.webservices.restfulwebservice.shopUntilDrop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.in28minutes.rest.webservices.restfulwebservice.users.User;

@Service
public class ItemHardcodedService {
	
	private static List<Item> items = new ArrayList();
	private static Long idCounter = (long) 0;
	
	
	static {
		items.add(new Item(++idCounter, "New", new Date(), "Gaming/Productivity PC"));
	
	}
	
	public List<Item> findAll() {
		return items;
	}
	

}
