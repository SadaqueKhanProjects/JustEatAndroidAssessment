# 🏗️ Architecture Overview

This document outlines the architectural structure and key design principles of the **JustEatAndroidAssessment** project.

---

## 1. 📐 Project Goal

To develop a **lightweight, modular Android application** using MVVM architecture, modern Android development standards, and a clear separation of concerns — to retrieve and display restaurant data from the Just Eat UK API based on user postcode input.

---

## 2. 🧱 High-Level Architecture

| Layer             | Description                                      | Technologies Used                      |
|------------------|--------------------------------------------------|----------------------------------------|
| **UI Layer**      | Renders composable functions                    | Jetpack Compose                        |
| **ViewModel**     | Manages UI state and business logic             | ViewModel + StateFlow                  |
| **Repository**    | Abstracts the data source                       | Interface + DI-bound implementation    |
| **Data Layer**    | Handles network and mapping logic               | Retrofit + DTOs + Mapper               |
| **Domain Layer**  | Holds clean UI-ready models                     | Domain Models (no Android dependencies)|

---

## 3. 🔄 Data Flow Diagram

```
[User Input]
      ↓
[ViewModel]  
• Validates input  
• Manages UI state  
      ↓
[Repository]  
• Abstracts data source  
      ↓
[Retrofit API Call]  
• Fetches restaurant data  
      ↓
[DTOs → Mapper]  
• Transforms DTOs to domain models  
      ↓
[Domain Models]
• Clean, UI-ready data  
      ↓
[UI (Jetpack Compose)]  
• Displays restaurant name, rating, cuisines, and address
```

---

## 4. 🧩 Module Breakdown

| Module             | Responsibility                                               |
|--------------------|-------------------------------------------------------------|
| `ui/screen`         | Top-level composable screen layout                         |
| `ui/components`     | Reusable UI pieces (SearchBar, RestaurantItem, etc.)       |
| `viewmodel`         | UI logic + business rules + StateFlow                      |
| `domain/model`      | Domain-safe, clean UI-ready data structures                |
| `data/dto`          | Maps to the JSON API contract from Just Eat                |
| `data/mapper`       | DTO → Domain transformation logic                          |
| `data/repository`   | Fetches and processes API data via repository pattern      |
| `network/api`       | Retrofit service definition                                |
| `di`                | Hilt modules for dependency injection                      |
| `docs`              | Architecture, user stories, and dev notes                  |
| `test/`             | Placeholder for test coverage (unit, UI, integration)      |

---

## 5. 📦 Technology Stack

| Category       | Technology                          |
|----------------|--------------------------------------|
| Language       | Kotlin                               |
| UI             | Jetpack Compose                      |
| Architecture   | MVVM + StateFlow                     |
| Networking     | Retrofit + Moshi                     |
| Async Ops      | Kotlin Coroutines                    |
| DI Framework   | Hilt                                 |
| Build System   | Gradle (Kotlin DSL)                  |
| Testing        | JUnit, Espresso (setup-ready)        |
| IDE            | Android Studio (Giraffe+)            |

---

## 6. 🧪 Extensibility Considerations

This codebase was designed with future extensibility in mind:

- **Composable UI**: Easy to add filters, sorters, or new UI sections.
- **Clean MVVM + Repository Separation**: Enables logic reuse across ViewModels or screens.
- **DTO → Domain Mapping**: Supports future Room, Caching, or Paging integration.
- **State Encapsulation**: Gracefully handles timeouts, no results, invalid input, or connection issues.

---

## 7. ⚠️ Known Constraints

- **403 Forbidden** responses from broader Just Eat endpoints blocked schema access.
- **Whitelist assumptions** used for cuisines due to lack of full metadata.
- **Pagination, sorting, and filtering** were excluded to stay within the intended task scope.

---

## 8. 📈 Suggested Enhancements

| Area              | Improvement Suggestion                                       |
|-------------------|---------------------------------------------------------------|
| UI Feedback        | Snackbar/Dialog instead of inline error messages             |
| Layout Support     | Apply WindowSizeClass for better adaptive UI                 |
| Testing            | Add mocked ViewModel + Repository tests                      |
| Metadata           | Integrate Just Eat schema (if publicly available)            |
| User Experience    | Add shimmer loading, placeholder UIs, and preview annotations |

---