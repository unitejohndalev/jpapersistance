package com.perjpasample.jpapersistance.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perjpasample.jpapersistance.main.model.PositionModel;
import com.perjpasample.jpapersistance.main.repository.PositionRepository;

@Service
public class PositionService {
    
    @Autowired
    private PositionRepository positionRepository;

    public PositionModel savePosition(PositionModel positionModel) {
        return positionRepository.save(positionModel);
    }

    public List<PositionModel> getPositions() {
        return positionRepository.findAll();
    }


    public PositionModel getPositionById(Integer id) {
        return positionRepository.findById(id).orElse(null);
    }

    public PositionModel updatePositionById(Integer id, PositionModel positionModel) {
        positionModel.setId(id);
        return positionRepository.save(positionModel);
    }

    public void deletePosition(Integer id) {
        positionRepository.deleteById(id);
    }

}
