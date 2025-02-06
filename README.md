# 🛠️ Sistema de Gestão de Estoque Seguro 🔒

## 📝 Descrição do Projeto
O Sistema de Gestão de Estoque Seguro é uma aplicação robusta desenvolvida em Java 17 com Spring Boot 3.4.1. Seu objetivo é permitir um gerenciamento seguro e eficiente de produtos e usuários, garantindo a integridade e a confidencialidade dos dados por meio de boas práticas de segurança.

## 🚀 Principais Características:
- CRUD de produtos, acessível para Operadores e Administradores;
- CRUD de usuários, acessível apenas para Administradores;
- Validação dos campos de request;
- Integração com API VIA CEP para consulta de endereços;
- Autenticação segura baseada em JWT;
- Registro de auditoria para rastrear alterações no sistema;
- Testes unitários abrangentes para garantir a confiabilidade do sistema.
---
## ✨ Funcionalidades

### 🔐 Autenticação e Autorização
- Login seguro com tokens JWT; 
- Controle de acesso com diferentes níveis de permissão (Usuario Padrao,Admin e Operador); 
- Middleware de segurança com filtros JWT.

### 📦 Gerenciamento de Produtos
- Cadastro de novos produtos (Admin e Operador); 
- Edição e remoção (apenas Admin); 
- Listagem de produtos cadastrados.

### 👥 Gerenciamento de Usuários
- Cadastro de novos usuários (Admin); 
- Edição e remoção de usuários (Admin); 
- Consulta de usuários cadastrados (Admin).

### 🗺️ Busca de CEP via API Externa
- Consulta automática de endereços a partir do CEP fornecido.

### 🔄 Movimentações de Estoque [EM CONTSTRUÇÃO]
Registrar entradas e saídas com rastreamento de quem realizou a ação.

### 🧾 Registro de Auditoria [EM CONTSTRUÇÃO]
Histórico detalhado de alterações feitas no sistema.

### 📊 Relatórios
- Produtos com estoque crítico.
- Histórico de movimentações e ações realizadas por usuários.

---
## 🛠️ Tecnologias Utilizadas
- 💻 Java 11+: Linguagem principal do backend.
- ⚡ Spring Boot: Framework para desenvolvimento rápido de aplicações.
- 🗄️ Spring Data JPA: Persistência e ORM com Hibernate.
- 🛡️ Spring Security: Autenticação e autorização.
- 🔑 JWT: Tokens de autenticação para sessões seguras.
- 📚 MySQL: Banco de dados relacional.
- 🛠️ Maven: Gerenciamento de dependências.
- ✏️ Swagger (SpringDoc OpenAPI)

---
## 🔗 Endpoints da API
### 🔑 Autenticação
- `POST /auth/login` - Autentica um usuário e retorna um token JWT. 
- `POST /auth/register` - Cadastra um novo usuário (apenas Admin).

### 📦 Produtos
- `GET /produtos` - Lista todos os produtos. 
- `POST /produtos` - Cadastra um novo produto (Admin e Operador). 
- `PUT /produtos/{id}` - Atualiza um produto existente (apenas admins). 
- `DELETE /produtos/{id}` - Remove um produto (apenas admins).

### 🔄 Movimentações [EM CONTSTRUÇÃO]
- `POST /movimentacoes` - Registra entrada ou saída de estoque.

### 📊 Relatórios [EM CONTSTRUÇÃO]
- `GET /relatorios/estoque-baixo` - Lista produtos com estoque crítico. 
- `GET /relatorios/movimentacoes` - Histórico de movimentações.

### 🗺️ Busca de CEP

- `GET /api/buscar-cep/{cep}` - Retorna informações sobre o endereço baseado no CEP.
---
## 🛡️ Padrões de Desenvolvimento

