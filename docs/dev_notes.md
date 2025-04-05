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

- Actively working under the branch: `feature/05-final-submission-polish`
- Architecture is stable; current efforts are focused on final polish:
  - Postcode validation improvements
  - UI consistency checks across devices and themes
  - README and documentation refinement

---

## 🧠 Core Design Decisions

### — 🧼 Name

- Sanitization applied to strip trailing characters (e.g., hyphens, brackets).  
- Removed non-restaurant content like embedded addresses or social handles.  
- Avoided implementing a complex blacklist engine due to the absence of a full dataset.

---

### — 🍽️ Cuisines

- Applied **whitelist-based filtering** for logical cuisine terms (e.g., "Pizza", "Mexican", "Halal").  
- Excluded unknown or ambiguous cuisine labels to reduce UI clutter.  
- Attempted broader endpoint queries for cuisine metadata — returned `403 Forbidden` (likely due to API access restrictions).

---

### — ⭐ Rating (as a Number)

- Displayed ratings **only** when `starRating` is present.  
- Omitted cuisine sections when rating was null to avoid suggesting 0 stars.  
- Avoided placeholder or default values to prevent misleading the user.

---

### — 🏡 Address

- Cleaned and normalized using domain-specific formatting logic.  
- No faulty addresses found — fields like `firstLine`, `city`, and `postalCode` were consistent.  
- Handled duplicate commas and improper casing where necessary.


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

- Full API schema was unavailable — attempts to fetch metadata from undocumented endpoints resulted in `403 Forbidden` errors.
- Without exhaustive datasets, complex blacklist logic for names/cuisines was deprioritized in favor of **whitelisting** known-good values.
- UI inconsistencies across devices were observed — partially mitigated using spacing tokens and `MaterialTheme`.

---

## 💡 Future Improvements

- Implement dynamic layout previews using `@Preview` annotations for visual QA.
- Add a `.github/ISSUE_TEMPLATE` to streamline issue reporting and backlog curation.
- Introduce a `Result<T>` wrapper or sealed classes for more robust, unified error handling.
- Expand the **DI graph** to support testable interfaces and mockable service layers.
- Further modularize **networking** and **domain** logic for better reuse and scalability.

---

## 🗂 Documentation References

- [Architecture Overview](architecture.md)  
- [User Stories](user_stories.md)  
- [README](../README.md)  
- [Kanban Task Board](https://github.com/users/SadaqueKhanProjects/projects/1/views/1)

---

