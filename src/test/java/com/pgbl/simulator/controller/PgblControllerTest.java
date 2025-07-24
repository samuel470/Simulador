package com.pgbl.simulator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgbl.simulator.dto.SimulacaoRequest;
import com.pgbl.simulator.model.TipoContribuicao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class PgblControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/pgbl/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("API PGBL Simulator está funcionando!"));
    }

    @Test
    void testInfoEndpoint() throws Exception {
        mockMvc.perform(get("/pgbl/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("PGBL Simulator API"))
                .andExpect(jsonPath("$.versao").value("1.0.0"));
    }

    @Test
    void testSimularComDadosValidos() throws Exception {
        SimulacaoRequest request = new SimulacaoRequest();
        request.setTipoContribuicao(TipoContribuicao.ANUAL);
        request.setRendaBruta(new BigDecimal("120000.00"));
        request.setTemPrevidenciaOutraInstituicao(true);
        request.setValorContribuicaoInss(new BigDecimal("1200.00"));
        request.setQuantidadeDependentes(2);

        mockMvc.perform(post("/pgbl/simular")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.baseTributariaAtual").exists())
                .andExpect(jsonPath("$.novaBaseTributaria").exists())
                .andExpect(jsonPath("$.ganhoFiscalMaximo").exists())
                .andExpect(jsonPath("$.valorIdealContribuicao").exists());
    }

    @Test
    void testSimularSemPrevidencia() throws Exception {
        SimulacaoRequest request = new SimulacaoRequest();
        request.setTipoContribuicao(TipoContribuicao.ANUAL);
        request.setRendaBruta(new BigDecimal("120000.00"));
        request.setTemPrevidenciaOutraInstituicao(false);

        mockMvc.perform(post("/pgbl/simular")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ganhoFiscalMaximo").value(0))
                .andExpect(jsonPath("$.valorIdealContribuicao").value(0));
    }

    @Test
    void testSimularComDadosInvalidos() throws Exception {
        SimulacaoRequest request = new SimulacaoRequest();
        // Não define campos obrigatórios

        mockMvc.perform(post("/pgbl/simular")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Dados de entrada inválidos"));
    }

    @Test
    void testSimularComRendaNegativa() throws Exception {
        SimulacaoRequest request = new SimulacaoRequest();
        request.setTipoContribuicao(TipoContribuicao.ANUAL);
        request.setRendaBruta(new BigDecimal("-1000.00"));
        request.setTemPrevidenciaOutraInstituicao(true);

        mockMvc.perform(post("/pgbl/simular")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSimularMensal() throws Exception {
        SimulacaoRequest request = new SimulacaoRequest();
        request.setTipoContribuicao(TipoContribuicao.MENSAL);
        request.setRendaBruta(new BigDecimal("10000.00"));
        request.setTemPrevidenciaOutraInstituicao(true);
        request.setValorContribuicaoInss(new BigDecimal("100.00"));

        mockMvc.perform(post("/pgbl/simular")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.baseTributariaAtual").exists())
                .andExpect(jsonPath("$.valorIdealContribuicao").exists());
    }
}

