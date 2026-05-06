# 🎨 Frontend - Instruções de Integração

## Resumo do Backend Desenvolvido

O backend da Library API está **100% pronto para produção** com os seguintes endpoints:

### Base URL
```
http://localhost:8080/api
```

---

## 🔐 Autenticação

### 1. **Registrar Usuário**
```javascript
// POST /auth/register
const register = async (name, email, password, confirmPassword) => {
  const response = await fetch('http://localhost:8080/api/auth/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, email, password, confirmPassword })
  });
  return response.json();
};
```

**Response (201):**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "name": "João Silva",
  "email": "joao@example.com",
  "active": true,
  "message": "Registrado com sucesso",
  "token": "uuid-token"
}
```

### 2. **Login**
```javascript
// POST /auth/login
const login = async (email, password) => {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password })
  });
  const data = await response.json();
  
  // Armazene o token e ID do usuário
  localStorage.setItem('token', data.token);
  localStorage.setItem('userId', data.id);
  localStorage.setItem('userName', data.name);
  
  return data;
};
```

---

## 📚 Gerenciamento de Livros

### 1. **Criar Livro**
```javascript
// POST /books
const createBook = async (book) => {
  const response = await fetch('http://localhost:8080/api/books', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(book)
  });
  return response.json();
};

// Exemplo de uso:
createBook({
  title: "Clean Code",
  author: "Robert C. Martin",
  genre: "Programação",
  publicationYear: 2008,
  isbn: "978-0132350884"
});
```

### 2. **Listar Todos os Livros**
```javascript
// GET /books
const getAllBooks = async () => {
  const response = await fetch('http://localhost:8080/api/books');
  return response.json();
};
```

### 3. **Buscar Livro por ID**
```javascript
// GET /books/:id
const getBookById = async (id) => {
  const response = await fetch(`http://localhost:8080/api/books/${id}`);
  return response.json();
};
```

### 4. **Buscar por Título**
```javascript
// GET /books/search/title?title=query
const searchByTitle = async (title) => {
  const response = await fetch(
    `http://localhost:8080/api/books/search/title?title=${title}`
  );
  return response.json();
};
```

### 5. **Buscar por Autor**
```javascript
// GET /books/search/author?author=query
const searchByAuthor = async (author) => {
  const response = await fetch(
    `http://localhost:8080/api/books/search/author?author=${author}`
  );
  return response.json();
};
```

### 6. **Atualizar Livro**
```javascript
// PUT /books/:id
const updateBook = async (id, bookData) => {
  const response = await fetch(`http://localhost:8080/api/books/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(bookData)
  });
  return response.json();
};
```

### 7. **Deletar Livro**
```javascript
// DELETE /books/:id
const deleteBook = async (id) => {
  const response = await fetch(`http://localhost:8080/api/books/${id}`, {
    method: 'DELETE'
  });
  return response.status === 204;
};
```

---

## 🎯 Telas Necessárias para o Frontend

### 1️⃣ **Tela de Login** (`/login`)
- Campo: Email
- Campo: Senha
- Botão: "Entrar"
- Link: "Criar Conta"
- Tratamento de erros (credenciais inválidas)

### 2️⃣ **Tela de Cadastro** (`/register`)
- Campo: Nome
- Campo: Email
- Campo: Senha
- Campo: Confirmar Senha
- Botão: "Registrar"
- Validações:
  - Nome: 3-100 caracteres
  - Email: formato válido
  - Senha: mínimo 6 caracteres
  - Senhas devem ser iguais

### 3️⃣ **Dashboard / Listagem** (`/dashboard` ou `/`)
- Exibição de todos os livros em lista ou grid
- Barra de busca por título e autor
- Botões por livro:
  - 👁️ Visualizar
  - ✏️ Editar
  - 🗑️ Deletar
- Botão "+ Novo Livro" no topo

### 4️⃣ **Formulário de Cadastro de Livro** (`/books/create`)
- Campo: Título (obrigatório)
- Campo: Autor (obrigatório)
- Campo: Gênero (opcional)
- Campo: Ano de Publicação (obrigatório)
- Campo: ISBN (opcional)
- Botão: "Salvar"
- Botão: "Cancelar"

### 5️⃣ **Tela de Edição de Livro** (`/books/:id/edit`)
- Igual ao formulário de cadastro
- Pré-preenchida com dados do livro
- Botão: "Atualizar" (em vez de "Salvar")

### 6️⃣ **Tela de Detalhes do Livro** (`/books/:id`)
- Exibição de todas as informações
- Apenas leitura
- Botões: "Editar", "Deletar", "Voltar"

---

## 🛠️ Tratamento de Erros

Todos os erros retornam neste formato:

```json
{
  "timestamp": "2026-05-05T21:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Mensagem de erro específica"
}
```

**Códigos principais:**
- `200` - Sucesso
- `201` - Criado com sucesso
- `204` - Deletado com sucesso
- `400` - Validação falhou
- `401` - Credenciais inválidas
- `404` - Não encontrado
- `409` - Email já cadastrado
- `500` - Erro do servidor

---

## 📦 Exemplo de Classe/Serviço do Frontend (JavaScript)

```javascript
class LibraryService {
  constructor(baseUrl = 'http://localhost:8080/api') {
    this.baseUrl = baseUrl;
  }

