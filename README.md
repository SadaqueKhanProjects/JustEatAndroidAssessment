# JustEatAndroidAssessment

A Kotlin-based Android app developed for the **Just Eat Takeaway.com Early Careers Mobile Engineering Program**.

This project showcases:
- ✅ MVVM architecture
- ✅ Jetpack Compose
- ✅ Modular API-driven development

---

## 👤 Candidate

- **Name:** Sadaque Khan
- **GitHub:** [github.com/SadaqueKhanProjects](https://github.com/SadaqueKhanProjects)

---

## 📌 Overview

- Queries Just Eat's API using UK postcode
- Displays:
  - Restaurant name
  - Cuisine types
  - Star rating (if available)
  - Address
- Renders the **first 10 results** using scalable Compose UI

---

## 🎥 Demo

<img src="docs/SamsungS22UltraDemo.gif" alt="Demo GIF" width="240"/>

---

## 🧱 Architecture

| Layer         | Responsibility                | Implementation                |
|--------------|--------------------------------|-------------------------------|
| Network       | API communication              | Retrofit + Moshi              |
| Repository    | Abstract data source access    | Repository pattern + Hilt DI  |
| Domain Model  | Mapped clean entities          | DTO → Mapper → Domain         |
| State Layer   | Reactive UI state management   | Kotlin `StateFlow`            |
| UI Layer      | UI rendering + interactions    | Material3 + composables       |

> See: [`docs/architecture.md`](docs/architecture.md)  
> User stories: [`docs/user_stories.md`](docs/user_stories.md)  
> Kanban board: [Assessment Project](https://github.com/users/SadaqueKhanProjects/projects/1/views/1)

---

## 🛠 Tech Stack

- Kotlin, Coroutines, Retrofit, Moshi
- Jetpack Compose (Material3)
- Hilt (DI), JUnit, Instrumentation
- Gradle (Kotlin DSL)

---

## 🚀 Setup

```bash
git clone https://github.com/SadaqueKhanProjects/JustEatAndroidAssessment.git
cd JustEatAndroidAssessment
```

Then:

1. Open in **Android Studio (Electric Eel+)**
2. Sync Gradle
3. Run on **emulator or device (API 24+)**

---

## ⚙️ Implementation Notes

### Restaurant Name
- Trimmed social handles, trailing dashes
- No blacklist filter (lack of full dataset)

### Cuisine Type
- Whitelist-based inclusion (e.g., Pizza, Halal)
- Removed untrusted or empty values

### Ratings
- Displayed only if available in API response
- No fake/default values used

### Address
- Normalized formatting (firstLine, city, postcode)
- Light sanitation, no heavy validation needed

---

## 🚫 API Limitations

Limited API access prevented:
- Advanced cuisine validation
- Schema-driven mapping
- Metadata or edge-case handling

---

## ✅ Compatibility

Tested on:
- Pixel 6 Emulator (API 34)
- Samsung S22 Ultra (API 14)

---

## 🔧 Extensibility

- Postcode validation via regex + length rules
- Differentiates between:
  - Empty results
  - Timeout
  - Invalid postcode
- Clean state + ViewModel layers = future-ready

---

## 📌 Not Implemented

To stay within scope, excluded:
- Sorting
- Filtering
- Infinite scroll
- State persistence

Architecture supports easy future integration.

---

## 📄 License

This project was created for an educational assessment.  
**No commercial license or production usage intended.**
```
