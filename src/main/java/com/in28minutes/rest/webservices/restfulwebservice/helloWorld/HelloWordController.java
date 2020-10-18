package com.in28minutes.rest.webservices.restfulwebservice.helloWorld;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

//for getChildNode dom
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
public class HelloWordController {
	
	
	@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World - changed");
	}
	
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}
	
	//get Zillow Api working
	public String getZillow()	{
		final String uri = 
	    		"https://www.zillow.com/webservice/";
	     
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	    
	    Document doc = convertStringToXMLDocument(result);

	    return result;	        	
	}
	
	//test zillow to get in xml formate
	public String getZillowXml()	{
		final String uri = 
	    		"https://www.zillow.com/";
	     
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		String responseBody = response.getBody();
		
	    return responseBody;	        	
	}
	
	@GetMapping(path = "/zillow-bean")
	public HelloWorldBean helloWorldBean2() {
		return new HelloWorldBean(getZillowXml());
	}
	
	
	//testing Zillow Api with parameter, working version
	public String getZillow2(String[] fullAddress)
	{
		final String uri = 
	    		"https://www.zillow.com/webservice" 
	    				+ fullAddress[0] + "&citystatezip=" + fullAddress[1] + "+" + fullAddress[2];
		
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	     
	    return result;	        	
	}
	
	@GetMapping(value = "/zillow/{fullAddress}")
	public HelloWorldBean getAllEmployeesXML2(@PathVariable String[] fullAddress) 
	{
		return new  HelloWorldBean(getZillow2(fullAddress));
	}
	
	
	
	//convert string to xml
	private static Document convertStringToXMLDocument(String xmlString) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
             
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }

}
