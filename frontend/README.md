# EChallenge — Frontend (React + Vite)

Interface utilisateur de la plateforme EChallenge. Voir le [README principal](../README.md)
pour les instructions complètes d'installation.

```bash
npm install
npm run dev     # http://localhost:5173 (les appels /api sont proxifiés vers le backend :8090)
npm run build   # build de production dans dist/
```

Structure : `src/pages` (pages publiques, admin et candidat), `src/components` (Layout, Alert),
`src/auth` (contexte d'authentification JWT + routes protégées), `src/api` (client axios).
