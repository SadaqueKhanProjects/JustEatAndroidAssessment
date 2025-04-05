# 📘 User Stories – Just Eat Android Assessment

This document outlines agile-style user stories and tasks that guided the development process using GitHub Projects.

---

##  Display Restaurants by Postcode given an API endpoint

---

### Story 1 – API Integration: Fetch Restaurant Data

**Role**: User  
**Goal**: See a list of restaurants by entering a UK postcode  
**Benefit**: Discover available delivery options

**Effort**: 5 points

**Tasks**:
- Confirm Just Eat endpoint structure via Postman
- Set up Retrofit client and base URL
- Create API interface and DTO models
- Parse JSON using Moshi
- Validate API response by logging raw output
- Wire data to repository and domain model

---

### Story 2 – UI Implementation: Render Restaurant List

**Role**: User  
**Goal**: View a clean, scrollable list of restaurants  
**Benefit**: Easily browse and make a choice

**Effort**: 3 points

**Tasks**:
- Build Compose LazyColumn to display restaurant items
- Show restaurant name, cuisines, and rating
- Limit visible list to top 10 results
- Render fallback message when no restaurants are found
- Add mock data for design-time preview testing

---

### Story 3 – Architecture Foundation: Set Up MVVM Layers

**Role**: Developer  
**Goal**: Separate logic using clean architecture  
**Benefit**: Ensure the codebase is scalable and maintainable

**Effort**: 3 points

**Tasks**:
- Create ViewModel and connect it to repository
- Use StateFlow to expose UI state to composables
- Organize folders into domain, data, and presentation layers
- Set up dependency injection using Hilt

---

### Story 4 – Error Handling: UX and Failure Feedback

**Role**: User  
**Goal**: Receive feedback when something goes wrong  
**Benefit**: Understand why no results are shown

**Effort**: 2 points

**Tasks**:
- Catch network errors such as IOExceptions and timeouts
- Display meaningful error messages via composables
- Avoid showing "No restaurants found" on initial load
- Add validation and error handling for invalid postcodes

---

### Story 5 – Input Validation: Handle Postcode Errors

**Role**: User  
**Goal**: Be warned when entering an invalid postcode  
**Benefit**: Prevent unnecessary or broken API calls

**Effort**: 1 point

**Tasks**:
- Sanitize and validate input (alphanumeric, 5–8 characters)
- Disallow symbols and malformed formats
- Show a clear error message for invalid postcodes

---
