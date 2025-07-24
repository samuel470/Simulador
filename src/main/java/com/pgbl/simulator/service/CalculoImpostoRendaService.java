package com.pgbl.simulator.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service responsável pelos cálculos de Imposto de Renda
 */
@Service
public class CalculoImpostoRendaService {

    // Constantes para cálculo anual (2025)
    private static final BigDecimal[] FAIXAS_ANUAIS = {
        new BigDecimal("27110.40"),
        new BigDecimal("33919.80"),
        new BigDecimal("45012.60"),
        new BigDecimal("55976.16")
    };

    private static final BigDecimal[] ALIQUOTAS_ANUAIS = {
        BigDecimal.ZERO,
        new BigDecimal("0.075"),
        new BigDecimal("0.15"),
        new BigDecimal("0.225"),
        new BigDecimal("0.275")
    };

    private static final BigDecimal[] PARCELAS_DEDUZIR_ANUAIS = {
        BigDecimal.ZERO,
        new BigDecimal("2033.28"),
        new BigDecimal("4577.28"),
        new BigDecimal("7953.24"),
        new BigDecimal("10752.00")
    };

    // Constantes para cálculo mensal (2025)
    private static final BigDecimal[] FAIXAS_MENSAIS = {
        new BigDecimal("2259.20"),
        new BigDecimal("2826.65"),
        new BigDecimal("3751.05"),
        new BigDecimal("4664.68")
    };

    private static final BigDecimal[] ALIQUOTAS_MENSAIS = {
        BigDecimal.ZERO,
        new BigDecimal("0.075"),
        new BigDecimal("0.15"),
        new BigDecimal("0.225"),
        new BigDecimal("0.275")
    };

    private static final BigDecimal[] PARCELAS_DEDUZIR_MENSAIS = {
        BigDecimal.ZERO,
        new BigDecimal("169.44"),
        new BigDecimal("381.44"),
        new BigDecimal("662.77"),
        new BigDecimal("896.00")
    };

    // Constantes de deduções
    private static final BigDecimal VALOR_DEPENDENTE_ANUAL = new BigDecimal("2275.08");
    private static final BigDecimal LIMITE_EDUCACAO_ANUAL = new BigDecimal("3561.50");
    private static final BigDecimal PERCENTUAL_LIMITE_PGBL = new BigDecimal("0.12");

