package com.example.springbootrestdemo;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class HelloController {
	
	@Autowired
	private UserJpaRepository  userJpaRepository;
	private static final String template ="Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@GetMapping("/hi")
	public String hello() {
		return "Hello World";
	}
	
	@GetMapping("/hello")
	public String hi() {
		return "Welcome to spring boot application";
	}
	
	@GetMapping(path ="/welcome", produces = {MediaType.APPLICATION_XML_VALUE})
	public Greeting welcome(@RequestParam(value="name",defaultValue = "Welcome To Rest Services") String name) {
		System.out.println("welcome");
		return new Greeting(counter.incrementAndGet(),String.format(template, name));
	}
	
	@GetMapping(path ="/greeting", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Greeting greeting(@RequestParam(value="field",defaultValue = "switterland") String name) {
		return new Greeting(counter.incrementAndGet(),String.format(template, name));
	}
	
	//users
	
	@GetMapping(value="/all",produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Users> findAll(){
		return userJpaRepository.findAll();
	}
	
	@GetMapping(value="/{name}")
	public Users findByName(@PathVariable final String name) {
		return userJpaRepository.findByName(name);
	}
	
	@PostMapping(value="/create")
	public Users create(@RequestBody final Users users) {
		userJpaRepository.save(users);
		return userJpaRepository.findByName(users.getName());
	}
	
	
}
