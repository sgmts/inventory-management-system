-- Criando a tabela de enderecos se não existir
CREATE TABLE IF NOT EXISTS user_address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cep VARCHAR(8) NOT NULL,
    street VARCHAR(255),
    number VARCHAR(10) NOT NULL,
    address_complement VARCHAR(100),
    neighborhood VARCHAR(100),
    city VARCHAR(50),
    uf VARCHAR(2),
    region VARCHAR(20)
);

-- Criando a tabela de usuários se não existir
CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    cell_phone VARCHAR(15) NOT NULL,
    birth_date DATE NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role_enum ENUM('USER', 'ADMIN', 'OPERATOR') NOT NULL,
    user_address_id BIGINT,
    CONSTRAINT fk_user_address FOREIGN KEY (user_address_id) REFERENCES user_address(id)
);

-- Inserindo enderecos na base de dados
INSERT INTO user_address (cep, street, number, address_complement, neighborhood, city, uf, region)
VALUES
    ('30130010', 'Rua A',       '123', 'Apto 101',      'Centro',               'Belo Horizonte',   'MG', 'Sudeste'),
    ('01000000', 'Avenida B',   '456', 'Bloco B',       'Centro',               'São Paulo',        'SP', 'Sudeste'),
    ('64217430', 'Rua C',       '789', 'Casa',          'Pituba',               'Salvador',         'BA', 'Nordeste'),
    ('55192970', 'Rua D',       '101', 'Sala 5',        'Asa Sul',              'Brasília',         'DF', 'Centro-Oeste'),
    ('59014720', 'Rua E',       '202', 'Fundos',        'Batel',                'Curitiba',         'PR', 'Sul'),
    ('76810420', 'Rua F',       '303', 'Loja 2',        'Moinhos de Vento',     'Porto Alegre',     'RS', 'Sul'),
    ('53429305', 'Rua G',       '404', 'Apartamento 7', 'Renascença',           'São Luís',         'MA', 'Nordeste'),
    ('79092151', 'Rua H',       '505', 'Bloco C',       'Tambaú',               'João Pessoa',      'PB', 'Nordeste'),
    ('69317829', 'Rua I',       '606', 'Sala 10',       'Centro',               'Goiânia',          'GO', 'Centro-Oeste'),
    ('29102032', 'Rua J',       '707', 'Galpão',        'Centro',               'Manaus',           'AM', 'Norte');


-- Inserindo usuários iniciais com endereços vinculados
-- password criptografado: senhaFraca123
INSERT INTO user (name, email, password, cpf, cell_phone, birth_date, role_enum, user_address_id)
VALUES
    ('Admin1',      'admin1@email.com',     '$2a$10$4ymJFBbRi08lmtq3I6XymetAW2EYHvjMS.O8/76WRl/OvZEQKQOxq', '51662368070',   '31999990001', '1980-05-15', 'ADMIN',       1),
    ('Admin2',      'admin2@email.com',     '$2a$10$4ymJFBbRi08lmtq3I6XymetAW2EYHvjMS.O8/76WRl/OvZEQKQOxq', '01252626045',   '31999990002', '1982-07-20', 'ADMIN',       2),
    ('Admin3',      'admin3@email.com',     '$2a$10$4ymJFBbRi08lmtq3I6XymetAW2EYHvjMS.O8/76WRl/OvZEQKQOxq', '48578401034',   '31999990003', '1985-09-25', 'ADMIN',       3),
    ('User1',       'user1@email.com',      '$2a$10$4ymJFBbRi08lmtq3I6XymetAW2EYHvjMS.O8/76WRl/OvZEQKQOxq', '99838015075',   '31999990004', '1990-02-10', 'USER',        4),
    ('User2',       'user2@email.com',      '$2a$10$4ymJFBbRi08lmtq3I6XymetAW2EYHvjMS.O8/76WRl/OvZEQKQOxq', '55318796010',   '31999990005', '1992-03-15', 'USER',        5),
    ('User3',       'user3@email.com',      '$2a$10$4ymJFBbRi08lmtq3I6XymetAW2EYHvjMS.O8/76WRl/OvZEQKQOxq', '99921493027',   '31999990006', '1995-08-30', 'USER',        6),
    ('Operator1',   'operator1@email.com',  '$2a$10$4ymJFBbRi08lmtq3I6XymetAW2EYHvjMS.O8/76WRl/OvZEQKQOxq', '56436222030',   '31999990007', '1988-12-05', 'OPERATOR',    7),
    ('Operator2',   'operator2@email.com',  '$2a$10$4ymJFBbRi08lmtq3I6XymetAW2EYHvjMS.O8/76WRl/OvZEQKQOxq', '73134712032',   '31999990008', '1986-04-18', 'OPERATOR',    8),
    ('Operator3',   'operator3@email.com',  '$2a$10$4ymJFBbRi08lmtq3I6XymetAW2EYHvjMS.O8/76WRl/OvZEQKQOxq', '23833422033',   '31999990009', '1983-06-22', 'OPERATOR',    9),
    ('Operator4',   'operator4@email.com',  '$2a$10$4ymJFBbRi08lmtq3I6XymetAW2EYHvjMS.O8/76WRl/OvZEQKQOxq', '68830973092',   '31999990010', '1981-11-28', 'OPERATOR',    10);


