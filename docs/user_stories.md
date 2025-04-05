# 🧩 User Stories – Just Eat Android Assessment

This document outlines agile-style user stories and tasks that guided the development process using GitHub Projects.

---

## 📦 Epic: Display Restaurants by Postcode

---

### 🧪 Story 1: API Integration – Fetch restaurant data

**As a** user,  
**I want** to see a list of restaurants by entering a UK postcode,  
**So that** I can discover available delivery options.

**Effort**: 5 points  
**Tasks**:
- Confirm Just Eat endpoint structure via Postman
- Setup Retrofit client and base URL
- Create API interface and DTO models
- Parse JSON using Moshi
- Validate API response by logging raw output
- Wire data to repository and domain model

---

### 🎨 Story 2: UI – Render restaurant list

**As a** user,  
**I want** a clean, scrollable list of restaurants,  
**So that** I can browse and choose easily.

**Effort**: 3 points  
**Tasks**:
- Build Compose LazyColumn for displaying restaurant items
- Show restaurant Name, Cuisine(s), and Rating
- Limit visible list to top 10 results
- Render fallback message when no restaurants found
- Add mock data for design-time preview testing

---

### 🧱 Story 3: Architecture & MVVM setup

**As a** developer,  
**I want** to separate logic across clean architecture layers,  
**So that** the code remains scalable and maintainable.

**Effort**: 3 points  
**Tasks**:
- Create ViewModel and connect to repository
- Use StateFlow to expose UI state to Composables
- Organize folders by domain, data, and presentation
- Ensure proper dependency injection via Hilt

---

### ⚠️ Story 4: Error Handling & UX Feedback

**As a** user,  
**I want** to be informed when things go wrong,  
**So that** I understand why results may not appear.

**Effort**: 2 points  
**Tasks**:
- Catch network errors (IOException, Timeout, etc.)
- Display error messages clearly via composables
- Prevent "No restaurants found" from appearing on first load
- Add invalid postcode handling and custom validation rules

---

### 🧪 Story 5: Postcode Validation Logic

**As a** user,  
**I want** to be warned when I enter an invalid postcode,  
**So that** I don’t unknowingly trigger broken API calls.

**Effort**: 1 point  
**Tasks**:
- Sanitize and validate postcodes: alphanumeric, 5–8 characters
- Disallow symbols and malformed input
- Trigger meaningful feedback (invalid postcode error message)

---