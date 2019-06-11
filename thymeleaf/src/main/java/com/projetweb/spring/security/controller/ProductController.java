package com.projetweb.spring.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.projetweb.spring.security.dao.ProductDao;
import com.projetweb.spring.security.model.Product;

@Controller
public class ProductController {
	
	@Autowired
	private ProductDao productDao;
	
	@GetMapping(value = "productList")
	public String listeProduits(Model model){
		
		List<Product> products = productDao.findAll();
        model.addAttribute("product", products);
 
        return "productList";
	}
	
	@PostMapping(value = "/AddProduct")
	public void ajouterProduit(@RequestBody Product product) {
		
		productDao.save(product);
		
	}

}