  // Autenticação
  async register(name, email, password, confirmPassword) {
    const res = await fetch(`${this.baseUrl}/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, email, password, confirmPassword })
    });
    return res.json();
  }

  async login(email, password) {
    const res = await fetch(`${this.baseUrl}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });
    const data = await res.json();
    localStorage.setItem('token', data.token);
    localStorage.setItem('userId', data.id);
    return data;
  }

  // Livros
  async getAllBooks() {
    const res = await fetch(`${this.baseUrl}/books`);
    return res.json();
  }

  async getBook(id) {
    const res = await fetch(`${this.baseUrl}/books/${id}`);
    return res.json();
  }

  async createBook(book) {
    const res = await fetch(`${this.baseUrl}/books`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(book)
    });
    return res.json();
  }

  async updateBook(id, book) {
    const res = await fetch(`${this.baseUrl}/books/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(book)
    });
    return res.json();
  }

  async deleteBook(id) {
    const res = await fetch(`${this.baseUrl}/books/${id}`, {
      method: 'DELETE'
    });
    return res.ok;
  }

  async searchByTitle(title) {
    const res = await fetch(
      `${this.baseUrl}/books/search/title?title=${title}`
    );
    return res.json();
  }

  async searchByAuthor(author) {
    const res = await fetch(
      `${this.baseUrl}/books/search/author?author=${author}`
    );
    return res.json();
  }
}

// Uso:
const service = new LibraryService();
const books = await service.getAllBooks();
```

---

## 🔄 Fluxo de Navegação Sugerido

```
┌─────────────────┐
│   Login/Home    │
├─────────────────┤
│ • Registrar     │
│ • Fazer Login   │
└────────┬────────┘
         │
    Login OK
         │
         ▼
┌─────────────────────────┐
│    Dashboard/Listagem   │
├─────────────────────────┤
│ • Listar todos livros   │
│ • Buscar por título     │
│ • Buscar por autor      │
│ • + Novo Livro (botão)  │
└────┬───────────┬────┬───┘
     │           │    │
  Novo       Editar Visualizar
   Livro      Livro   Livro
     │           │       │
     ▼           ▼       ▼
 Formulário  Formulário Detalhes
 Criar       Editar     Somente
                        Leitura
```

---

## 📝 Validações Recomendadas no Frontend

```javascript
const validateBook = (book) => {
  const errors = {};

  if (!book.title || book.title.trim() === '') {
    errors.title = 'Título é obrigatório';
  }

  if (!book.author || book.author.trim() === '') {
    errors.author = 'Autor é obrigatório';
  }

  if (!book.publicationYear) {
    errors.publicationYear = 'Ano é obrigatório';
  } else if (book.publicationYear < 0 || book.publicationYear > new Date().getFullYear()) {
    errors.publicationYear = 'Ano inválido';
  }

  return Object.keys(errors).length === 0 ? null : errors;
};
```

---

## 🚀 Deploy da Aplicação Completa

### Backend (já está pronto)
```bash
# Build
mvn clean package

# Executar
java -jar target/library-api-0.0.1-SNAPSHOT.jar
```

### Frontend (próximo passo)
- Implemente as 6 telas descritas
- Integre com os endpoints
- Deploy em servidor Node/Static

---

## 💡 Dicas Importantes

1. **Armazene o Token**: Após login, armazene em `localStorage` ou `sessionStorage`
2. **Feedback Visual**: Mostre loading durante requisições
3. **Tratamento de Erros**: Exiba mensagens amigáveis do backend
4. **Responsivo**: Torne a interface mobile-friendly
5. **Cache**: Considere caching local de dados

---

**Backend ✅ 100% Pronto | Frontend 🚀 Próximo Passo**

Qualquer dúvida, consulte `API_DOCUMENTATION.md` ou `QUICK_START.md`
