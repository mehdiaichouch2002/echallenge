---
name: verify
description: How to launch and drive the EChallenge app (Spring Boot + React) for end-to-end verification with Playwright.
---

# Verifying EChallenge

## Launch (all three needed)

```bash
docker compose up -d                                   # MySQL 8 on host port 3307 (data in named volume)
cd backend && mvn spring-boot:run                      # API on http://localhost:8090 (NOT 8080/8081 — taken on this machine)
cd frontend && npm run dev                             # UI on http://localhost:5173, proxies /api to 8090
```

Health: `curl localhost:8090/` returns a JSON status page; `curl localhost:8090/api/tests` lists tests.

## Accounts (seeded at startup by DataInitializer)

- Admin: `admin@echallenge.com` / `admin123` — the login form is **pre-filled** with this; just submit.
- Candidate: `candidat@echallenge.com` / `candidat123` — click the "Candidat" demo button on /login.

## Driving with Playwright (chromium)

- `npm i playwright` in a scratch dir; chromium binary persists in `~/.cache/ms-playwright`.
- Accept `window.confirm` dialogs: `page.on('dialog', d => d.accept())` (all deletes confirm).
- Selectors: `.alert-error` (error banners, dismiss via `.alert-close`), `.stamp-pass`/`.stamp-fail`
  (result verdicts), `.exam-timer .time` (mono countdown), `.demo-accounts button` (login quick-fill),
  `nav a.active` (exactly one should match per page).

## Gotchas that have burned past sessions

- **Fetch race**: list pages render an empty `<tbody>` before data arrives. Never assert on
  `waitForSelector('tbody tr')` right after a nav click — the OLD page's rows can satisfy it.
  Use a direct `page.goto(url)` then wait for rows, or `waitForFunction` on row content.
- **403 not 401**: the backend returns 403 for missing/invalid tokens (no AuthenticationEntryPoint
  configured), so the axios 401-interceptor does not auto-logout on stale tokens.
- Exam flow: enrolling books the slot (one active session per test per candidate); sessions can't
  be re-submitted; correct options in the seeded data are always `displayOrder 1` (first shown),
  so "first option = correct, last = wrong" gives deterministic scores.
- SQL imports into the container need `mysql --default-character-set=utf8mb4` or accents corrupt.

## Flows worth driving

Admin: theme/question/test/timeslot CRUD, delete guards (booked slot 400, FK conflicts 409 French messages),
test deactivation hides it from the public catalog. Candidate: register → enroll → timed exam →
instant grading (50% pass threshold) → results both sides. Screenshots land in `docs/screenshots/`.
