package com.jsp.agro.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.agro.entity.Image;

public interface ImageRepo extends JpaRepository<Image, Integer> {
	 
//	@Query("selectbn from Image n where n.name=?1")
//	Optional<Image> findByName(String name);
}
