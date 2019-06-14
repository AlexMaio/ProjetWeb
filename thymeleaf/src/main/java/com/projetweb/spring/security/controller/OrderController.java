package com.projetweb.spring.security.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class OrderController {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderDetailDao orderDetailDao;
	
    public static float roundTotal(float value) {
        int pow = 10;
        for (int i = 1; i < 2; i++) {
            pow *= 10;
        }
        float tmp = value * pow;
        float tmpSub = tmp - (int) tmp;

        return ( (float) ( (int) (
                value >= 0
                ? (tmpSub >= 0.5f ? tmp + 1 : tmp)
                : (tmpSub >= -0.5f ? tmp : tmp - 1)
                ) ) ) / pow;
    }
    
    @SuppressWarnings("unchecked")
	@GetMapping("/cart")
    public String cart(Model model, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	if(session.getAttribute("cart") != null ) {
	    	// On recupere le contenu du panier
			List<Product> products = (List<Product>) session.getAttribute("cart");
			// On calcule le montant total
			Float total = 0.f;
			for(Product product : products) {
				total += product.getPrix();
			}
			
			model.addAttribute("total", roundTotal(total));
	        model.addAttribute("product", products);
    	}
        return "cart";
    }

    @SuppressWarnings("unchecked")
	@GetMapping(value = "/addToCart/{id}")
    public String addToCart(@PathVariable String id, Model model, HttpServletRequest request) {
    	System.out.println("Id du produit ajouté au panier : " + id);
    	HttpSession session = request.getSession();
    	//Teste si le panier est vide ou non
    	if(session.getAttribute("cart") == null ) {
    		List<Product> products = new ArrayList<>();
    		products.add(productDao.findById(Integer.parseInt(id)));
    		session.setAttribute("cart", products);
    	}
    	else {
    		List<Product> products = (List<Product>) session.getAttribute("cart");
    		products.add(productDao.findById(Integer.parseInt(id)));
    		session.setAttribute("cart", products);
    	}
    	
    	 return "redirect:/productList";
    }
    
    @SuppressWarnings("unchecked")
	@GetMapping(value = "/removeToCart/{id}")
    public String removeToCart(@PathVariable String id, Model model, HttpServletRequest request) {
    	System.out.println("Id du produit retiré du panier : " + id);
    	HttpSession session = request.getSession();
    	if(session.getAttribute("cart") != null ) {    		
	    	// On recupere le contenu du panier
			List<Product> products = (List<Product>) session.getAttribute("cart");			
			for(Product product : products) {
				if(product.getId() == Integer.parseInt(id)) {
					products.remove(product);
					break;
				}
			}			
			session.setAttribute("cart", products);
    	}
    	
    	return "redirect:/cart";
    }
    
	@SuppressWarnings("unchecked")
	@GetMapping("/checkout")
    public String checkout(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
    	if(session.getAttribute("cart") != null ) {
	    	// On recupere le contenu du panier
			List<Product> products = (List<Product>) session.getAttribute("cart");
			// On calcule le montant total
			Float total = 0.00f;
			for(Product product : products) {
				total += product.getPrix();
			}
			
			if(session.getAttribute("checkoutMessage") != null) {
				model.addAttribute("checkoutMessage",  session.getAttribute("checkoutMessage"));
				session.setAttribute("checkoutMessage", null);
			}
			
			session.setAttribute("total", roundTotal(total));			
			model.addAttribute("total",  roundTotal(total));
	        model.addAttribute("product", products);
	        
	        return "checkout";
    	}
        return "redirect:/productList";
    }
	
    @SuppressWarnings("unchecked")
	@PostMapping(value = "/checkoutSuccess")
    public String checkoutSuccess(Model model, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	if(session.getAttribute("cart") == null || session.getAttribute("total") == null) {
            return "redirect:/cart";
    	}
    	
    	String checkoutMessage = "";
    	Boolean validate = true;
    	
    	// Recuperation des données
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String email = authentication.getName();
    	List<Product> products = (List<Product>) session.getAttribute("cart");
    	Float total = (Float) session.getAttribute("total");
    	String address = request.getParameter("address");
    	String city = request.getParameter("city");
    	String zipCode = request.getParameter("zipCode");
    	String phoneNumber = request.getParameter("phoneNumber");
    	String cardOwner = request.getParameter("cardOwner");
    	String cardNumber = request.getParameter("cardNumber");
    	String cardCode = request.getParameter("cardCode");
    	String cardMonth = request.getParameter("cardMonth");
    	String cardYear = request.getParameter("cardYear");
    	
    	//Verification du total
    	if(total == null || total <= 0.f) {
    		return "redirect:/productList";
    	}
    	
    	//Verification de l'adresse
    	if((address == null) || (address != null && address.trim().length() < 3)) {
    		validate = false;
    		checkoutMessage += "Adresse non valide <BR/>";
    	}
    	
    	//Verification de la ville
    	if((city == null) || (city != null && city.trim().length() < 3)) {
    		validate = false;
    		checkoutMessage += "Ville non valide <BR/>";
    	}
    	
    	//Verification du code postal
    	if((zipCode == null) || (!zipCode.toString().matches("^([0-9]{5})$"))) {
    		validate = false;
    		checkoutMessage += "Code postal non valide <BR/>";
    	}
    	
    	//Verification du telephone
    	if((phoneNumber == null) || (!phoneNumber.toString().matches("^0[1-9]([-. ]?[0-9]{2}){4}$"))) {
    		validate = false;
    		checkoutMessage += "Numéro de téléphone non valide <BR/>";
    	}
    	
    	//Verification du titulaire de la carte
    	if((cardOwner == null) || (cardOwner != null && cardOwner.trim().length() < 3)) {
    		validate = false;
    		checkoutMessage += "Le nom du titulaire de la carte doit contenir au moins 3 caractères <BR/>";
    	}
    	
    	//Verification du numero de carte
    	if((cardNumber == null) || (!cardNumber.toString().matches("^[0-9]{16}$"))) {
    		validate = false;
    		checkoutMessage += "Numero de carte non valide <BR/>";
    	}
    	
    	//Verification du cryptogramme
    	if((cardCode == null) || (cardCode != null && cardCode.trim().length() != 3)) {
    		validate = false;
    		checkoutMessage += "Cryptogramme visuel non valide <BR/>";
    	}
    	
    	//Verification de la date d'expiration
    	if((cardMonth == "") || (cardYear== "")) {
    		validate = false;
    		checkoutMessage += "Veuillez choisir une date d'expiration <BR/>";
    	}
    	
    	if(validate) {
    		//Commande validée
    		
    		//On recupère les infos du compte acheteur
    		List<User> user = userDao.findByEmail(email);
    		
    		//On ajoute la commande à la BDD
    		Order order = new Order(user.get(0).getId(), address, city, zipCode, phoneNumber, cardOwner,
    				cardNumber, cardCode, cardMonth, cardYear, "En cours", total);
    		orderDao.save(order);
    		
    		//On recupère l'id
    		int idLastOrder = orderDao.idLastOrder();
    		
    		//On ajoute la liste des produit à la table orderDetail
    		for(Product product : products) {
    			OrderDetail orderDetail = new OrderDetail(product.getId(), idLastOrder);
    			orderDetailDao.save(orderDetail);
    		}
    		
    		// On vide le panier
    		session.setAttribute("cart", null);
    		
    		return "checkoutSucces";
    		
    	}
    	
    	session.setAttribute("checkoutMessage", checkoutMessage);
    	return "redirect:/checkout";
    }

}
