-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 28-Set-2019 às 18:09
-- Versão do servidor: 10.4.6-MariaDB
-- versão do PHP: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `gerenciadorhospitalar`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `nome` varchar(300) NOT NULL,
  `cpf` varchar(15) DEFAULT NULL,
  `numero` varchar(15) DEFAULT NULL,
  `endereco` varchar(300) DEFAULT NULL,
  `cadastro` date NOT NULL DEFAULT current_timestamp(),
  `dataNascimento` varchar(10) DEFAULT NULL,
  `sexo` varchar(10) DEFAULT NULL,
  `peso` double DEFAULT NULL,
  `altura` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `clientes`
--

INSERT INTO `clientes` (`id`, `nome`, `cpf`, `numero`, `endereco`, `cadastro`, `dataNascimento`, `sexo`, `peso`, `altura`) VALUES
(42, 'Matheus Viana Souza', '612.693.673-66', '(98) 98435-8483', 'Rua Jose Filomeno\nn 252\nAviação\nCândido Mendes - MA', '2019-09-28', '30/06/1997', 'Masculino', 80, 1.63);

-- --------------------------------------------------------

--
-- Estrutura da tabela `configuracoes`
--

CREATE TABLE `configuracoes` (
  `usuario` varchar(30) NOT NULL,
  `logo` varchar(1000) DEFAULT NULL,
  `background` varchar(1000) DEFAULT NULL,
  `tituloConsulta` varchar(300) DEFAULT NULL,
  `validade` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `consulta`
--

CREATE TABLE `consulta` (
  `usuario` varchar(30) NOT NULL,
  `retornoId` int(11) NOT NULL,
  `clienteId` int(11) NOT NULL,
  `saeId` int(11) NOT NULL,
  `data` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `evolucao`
--

CREATE TABLE `evolucao` (
  `saeId` int(11) NOT NULL,
  `data` date NOT NULL DEFAULT current_timestamp(),
  `fotoAnterior` varchar(1000) DEFAULT NULL,
  `fotoAtual` varchar(1000) DEFAULT NULL,
  `evolucao` varchar(5000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `login`
--

CREATE TABLE `login` (
  `usuario` varchar(30) NOT NULL,
  `nome` varchar(300) NOT NULL,
  `senha` varchar(30) NOT NULL,
  `nivel` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `login`
--

INSERT INTO `login` (`usuario`, `nome`, `senha`, `nivel`) VALUES
('theuskira', 'Matheus Viana', 'pw123456', 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `retorno`
--

CREATE TABLE `retorno` (
  `usuario` varchar(30) NOT NULL,
  `clienteId` int(11) NOT NULL,
  `tipo` varchar(300) DEFAULT NULL,
  `procedimento` varchar(1000) DEFAULT NULL,
  `motivo` varchar(1000) DEFAULT NULL,
  `data` date NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `sae`
--

CREATE TABLE `sae` (
  `id` int(11) NOT NULL,
  `usuario` varchar(30) NOT NULL,
  `clienteId` int(11) NOT NULL,
  `historico` varchar(1000) DEFAULT NULL,
  `diagnostico` varchar(1000) DEFAULT NULL,
  `planejamento` varchar(1000) DEFAULT NULL,
  `implementacao` varchar(1000) DEFAULT NULL,
  `avaliacaoEvolucao` varchar(1000) DEFAULT NULL,
  `data` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `configuracoes`
--
ALTER TABLE `configuracoes`
  ADD KEY `usuario` (`usuario`);

--
-- Índices para tabela `consulta`
--
ALTER TABLE `consulta`
  ADD KEY `usuario` (`usuario`),
  ADD KEY `retornoId` (`retornoId`),
  ADD KEY `clienteId` (`clienteId`),
  ADD KEY `saeId` (`saeId`);

--
-- Índices para tabela `evolucao`
--
ALTER TABLE `evolucao`
  ADD KEY `saeId` (`saeId`);

--
-- Índices para tabela `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`usuario`);

--
-- Índices para tabela `retorno`
--
ALTER TABLE `retorno`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario` (`usuario`),
  ADD KEY `clienteId` (`clienteId`);

--
-- Índices para tabela `sae`
--
ALTER TABLE `sae`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario` (`usuario`),
  ADD KEY `clienteId` (`clienteId`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT de tabela `retorno`
--
ALTER TABLE `retorno`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `sae`
--
ALTER TABLE `sae`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `configuracoes`
--
ALTER TABLE `configuracoes`
  ADD CONSTRAINT `configuracoes_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `login` (`usuario`);

--
-- Limitadores para a tabela `consulta`
--
ALTER TABLE `consulta`
  ADD CONSTRAINT `consulta_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `login` (`usuario`),
  ADD CONSTRAINT `consulta_ibfk_2` FOREIGN KEY (`retornoId`) REFERENCES `retorno` (`id`),
  ADD CONSTRAINT `consulta_ibfk_3` FOREIGN KEY (`clienteId`) REFERENCES `clientes` (`id`),
  ADD CONSTRAINT `consulta_ibfk_4` FOREIGN KEY (`saeId`) REFERENCES `sae` (`id`);

--
-- Limitadores para a tabela `evolucao`
--
ALTER TABLE `evolucao`
  ADD CONSTRAINT `evolucao_ibfk_1` FOREIGN KEY (`saeId`) REFERENCES `sae` (`id`);

--
-- Limitadores para a tabela `retorno`
--
ALTER TABLE `retorno`
  ADD CONSTRAINT `retorno_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `login` (`usuario`),
  ADD CONSTRAINT `retorno_ibfk_2` FOREIGN KEY (`clienteId`) REFERENCES `clientes` (`id`);

--
-- Limitadores para a tabela `sae`
--
ALTER TABLE `sae`
  ADD CONSTRAINT `sae_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `login` (`usuario`),
  ADD CONSTRAINT `sae_ibfk_2` FOREIGN KEY (`clienteId`) REFERENCES `clientes` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
