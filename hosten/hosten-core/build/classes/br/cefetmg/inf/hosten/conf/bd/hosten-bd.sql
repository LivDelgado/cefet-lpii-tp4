

-- Create tables section -------------------------------------------------

-- Table Programa

CREATE TABLE Programa(
 codPrograma Character(3) NOT NULL,
 desPrograma Character varying(200) NOT NULL
)
;

-- Add keys for table Programa

ALTER TABLE Programa ADD CONSTRAINT Key1 PRIMARY KEY (codPrograma)
;

-- Table Cargo

CREATE TABLE Cargo(
 codCargo Character(3) NOT NULL,
 nomCargo Character varying(40) NOT NULL,
 idtMaster Boolean NOT NULL
)
;

-- Add keys for table Cargo

ALTER TABLE Cargo ADD CONSTRAINT Key2 PRIMARY KEY (codCargo)
;

-- Table CargoPrograma

CREATE TABLE CargoPrograma(
 codPrograma Character(3) NOT NULL,
 codCargo Character(3) NOT NULL
)
;

-- Add keys for table CargoPrograma

ALTER TABLE CargoPrograma ADD CONSTRAINT Key3 PRIMARY KEY (codPrograma,codCargo)
;

-- Table Usuario

CREATE TABLE Usuario(
 codUsuario Character(4) NOT NULL,
 nomUsuario Character varying(90) NOT NULL,
 codCargo Character(3) NOT NULL,
 desSenha Character(64),
 desEmail Character varying(60)
)
;

-- Create indexes for table Usuario

CREATE INDEX IX_Relationship6 ON Usuario (codCargo)
;

-- Add keys for table Usuario

ALTER TABLE Usuario ADD CONSTRAINT Key5 PRIMARY KEY (codUsuario)
;

-- Table Quarto

CREATE TABLE Quarto(
 nroQuarto Smallint NOT NULL,
 codCategoria Character(3) NOT NULL,
 idtOcupado Boolean
)
;

-- Create indexes for table Quarto

CREATE INDEX IX_Relationship9 ON Quarto (codCategoria)
;

-- Add keys for table Quarto

ALTER TABLE Quarto ADD CONSTRAINT Key7 PRIMARY KEY (nroQuarto)
;

-- Table Categoria

CREATE TABLE Categoria(
 codCategoria Character(3) NOT NULL,
 nomCategoria Character varying(40) NOT NULL,
 vlrDiaria Numeric(7,2) NOT NULL
)
;

-- Add keys for table Categoria

ALTER TABLE Categoria ADD CONSTRAINT Key8 PRIMARY KEY (codCategoria)
;

-- Table ItemConforto

CREATE TABLE ItemConforto(
 codItem Character(3) NOT NULL,
 desItem Character varying(40) NOT NULL
)
;

-- Add keys for table ItemConforto

ALTER TABLE ItemConforto ADD CONSTRAINT Key9 PRIMARY KEY (codItem)
;

-- Table CategoriaItemConforto

CREATE TABLE CategoriaItemConforto(
 codCategoria Character(3) NOT NULL,
 codItem Character(3) NOT NULL
)
;

-- Add keys for table CategoriaItemConforto

ALTER TABLE CategoriaItemConforto ADD CONSTRAINT Key10 PRIMARY KEY (codCategoria,codItem)
;

-- Table Hospede

CREATE TABLE Hospede(
 codCPF Character(11) NOT NULL,
 nomHospede Character varying(90) NOT NULL,
 desTelefone Character varying(15) NOT NULL,
 desEmail Character varying(90) NOT NULL
)
;

-- Add keys for table Hospede

ALTER TABLE Hospede ADD CONSTRAINT Key11 PRIMARY KEY (codCPF)
;

-- Table Hospedagem

CREATE TABLE Hospedagem(
 seqHospedagem Serial NOT NULL,
 datCheckIn Timestamp NOT NULL,
 datCheckOut Timestamp,
 vlrPago Numeric(7,2),
 codCPF Character(11)
)
;

