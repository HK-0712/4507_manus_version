# Part 4 - Test Cases

## Test Cases Overview
This document contains test cases covering all system commands including create, modify, delete, query operations, and Undo/Redo functionality.

---

## Test Case 1: Create Orchestra Ensemble

**Input:**
```
c
o
E001
Vienna Orchestra
```

**Expected Output:**
```
Orchestra ensemble is created.
Current ensemble is changed to E001.
```

**Explanation:** Create an orchestra ensemble with ID E001 and name Vienna Orchestra

**Screenshot:** ![alt text](image.png)

---

## Test Case 2: Create Jazz Band Ensemble

**Input:**
```
c
j
J001
Blue Note Band
```

**Expected Output:**
```
Jazz band ensemble is created.
Current ensemble is changed to J001.
```

**Explanation:** Create a jazz band ensemble with ID J001 and name Blue Note Band

**Screenshot:** ![alt text](image-1.png)

---

## Test Case 3: Display All Ensembles

**Input:**
```
sa
```

**Expected Output:**
```
orchestra ensemble Vienna Orchestra (E001)
jazz band ensemble Blue Note Band (J001)
```

**Explanation:** List all ensembles in the system

**Screenshot:** ![alt text](image-2.png)

---

## Test Case 4: Set Current Ensemble

**Input:**
```
s
E001
```

**Expected Output:**
```
Changed current ensemble to E001.
```

**Explanation:** Switch current working ensemble to E001

**Screenshot:** ![alt text](image-3.png)

---

## Test Case 5: Add Musician to Orchestra

**Precondition:** Current ensemble is E001 (Orchestra)

**Input:**
```
a
M001, John Smith
1
```

**Expected Output:**
```
Musician is added.
```

**Explanation:** Add violinist John Smith (ID: M001) to orchestra, role code 1 represents violinist

**Screenshot:** ![alt text](image-4.png)

---

## Test Case 6: Add Musician to Jazz Band

**Precondition:** Current ensemble is J001 (Jazz Band)

**Input:**
```
s
J001
a
M002, Mary Johnson
1
```

**Expected Output:**
```
Changed current ensemble to J001.
Musician is added.
```

**Explanation:** Add saxophonist Mary Johnson (ID: M002) to jazz band, role code 1 represents saxophonist

**Screenshot:** ![alt text](image-5.png)

---

## Test Case 7: Show Ensemble Details

**Precondition:** Current ensemble has musicians

**Input:**
```
se
```

**Expected Output:**
```
Jazz Band Ensemble Blue Note Band (J001)
Pianist:
M002, Mary Johnson
Saxophonist:
NIL
Drummer:
NIL
```

**Explanation:** Display current ensemble details including all musicians and their roles

**Screenshot:** ![alt text](image-6.png)

---

## Test Case 8: Modify Musician's Instrument

**Precondition:** Current ensemble is J001, has musician M002

**Input:**
```
m
M002
2
```

**Expected Output:**
```
instrument is updated.
```

**Explanation:** Change musician M002's instrument from saxophonist to pianist (role code 2)

**Screenshot:** ![alt text](image-7.png)

---

## Test Case 9: Change Ensemble Name

**Precondition:** Current ensemble is J001

**Input:**
```
cn
Blue Note Jazz Ensemble
```

**Expected Output:**
```
Ensemble's name is updated.
```

**Explanation:** Change ensemble J001's name from "Blue Note Band" to "Blue Note Jazz Ensemble"

**Screenshot:** ![alt text](image-8.png)

---

## Test Case 10: Delete Musician

**Precondition:** Current ensemble has musician M002

**Input:**
```
d
M002
```

**Expected Output:**
```
Musician is deleted.
```

**Explanation:** Delete musician with ID M002 from current ensemble

**Screenshot:** ![alt text](image-9.png)

---

## Test Case 11: List Undo/Redo History

**Input:**
```
l
```

**Expected Output:**
```
Undo List
Create orchestra ensemble, E001, Vienna Orchestra
Create jazz band ensemble, J001, Blue Note Band
Add musician, M001, John Smith, violinist
Add musician, M002, Mary Johnson, pianist
Modify musician's instrument, M002, saxophonist
Change ensemble's name, J001, Blue Note Jazz
Delete musician, M002
-- End of undo list --

Redo List
-- End of redo list --
```

**Explanation:** Display all undoable and redoable commands

**Screenshot:** ![alt text](image-10.png)

---

## Test Case 12: Undo Command

**Precondition:** At least one undoable command executed

**Input:**
```
u
```

**Expected Output:**
```
Command (Delete musician, M002) is undone.
```

**Explanation:** Undo the last executed command (delete musician)

**Screenshot:** ![alt text](image-11.png)

---

## Test Case 13: Redo Command

**Precondition:** Undo has been executed

**Input:**
```
r
```

**Expected Output:**
```
Command (Delete musician, M002) is redone.
```

**Explanation:** Redo the previously undone command

**Screenshot:** ![alt text](image-12.png)

---

## Test Case 14: Multiple Undo Operations

**Input:**
```
u
u
u
```

**Expected Output:**
```
Command (Delete musician, M002) is undone.
Command (Change ensemble's name, J001, Blue Note Jazz) is undone.
Command (Modify musician's instrument, M002, saxophonist) is undone.
```

**Explanation:** Undo multiple operations consecutively

**Screenshot:** ![alt text](image-13.png)

---

## Test Case 15: Error Handling - Add Musician Without Current Ensemble

**Precondition:** System just started, no current ensemble set

**Input:**
```
a
```

**Expected Output:**
```
No current ensemble set.
```

**Explanation:** Test error handling when trying to add musician without current ensemble

**Screenshot:** ![alt text](image-14.png)

---

## Test Case 16: Error Handling - Set Non-existent Ensemble

**Input:**
```
s
E999
```

**Expected Output:**
```
Ensemble E999 is not found!!
```

**Explanation:** Test error handling when trying to set a non-existent ensemble ID

**Screenshot:** ![alt text](image-15.png)

---

## Test Case 17: Error Handling - Undo with Empty List

**Precondition:** System just started or undo list is empty

**Input:**
```
u
```

**Expected Output:**
```
Undo List is empty.
```

**Explanation:** Test error handling when undo list is empty

**Screenshot:** ![alt text](image-16.png)

---

## Test Case 18: Error Handling - Redo with Empty List

**Precondition:** No undo has been executed

**Input:**
```
r
```

**Expected Output:**
```
Redo List is empty.
```

**Explanation:** Test error handling when redo list is empty

**Screenshot:** ![alt text](image-17.png)

---

## Test Case 19: Exit System

**Input:**
```
x
```

**Expected Output:**
```
Exiting system.
```

**Explanation:** Normal system exit

**Screenshot:** ![alt text](image-18.png)

---