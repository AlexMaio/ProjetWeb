package com.projetweb.spring.security.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_table")
public class Order {

	@Id
	@GeneratedValue
	private int id;
	private int idUser;
	private String address;
	private String city;
	private String zipCode;
	private String phoneNumber;
	private String cardOwner;
	private String cardNumber;
	private String cardCode;
	private String cardMonth;
	private String cardYear;
	private String statut;
	private float total;
	
	public Order() {}
	
	public Order(int idUser, String address, String city, String zipCode, String phoneNumber, String cardOwner,
					String cardNumber, String cardCode, String cardMonth, String cardYear, String staut, float total) {
		this.setIdUser(idUser);
		this.setAddress(address);
		this.setCity(city);
		this.setZipCode(zipCode);
		this.setPhoneNumber(phoneNumber);
		this.setCardOwner(cardOwner);
		this.setCardNumber(cardNumber);
		this.setCardCode(cardCode);
		this.setCardMonth(cardMonth);
		this.setCardYear(cardYear);
		this.statut = "En cours";
		this.setTotal(total);
	}

	public int getId() {
		return id;
	}
	
	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardMonth() {
		return cardMonth;
	}

	public void setCardMonth(String cardMonth) {
		this.cardMonth = cardMonth;
	}

	public String getCardYear() {
		return cardYear;
	}

	public void setCardYear(String cardYear) {
		this.cardYear = cardYear;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}
	
}
