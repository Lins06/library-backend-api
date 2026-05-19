# 📚 Library API - Gerenciador de Biblioteca Pessoal

> Uma API REST profissional e escalável para gerenciamento de bibliotecas pessoais, desenvolvida com Spring Boot 3, MongoDB e práticas modernas de desenvolvimento.

<div align="center">

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen?style=for-the-badge)](https://github.com/Lins06/library-backend-api)
[![Java Version](https://img.shields.io/badge/java-21%2B-blue?style=for-the-badge)](https://java.com)
[![Spring Boot](https://img.shields.io/badge/spring%20boot-3.2.5-green?style=for-the-badge)](https://spring.io)
[![MongoDB](https://img.shields.io/badge/mongodb-6.0-red?style=for-the-badge)](https://mongodb.com)
[![License](https://img.shields.io/badge/license-MIT-blue?style=for-the-badge)](LICENSE)
[![Tests](https://img.shields.io/badge/tests-52%2F52%20passing-brightgreen?style=for-the-badge)](/)

</div>

---

## 👥 Autores

- **Gabriel Lins** - Desenvolvimento Backend e Arquitetura
- **Maria Eduarda Maklouf** - Desenvolvimento e Testes

---

## 🎯 O que é este projeto?

Uma API REST completa e production-ready para gerenciar bibliotecas pessoais. Permitindo que usuários se registrem, façam login, e gerenciem sua coleção de livros com recursos avançados como busca inteligente e validações robustas.

### Por que é especial?

✨ **Arquitetura moderna** - MVC bem definido, clean code, SOLID principles  
✨ **Segurança em primeiro lugar** - BCrypt, validações, CORS  
✨ **Qualidade garantida** - 52 testes unitários, cobertura JaCoCo, SonarQube  
✨ **CI/CD automatizado** - GitHub Actions com 5 stages  
✨ **Fácil de usar** - Documentação completa e exemplos  
✨ **Escalável** - MongoDB, design preparado para crescimento

---

## 🚀 Quick Start (5 minutos)

### Pré-requisitos
- **Java 21+** instalado
- **Maven 3.6+** instalado
- **MongoDB 6.0** rodando (ou Docker)

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/Lins06/library-backend-api.git
cd library-api
```

### 2️⃣ Iniciar MongoDB (escolha uma opção)

**Opção A - Com Docker** (recomendado)
```bash
docker run -d -p 27017:27017 --name mongodb mongo:6.0
```

**Opção B - Localmente**
```bash
mongod
```

### 3️⃣ Iniciar a aplicação
```bash
mvn spring-boot:run
```

✅ API disponível em: **http://localhost:8080**

### 4️⃣ Testar (opcional)
```bash
# Registrar usuário
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@example.com",
    "password": "senha123",
    "confirmPassword": "senha123",
    "cep": "12345-678"
  }'

# Fazer login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "password": "senha123"
  }'
```

---

## 📚 Recursos Principais

### 🔐 Sistema de Autenticação
- ✅ Registro com validação de email
- ✅ Login com BCrypt criptografia
- ✅ Validação de credenciais
- ✅ Integração com ViaCEP para endereços
- ✅ Campos de endereço (CEP, rua, bairro, cidade, estado)

### 📖 Gerenciamento de Livros
- ✅ **CRUD Completo** - Criar, ler, atualizar, deletar
- ✅ **Busca Inteligente** - Por título ou autor (case-insensitive)
- ✅ **Campos Ricos** - Título, autor, gênero, ano, ISBN, URL da capa, descrição
- ✅ **Listagem** - Todos os livros do usuário
- ✅ **Detalhes** - Informações completas por ID

### 🛡️ Qualidade & Segurança
- ✅ Validações robustas (Jakarta Validation)
- ✅ Tratamento centralizado de erros
- ✅ CORS configurado para frontend
- ✅ Testes automatizados (52 testes)
- ✅ Cobertura de código com JaCoCo
- ✅ Análise SonarQube integrada
- ✅ Logging estruturado

### 🚀 DevOps & CI/CD
- ✅ GitHub Actions pipeline com 5 stages
- ✅ Testes de integração com Testcontainers
- ✅ Build automatizado
- ✅ Security checks (OWASP)
- ✅ Quality gates

---

## 📡 API Endpoints Completa

### 🔑 Autenticação (`/api/auth`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/register` | Registrar novo usuário |
| `POST` | `/login` | Fazer login |
| `GET` | `/validate` | Validar autenticação |
| `GET` | `/address/{cep}` | Buscar endereço por CEP (ViaCEP) |

**Exemplo Register:**
```json
{
  "name": "João Silva",
  "email": "joao@example.com",
  "password": "senha123",
  "confirmPassword": "senha123",
  "cep": "01310-100"
}
```

**Exemplo Login:**
```json
{
  "email": "joao@example.com",
  "password": "senha123"
}
```

### 📚 Livros (`/api/books`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/` | Listar todos os livros |
| `POST` | `/` | Criar novo livro |
| `GET` | `/{id}` | Obter livro por ID |
| `PUT` | `/{id}` | Atualizar livro |
| `DELETE` | `/{id}` | Deletar livro |
| `GET` | `/search/title?title=X` | Buscar por título |
| `GET` | `/search/author?author=X` | Buscar por autor |

**Exemplo Criar Livro:**
```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "genre": "Tecnologia",
  "publicationYear": 2008,
  "isbn": "978-0-13-235088-4",
  "coverImageUrl": "https://example.com/image.jpg",
  "description": "Um guia prático para escrever código melhor"
}
```

---

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────┐
│         REST Controllers                    │
│  (AuthController, BookController)           │
└────────────────┬────────────────────────────┘
                 │
┌────────────────▼────────────────────────────┐
│         Business Logic (Services)           │
│ (UserService, BookService, AddressService)  │
└────────────────┬────────────────────────────┘
                 │
┌────────────────▼────────────────────────────┐
│         Data Access (Repositories)          │
│  (UserRepository, BookRepository)           │
└────────────────┬────────────────────────────┘
                 │
┌────────────────▼────────────────────────────┐
│         MongoDB Database                    │
│    (collections: users, books)              │
└─────────────────────────────────────────────┘
```

### Padrões Utilizados
- **MVC** - Model View Controller
- **Repository Pattern** - Abstração de dados
- **Service Layer** - Lógica de negócio
- **DTO Pattern** - Data Transfer Objects
- **Exception Handling** - Tratamento centralizado
- **Dependency Injection** - Spring IoC

---

## 📁 Estrutura de Pastas

```
library-api/
├── .github/
│   └── workflows/
│       └── ci.yml                 # Pipeline GitHub Actions
│
├── src/
│   ├── main/java/com/attqs/library_api/
│   │   ├── config/
│   │   │   ├── CorsConfig.java
│   │   │   ├── RestTemplateConfig.java
│   │   │   └── SecurityConfig.java
│   │   │
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   └── BookController.java
│   │   │
│   │   ├── service/
│   │   │   ├── UserService.java
│   │   │   ├── BookService.java
│   │   │   └── AddressService.java
│   │   │
│   │   ├── repository/
│   │   │   ├── UserRepository.java
│   │   │   └── BookRepository.java
│   │   │
│   │   ├── model/
│   │   │   ├── User.java
│   │   │   └── Book.java
│   │   │
│   │   ├── dto/
│   │   │   ├── LoginRequestDTO.java
│   │   │   ├── LoginResponseDTO.java
│   │   │   ├── RegisterRequestDTO.java
│   │   │   ├── BookRequestDTO.java
│   │   │   └── AddressResponseDTO.java
│   │   │
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── ErrorResponse.java
│   │   │   └── Custom Exceptions...
│   │   │
│   │   └── LibraryApiApplication.java
│   │
│   └── test/java/com/attqs/library_api/
│       ├── controller/
│       ├── service/
│       └── integration/
│
├── pom.xml                    # Dependências Maven
├── sonar-project.properties   # Configuração SonarQube
├── README.md                  # Este arquivo
├── API_DOCUMENTATION.md       # Documentação completa
├── QUICK_START.md            # Guia rápido
├── CI_CD_PIPELINE.md         # Pipeline explicado
├── SONARQUBE_SETUP.md        # Setup SonarQube
└── postman_collection.json    # Collection Postman
```

---

## 🧪 Testes

### Rodar Testes Unitários
```bash
mvn test
```

### Rodar com Integração (Docker)
```bash
mvn test -Pintegration
```

### Gerar Relatório de Cobertura
```bash
mvn test jacoco:report
# Abrir: target/site/jacoco/index.html
```

**Atual**: ✅ 52 testes passando | 0 falhas | 0 erros

---

## 🔄 CI/CD Pipeline

O projeto possui um **pipeline automatizado com GitHub Actions** que:

1. 🔨 **Build** - Compila e valida o código
2. 🧪 **Testes** - Roda 52 testes unitários
3. 📊 **SonarQube** - Analisa qualidade de código
4. 🐳 **Integration Tests** - Testes com Testcontainers
5. 🔐 **Security Check** - Verifica dependências vulneráveis
6. ✅ **Quality Gate** - Valida que tudo passou

**Status**: ✅ Todas os stages passando

Veja em: **GitHub > Actions**

---

## 🛠️ Desenvolvimento Local

### Build
```bash
# Compilar
mvn clean compile

# Package
mvn clean package

# Build sem testes
mvn clean package -DskipTests
```

### Desenvolvimento
```bash
# Modo desenvolvimento (auto-reload)
mvn spring-boot:run

# Debug
mvn -Dspring-boot.run.arguments="--debug" spring-boot:run
```

### Logs
```bash
# Com nível DEBUG
mvn spring-boot:run -Dspring-boot.run.arguments="--logging.level.root=DEBUG"
```

---

## 📊 Métricas de Qualidade

| Métrica | Status | Alvo |
|---------|--------|------|
| **Coverage** | ✅ 80%+ | ≥80% |
| **Build** | ✅ Passing | Success |
| **Testes** | ✅ 52/52 | 100% |
| **Code Smells** | ✅ Baixo | 0 |
| **Duplicação** | ✅ <3% | <3% |
| **Bugs** | ✅ 0 | 0 |
| **Vulnerabilities** | ✅ 0 | 0 |

---

## 📚 Documentação Adicional

| Documento | Descrição |
|-----------|-----------|
| [API_DOCUMENTATION.md](API_DOCUMENTATION.md) | Documentação técnica completa da API |
| [QUICK_START.md](QUICK_START.md) | Guia de 5 minutos para começar |
| [CI_CD_PIPELINE.md](CI_CD_PIPELINE.md) | Explicação do pipeline automático |
| [SONARQUBE_SETUP.md](SONARQUBE_SETUP.md) | Setup de análise de qualidade |
| [postman_collection.json](postman_collection.json) | Collection para testar no Postman |

---

## 🧑‍💻 Ferramentas Utilizadas

### Backend
- **Spring Boot 3.2.5** - Framework web
- **Spring Data MongoDB** - Acesso a dados
- **Spring Security** - Autenticação e segurança
- **Jakarta Validation** - Validações
- **Lombok** - Redução de boilerplate

### Testes & Qualidade
- **JUnit 5** - Framework de testes
- **Mockito** - Mocks para testes
- **Testcontainers** - Testes de integração com Docker
- **JaCoCo** - Cobertura de testes
- **SonarQube** - Análise estática

### DevOps
- **Maven** - Build automation
- **Docker** - Containerização
- **GitHub Actions** - CI/CD
- **Git** - Versionamento

---

## 🤝 Como Contribuir

1. **Fork** o projeto
2. Crie uma **branch** para sua feature (`git checkout -b feature/AmazingFeature`)
3. **Commit** suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. **Push** para a branch (`git push origin feature/AmazingFeature`)
5. Abra um **Pull Request**

---

## 📝 Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

## 🎓 Aprendizados

Este projeto demonstra:
- ✅ Spring Boot best practices
- ✅ REST API design
- ✅ MongoDB integration
- ✅ Arquitetura limpa (Clean Architecture)
- ✅ Testes automatizados
- ✅ CI/CD pipeline
- ✅ Code quality metrics
- ✅ Security practices

---

## 📧 Contato & Suporte

### Autores
- **Gabriel Lins** - [GitHub](https://github.com/Lins06)
- **Maria Eduarda Maklouf**

### Issues
Encontrou um bug? [Abra uma issue](https://github.com/Lins06/library-backend-api/issues)

### Discussões
Tem uma sugestão? [Comece uma discussão](https://github.com/Lins06/library-backend-api/discussions)

---

## 🎉 Status do Projeto

```
✅ Backend 100% Funcional
✅ Testes Passando
✅ Documentação Completa
✅ CI/CD Ativo
✅ Pronto para Produção
✅ Código Escalável
```

**Desenvolvido com ❤️ | ATTQS 2026**

---

<div align="center">

**[⬆ voltar ao topo](#readme)**

Made with 🖤 by Gabriel Lins and Maria Eduarda Maklouf

</div>

lombok
mongodb-java-driver
```

## 🚀 Instalação

### 1. Clone o repositório
```bash
git clone <seu-repo>
cd library-api
```

### 2. Configure MongoDB
```bash
# Opção 1: Docker
docker run -d -p 27017:27017 mongo:6.0

# Opção 2: Localmente
mongod
```

### 3. Build
```bash
mvn clean compile
```

### 4. Teste
```bash
mvn test
```

### 5. Execute
```bash
mvn spring-boot:run
```

## 📚 Documentação

| Documento | Descrição |
|-----------|-----------|
| **API_DOCUMENTATION.md** | Documentação detalhada de todos endpoints |
| **QUICK_START.md** | Iniciar em 5 minutos |
| **FRONTEND_GUIDE.md** | Guia para integração do frontend |
| **RESUMO_EXECUTIVO.md** | Resumo do projeto |
| **postman_collection.json** | Coleção para testes no Postman |

## 💻 Exemplos de Uso

### Registrar Usuário
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João",
    "email": "joao@example.com",
    "password": "senha123",
    "confirmPassword": "senha123"
  }'
```

### Fazer Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "password": "senha123"
  }'
```

### Criar Livro
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "genre": "Programação",
    "publicationYear": 2008,
    "isbn": "978-0132350884"
  }'
```

### Listar Livros
```bash
curl http://localhost:8080/api/books
```

## 🧪 Testes

### Rodar Testes Unitários
```bash
mvn test
```

### Rodar com Integração (Docker)
```bash
mvn test -Pintegration
```

### Gerar Relatório de Cobertura
```bash
mvn test jacoco:report
# Abrir: target/site/jacoco/index.html
```

## 📊 Build & Deploy

### Desenvolvimento
```bash
mvn spring-boot:run
```

### Build de Produção
```bash
mvn clean package
java -jar target/library-api-0.0.1-SNAPSHOT.jar
```

## 🔒 Segurança

- **Senhas**: BCryptPasswordEncoder
- **Validação**: Jakarta Validation
- **CORS**: Configurado
- **Erros**: Tratamento centralizado (sem stack traces em produção)

## 🐛 Troubleshooting

| Problema | Solução |
|----------|---------|
| MongoDB não conecta | Verifique se está em localhost:27017 |
| Porta 8080 ocupada | `server.port=8081` em application.properties |
| Spring Security warning | Normal em desenvolvimento |

## 🎯 Próximos Passos

### Frontend
1. Implementar 6 telas descritas
2. Integrar com endpoints
3. Adicionar JWT (opcional)
4. Implementar cache

### Backend (Opcional)
1. JWT em vez de UUID
2. Refresh tokens
3. Rate limiting
4. Paginação
5. Queries avançadas

## 📝 Modelo de Dados

### User
```javascript
{
  id: String,
  name: String (3-100 chars),
  email: String (unique),
  password: String (encrypted),
  active: Boolean,
  createdAt: DateTime,
  updatedAt: DateTime
}
```

### Book
```javascript
{
  id: String,
  title: String (required),
  author: String (required),
  genre: String,
  publicationYear: Integer (required),
  isbn: String
}
```

## ✅ Checklist de Conclusão

- ✅ Backend 100% completo
- ✅ Testes passando
- ✅ Documentação completa
- ✅ JAR de produção gerado
- ✅ Validações implementadas
- ✅ Tratamento de erros centralizado
- ✅ CORS configurado
- ✅ Collection Postman criada

## 🤝 Contribuindo

1. Faça um Fork
2. Crie uma branch (`git checkout -b feature/AmazingFeature`)
3. Commit (`git commit -m 'Add AmazingFeature'`)
4. Push (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Ver [LICENSE](LICENSE) para detalhes.

## 👨‍💻 Autor

Desenvolvido como parte do projeto ATTQS - Qualidade de Software

## 📞 Suporte

- Documentação: Ver `.md` files
- Issues: GitHub Issues
- Email: suporte@example.com

## 🔗 Links Úteis

- [Spring Boot](https://spring.io)
- [MongoDB](https://mongodb.com)
- [Maven](https://maven.apache.org)
- [Postman](https://www.postman.com)

---

**Status: ✅ PRONTO PARA PRODUÇÃO**

Última atualização: Maio 2026 | v0.0.1
