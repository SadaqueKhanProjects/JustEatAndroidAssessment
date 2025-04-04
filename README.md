
# JustEatAndroidAssessment

A Kotlin-based Android app developed for the Just Eat Takeaway.com Early Careers Mobile Engineering Program.

This assignment demonstrates **API integration**, **MVVM architecture**, **Jetpack Compose UI**, and **agile-style planning** — focused on retrieving and displaying restaurant data based on a UK postcode.

---

## Candidate Info

- **Name:** Sadaque Khan
- **GitHub:** [github.com/SadaqueKhanProjects](https://github.com/SadaqueKhanProjects)

---

## Assignment Objective

Using Just Eat’s API:

- Fetch restaurant data for a given UK postcode
- Extract and display 4 key data points:
  - Restaurant Name
  - Rating / Stars
  - Cuisine Type
  - Address (UK Format)

---

## Architecture & Planning

- Architecture Doc: [`docs/architecture.md`](docs/architecture.md)
- User Stories: [`docs/user_stories.md`](docs/user_stories.md)
- Task Board: [GitHub Project](https://github.com/SadaqueKhanProjects/JustEatAndroidAssessment/projects)

---

## Tech Stack

| Layer         | Tech Used                          |
|--------------|-------------------------------------|
| Language      | Kotlin                             |
| UI            | Jetpack Compose                    |
| Architecture  | MVVM (ViewModel + StateFlow)       |
| Networking    | Retrofit + Moshi                   |
| Asynchrony    | Kotlin Coroutines                  |
| Build System  | Gradle (Kotlin DSL)                |
| Min SDK       | 24 (Android 7.0)                   |
| IDE           | Android Studio (Electric Eel+)     |

---

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/SadaqueKhanProjects/JustEatAndroidAssessment.git
cd JustEatAndroidAssessment
```

### Open in Android Studio

- Open the project folder in Android Studio.
- Let Gradle sync finish.

### Run the App

- Select a device/emulator.
- Click Run ▶️ from Android Studio.

---

## 📊 Assumptions, Considerations & Solutions

---

### 🏷️ Restaurant Name

**Assumptions & Challenges:**
- Some restaurant names from the API included **non-essential or noisy tokens** such as:
  - Trailing dashes: `“Bento Box -”`
  - Embedded address fragments: `“Sushi Daily - London Bridge”`
  - Social media handles or branding noise

**Implemented Solution:**
- Applied **light name sanitization** using string processing to trim:
  - Trailing `-` and brackets
  - Obvious non-name fragments

**Considered but Not Implemented:**
- A **blacklist-based rule engine** was avoided due to **lack of full dataset access**.

**If Full Data Was Available:**
- Frequency-based detection could classify “junk” tokens.
- Normalize and group variants of restaurant brands.

---

### ⭐ Rating / Stars

**Assumptions & Challenges:**
- API returns a `starRating` float inside a `rating` object reliably.

**Implemented Solution:**
- Used directly with **no transformations or assumptions**.

---

### 🍽️ Cuisine Type

**Assumptions & Challenges:**
- Inconsistent or ambiguous cuisine values from the API.

**Implemented Solution:**
- **Whitelist-based filtering** applied using well-known cuisines (e.g., “Pizza”, “Halal”, “Mexican”).

**Why Whitelist:**
- Finite number of logical cuisines.
- Executing all postcodes to gather more data was **not feasible** due to time limits and API restrictions.

**Attempted Data Expansion:**
- Tried querying alternative endpoints for metadata or broader results.
- Received `403 Forbidden` — likely due to public API protection and authentication barriers.

**Ideal Future Approach:**
- Use full metadata or schema to dynamically validate and group cuisines.

---

### 🧭 Address (UK Format)

**Status:**
- Address data (`firstLine`, `city`, `postalCode`) was **clean and properly structured**.
- No special sanitization required beyond spacing adjustments.

---

## Device Compatibility & Visual Feedback

- Tested on:
  - **Pixel 6 Emulator (API 34)**
  - **Samsung S22 Ultra (API 14)**
- Differences in UI appearance (e.g. spacing, font size) are expected due to:
  - Varying screen densities
  - System font scaling
  - OEM-level theming

---

## 🔧 Suggested Improvements

- **Consistent UI Across Devices**  
  Some UI components (like the search bar and error messages) may appear differently depending on screen size, resolution, or font scaling settings. Using a more standardized spacing and typography system via `MaterialTheme` ensures a **consistent and polished experience**, no matter what phone the user is on.

- **More Intuitive Error Feedback**  
  Presenting error messages via **dialogs or snackbars**—instead of inline text—mirrors how most apps provide feedback. This instantly communicates that “something went wrong” in a way that’s **familiar, unobtrusive, and human-centric**, reducing the chance users miss important messages.

- **UI Confidence Across Devices**  
  The app was tested on:
  - Pixel 6 Emulator (API 34)
  - Samsung S22 Ultra (API 14)

  To ensure a more universal experience, further testing on modern devices with varied screen densities would help catch any unexpected layout shifts or usability issues.

---