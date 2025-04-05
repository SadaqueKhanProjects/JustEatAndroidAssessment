# JustEatAndroidAssessment

A Kotlin-based Android application developed as part of the **Just Eat Takeaway.com Early Careers Mobile Engineering Program**.

This solution demonstrates **MVVM architecture**, **Jetpack Compose**, and **modular API-driven development** — enabling users to query and view restaurant data via UK postcode.

---

## Candidate

- **Name:** Sadaque Khan
- **GitHub:** [github.com/SadaqueKhanProjects](https://github.com/SadaqueKhanProjects)

---

## Overview

The project fulfills the following criteria:

- Query Just Eat’s API by postcode
- Extract and display:
  - ✅ Restaurant name
  - ✅ Cuisine types
  - ✅ Star rating (if available)
  - ✅ Address
- Render first 10 results using clean, scalable UI components

---

## Demo

GIF captured on **Samsung S22 Ultra**:

![Demo](docs/SamsungS22UltraDemo.gif)

---

## Architecture

| Layer            | Responsibility                            | Implementation                            |
|------------------|--------------------------------------------|--------------------------------------------|
| **Network**       | API communication                         | Retrofit + Moshi                           |
| **Repository**    | Abstract data source access               | Repository pattern w/ DI                   |
| **Domain Model**  | Clean, mapped restaurant entities         | DTO → Mapper → Domain                      |
| **State Layer**   | Manage UI state reactively                | Kotlin `StateFlow`                         |
| **UI Layer**      | Compose UI rendering and interaction      | Material3, reusable composables            |

> For architecture decisions, see [`docs/architecture.md`](docs/architecture.md)  
> For user-focused planning, see [`docs/user_stories.md`](docs/user_stories.md)  
> GitHub Kanban Board: [Just Eat Assessment Project](https://github.com/users/SadaqueKhanProjects/projects/1/views/1)

---

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM + Clean Architecture
- **UI Toolkit**: Jetpack Compose (Material3)
- **Async**: Coroutines
- **Networking**: Retrofit + Moshi
- **Dependency Injection**: Hilt
- **Testing**: JUnit + Android Instrumentation
- **Build System**: Gradle (Kotlin DSL)

---

## Setup

```bash
git clone https://github.com/SadaqueKhanProjects/JustEatAndroidAssessment.git
cd JustEatAndroidAssessment

Then:
	1.	Open in Android Studio (Electric Eel+)
	2.	Sync Gradle
	3.	Run app on emulator or device (API 24+)

⸻

Key Considerations

1. Restaurant Name
	•	Applied basic sanitation (e.g., trimmed trailing dashes, removed social handles)
	•	Avoided over-aggressive filtering to preserve identity
	•	Did not implement a full blacklist engine due to lack of exhaustive dataset

2. Cuisine Type
	•	Used whitelist-based inclusion of trusted values (e.g., Pizza, Halal)
	•	API inconsistency made untrusted cuisine entries unreliable
	•	Removed cuisines entirely if no value was present to avoid rendering [empty]

⸻

3. Rating / Stars
	•	Displayed only if rating object exists in API response
	•	No default values assigned (e.g., 0.0), which could misrepresent the restaurant

⸻

4. Address Formatting
	•	Most address data was well-structured (firstLine, city, postalCode)
	•	Performed light normalization for consistent spacing
	•	No advanced parsing or validation required

⸻

Limitations Due to API Access

Due to limited API access, metadata/schema information was unavailable. This restricted:
	•	Dynamic classification of cuisines
	•	Validation rule generation
	•	Error bucket mapping

⸻

Why More Data Would Help

Having access to:
	•	✅ Master list of cuisines
	•	✅ Metadata schema (field length, type)
	•	✅ Known edge cases or error entry formats

…would improve validation, clustering, and output quality.

⸻

Ideal Cuisine Handling Flow

+--------------------------+
|  Full Dataset/API        |
|  Access Granted          |
+--------------------------+
           |
           v
+--------------------------+     Group all cuisines
| Extract All Unique       |--------------------------------------+
| Cuisines                 |                                      |
+--------------------------+                                      |
           |                                                     v
+--------------------------+     +-----------------------------+ 
| Cluster by Tags          | --> | Create Master Taxonomy      | 
| / Frequencies            |     | (e.g., Pizza, Indian)       | 
+--------------------------+     +-----------------------------+ 
           |                               |                        
           v                               v                        
+--------------------------+     +-----------------------------+ 
| Validate with Metadata   |     | Map Unknown to Closest      | 
| or API Schema            |     | or “Other” bucket           | 
+--------------------------+     +-----------------------------+ 



⸻

Example Limitation

API Request        →   Sanitization       →   Domain Model
/restaurants       →   “Pizza Hut–London” →   “Pizza Hut”
                   →   “French/??!”       →   [excluded]



⸻

UI Testing & Compatibility
	•	Tested on:
	•	✅ Pixel 6 Emulator (API 34)
	•	✅ Samsung S22 Ultra (API 14)
	•	Jetpack Compose + Material3 ensures wide compatibility and theme consistency

⸻

UX & Interaction Model
	•	Postcode Validation: Enforced via regex + length constraints (5–8 alphanum)
	•	Loading Indicators: Shown during network calls
	•	Error Handling: Differentiates no results vs. timeout vs. invalid input
	•	Extensibility: ViewModel and state layers easily accept enhancements

⸻

Why Features Weren’t Added

This solution focused on core requirements. Additional features such as:
	•	Sorting
	•	Filtering
	•	Infinite scroll
	•	State persistence

…were not added to stay within brief scope. The underlying codebase is:
	•	Modular (separated by feature and domain)
	•	Composable (easy to extend screens or models)
	•	Clean (minimal UI coupling)

So any future enhancement can plug into existing pipelines with ease.

⸻

License

This repository is part of an educational coding assessment. No commercial license included.

---
