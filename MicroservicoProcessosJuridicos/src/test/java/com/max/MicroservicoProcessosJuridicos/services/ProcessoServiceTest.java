package com.max.MicroservicoProcessosJuridicos.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.max.MicroservicoProcessosJuridicos.models.Processo;
import com.max.MicroservicoProcessosJuridicos.models.Reu;
import com.max.MicroservicoProcessosJuridicos.repositories.ProcessoRepository;
import com.max.MicroservicoProcessosJuridicos.repositories.ReuRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest // Carrega o contexto do Spring
@Transactional // Garante que cada teste seja executado em uma transação, que será revertida após o teste
public class ProcessoServiceTest {

    @Autowired
    private ProcessoService processoService; // Injetar o serviço que estamos testando

    @Autowired
    private ProcessoRepository processoRepository; // Injetar o repositório de Processo

    @Autowired
    private ReuRepository reuRepository; // Injetar o repositório de Réu

    @Test
    public void testAddReuToProcesso() {
        // Cria e salva um novo Processo
        Processo processo = new Processo();
        processo.setNumeroProcesso("12345");
        processo = processoRepository.save(processo);

        // Cria e salva um novo Reu
        Reu reu = new Reu();
        reu.setNome("John Doe");
        reu.setProcesso(processo); // Associar o processo ao réu
        reuRepository.save(reu); // Salva o réu

        // Adiciona o Reu ao Processo
        processoService.addReuToProcesso(processo.getId(), reu);

        // Busca o Processo atualizado
        Processo foundProcesso = processoRepository.findById(processo.getId()).orElse(null);
        assertThat(foundProcesso).isNotNull();
        assertThat(foundProcesso.getReus()).hasSize(1); // Verifica se há um réu
        assertThat(foundProcesso.getReus()).contains(reu); // Verifica se o réu está presente
    }

    @Test
    void testBuscarProcessoPorId() {
        Processo processo = new Processo();
        processo.setNumeroProcesso("12345");
        processo = processoRepository.save(processo);

        Processo foundProcesso = processoService.buscarProcessoPorId(processo.getId());

        assertThat(foundProcesso).isNotNull();
        assertThat(foundProcesso.getId()).isEqualTo(processo.getId());
    }

    @Test
    public void testCreateProcessoWithReus() {
        // Cria um novo processo
        Processo processo = new Processo();
        processo.setNumeroProcesso("12345");

        // Cria um novo réu e associa ao processo
        Reu reu = new Reu();
        reu.setNome("John Doe");
        reu.setProcesso(processo);

        // Adiciona o réu ao processo
        processo.setReus(new HashSet<>());
        processo.getReus().add(reu);

        // Chama o método a ser testado
        Processo createdProcesso = processoService.createProcessoWithReus(processo);

        // Verifica se o processo foi criado e se o réu foi associado corretamente
        assertThat(createdProcesso).isNotNull();
        assertThat(createdProcesso.getReus()).hasSize(1);
        assertThat(createdProcesso.getReus()).contains(reu);
        
        // Verifica se o réu foi salvo no repositório
        Reu foundReu = reuRepository.findById(reu.getId()).orElse(null);
        assertThat(foundReu).isNotNull();
        assertThat(foundReu.getNome()).isEqualTo("John Doe");
        assertThat(foundReu.getProcesso()).isEqualTo(createdProcesso);
    }

    @Test
    void testDeleteProcesso() {
        Processo processo = new Processo();
        processo.setNumeroProcesso("12345");
        processo = processoRepository.save(processo);

        Long identificador = processo.getId();
        processoService.deleteProcesso(identificador);

        assertThrows(EntityNotFoundException.class, () -> {
            processoService.buscarProcessoPorId(identificador);
        });
    }

    @Test
    void testExistsByNumeroProcesso() {
        Processo processo = new Processo();
        processo.setNumeroProcesso("12345");
        processoRepository.save(processo);

        boolean exists = processoService.existsByNumeroProcesso("12345");

        assertThat(exists).isTrue();
    }

    @Test
    void testFindAll() {
        Processo processo1 = new Processo();
        processo1.setNumeroProcesso("12345");
        processoRepository.save(processo1);

        Processo processo2 = new Processo();
        processo2.setNumeroProcesso("67890");
        processoRepository.save(processo2);

        List<Processo> processos = (List<Processo>) processoService.findAll();

        assertThat(processos).hasSize(2);
        assertThat(processos).contains(processo1, processo2);
    }

    @Test
    void testFindProcessoById() {
        Processo processo = new Processo();
        processo.setNumeroProcesso("12345");
        processo = processoRepository.save(processo);

        Optional<Processo> foundProcesso = processoService.findProcessoById(processo.getId());

        assertThat(foundProcesso).isPresent();
        assertThat(foundProcesso.get().getId()).isEqualTo(processo.getId());
    }

    @Test
    void testSaveProcesso() {
        Processo processo = new Processo();
        processo.setNumeroProcesso("12345");

        Processo savedProcesso = processoService.saveProcesso(processo);

        assertThat(savedProcesso).isNotNull();
        assertThat(savedProcesso.getId()).isNotNull();
        assertThat(savedProcesso.getNumeroProcesso()).isEqualTo("12345");
    }

}