-- Create indexes for table Hospedagem

CREATE INDEX IX_Relationship26 ON Hospedagem (codCPF)
;

-- Add keys for table Hospedagem

ALTER TABLE Hospedagem ADD CONSTRAINT Key14 PRIMARY KEY (seqHospedagem)
;

-- Table Servico

CREATE TABLE Servico(
 seqServico Smallserial NOT NULL,
 desServico Character varying(40) NOT NULL,
 vlrUnit Numeric(7,2) NOT NULL,
 codServicoArea Character(3) NOT NULL
)
;

-- Create indexes for table Servico

CREATE INDEX IX_Relationship20 ON Servico (codServicoArea)
;

-- Add keys for table Servico

ALTER TABLE Servico ADD CONSTRAINT Key15 PRIMARY KEY (seqServico)
;

-- Table ServicoArea

CREATE TABLE ServicoArea(
 codServicoArea Character(3) NOT NULL,
 nomServicoArea Character varying(40) NOT NULL
)
;

-- Add keys for table ServicoArea

ALTER TABLE ServicoArea ADD CONSTRAINT Key16 PRIMARY KEY (codServicoArea)
;

-- Table QuartoConsumo

CREATE TABLE QuartoConsumo(
 seqHospedagem Integer NOT NULL,
 nroQuarto Smallint NOT NULL,
 datConsumo Timestamp NOT NULL,
 qtdConsumo Smallint NOT NULL,
 seqServico Smallint NOT NULL,
 codUsuarioRegistro Character(4)
)
;

-- Create indexes for table QuartoConsumo

CREATE INDEX IX_Relationship16 ON QuartoConsumo (seqServico)
;

CREATE INDEX IX_Relationship17 ON QuartoConsumo (codUsuarioRegistro)
;

-- Add keys for table QuartoConsumo

ALTER TABLE QuartoConsumo ADD CONSTRAINT Key17 PRIMARY KEY (datConsumo,seqHospedagem,nroQuarto)
;

-- Table QuartoHospedagem

CREATE TABLE QuartoHospedagem(
 seqHospedagem Integer NOT NULL,
 nroQuarto Smallint NOT NULL,
 nroAdultos Smallint NOT NULL,
 nroCriancas Smallint NOT NULL,
 vlrDiaria Numeric(7,2) NOT NULL
)
;

-- Add keys for table QuartoHospedagem

ALTER TABLE QuartoHospedagem ADD CONSTRAINT Key18 PRIMARY KEY (seqHospedagem,nroQuarto)
;
-- Create foreign keys (relationships) section -------------------------------------------------

ALTER TABLE CargoPrograma ADD CONSTRAINT Relationship2 FOREIGN KEY (codPrograma) REFERENCES Programa (codPrograma) ON DELETE NO ACTION ON UPDATE CASCADE
;

ALTER TABLE CargoPrograma ADD CONSTRAINT Relationship3 FOREIGN KEY (codCargo) REFERENCES Cargo (codCargo) ON DELETE NO ACTION ON UPDATE CASCADE
;

ALTER TABLE Usuario ADD CONSTRAINT Relationship6 FOREIGN KEY (codCargo) REFERENCES Cargo (codCargo) ON DELETE NO ACTION ON UPDATE CASCADE
;

ALTER TABLE CategoriaItemConforto ADD CONSTRAINT Relationship7 FOREIGN KEY (codCategoria) REFERENCES Categoria (codCategoria) ON DELETE NO ACTION ON UPDATE CASCADE
;

ALTER TABLE CategoriaItemConforto ADD CONSTRAINT Relationship8 FOREIGN KEY (codItem) REFERENCES ItemConforto (codItem) ON DELETE NO ACTION ON UPDATE CASCADE
;

ALTER TABLE Quarto ADD CONSTRAINT Relationship9 FOREIGN KEY (codCategoria) REFERENCES Categoria (codCategoria) ON DELETE NO ACTION ON UPDATE CASCADE
;

