package com.example.review.service;

import com.example.review.entity.Merchant;
import com.example.review.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {
    @Autowired
    private MerchantRepository merchantRepository;

    public void saveAll(List<Merchant> merchants) {
        merchantRepository.saveAll(merchants);
    }
}
