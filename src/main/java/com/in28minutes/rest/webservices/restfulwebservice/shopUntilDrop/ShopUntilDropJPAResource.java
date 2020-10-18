package com.in28minutes.rest.webservices.restfulwebservice.shopUntilDrop;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
//import java.net.PasswordAuthentication;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservice.helloWorld.HelloWorldBean;
import com.in28minutes.rest.webservices.restfulwebservice.users.Post;
import com.in28minutes.rest.webservices.restfulwebservice.users.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservice.users.User;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class ShopUntilDropJPAResource {
	
	@Autowired
	private ItemJpaRepository itemJpaRepository;
	
	@Autowired
	ImageRepository imageRepository;
	
	//get Jpa items
	@GetMapping("/jpa/shop/items")
	public List<Item> retrieveAllItemJpa() {
		return itemJpaRepository.findAll();
	}
	
	//post item
	@PostMapping("/jpa/shop/items")
	public ResponseEntity<Object> createItem(@RequestBody Item item) {
		Item savedItem = itemJpaRepository.save(item);
		
//		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(savedItem.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	//testing post image
	@PostMapping("/jpa/shop/items2")
	public BodyBuilder uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		
		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		
		ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()));
		imageRepository.save(img);
		return ResponseEntity.status(HttpStatus.OK);
	}
	
	
	@GetMapping(path = { "/jpa/shop/get/{imageName}" })
	public ImageModel getImage(@PathVariable("imageName") String imageName) throws IOException {
		final Optional<ImageModel> retrievedImage = imageRepository.findByName(imageName);
		ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
				decompressBytes(retrievedImage.get().getPicByte()));
		return img;
	}
	
//	@GetMapping(path = { "/jpa/shop/get/{imageName}" })
//	public ImageModel getImage(@PathVariable("imageName") String imageName) throws IOException {
//		final Optional<ImageModel> retrievedImage = imageRepository.findByName(imageName);
//		ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
//				retrievedImage.get().getPicByte());
//		return img;
//	}
	
	
//	testing get all image
	@GetMapping(path = { "/jpa/shop/get/findAll/{imageName}" })
	public List<ImageModel> getImageArray(@PathVariable("imageName") String imageName) throws IOException {
		int counter= 0;
		
		final Optional<ImageModel> retrievedImage = imageRepository.findByName(imageName);
		ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
				decompressBytes(retrievedImage.get().getPicByte()));
		
//		List<ImageModel> imageArray = new List<ImageModel>();
		
		List<ImageModel> imageList = new ArrayList<ImageModel>();
		imageList.add(counter++, img);
		
		return imageList;
	}
	
	@GetMapping("jpa/shop/get/allImages")
	public List<ImageModel> retrieveAllImages() {
		List<ImageModel> imageArray = imageRepository.findAll();
		
		for(int i = 0; i < imageArray.size(); i++) {
			ImageModel img = new ImageModel(imageArray.get(i).getName(), imageArray.get(i).getType(),
					decompressBytes(imageArray.get(i).getPicByte()));
			imageArray.add(i, img);
		}
		
		System.out.print(imageArray);
		
		return imageArray;
	}
	
//	@GetMapping("jpa/shop/get/allImages")
//	public List<ImageModel> retrieveAllImages() {
//		return imageRepository.findAll();
//	}
//	
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}
	
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	
	
	//delete item
	@DeleteMapping("/jpa/shop/items/{id}")
	public void deleteItem(@PathVariable Long id) {
		itemJpaRepository.deleteById(id);
	}
	
	//edit item
	@PutMapping("jpa/shop/items/{id}")
	public ResponseEntity<Item> updateItem(
			@PathVariable Long id, @RequestBody Item item) {
		
		Item itemUpdated = itemJpaRepository.save(item);
		
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}
	
	
//	http://localhost:9090/sendemail
	@RequestMapping(value = "/sendemail")
	public String sendEmail() {
		return "Email sent successfully";
	}   
	
	private void sendmail() throws AddressException, MessagingException, IOException {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication();
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress(, false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(""));
		   msg.setSubject("Tutorials point email");
		   msg.setContent("Tutorials point email", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Tutorials point email", "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   MimeBodyPart attachPart = new MimeBodyPart();

		   attachPart.attachFile("/var/tmp/image19.png");
		   multipart.addBodyPart(attachPart);
		   msg.setContent(multipart);
		   Transport.send(msg);   
	}

}
