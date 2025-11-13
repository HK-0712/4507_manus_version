# Part 2 - Application Design with Class Diagram

## Overview
This section present the system architecture and class diagram for Music Ensemble Management System (MEMS).

---

## 1. System Architecture

The system is designed with clear separation of concerns:

**Main Components:**
- **Main**: Handle user interface and input/output
- **CommandParser**: Factory for creating command objects
- **EnsembleService**: Manage ensembles and command history
- **Command Classes**: Implement 11 different functions
- **Ensemble Classes**: Represent different types of music ensembles
- **Memento Classes**: Store state for undo/redo operations

---

## 2. Class Diagram

![pdf](report/2_Class_Diagram.pdf)

The class diagram show the relationships between all classes in the system.

---