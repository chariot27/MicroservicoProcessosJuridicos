package com.max.MicroservicoProcessosJuridicos.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.max.MicroservicoProcessosJuridicos.models.Processo;
import com.max.MicroservicoProcessosJuridicos.models.Reu;
import com.max.MicroservicoProcessosJuridicos.repositories.ProcessoRepository;
import com.max.MicroservicoProcessosJuridicos.repositories.ReuRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ReuRepository reuRepository;

    public Processo saveProcesso(Processo processo) {
        return processoRepository.save(processo);
    }

    public Optional<Processo> findProcessoById(Long id) {
        return processoRepository.findById(id);
    }

    public void deleteProcesso(Long id) {
        processoRepository.deleteById(id);
    }

    @Transactional
    public Processo createProcessoWithReus(Processo processo) {
        // Salvar o processo primeiro
        Processo processoSalvo = processoRepository.save(processo);

        // Associar os réus ao processo
        for (Reu reu : processoSalvo.getReus()) {
            reu.setProcesso(processoSalvo); // Associar o processo ao réu
            reuRepository.save(reu); // Salvar o réu
        }

        return processoSalvo;
    }

    public Processo buscarProcessoPorId(Long id) {
        return processoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Processo não encontrado com ID: " + id));
    }

    @Transactional
    public void addReuToProcesso(Long processoId, Reu reu) {
        Processo processo = processoRepository.findById(processoId)
            .orElseThrow(() -> new EntityNotFoundException("Processo não encontrado"));

        // Associa o réu ao processo
        reu.setProcesso(processo);
        processo.getReus().add(reu); // Adiciona o réu à lista de réus do processo

        // Salva o processo para persistir a relação
        processoRepository.save(processo);
    }

    public boolean existsByNumeroProcesso(String numeroProcesso) {
        return processoRepository.existsByNumeroProcesso(numeroProcesso);
    }

    public Iterable<Processo> findAll() {
        return processoRepository.findAll();
    }

    public void setReuRepository(ReuRepository reuRepositoryTest) {
        this.reuRepository = reuRepositoryTest;
    }

    public void setProcessoRepository(ProcessoRepository processoRepositoryTest) {
       this.processoRepository = processoRepositoryTest;
    }


}