-- phpMyAdmin SQL Dump
-- version 4.6.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 23-Nov-2016 às 08:08
-- Versão do servidor: 5.7.12
-- PHP Version: 5.6.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_spcultura`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_agenda`
--

CREATE TABLE `tb_agenda` (
  `idAgenda` int(5) NOT NULL,
  `idEvento` int(5) NOT NULL,
  `usuarioEvento` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_agenda`
--

INSERT INTO `tb_agenda` (`idAgenda`, `idEvento`, `usuarioEvento`) VALUES
(4, 1, 'Sena');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_eventos`
--

CREATE TABLE `tb_eventos` (
  `idEvento` int(5) NOT NULL,
  `idLocal` int(5) NOT NULL,
  `nomeEvento` varchar(80) NOT NULL,
  `diaEvento` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_eventos`
--

INSERT INTO `tb_eventos` (`idEvento`, `idLocal`, `nomeEvento`, `diaEvento`) VALUES
(1, 1, 'Reveion', '2017-01-01 00:00:00');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_favoritos`
--

CREATE TABLE `tb_favoritos` (
  `idFavoritos` int(5) NOT NULL,
  `idLocal` int(5) NOT NULL,
  `userUsuario` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_favoritos`
--

INSERT INTO `tb_favoritos` (`idFavoritos`, `idLocal`, `userUsuario`) VALUES
(11, 10, 'Sena');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_locais`
--

CREATE TABLE `tb_locais` (
  `idLocal` int(5) NOT NULL,
  `nomeLocal` varchar(80) NOT NULL,
  `endLocal` varchar(150) NOT NULL,
  `contatoLocal` varchar(100) DEFAULT NULL,
  `dispLocal` varchar(20) NOT NULL,
  `periodoLocal` varchar(20) NOT NULL,
  `descLocal` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_locais`
--

INSERT INTO `tb_locais` (`idLocal`, `nomeLocal`, `endLocal`, `contatoLocal`, `dispLocal`, `periodoLocal`, `descLocal`) VALUES
(1, 'Catavento – Espaço Cultural da Criança', 'Palácio das Indústrias – Praça Cívica Ulisses Guimarães, Parque Dom Pedro II', '(11) 3315-0051', 'pago', 'diurno', 'Localizado no centro de São Paulo, o Catavento Cultural e Educacional é bem popular entre o público infantojuvenil, o espaço busca alimentar o visitante de informação de uma forma lúdica por meio de exposições e atividades interativas.'),
(2, 'Museu Afro Brasil', 'R. Pedro Álvares Cabral, Parque do Ibirapuera, portão 10', '(11) 3320-8900', 'pago', 'diurno', 'O museu possui um rico acervo com mais de cinco mil obras representando a cultura africana e afro-brasileira. O museu fica no Pavilhão Padre Manoel da Nóbrega, um dos edifícios integrantes do conjunto arquitetônico do Parque Ibirapuera, projetado por Oscar Niemeyer na década de 1950.'),
(3, 'Museu da Casa Brasileira', 'Av. Brigadeiro Faria Lima, 2.705, Jardim Paulistano', '(11) 3032-3727 / 3032-2564', 'pago', 'diurno', 'O único museu do país especializado em design e arquitetura está abrigado em um casarão da década de 40, construído para abrigar o antigo prefeito da cidade Fábio da Silva Prado. Além do amplo jardim com mais de seis mil m², que permite momentos de reflexão e calma, o visitante também encontra o simpático restaurante Quinta do Museu, no qual é oferecida seleção de pratos da culinária brasileira e internacional.'),
(4, 'Museu da Imagem e do Som', 'Av. Europa, 158, Jardim Europa', '(11) 2117-4777', 'gratuito', 'diurno', 'Um dos museus mais populares das cidades, o MIS (Museu de Imagem e do Som) possuí um acervo de coletas e principais registros representativos da cultura brasileira. O espaço oferece uma mistura de obras que variam em vídeos, fotos, músicas e grandes exposições.'),
(5, 'Museu da Língua Portuguesa', 'Praça da Luz, s/nº, Luz', '(11) 3322-0080', 'intenditado', 'interditado', 'Inaugurado em 2006 na Estação da Luz, o museu permite que seus visitantes façam uma viagem pelo mundo das palavras. Entre as atrações, uma linha do tempo onde é possível conhecer a origem da língua, um jogo que estimula a criação de palavras e um mapa onde é possível ver e ouvir depoimentos de moradores de todas as regiões do Brasil.'),
(6, 'Centro Cultural Banco do Brasil (CCBB)', 'Rua Álvares Penteado, 112 – Centro', ' (11) 3113-3651//CEP: 01012-000', 'gratuito', 'diurno', 'Localizado no coração histórico da cidade, numa via hoje de pedestres, o edifício foi comprado em 1923 pelo Banco do Brasil. A construção foi inteiramente reformada para abrigar o CCBB São Paulo. Os elementos originais foram restaurados, mantendo assim as linhas que o tornam um dos mais significativos exemplos da arquitetura do início do século.'),
(7, 'Museu de Arte Sacra', 'Av. Tiradentes, 676, Luz', '(11) 3326-3336', 'pago', 'diurno', 'Localizado na Avenida Tiradentes, no centro de São Paulo, o museu expõe objetivos religiosos de valor estético ou histórico.'),
(8, 'Museu do Futebol', 'Praça Charles Miller, s/nº, Estádio Paulo Machado de Carvalho (Pacaembu)', '(11) 3664-3848', 'diurno', 'pago', 'Para os apaixonados pelo esporte, o Museu do Futebol foi inaugurado o Museu do Futebol, dentro do estádio do Pacaembu. Quem visita o local pode conferir curiosidades sobre o futebol, narradores e até treinar um chute virtual. É um dos museus mais tecnológicos da cidade, além de muito interativo.'),
(9, 'Pinacoteca', 'Praça da Luz, 2, Luz', '(11) 3324-1000', 'pago', 'diurno', 'Localizada próxima à estação da Luz, a Pinacoteca recebe exposições temporárias, além do rico acervo permanente.'),
(10, 'Museu da Imigração', 'Rua Visconde de Parnaíba, 1316, Mooca', '(11) 3311-7700 / 2692-1866 / 2692-9218', 'pago', 'diurno', 'Gratuito aos sábados, o museu preserva a memórias dos imigrantes que chegaram ao Brasil entre o século XIX e XX, Localizado na Mooca.'),
(11, 'Museu de Arte Contemporânea de São Paulo', 'Rua da Praça do Relógio, 160 – Cidade Universitária', '(11) 3091-3039', 'Dado não disponivel', 'diurno', 'Um nome de destaque da arte contemporânea e moderna em toda América Latina, o Museu de Arte Contemporânea, ou MAC possui cerca de dez mil obras em seu acervo. Exposições temporárias ao longo do ano descortinam óleos, desenhos, gravuras, esculturas, objetos e trabalhos conceituais de grandes nomes do século XX como Picasso, Matisse, Miró, Kandinsky, Modigliani, Calder, Braque, Henry Moore, Tarsila do Amaral, Di Cavalcanti, Volpi, Brecheret, Flávio de Carvalho , Manabu Mabe, Antonio Dias e Regina Silveira, entre outros.'),
(12, 'Museu Brasileiro de Escultura (MUBE)', 'Av. Europa, 218', '(11) 2594-2601', 'Dado não disponível', 'diurno', 'Inaugurado há 15 anos, o prédio de arquitetura moderna já abrigou mais de 160 exposições com nomes de destaque como Brecheret, De Chirico, Max Ernst, Kcho, Bernar Venet, Benjamim, Luchesi e Bento, Claude Viallat, Marcos Lodola, DEV, Juan Diego Miguel, Jô Soares entre outros.'),
(13, 'Museu de Arte de São Paulo Assis Chateaubriand (Masp)', 'Avenida Paulista, 1578 – Bela Vista', '(11) 3251-5644', 'pago', 'diurno', 'Localizado no coração da cidade, Avenida Paulista, o Masp – Museu de Arte de São Paulo Assis Chateaubriand – é um dos mais importantes museus do hemisfério Sul e um dos principais cartões-postais da cidade. Está na lista das dez atrações turísticas mais visitadas de São Paulo e, às terças, oferece visita gratuita ao público em geral. Nos demais dias, de quarta a domingo, o ingresso custa R$ 15.'),
(14, 'Museu da Cultura – PUC', 'Rua Monte Alegre 984, Prédio Sede – Perdizes – zona Oeste', '(11) 3670-8559/ (11) 3670-8331', 'Dado não disponível', 'diurno', 'Com o intuito de criar, preservar, pesquisar e expor acervos que ativem memórias e imaginários, o Museu da Cultura, situado na Universidade Puc-SP,funciona de segunda a sexta-feira, das 14h às 19h, com atividades culturais, acadêmicas e acervo de coleções: indígenas, temáticas, fotos e vídeos.'),
(15, 'Museu Belas Artes de São Paulo', 'Rua Dr. Álvaro Alvim, 76 – Vila Mariana – zona Sul', '(11) 5576-7300', 'gratuito', 'diurno', 'Também conhecido como Museu Universitário, o MuBA é vinculado ao Centro Universitário Belas Artes de São Paulo, uma das mais tradicionais instituições de ensino superior voltadas ao fazer artístico no Brasil,  e conta com quatro galerias – Galeria do Acervo, Galeria Vicente Di Grado, Galeria 13 e Galeria do Núcleo de Design – em que são realizadas mostras permanentes, de média duração e temporárias.'),
(16, 'Museu Aberto de Arte Urbana de São Paulo', 'Av. Cruzeiro do Sul – Santana – zona Norte', 'Sem contato', 'gratuito', '24hrs', 'São Paulo também possuí um dos primeiros museus a céu aberto do mundo, no bairro Santana. A região já era considerada para muitos como o berço do grafite paulistano e hoje abriga o MAAU.');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_usuario`
--

CREATE TABLE `tb_usuario` (
  `nomeUsuario` varchar(80) NOT NULL,
  `userUsuario` varchar(20) NOT NULL,
  `emailUsuario` varchar(120) NOT NULL,
  `senhaUsuario` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_usuario`
--

INSERT INTO `tb_usuario` (`nomeUsuario`, `userUsuario`, `emailUsuario`, `senhaUsuario`) VALUES
('Matheus', 'Sena', '123@123.com', '123'),
('Nome', 'Usuario', 'Email', 'senha'),
('nome', 'xxxxx', 'email', 'xxxxx');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_agenda`
--
ALTER TABLE `tb_agenda`
  ADD PRIMARY KEY (`idAgenda`),
  ADD KEY `usuarioEvento` (`usuarioEvento`),
  ADD KEY `idEvento` (`idEvento`);

--
-- Indexes for table `tb_eventos`
--
ALTER TABLE `tb_eventos`
  ADD PRIMARY KEY (`idEvento`),
  ADD KEY `idLocal` (`idLocal`);

--
-- Indexes for table `tb_favoritos`
--
ALTER TABLE `tb_favoritos`
  ADD PRIMARY KEY (`idFavoritos`),
  ADD KEY `idLocal` (`idLocal`),
  ADD KEY `userUsuario` (`userUsuario`);

--
-- Indexes for table `tb_locais`
--
ALTER TABLE `tb_locais`
  ADD PRIMARY KEY (`idLocal`);

--
-- Indexes for table `tb_usuario`
--
ALTER TABLE `tb_usuario`
  ADD PRIMARY KEY (`userUsuario`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_agenda`
--
ALTER TABLE `tb_agenda`
  MODIFY `idAgenda` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tb_eventos`
--
ALTER TABLE `tb_eventos`
  MODIFY `idEvento` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `tb_favoritos`
--
ALTER TABLE `tb_favoritos`
  MODIFY `idFavoritos` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `tb_locais`
--
ALTER TABLE `tb_locais`
  MODIFY `idLocal` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `tb_agenda`
--
ALTER TABLE `tb_agenda`
  ADD CONSTRAINT `tb_agenda_ibfk_1` FOREIGN KEY (`usuarioEvento`) REFERENCES `tb_usuario` (`userUsuario`),
  ADD CONSTRAINT `tb_agenda_ibfk_2` FOREIGN KEY (`idEvento`) REFERENCES `tb_eventos` (`idEvento`);

--
-- Limitadores para a tabela `tb_eventos`
--
ALTER TABLE `tb_eventos`
  ADD CONSTRAINT `tb_eventos_ibfk_1` FOREIGN KEY (`idLocal`) REFERENCES `tb_locais` (`idLocal`);

--
-- Limitadores para a tabela `tb_favoritos`
--
ALTER TABLE `tb_favoritos`
  ADD CONSTRAINT `tb_favoritos_ibfk_1` FOREIGN KEY (`idLocal`) REFERENCES `tb_locais` (`idLocal`),
  ADD CONSTRAINT `tb_favoritos_ibfk_2` FOREIGN KEY (`userUsuario`) REFERENCES `tb_usuario` (`userUsuario`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
