---
name: uml-diagrams
description: Generate and update the project's UML diagrams with PlantUML. Use whenever entities, repositories, services or their relations change, or when a new diagram (use case, sequence…) is requested.
---

# UML diagrams (PlantUML)

Sources live in `docs/uml/*.puml`; rendered PNGs land next to them and are embedded in
`docs/Rapport_Projet.docx` and `docs/Presentation.pptx` (regenerate those after re-rendering).

## Render

```bash
java -jar ~/tools/plantuml/plantuml.jar -tpng -DPLANTUML_LIMIT_SIZE=8192 docs/uml/*.puml
```

- The jar is at `~/tools/plantuml/plantuml.jar` (v1.2025.4). Graphviz is NOT installed —
  every diagram must start with `!pragma layout smetana`.
- Always **Read the output PNG** after rendering: smetana sometimes produces absurd
  aspect ratios. If a diagram comes out as a wide strip, add `left to right direction`
  (paradoxically it stacks packages vertically) or split the diagram.

## Existing diagrams

- `entites.puml` — domain class diagram: 11 JPA entities, 2 «enumeration», inheritance
  Candidate --|> User, composition Question *-- QuestionOption, all associations with
  multiplicities and French role names. (A DAO/services layers diagram existed once but
  the user asked to keep only the entities diagram — describe layers in prose instead.)

## Conventions (keep them)

- Standard UML notation: `-` private attributes with types, `+` public methods,
  `..|>` realization, `--|>` generalization, `..>` dependency, `*--` composition,
  multiplicities quoted (`"*" --> "1"`).
- One concern per diagram — don't merge domain and layers back into one diagram.
- `hide circle` on the domain diagram, `hide empty members` on the layers diagram.
- Labels and titles in French (matches the report).
- Keep methods minimal: only signatures that carry business meaning (enroll, submitAndGrade…).
