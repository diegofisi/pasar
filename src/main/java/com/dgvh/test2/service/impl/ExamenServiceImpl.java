package com.dgvh.test2.service.impl;

import com.dgvh.test2.model.Examen;
import com.dgvh.test2.repo.IGenericRepo;
import com.dgvh.test2.repo.IExamenRepo;
import com.dgvh.test2.service.IExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamenServiceImpl extends CRUDImpl<Examen, Integer> implements IExamenService {

    @Autowired
    private IExamenRepo repo;

    @Override
    protected IGenericRepo<Examen, Integer> getRepo() {
        return repo;
    }
}
