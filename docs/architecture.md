# 🍽️ JustEatAndroidAssessment

A Kotlin-based Android app developed for the **Just Eat Takeaway.com Early Careers Mobile Engineering Program**.  
This assignment demonstrates clean **MVVM architecture**, **Jetpack Compose UI**, robust **network integration**, and organized **agile-style planning** — focused on retrieving and displaying restaurant data based on a UK postcode.

---

## 🧑‍💻 Candidate Info

- **Name:** Sadaque Khan
- **GitHub:** [github.com/SadaqueKhanProjects](https://github.com/SadaqueKhanProjects)
- **Submission Date:** Tuesday, 8th April 2025

---

## 🎯 Assignment Objective

Using Just Eat’s UK API:

- ✅ Fetch restaurant data for a given UK postcode using an API endpoint
- ✅ Display only the **first 10** restaurants
- ✅ Show 4 key data points:
    - **Restaurant Name**
    - **Cuisine Type(s)**
    - **Rating** (numeric)
    - **Address** (clean UK formatting)

---

## ✅ Tech Stack

| Layer         | Technology                          |
|---------------|--------------------------------------|
| Language      | Kotlin                               |
| Architecture  | MVVM (ViewModel + StateFlow)         |
| UI            | Jetpack Compose                      |
| Networking    | Retrofit + Moshi                     |
| Async Ops     | Kotlin Coroutines                    |
| DI            | Hilt                                 |
| Build System  | Gradle (Kotlin DSL)                  |
| IDE           | Android Studio (Giraffe+)            |
| Min SDK       | 24 (Android 7.0)                     |

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/SadaqueKhanProjects/JustEatAndroidAssessment.git
cd JustEatAndroidAssessment

2. Open in Android Studio
	•	Open the folder in Android Studio
	•	Wait for Gradle sync to complete

3. Run the App
	•	Use an emulator or physical device (e.g., Pixel 6, Samsung S22 Ultra API 34)
	•	Hit Run ▶️ from Android Studio

⸻

📁 Project Structure

com.sadaquekhan.justeatassessment
│
├── data
│   ├── dto                 # API-specific response models
│   ├── mapper              # Converts DTOs to Domain Models
│   └── repository          # Repository implementation layer
│
├── domain.model            # Clean domain models used in ViewModels/UI
│
├── network.api             # Retrofit service interface
│
├── di                      # Hilt DI modules (AppModule, NetworkModule)
│
├── ui
│   ├── screen              # Composable screen(s)
│   ├── components          # Reusable UI components (e.g., SearchBar)
│   └── theme               # App theme definitions
│
├── viewmodel               # UI logic with ViewModel + StateFlow
│
└── docs                    # Architecture & agile planning



⸻

🧩 Assumptions Made
	•	Cuisine Filtering: Only known, relevant cuisines were whitelisted to avoid clutter (e.g., ignored internal kitchen terms or duplicates).
	•	Restaurant Names: Cleaned up using heuristics — removed trailing dashes, brackets, or branches embedded in names.
	•	Addresses: Cleaned and formatted to UK standards, removing commas and correcting casing.
	•	Full data validation could not be performed due to 403 errors from related Just Eat endpoints. A full blacklist or pattern model was infeasible.

⸻

📱 UI Device Testing

The app was tested on:
	•	Google Pixel 6 (API 34)
	•	Samsung S22 Ultra (API 34)

Note: Minor visual inconsistencies may occur on different screen sizes and densities due to Jetpack Compose rendering and system font scaling.

Suggested Improvements:
	•	Create universal spacing/margin constants and shared Dimension.kt file for consistent padding.
	•	Use BoxWithConstraints or WindowSizeClass for adaptive layouts.
	•	Implement dynamic theme previews for dark mode and font scaling.
	•	Use preview annotations (@Preview) to design against small, medium, and expanded screens.

⸻

🛠 Agile Planning

Document	File/Link
Architecture Doc	docs/architecture.md
User Stories	docs/user_stories.md
Dev Notes	docs/dev_notes.md
Kanban Board	GitHub Project



⸻
