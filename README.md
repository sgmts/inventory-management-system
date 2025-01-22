# 🛠️ Sistema de Gestão de Estoque Seguro 🔒

## 📝 Descrição do Projeto
O Sistema de Gestão de Estoque com Segurança é uma aplicação robusta desenvolvida em Java, projetada para gerenciar produtos, categorias e movimentações de estoque. 
  
🚀O foco do sistema é combinar eficiência operacional com boas práticas de segurança, garantindo a integridade e confidencialidade dos dados.

---
## ✨ Funcionalidades
### 📦 Gerenciamento de Produtos
- Cadastro;
- edição;
- exclusão; 
- listagem de produtos.

### 📂 Controle de Categorias
- Adicionar;
- editar;
- listar; 
- remover categorias de produtos.

### 🔄 Movimentações de Estoque
Registrar entradas e saídas com rastreamento de quem realizou a ação.

### 🔐 Autenticação e Autorização

- Login seguro com **tokens JWT**.
- Controle de permissões (admin, operador e visualizador).

### 🧾 Registro de Auditoria
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

---
## 🔗 Endpoints da API
### 🔑 Autenticação
- `POST /auth/login` - Login com e-mail e senha. 
- `POST /auth/register` - Registrar novos usuários.

### 📦 Produtos
- `GET /produtos` - Lista todos os produtos. 
- `POST /produtos` - Cadastra um novo produto (apenas admins). 
- `PUT /produtos/{id}` - Atualiza um produto existente (apenas admins). 
- `DELETE /produtos/{id}` - Remove um produto (apenas admins).

### 📂 Categorias
- `GET /categorias` - Lista todas as categorias. 
- `POST /categorias` - Adiciona uma nova categoria.

### 🔄 Movimentações
- `POST /movimentacoes` - Registra entrada ou saída de estoque.

### 📊 Relatórios
- `GET /relatorios/estoque-baixo` - Lista produtos com estoque crítico. 
- `GET /relatorios/movimentacoes` - Histórico de movimentações.

---
## 🛡️ Padrões de Desenvolvimento
### 🔄 Padrão de Commits
Este projeto segue o padrão de commits baseado no Conventional Commits para facilitar o entendimento e rastreamento das alterações.

#### Estrutura do Commit
```
<tipo>: <descrição>
```

#### Tipos de Commits:
- `feat`: Adição de uma nova funcionalidade. 
- `fix`: Correção de bugs. 
- `docs`: Alterações na documentação. 
- `style`: Alterações que não afetam o código (formatação, espaçamento, etc.). 
- `refactor`: Refatorações que não alteram a funcionalidade. 
- `test`: Adição ou modificação de testes. 
- `chore`: Alterações na configuração do projeto ou tarefas auxiliares.

### 🏗️ Padrões de Projeto
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
## 🧪 Testes
### 🔍 Testes Automatizados
- ✅ Testes unitários com JUnit para validação dos serviços. 
- ✅ Testes de integração para endpoints REST.

### 🛠️ Testes Manuais
- 🔄 Verificação de fluxo completo usando Postman. 
- 🔐 Testes de segurança simulando entradas maliciosas.

---
## 📝 Licença
Este projeto está licenciado sob a **GNU General Public License v3.0**.

Para mais detalhes, leia o arquivo **[LICENSE](LICENSE)** ou visite [GNU.org](https://www.gnu.org/).