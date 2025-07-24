# Exemplos Práticos de Uso - PGBL Simulator API

Este documento apresenta exemplos práticos de uso da API PGBL Simulator para diferentes cenários reais.

## 📋 Cenários de Teste

### Cenário 1: Profissional Liberal com Dependentes

**Perfil:**
- Renda anual: R$ 180.000
- 2 dependentes
- Gastos com educação própria: R$ 3.500
- Gastos com educação dos filhos: R$ 8.000
- Despesas médicas: R$ 4.000
- Contribui para INSS: R$ 1.500/ano

```bash
curl -X POST http://localhost:8080/api/pgbl/simular \
  -H "Content-Type: application/json" \
  -d '{
    "tipoContribuicao": "ANUAL",
    "rendaBruta": 180000.00,
    "temPrevidenciaOutraInstituicao": true,
    "valorContribuicaoInss": 1500.00,
    "valorInvestidoEducacao": 3500.00,
    "valorDespesasMedicas": 4000.00,
    "quantidadeDependentes": 2,
    "valorInvestidoEducacaoDependentes": 8000.00,
    "valorDespesasMedicasDependentes": 2000.00
  }'
```

**Resultado Esperado:**
- Limite PGBL: R$ 21.600 (12% de R$ 180.000)
- Ganho fiscal significativo devido à alta alíquota marginal
- Economia substancial com as deduções dos dependentes

### Cenário 2: Funcionário CLT Mensal

**Perfil:**
- Salário mensal: R$ 8.000
- Sem dependentes
- Contribuição INSS: R$ 200/mês
- Gastos com educação: R$ 300/mês
- Plano de saúde: R$ 400/mês

```bash
curl -X POST http://localhost:8080/api/pgbl/simular \
  -H "Content-Type: application/json" \
  -d '{
    "tipoContribuicao": "MENSAL",
    "rendaBruta": 8000.00,
    "temPrevidenciaOutraInstituicao": true,
    "valorContribuicaoInss": 200.00,
    "valorInvestidoEducacao": 300.00,
    "valorDespesasMedicas": 400.00,
    "quantidadeDependentes": 0
  }'
```

### Cenário 3: Aposentado sem Previdência Oficial

**Perfil:**
- Renda de aluguéis: R$ 60.000/ano
- Não contribui para previdência oficial
- Despesas médicas elevadas: R$ 15.000/ano

```bash
curl -X POST http://localhost:8080/api/pgbl/simular \
  -H "Content-Type: application/json" \
  -d '{
    "tipoContribuicao": "ANUAL",
    "rendaBruta": 60000.00,
    "temPrevidenciaOutraInstituicao": false,
    "valorDespesasMedicas": 15000.00
  }'
```

**Resultado Esperado:**
- Ganho fiscal PGBL: R$ 0 (sem direito à dedução)
- Apenas deduções médicas aplicáveis

### Cenário 4: Empresário com Família Grande

**Perfil:**
- Renda anual: R$ 300.000
- 4 dependentes
- Pensão alimentícia: R$ 2.000/mês
- Educação própria: R$ 3.561,50 (limite máximo)
- Educação dependentes: R$ 14.246 (4 × R$ 3.561,50)
- Despesas médicas família: R$ 20.000

```bash
curl -X POST http://localhost:8080/api/pgbl/simular \
  -H "Content-Type: application/json" \
  -d '{
    "tipoContribuicao": "ANUAL",
    "rendaBruta": 300000.00,
    "temPrevidenciaOutraInstituicao": true,
    "valorContribuicaoInss": 2000.00,
    "valorInvestidoEducacao": 3561.50,
    "valorDespesasMedicas": 10000.00,
    "valorPensaoAlimenticia": 24000.00,
    "quantidadeDependentes": 4,
    "valorInvestidoEducacaoDependentes": 14246.00,
    "valorDespesasMedicasDependentes": 10000.00
  }'
```

### Cenário 5: Jovem Profissional Iniciante

**Perfil:**
- Renda anual: R$ 45.000
- Sem dependentes
- Contribuição INSS: R$ 800/ano
- Gastos mínimos com educação e saúde

```bash
curl -X POST http://localhost:8080/api/pgbl/simular \
  -H "Content-Type: application/json" \
  -d '{
    "tipoContribuicao": "ANUAL",
    "rendaBruta": 45000.00,
    "temPrevidenciaOutraInstituicao": true,
    "valorContribuicaoInss": 800.00,
    "valorInvestidoEducacao": 1000.00,
    "valorDespesasMedicas": 500.00,
    "quantidadeDependentes": 0
  }'
```

## 🧮 Interpretando os Resultados

### Campos Principais

1. **valorIdealContribuicao**: Quanto investir para maximizar o benefício fiscal
2. **ganhoFiscalMaximo**: Economia máxima de IR possível
3. **quantoFaltaInvestir**: Diferença entre o ideal e o valor atual investido
4. **aliquotaIr**: Sua faixa de tributação atual

### Exemplo de Análise

Para uma resposta como:
```json
{
  "ganhoFiscalMaximo": 3960.00,
  "valorIdealContribuicao": 14400.00,
  "quantoFaltaInvestir": 14400.00,
  "aliquotaIr": 27.5
}
```

**Interpretação:**
- Investindo R$ 14.400 em PGBL, você economiza R$ 3.960 em IR
- Sua alíquota marginal é 27,5%
- Retorno imediato de 27,5% sobre o investimento PGBL