ALTER TABLE QuartoConsumo ADD CONSTRAINT Relationship16 FOREIGN KEY (seqServico) REFERENCES Servico (seqServico) ON DELETE NO ACTION ON UPDATE CASCADE
;

ALTER TABLE QuartoConsumo ADD CONSTRAINT Relationship17 FOREIGN KEY (codUsuarioRegistro) REFERENCES Usuario (codUsuario) ON DELETE NO ACTION ON UPDATE CASCADE
;

ALTER TABLE Servico ADD CONSTRAINT Relationship20 FOREIGN KEY (codServicoArea) REFERENCES ServicoArea (codServicoArea) ON DELETE NO ACTION ON UPDATE CASCADE
;

ALTER TABLE QuartoHospedagem ADD CONSTRAINT Relationship21 FOREIGN KEY (seqHospedagem) REFERENCES Hospedagem (seqHospedagem) ON DELETE NO ACTION ON UPDATE CASCADE
;

ALTER TABLE QuartoHospedagem ADD CONSTRAINT Relationship22 FOREIGN KEY (nroQuarto) REFERENCES Quarto (nroQuarto) ON DELETE NO ACTION ON UPDATE CASCADE
;

ALTER TABLE QuartoConsumo ADD CONSTRAINT Relationship24 FOREIGN KEY (seqHospedagem, nroQuarto) REFERENCES QuartoHospedagem (seqHospedagem, nroQuarto) ON DELETE NO ACTION ON UPDATE CASCADE
;

ALTER TABLE Hospedagem ADD CONSTRAINT Relationship26 FOREIGN KEY (codCPF) REFERENCES Hospede (codCPF) ON DELETE NO ACTION ON UPDATE CASCADE
;

CREATE VIEW relatorioDespesas AS
SELECT A.seqHospedagem, A.nroQuarto, A.nroAdultos, A.nroCriancas, A.vlrDiaria,
       B.datCheckIn, B.datCheckOut, B.vlrPago,
       C.nomHospede,
       D.seqServico, D.qtdConsumo,
       E.desServico, E.vlrUnit
FROM
	QuartoHospedagem A
	JOIN Hospedagem B ON A.seqHospedagem = B.seqHospedagem
	JOIN Hospede C ON B.codCPF = C.codCPF
	JOIN QuartoConsumo D ON A.seqHospedagem = D.seqHospedagem AND A.nroQuarto = D.nroQuarto
	JOIN Servico E ON D.seqServico = E.seqServico;

INSERT INTO public.programa(codprograma, desprograma)
    VALUES ('001', 'Tela de Cargos');
INSERT INTO public.programa(codprograma, desprograma)
    VALUES ('002', 'Tela de Check-in');
INSERT INTO public.programa(codprograma, desprograma)
    VALUES ('003', 'Tela de Check-out');
INSERT INTO public.programa(codprograma, desprograma)
    VALUES ('004', 'Tela de Visualização de Detalhes da Conta');
INSERT INTO public.programa(codprograma, desprograma)
    VALUES ('005', 'Tela de Lançamento de Despesas');
INSERT INTO public.programa(codprograma, desprograma)
    VALUES ('006', 'Tela de Hóspedes');
INSERT INTO public.programa(codprograma, desprograma)
    VALUES ('007', 'Tela de Funcionários');
INSERT INTO public.programa(codprograma, desprograma)
    VALUES ('008', 'Tela de Itens de Conforto');
INSERT INTO public.programa(codprograma, desprograma)
    VALUES ('009', 'Tela de Categorias de Quartos');
INSERT INTO public.programa(codprograma, desprograma)
    VALUES ('010', 'Tela de Quartos');
INSERT INTO public.programa(codprograma, desprograma)
    VALUES ('011', 'Tela de Áreas de Serviço');
INSERT INTO public.programa(codprograma, desprograma)
    VALUES ('012', 'Tela de Serviços');
