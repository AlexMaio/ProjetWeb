package com.projetweb.spring.security.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.projetweb.spring.security.dao.OrderDao;
import com.projetweb.spring.security.dao.OrderDetailDao;
import com.projetweb.spring.security.dao.ProductDao;
import com.projetweb.spring.security.dao.UserDao;
import com.projetweb.spring.security.model.Order;
import com.projetweb.spring.security.model.OrderDetail;
import com.projetweb.spring.security.model.Product;
import com.projetweb.spring.security.model.User;

@Controller
public class AdminController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderDetailDao orderDetailDao;
    
    @GetMapping(value = "/userList")
    public String userList(Model model) {
    	
    	List<User> users = userDao.findAll();
        model.addAttribute("user", users);
 
        return "admin/userList";
    }
    
    @GetMapping(value = "/orderList")
    public String orderList(Model model) {
    	
    	// Recuperation des commandes
    	List<Order> orders = orderDao.findAll();
    	
    	// Recuperation des donnes de l'acheteur pour chaque commande
    	List<User> users = new ArrayList<>();
    	for(Order order : orders) {
    		User user = userDao.findById(order.getIdUser());
    		System.out.println("Id commande" + order.getId());
    		users.add(user);
    	}
    	
        model.addAttribute("order", orders);
        model.addAttribute("user", users);
    	
        return "admin/orderList";
    }
    
    @GetMapping(value = "/detailOrder/{id}")
    public String detailOrder(@PathVariable String id, Model model, HttpServletRequest request) {
    	
    	//Recuperation de la commande
    	Order order = orderDao.findById(Integer.valueOf(id));
    	
    	//Recuperation de l'acheteur
    	User user = userDao.findById(order.getIdUser());
    	
    	//Recuperation des produits
    	List<OrderDetail> orderDetail = orderDetailDao.listeIdProducts(Integer.valueOf(id));
    	List<Product> products = new ArrayList<>();
    	for(OrderDetail idProduit : orderDetail) {
    		Product product = productDao.findById(idProduit.getIdProduct());
    		products.add(product);
    	}
    	
    	model.addAttribute("product", products);
    	model.addAttribute("user", user);
    	model.addAttribute("order", order);
    	
    	return "admin/detailOrder";
    }
    
	@GetMapping(value = "/productsList")
	public String listeProduits(Model model){
		
		List<Product> products = productDao.findAll();
        model.addAttribute("product", products);
 
        return "admin/productsList";
	}
}