    /**
     * Calcula o imposto de renda com base na renda tributável
     */
    public BigDecimal calcularImpostoRenda(BigDecimal rendaTributavel, boolean isAnual) {
        if (rendaTributavel.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal[] faixas = isAnual ? FAIXAS_ANUAIS : FAIXAS_MENSAIS;
        BigDecimal[] aliquotas = isAnual ? ALIQUOTAS_ANUAIS : ALIQUOTAS_MENSAIS;
        BigDecimal[] parcelasADeduzir = isAnual ? PARCELAS_DEDUZIR_ANUAIS : PARCELAS_DEDUZIR_MENSAIS;

        int faixa = determinarFaixa(rendaTributavel, faixas);
        
        BigDecimal impostoCalculado = rendaTributavel
            .multiply(aliquotas[faixa])
            .subtract(parcelasADeduzir[faixa]);

        return impostoCalculado.max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Determina a faixa de tributação
     */
    private int determinarFaixa(BigDecimal valor, BigDecimal[] faixas) {
        for (int i = 0; i < faixas.length; i++) {
            if (valor.compareTo(faixas[i]) <= 0) {
                return i;
            }
        }
        return faixas.length; // Última faixa
    }

    /**
     * Calcula a alíquota marginal de IR
     */
    public BigDecimal calcularAliquotaMarginal(BigDecimal rendaTributavel, boolean isAnual) {
        BigDecimal[] faixas = isAnual ? FAIXAS_ANUAIS : FAIXAS_MENSAIS;
        BigDecimal[] aliquotas = isAnual ? ALIQUOTAS_ANUAIS : ALIQUOTAS_MENSAIS;

        int faixa = determinarFaixa(rendaTributavel, faixas);
        return aliquotas[faixa].multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP);
    }

    /**
     * Calcula o total de deduções permitidas
     */
    public BigDecimal calcularDeducoes(BigDecimal valorContribuicaoInss,
                                     BigDecimal valorEducacao,
                                     BigDecimal valorDespesasMedicas,
                                     BigDecimal valorPensaoAlimenticia,
                                     int quantidadeDependentes,
                                     BigDecimal valorEducacaoDependentes,
                                     BigDecimal valorDespesasMedicasDependentes,
                                     boolean isAnual) {
        
        BigDecimal totalDeducoes = BigDecimal.ZERO;

        // INSS
        totalDeducoes = totalDeducoes.add(valorContribuicaoInss);

        // Dependentes
        BigDecimal valorDependentes = VALOR_DEPENDENTE_ANUAL.multiply(new BigDecimal(quantidadeDependentes));
        if (!isAnual) {
            valorDependentes = valorDependentes.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
        }
        totalDeducoes = totalDeducoes.add(valorDependentes);

        // Educação (titular)
        BigDecimal limiteEducacao = isAnual ? LIMITE_EDUCACAO_ANUAL : 
            LIMITE_EDUCACAO_ANUAL.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
        totalDeducoes = totalDeducoes.add(valorEducacao.min(limiteEducacao));

        // Educação (dependentes) - limite por dependente
        BigDecimal limiteEducacaoPorDependente = limiteEducacao;
        BigDecimal limiteEducacaoTotalDependentes = limiteEducacaoPorDependente.multiply(new BigDecimal(quantidadeDependentes));
        totalDeducoes = totalDeducoes.add(valorEducacaoDependentes.min(limiteEducacaoTotalDependentes));

        // Despesas médicas (sem limite)
        totalDeducoes = totalDeducoes.add(valorDespesasMedicas);
        totalDeducoes = totalDeducoes.add(valorDespesasMedicasDependentes);

        // Pensão alimentícia
        totalDeducoes = totalDeducoes.add(valorPensaoAlimenticia);

        return totalDeducoes.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula o limite máximo de contribuição PGBL (12% da renda bruta)
     */
    public BigDecimal calcularLimitePgbl(BigDecimal rendaBruta) {
        return rendaBruta.multiply(PERCENTUAL_LIMITE_PGBL).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula a base tributável
     */
    public BigDecimal calcularBaseTributavel(BigDecimal rendaBruta, BigDecimal totalDeducoes) {
        BigDecimal baseTributavel = rendaBruta.subtract(totalDeducoes);
        return baseTributavel.max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula o ganho fiscal com PGBL
     */
    public BigDecimal calcularGanhoFiscal(BigDecimal valorPgbl, BigDecimal aliquotaMarginal) {
        return valorPgbl.multiply(aliquotaMarginal.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula o valor ideal de contribuição PGBL
     */
    public BigDecimal calcularValorIdealPgbl(BigDecimal rendaBruta, 
                                           BigDecimal baseTributavelSemPgbl, 
                                           boolean isAnual) {
        
        BigDecimal limitePgbl = calcularLimitePgbl(rendaBruta);
        
        // Calcula quanto seria necessário para zerar o IR
        BigDecimal valorParaZerarIr = calcularValorParaZerarIr(baseTributavelSemPgbl, isAnual);
        
        // Retorna o menor entre o limite PGBL e o valor para zerar IR
        return limitePgbl.min(valorParaZerarIr).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula quanto seria necessário investir para zerar o IR
     */
    private BigDecimal calcularValorParaZerarIr(BigDecimal baseTributavel, boolean isAnual) {
        BigDecimal[] faixas = isAnual ? FAIXAS_ANUAIS : FAIXAS_MENSAIS;
        
        if (baseTributavel.compareTo(faixas[0]) <= 0) {
            return BigDecimal.ZERO;
        }
        
        // Retorna a diferença entre a base tributável e a primeira faixa isenta
        return baseTributavel.subtract(faixas[0]).setScale(2, RoundingMode.HALF_UP);
    }
}

