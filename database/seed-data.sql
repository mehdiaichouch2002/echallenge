-- ============================================================================
-- EChallenge (Spring Boot) — Script de peuplement de la base de démonstration
-- Inspiré du script init-data.sql du projet echallenge d'origine.
-- À exécuter APRÈS le premier démarrage de l'application (le schéma et les
-- types de questions sont créés automatiquement au démarrage).
-- IMPORTANT : le drapeau --default-character-set=utf8mb4 est indispensable,
-- sinon les accents seront corrompus (le client mysql utilise latin1 par défaut).
--   docker exec -i echallenge-mysql mysql --default-character-set=utf8mb4 -uroot -proot echallenge < database/seed-data.sql
-- ============================================================================

-- ============================================================================
-- 1. THÈMES (Java et SQL existent peut-être déjà : insérés seulement si absents)
-- ============================================================================
INSERT INTO themes (name, description)
SELECT * FROM (SELECT 'Java', 'Langage Java : syntaxe, POO, collections' ) t
WHERE NOT EXISTS (SELECT 1 FROM themes WHERE name = 'Java');

INSERT INTO themes (name, description)
SELECT * FROM (SELECT 'SQL', 'Bases de données relationnelles et langage SQL') t
WHERE NOT EXISTS (SELECT 1 FROM themes WHERE name = 'SQL');

INSERT INTO themes (name, description)
SELECT * FROM (SELECT 'Mathématiques', 'Algèbre, analyse et géométrie') t
WHERE NOT EXISTS (SELECT 1 FROM themes WHERE name = 'Mathématiques');

INSERT INTO themes (name, description)
SELECT * FROM (SELECT 'Physique', 'Mécanique, électricité et physique moderne') t
WHERE NOT EXISTS (SELECT 1 FROM themes WHERE name = 'Physique');

INSERT INTO themes (name, description)
SELECT * FROM (SELECT 'Réseaux', 'Protocoles, adressage et services réseau') t
WHERE NOT EXISTS (SELECT 1 FROM themes WHERE name = 'Réseaux');

SET @java  = (SELECT id FROM themes WHERE name = 'Java');
SET @sql   = (SELECT id FROM themes WHERE name = 'SQL');
SET @math  = (SELECT id FROM themes WHERE name = 'Mathématiques');
SET @phys  = (SELECT id FROM themes WHERE name = 'Physique');
SET @net   = (SELECT id FROM themes WHERE name = 'Réseaux');
SET @qcm   = (SELECT id FROM question_types WHERE name = 'QCM');
SET @vf    = (SELECT id FROM question_types WHERE name = 'Vrai/Faux');

-- Désactive les questions de démonstration factices éventuelles
UPDATE questions SET active = 0 WHERE question_text LIKE 'Question _: what is JVM?';

