---
name: rapport
description: Regenerate the project's report (docs/Rapport_Projet.docx) and defense deck (docs/Presentation.pptx). Use whenever features, diagrams or screenshots change, or when the user asks to update/improve the rapport or the presentation.
---

# Rapport & Présentation EChallenge

Both files are **generated** — never hand-edit them; regenerate from the script.
Consult the `docx` and `pptx` skills for format-level guidance and design rules
(bold palette, sandwich structure, one visual motif, varied layouts).

## Pipeline

1. Generator script: `gen_docs.py` (python-docx + python-pptx), kept in the session
   scratchpad — if missing, rebuild it from the conventions below.
2. Venv: `python3 -m venv docvenv && docvenv/bin/pip install python-docx python-pptx`.
3. Regenerate: `docvenv/bin/python gen_docs.py` → writes both files into `docs/`.
4. **Always render-verify before delivering**:
   `soffice --headless --convert-to pdf <file>` then `pdftoppm -png -r 60` and Read the
   images. This has caught real defects every time (overflowing title, empty-table
   screenshot, stale figures).

## Report conventions (structure académique française)

Page de garde (nom, « Encadré par : ___ », année) → Sommaire (champ TOC Word, à mettre à
jour dans Word) → Introduction générale → Chapitre 1 Analyse des besoins (acteurs,
fonctionnels, non fonctionnels) → Chapitre 2 Conception (Figure 1 architecture.png,
Figure 2 uml/entites.png, couches DAO/métier en prose — l'utilisateur ne veut PLUS de
second diagramme UML) → Chapitre 3 Réalisation (technologies, sécurité, règles métier,
captures par parcours admin puis candidat) → Conclusion et perspectives → Webographie.
Numéros de page en pied de page (champ PAGE). Texte en français.

## Deck conventions

Identité « copie d'examen » : encre #16213A dominante, papier #FBFAF6, accent rouge
#D8432F (la marge rouge = motif visuel répété), Georgia pour les titres. Structure
sandwich : diapo de titre et conclusion sur fond encre, contenu sur fond clair.
Screenshots come from `docs/screenshots/` (retake with Playwright if the UI changed —
see the `verify` skill; beware list pages rendering empty before data loads).
