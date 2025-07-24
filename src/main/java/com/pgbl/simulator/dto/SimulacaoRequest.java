package com.pgbl.simulator.dto;

import com.pgbl.simulator.model.TipoContribuicao;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO para requisição de simulação de incentivo fiscal PGBL
 */
@Schema(description = "Dados de entrada para simulação de incentivo fiscal PGBL")
public class SimulacaoRequest {

    @NotNull(message = "Tipo de contribuição é obrigatório")
    @Schema(description = "Tipo de contribuição (MENSAL ou ANUAL)", example = "ANUAL", required = true)
    private TipoContribuicao tipoContribuicao;

    @NotNull(message = "Renda bruta é obrigatória")
    @DecimalMin(value = "0.0", inclusive = false, message = "Renda bruta deve ser maior que zero")
    @Schema(description = "Renda bruta anual ou mensal", example = "120000.00", required = true)
    private BigDecimal rendaBruta;

    @NotNull(message = "Campo 'tem previdência em outra instituição' é obrigatório")
    @Schema(description = "Indica se possui previdência em outra instituição", example = "true", required = true)
    private Boolean temPrevidenciaOutraInstituicao;

    @DecimalMin(value = "0.0", message = "Valor da contribuição INSS não pode ser negativo")
    @Schema(description = "Valor da contribuição para o INSS", example = "1200.00")
    private BigDecimal valorContribuicaoInss = BigDecimal.ZERO;

    @DecimalMin(value = "0.0", message = "Valor investido em educação não pode ser negativo")
    @Schema(description = "Valor investido em educação do titular", example = "3000.00")
    private BigDecimal valorInvestidoEducacao = BigDecimal.ZERO;

    @DecimalMin(value = "0.0", message = "Valor de despesas médicas não pode ser negativo")
    @Schema(description = "Valor das despesas médicas do titular", example = "5000.00")
    private BigDecimal valorDespesasMedicas = BigDecimal.ZERO;

    @DecimalMin(value = "0.0", message = "Valor de pensão alimentícia não pode ser negativo")
    @Schema(description = "Valor da pensão alimentícia", example = "2400.00")
    private BigDecimal valorPensaoAlimenticia = BigDecimal.ZERO;

    @Min(value = 0, message = "Quantidade de dependentes não pode ser negativa")
    @Max(value = 20, message = "Quantidade de dependentes não pode ser maior que 20")
    @Schema(description = "Quantidade de dependentes", example = "2")
    private Integer quantidadeDependentes = 0;

    @DecimalMin(value = "0.0", message = "Valor investido em educação dos dependentes não pode ser negativo")
    @Schema(description = "Valor total investido em educação dos dependentes", example = "6000.00")
    private BigDecimal valorInvestidoEducacaoDependentes = BigDecimal.ZERO;

    @DecimalMin(value = "0.0", message = "Valor de despesas médicas dos dependentes não pode ser negativo")
    @Schema(description = "Valor total das despesas médicas dos dependentes", example = "3000.00")
    private BigDecimal valorDespesasMedicasDependentes = BigDecimal.ZERO;

    // Construtores
    public SimulacaoRequest() {}

    public SimulacaoRequest(TipoContribuicao tipoContribuicao, BigDecimal rendaBruta, 
                           Boolean temPrevidenciaOutraInstituicao) {
        this.tipoContribuicao = tipoContribuicao;
        this.rendaBruta = rendaBruta;
        this.temPrevidenciaOutraInstituicao = temPrevidenciaOutraInstituicao;
    }

    // Getters e Setters
    public TipoContribuicao getTipoContribuicao() {
        return tipoContribuicao;
    }

    public void setTipoContribuicao(TipoContribuicao tipoContribuicao) {
        this.tipoContribuicao = tipoContribuicao;
    }

    public BigDecimal getRendaBruta() {
        return rendaBruta;
    }

    public void setRendaBruta(BigDecimal rendaBruta) {
        this.rendaBruta = rendaBruta;
    }

    public Boolean getTemPrevidenciaOutraInstituicao() {
        return temPrevidenciaOutraInstituicao;
    }

    public void setTemPrevidenciaOutraInstituicao(Boolean temPrevidenciaOutraInstituicao) {
        this.temPrevidenciaOutraInstituicao = temPrevidenciaOutraInstituicao;
    }

    public BigDecimal getValorContribuicaoInss() {
        return valorContribuicaoInss;
    }

    public void setValorContribuicaoInss(BigDecimal valorContribuicaoInss) {
        this.valorContribuicaoInss = valorContribuicaoInss != null ? valorContribuicaoInss : BigDecimal.ZERO;
    }

    public BigDecimal getValorInvestidoEducacao() {
        return valorInvestidoEducacao;
    }

    public void setValorInvestidoEducacao(BigDecimal valorInvestidoEducacao) {
        this.valorInvestidoEducacao = valorInvestidoEducacao != null ? valorInvestidoEducacao : BigDecimal.ZERO;
    }

    public BigDecimal getValorDespesasMedicas() {
        return valorDespesasMedicas;
    }

    public void setValorDespesasMedicas(BigDecimal valorDespesasMedicas) {
        this.valorDespesasMedicas = valorDespesasMedicas != null ? valorDespesasMedicas : BigDecimal.ZERO;
    }

    public BigDecimal getValorPensaoAlimenticia() {
        return valorPensaoAlimenticia;
    }

    public void setValorPensaoAlimenticia(BigDecimal valorPensaoAlimenticia) {
        this.valorPensaoAlimenticia = valorPensaoAlimenticia != null ? valorPensaoAlimenticia : BigDecimal.ZERO;
    }

    public Integer getQuantidadeDependentes() {
        return quantidadeDependentes;
    }

    public void setQuantidadeDependentes(Integer quantidadeDependentes) {
        this.quantidadeDependentes = quantidadeDependentes != null ? quantidadeDependentes : 0;
    }

    public BigDecimal getValorInvestidoEducacaoDependentes() {
        return valorInvestidoEducacaoDependentes;
    }

    public void setValorInvestidoEducacaoDependentes(BigDecimal valorInvestidoEducacaoDependentes) {
        this.valorInvestidoEducacaoDependentes = valorInvestidoEducacaoDependentes != null ? 
            valorInvestidoEducacaoDependentes : BigDecimal.ZERO;
    }

    public BigDecimal getValorDespesasMedicasDependentes() {
        return valorDespesasMedicasDependentes;
    }

    public void setValorDespesasMedicasDependentes(BigDecimal valorDespesasMedicasDependentes) {
        this.valorDespesasMedicasDependentes = valorDespesasMedicasDependentes != null ? 
            valorDespesasMedicasDependentes : BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "SimulacaoRequest{" +
                "tipoContribuicao=" + tipoContribuicao +
                ", rendaBruta=" + rendaBruta +
                ", temPrevidenciaOutraInstituicao=" + temPrevidenciaOutraInstituicao +
                ", valorContribuicaoInss=" + valorContribuicaoInss +
                ", valorInvestidoEducacao=" + valorInvestidoEducacao +
                ", valorDespesasMedicas=" + valorDespesasMedicas +
                ", valorPensaoAlimenticia=" + valorPensaoAlimenticia +
                ", quantidadeDependentes=" + quantidadeDependentes +
                ", valorInvestidoEducacaoDependentes=" + valorInvestidoEducacaoDependentes +
                ", valorDespesasMedicasDependentes=" + valorDespesasMedicasDependentes +
                '}';
    }
}

