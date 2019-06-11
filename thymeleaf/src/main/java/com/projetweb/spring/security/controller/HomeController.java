package com.projetweb.spring.security.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.projetweb.spring.security.dao.ProductDao;
import com.projetweb.spring.security.model.Product;

@Controller
public class HomeController {
	
	private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
	
    @Autowired
    public HomeController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
       this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }
	
	@Autowired
	private ProductDao productDao;

    @GetMapping(value = {"/", "/index", "/home"})
    public String root(Model model, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	if(session.getAttribute("inscriptionSucces") != null) {
			model.addAttribute("inscriptionSucces",  session.getAttribute("inscriptionSucces"));
			session.setAttribute("inscriptionSucces", null);
		}
    	
    	List<Product> products = productDao.carouselIndex();
    	model.addAttribute("firstProduct", products.get(0));
    	model.addAttribute("secondProduct", products.get(1));
    	model.addAttribute("thirdProduct", products.get(2));

        return "index";
    }

    @GetMapping("/login")
    public String login() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/myAccount";
        }
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

    @GetMapping("/inscription")
    public String inscription() {
        return "inscription";
    }
    
    @GetMapping("/saveUser")
    public String saveUser(String email, String password) {
    	inMemoryUserDetailsManager.createUser(User.withUsername(email).password(password).roles("USER").build());
    	
    	return "index";
    }
    
}
