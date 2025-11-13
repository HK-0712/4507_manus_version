# Part 2 - Application Design

## System Architecture

The Music Ensembles Management System (MEMS) is design using object-oriented principles with several design patterns to make code more flexible and maintainable.

## Class Diagram

*(Please refer to 2_Class_diagram.pdf in the same folder)*

## Main Components

### 1. Main Application Layer
- **Main.java**: Entry point of program, handle user interface and main loop
- **EnsembleService.java**: Central manager that coordinate all operations and maintain system state

### 2. Ensemble Layer
- **Ensemble.java**: Abstract base class for all ensemble types
- **OrchestraEnsemble.java**: Concrete class for orchestra ensemble with violinist and cellist
- **JazzBandEnsemble.java**: Concrete class for jazz band with pianist, saxophonist, and drummer

### 3. Musician Layer
- **Musician.java**: Represent individual musician with ID, name and role/instrument

### 4. Command Layer
- **Command.java**: Interface define command operations
- **EnsembleCommand.java**: Interface for commands that operate on ensemble
- **CommandParser.java**: Factory class create command objects from user input
- Concrete command classes:
  - **MakeEnsembleCmd.java**: Create new ensemble
  - **AddMusicianCmd.java**: Add musician to ensemble
  - **ChangeInstrumentCmd.java**: Change musician instrument
  - **ChangeNameCmd.java**: Change ensemble name
  - **DeleteMusicianCmd.java**: Remove musician from ensemble
  - **SetCurrentEnsembleCmd.java**: Switch current ensemble
  - **ShowEnsembleCmd.java**: Display current ensemble
  - **ShowAllEnsemblesCmd.java**: Display all ensembles
  - **ShowHistoryCmd.java**: Show undo/redo history
  - **UndoCommand.java**: Undo last operation
  - **RedoCommand.java**: Redo undone operation

### 5. State Management Layer
- **EnsembleState.java**: Store ensemble state for memento pattern
- **MusicianState.java**: Store musician state for memento pattern