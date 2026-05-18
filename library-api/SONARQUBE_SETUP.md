# SonarQube Setup - Library API

## 📋 Requisitos de Qualidade de Código

Este projeto está configurado para integração completa com **SonarQube** para análise de qualidade de código, cobertura de testes e detecção de vulnerabilidades.

---

## 🚀 Configuração do SonarQube

### Opção 1: SonarQube Local com Docker

```bash
# 1. Iniciar SonarQube em Docker
docker run -d \
  --name sonarqube \
  -p 9000:9000 \
  -e SONAR_JDBC_URL=jdbc:h2:./data/h2db/sonar \
  sonarqube:latest

# 2. Acessar em http://localhost:9000
# Login: admin / admin
```

### Opção 2: SonarCloud (Cloud-based)

```bash
# 1. Criar conta em https://sonarcloud.io
# 2. Conectar repositório GitHub
# 3. Adicionar SONAR_TOKEN aos Secrets do GitHub
```

---

## 🔧 Executar Análise SonarQube Localmente

### Pré-requisito: SonarQube Scanner

```bash
# Instalar SonarQube Scanner CLI
# Windows:
choco install sonarqube-scanner

# Linux/Mac:
brew install sonarqube-scanner
```

### Executar Análise

```bash
# 1. Compilar e testar
mvn clean test jacoco:report

# 2. Executar análise SonarQube
mvn sonar:sonar \
  -Dsonar.projectKey=library-api \
  -Dsonar.sources=src/main/java \
  -Dsonar.tests=src/test/java \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=seu_token_aqui
```

---

## 🔐 GitHub Secrets Necessários

Para CI/CD com SonarCloud, configure os seguintes secrets no GitHub:

| Secret | Descrição |
|--------|-----------|
| `SONAR_TOKEN` | Token do SonarCloud gerado em https://sonarcloud.io |
| `GITHUB_TOKEN` | Automático pelo GitHub (usado para SonarCloud) |

### Como Adicionar Secrets:

1. Vá para: **Settings > Secrets and variables > Actions**
2. Clique em **New repository secret**
3. Adicione:
   - **Name**: `SONAR_TOKEN`
   - **Value**: Token do SonarCloud

---

## 📊 Métricas Monitoradas

| Métrica | Descrição |
|---------|-----------|
| **Coverage** | Cobertura de testes (target: >80%) |
| **Bugs** | Detecta bugs potenciais |
| **Code Smells** | Problemas de design e manutenibilidade |
| **Vulnerabilities** | Vulnerabilidades de segurança |
| **Security Hotspots** | Pontos críticos de segurança |
| **Duplication** | Código duplicado (target: <3%) |

---

## 📈 Relatório de Cobertura (JaCoCo)

O projeto usa **JaCoCo** para gerar relatórios de cobertura:

```bash
# Gerar relatório
mvn test jacoco:report

# Acessar em
target/site/jacoco/index.html
```

---

## 🔄 Pipeline de CI/CD Automatizado

O arquivo `.github/workflows/ci.yml` executa:

1. **Build & Unit Tests** - Compila e testa o código
2. **Code Coverage** - Gera relatório JaCoCo
3. **SonarQube Analysis** - Analisa qualidade (se token configurado)
4. **Integration Tests** - Testes com Testcontainers
5. **Security Check** - Verifica dependências (OWASP)
6. **Quality Gate** - Valida que tudo passou

### Status do Pipeline:

Visível em: **GitHub > Actions**

---

## 🎯 Quality Gate Recomendado

Configure em SonarQube:

```
- Coverage >= 80%
- Duplicated Lines <= 3%
- Code Smells: A (Better)
- Bugs: 0
- Vulnerabilities: 0
- Hotspots Reviewed: 100%
```

---

## 📝 Exemplo de Análise

```bash
# Análise completa do projeto
mvn clean verify sonar:sonar
```

---

## 🔗 Referências

- [SonarQube Documentação](https://docs.sonarqube.org/)
- [SonarCloud](https://sonarcloud.io/)
- [JaCoCo Maven Plugin](https://www.jacoco.org/jacoco/trunk/doc/maven.html)
- [GitHub Actions](https://docs.github.com/en/actions)

---

**Status**: ✅ Configurado e Pronto para Usar
