package com.dgvh.test2.service.impl;

import com.dgvh.test2.model.Paciente;
import com.dgvh.test2.repo.IGenericRepo;
import com.dgvh.test2.repo.IPacienteRepo;
import com.dgvh.test2.service.ICRUD;
import com.dgvh.test2.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl extends CRUDImpl<Paciente,Integer> implements IPacienteService {

    @Autowired
    private IPacienteRepo repo;

    @Override
    protected IGenericRepo<Paciente, Integer> getRepo() {
        return repo;
    }
}
