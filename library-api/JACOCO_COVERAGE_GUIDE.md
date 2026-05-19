# JaCoCo - Guia de Cobertura de Testes

## 📊 Visão Geral

JaCoCo (Java Code Coverage) é uma ferramenta que mede a porcentagem de código coberto pelos testes. Esta configuração foi implementada para garantir qualidade e confiabilidade do código através de verificações automáticas de cobertura.

## 🎯 Limites de Cobertura Configurados

A configuração atual estabelece os seguintes limites de cobertura mínima:

| Tipo de Classe | Cobertura de Linha | Cobertura de Branch | Status |
|---|---|---|---|
| **Geral (Padrão)** | 80% | 80% | 🟢 Produção |
| **Controllers** | 80% | - | 🟢 Produção |
| **Services** | 80% | - | 🟢 Produção |
| **DTOs, Models, Entities, Configs, Exceptions** | 0% (Sem limite) | - | ⚪ Não Obrigatório |

### 📈 Manutenção da Qualidade

A partir de agora:
- Manter a cobertura de código acima de 80% em todas as novas implementações (pull requests).
- O Quality Gate do SonarQube falhará se a cobertura de código novo for inferior a 80%.

## 🚀 Como Executar

### 1. Executar apenas testes unitários com cobertura

```bash
mvn clean test
```

Esto vai:
- Executar todos os testes
- Coletar dados de cobertura
- Gerar relatório HTML
- Verificar se os limites de cobertura foram atingidos
- **Falhar o build se não atingir os limites** ❌

### 2. Executar testes de integração (inclui cobertura)

```bash
mvn clean test -Pintegration
```

### 3. Pular verificação de cobertura (apenas gerar relatório)

```bash
mvn clean test -Djacoco.skip=true
```

### 4. Apenas gerar relatório sem verificação rigorosa

```bash
mvn test jacoco:report
```

## 📈 Interpretando os Relatórios

### Relatório HTML

Após executar os testes, abra:

```
target/site/jacoco/index.html
```

**Estrutura do relatório:**
- **Elementos:** Mostra cobertura por package, classe, método e linha
- **Missed Instructions:** Instruções não executadas
- **Cov.:** Porcentagem de cobertura
- **Branchs:** Cobertura de decisões (if/else, switch, etc)

### Arquivo XML (para CI/CD)

O arquivo `target/site/jacoco/jacoco.xml` é usado por:
- **SonarQube:** Análise de qualidade de código
- **CI/CD Pipelines:** Automação
- **Ferramentas de QA:** Integração com outras plataformas

## 🔍 Analisando Cobertura

### Ver cobertura por classe

1. Abra `target/site/jacoco/index.html`
2. Clique em um pacote para expandir
3. Clique em uma classe para ver cobertura de método
4. Clique em um método para ver cobertura de linha

### Código não coberto aparece em:
- **🔴 Vermelho:** Linhas não executadas
- **🟡 Amarelo:** Branches não totalmente cobertos
- **🟢 Verde:** Código totalmente coberto

## 💡 Boas Práticas

### 1. Aumentar cobertura incrementalmente

```java
// ❌ Evite ignorar métodos
@Ignore
public void testMethod() {}

// ✅ Trate exceções e casos edge
@Test
void shouldHandleNullInput() {
    assertThrows(IllegalArgumentException.class, () -> service.process(null));
}
```

### 2. Testar Controllers

```java
@Test
void shouldReturnBooksSuccessfully() {
    // Arrange
    List<Book> books = Arrays.asList(new Book(...));
    when(service.getAll()).thenReturn(books);
    
    // Act & Assert
    mvc.perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(content().json(...));
}
```

### 3. Testar Services

```java
@Test
void shouldCreateBookWithValidData() {
    // Test normal flow
}

@Test
void shouldThrowExceptionWhenDuplicateEmail() {
    // Test exception handling
}

@Test
void shouldReturnEmptyListWhenNoBooks() {
    // Test edge cases
}
```

### 4. Não forçar 100% de cobertura

- Exceções de configuração não precisam teste
- Getters/Setters em DTOs podem ser gerados
- Métodos deprecated podem ter limite menor

## 🛠️ Troubleshooting

### Build falha: "Coverage is below XX%"

**Solução:** Adicione testes para aumentar cobertura

```bash
# Ver qual código não está coberto
cat target/site/jacoco/index.html

# Identificar gaps e adicionar testes
```

### Relatório não está sendo gerado

```bash
# Limpar cache
mvn clean

# Rodar novamente
mvn test
```

### JaCoCo não está capturando dados

Verifique se `argLine` está configurado em `maven-surefire-plugin`:

```xml
<argLine>${jacoco.agent.argLine}</argLine>
```

## 📊 Integração com SonarQube

Os dados JaCoCo são automaticamente consumidos pelo SonarQube:

```bash
mvn clean test sonar:sonar
```

O SonarQube lê o arquivo `jacoco.xml` configurado em `sonar-project.properties`:

```properties
sonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

## 📝 Arquivos Gerados

Após executar `mvn clean test`:

```
target/
├── jacoco.exec                          # Dados brutos de cobertura
├── site/jacoco/
│   ├── index.html                       # Relatório principal
│   ├── jacoco.xml                       # Formato XML (SonarQube)
│   ├── jacoco.csv                       # Formato CSV
│   └── ...                              # Detalhes por classe
```

## 🎓 Referências

- [Documentação JaCoCo](https://www.jacoco.org/)
- [Maven JaCoCo Plugin](https://www.jacoco.org/jacoco/trunk/doc/maven.html)
- [SonarQube + JaCoCo](https://docs.sonarqube.org/latest/analysis/coverage/)

---

**Nota:** Os limites de cobertura podem ser ajustados no `pom.xml` conforme necessário do projeto.
