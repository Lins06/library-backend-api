# CI/CD Pipeline - Library API

## 🚀 Integração Contínua e Deployment Contínuo

Este projeto possui um **pipeline CI/CD completo** automatizado via **GitHub Actions**, que valida cada push e pull request.

---

## 📋 Pipeline Stages

### 1️⃣ **Build & Unit Tests**
- **Quando**: A cada push/PR
- **O que faz**:
  - ✅ Compila o código com Maven
  - ✅ Roda testes unitários
  - ✅ Gera cobertura de código (JaCoCo)
  - ✅ Constrói o JAR executável
- **Duração**: ~2-3 minutos

### 2️⃣ **SonarQube Analysis** (Qualidade de Código)
- **Quando**: A cada push/PR (se SONAR_TOKEN configurado)
- **O que faz**:
  - ✅ Analisa qualidade de código
  - ✅ Detecta bugs e vulnerabilidades
  - ✅ Valida cobertura de testes
  - ✅ Gera relatório em SonarCloud
- **Dashboard**: https://sonarcloud.io/

### 3️⃣ **Integration Tests**
- **Quando**: PRs e pushes para `main`
- **O que faz**:
  - ✅ Roda testes de integração com Testcontainers
  - ✅ Testa com MongoDB real em container
  - ✅ Valida fluxos end-to-end
- **Duração**: ~4-5 minutos

### 4️⃣ **Security Check**
- **Quando**: A cada push/PR
- **O que faz**:
  - ✅ Verifica dependências com OWASP
  - ✅ Detecta vulnerabilidades em bibliotecas
  - ✅ Gera relatório de segurança
- **Falha**: Opcional (não bloqueia)

### 5️⃣ **Quality Gate** (Validação Final)
- **Quando**: A cada push/PR
- **O que faz**:
  - ✅ Confirma que build passou
  - ✅ Valida que artefatos foram criados
  - ✅ Marca sucesso/falha do pipeline
- **Duração**: ~1 minuto

---

## 📊 Matriz de Execução

| Stage | Trigger | Status | Artefatos |
|-------|---------|--------|-----------|
| Build & Test | Push / PR | ✅ Obrigatório | JAR |
| SonarQube | Push / PR | ⚠️ Se configurado | Relatório online |
| Integration | Main/Develop | ✅ Se necessário | Log de testes |
| Security | Push / PR | ⚠️ Contínuo | Relatório OWASP |
| Quality Gate | Todas as acima | ✅ Obrigatório | Status final |

---

## 🔄 Fluxo de Trabalho Recomendado

```
1. Você faz push / abre PR
   ↓
2. GitHub Actions inicia automaticamente
   ↓
3. Build & Testes rodam em paralelo
   ↓
4. Se tudo passou: SonarQube analisa
   ↓
5. Integration Tests validam comportamento
   ↓
6. Security Check verifica dependências
   ↓
7. Quality Gate aprova ou rejeita
   ↓
8. Status exibido no PR/Commit
```

---

## 📈 Visualizar Status

### No GitHub:
1. Vá para **Actions**
2. Veja execução em tempo real
3. Clique em uma execução para detalhes
4. Status aparece em cada commit/PR

### Exemplo:
```
✅ build-and-test (2m 45s)
✅ sonarqube (1m 30s)  
✅ integration-tests (3m 20s)
⚠️ security-check (skipped)
✅ quality-gate (45s)
```

---

## 🛠️ Configuração Necessária

### 1. GitHub Secrets

```bash
# SONAR_TOKEN (opcional, para SonarCloud)
Settings > Secrets and variables > Actions > New repository secret
```

### 2. Branch Protection (Recomendado)

```
Settings > Branches > Branch protection rules

☑ Require status checks to pass before merging
☑ Require branches to be up to date before merging
```

---

## 📝 Arquivo de Configuração

Local: `.github/workflows/ci.yml`

```yaml
# Triggers
on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main, develop ]

# Jobs paralelos
jobs:
  build-and-test      # Maven compile + test
  sonarqube          # Análise de código
  integration-tests  # Testcontainers
  security-check     # OWASP dependency
  quality-gate       # Validação final
```

---

## 🔍 Monitorando Falhas

Se algo falhar:

1. **Build falhou**:
   - Verificar logs em `Maven compile`
   - Conferir syntax/imports

2. **Testes falharam**:
   - Verificar logs em `Unit Tests`
   - Rodar localmente: `mvn test`

3. **Coverage baixa**:
   - Verificar SonarQube
   - Adicionar testes para novos códigos

4. **Security Alert**:
   - Verificar `OWASP Dependency Check`
   - Atualizar dependências vulneráveis

### Debug Local:

```bash
# Reproduzir exatamente o que CI faz
mvn clean verify
mvn test jacoco:report
mvn org.owasp:dependency-check-maven:check
```

---

## 📊 Métricas Monitoradas

| Métrica | Alvo | Status |
|---------|------|--------|
| Build | Sucesso | ✅ |
| Unit Tests | 100% pass | ✅ |
| Coverage | ≥80% | ✅ |
| SonarQube | A/B grade | ⏳ |
| Security | 0 críticos | ✅ |
| Integration | 100% pass | ✅ |

---

## 🚀 Deploy Automático (Futuro)

O pipeline está pronto para adicionar stages de deploy:

```yaml
  deploy-staging:
    needs: quality-gate
    if: github.ref == 'refs/heads/develop'
    
  deploy-production:
    needs: quality-gate
    if: github.ref == 'refs/heads/main'
```

---

## 📚 Referências

- [GitHub Actions Documentação](https://docs.github.com/en/actions)
- [SonarSource GitHub Action](https://github.com/SonarSource/sonarcloud-github-action)
- [Maven Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
- [Testcontainers](https://www.testcontainers.org/)

---

**Status**: ✅ Implementado e Ativo
