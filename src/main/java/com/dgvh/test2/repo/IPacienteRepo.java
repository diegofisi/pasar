package com.dgvh.test2.repo;

import com.dgvh.test2.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository -> no es necesario ya que al momento de hererar de JpaRespository, se asume que tienes como intencion
// tener una instancia de esa interfaz, asi q por eso no es necesario, por q ya es automatico.
public interface IPacienteRepo extends IGenericRepo<Paciente, Integer> {
}
