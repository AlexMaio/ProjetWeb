package com.projetweb.spring.security.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

	@Id
	@GeneratedValue
	private int id;
	private int idProduct;
	private int idOrder;
	
	public OrderDetail() {}
	
	public OrderDetail(int idProduct, int idOrder) {
		this.setIdProduct(idProduct);
		this.setIdOrder(idOrder);
	}

	public int getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}
	
}
