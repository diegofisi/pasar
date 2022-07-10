package com.dgvh.test2.repo;

import com.dgvh.test2.model.Medico;
import com.dgvh.test2.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface IMedicoRepo extends IGenericRepo<Medico,Integer> {

}
