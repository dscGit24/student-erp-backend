package com.ds.studenterp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ds.studenterp.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	Optional<Admin> findByEmailAndPassword(String email, String password);
}