-- ============================================================================
-- 2. QUESTIONS JAVA
-- ============================================================================
INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Que signifie JVM ?', 'JVM = Java Virtual Machine, la machine virtuelle qui exécute le bytecode', 1, 1, @java, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('Java Virtual Machine', 1, 1, @q), ('Java Vendor Model', 0, 2, @q), ('Java Visual Maker', 0, 3, @q), ('Just Virtual Memory', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quel mot-clé permet l''héritage entre classes en Java ?', 'Une classe hérite d''une autre avec « extends »', 1, 1, @java, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('extends', 1, 1, @q), ('implements', 0, 2, @q), ('inherits', 0, 3, @q), ('super', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Une interface Java peut contenir des méthodes par défaut.', 'Depuis Java 8, les interfaces peuvent définir des méthodes « default »', 1, 1, @java, @vf);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('Vrai', 1, 1, @q), ('Faux', 0, 2, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quelle collection Java n''autorise pas les doublons ?', 'Un Set garantit l''unicité de ses éléments', 1, 1, @java, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('Set', 1, 1, @q), ('List', 0, 2, @q), ('ArrayList', 0, 3, @q), ('Vector', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Le mot-clé « final » sur une méthode empêche sa redéfinition.', 'Une méthode final ne peut pas être surchargée dans une sous-classe', 1, 1, @java, @vf);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('Vrai', 1, 1, @q), ('Faux', 0, 2, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quelle est la classe mère de toutes les classes Java ?', 'Toute classe hérite implicitement de java.lang.Object', 1, 1, @java, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('Object', 1, 1, @q), ('Class', 0, 2, @q), ('Main', 0, 3, @q), ('Root', 0, 4, @q);

-- ============================================================================
-- 3. QUESTIONS SQL
-- ============================================================================
INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quelle clause filtre les lignes d''une requête ?', 'WHERE filtre les lignes avant agrégation', 1, 1, @sql, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('WHERE', 1, 1, @q), ('ORDER BY', 0, 2, @q), ('GROUP BY', 0, 3, @q), ('SELECT', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quelle commande supprime définitivement une table ?', 'DROP TABLE supprime la structure et les données', 1, 1, @sql, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('DROP TABLE', 1, 1, @q), ('DELETE TABLE', 0, 2, @q), ('REMOVE TABLE', 0, 3, @q), ('TRUNCATE ROW', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Une clé primaire peut contenir des valeurs NULL.', 'Une clé primaire est NOT NULL et unique par définition', 1, 1, @sql, @vf);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('Vrai', 0, 1, @q), ('Faux', 1, 2, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quelle jointure retourne uniquement les lignes présentes dans les deux tables ?', 'INNER JOIN ne conserve que les correspondances', 1, 1, @sql, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('INNER JOIN', 1, 1, @q), ('LEFT JOIN', 0, 2, @q), ('RIGHT JOIN', 0, 3, @q), ('FULL JOIN', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quelle fonction d''agrégation compte le nombre de lignes ?', 'COUNT(*) compte toutes les lignes du groupe', 1, 1, @sql, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('COUNT(*)', 1, 1, @q), ('SUM(*)', 0, 2, @q), ('TOTAL()', 0, 3, @q), ('NB()', 0, 4, @q);

-- ============================================================================
-- 4. QUESTIONS MATHÉMATIQUES
-- ============================================================================
INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quelle est la dérivée de x² ?', 'Règle de dérivation : d/dx(x^n) = n·x^(n-1)', 1, 1, @math, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('2x', 1, 1, @q), ('x²', 0, 2, @q), ('x', 0, 3, @q), ('x³', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('La somme des angles d''un triangle vaut toujours 180°.', 'Théorème fondamental de la géométrie euclidienne', 1, 1, @math, @vf);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('Vrai', 1, 1, @q), ('Faux', 0, 2, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Résoudre : 2x + 5 = 13', '2x = 8 donc x = 4', 1, 1, @math, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('x = 4', 1, 1, @q), ('x = 9', 0, 2, @q), ('x = 5', 0, 3, @q), ('x = 8', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quelle est la valeur approchée de π ?', 'π ≈ 3,14159', 1, 1, @math, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('3,14159', 1, 1, @q), ('3,0', 0, 2, @q), ('3,5', 0, 3, @q), ('2,71828', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Si f(x) = x² + 3x + 2, que vaut f(2) ?', 'f(2) = 4 + 6 + 2 = 12', 1, 1, @math, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('12', 1, 1, @q), ('10', 0, 2, @q), ('8', 0, 3, @q), ('14', 0, 4, @q);

-- ============================================================================
-- 5. QUESTIONS PHYSIQUE
-- ============================================================================
INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quelle est l''unité SI de la force ?', 'Le newton (N) = kg·m·s⁻²', 1, 1, @phys, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('Newton', 1, 1, @q), ('Joule', 0, 2, @q), ('Watt', 0, 3, @q), ('Pascal', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('L''accélération de la pesanteur sur Terre vaut environ 9,8 m/s².', 'Valeur standard utilisée en mécanique', 1, 1, @phys, @vf);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('Vrai', 1, 1, @q), ('Faux', 0, 2, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quelle est la célèbre équation d''Einstein ?', 'E = mc² relie énergie et masse', 1, 1, @phys, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('E = mc²', 1, 1, @q), ('F = ma', 0, 2, @q), ('E = ½mv²', 0, 3, @q), ('P = UI', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('La lumière se propage dans le vide à environ 300 000 km/s.', 'c ≈ 299 792 458 m/s', 1, 1, @phys, @vf);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('Vrai', 1, 1, @q), ('Faux', 0, 2, @q);

-- ============================================================================
-- 6. QUESTIONS RÉSEAUX
-- ============================================================================
INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Que signifie HTTP ?', 'HyperText Transfer Protocol, protocole du Web', 1, 1, @net, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('HyperText Transfer Protocol', 1, 1, @q), ('High Transfer Text Protocol', 0, 2, @q), ('Host Transfer Protocol', 0, 3, @q), ('HyperText Terminal Process', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quel est le port par défaut de HTTPS ?', 'HTTPS utilise le port 443', 1, 1, @net, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('443', 1, 1, @q), ('80', 0, 2, @q), ('21', 0, 3, @q), ('8080', 0, 4, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Une adresse IPv4 est codée sur 32 bits.', '4 octets = 32 bits (ex. 192.168.1.1)', 1, 1, @net, @vf);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('Vrai', 1, 1, @q), ('Faux', 0, 2, @q);

INSERT INTO questions (question_text, explanation, points, active, theme_id, question_type_id)
VALUES ('Quel protocole traduit les noms de domaine en adresses IP ?', 'DNS = Domain Name System', 1, 1, @net, @qcm);
SET @q = LAST_INSERT_ID();
INSERT INTO question_options (option_text, is_correct, display_order, question_id) VALUES
('DNS', 1, 1, @q), ('DHCP', 0, 2, @q), ('FTP', 0, 3, @q), ('SMTP', 0, 4, @q);

-- ============================================================================
-- 7. CRÉNEAUX (tous disponibles)
-- ============================================================================
INSERT INTO timeslots (start_time, end_time, duration_minutes, booked) VALUES
('2026-07-22 09:00:00', '2026-07-22 11:00:00', 120, 0),
('2026-07-22 14:00:00', '2026-07-22 16:00:00', 120, 0),
('2026-07-23 09:00:00', '2026-07-23 11:00:00', 120, 0),
('2026-07-23 14:00:00', '2026-07-23 16:00:00', 120, 0),
('2026-07-24 09:00:00', '2026-07-24 11:00:00', 120, 0),
('2026-07-24 14:00:00', '2026-07-24 16:00:00', 120, 0),
('2026-07-27 09:00:00', '2026-07-27 11:00:00', 120, 0),
('2026-07-27 14:00:00', '2026-07-27 16:00:00', 120, 0),
('2026-07-28 09:00:00', '2026-07-28 11:00:00', 120, 0),
('2026-07-28 14:00:00', '2026-07-28 16:00:00', 120, 0);

-- ============================================================================
-- 8. TESTS et composition par thème
-- ============================================================================
INSERT INTO tests (name, description, total_duration_minutes, question_duration_seconds, total_questions, active)
VALUES ('Test SQL', 'Évaluation des bases du langage SQL', 30, 45, 4, 1);
SET @t = LAST_INSERT_ID();
INSERT INTO test_theme_questions (test_id, theme_question_counts_key, question_count) VALUES (@t, @sql, 4);

INSERT INTO tests (name, description, total_duration_minutes, question_duration_seconds, total_questions, active)
VALUES ('Quiz Culture Informatique', 'Quiz transversal : Java, SQL et réseaux', 30, 60, 6, 1);
SET @t = LAST_INSERT_ID();
INSERT INTO test_theme_questions (test_id, theme_question_counts_key, question_count) VALUES
(@t, @java, 2), (@t, @sql, 2), (@t, @net, 2);

INSERT INTO tests (name, description, total_duration_minutes, question_duration_seconds, total_questions, active)
VALUES ('Test Scientifique', 'Mathématiques et physique de base', 40, 60, 5, 1);
SET @t = LAST_INSERT_ID();
INSERT INTO test_theme_questions (test_id, theme_question_counts_key, question_count) VALUES
(@t, @math, 3), (@t, @phys, 2);

-- ============================================================================
-- RÉSUMÉ : 5 thèmes · 24 questions actives · 10 créneaux · 3 nouveaux tests
-- (les candidats, sessions, réponses et résultats sont créés par l'application)
-- ============================================================================
