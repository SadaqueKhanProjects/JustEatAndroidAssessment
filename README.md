# JustEatAndroidAssessment

An Android application developed in **Kotlin** for the **Just Eat Takeaway.com Early Careers Mobile Engineering Program**.

This project showcases:

- MVVM architecture with StateFlow
- Jetpack Compose UI framework
- Modular, testable, and scalable codebase
- API-driven design using Retrofit & Moshi
- Interface-based dependency injection and robust testing strategy

---

## Candidate

- **Name:** Sadaque Khan
- **GitHub:** [github.com/SadaqueKhanProjects](https://github.com/SadaqueKhanProjects)

---

## Overview

This app retrieves restaurant data from the official Just Eat UK API based on a user-provided **UK postcode**. It displays **only the first 10** restaurants, each showing:

- **Name** (cleaned)
- **Cuisines** (whitelist filtered)
- **Rating** (shown if available)
- **Address** (formatted for UK standards)

---

## Demo

<img src="docs/JustEatAppDemo_SamsungS22_v3.gif" alt="App Demo on Samsung S22 Ultra" width="300"/>

---

## Architecture

| Layer         | Purpose                        | Tools Used                    |
|---------------|--------------------------------|-------------------------------|
| UI            | Display & Interaction           | Jetpack Compose (Material3)   |
| State         | UI State Management             | ViewModel + StateFlow         |
| Repository    | Data Abstraction + Logic        | Kotlin + Hilt                 |
| Network       | API Communication               | Retrofit + Moshi              |
| Domain Model  | Clean mapped data               | DTO → Mapper → Domain Model   |
| Testing       | Unit test coverage & mocks      | JUnit, Truth, Robolectric     |

Related docs:

- [Architecture Overview](docs/architecture.md)
- [User Stories](docs/user_stories.md)
- [Developer Notes](docs/dev_notes.md)
- [Kanban Board](https://github.com/users/SadaqueKhanProjects/projects/1/views/1)

---

## Tech Stack

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

## Setup Instructions

```bash
git clone https://github.com/SadaqueKhanProjects/JustEatAndroidAssessment.git
cd JustEatAndroidAssessment
```

Then:

1. Open the project in Android Studio
2. Let Gradle sync
3. Run on emulator or physical device (tested on Pixel 6 and Samsung S22 Ultra)

---

## Assumptions

- **Cuisine Filtering**: A whitelist removes noisy/informal entries like "Unknown" or "Kitchen".
- **Rating Handling**: Null ratings are shown as "Not rated yet" rather than defaulting to a numeric value.
- **Name Cleanup**: Symbols, handles, and redundant dashes/parentheses are removed when distracting from the display.
- **Address Format**: Constructed from `firstLine`, `city`, and `postalCode`, with standard UK formatting applied and de-duplicated city names where applicable.
- **Postcode Handling**: Postcodes are validated using a **comprehensive regex** but are **not all-encompassing** because of the varied standards across different UK postcodes. The regex removes spaces and converts to uppercase for compatibility with the Just Eat API.

---

## Future Improvements

- Support **pagination** for broader result coverage beyond the first 10 entries
- Add **dialog-based error handling** with retry/refresh actions
- Expand **unit and UI test coverage** for edge cases and input validation logic
- Improve **name parsing** using NLP or branded keyword datasets
- **Postcode Validation Enhancement**: The current regex covers most valid UK postcodes but may not encompass all edge cases. In the future, it will integrate a more comprehensive validation solution using a third-party **postcode validation service** or an **up-to-date postcode database** to ensure complete coverage of all possible formats.
- Cuisine Data Management: Instead of hardcoding valid cuisines, the list will be cached in a local database (e.g., Room or SharedPreferences) and dynamically updated via an API or offline resource. This approach ensures the list stays up-to-date, scalable, and improves performance without requiring code changes.
- Responsive UI for Different Devices: The app's UI will be improved to be fully responsive across different devices and screen sizes. This will involve more advanced layout techniques and testing to ensure that UI elements render consistently, providing the same user experience on various devices.

---

## Key Implementation Details

- **Postcode Encoding**: All postcodes are validated using regex and then encoded using `URLEncoder`, ensuring compatibility with the Just Eat API endpoint.
- **Repository Logic**: The repository handles API communication, response validation, and maps raw DTOs into clean domain models using a dedicated mapper.
- **Error Handling**: Differentiates between timeout, network issues, and unexpected API responses. Errors are logged and surfaced with tailored messages.
- **Testing Strategy**:  
  Comprehensive testing was applied across layers using fake dependencies and coroutine test tooling:
  - Unit tests: Core logic in the mapper, repository, and ViewModel (`RestaurantMapperTest.kt`, `RestaurantRepositoryImplTest.kt`, `RestaurantViewModelTest.kt`)
  - UI tests: Compose-based test for the `RestaurantScreen` with a fake ViewModel to simulate screen states and verify components like `SearchBar` and `RestaurantItem` respond correctly.  
    While not full integration tests, this ensured end-to-end behavior from ViewModel to UI was validated under realistic scenarios.

---

## Device Compatibility

- Pixel 6 Emulator (API 34)
- Samsung S22 Ultra (API 34)

Renders cleanly across modern screen sizes and densities.

---

## Assessment Criteria Mapping

| Requirement                           | Implementation Status           |
|--------------------------------------|----------------------------------|
| Name                                 | ✅ Displayed, cleaned            |
| Cuisines                             | ✅ Whitelist-filtered            |
| Rating (as number)                   | ✅ Conditionally shown if present |
| Address                              | ✅ Merged and cleaned properly   |
| Kotlin usage                         | ✅ Fully written in Kotlin       |
| Git usage visible                    | ✅ Maintained commit history     |
| Build/run instructions               | ✅ Included in setup section     |
| Assumptions & improvements noted     | ✅ Included in dedicated sections |
| API: `GET /bypostcode/{postcode}`    | ✅ Integrated and tested         |
| Testing                              | ✅ Unit tested core components   |

---

## License

This application was developed solely for the **Just Eat Takeaway Android Coding Assessment**.  
It is not intended for commercial deployment or distribution.
