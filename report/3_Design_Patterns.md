# Part 3 - Design Patterns

## Discussion and Explanation on Design Patterns

This section discuss and explain each of the design patterns applied to this Music Ensembles Management System.

---

## 1. Command Pattern

### What is Command Pattern

Command Pattern is behavioral design pattern that encapsulate a request as an object. This pattern turn operations into stand-alone objects that contain all information about the operation.

### Implementation in MEMS

**Command Interface:**
```java
public interface Command {
    void execute();
    void undo();
    String getDescription();
    void setManager(EnsembleService manager);
}
```

The system implement 11 concrete command classes:
- `MakeEnsembleCmd`, `AddMusicianCmd`, `DeleteMusicianCmd`, `ChangeInstrumentCmd`, `ChangeNameCmd`
- `SetCurrentEnsembleCmd`, `ShowEnsembleCmd`, `ShowAllEnsemblesCmd`, `ShowHistoryCmd`
- `UndoCommand`, `RedoCommand`

Some commands also implement `EnsembleCommand` interface which extend Command and add getEnsemble() method.

**Invoker (EnsembleService):**
```java
public class EnsembleService {
    private final Stack<Command> history;
    private final Stack<Command> undoneCommands;
    
    public void execute(Command command) {
        undoneCommands.clear();
        command.execute();
        history.push(command);
    }
}
```

EnsembleService maintain two stacks for undo/redo functionality.

### How It Work

1. CommandParser create appropriate Command object from user input
2. For modification commands (create, add, delete, change), Main call manager.execute() which store command in history
3. For query commands (se, sa, l) and control commands (u, r), Main call command.execute() directly without storing in history
4. When undo is called, command pop from history and execute undo()
5. Undone commands can be redone

This design allow modification commands to be undone while query commands don't affect undo/redo history.

### Benefits

- Each operation is separate class with own logic
- Easy to implement undo/redo functionality for modification commands
- New commands can add but require modify CommandParser switch statement
- Main class don't need know implementation details of each operation

---

## 2. Factory Pattern

### What is Factory Pattern

Factory Pattern is creational design pattern that provide interface for creating objects without specify their exact classes.

### Implementation in MEMS

**CommandParser Factory:**
```java
public class CommandParser {
    private final EnsembleService manager;
    private final Scanner scanner;
    
    public Command createCommand(String commandCode) {
        switch (commandCode) {
            case "c": return makeEnsembleCmd();
            case "a": return addMusicianCmd();
            case "m": return changeInstrumentCmd();
            case "d": return deleteMusicianCmd();
            // ... other cases
            default: return null;
        }
    }
}
```

Factory have helper methods to create complex commands and collect user input. This is simple factory pattern implementation where factory handle both object creation and user input collection.

### How It Work

1. Main class receive command code from user
2. CommandParser's createCommand() is called
3. Factory use switch statement to determine which command to create
4. Factory collect necessary parameters from user (using Scanner)
5. Factory return ready-to-execute Command object

### Benefits and Limitations

**Benefits:**
- All object creation logic in one place
- Main class don't need know how to create each command
- User input handling separated from Main class

**Limitations:**
- Factory use switch statement, so adding new command require modify factory code (not fully follow Open-Closed Principle)
- Factory have dual responsibility: create objects and collect user input (coupling UI and creation logic)
- This is acceptable for assignment simplification but can be improved by separate concerns

---

## 3. Memento Pattern

### What is Memento Pattern

Memento Pattern is behavioral design pattern that let you save and restore previous state of an object without reveal implementation details.

### Implementation in MEMS

**Memento Classes:**
```java
public class MusicianState {
    private final int role;
    private final Musician musician;
    
    public MusicianState(Musician musician) {
        this.musician = musician;
        this.role = musician.getRole();
    }
    
    public void restore() {
        musician.setRole(role);
    }
}
```

```java
public class EnsembleState {
    private final String name;
    private final Ensemble ensemble;
    
    public EnsembleState(Ensemble ensemble) {
        this.ensemble = ensemble;
        this.name = ensemble.getName();
    }
    
    public void restore() {
        ensemble.setName(name);
    }
}
```

**Usage in Commands:**
```java
public class ChangeInstrumentCmd implements EnsembleCommand {
    private MusicianState memento;
    
    public void execute() {
        memento = new MusicianState(musician);  // save state
        musician.setRole(newInstrument);         // change state
    }
    
    public void undo() {
        memento.restore();  // restore old state
    }
}
```

### How It Work

1. Before modify object, command create memento to save current state
2. Memento store reference to original object and old values
3. Command execute and change object state
4. When undo called, memento restore old state

### Benefits

- Object internal state remain private
- Easy to restore previous state
- Undo logic separate from business logic
- Don't need expose object internals

---

## How Patterns Work Together

The three patterns work together in this workflow:

**For Modification Commands (create, add, delete, change):**
```
User Input → Factory create Command → Command save state with Memento (if needed)
          → Main call manager.execute(command)
          → Command execute and store in history stack
          → User type "u" → Undo pop command from history
          → Command use Memento to restore old state (for change operations)
```

**For Query Commands (show ensemble, show all, list history):**
```
User Input → Factory create Command → Main call command.execute() directly
          → Command execute (no history storage)
          → Command undo() is empty (no-op)
```

**For Control Commands (undo, redo):**
```
User Input "u" or "r" → Factory create UndoCommand or RedoCommand
                     → Main call command.execute() directly
                     → Command manipulate history stacks in EnsembleService
```

**Summary:**

| Pattern | Role | Key Classes |
|---------|------|-------------|
| Command | Encapsulate operations, enable undo/redo for modification commands | Command interface, EnsembleCommand interface, 11 concrete commands, EnsembleService |
| Factory | Create command objects and collect user input | CommandParser |
| Memento | Save and restore state for change operations | MusicianState, EnsembleState |

**Key Points:**
- Not all commands go through EnsembleService.execute() - only modification commands do
- Query and control commands execute directly but still implement Command interface
- Factory use switch statement which require modification when add new command type
- Factory handle both object creation and user input (dual responsibility for simplification)

These three design patterns was applied according to assignment requirements. They solve specific problems: Factory solve object creation, Command solve undo/redo for modifications, Memento solve state preservation for change operations.
