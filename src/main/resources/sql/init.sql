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

-- Criando a tabela de produtos se não existir
CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    barCode VARCHAR(13) UNIQUE NOT NULL,
    stockQuantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    category_enum ENUM('CAT_A', 'CAT_B', 'CAT_C') NOT NULL,
    supplier VARCHAR(100) NOT NULL,
    expirationDate DATE NOT NULL,
    registrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
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

INSERT INTO product (name, description, barCode, stockQuantity, price, category_enum, supplier, expirationDate)
VALUES
    ('Notebook Dell XPS',               'Ultrabook de alto desempenho',                 '1234567890123',    10,    7599.99,   'CAT_A', 'Dell Brasil',     '2026-12-31'),
    ('Smartphone Samsung Galaxy S24',   'Modelo mais recente da linha Galaxy',          '9876543210123',    25,    4999.99,   'CAT_B', 'Samsung Brasil',  '2025-08-20'),
    ('Cadeira Gamer',                   'Conforto e ergonomia para jogos e trabalho',   '3216549870123',    50,    1299.90,   'CAT_C', 'DXRacer',         '2027-01-10'),
    ('Monitor LG 27"',                  'Monitor IPS 4K Ultra HD',                      '7418529630123',    15,    2399.50,   'CAT_A', 'LG Electronics',  '2026-05-15'),
    ('Teclado Mecânico Logitech',       'Switches Red para melhor resposta',            '8529637410123',    30,    699.99,    'CAT_B', 'Logitech',        '2025-11-25');
