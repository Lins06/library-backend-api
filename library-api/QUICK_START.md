# 🚀 Quick Start - Library API

## ⏱️ Iniciar em 5 minutos

### Pré-requisitos
- Java 21 instalado
- Maven instalado
- MongoDB rodando em localhost:27017

### 1️⃣ Iniciar MongoDB (em um terminal separado)
```bash
# Se tiver Docker:
docker run -d -p 27017:27017 --name mongodb mongo:6.0

# Ou start no MongoDB instalado localmente
mongod
```

### 2️⃣ Compilar o Projeto
```bash
cd library-api
mvn clean compile
```

### 3️⃣ Rodar os Testes
```bash
mvn test
```

### 4️⃣ Iniciar a Aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: **http://localhost:8080**

---

## 📡 Testar Endpoints Rapidamente

### Via cURL

#### 1. Registrar Usuário
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@example.com",
    "password": "senha123",
    "confirmPassword": "senha123"
  }'
```

#### 2. Fazer Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "password": "senha123"
  }'
```

#### 3. Criar Livro
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

#### 4. Listar Livros
```bash
curl http://localhost:8080/api/books
```

#### 5. Buscar por Título
```bash
curl "http://localhost:8080/api/books/search/title?title=Clean"
```

---

## 🧪 Postman Collection

Importe o arquivo `postman_collection.json` no Postman para testar todos os endpoints visualmente.

---

## 📁 Estrutura de Pastas

```
library-api/
├── src/
│   ├── main/
│   │   ├── java/com/attqs/library_api/
│   │   │   ├── config/           # CORS e configurações
│   │   │   ├── controller/       # Endpoints
│   │   │   ├── service/          # Lógica de negócio
│   │   │   ├── repository/       # Acesso a dados
│   │   │   ├── model/            # Entidades
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   └── exception/        # Tratamento de erros
│   │   └── resources/
│   │       └── application.properties
│   └── test/                     # Testes
├── pom.xml                       # Dependências Maven
├── API_DOCUMENTATION.md          # Documentação completa
└── postman_collection.json       # Coleção Postman
```

---

## 🐛 Troubleshooting

### Erro: "Could not find MongoDB"
- Certifique-se que MongoDB está rodando em `localhost:27017`
- Se usar Docker: `docker run -d -p 27017:27017 mongo:6.0`

### Erro: "Port 8080 already in use"
- Altere a porta em `application.properties`:
  ```properties
  server.port=8081
  ```

### Testes falhando
- Testes de integração podem ser pulados:
  ```bash
  mvn test -Pdefault
  ```

---

## 📚 Recursos

- **Documentação Completa**: Ver `API_DOCUMENTATION.md`
- **GitHub**: [Seu repositório]
- **Issues**: Reporte bugs e sugestões

---

## ✅ Checklist - Projeto Completo

- ✅ API REST com Spring Boot 3.2.5
- ✅ Autenticação e Registro de Usuários
- ✅ Criptografia BCrypt
- ✅ MongoDB para persistência
- ✅ CRUD completo de Livros
- ✅ Busca por Título e Autor
- ✅ Validações de entrada
- ✅ Tratamento centralizado de erros
- ✅ Testes unitários
- ✅ CORS configurado
- ✅ Documentação da API
- ✅ Coleção Postman

---

**Desenvolvido com ❤️ | ATTQS 2026**
