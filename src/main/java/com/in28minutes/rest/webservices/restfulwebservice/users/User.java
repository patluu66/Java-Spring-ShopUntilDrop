package com.in28minutes.rest.webservices.restfulwebservice.users;

import java.util.Date;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.bash.rest.webservices.bashwebservices.todo.TodoHardcodedService;

//import com.bash.rest.webservices.bashwebservices.todo.Todo;

import io.swagger.annotations.ApiModel;



@ApiModel(description="All details about the user. ")
@Entity
@Service
public class User {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private Date birthDate;
	
	@OneToMany(mappedBy = "user")
	private List<Post> posts;
	
	private String city;
	private String state;
	private String street;
	private String password;
	private Date dateStamp;
	private String email;
	private String phone;

	
	protected User() {
		
	}
	
	public User(Integer id, String name, Date birthDate, String city, String state, String street, String password) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.city = city;
		this.state = state;
		this.street = street;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Post save(Post post) {
		if(post.getId() == -1 || post.getId() == 0) {
			post.setId(++id);
			System.out.println("Id counter: " + id);
			posts.add(post);
		} else {
			deleteById(post.getId());
			posts.add(post);
		}
		return post;
	}
	
	public Post deleteById(long id) {
		Post post = findById(id);
		
		if(post == null) return null;
		
		if(posts.remove(post)) {
			return post;
		}
		return null;
	}
	
	public Post findById(long id) {
		for(Post post:posts) {
			if(post.getId() == id) {
				return post;
			}
		}
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post findById(int id) {
		for(Post post: posts) {
			if(post.getId() == id) {
				return post;
			}
		}
		return null;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", city=" + city + ", state=" + state
				+ ", street=" + street + "]";
	}
}
