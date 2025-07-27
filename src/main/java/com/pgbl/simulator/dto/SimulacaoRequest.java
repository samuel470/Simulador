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

    @DecimalMin(value = "0.0", message = "Valor investido em PGBL em outras instituições não pode ser negativo")
    @Schema(description = "Valor investido em PGBL em outra instituicao", example = "0")
    private BigDecimal valorInvestidoPgblOutraInstituicao = BigDecimal.ZERO;

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

    public @NotNull(message = "Tipo de contribuição é obrigatório") TipoContribuicao getTipoContribuicao() {
        return tipoContribuicao;
    }

    public void setTipoContribuicao(@NotNull(message = "Tipo de contribuição é obrigatório") TipoContribuicao tipoContribuicao) {
        this.tipoContribuicao = tipoContribuicao;
    }

    public @NotNull(message = "Renda bruta é obrigatória") @DecimalMin(value = "0.0", inclusive = false, message = "Renda bruta deve ser maior que zero") BigDecimal getRendaBruta() {
        return rendaBruta;
    }

    public void setRendaBruta(@NotNull(message = "Renda bruta é obrigatória") @DecimalMin(value = "0.0", inclusive = false, message = "Renda bruta deve ser maior que zero") BigDecimal rendaBruta) {
        this.rendaBruta = rendaBruta;
    }

    public @DecimalMin(value = "0.0", message = "Valor investido em PGBL em outras instituições não pode ser negativo") BigDecimal getValorInvestidoPgblOutraInstituicao() {
        return valorInvestidoPgblOutraInstituicao;
    }

    public void setValorInvestidoPgblOutraInstituicao(@DecimalMin(value = "0.0", message = "Valor investido em PGBL em outras instituições não pode ser negativo") BigDecimal valorInvestidoPgblOutraInstituicao) {
        this.valorInvestidoPgblOutraInstituicao = valorInvestidoPgblOutraInstituicao;
    }

    public @DecimalMin(value = "0.0", message = "Valor da contribuição INSS não pode ser negativo") BigDecimal getValorContribuicaoInss() {
        return valorContribuicaoInss;
    }

    public void setValorContribuicaoInss(@DecimalMin(value = "0.0", message = "Valor da contribuição INSS não pode ser negativo") BigDecimal valorContribuicaoInss) {
        this.valorContribuicaoInss = valorContribuicaoInss;
    }

    public @DecimalMin(value = "0.0", message = "Valor investido em educação não pode ser negativo") BigDecimal getValorInvestidoEducacao() {
        return valorInvestidoEducacao;
    }

    public void setValorInvestidoEducacao(@DecimalMin(value = "0.0", message = "Valor investido em educação não pode ser negativo") BigDecimal valorInvestidoEducacao) {
        this.valorInvestidoEducacao = valorInvestidoEducacao;
    }

    public @DecimalMin(value = "0.0", message = "Valor de despesas médicas não pode ser negativo") BigDecimal getValorDespesasMedicas() {
        return valorDespesasMedicas;
    }

    public void setValorDespesasMedicas(@DecimalMin(value = "0.0", message = "Valor de despesas médicas não pode ser negativo") BigDecimal valorDespesasMedicas) {
        this.valorDespesasMedicas = valorDespesasMedicas;
    }

    public @DecimalMin(value = "0.0", message = "Valor de pensão alimentícia não pode ser negativo") BigDecimal getValorPensaoAlimenticia() {
        return valorPensaoAlimenticia;
    }

    public void setValorPensaoAlimenticia(@DecimalMin(value = "0.0", message = "Valor de pensão alimentícia não pode ser negativo") BigDecimal valorPensaoAlimenticia) {
        this.valorPensaoAlimenticia = valorPensaoAlimenticia;
    }

    public @Min(value = 0, message = "Quantidade de dependentes não pode ser negativa") @Max(value = 20, message = "Quantidade de dependentes não pode ser maior que 20") Integer getQuantidadeDependentes() {
        return quantidadeDependentes;
    }

    public void setQuantidadeDependentes(@Min(value = 0, message = "Quantidade de dependentes não pode ser negativa") @Max(value = 20, message = "Quantidade de dependentes não pode ser maior que 20") Integer quantidadeDependentes) {
        this.quantidadeDependentes = quantidadeDependentes;
    }

    public @DecimalMin(value = "0.0", message = "Valor investido em educação dos dependentes não pode ser negativo") BigDecimal getValorInvestidoEducacaoDependentes() {
        return valorInvestidoEducacaoDependentes;
    }

    public void setValorInvestidoEducacaoDependentes(@DecimalMin(value = "0.0", message = "Valor investido em educação dos dependentes não pode ser negativo") BigDecimal valorInvestidoEducacaoDependentes) {
        this.valorInvestidoEducacaoDependentes = valorInvestidoEducacaoDependentes;
    }

    public @DecimalMin(value = "0.0", message = "Valor de despesas médicas dos dependentes não pode ser negativo") BigDecimal getValorDespesasMedicasDependentes() {
        return valorDespesasMedicasDependentes;
    }

    public void setValorDespesasMedicasDependentes(@DecimalMin(value = "0.0", message = "Valor de despesas médicas dos dependentes não pode ser negativo") BigDecimal valorDespesasMedicasDependentes) {
        this.valorDespesasMedicasDependentes = valorDespesasMedicasDependentes;
    }

    @Override
    public String toString() {
        return "SimulacaoRequest{" +
                "tipoContribuicao=" + tipoContribuicao +
                ", rendaBruta=" + rendaBruta +
                ", valorInvestidoPgblOutraInstituicao=" + valorInvestidoPgblOutraInstituicao +
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

