package com.max.MicroservicoProcessosJuridicos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.max.MicroservicoProcessosJuridicos.models.Processo;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    boolean existsByNumeroProcesso(String numeroProcesso);
}
