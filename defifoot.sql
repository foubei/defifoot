-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Sam 02 Juillet 2016 à 10:39
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `fouzi`
--

-- --------------------------------------------------------

--
-- Structure de la table `entrainement`
--

CREATE TABLE IF NOT EXISTS `entrainement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `joueur_id` int(11) DEFAULT NULL,
  `reflexe` float DEFAULT '0',
  `prise_balle` float DEFAULT '0',
  `detente` float DEFAULT '0',
  `tete` float DEFAULT '0',
  `tacle` float DEFAULT '0',
  `passe` float DEFAULT '0',
  `tir` float DEFAULT '0',
  `dribble` float DEFAULT '0',
  `controle` float DEFAULT '0',
  `vitesse` float DEFAULT '0',
  `endurance` float DEFAULT '0',
  `physique` float DEFAULT '0',
  `date` date DEFAULT NULL COMMENT 'Date d''entrainement du joueur.',
  `date_creation` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Date de creation de l''entrainement dans la bdd.',
  PRIMARY KEY (`id`),
  KEY `joueur_id` (`joueur_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `equipe`
--

CREATE TABLE IF NOT EXISTS `equipe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vrai_id` int(11) DEFAULT NULL COMMENT 'Id de l’équipe sur www.defifoot.com',
  `nom` varchar(255) DEFAULT NULL,
  `manager` varchar(255) DEFAULT NULL,
  `joueurs` int(11) DEFAULT NULL COMMENT 'Nombre des joueurs.',
  `joueurs_pro` int(11) DEFAULT NULL COMMENT 'Nombre des joueurs professionnels.',
  `joueurs_centre` int(11) DEFAULT '0' COMMENT 'Nombre des jeunes joueurs < 21 ans.',
  `age` float DEFAULT NULL COMMENT 'Age moyen de l''equipe.',
  `npa` float DEFAULT NULL COMMENT 'NPA moyen de l''equipe.',
  `npv` float DEFAULT NULL COMMENT 'NPV moyen de l''equipe.',
  `experience` float DEFAULT NULL COMMENT 'Total d''experience donne par les joueurs.',
  `pression` float DEFAULT NULL COMMENT 'Total de pression donne par les joueurs.',
  `flair` float DEFAULT NULL COMMENT 'Total du flair donne par les joueurs.',
  `date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Derniere date de modification des parametres de l''equipe dans la bdd.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=25 ;

-- --------------------------------------------------------

--
-- Structure de la table `joueur`
--

CREATE TABLE IF NOT EXISTS `joueur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vrai_id` int(11) DEFAULT NULL COMMENT 'Id du joueur sur www.defifoot.com',
  `prenom` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `age` float DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `npa` float DEFAULT NULL,
  `npv` float DEFAULT NULL,
  `position_actuelle` varchar(10) DEFAULT NULL COMMENT 'Position choisie par le manager.',
  `position_origine` varchar(10) DEFAULT NULL COMMENT 'Position originale du joueur sur defifoot.',
  `equipe_id` int(11) DEFAULT NULL,
  `condition_arrivee` varchar(255) DEFAULT NULL,
  `date_arrivee` date DEFAULT NULL COMMENT 'Date d''arrivee au club.',
  `reflexe` float DEFAULT NULL,
  `prise_balle` float DEFAULT NULL,
  `detente` float DEFAULT NULL,
  `tete` float DEFAULT NULL,
  `tacle` float DEFAULT NULL,
  `passe` float DEFAULT NULL,
  `tir` float DEFAULT NULL,
  `dribble` float DEFAULT NULL,
  `controle` float DEFAULT NULL,
  `vitesse` float DEFAULT NULL,
  `endurance` float DEFAULT NULL,
  `physique` float DEFAULT NULL,
  `forme` float DEFAULT NULL,
  `flair` float DEFAULT NULL,
  `pression` float DEFAULT NULL,
  `mental` float DEFAULT NULL,
  `experience` float DEFAULT NULL,
  `qualite_psychologique` float DEFAULT NULL,
  `niveau_tete` float DEFAULT NULL,
  `niveau_frappe` float DEFAULT NULL,
  `niveau_controle` float DEFAULT NULL,
  `niveau_dribble` float DEFAULT NULL,
  `niveau_recuperation` float DEFAULT NULL,
  `niveau_passe` float DEFAULT NULL,
  `niveau_endurance` float DEFAULT NULL,
  `niveau_gardien` float DEFAULT NULL,
  `progression_potentiel` float DEFAULT NULL,
  `progression_vitesse` float DEFAULT NULL,
  `progression_rigueur` float DEFAULT NULL,
  `progression_rigularite` float DEFAULT NULL,
  `npa_gb` float DEFAULT NULL,
  `npa_df` float DEFAULT NULL,
  `npa_lb` float DEFAULT NULL,
  `npa_md` float DEFAULT NULL,
  `npa_mt` float DEFAULT NULL,
  `npa_mo` float DEFAULT NULL,
  `npa_at` float DEFAULT NULL,
  `npa_bc` float DEFAULT NULL,
  `valeur` varchar(255) DEFAULT NULL,
  `salaire` varchar(255) DEFAULT NULL,
  `cote` float DEFAULT NULL,
  `statut` varchar(255) DEFAULT NULL,
  `capitaine` float DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL COMMENT 'Lien de l''avatar du joueur sur defifoot.',
  `lien` varchar(255) DEFAULT NULL COMMENT 'Lien du joueur sur defifoot.',
  `date_creation` datetime DEFAULT NULL COMMENT 'Date de creation du joueur dans la bdd.',
  `date_modification` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Derniere date de modification des parametres du joueur dans la bdd.',
  PRIMARY KEY (`id`),
  KEY `equipe__index` (`equipe_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=31 ;

