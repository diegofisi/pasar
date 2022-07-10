package com.dgvh.test2.service.impl;

import com.dgvh.test2.model.Especialidad;
import com.dgvh.test2.repo.IEspecialidadRepo;
import com.dgvh.test2.repo.IGenericRepo;
import com.dgvh.test2.service.IEspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspecialidadServiceImpl extends CRUDImpl<Especialidad, Integer> implements IEspecialidadService {

    @Autowired
    private IEspecialidadRepo repo;

    @Override
    protected IGenericRepo<Especialidad, Integer> getRepo() {
        return repo;
    }
}
