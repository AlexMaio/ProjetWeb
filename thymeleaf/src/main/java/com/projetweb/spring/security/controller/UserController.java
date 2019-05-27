package com.projetweb.spring.security.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.projetweb.spring.security.dao.UserDao;
import com.projetweb.spring.security.model.User;

@Controller
public class UserController {
	
	@Autowired
	private UserDao userDao;
    
    @GetMapping(value = "/admin")
    public String userList(Model model) {
    	
    	List<User> users = userDao.findAll();
        model.addAttribute("user", users);
 
        return "admin/userList";
    }
    
    @PostMapping(value = "/inscription")//
    public String inscription(Model model, HttpServletRequest request) {
    	String inscriptionMessage = "";
    	Boolean validate = true;
    	
    	// Recuperation des données
    	String firstName = request.getParameter("firstName");
    	String lastName = request.getParameter("lastName");
    	String email = request.getParameter("username");
    	String password = request.getParameter("password");
    	String confirmation = request.getParameter("passwordConfirm");
    	
    	//Verification du nom
    	if((firstName == null) || (firstName != null && firstName.trim().length() < 3)) {
    		validate = false;
    		inscriptionMessage += "Le nom doit contenir au moins 3 caractères <BR/>";
    	}
    	
    	//Verification du prénom
    	if((lastName == null) || (lastName != null && lastName.trim().length() < 3)) {
    		validate = false;
    		inscriptionMessage += "Le prénom doit contenir au moins 3 caractères <BR/>";
    	}
    	
    	//Verification de l'email
    	if((email == null) || (!email.toString().matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$"))) {
    		validate = false;
    		inscriptionMessage += "Email non valide <BR/>";
    	}
    	
    	//Verification email déjà inscrit
    	List<User> checkemail = userDao.findByEmail(email);
    	if(checkemail != null && !checkemail.isEmpty()) {
         	validate = false;
        	inscriptionMessage += "Email déjà utilisé <BR/>";
         }
    	

    	//Verfication du mot de passe
         if((password == null) || (password != null && password.trim().length() < 3)) {
     		validate = false;
     		inscriptionMessage += "Le mot de passe doit contenir au moins 3 caractères <BR/>";
     	}
         
        //Verification de la confirmation
         if(!password.equals(confirmation)) {
        	 validate = false;
      		inscriptionMessage += "Les mots de passe sont différents <BR/>";
         }
         
         if(validate) {
        	 User newUser = new User(firstName, lastName, email, password);
        	 userDao.save(newUser);
        	 model.addAttribute("inscriptionMessage", "Utilisateur inscrit!");
             return "index";
         }
    	
    	model.addAttribute("inscriptionMessage", inscriptionMessage);
        return "inscription";
    }
}