-- --------------------------------------------------------

--
-- Structure de la table `mise_a_jour`
--

CREATE TABLE IF NOT EXISTS `mise_a_jour` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `equipe_id` int(11) NOT NULL,
  `maj_equipe` datetime DEFAULT NULL COMMENT 'Derniere mise a jour de la table equipe',
  `maj_joueur` datetime DEFAULT NULL COMMENT 'Derniere mise a jour de la table joueur',
  `maj_entrainement` datetime DEFAULT NULL COMMENT 'Derniere mise a jour de la table entrainement',
  `maj_tout` datetime DEFAULT NULL COMMENT 'Dernière mise a jour de la table',
  PRIMARY KEY (`id`),
  KEY `equipe_id` (`equipe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `progression`
--

CREATE TABLE IF NOT EXISTS `progression` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `joueur_id` int(11) NOT NULL,
  `potentiel` float DEFAULT NULL,
  `vitesse` float DEFAULT NULL,
  `rigueur` float DEFAULT NULL,
  `rigularite` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `joueur_id` (`joueur_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `statut`
--

CREATE TABLE IF NOT EXISTS `statut` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `joueur_id` int(11) NOT NULL,
  `actuel` int(11) DEFAULT NULL COMMENT 'Statut actuel du joueur.',
  `normal` tinyint(1) DEFAULT NULL,
  `vestiaire` float DEFAULT NULL,
  `gloire` float DEFAULT NULL,
  `ame` float DEFAULT NULL,
  `vedette` tinyint(1) DEFAULT NULL,
  `experience` float DEFAULT NULL COMMENT 'Bonus "experience" donne par le joueur',
  `pression` float DEFAULT NULL COMMENT 'Bonus "pression" donne par le joueur',
  `flair` float DEFAULT NULL COMMENT 'Bonus "flair" donne par le joueur',
  PRIMARY KEY (`id`),
  KEY `joueur_id` (`joueur_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `entrainement`
--
ALTER TABLE `entrainement`
  ADD CONSTRAINT `entrainement_joueur___fk` FOREIGN KEY (`joueur_id`) REFERENCES `joueur` (`id`);

--
-- Contraintes pour la table `joueur`
--
ALTER TABLE `joueur`
  ADD CONSTRAINT `joueur_equipe___fk` FOREIGN KEY (`equipe_id`) REFERENCES `equipe` (`id`);

--
-- Contraintes pour la table `mise_a_jour`
--
ALTER TABLE `mise_a_jour`
  ADD CONSTRAINT `mise_a_jour_equipe___fk` FOREIGN KEY (`equipe_id`) REFERENCES `equipe` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
