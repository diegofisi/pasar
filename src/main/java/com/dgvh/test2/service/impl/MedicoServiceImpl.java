package com.dgvh.test2.service.impl;

import com.dgvh.test2.model.Medico;
import com.dgvh.test2.repo.IGenericRepo;
import com.dgvh.test2.repo.IMedicoRepo;
import com.dgvh.test2.service.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicoServiceImpl extends CRUDImpl<Medico, Integer> implements IMedicoService {

    @Autowired
    private IMedicoRepo repo;

    @Override
    protected IGenericRepo<Medico, Integer> getRepo() {
        return repo;
    }
}
