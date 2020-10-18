package com.in28minutes.rest.webservices.restfulwebservice.users;



import java.net.URI;


import org.springframework.web.client.RestTemplate;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



//import com.bash.rest.webservices.bashwebservices.todo.Todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@CrossOrigin(origins="http://localhost:4200")
@CrossOrigin(origins= {"http://localhost:4200"})
@RestController

public class UserJPAResource {
	
	@Autowired
	private User userService;
	
	@Autowired
	private UserDaoService service;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	Logger logger = LoggerFactory.getLogger(UserJPAResource.class);
	
	
	//get Zillow Api working
	public String getZillow()
	{
		final String uri = 
	    		"https://www.zillow.com/webservice/";
	     
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	     
	    return result;	        	
	}
	
	@GetMapping(value = "/zillow", produces = MediaType.APPLICATION_XML_VALUE)
	public String getAllEmployeesXML() 
	{
	    return getZillow();
	}
	
	//testing Zillow Api with parameter, not working yet
	public String getZillow2(String[] fullAddress)
	{	
		final String uri = 
	    		"https://www.zillow.com/webservice" 
	    				+ fullAddress[0] + "&citystatezip=" + fullAddress[1] + "+" + fullAddress[2];
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		String responseBody = response.getBody();
	     
	    return responseBody;	        	
	}
	
	
	@GetMapping("/jpa/giphy")
	public String retrieveGiphy() {
		final String uri = "http://api.giphy.com/v1/";
	     
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
		    
		return result;
	}
	
	//testing get find by name
	@GetMapping("/jpa/posts/name")
	public List<Post> retrieveListingName() {
		return postRepository.findByName("Patrick");
	}
	
	//testing to find same address
	@GetMapping("/jpa/posts/address")
	public List<Post> retrieveListingAddress() {
		return postRepository.findByAddress("888 19th Ave. Alameda, Ca");
	}
	
	//testing commit push 1pm
	//testing to find same zpid 7/15
	@GetMapping("/jpa/posts/zillowId/{zpid}")
	public List<Post> retrieveListingZpid(@PathVariable String zpid) {
		return postRepository.findByZpid(zpid);
	}
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}
	
	//get post
	@GetMapping("/jpa/posts")
	public List<Post> retrieveAllPost() {
		return postRepository.findAll();
	}
	
	//get post by id
	@GetMapping("/jpa/posts/{id}")
	public Optional<Post> retrieveOnePost(@PathVariable int id) {
		Optional<Post> post = postRepository.findById(id);
		return post;
	}
	
	//post comment
	@PostMapping("/jpa/posts")
	public ResponseEntity<Object> postObject(@RequestBody Post post) {
		Post savePost =  postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(savePost.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	//testing branch commit master
	//	not working yet, Edit comment
	@PutMapping("jpa/user/posts/{id}")
	public ResponseEntity<Post> updateTodo(
			@PathVariable int id, @RequestBody Post post) {
		
		Post postUpdated = postRepository.save(post);
		
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}
	
	//delete comment
	@DeleteMapping("/jpa/posts/{id}")
	public void deletePost(@PathVariable int id) {
		postRepository.deleteById(id);
	}
	
	@GetMapping("/jpa/city")
	public List<User> retrieveCity() {
		return Arrays.asList(userRepository.findCity("Irving"));
	}
	
	@GetMapping("/jpa/users/{id}")
	public Optional<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		return user;
	}
	
	@GetMapping("/jpa/user/name/{name}")
	public User retrieveUserByName(@PathVariable String name) {
		User user = userRepository.findByName(name);
		return user;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllUsers(@PathVariable int id) throws UserPrincipalNotFoundException {
		Optional<User> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserPrincipalNotFoundException("id-" + id);
		}
		
		return userOptional.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) throws UserPrincipalNotFoundException {
		Optional<User> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserPrincipalNotFoundException("id-" + id);
		}
		
		User user = userOptional.get();
		
		post.setUser(user);
		
		Post savedUser = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}	
}



