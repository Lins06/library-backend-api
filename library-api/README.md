# 📚 Library API - Gerenciador de Biblioteca Pessoal

> API REST completa com Spring Boot 3.2.5, MongoDB e autenticação de usuários

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com)
[![Java Version](https://img.shields.io/badge/java-21+-blue.svg)](https://java.com)
[![Spring Boot](https://img.shields.io/badge/spring--boot-3.2.5-green.svg)](https://spring.io)
[![MongoDB](https://img.shields.io/badge/mongodb-6.0-red.svg)](https://mongodb.com)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## 🚀 Quick Start

```bash
# 1. Iniciar MongoDB
docker run -d -p 27017:27017 mongo:6.0

# 2. Compilar e rodar
mvn spring-boot:run

# 3. API disponível em
http://localhost:8080/api
```

## 📋 Características

### 🔐 Autenticação
- ✅ Registro de usuários com validação
- ✅ Login com BCrypt criptografia
- ✅ Token geração
- ✅ Validação de credenciais

### 📚 Gerenciamento de Livros
- ✅ CRUD completo (Create, Read, Update, Delete)
- ✅ Busca por título (case-insensitive)
- ✅ Busca por autor (case-insensitive)
- ✅ Listagem completa
- ✅ Detalhes por ID

### 🛡️ Qualidade
- ✅ Validações de entrada (Jakarta)
- ✅ Tratamento centralizado de erros
- ✅ CORS configurado
- ✅ Testes unitários
- ✅ Logging estruturado

## 📡 API Endpoints

### Autenticação
```
POST   /api/auth/register       # Registrar novo usuário
POST   /api/auth/login          # Fazer login
GET    /api/auth/validate       # Validar autenticação
```

### Livros
```
GET    /api/books               # Listar todos
POST   /api/books               # Criar livro
GET    /api/books/{id}          # Detalhes do livro
PUT    /api/books/{id}          # Atualizar livro
DELETE /api/books/{id}          # Deletar livro
GET    /api/books/search/title?title=X    # Buscar por título
GET    /api/books/search/author?author=X  # Buscar por autor
```

## 📁 Estrutura

```
library-api/
├── src/main/java/com/attqs/library_api/
│   ├── config/              # Configurações (CORS, etc)
│   ├── controller/          # REST Controllers
│   ├── model/               # Entidades JPA
│   ├── repository/          # Data Access
│   ├── service/             # Business Logic
│   ├── dto/                 # Data Transfer Objects
│   ├── exception/           # Exception Handling
│   └── LibraryApiApplication.java
├── src/test/                # Testes
├── pom.xml                  # Dependências Maven
└── README.md                # Este arquivo
```

## 🛠️ Requisitos

- Java 21+
- Maven 3.6+
- MongoDB 6.0+
- Docker (opcional, para MongoDB)

## 📦 Dependências Principais

```xml
<!-- Spring Boot -->
spring-boot-starter-web
spring-boot-starter-data-mongodb
spring-boot-starter-validation
spring-boot-starter-security

<!-- Tools -->
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
