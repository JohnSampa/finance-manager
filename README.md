# Finance Manager API

API REST para gerenciamento financeiro pessoal: controle de gastos, receitas, contas bancárias e categorias, com autenticação segura via JWT.

![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

---

## Tecnologias

- Java 21
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- MapStruct
- OpenFeign
- Swagger / OpenAPI
- Lombok
- Jakarta Validation

---

## Como rodar localmente

### Pré-requisitos

- Java 21
- PostgreSQL rodando localmente

### Configuração

Clone o repositório:

```bash
git clone https://github.com/JohnSampa/finance-manager
cd finance-manager
```

Configure as variáveis no `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_manager
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

api.security.token.secret=seu_secret_jwt
```

Suba a aplicação:

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## Documentação

Com a aplicação rodando, acesse a documentação completa dos endpoints via Swagger:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Autenticação

A API utiliza autenticação stateless com JWT. As únicas rotas públicas são as de registro e login.

### Registro

```http
POST /auth/register
Content-Type: application/json

{
  "name": "Jonathan",
  "email": "jonathan@email.com",
  "cpf": "000.000.000-00",
  "zipcode": "44000-000",
  "addressNumber": "123",
  "password": "senha123"
}
```

### Login

```http
POST /auth/login
Content-Type: application/json

{
  "email": "jonathan@email.com",
  "password": "senha123"
}
```

Resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer"
}
```

Todas as demais requisições devem incluir o header:

```
Authorization: Bearer {token}
```

---

## Endpoints

### Autenticação

| Método | Rota             | Descrição          | Auth |
|--------|------------------|--------------------|------|
| POST   | /auth/register   | Cadastro de usuário | Não  |
| POST   | /auth/login      | Login               | Não  |

### Usuários

| Método | Rota          | Descrição              | Auth |
|--------|---------------|------------------------|------|
| GET    | /users        | Listar usuários         | Sim  |
| GET    | /users/{id}   | Buscar usuário por ID   | Sim  |
| PUT    | /users/{id}   | Atualizar usuário       | Sim  |
| DELETE | /users/{id}   | Deletar usuário         | Sim  |

### Contas

| Método | Rota                      | Descrição              | Auth |
|--------|---------------------------|------------------------|------|
| GET    | /accounts                 | Listar contas           | Sim  |
| POST   | /accounts                 | Criar conta             | Sim  |
| GET    | /accounts/{id}            | Buscar conta por ID     | Sim  |
| DELETE | /accounts/{id}            | Deletar conta           | Sim  |
| POST   | /accounts/{id}/deposit    | Depositar valor         | Sim  |
| POST   | /accounts/{id}/withdraw   | Sacar valor             | Sim  |

### Gastos (Expenses)

| Método | Rota                      | Descrição                     | Auth |
|--------|---------------------------|-------------------------------|------|
| GET    | /expenses                 | Listar gastos (filtros opcionais) | Sim  |
| POST   | /expenses                 | Registrar gasto               | Sim  |
| GET    | /expenses/{id}            | Buscar gasto por ID           | Sim  |
| POST   | /expenses/{id}/confirm    | Confirmar gasto               | Sim  |
| DELETE | /expenses/{id}            | Deletar gasto                 | Sim  |

Filtros disponíveis em `GET /expenses`: `categoryId`, `date`, `status` (`planned`, `pending_confirmation`, `confirmed`, `deleted`).

### Receitas (Earnings)

| Método | Rota                      | Descrição                     | Auth |
|--------|---------------------------|-------------------------------|------|
| GET    | /earnings                 | Listar receitas (filtros opcionais) | Sim  |
| POST   | /earnings                 | Registrar receita             | Sim  |
| GET    | /earnings/{id}            | Buscar receita por ID         | Sim  |
| POST   | /earnings/{id}/confirm    | Confirmar receita             | Sim  |
| DELETE | /earnings                 | Deletar receita               | Sim  |

### Categorias

| Método | Rota                          | Descrição                          | Auth |
|--------|-------------------------------|------------------------------------|------|
| GET    | /categories                   | Listar categorias                  | Sim  |
| POST   | /categories                   | Criar categoria                    | Sim  |
| GET    | /categories/{id}              | Buscar categoria por ID            | Sim  |
| PUT    | /categories/{id}              | Atualizar categoria                | Sim  |
| DELETE | /categories/{id}              | Deletar categoria                  | Sim  |
| GET    | /categories/{id}/expenses     | Listar gastos da categoria         | Sim  |
| GET    | /categories/{id}/earnings     | Listar receitas da categoria       | Sim  |

---

## Decisões técnicas

**UUID externo + Long interno**
Os recursos são expostos na API com UUID, enquanto o banco de dados utiliza Long como chave primária. Isso mantém a performance nas queries e protege contra enumeração de recursos (IDOR).

**Bloqueio de login por tentativas**
Após múltiplas tentativas de login sem sucesso, o usuário é bloqueado temporariamente e a API retorna `429 Too Many Requests`. A lógica está integrada diretamente ao fluxo do Spring Security.

**GlobalExceptionHandler**
Todas as exceções da aplicação são tratadas de forma centralizada, garantindo respostas de erro padronizadas em todos os endpoints.

**Separação de DTOs com MapStruct**
O mapeamento entre entidades e DTOs de request/response é feito via MapStruct, mantendo as camadas bem separadas e evitando exposição desnecessária de dados internos.

**Validação na entrada**
Jakarta Validation é aplicado nos DTOs de request, impedindo que dados inválidos cheguem à camada de serviço e reduzindo a ocorrência de exceções desnecessárias.

**Integração com ViaCEP**
O endereço do usuário é preenchido automaticamente a partir do CEP informado no cadastro, via integração com a API do ViaCEP usando OpenFeign.

---

## Contato

Jonathan Sampaio — [aurijona192@gmail.com](mailto:aurijona192@gmail.com)
