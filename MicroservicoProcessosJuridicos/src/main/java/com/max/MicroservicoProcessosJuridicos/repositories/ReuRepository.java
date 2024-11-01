package com.max.MicroservicoProcessosJuridicos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.max.MicroservicoProcessosJuridicos.models.Reu;

@Repository
public interface ReuRepository extends JpaRepository<Reu, Long> {
    boolean existsByNome(String nome);
}