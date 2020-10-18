package com.in28minutes.rest.webservices.restfulwebservice.shopUntilDrop;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {
	@Id
	@GeneratedValue
	private Long id;
	private String description;
	private Date date;
	private String name;
	private Double price;
	private String currency;
	private Integer quantity;
	private Double total;
	private String seller;
	@Lob
	private Blob img;
//	@Column(name = "picByte", length = 1000)
	private byte[] picByte;
	private String imageName;
	private String imageUrl;
	private String imageId;
	private String imageDeletehash;



	protected Item() {
		
	}

	public Item(Long idCounter, String description, Date date, String name, Double price, 
			String currency, Integer quantity, Double total, String seller, Blob img, 
			byte[] picByte, String imageName, String imageUrl, String imageDeletehash, String imageId) {
		super();
		this.id = idCounter;
		this.description = description;
		this.date = date;
		this.name = name;
		this.price = price;
		this.currency = currency;
		this.quantity = quantity;
		this.total = total;
		this.seller = seller;
		this.img = img;
		this.picByte = picByte;
		this.imageName = imageName;
		this.imageUrl = imageUrl;
		this.imageDeletehash = imageDeletehash;
		this.imageId = imageId;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageDeletehash() {
		return imageDeletehash;
	}

	public void setImageDeletehash(String imageDeletehash) {
		this.imageDeletehash = imageDeletehash;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Blob getImg() {
		return img;
	}

	public void setImg(Blob img) {
		this.img = img;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
