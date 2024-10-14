package com.perjpasample.jpapersistance.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perjpasample.jpapersistance.main.model.BilledManhourModel;
import com.perjpasample.jpapersistance.main.repository.BilledManhourRepository;

@Service
public class BilledManhourService {
    
    @Autowired
    private BilledManhourRepository billedManhourRepository;

    public BilledManhourModel saveBilledManhour(BilledManhourModel billedManhour) {
        return billedManhourRepository.save(billedManhour);
    }

    public BilledManhourModel getBilledManhourById(Long id) {
        return billedManhourRepository.findById(id).orElse(null);
    }

   
    public List<BilledManhourModel> getBilledManhours() {
        return billedManhourRepository.findAll();
    }

    

}
