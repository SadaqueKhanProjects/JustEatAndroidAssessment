# 🛠 Developer Notes

This document serves as a development log and architectural trace for the Just Eat Android Assessment. It captures internal decisions, progress updates, and edge-case handling throughout the implementation lifecycle.

---

## ✅ Project State

- Jetpack Compose scaffold initialized with Kotlin DSL build setup.
- `main` branch force-pushed after resetting local Git history.
- Manual restoration of planning docs (`README.md`, `user_stories.md`, `architecture.md`).
- Git branching follows semantic structure: `type/{issue-number}-{short-description}` — e.g., `feature/03-setup-api-layer`, `bugfix/04-error-boundaries`, `doc/01-init-docs`.

---

## 🔄 Branch Workflow

- **Main branches:**
  - `main`: production-ready state
  - `feature/*`: progressive development
  - `doc/*`: documentation-only changes
  - `bugfix/*`: fixes for regressions or breaking behavior
- Issues are tracked via GitHub Project board (linked below).
- Commits are linked to Issues with PR headers like `Closes #5` or `Progresses #7`.

---

## 📌 Current Focus

Actively working under `feature/05-final-submission-polish`.  
Architecture is stable; final polish includes:  
– Postcode validation  
– UI consistency checks  
– README and documentation refinement

---

## 🧠 Core Design Decisions

### — Name

Sanitization applied to strip trailing characters (e.g., hyphens, brackets).  
Removed non-restaurant content like embedded addresses or social handles.  
Avoided a complex blacklist engine due to absence of full dataset.

### — Cuisines

Used a **whitelist-based filtering** for logical cuisine terms (e.g., "Pizza", "Mexican", "Halal").  
Excluded unknown or ambiguous cuisine labels to prevent UI clutter.  
Attempted broader endpoint queries for cuisine metadata, which returned `403 Forbidden` — likely due to Just Eat API access restrictions.

### — Rating (as a Number)

Ratings only shown when `starRating` is present.  
Omitted cuisine sections when rating was null to avoid suggesting 0 stars.  
Avoided placeholder/default values to prevent misleading the user.

### — Address

Cleaned and normalized using domain-specific formatting logic.  
No faulty addresses found; response fields were consistent (`firstLine`, `city`, `postalCode`).  
Handled duplicate commas and improper casing where necessary.

---

## ⚙️ Implementation Highlights

✅ ViewModel uses `StateFlow` for reactive UI binding.  
✅ Postcode input is validated against UK standards:  
Length: 5–8 characters  
Pattern: Only alphanumeric, uppercase characters  
✅ Centralized API integration in `RestaurantRepositoryImpl`.  
✅ Clean architecture enforced through separation of concerns:  
`DTO → Mapper → Domain Model → UI`.

---

## ⚠️ Challenges & Constraints

Full API schema was unavailable. Attempts to fetch metadata from undocumented endpoints failed due to 403 errors.  
Without exhaustive datasets, pattern-based blacklist logic for names/cuisines was deprioritized in favor of known-good data (whitelisting).  
UI inconsistencies across devices were observed — partially mitigated via spacing tokens and `MaterialTheme`.

---

## 💡 Future Improvements

Implement dynamic preview annotations (`@Preview`) for layout QA.  
Add `.github/ISSUE_TEMPLATE` for easier backlog curation.  
Introduce `Result<T>` or sealed class wrappers for unified error handling.  
Expand DI graph to support testable interfaces and mockable service layers.  
Modularize networking and domain logic further for reuse.

---

## 🗂 Documentation References

[Architecture Overview](architecture.md)  
[User Stories](user_stories.md)  
[README](../README.md)  
[Kanban Task Board](https://github.com/users/SadaqueKhanProjects/projects/1/views/1)

---
