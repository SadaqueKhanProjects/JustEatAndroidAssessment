# 🧱 Architecture Overview – Just Eat Android Assessment

This app follows a clean **MVVM (Model–View–ViewModel)** architecture, ensuring separation of
concerns, testability, and scalability — ideal for modern Android development.

---

## 🔄 Data Flow (Unidirectional)

User Interaction ↓ Jetpack Compose View ↓ ViewModel (Handles logic & state) ↓ Repository (Handles
data sources) ↓ Retrofit Service (API Layer) ↓ API Response (JSON → Model) ↓ ViewModel updates state
↓ Compose re-renders UI


---

## 🗂 Planned Folder Structure

app/ └── data/ └── model/ # Data classes for Restaurant, Cuisine, Address, etc. └── network/ #
Retrofit interfaces and API config └── domain/ # Repository interface abstraction (optional) └──
presentation/ └── ui/ # Jetpack Compose UI Screens └── viewmodel/ # State and logic └── components/
# Reusable UI components (e.g., Card, Loader) └── di/ # Hilt modules (if applied) └── utils/ #
Constants, mappers, formatters


---

## 💡 Why MVVM?

- **Composable architecture**: Each layer has one responsibility.
- **Testability**: ViewModel logic can be tested independently.
- **Separation of concerns**: UI, logic, and data are well-isolated.
- **Scalability**: New features or sources (e.g., cache layer) can be added without disruption.
- **Compose-friendly**: ViewModel + StateFlow works well with reactive UI updates.

---

## 🛠 Technology Map

| Layer          | Library / Tool         | Justification                      |
|----------------|------------------------|------------------------------------|
| UI             | Jetpack Compose        | Declarative, modern UI             |
| Logic          | ViewModel + StateFlow  | Lifecycle-safe & reactive          |
| Data Layer     | Retrofit + Coroutine   | Clean async HTTP                   |
| JSON Parsing   | Moshi                  | Fast and flexible                  |
| DI (Optional)  | Hilt                   | Simplifies injection if used       |
| Build System   | Gradle with Kotlin DSL | Modern build config                |
| Min SDK        | API 24                 | Covers most Android devices        |

---

## 📐 Design Principles

- **Single Source of Truth**: UI reads only from ViewModel state
- **Unidirectional Data Flow (UDF)**: Data moves one way, reducing complexity
- **Reusability**: Components and models designed to be modular
- **Minimal Dependencies**: Lean by design to focus on the assessment task

> _This architecture reflects initial intentions and may evolve during implementation._
