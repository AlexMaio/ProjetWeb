package com.projetweb.spring.security.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.projetweb.spring.security.dao.OrderDao;
import com.projetweb.spring.security.dao.OrderDetailDao;
import com.projetweb.spring.security.dao.ProductDao;
import com.projetweb.spring.security.dao.UserDao;
import com.projetweb.spring.security.model.Order;
import com.projetweb.spring.security.model.OrderDetail;
import com.projetweb.spring.security.model.Product;
import com.projetweb.spring.security.model.User;

@Controller
@MultipartConfig
public class AdminController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4651877719815157591L;

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
	
	@GetMapping(value = "/removeProduct/{id}")
    public String removeProduct(@PathVariable String id) {
    	System.out.println("Id du produit supprimé : " + id);
    	productDao.deleteId(Integer.parseInt(id));
    	
    	return "redirect:/productsList";
    }
	
	@GetMapping(value = "/removeUser/{id}")
    public String removeUser(@PathVariable String id) {
    	System.out.println("Id du user supprimé : " + id);
    	userDao.deleteId(Integer.parseInt(id));
    	
    	return "redirect:/userList";
    }
	
	@GetMapping(value = "/addProduct")
	public String ajouterProduit(){ 
        return "admin/addProduct";
	}
	
	@PostMapping(value = "/addNewProduct")
	public String ajouterNouveauProduit(Model model, HttpServletRequest request){
		String ajoutMessage = "";
    	Boolean validate = true;
    	
    	// Recuperation des données
    	String nom = request.getParameter("nom");
    	Float prix = Float.valueOf(request.getParameter("prix"));
    	
    	//Verification du nom
    	if((nom == null) || (nom != null && nom.trim().length() < 3)) {
    		validate = false;
    		ajoutMessage += "Le nom doit contenir au moins 3 caractères <BR/>";
    	}
    	
    	if(validate) {
		
	    	try {
	    		//On recupere l'extension du fichier uploadé
	    		Part filePart = request.getPart("image");
		        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		        System.out.println("Downloaded file name : " + fileName);
		        String extension = fileName.substring(fileName.lastIndexOf('.'));
	    		
	    		//On recupere l'id du dernier produit qu'on incremente et on ajoute l'extension pour le nom du nouveau
	    		String nomImage = Integer.toString(productDao.idLastProduct()+1);
	    		nomImage += extension;
	    		System.out.println("Saved file name : " + nomImage);
				
	    		//On enregistre le fichier
		        File uploads = new File("C:\\Users\\Alexandre\\git\\repository3\\thymeleaf\\src\\main\\resources\\static\\img\\product\\");
		        File file = new File(uploads, nomImage);
		        InputStream input = filePart.getInputStream();
		        Files.copy(input, file.toPath());
		        
		        Product product = new Product(nomImage, nom, prix);
	    		productDao.save(product);
	    		
		        return "redirect:/productsList";
			} catch (IOException|ServletException e) {
				ajoutMessage += "Erreur dans l'upload de l'image <BR/>";
				e.printStackTrace();
				model.addAttribute("ajoutMessage", ajoutMessage);
				return "admin/addProduct";
			}
    	
    	}
    
    	model.addAttribute("ajoutMessage", ajoutMessage);
    	return "admin/addProduct";
	}
	
}
