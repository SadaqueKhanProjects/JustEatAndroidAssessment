# JustEatAndroidAssessment

An Android app built with Kotlin for the **Just Eat Takeaway.com Early Careers Mobile Engineering Program**.

This project demonstrates:

- MVVM architecture with StateFlow  
- Jetpack Compose UI  
- Modular, testable, and scalable codebase  
- API-driven development using Retrofit & Moshi

---

## Candidate

- **Name:** Sadaque Khan  
- **GitHub:** [github.com/SadaqueKhanProjects](https://github.com/SadaqueKhanProjects)

---

## Overview

This app fetches UK restaurant listings from the official Just Eat API based on a user-entered postcode. It displays only the first **10** restaurants, focusing on:

- **Name**  
- **Cuisines** (whitelist filtered)  
- **Rating** (when available)  
- **Address** (formatted to UK standards)

---

## Demo

<img src="docs/SamsungS22UltraDemo.gif" alt="App Demo on Samsung S22 Ultra" width="300"/>

---

## Architecture

| Layer         | Purpose                        | Tools Used                    |
|---------------|---------------------------------|-------------------------------|
| UI            | Display & Interaction           | Jetpack Compose (Material3)   |
| State         | UI State Management             | ViewModel + StateFlow         |
| Repository    | Data Source Abstraction         | Kotlin + Hilt                 |
| Network       | API Communication               | Retrofit + Moshi              |
| Domain Model  | App-safe Mapped Data            | DTO → Mapper → Domain Model   |

Related docs:

- [Architecture Overview](docs/architecture.md)  
- [User Stories](docs/user_stories.md)  
- [Developer Notes](docs/dev_notes.md)  
- [Kanban Board](https://github.com/users/SadaqueKhanProjects/projects/1/views/1)

---

## Tech Stack

| Category              | Tools / Frameworks              |
|------------------------|---------------------------------|
| Language              | Kotlin                          |
| Build System          | Gradle (Kotlin DSL)             |
| UI Toolkit            | Jetpack Compose                 |
| Dependency Injection  | Hilt                            |
| Networking            | Retrofit + Moshi                |
| Async Ops             | Kotlin Coroutines               |
| IDE                   | Android Studio Electric Eel+    |
| Min SDK               | 24+                             |

---

## Setup

```bash
git clone https://github.com/SadaqueKhanProjects/JustEatAndroidAssessment.git
cd JustEatAndroidAssessment
```

Then:

1. Open in Android Studio  
2. Let Gradle sync  
3. Run on emulator or physical device (tested on Pixel 6 & Samsung S22 Ultra)

---

## Assumptions and Future Improvements

### Assumptions

- Cuisines were filtered using a **whitelist** to avoid internal/kitchen labels and maintain clarity.
- Restaurant names were **minimally cleaned** (e.g., trailing dashes removed) to preserve brand identity without over-sanitizing.
- Ratings only shown if explicitly present — no default placeholder like `0` or `N/A` was assumed.

### Future Improvements

- **Smarter cuisine parsing** once schema or permissions allow deeper categorization.
- **Brand-aware name cleaning** using NLP or external datasets.
- **Enhanced error handling UI** (e.g., retry dialogs, clearer empty state feedback).
- **Pagination/lazy loading** for broader browsing beyond 10 results.
- **Extended test coverage** for edge cases, ViewModel logic, and repository behavior.

---

## Key Implementation Details

- **Cuisines**: Ambiguous/irrelevant labels were omitted. Examples retained: Pizza, Sushi, Indian, Halal.
- **Rating**: Retrieved from `rating.starRating`. Shown only if available to avoid misleading users.
- **Address**: Built from `firstLine`, `city`, and `postalCode`. Cleaned and title-cased for UK format.
- **Restaurant Name**: Cleaned to remove UI-noise (symbols, trailing dashes), without harming branding.

---

## Device Compatibility

- ✅ Pixel 6 Emulator (API 34)  
- ✅ Samsung S22 Ultra (API 14)  

Renders reliably across screen sizes. Minor layout shifts may occur from font scaling.

---

## License

This project was built exclusively for the Just Eat Android Assessment.  
Not intended for production use or public distribution.
