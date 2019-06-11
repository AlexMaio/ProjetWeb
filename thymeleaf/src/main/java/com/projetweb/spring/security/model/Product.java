package com.projetweb.spring.security.model;

import javax.persistence.*;

@Entity
@Table(name = "Product")
public class Product {

	@Id
	@GeneratedValue
	private int id;
	private String image;
	private String nom;
	private float prix;
	
	public Product() {}
	
	public Product(String image, String nom, float prix) {
		this.image = image;
		this.nom = nom;
		this.prix = prix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
