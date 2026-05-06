# 📚 Library API - Gerenciador de Biblioteca Pessoal

## Visão Geral
API REST para gerenciamento de biblioteca pessoal com autenticação de usuários e CRUD completo de livros, desenvolvida com Spring Boot, MongoDB e Java 21.

## 🚀 Funcionalidades

### Autenticação e Usuários
- ✅ Registro de novos usuários
- ✅ Login com validação de credenciais
- ✅ Criptografia de senhas com BCrypt
- ✅ Gerenciamento de perfil

### Gerenciamento de Livros
- ✅ Criar novos livros
- ✅ Listar todos os livros
- ✅ Buscar livro por ID
- ✅ Buscar livros por título
- ✅ Buscar livros por autor
- ✅ Atualizar informações do livro
- ✅ Deletar livro

## 📋 Tecnologias Utilizadas

- **Backend**: Spring Boot 3.2.5
- **Banco de Dados**: MongoDB
- **Autenticação**: BCrypt Password Encoder
- **Validação**: Jakarta Validation
- **ORM**: Spring Data MongoDB
- **Testes**: JUnit 5, Testcontainers
- **Java**: 21
- **Build**: Maven 3

## 🛠️ Instalação e Configuração

### Pré-requisitos
- Java 21+
- Maven 3.6+
- MongoDB rodando localmente (porta 27017)

### Passos

1. **Clonar o repositório**
```bash
git clone <seu-repo>
cd library-api
```

2. **Configurar MongoDB**
```bash
# MongoDB deve estar rodando em localhost:27017
# Cria automaticamente o banco 'library_db'
```

3. **Compilar o projeto**
```bash
mvn clean compile
```

4. **Rodar os testes**
```bash
mvn test
# Ou apenas testes unitários (sem integração):
mvn test -Pdefault
```

5. **Iniciar a aplicação**
```bash
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

## 📡 Endpoints da API

### 🔐 Autenticação (`/api/auth`)

#### Registrar Novo Usuário
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@example.com",
  "password": "senha123",
  "confirmPassword": "senha123"
}

Response (201 Created):
{
  "id": "507f1f77bcf86cd799439011",
  "name": "João Silva",
  "email": "joao@example.com",
  "active": true,
  "message": "Registrado com sucesso",
  "token": "uuid-token-aqui"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao@example.com",
  "password": "senha123"
}

Response (200 OK):
{
  "id": "507f1f77bcf86cd799439011",
  "name": "João Silva",
  "email": "joao@example.com",
  "active": true,
  "message": "Login realizado com sucesso",
  "token": "uuid-token-aqui"
}
```

#### Validar Autenticação
```http
GET /api/auth/validate

Response (200 OK):
"Autenticação válida"
```

---

### 📚 Livros (`/api/books`)

#### Criar Livro
```http
POST /api/books
Content-Type: application/json

{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "genre": "Programação",
  "publicationYear": 2008,
  "isbn": "978-0132350884"
}

Response (201 Created):
{
  "id": "507f1f77bcf86cd799439011",
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "genre": "Programação",
  "publicationYear": 2008,
  "isbn": "978-0132350884"
}
```

#### Listar Todos os Livros
```http
GET /api/books

Response (200 OK):
[
  {
    "id": "507f1f77bcf86cd799439011",
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "genre": "Programação",
    "publicationYear": 2008,
    "isbn": "978-0132350884"
  },
  ...
]
```

#### Obter Livro por ID
```http
GET /api/books/{id}

Response (200 OK):
{
  "id": "507f1f77bcf86cd799439011",
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "genre": "Programação",
  "publicationYear": 2008,
  "isbn": "978-0132350884"
}
```

#### Buscar por Título
```http
GET /api/books/search/title?title=Clean

Response (200 OK):
[
  {
    "id": "507f1f77bcf86cd799439011",
    "title": "Clean Code",
    "author": "Robert C. Martin",
    ...
  }
]
```

#### Buscar por Autor
```http
GET /api/books/search/author?author=Martin

Response (200 OK):
[
  {
    "id": "507f1f77bcf86cd799439011",
    "title": "Clean Code",
    "author": "Robert C. Martin",
    ...
  }
]
```

#### Atualizar Livro
```http
PUT /api/books/{id}
Content-Type: application/json

{
  "title": "Clean Code (2nd Edition)",
  "author": "Robert C. Martin",
  "genre": "Programação",
  "publicationYear": 2024,
  "isbn": "978-0132350884"
}

Response (200 OK):
{
  "id": "507f1f77bcf86cd799439011",
  "title": "Clean Code (2nd Edition)",
  ...
}
```

#### Deletar Livro
```http
DELETE /api/books/{id}

Response (204 No Content)
```

---

## 📝 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/attqs/library_api/
│   │   ├── config/              # Configurações (CORS, Security)
│   │   ├── controller/          # Controllers (AuthController, BookController)
│   │   ├── model/               # Entidades (User, Book)
│   │   ├── repository/          # Data Access Layer (UserRepository, BookRepository)
│   │   ├── service/             # Business Logic (UserService, BookService)
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── exception/           # Custom Exceptions e Handler
│   │   └── LibraryApiApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/attqs/library_api/
        ├── LibraryApiApplicationTests.java
        └── integration/
```

## 🧪 Testes

### Rodar Testes Unitários
```bash
mvn test
```

### Rodar Testes com Integração (requer Docker)
```bash
mvn test -Pintegration
```

### Gerar Relatório de Cobertura
```bash
mvn test jacoco:report
# Relatório gerado em: target/site/jacoco/index.html
```

## ⚠️ Tratamento de Erros

A API retorna respostas padronizadas para erros:

```json
{
  "timestamp": "2026-05-05T21:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Erro de validação"
}
```

### Códigos HTTP Utilizados
- `200 OK`: Requisição bem-sucedida
- `201 Created`: Recurso criado com sucesso
- `204 No Content`: Deletado com sucesso
- `400 Bad Request`: Validação falhou
- `401 Unauthorized`: Credenciais inválidas
- `404 Not Found`: Recurso não encontrado
- `409 Conflict`: Email já cadastrado
- `500 Internal Server Error`: Erro no servidor

## 🔒 Segurança

- Senhas criptografadas com BCrypt
- CORS configurado para aceitar todas as origens (desenvolvimento)
- Validação de entrada em todos os endpoints
- Tratamento centralizado de exceções

## 📦 Dependências Principais

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

## 🚀 Deploy

### Build Production
```bash
mvn clean package
```

### Executar JAR
```bash
java -jar target/library-api-0.0.1-SNAPSHOT.jar
```

## 📝 Exemplo Completo de Uso

```bash
# 1. Registrar usuário
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria",
    "email": "maria@example.com",
    "password": "senha123",
    "confirmPassword": "senha123"
  }'

# 2. Fazer login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "maria@example.com",
    "password": "senha123"
  }'

# 3. Adicionar livro
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "O Senhor dos Anéis",
    "author": "J.R.R. Tolkien",
    "genre": "Ficção",
    "publicationYear": 1954,
    "isbn": "978-0544003415"
  }'

# 4. Listar todos os livros
curl http://localhost:8080/api/books

# 5. Buscar por título
curl "http://localhost:8080/api/books/search/title?title=Senhor"
```

## 🤝 Contribuindo

1. Faça um Fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Ver arquivo LICENSE para mais detalhes.

## 👨‍💻 Autor

Desenvolvido como parte do projeto de Qualidade de Software (ATTQS).

## 📞 Suporte

Para dúvidas ou sugestões, abra uma issue no repositório.

---

**Status**: ✅ Production Ready | Última atualização: Maio 2026
