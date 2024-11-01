package com.max.MicroservicoProcessosJuridicos.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import com.max.MicroservicoProcessosJuridicos.models.Reu;
import com.max.MicroservicoProcessosJuridicos.models.Processo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReuRepositoryTest {

    @Autowired
    private ReuRepository reuRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    @BeforeEach
    public void setUp() {
        reuRepository.deleteAll(); // Limpa a tabela de Reus antes de cada teste
        processoRepository.deleteAll(); // Limpa a tabela de Processos antes de cada teste
    }

    @Test
    @Rollback(false)
    public void testSaveAndFindReu() {
        // Criação de um novo objeto Processo
        Processo processo = new Processo();
        processo.setNumeroProcesso("123452023");
        processoRepository.save(processo); // Salva o objeto Processo no banco de dados

        // Criação de um novo objeto Reu
        Reu reu = new Reu();
        reu.setNome("João da Silva");
        reu.setProcesso(processo); // Associa o Reu ao Processo
        reuRepository.save(reu); // Salva o objeto Reu no banco de dados

        // Verifica se o Reu foi salvo corretamente
        Reu foundReu = reuRepository.findById(reu.getId()).orElse(null);
        assertThat(foundReu).isNotNull();
        assertThat(foundReu.getNome()).isEqualTo("João da Silva");
        assertThat(foundReu.getProcesso()).isEqualTo(processo);

        // Mensagem de sucesso
        assertThat(true).as("Teste testSaveAndFindReu passou com sucesso!").isTrue();
    }

    @Test
    public void testExistsByNome() {
        // Criação de um novo objeto Processo
        Processo processo = new Processo();
        processo.setNumeroProcesso("543212023");
        processoRepository.save(processo); // Salva o objeto Processo no banco de dados

        // Criação de um novo objeto Reu
        Reu reu = new Reu();
        reu.setNome("Maria Oliveira");
        reu.setProcesso(processo); // Associa o Reu ao Processo
        reuRepository.save(reu); // Salva o objeto Reu no banco de dados

        // Verifica se o Reu existe pelo nome
        boolean exists = reuRepository.existsByNome("Maria Oliveira");
        assertThat(exists).isTrue();

        boolean notExists = reuRepository.existsByNome("Carlos Pereira");
        assertThat(notExists).isFalse();

        // Mensagem de sucesso
        assertThat(true).as("Teste testExistsByNome passou com sucesso!").isTrue();
    }
}