### 📂 Estrutura do Projeto
Este projeto segue o padrão de commits baseado no Conventional Commits para facilitar o entendimento e rastreamento das alterações.
```
📦 inventory-management-system
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java
 ┃ ┃ ┃ ┣ 📂 br.com.sgm.inventory_management_system
 ┃ ┃ ┃ ┃ ┣ 📂 controller
 ┃ ┃ ┃ ┃ ┣ 📂 dto
 ┃ ┃ ┃ ┃ ┣ 📂 exceptions
 ┃ ┃ ┃ ┃ ┣ 📂 filter
 ┃ ┃ ┃ ┃ ┣ 📂 model
 ┃ ┃ ┃ ┃ ┣ 📂 repository
 ┃ ┃ ┃ ┃ ┣ 📂 security
 ┃ ┃ ┃ ┃ ┣ 📂 service
 ┃ ┃ ┃ ┃ ┗ 📂 util
 ┃ ┣ 📂 test

```
---

## 🧪 Testes
### 🔍 Testes Automatizados
- Testes unitários com JUnit e Mockito. 
- Testes de segurança simulando acessos indevidos. 
- Testes de integração para endpoints REST.

### 🛠️ Testes Manuais
- Verificação de fluxo completo usando Postman.

## 🔄 Padrão de Commits
Este projeto segue o **Conventional Commits** para padronização:

#### Tipos de Commits:
- `feat`: Adição de uma nova funcionalidade. 
- `fix`: Correção de bugs. 
- `docs`: Alterações na documentação. 
- `style`: Alterações que não afetam o código (formatação, espaçamento, etc.). 
- `refactor`: Refatorações que não alteram a funcionalidade. 
- `test`: Adição ou modificação de testes. 

## 🏗️ Padrões de Projeto
Este projeto utiliza padrões de projeto (design patterns) amplamente reconhecidos para garantir a manutenibilidade e qualidade do código.

- **[Singleton](https://refactoring.guru/pt-br/design-patterns/singleton)**: Para gerenciar instâncias únicas (ex.: configuração de segurança). 
- **[Factory](https://refactoring.guru/pt-br/design-patterns/factory-method)**: Para criação de objetos de forma controlada. 
- **[Repository](https://www.geeksforgeeks.org/repository-design-pattern/)**: Para abstrair operações no banco de dados. 
- **[Service Layer](https://java-design-patterns.com/patterns/service-layer/#programmatic-example-of-service-layer-pattern-in-java)**: Para concentrar regras de negócio e lógica da aplicação.

---
## 🗂️ Estrutura do Banco de Dados
### 📦 Produtos
- `id` - Identificador único. 
- `nome` - Nome do produto. 
- `quantidade` - Quantidade em estoque. 
- `preco` - Preço unitário. 
- `categoria_id` - Relacionamento com a categoria.

### 📂 Categorias
- `id` - Identificador único.
- `nome` - Nome da categoria.

### 🔄 Movimentações
- `id` - Identificador único.
- `produto_id` - Relacionamento com o produto.
- `tipo` - Entrada ou saída.
- `quantidade` - Quantidade movimentada.
- `usuario_id` - Usuário responsável pela movimentação.
- `data` - Data da movimentação.

### 🧾 Auditoria
- `id` - Identificador único.
- `acao` - Ação realizada.
- `usuario_id` - Usuário que realizou a ação.
- `data` - Data e hora da ação.
- `detalhes` - Informações adicionais.

---
## Documentação da API
A documentação da API é gerada automaticamente pelo Swagger (SpringDoc OpenAPI).

### Acessando o Swagger
Após iniciar o projeto, você pode acessar a documentação interativa em:
`http://localhost:8080/swagger-ui/index.html`

---
## 📝 Licença
Este projeto está licenciado sob a **GNU General Public License v3.0**.

Para mais detalhes, leia o arquivo **[LICENSE](LICENSE)** ou visite [GNU.org](https://www.gnu.org/).

---
### Contato
Para mais informações, entre em contato:
- **Desenvolvedor**: Samuel Martins
- **E-mail**: sgmartins777@gmail.com