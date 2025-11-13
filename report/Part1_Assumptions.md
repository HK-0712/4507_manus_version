# Part 1 - Assumptions

## Overview
This document describe the assumptions I made when developing the Music Ensemble Management System (MEMS).

---

## 1. System Scope Assumptions

**Ensemble Types:**
- The system support two types of ensembles: Orchestra and Jazz Band
- Orchestra has 2 instrument roles: Violinist and Cellist
- Jazz Band has 3 instrument roles: Pianist, Saxophonist, and Drummer

**User Operations:**
- User interact with system through command-line interface
- User can create ensembles, add/delete/modify musicians, and perform undo/redo operations
- System must have a "current ensemble" set before performing operations on musicians

---

## 2. Data Management Assumptions

**Ensemble Management:**
- Each ensemble have unique ID (provided by user when creating ensemble)
- User must set current ensemble before add or modify musicians
- System can manage multiple ensembles at same time

**Musician Management:**
- Each musician have unique ID and name within their ensemble
- Musicians belong to only one ensemble (cannot be shared)
- When adding musician, user provide ID and name, then select instrument role

---

## 3. Command Pattern Assumptions

**Command Execution:**
- All 11 functions (create, set, add, modify, delete, show, display, change name, undo, redo, list) are implemented as Command objects
- Commands that modify data can be undone
- Read-only commands (show, display, list) cannot be undone

**Undo/Redo Mechanism:**
- System maintain history of executed commands for undo
- When user execute new command after undo, redo history is cleared
- Undo and redo operations switch the current ensemble automatically when needed

---

## 4. Design Constraints

**From Assignment Requirements:**
- Cannot modify existing classes: Ensemble, OrchestraEnsemble, JazzBandEnsemble, Musician
- Must use Command Pattern for all 11 functions
- Must use Factory Pattern to create Command and Ensemble/Musician objects
- Must use Memento Pattern for undo/redo on "modify instrument" and "change name" functions

**System Design:**
- System should follow Open-Closed Principle to easily add new ensemble types (like Rock Band)
- All data stored in memory only (no database or file persistence)
- System exit when user type 'x' command

---

## 5. Error Handling Assumptions

**Invalid Input:**
- If user enter invalid command, system show error message and ask again
- If user try to add musician without current ensemble, system show error
- If user enter invalid instrument role number, system show error

**Undo/Redo Errors:**
- If undo list is empty, system display "Undo List is empty"
- If redo list is empty, system display "Redo List is empty"

---

## 6. Output Format Assumptions

**Display Format:**
- Ensemble information shown as: "type name (ID)"
- Musician information shown as: "ID, name (role)"
- All musicians grouped by their instrument role when displaying ensemble

**Success Messages:**
- When create ensemble: "Orchestra ensemble is created" or "Jazz band ensemble is created"
- When add musician: "Musician is added"
- When modify instrument: "instrument is updated"
- When delete musician: "Musician is deleted"
- When change name: "Ensemble's name is updated"
