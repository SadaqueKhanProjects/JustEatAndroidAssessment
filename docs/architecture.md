Here is your updated architecture.md with standardized structure and semantics consistent with common software engineering conventions, inspired by Just Eat’s documentation style:

⸻

Architecture Overview

This document outlines the architectural structure and key design principles of the JustEatAndroidAssessment project.

⸻

1. 📐 Project Goal

To develop a lightweight, modular Android application using MVVM architecture, modern Android development standards, and clean separation of concerns, in order to retrieve and display restaurant data from the Just Eat UK API based on user postcode input.

⸻

2. 🧱 High-Level Architecture

+-------------------------+
|     UI Layer           |  ← Jetpack Compose (Composable functions)
+-------------------------+
| ViewModel Layer        |  ← ViewModel + StateFlow (Business logic)
+-------------------------+
| Repository Layer       |  ← Interface abstraction to data source
+-------------------------+
| Data Layer             |  ← Retrofit + DTO + Mapper
+-------------------------+
| Domain Layer           |  ← Clean, UI-ready models
+-------------------------+



⸻

3. 🔄 Data Flow

User Input → ViewModel → Repository → API Call (Retrofit) → DTO → Mapper → Domain Model → UI

	•	ViewModel: Initiates restaurant fetch and maintains UI state.
	•	Repository: Abstracts the network source.
	•	Retrofit: Makes API requests to the Just Eat endpoint.
	•	Mapper: Converts raw DTOs into clean, domain models.
	•	Composable UI: Renders 10 restaurants with name, rating, cuisines, and address.

⸻

4. 🧩 Module Breakdown

Module	Responsibility
ui/screen	Holds the top-level composable screen and reactive rendering
ui/components	Reusable composables like SearchBar, RestaurantItem
viewmodel	UI logic with StateFlow + input validation
domain/model	Domain-safe models used across app
data/dto	API-specific structures
data/mapper	Maps DTO → Domain
data/repository	API orchestration logic
network/api	Retrofit interface
di	Hilt dependency injection modules
docs	Developer documentation
test/	Placeholder for unit tests



⸻

5. 📦 Technology Stack

Layer	Tech Used
Language	Kotlin
UI	Jetpack Compose
Architecture	MVVM + StateFlow
DI	Hilt
Networking	Retrofit + Moshi
Async Ops	Kotlin Coroutines
Testing	JUnit, Espresso (setup-ready)
Build System	Gradle (Kotlin DSL)



⸻

6. 🧪 Extensibility Considerations

This codebase was designed with future extensibility in mind. Features like:
•	Composable UI layers
Easy to add additional filters, sorters, or detail screens.
•	Clean MVVM + Repository Pattern
Enables reuse of logic across multiple ViewModels or screen variants.
•	Modular DTO → Domain Mapping
Facilitates future integration with Room, Paging, or Caching layers.
•	Error State Encapsulation
UI gracefully handles timeout, no internet, invalid input, and no results.

⸻

7. ⚠️ Known Constraints
   •	403 errors from unlisted API endpoints prevented metadata scraping for a complete cuisine list.
   •	Due to limited access, hardcoded assumptions were used (e.g., whitelisted cuisines).
   •	Pagination and filters were skipped to stay within scope.

⸻

8. 📈 Suggested Enhancements

Area	Improvement Suggestion
UI Feedback	Use Snackbar/Dialog for errors (currently inline text)
Device Support	Apply WindowSizeClass for layout adaptability
Testing	Add ViewModel tests with mocked Repository
Metadata Use	Integrate Just Eat metadata schema (if public)
UX Optimizations	Implement shimmer loading or placeholder previews



⸻