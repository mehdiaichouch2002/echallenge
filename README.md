# 🎓 EChallenge — Plateforme d'examens en ligne

**Projet de Fin de Module JEE / Spring Boot**

Application web de gestion d'examens en ligne : un administrateur gère les thèmes, questions, tests et créneaux ; les candidats s'inscrivent, réservent un créneau, passent un test chronométré et consultent leurs résultats.

## Architecture

```
React (Vite)  ──HTTP/JSON──▶  Spring MVC (@RestController)
                                    │
                              Couche métier (interfaces + implémentations @Service)
                                    │
                              Couche DAO (Spring Data JPA — JpaRepository)
                                    │
                              MySQL 8 (Docker)

Sécurité transversale : Spring Security 6 + JWT (stateless) + BCrypt
```

- **Backend** : Spring Boot 3.5, Java 17+, Maven — dossier [`backend/`](backend/)
- **Frontend** : React 19 + Vite + react-router + axios — dossier [`frontend/`](frontend/)
- **Base de données** : MySQL 8 via `docker-compose.yml` — script SQL dans [`database/echallenge.sql`](database/echallenge.sql)
- **Documentation** : diagrammes, rapport et présentation dans [`docs/`](docs/)

## Prérequis

- Java 17+ et Maven 3.9+
- Node.js 18+ et npm
- Docker + Docker Compose

## Étapes d'exécution

### 1. Démarrer MySQL

```bash
docker compose up -d
```

MySQL écoute sur le port **3307** (base `echallenge`, utilisateur `echallenge` / `echallenge`).

> Alternative sans Docker : créer la base avec `database/echallenge.sql` et adapter
> `backend/src/main/resources/application.properties`.

### 2. Démarrer le backend

```bash
cd backend
mvn spring-boot:run
```

L'API démarre sur **http://localhost:8090** (une page de statut JSON est visible à cette adresse).
Au premier lancement, le schéma est créé automatiquement (`ddl-auto=update`) et les deux comptes
de démonstration + les types de questions sont insérés.

### (Optionnel) Charger le jeu de données de démonstration

Pour peupler la base avec 5 thèmes, 25 questions, 10 créneaux et 3 tests supplémentaires
(dont un quiz multi-thèmes) :

```bash
docker exec -i echallenge-mysql mysql --default-character-set=utf8mb4 -uroot -proot echallenge < database/seed-data.sql
```

> Le drapeau `--default-character-set=utf8mb4` est indispensable pour préserver les accents.

Alternative : `database/echallenge.sql` est un export complet de la base (schéma **et**
toutes les données initiales : comptes, thèmes, questions, tests, créneaux). Pour
restaurer la base entière d'un coup :

```bash
docker exec -i echallenge-mysql mysql --default-character-set=utf8mb4 -uroot -proot < database/echallenge.sql
```

### 3. Démarrer le frontend

```bash
cd frontend
npm install
npm run dev
```

L'application est accessible sur **http://localhost:5173** (les appels `/api` sont proxifiés vers le backend).

## Comptes par défaut

| Rôle     | Email                     | Mot de passe |
|----------|---------------------------|--------------|
| Admin    | admin@echallenge.com      | admin123     |
| Candidat | candidat@echallenge.com   | candidat123  |

Ces deux comptes sont créés automatiquement au démarrage. La page de connexion est
pré-remplie avec le compte admin, et des boutons « Comptes de démonstration »
permettent de basculer entre les deux en un clic. Les autres candidats créent
leur compte via la page **S'inscrire**.

## Scénario de démonstration

1. Connectez-vous en admin → créez un **thème**, des **questions** (avec options et bonne réponse), un **test** (nombre de questions par thème) et des **créneaux**.
2. Déconnectez-vous → créez un compte **candidat**.
3. Candidat : *S'inscrire à un test* → choisir le test et un créneau libre.
4. *Mes sessions* → **Passer le test** : questions aléatoires, chronomètre, soumission.
5. Le score s'affiche immédiatement ; il est visible côté candidat (*Mes résultats*) et côté admin (*Résultats*).

## Principaux endpoints REST

| Méthode | URL | Accès | Description |
|---|---|---|---|
| POST | `/api/auth/register` | public | Inscription candidat (retourne un JWT) |
| POST | `/api/auth/login` | public | Authentification (retourne un JWT) |
| GET | `/api/tests`, `/api/themes` | public | Catalogue des tests / thèmes |
| CRUD | `/api/admin/{themes,questions,tests,timeslots,candidates}` | ADMIN | Gestion des entités |
| GET | `/api/admin/{sessions,results}` | ADMIN | Suivi des sessions et résultats |
| POST | `/api/candidate/enroll` | CANDIDATE | Inscription à un test sur un créneau |
| POST | `/api/candidate/sessions/{id}/start` | CANDIDATE | Démarrer le test (questions aléatoires) |
| POST | `/api/candidate/sessions/{id}/submit` | CANDIDATE | Soumettre les réponses (notation automatique) |
| GET | `/api/candidate/results` | CANDIDATE | Mes résultats |

Le JWT doit être transmis dans l'en-tête `Authorization: Bearer <token>`.

## Tests

```bash
cd backend
mvn test
```