-- Criando a tabela de categorias
CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500)
);

-- Criando a tabela de produtos
CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    bar_code VARCHAR(13) UNIQUE NOT NULL,
    stock_quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    supplier VARCHAR(100) NOT NULL,
    expiration_date DATE NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
);

-- Criando a tabela intermediária product_category para relacionamento N:N
CREATE TABLE IF NOT EXISTS product_category (
    product_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

-- Inserindo categorias
INSERT INTO category (name, description) VALUES
    ('Eletrônicos', 'Produtos de tecnologia e informática'),
    ('Móveis', 'Itens para casa e escritório'),
    ('Acessórios', 'Itens complementares para eletrônicos'),
    ('Automotivo', 'Produtos para veículos e acessórios automotivos'),
    ('Beleza', 'Cosméticos e produtos de cuidados pessoais'),
    ('Alimentos', 'Produtos alimentícios e bebidas'),
    ('Esportes', 'Equipamentos esportivos e roupas fitness'),
    ('Brinquedos', 'Jogos e brinquedos para todas as idades'),
    ('Ferramentas', 'Ferramentas elétricas e manuais'),
    ('Livros', 'Livros e materiais educativos');

-- Inserindo produtos
INSERT INTO product (name, description, bar_code, stock_quantity, price, supplier, expiration_date)
VALUES
    ('Notebook Dell XPS', 'Ultrabook de alta performance', '1234567890123', 10, 7599.99, 'Dell', '2026-12-31'),
    ('Cadeira Gamer', 'Conforto e ergonomia para jogos', '9876543210123', 50, 1299.90, 'DXRacer', '2025-08-20'),
    ('Fone de Ouvido Bluetooth', 'Som de alta qualidade', '3216549870123', 30, 299.90, 'JBL', '2026-05-10'),
    ('Câmera DSLR Canon', 'Fotografia profissional', '7418529630123', 15, 4599.00, 'Canon', '2026-07-15'),
    ('Teclado Mecânico RGB', 'Switches para jogos', '8529637410123', 40, 699.99, 'Logitech', '2025-11-25'),
    ('Mesa de Escritório', 'Móvel para home office', '9517538520123', 20, 899.90, 'Tok&Stok', '2027-01-01'),
    ('Câmera de Segurança', 'Monitoramento residencial', '7539518520123', 25, 399.90, 'Intelbras', '2026-09-30'),
    ('Patins Inline', 'Alta velocidade e conforto', '3571598520123', 18, 499.90, 'FILA', '2025-12-31'),
    ('Kit de Ferramentas', 'Maleta com chaves e alicates', '1593578520123', 35, 279.99, 'Bosch', '2027-03-20'),
    ('Livro de Java', 'Programação para iniciantes', '8521479630123', 100, 89.90, 'OReilly', '2030-01-01');

-- Relacionando produtos com categorias (M:N)
INSERT INTO product_category (product_id, category_id) VALUES
    (1, 1),  -- Notebook Dell XPS → Eletrônicos
    (2, 2),  -- Cadeira Gamer → Móveis
    (2, 1),  -- Cadeira Gamer → Eletrônicos
    (3, 1),  -- Fone de Ouvido → Eletrônicos
    (3, 3),  -- Fone de Ouvido → Acessórios
    (4, 1),  -- Câmera DSLR → Eletrônicos
    (5, 1),  -- Teclado Mecânico → Eletrônicos
    (6, 2),  -- Mesa de Escritório → Móveis
    (7, 1),  -- Câmera de Segurança → Eletrônicos
    (7, 4),  -- Câmera de Segurança → Automotivo
    (8, 7),  -- Patins → Esportes
    (9, 9),  -- Kit de Ferramentas → Ferramentas
    (10, 10); -- Livro de Java → Livros
