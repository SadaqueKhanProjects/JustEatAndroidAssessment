# 🧩 User Stories – Just Eat Android Assessment

This document outlines agile-style user stories that guided the development process.

---

### 📦 Epic: Display Restaurants by Postcode

---

#### 🧪 Story 1: API Integration – Fetch restaurant data

**As a** user,  
**I want** to see a list of restaurants by entering a UK postcode,  
**So that** I can discover available delivery options.

**Effort**: 5 points  
**Tasks**:
- Setup Retrofit base URL and endpoints
- Parse JSON using Moshi
- Return list of restaurant data

---

#### 🎨 Story 2: UI – Render restaurant list

**As a** user,  
**I want** a clean, scrollable list of restaurants,  
**So that** I can browse and choose easily.

**Effort**: 3 points  
**Tasks**:
- Build Compose LazyColumn UI
- Display Restaurant Name, Cuisine, Rating, ETA
- Handle empty/postcode-not-found states

---

#### 🧱 Story 3: Architecture setup

**As a** developer,  
**I want** to separate layers with MVVM,  
**So that** the codebase is testable and scalable.

**Effort**: 2 points  
**Tasks**:
- Setup ViewModel + Repository
- Organise folders by domain/presentation/data
- Use StateFlow to drive UI state

---

#### 🧪 Story 4: Basic error handling

**As a** user,  
**I want** to be notified if the data fails to load,  
**So that** I’m not confused when nothing shows up.

**Effort**: 2 points  
**Tasks**:
- Handle API failure (404, timeout)
- Show snackbar or fallback message

---

> This list will grow and evolve with each feature branch and commit.
