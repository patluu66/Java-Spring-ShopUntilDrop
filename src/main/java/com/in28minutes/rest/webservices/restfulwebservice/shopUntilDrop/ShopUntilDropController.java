package com.in28minutes.rest.webservices.restfulwebservice.shopUntilDrop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.rest.webservices.restfulwebservice.helloWorld.HelloWorldBean;
import com.in28minutes.rest.webservices.restfulwebservice.users.Post;
import com.in28minutes.rest.webservices.restfulwebservice.users.PostRepository;


@RestController
public class ShopUntilDropController {
	@Autowired
	private ItemHardcodedService itemService;
	
//	@Autowired
//	private ItemRepository itemRepository;
	
	@RequestMapping(method = RequestMethod.GET, path = "/shopUntilDrop")
	public String helloWorld() {
		return "Welcome to shopUntilDrop";
	}
	
	@GetMapping(path = "/shopUntilDrop-bean")
	public ShopUntilDropBean shopUntilDropBean() {
		return new ShopUntilDropBean("Shop Until Drop Store - changed");
	}
	
	//get hardcode items
	@GetMapping("/shop/items")
	public List<Item> retrieveAllItem() {
		return itemService.findAll();
	}
	
	
//	@GetMapping("/shop/items")
//	public List<Item> retrieveAllItem() {
//		return itemRepository.findAll();
//	}

}
