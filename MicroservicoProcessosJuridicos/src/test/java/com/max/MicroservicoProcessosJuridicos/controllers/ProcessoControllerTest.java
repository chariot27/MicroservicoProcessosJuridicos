package com.max.MicroservicoProcessosJuridicos.controllers;

import com.max.MicroservicoProcessosJuridicos.controllers.config.TestConfig;
import com.max.MicroservicoProcessosJuridicos.models.Processo;
import com.max.MicroservicoProcessosJuridicos.repositories.ProcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
public class ProcessoControllerTest {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private RestTemplate restTemplate; // Injeção do RestTemplate como um campo

    private String baseUrl;

    @BeforeEach
    void setUp() {
        processoRepository.deleteAll(); // Limpa o banco de dados antes de cada teste
        baseUrl = "http://localhost:8080/api/processos"; // A URL base para os testes
    }

    @SuppressWarnings("deprecation")
    @Test
    void testCreateProcesso() throws Exception {
        Processo processo = new Processo();
        processo.setNumeroProcesso("12345");

        ResponseEntity<Processo> response = restTemplate.postForEntity(baseUrl, processo, Processo.class);
        
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNumeroProcesso()).isEqualTo("12345");
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetAllProcessos() throws Exception {
        Processo processo1 = new Processo();
        processo1.setNumeroProcesso("12345");
        processoRepository.save(processo1);

        Processo processo2 = new Processo();
        processo2.setNumeroProcesso("67890");
        processoRepository.save(processo2);

        ResponseEntity<Processo[]> response = restTemplate.getForEntity(baseUrl, Processo[].class);
        
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()[0].getNumeroProcesso()).isEqualTo("12345");
        assertThat(response.getBody()[1].getNumeroProcesso()).isEqualTo("67890");
    }

    @Test
    void testDeleteProcesso() throws Exception {
        Processo processo = new Processo();
        processo.setNumeroProcesso("12345");
        Processo savedProcesso = processoRepository.save(processo);

        // Exclui o processo
        restTemplate.delete(baseUrl + "/" + savedProcesso.getId());

        // Verifica se o processo foi excluído
        ResponseEntity<Processo[]> response = restTemplate.getForEntity(baseUrl, Processo[].class);
        assertThat(response.getBody()).isEmpty();
    }
}