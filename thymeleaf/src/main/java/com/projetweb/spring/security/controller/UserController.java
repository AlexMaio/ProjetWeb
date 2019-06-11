package com.projetweb.spring.security.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.projetweb.spring.security.dao.UserDao;
import com.projetweb.spring.security.model.User;

@Controller
public class UserController {
	
	private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
	
    @Autowired
    public UserController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
       this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }
	
	@Autowired
	private UserDao userDao;
	
    @GetMapping("/myAccount")
    public String userIndex(Model model) {
    	//On recupère les infos du compte
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String email = authentication.getName();
		List<User> user = userDao.findByEmail(email);
		model.addAttribute("user", user.get(0));
		
        return "user/myAccount";
    }
    
    @PostMapping(value = "/updateUser")
    public String updateUser(Model model, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	
    	//On recupère les infos du compte
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String email = authentication.getName();
		List<User> user = userDao.findByEmail(email);
		model.addAttribute("user", user.get(0));
		
		String modifInfoMessage = "";
    	Boolean validate = true;
    	
    	// Recuperation des données
    	String firstName = request.getParameter("firstName");
    	String lastName = request.getParameter("lastName");
    	//String email = request.getParameter("username");
    	String password = request.getParameter("password");
    	String confirmation = request.getParameter("passwordConfirm");
    	
    	//Verification du nom
    	if((firstName == null) || (firstName != null && firstName.trim().length() < 3)) {
    		validate = false;
    		modifInfoMessage += "Le nom doit contenir au moins 3 caractères <BR/>";
    	}
    	
    	//Verification du prénom
    	if((lastName == null) || (lastName != null && lastName.trim().length() < 3)) {
    		validate = false;
    		modifInfoMessage += "Le prénom doit contenir au moins 3 caractères <BR/>";
    	}
    	
    	/*Verification de l'email
    	if((email == null) || (!email.toString().matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$"))) {
    		validate = false;
    		modifInfoMessage += "Email non valide <BR/>";
    	}
    	
    	//Verification email déjà inscrit
    	//Si l'adresse mail a été changé
    	if(!email.equals(user.get(0).getEmail())) {
    		List<User> checkemail = userDao.findByEmail(email);
        	if(checkemail != null && !checkemail.isEmpty()) {
             	validate = false;
             	modifInfoMessage += "Email déjà utilisé <BR/>";
             } 
    	}*/

    	//Verfication du mot de passe
    	//On verifie d'abord si le mot de passe a été modifié
    	if(!password.equals("")) {
	        if((password == null) || (password != null && password.trim().length() < 3)) {
	     		validate = false;
	     		modifInfoMessage += "Le mot de passe doit contenir au moins 3 caractères <BR/>";
	     	}
	         
	        //Verification de la confirmation
	         if(!password.equals(confirmation)) {
	        	 validate = false;
	        	 modifInfoMessage += "Les mots de passe sont différents <BR/>";
	         }
    	}
         
         if(validate) {
        	 // Si le mot de passe *a été changé
        	 if(!password.equals("")) {
        		 User updateUser = new User(firstName, lastName, email, password);	 
        		 userDao.modifUser(updateUser.getFirstName(), updateUser.getLastName(), updateUser.getEmail(), 
        				 updateUser.getPassword(), user.get(0).getId());
        	 } else {
        		 User updateUser = new User(firstName, lastName, email, user.get(0).getPassword());
        		 userDao.modifUser(updateUser.getFirstName(), updateUser.getLastName(), updateUser.getEmail(), 
        				 updateUser.getPassword(), user.get(0).getId());
        	 }
        	 session.setAttribute("inscriptionSucces", "<center>Vos informations ont bien été modifiées.</center>");
             return "redirect:/index";
         }
		
    	model.addAttribute("modifInfoMessage", modifInfoMessage);
        return "user/myAccount";
    }
    
    @PostMapping(value = "/inscription")
    public String inscription(Model model, HttpServletRequest request) {
    	HttpSession session = request.getSession();
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
        	 inMemoryUserDetailsManager.createUser(org.springframework.security.core.userdetails.User.withUsername(newUser.getEmail()).password(newUser.getPassword()).roles("USER").build());
        	 session.setAttribute("inscriptionSucces", "<center>Inscription validée! Veuillez vous connecter pour confirmer votre inscription.</center>");
             return "redirect:/index";
         }
    	
    	model.addAttribute("inscriptionMessage", inscriptionMessage);
        return "inscription";
    }
}
