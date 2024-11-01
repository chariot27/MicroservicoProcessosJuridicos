package com.max.MicroservicoProcessosJuridicos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.max.MicroservicoProcessosJuridicos.models.Processo;
import com.max.MicroservicoProcessosJuridicos.models.Reu;
import com.max.MicroservicoProcessosJuridicos.services.ProcessoService;


@RestController
@RequestMapping("/api/processos")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;

    @PostMapping
    public ResponseEntity<?> createProcesso(@RequestBody Processo processo) {
        if (processoService.existsByNumeroProcesso(processo.getNumeroProcesso())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Número de processo já cadastrado.");
        }

        // Associar o processo aos réus
        for (Reu reu : processo.getReus()) {
            reu.setProcesso(processo); // Associar o processo ao réu
        }

        Processo savedProcesso = processoService.createProcessoWithReus(processo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProcesso);
    }

    @GetMapping
    public ResponseEntity<Iterable<Processo>> getAllProcessos() {
        Iterable<Processo> processos = processoService.findAll();
        return ResponseEntity.ok(processos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcesso(@PathVariable Long id) {
        processoService.deleteProcesso(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{processoId}/reus")
    public ResponseEntity<?> addReu(@PathVariable Long processoId, @RequestBody Reu reu) {
        try {
            processoService.addReuToProcesso(processoId, reu);
            return ResponseEntity.status(HttpStatus.CREATED).body(reu);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Processo não encontrado.");
        }
    }

    
}