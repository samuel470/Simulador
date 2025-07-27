package com.pgbl.simulator.service;

import com.pgbl.simulator.dto.SimulacaoRequest;
import com.pgbl.simulator.dto.SimulacaoResponse;
import com.pgbl.simulator.model.TipoContribuicao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service principal para simulação de incentivo fiscal PGBL
 */
@Service
public class PgblSimuladorService {

    @Autowired
    private CalculoImpostoRendaService calculoIrService;

    /**
     * Realiza a simulação completa de incentivo fiscal PGBL
     */
    public SimulacaoResponse simular(SimulacaoRequest request) {


        boolean isAnual = request.getTipoContribuicao() == TipoContribuicao.ANUAL;
        BigDecimal rendaBruta = request.getRendaBruta();

        // Converte valores para base anual se necessário
        if (!isAnual) {
            rendaBruta = rendaBruta.multiply(new BigDecimal("12"));
        }
        if(request.getValorContribuicaoInss().compareTo(BigDecimal.ZERO) == 0 ){


        }

        // Calcula deduções totais (exceto PGBL)
        BigDecimal totalDeducoes = calculoIrService.calcularDeducoes(
            ajustarParaAnual(request.getValorContribuicaoInss(), isAnual),
            ajustarParaAnual(request.getValorInvestidoEducacao(), isAnual),
            ajustarParaAnual(request.getValorDespesasMedicas(), isAnual),
            ajustarParaAnual(request.getValorPensaoAlimenticia(), isAnual),
            request.getQuantidadeDependentes(),
            ajustarParaAnual(request.getValorInvestidoEducacaoDependentes(), isAnual),
            ajustarParaAnual(request.getValorDespesasMedicasDependentes(), isAnual),
            true // Sempre calcular em base anual
        );

        // Base tributável atual (sem PGBL)
        BigDecimal baseTributariaAtual = calculoIrService.calcularBaseTributavel(rendaBruta, totalDeducoes);

        // IR atual (sem PGBL)
        BigDecimal irSemPgbl = calculoIrService.calcularImpostoRenda(baseTributariaAtual, true);

        // Limite máximo PGBL (12% da renda bruta)
        BigDecimal limitePgbl = calculoIrService.calcularLimitePgbl(rendaBruta);

        // Valor ideal de contribuição PGBL
        BigDecimal valorIdealContribuicao = calculoIrService.calcularValorIdealPgbl(
            rendaBruta, baseTributariaAtual, true);

        // Nova base tributária com valor ideal
        BigDecimal novaBaseTributaria = baseTributariaAtual.subtract(valorIdealContribuicao);
        novaBaseTributaria = novaBaseTributaria.max(BigDecimal.ZERO);

        // IR com PGBL (valor ideal)
        BigDecimal irComPgbl = calculoIrService.calcularImpostoRenda(novaBaseTributaria, true);

        // Alíquota marginal
        BigDecimal aliquotaMarginal = calculoIrService.calcularAliquotaMarginal(baseTributariaAtual, true);

        // Ganho fiscal máximo
        BigDecimal ganhoFiscalMaximo = irSemPgbl.subtract(irComPgbl);

        // Máximo ganho fiscal possível (com limite de 12%)
        BigDecimal maximoGanhoFiscal = calculoIrService.calcularGanhoFiscal(limitePgbl, aliquotaMarginal);

        // Valor atualmente investido (assumindo zero se não informado)
        BigDecimal valorInvestido = BigDecimal.valueOf(5000);

        // Ganho fiscal atual
        BigDecimal ganhoFiscalAtual = calculoIrService
                .calcularGanhoFiscal(valorInvestido, aliquotaMarginal)
                .min(maximoGanhoFiscal);

        // Quanto falta investir
        BigDecimal quantoFaltaInvestir = valorIdealContribuicao.subtract(valorInvestido);
        quantoFaltaInvestir = quantoFaltaInvestir.max(BigDecimal.ZERO);

        // Economia total de IR
        BigDecimal economiaTotalIr = ganhoFiscalMaximo;

        // Ajusta valores para retorno conforme tipo de contribuição
        if (!isAnual) {
            baseTributariaAtual = ajustarParaMensal(baseTributariaAtual);
            novaBaseTributaria = ajustarParaMensal(novaBaseTributaria);
            irSemPgbl = ajustarParaMensal(irSemPgbl);
            irComPgbl = ajustarParaMensal(irComPgbl);
            ganhoFiscalMaximo = ajustarParaMensal(ganhoFiscalMaximo);
            maximoGanhoFiscal = ajustarParaMensal(maximoGanhoFiscal);
            valorIdealContribuicao = ajustarParaMensal(valorIdealContribuicao);
            quantoFaltaInvestir = ajustarParaMensal(quantoFaltaInvestir);
            economiaTotalIr = ajustarParaMensal(economiaTotalIr);
        }

        // Monta resposta
        SimulacaoResponse response = new SimulacaoResponse();
        response.setGanhoFiscalMaximo(ganhoFiscalMaximo);
        response.setNovaBaseTributaria(novaBaseTributaria);
        response.setValorIrComPgbl(irComPgbl);
        response.setBaseTributariaAtual(baseTributariaAtual);
        response.setGanhoFiscalAtual(ganhoFiscalAtual);
        response.setMaximoGanhoFiscal(maximoGanhoFiscal);
        response.setValorIdealContribuicao(valorIdealContribuicao);
        response.setQuantoFaltaInvestir(quantoFaltaInvestir);
        response.setValorInvestido(valorInvestido);
        response.setAliquotaIr(aliquotaMarginal);
        response.setValorIrSemPgbl(irSemPgbl);
        response.setEconomiaTotalIr(economiaTotalIr);

        return response;
    }


    /**
     * Ajusta valor para base anual se for mensal
     */
    private BigDecimal ajustarParaAnual(BigDecimal valor, boolean isAnual) {
        if (isAnual || valor == null) {
            return valor != null ? valor : BigDecimal.ZERO;
        }
        return valor.multiply(new BigDecimal("12"));
    }

    /**
     * Ajusta valor para base mensal
     */
    private BigDecimal ajustarParaMensal(BigDecimal valor) {
        if (valor == null) {
            return BigDecimal.ZERO;
        }
        return valor.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
    }
}

