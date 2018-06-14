package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Student entity.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

}
