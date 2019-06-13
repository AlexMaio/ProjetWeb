package com.projetweb.spring.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projetweb.spring.security.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

	public List<Product> findAll();
	
	public Product findById(int id);
	
	@SuppressWarnings("unchecked")
	public Product save(Product product);

	List<Product> findByPrixGreaterThan(int prixLimit);
	
	List<Product> findByNomLike(String recherche);
	
	@Query(nativeQuery = true,
            value = "SELECT TOP 3 * FROM Product ORDER BY prix DESC")
	public List<Product> carouselIndex();
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,
            value = "DELETE FROM Product WHERE product.id = :id")
	public void deleteId(@Param("id") int id);
	
	@Query(nativeQuery = true,
            value = "SELECT Max(id) FROM Product")
	public int idLastProduct();
	
}
