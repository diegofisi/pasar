package com.dgvh.test2.service.impl;

import com.dgvh.test2.model.Consulta;
import com.dgvh.test2.model.Examen;
import com.dgvh.test2.repo.IConsultaExamenRepo;
import com.dgvh.test2.repo.IConsultaRepo;
import com.dgvh.test2.repo.IGenericRepo;
import com.dgvh.test2.service.IConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ConsultaServiceImpl extends CRUDImpl<Consulta, Integer> implements IConsultaService {

    @Autowired
    private IConsultaRepo repo;

    @Autowired
    private IConsultaExamenRepo ceRepo;

    @Override
    protected IGenericRepo<Consulta, Integer> getRepo() {
        return repo;
    }

    @Transactional
    @Override
    public Consulta registrarTransaccional(Consulta consulta, List<Examen> examenes) throws Exception {
        consulta.getDetalleConsulta().forEach(det -> det.setConsulta(consulta));

		/*for(DetalleConsulta det : consulta.getDetalleConsulta()) {
			det.setConsulta(consulta);
		}*/

        repo.save(consulta);

        examenes.forEach(ex -> ceRepo.registrar(consulta.getIdConsulta(), ex.getIdExamen()));

        return consulta;
    }
}
