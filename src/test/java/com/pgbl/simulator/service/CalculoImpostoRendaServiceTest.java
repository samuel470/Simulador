package com.pgbl.simulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculoImpostoRendaServiceTest {

    private CalculoImpostoRendaService calculoIrService;

    @BeforeEach
    void setUp() {
        calculoIrService = new CalculoImpostoRendaService();
    }

    @Test
    void testCalcularImpostoRendaAnual_FaixaIsenta() {
        BigDecimal rendaTributavel = new BigDecimal("25000.00");
        BigDecimal ir = calculoIrService.calcularImpostoRenda(rendaTributavel, true);
        assertEquals(BigDecimal.ZERO.setScale(2), ir);
    }

    @Test
    void testCalcularImpostoRendaAnual_SegundaFaixa() {
        BigDecimal rendaTributavel = new BigDecimal("30000.00");
        BigDecimal ir = calculoIrService.calcularImpostoRenda(rendaTributavel, true);
        assertTrue(ir.compareTo(BigDecimal.ZERO) > 0);
        assertTrue(ir.compareTo(new BigDecimal("500.00")) < 0);
    }

    @Test
    void testCalcularImpostoRendaAnual_QuintaFaixa() {
        BigDecimal rendaTributavel = new BigDecimal("100000.00");
        BigDecimal ir = calculoIrService.calcularImpostoRenda(rendaTributavel, true);
        assertTrue(ir.compareTo(new BigDecimal("15000.00")) > 0);
    }

    @Test
    void testCalcularLimitePgbl() {
        BigDecimal rendaBruta = new BigDecimal("120000.00");
        BigDecimal limite = calculoIrService.calcularLimitePgbl(rendaBruta);
        assertEquals(new BigDecimal("14400.00"), limite);
    }

    @Test
    void testCalcularDeducoes_SemDependentes() {
        BigDecimal deducoes = calculoIrService.calcularDeducoes(
            new BigDecimal("1200.00"), // INSS
            new BigDecimal("3000.00"), // Educação
            new BigDecimal("2000.00"), // Despesas médicas
            new BigDecimal("1000.00"), // Pensão
            0, // Dependentes
            BigDecimal.ZERO, // Educação dependentes
            BigDecimal.ZERO, // Despesas médicas dependentes
            true // Anual
        );
        
        assertTrue(deducoes.compareTo(new BigDecimal("7000.00")) > 0);
    }

    @Test
    void testCalcularDeducoes_ComDependentes() {
        BigDecimal deducoes = calculoIrService.calcularDeducoes(
            new BigDecimal("1200.00"), // INSS
            new BigDecimal("3000.00"), // Educação
            new BigDecimal("2000.00"), // Despesas médicas
            new BigDecimal("1000.00"), // Pensão
            2, // Dependentes
            new BigDecimal("5000.00"), // Educação dependentes
            new BigDecimal("1500.00"), // Despesas médicas dependentes
            true // Anual
        );
        
        // Deve incluir valor dos dependentes (2 * 2275.08 = 4550.16)
        assertTrue(deducoes.compareTo(new BigDecimal("12000.00")) > 0);
    }

    @Test
    void testCalcularAliquotaMarginal() {
        BigDecimal rendaTributavel = new BigDecimal("50000.00");
        BigDecimal aliquota = calculoIrService.calcularAliquotaMarginal(rendaTributavel, true);
        assertEquals(new BigDecimal("22.5"), aliquota);
    }

    @Test
    void testCalcularGanhoFiscal() {
        BigDecimal valorPgbl = new BigDecimal("10000.00");
        BigDecimal aliquotaMarginal = new BigDecimal("22.5");
        BigDecimal ganho = calculoIrService.calcularGanhoFiscal(valorPgbl, aliquotaMarginal);
        assertEquals(new BigDecimal("2250.00"), ganho);
    }

    @Test
    void testCalcularBaseTributavel() {
        BigDecimal rendaBruta = new BigDecimal("120000.00");
        BigDecimal deducoes = new BigDecimal("20000.00");
        BigDecimal baseTributavel = calculoIrService.calcularBaseTributavel(rendaBruta, deducoes);
        assertEquals(new BigDecimal("100000.00"), baseTributavel);
    }

    @Test
    void testCalcularBaseTributavel_NaoNegativa() {
        BigDecimal rendaBruta = new BigDecimal("50000.00");
        BigDecimal deducoes = new BigDecimal("60000.00");
        BigDecimal baseTributavel = calculoIrService.calcularBaseTributavel(rendaBruta, deducoes);
        assertEquals(BigDecimal.ZERO.setScale(2), baseTributavel);
    }
}

