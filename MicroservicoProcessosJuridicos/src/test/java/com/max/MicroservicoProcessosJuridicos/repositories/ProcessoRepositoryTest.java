package com.max.MicroservicoProcessosJuridicos.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import com.max.MicroservicoProcessosJuridicos.models.Processo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProcessoRepositoryTest {

    @Autowired
    private ProcessoRepository processoRepository;

    @BeforeEach
    public void setUp() {
        processoRepository.deleteAll();
    }

    @Test
    @Rollback(false)
    public void testExistsByNumeroProcesso() {
        Processo processo = new Processo();
        processo.setNumeroProcesso("123452023");
        processoRepository.save(processo);

        boolean exists = processoRepository.existsByNumeroProcesso("123452023");
        assertThat(exists).as("Verificando se o processo existe").isTrue();

        boolean notExists = processoRepository.existsByNumeroProcesso("543212023");
        assertThat(notExists).as("Verificando se o processo não existe").isFalse();

        // Mensagem de sucesso
        assertThat(true).as("Teste testExistsByNumeroProcesso passou com sucesso!").isTrue();
    }
}