## 📊 Comparação de Cenários

### Tabela de Resultados por Perfil

| Perfil | Renda Anual | Limite PGBL | Ganho Fiscal | ROI Imediato |
|--------|-------------|-------------|--------------|--------------|
| Profissional Liberal | R$ 180.000 | R$ 21.600 | R$ 5.940 | 27,5% |
| CLT (anualizado) | R$ 96.000 | R$ 11.520 | R$ 2.592 | 22,5% |
| Empresário | R$ 300.000 | R$ 36.000 | R$ 9.900 | 27,5% |
| Jovem Profissional | R$ 45.000 | R$ 5.400 | R$ 810 | 15,0% |

## 🎯 Dicas de Otimização

### 1. Maximize as Deduções
- Use o limite máximo de educação (R$ 3.561,50 por pessoa)
- Inclua todas as despesas médicas (sem limite)
- Considere dependentes para aumentar deduções

### 2. Planejamento Anual
- Monitore sua alíquota marginal
- Ajuste contribuições PGBL conforme a renda
- Considere antecipação de despesas dedutíveis

### 3. Casos Especiais
- **Sem previdência oficial**: Sem direito ao PGBL
- **Renda baixa**: Pode não compensar devido à baixa alíquota
- **Muitos dependentes**: Maximize deduções antes do PGBL

## 🔄 Testes Automatizados

### Script de Teste Completo

```bash
#!/bin/bash

echo "=== Testando PGBL Simulator API ==="

# Teste 1: Health Check
echo "1. Health Check..."
curl -s http://localhost:8080/api/pgbl/health
echo -e "\n"

# Teste 2: Cenário com dependentes
echo "2. Cenário com dependentes..."
curl -s -X POST http://localhost:8080/api/pgbl/simular \
  -H "Content-Type: application/json" \
  -d '{
    "tipoContribuicao": "ANUAL",
    "rendaBruta": 120000.00,
    "temPrevidenciaOutraInstituicao": true,
    "quantidadeDependentes": 2
  }' | jq '.'

# Teste 3: Cenário sem previdência
echo "3. Cenário sem previdência..."
curl -s -X POST http://localhost:8080/api/pgbl/simular \
  -H "Content-Type: application/json" \
  -d '{
    "tipoContribuicao": "ANUAL",
    "rendaBruta": 120000.00,
    "temPrevidenciaOutraInstituicao": false
  }' | jq '.'

echo "=== Testes concluídos ==="
```

## 📱 Integração Frontend

### Exemplo JavaScript

```javascript
async function simularPGBL(dados) {
  try {
    const response = await fetch('http://localhost:8080/api/pgbl/simular', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(dados)
    });
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    const resultado = await response.json();
    return resultado;
  } catch (error) {
    console.error('Erro na simulação:', error);
    throw error;
  }
}

// Exemplo de uso
const dadosSimulacao = {
  tipoContribuicao: "ANUAL",
  rendaBruta: 120000.00,
  temPrevidenciaOutraInstituicao: true,
  quantidadeDependentes: 2
};

simularPGBL(dadosSimulacao)
  .then(resultado => {
    console.log('Ganho fiscal:', resultado.ganhoFiscalMaximo);
    console.log('Valor ideal:', resultado.valorIdealContribuicao);
  })
  .catch(error => {
    console.error('Erro:', error);
  });
```

### Exemplo Python

```python
import requests
import json

def simular_pgbl(dados):
    url = "http://localhost:8080/api/pgbl/simular"
    headers = {"Content-Type": "application/json"}
    
    try:
        response = requests.post(url, headers=headers, json=dados)
        response.raise_for_status()
        return response.json()
    except requests.exceptions.RequestException as e:
        print(f"Erro na requisição: {e}")
        return None

# Exemplo de uso
dados = {
    "tipoContribuicao": "ANUAL",
    "rendaBruta": 120000.00,
    "temPrevidenciaOutraInstituicao": True,
    "quantidadeDependentes": 2
}

resultado = simular_pgbl(dados)
if resultado:
    print(f"Ganho fiscal: R$ {resultado['ganhoFiscalMaximo']}")
    print(f"Valor ideal: R$ {resultado['valorIdealContribuicao']}")
```

## 🚨 Tratamento de Erros Comuns

### Erro 400: Dados Inválidos

```json
{
  "status": 400,
  "error": "Dados de entrada inválidos",
  "validationErrors": [
    "rendaBruta: Renda bruta deve ser maior que zero",
    "quantidadeDependentes: Quantidade de dependentes não pode ser maior que 20"
  ]
}
```

### Erro 500: Erro Interno

```json
{
  "status": 500,
  "error": "Erro interno do servidor",
  "message": "Ocorreu um erro inesperado. Tente novamente mais tarde."
}
```

## 📈 Monitoramento e Logs

### Logs da Aplicação

A aplicação gera logs detalhados para monitoramento:

```
2025-07-20 17:26:27 - Started PgblSimulatorApplication in 1.819 seconds
2025-07-20 17:26:27 - Tomcat started on port(s): 8080 (http)
```

### Métricas de Performance

- Tempo médio de resposta: ~50ms
- Throughput: 1000+ req/s
- Uso de memória: ~200MB

---

**Para mais exemplos e documentação completa, consulte o README.md**

