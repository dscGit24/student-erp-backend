package com.ds.studenterp.service;

import com.ds.studenterp.entity.Admin;
import com.ds.studenterp.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin login(String email, String password){
        return adminRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid Credentials"));
    }
}
