# 4. Test Plan and Test Cases

## 4.1 Test Strategy

The testing approach covers all main functions of the system including:
- Creating ensembles
- Managing musicians (add, modify, delete)
- Undo/redo operations
- Display functions
- Error handling

Each test case includes:
- Test Case ID
- Description
- Input steps
- Expected result
- Actual result (screenshot)
- Pass/Fail status

---

## 4.2 Test Cases

### TC001: Create Orchestra Ensemble
**Description:** Test creating a new orchestra ensemble  
**Input:**
```
Command: c
Type: o
Ensemble ID: E001
Ensemble Name: Symphony Orchestra
```
**Expected Result:**
- Message: "orchestra ensemble is created."
- Current ensemble changed to E001
- Can see "The current ensemble is E001 Symphony Orchestra"

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC002: Create Jazz Band Ensemble
**Description:** Test creating a new jazz band ensemble  
**Input:**
```
Command: c
Type: j
Ensemble ID: J001
Ensemble Name: Cool Jazz Band
```
**Expected Result:**
- Message: "jazz band ensemble is created."
- Current ensemble changed to J001

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC003: Set Current Ensemble
**Description:** Test switching between ensembles  
**Prerequisites:** Two ensembles exist (E001 and J001)  
**Input:**
```
Command: s
Ensemble ID: E001
```
**Expected Result:**
- Message: "Changed current ensemble to E001."
- Display shows "The current ensemble is E001 Symphony Orchestra"

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC004: Add Violinist to Orchestra
**Description:** Test adding a violinist to orchestra ensemble  
**Prerequisites:** Current ensemble is E001 (orchestra)  
**Input:**
```
Command: a
Musician info: M001, John Smith
Instrument: 1 (violinist)
```
**Expected Result:**
- Message: "Musician is added."

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC005: Add Cellist to Orchestra
**Description:** Test adding a cellist to orchestra ensemble  
**Prerequisites:** Current ensemble is E001 (orchestra)  
**Input:**
```
Command: a
Musician info: M002, Mary Johnson
Instrument: 2 (cellist)
```
**Expected Result:**
- Message: "Musician is added."

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC006: Add Pianist to Jazz Band
**Description:** Test adding a pianist to jazz band ensemble  
**Prerequisites:** Current ensemble is J001 (jazz band)  
**Input:**
```
Command: a
Musician info: J001, Charlie Parker
Instrument: 1 (pianist)
```
**Expected Result:**
- Message: "Musician is added."

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC007: Show Current Ensemble
**Description:** Test displaying current ensemble with musicians  
**Prerequisites:** E001 has musicians M001 (violinist) and M002 (cellist)  
**Input:**
```
Command: se
```
**Expected Result:**
- Display ensemble name and ID
- List musicians by instrument type
- Show "M001, John Smith" under Violinist
- Show "M002, Mary Johnson" under Cellist

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC008: Display All Ensembles
**Description:** Test listing all ensembles in system  
**Prerequisites:** E001 and J001 exist  
**Input:**
```
Command: sa
```
**Expected Result:**
- Display all ensembles with type, name, and ID
- Show "orchestra ensemble Symphony Orchestra (E001)"
- Show "jazz band ensemble Cool Jazz Band (J001)"

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC009: Modify Musician's Instrument
**Description:** Test changing a musician's instrument  
**Prerequisites:** M001 is violinist in E001  
**Input:**
```
Command: m
Musician ID: M001
New Instrument: 2 (cellist)
```
**Expected Result:**
- Message: "instrument is updated."
- M001 now appears under Cellist when show ensemble

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC010: Undo Modify Instrument
**Description:** Test undo function for instrument modification  
**Prerequisites:** Just modified M001 from violinist to cellist  
**Input:**
```
Command: u
```
**Expected Result:**
- Message: "Command (Modify musician's instrument, M001, cellist) is undone."
- M001 back to violinist

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC011: Change Ensemble Name
**Description:** Test changing ensemble's name  
**Prerequisites:** Current ensemble is E001  
**Input:**
```
Command: cn
New name: Royal Symphony Orchestra
```
**Expected Result:**
- Message: "Ensemble's name is updated."
- Display shows new name "Royal Symphony Orchestra"

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC012: Undo Change Name
**Description:** Test undo function for name change  
**Prerequisites:** Just changed name to "Royal Symphony Orchestra"  
**Input:**
```
Command: u
```
**Expected Result:**
- Message: "Command (Change ensemble's name, E001, Royal Symphony Orchestra) is undone."
- Name back to "Symphony Orchestra"

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC013: Redo Operation
**Description:** Test redo function  
**Prerequisites:** Just did undo on change name  
**Input:**
```
Command: r
```
**Expected Result:**
- Message: "Command (Change ensemble's name, E001, Royal Symphony Orchestra) is redone."
- Name changed back to "Royal Symphony Orchestra"

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC014: List Undo/Redo History
**Description:** Test displaying undo/redo list  
**Prerequisites:** Some commands executed  
**Input:**
```
Command: l
```
**Expected Result:**
- Show "Undo List" with all commands
- Show "Redo List" with available redo commands
- Display "-- End of undo list --" and "-- End of redo list --"

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC015: Delete Musician
**Description:** Test deleting a musician from ensemble  
**Prerequisites:** M002 exists in E001  
**Input:**
```
Command: d
Musician ID: M002
```
**Expected Result:**
- Message: "Musician is deleted."
- M002 no longer appears when show ensemble

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC016: Undo Delete Musician
**Description:** Test undo delete operation  
**Prerequisites:** Just deleted M002  
**Input:**
```
Command: u
```
**Expected Result:**
- Message: "Command (Delete musician, M002) is undone."
- M002 appears again in ensemble

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC017: Error - Invalid Ensemble Type
**Description:** Test error handling for invalid ensemble type  
**Input:**
```
Command: c
Type: x (invalid)
```
**Expected Result:**
- Message: "Invalid music type."
- No ensemble created

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC018: Error - Musician Not Found
**Description:** Test error when modifying non-existent musician  
**Prerequisites:** Current ensemble is E001  
**Input:**
```
Command: m
Musician ID: M999 (doesn't exist)
```
**Expected Result:**
- Message: "Musician not found in current ensemble."

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC019: Error - No Current Ensemble
**Description:** Test operations without setting current ensemble  
**Prerequisites:** No current ensemble set  
**Input:**
```
Command: a
```
**Expected Result:**
- Message: "No current ensemble set."
- Cannot add musician

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC020: Error - Empty Undo List
**Description:** Test undo when history is empty  
**Prerequisites:** No commands executed yet  
**Input:**
```
Command: u
```
**Expected Result:**
- Message: "Undo List is empty."

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC021: Multiple Undo/Redo
**Description:** Test multiple undo and redo operations  
**Prerequisites:** Execute add musician, modify instrument, change name  
**Input:**
```
Commands: u, u, u (undo 3 times)
Then: r, r (redo 2 times)
```
**Expected Result:**
- All three operations undone correctly
- First two operations redone correctly
- Command history maintained properly

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

### TC022: Exit System
**Description:** Test exiting the system  
**Input:**
```
Command: x
```
**Expected Result:**
- Message: "Exiting system."
- Program terminates

**Actual Result:** [Screenshot here]  
**Status:** Pass ☐ Fail ☐

---

## 4.3 Test Summary

Total Test Cases: 22

| Category | Number of Tests |
|----------|----------------|
| Create Operations | 2 |
| Add Musicians | 3 |
| Display Functions | 2 |
| Modify Operations | 2 |
| Delete Operations | 1 |
| Undo/Redo Functions | 6 |
| Error Handling | 5 |
| Integration Tests | 1 |

**Expected Pass Rate:** 100%  
**Actual Pass Rate:** ___ % (after testing)

---

## 4.4 Notes

- All screenshots should show both input and output
- Test in the order listed to satisfy prerequisites
- If any test fails, note the reason in actual result
- Error handling tests are important to show system robustness
