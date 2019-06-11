package com.projetweb.spring.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projetweb.spring.security.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	public List<User> findAll();
	
	@Query("SELECT email, password FROM User u WHERE u.email = :email AND u.password = :password")
	public List<User> rechercherUser(@Param("email") String email, @Param("password") String password);
	
	public List<User> findByEmail(String email);
	
	public User findById(int id);
	
	@SuppressWarnings("unchecked")
	public User save(User user);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,
            value = "UPDATE User u "
            		+ "SET first_name = :firstname, last_name = :lastname, email = :email, password = :password "
            		+ "WHERE u.id = :theid")
	public void modifUser(@Param("firstname") String firstname, @Param("lastname") String lastname,
			@Param("email") String email, @Param("password") String password, @Param("theid") int theid);
	
}
