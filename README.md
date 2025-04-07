# JustEatAndroidAssessment

A Kotlin-based Android application developed for the **Just Eat Takeaway.com Early Careers Mobile
Engineering Program**.

This project demonstrates:

- MVVM architecture using StateFlow
- Jetpack Compose UI
- Modular, API-driven development
- Clean implementation with extensibility and clarity in mind

---

## Candidate

- **Name:** Sadaque Khan
- **GitHub:** [github.com/SadaqueKhanProjects](https://github.com/SadaqueKhanProjects)

---

## Overview

This app consumes the official Just Eat UK API and displays restaurant listings based on user
postcode input.

### Rendered Data Includes:

- Restaurant Name
- Cuisines (filtered using whitelist logic)
- Rating (only when available)
- Address (formatted in UK style)

The interface limits output to the **first 10 results**, in line with the assessment brief.

---

## Demo

<img src="docs/SamsungS22UltraDemo.gif" alt="App Demo on Samsung S22 Ultra" width="300"/>

---

## Architecture Summary

| Layer         | Responsibility                 | Tools Used                    |
|---------------|---------------------------------|-------------------------------|
| UI Layer      | Display + Interaction           | Jetpack Compose (Material3)   |
| State Layer   | Reactive UI state               | ViewModel + StateFlow         |
| Repository    | Data source abstraction         | Kotlin + Hilt                 |
| Networking    | API access                      | Retrofit + Moshi              |
| Domain Models | Clean, app-safe data structures | DTO → Mapper → Domain Model   |

Related docs:

- [Architecture Overview](docs/architecture.md)
- [User Stories](docs/user_stories.md)
- [Developer Notes](docs/dev_notes.md)
- [Kanban Board](https://github.com/users/SadaqueKhanProjects/projects/1/views/1)

---

## Tech Stack

| Category         | Technology                  |
|------------------|-----------------------------|
| Language         | Kotlin                      |
| Build System     | Gradle (Kotlin DSL)         |
| UI Toolkit       | Jetpack Compose             |
| Dependency Injection | Hilt                    |
| Networking       | Retrofit + Moshi            |
| Async Operations | Kotlin Coroutines           |
| IDE              | Android Studio (Electric Eel+) |
| Minimum SDK      | API 24+                     |

---

## Setup Instructions

```bash
git clone https://github.com/SadaqueKhanProjects/JustEatAndroidAssessment.git
cd JustEatAndroidAssessment
```

Then:

1. Open the project in Android Studio
2. Allow Gradle to sync
3. Run on emulator or physical device (tested on Pixel 6 & Samsung S22 Ultra)

---

## Key Implementation Notes

### Restaurant Name

- Names returned from the API often included:
    - Trailing platform tags
    - Locality markers (e.g., “Soho”, “London Bridge”)
    - Symbols like dashes or brackets
- Applied minimal sanitization logic to:
    - Remove trailing dashes (e.g., `"Bento Box -"` → `"Bento Box"`)
    - Strip clearly appended city or location markers when not part of branding
    - Trim social tags or non-UI-friendly fragments
- **Why no full blacklist logic?**  
  Without access to the full dataset or a validated list of legitimate names, implementing a
  blacklist risked removing valid entries or damaging brand fidelity. A minimal, conservative
  cleanup was preferred.

---

### Cuisines

- The API often returned ambiguous or overly specific cuisine labels, including:
    - Internal kitchen terms
    - Duplicated or nested tags
- A **whitelist-based filter** was applied, retaining only recognizable and user-friendly terms:
    - Examples: Pizza, Halal, Mexican, Indian, Sushi
- If the cuisine field was:
    - Empty
    - Contained unclear or irrelevant values  
      → It was omitted from the UI to preserve clarity.
- **403 Metadata Limitation**:
    - Attempts to fetch a full cuisine list from broader or undocumented endpoints failed due
      to `403 Forbidden` errors.
    - These were likely caused by restricted schema access or missing authentication.
    - As a result, deeper schema parsing or dynamic categorization was not feasible.

---

### Rating

- Ratings were retrieved from the nested field `rating.starRating`.
- To ensure accuracy:
    - No default rating was shown (e.g., no "0" or "N/A" placeholders)
    - A rating was only displayed when the field was explicitly present
- This avoided misinforming users about restaurants that were simply unrated.

---

### Address

- Address fields were cleanly structured from:
    - `firstLine`
    - `city`
    - `postalCode`
- Normalization logic included:
    - Removing extra punctuation (e.g., trailing commas or dashes)
    - Applying title casing and trimming excess whitespace
- No significant transformation was needed — the format was generally clean and conformed to UK
  address standards.

---

## Device Compatibility

Tested on:

- Pixel 6 Emulator (API 34)
- Samsung S22 Ultra (API 14)

Renders reliably across resolutions. Minor layout shifts may occur due to font scaling differences.

---

## License

This project was created solely for the Just Eat Android Assessment.  
It is not intended for production use or commercial distribution.
