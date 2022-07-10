package com.dgvh.test2.service;

import com.dgvh.test2.model.Consulta;
import com.dgvh.test2.model.Examen;

import java.util.List;

public interface IConsultaService extends ICRUD<Consulta,Integer>{
    Consulta registrarTransaccional(Consulta consulta, List<Examen> examenes) throws Exception;
}
