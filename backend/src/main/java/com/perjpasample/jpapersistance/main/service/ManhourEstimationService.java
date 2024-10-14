package com.perjpasample.jpapersistance.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perjpasample.jpapersistance.main.model.ManhourEstimationModel;
import com.perjpasample.jpapersistance.main.repository.ManhourEstimationRepository;

@Service
public class ManhourEstimationService {

    @Autowired
    private ManhourEstimationRepository manhourEstimationRepository;

    public ManhourEstimationModel save(ManhourEstimationModel manhourEstimationModel) {
        return manhourEstimationRepository.save(manhourEstimationModel);

    }

    public List<ManhourEstimationModel> getManhourEstimations() {
        return manhourEstimationRepository.findAll();
    }

}
