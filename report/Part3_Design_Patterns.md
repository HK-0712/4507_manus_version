# Part 3 - Design Patterns Discussion

## Overview
This section explain the three design patterns used in MEMS and why they are important for the system.

---

## 1. Command Pattern

### 1.1 What is Command Pattern
Command Pattern turn a request into object. Each command object contain all information needed to execute the request.

### 1.2 Why We Use It
In MEMS, we have 11 different functions. Without Command Pattern, all logic will be put in Main class which is messy. Also, we need undo/redo functionality which is difficult without Command Pattern.

### 1.3 How We Implement It

**Command Interface:**
All commands implement this interface:
```java
public interface Command {
    void execute();      // do the action
    void undo();         // reverse the action
    String getDescription();  // for undo/redo list
    void setManager(EnsembleService manager);
}
```

**EnsembleCommand Interface:**
For commands that work on specific ensemble:
```java
public interface EnsembleCommand extends Command {
    Ensemble getEnsemble();
}
```

**11 Command Classes:**
1. MakeEnsembleCmd - create new ensemble
2. SetCurrentEnsembleCmd - set which ensemble to work on
3. AddMusicianCmd - add musician to ensemble
4. ChangeInstrumentCmd - change musician's instrument
5. DeleteMusicianCmd - remove musician from ensemble
6. ShowEnsembleCmd - display current ensemble
7. ShowAllEnsemblesCmd - show all ensembles
8. ChangeNameCmd - change ensemble name
9. ShowHistoryCmd - show undo/redo lists
10. UndoCommand - undo last command
11. RedoCommand - redo undone command

**Example - AddMusicianCmd:**
```java
public class AddMusicianCmd implements EnsembleCommand {
    private Ensemble ensemble;
    private Musician musician;
    
    public void execute() {
        ensemble.addMusician(musician);
    }
    
    public void undo() {
        ensemble.dropMusician(musician);
    }
}
```

### 1.4 Benefits
- Each command is separate class, easy to understand
- Can add new commands without change existing code
- Undo/redo become simple
- Main class is clean

---

## 2. Factory Pattern

### 2.1 What is Factory Pattern
Factory Pattern provide way to create objects without specify exact class. The factory decide which class to create.

### 2.2 Why We Use It
Creating command objects is complicated because different commands need different information. We want Main class to be simple and not worry about how to create commands.

### 2.3 How We Implement It

**CommandParser Class (Factory):**
```java
public class CommandParser {
    private EnsembleService manager;
    private Scanner scanner;
    
    public Command createCommand(String commandCode) {
        switch (commandCode) {
            case "c": return makeEnsembleCmd();
            case "a": return addMusicianCmd();
            case "m": return changeInstrumentCmd();
            // ... other commands
        }
    }
}
```

**Creating Different Objects:**

1. **Command Objects:**
   - All 11 commands created by CommandParser
   - Factory handle user input and create correct command

2. **Ensemble Objects:**
   ```java
   private Command makeEnsembleCmd() {
       // ask user for type
       if (type == "o") {
           OrchestraEnsemble orchestra = new OrchestraEnsemble(id);
           return new MakeEnsembleCmd(..., orchestra, ...);
       } else {
           JazzBandEnsemble jazzBand = new JazzBandEnsemble(id);
           return new MakeEnsembleCmd(..., jazzBand, ...);
       }
   }
   ```

3. **Musician Objects:**
   ```java
   private Command addMusicianCmd() {
       Musician m = new Musician(id);
       m.setName(name);
       m.setRole(role);
       return new AddMusicianCmd(ensemble, m);
   }
   ```

### 2.4 Benefits
- Main class don't need know how to create commands
- All creation logic in one place
- Easy to add new command types
- User input handling separate from Main

---

## 3. Memento Pattern

### 3.1 What is Memento Pattern
Memento Pattern save object's state so we can restore it later. It's like taking snapshot of object before change it.

### 3.2 Why We Use It
For undo function, we need save state before make changes. When user want undo, we restore the saved state.

Assignment require Memento Pattern for:
- Modify musician's instrument
- Change ensemble's name

### 3.3 How We Implement It

**MusicianState Class:**
```java
public class MusicianState {
    private int role;
    private Musician musician;
    
    public MusicianState(Musician musician) {
        this.musician = musician;
        this.role = musician.getRole();  // save current role
    }
    
    public void restore() {
        musician.setRole(role);  // restore old role
    }
}
```

**EnsembleState Class:**
```java
public class EnsembleState {
    private String name;
    private Ensemble ensemble;
    
    public EnsembleState(Ensemble ensemble) {
        this.ensemble = ensemble;
        this.name = ensemble.getName();  // save current name
    }
    
    public void restore() {
        ensemble.setName(name);  // restore old name
    }
}
```

**Using Memento in Commands:**

**ChangeInstrumentCmd:**
```java
public class ChangeInstrumentCmd implements EnsembleCommand {
    private MusicianState memento;
    
    public void execute() {
        memento = new MusicianState(musician);  // save state
        musician.setRole(newRole);  // change role
    }
    
    public void undo() {
        memento.restore();  // restore old role
    }
}
```

**ChangeNameCmd:**
```java
public class ChangeNameCmd implements EnsembleCommand {
    private EnsembleState memento;
    
    public void execute() {
        memento = new EnsembleState(ensemble);  // save state
        ensemble.setName(newName);  // change name
    }
    
    public void undo() {
        memento.restore();  // restore old name
    }
}
```

### 3.4 Benefits
- Save and restore state easily
- Command classes don't need know internal details of Ensemble/Musician
- Clean separation between command and domain objects
- Easy to add more memento for other operations

---

## 4. How Patterns Work Together

All three patterns work together to make system flexible:

**User Input Flow:**
1. User type command → Main receive input
2. Main ask CommandParser (Factory) to create command
3. CommandParser create correct Command object
4. Main execute the command

**Execution with Memento:**
1. Command execute() called
2. Command create Memento to save current state
3. Command make changes
4. If user undo, Command use Memento to restore state

**History Management:**
1. EnsembleService execute commands
2. Commands stored in history stack
3. User can undo to go back
4. Undone commands move to redo stack

---

## 5. Design Benefits

**Maintainability:**
- Each pattern has clear responsibility
- Easy to understand and modify

**Extensibility:**
- Can add new ensemble types easily
- Can add new commands if needed (though not required)
- Follow Open-Closed Principle

**Testability:**
- Each command can be test separately
- Easy to write unit tests

---

## 6. Summary

| Pattern | Purpose | Main Classes |
|---------|---------|--------------|
| Command | Make functions as objects | Command interface, 11 command classes |
| Factory | Create objects | CommandParser |
| Memento | Save/restore state | MusicianState, EnsembleState |

These patterns not just for assignment requirement. They actually solve real problems and make code better organized.
