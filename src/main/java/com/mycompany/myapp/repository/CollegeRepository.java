package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.College;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the College entity.
 */
public interface CollegeRepository extends JpaRepository<College, Long> {

}
