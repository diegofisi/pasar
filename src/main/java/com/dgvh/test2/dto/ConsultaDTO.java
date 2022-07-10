package com.dgvh.test2.dto;

import com.dgvh.test2.model.DetalleConsulta;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaDTO {
    private Integer idConsulta;
    @NotNull
    private PacienteDTO paciente;
    @NotNull
    private MedicoDTO medico;
    @NotNull
    private EspecialidadDTO especialidad;
    @NotNull
    private String numConsultorio;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") // ISODate yyyy-MM-dd
    @NotNull
    private LocalDateTime fecha;
    @NotNull
    private List<DetalleConsulta> detalleConsulta;

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public MedicoDTO getMedico() {
        return medico;
    }

    public void setMedico(MedicoDTO medico) {
        this.medico = medico;
    }

    public EspecialidadDTO getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadDTO especialidad) {
        this.especialidad = especialidad;
    }

    public String getNumConsultorio() {
        return numConsultorio;
    }

    public void setNumConsultorio(String numConsultorio) {
        this.numConsultorio = numConsultorio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<DetalleConsulta> getDetalleConsulta() {
        return detalleConsulta;
    }

    public void setDetalleConsulta(List<DetalleConsulta> detalleConsulta) {
        this.detalleConsulta = detalleConsulta;
    }
}
