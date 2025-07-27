package com.pgbl.simulator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/**
 * DTO para resposta da simulação de incentivo fiscal PGBL
 */
@Schema(description = "Resultado da simulação de incentivo fiscal PGBL")
public class SimulacaoResponse {

    @Schema(description = "Base tributária atual (sem PGBL)", example = "120000.00")
    private BigDecimal baseTributariaAtual;

    @Schema(description = "Alíquota de IR aplicável", example = "22.5")
    private BigDecimal aliquotaIr;

    @Schema(description = "Valor do IR sem PGBL", example = "19000.00")
    private BigDecimal valorIrSemPgbl;

    @Schema(description = "Nova base tributária com PGBL", example = "108000.00")
    private BigDecimal novaBaseTributaria;

    @Schema(description = "Valor do IR com PGBL", example = "15400.00")
    private BigDecimal valorIrComPgbl;

    @Schema(description = "Ganho fiscal máximo possível", example = "3600.00")
    private BigDecimal ganhoFiscalMaximo;

    @Schema(description = "Ganho fiscal atual com valor investido", example = "2400.00")
    private BigDecimal ganhoFiscalAtual;

    @Schema(description = "Máximo ganho fiscal possível", example = "3600.00")
    private BigDecimal maximoGanhoFiscal;

    @Schema(description = "Valor ideal de contribuição para PGBL", example = "14400.00")
    private BigDecimal valorIdealContribuicao;

    @Schema(description = "Valor atualmente investido em PGBL", example = "8400.00")
    private BigDecimal valorInvestido;

    @Schema(description = "Quanto falta investir para atingir o ideal", example = "6000.00")
    private BigDecimal quantoFaltaInvestir;

    @Schema(description = "Economia total de IR com PGBL", example = "3600.00")
    private BigDecimal economiaTotalIr;

    // Construtores
    public SimulacaoResponse() {}

    // Getters e Setters
    public BigDecimal getBaseTributariaAtual() {
        return baseTributariaAtual;
    }

    public void setBaseTributariaAtual(BigDecimal baseTributariaAtual) {
        this.baseTributariaAtual = baseTributariaAtual;
    }

    public BigDecimal getAliquotaIr() {
        return aliquotaIr;
    }

    public void setAliquotaIr(BigDecimal aliquotaIr) {
        this.aliquotaIr = aliquotaIr;
    }

    public BigDecimal getValorIrSemPgbl() {
        return valorIrSemPgbl;
    }

    public void setValorIrSemPgbl(BigDecimal valorIrSemPgbl) {
        this.valorIrSemPgbl = valorIrSemPgbl;
    }

    public BigDecimal getNovaBaseTributaria() {
        return novaBaseTributaria;
    }

    public void setNovaBaseTributaria(BigDecimal novaBaseTributaria) {
        this.novaBaseTributaria = novaBaseTributaria;
    }

    public BigDecimal getValorIrComPgbl() {
        return valorIrComPgbl;
    }

    public void setValorIrComPgbl(BigDecimal valorIrComPgbl) {
        this.valorIrComPgbl = valorIrComPgbl;
    }

    public BigDecimal getGanhoFiscalMaximo() {
        return ganhoFiscalMaximo;
    }

    public void setGanhoFiscalMaximo(BigDecimal ganhoFiscalMaximo) {
        this.ganhoFiscalMaximo = ganhoFiscalMaximo;
    }

    public BigDecimal getGanhoFiscalAtual() {
        return ganhoFiscalAtual;
    }

    public void setGanhoFiscalAtual(BigDecimal ganhoFiscalAtual) {
        this.ganhoFiscalAtual = ganhoFiscalAtual;
    }

    public BigDecimal getMaximoGanhoFiscal() {
        return maximoGanhoFiscal;
    }

    public void setMaximoGanhoFiscal(BigDecimal maximoGanhoFiscal) {
        this.maximoGanhoFiscal = maximoGanhoFiscal;
    }

    public BigDecimal getValorIdealContribuicao() {
        return valorIdealContribuicao;
    }

    public void setValorIdealContribuicao(BigDecimal valorIdealContribuicao) {
        this.valorIdealContribuicao = valorIdealContribuicao;
    }

    public BigDecimal getValorInvestido() {
        return valorInvestido;
    }

    public void setValorInvestido(BigDecimal valorInvestido) {
        this.valorInvestido = valorInvestido;
    }

    public BigDecimal getQuantoFaltaInvestir() {
        return quantoFaltaInvestir;
    }

    public void setQuantoFaltaInvestir(BigDecimal quantoFaltaInvestir) {
        this.quantoFaltaInvestir = quantoFaltaInvestir;
    }

    public BigDecimal getEconomiaTotalIr() {
        return economiaTotalIr;
    }

    public void setEconomiaTotalIr(BigDecimal economiaTotalIr) {
        this.economiaTotalIr = economiaTotalIr;
    }

    @Override
    public String toString() {
        return "SimulacaoResponse{" +
                "ganhoFiscalMaximo=" + ganhoFiscalMaximo +
                ", novaBaseTributaria=" + novaBaseTributaria +
                ", valorIrComPgbl=" + valorIrComPgbl +
                ", baseTributariaAtual=" + baseTributariaAtual +
                ", ganhoFiscalAtual=" + ganhoFiscalAtual +
                ", maximoGanhoFiscal=" + maximoGanhoFiscal +
                ", valorIdealContribuicao=" + valorIdealContribuicao +
                ", quantoFaltaInvestir=" + quantoFaltaInvestir +
                ", valorInvestido=" + valorInvestido +
                ", aliquotaIr=" + aliquotaIr +
                ", valorIrSemPgbl=" + valorIrSemPgbl +
                ", economiaTotalIr=" + economiaTotalIr +
                '}';
    }
}

