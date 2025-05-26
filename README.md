# PostcodeEats

**PostcodeEats** is an independently developed Android app built with **Kotlin**, designed to help users find restaurants that deliver to a **UK postcode**.

It showcases modern Android development using:

- MVVM architecture with StateFlow
- Jetpack Compose UI framework
- Retrofit-based API integration
- Interface-driven dependency injection
- Testable and scalable clean architecture

---

## 📱 Demo

<img src="docs/JustEatAppDemo_SamsungS22_v4.gif" alt="App Demo on Samsung S22 Ultra" width="300"/>

---

## 🔍 Features

- Search restaurants using a UK postcode
- Display restaurant name, cuisines, rating, and full address
- Shows up to 10 results per search
- Elegant UI with Jetpack Compose
- Built-in error handling and validation

---

## 🧱 Architecture

| Layer         | Purpose                        | Tools Used                    |
|---------------|--------------------------------|-------------------------------|
| UI            | Display & Interaction          | Jetpack Compose (Material3)   |
| State         | UI State Management            | ViewModel + StateFlow         |
| Repository    | Data Abstraction + Logic       | Kotlin + Hilt                 |
| Network       | API Communication              | Retrofit + Moshi              |
| Domain Model  | Clean mapped data              | DTO → Mapper → Domain Model   |
| Testing       | Unit test coverage & mocks     | JUnit, Truth, Robolectric     |

📎 Related docs:
- [Architecture Overview](docs/architecture.md)
- [User Stories](docs/user_stories.md)
- [Developer Notes](docs/dev_notes.md)
- [Kanban Board](https://github.com/users/SadaqueKhanProjects/projects/1/views/1)

---

## 🛠️ Tech Stack

| Category              | Tools / Frameworks              |
|------------------------|---------------------------------|
| Language              | Kotlin (JDK 17)                 |
| Build System          | Gradle (Kotlin DSL)             |
| UI Toolkit            | Jetpack Compose                 |
| Dependency Injection  | Hilt                            |
| Networking            | Retrofit + Moshi                |
| Async Ops             | Kotlin Coroutines               |
| Testing               | JUnit, Truth, Mockito, Turbine  |
| IDE                   | Android Studio Electric Eel+    |
| Min SDK               | 24                              |

---

## 🚀 Getting Started

```bash
git clone https://github.com/SadaqueKhanProjects/PostcodeEats.git
cd PostcodeEats
