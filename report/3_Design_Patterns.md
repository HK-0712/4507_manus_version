# 3. Discussion and Explanation on Design Patterns

In this system, I applied three design patterns: Command Pattern, Factory Pattern, and Memento Pattern.

---

## 3.1 Command Pattern

### Why I Used It

Need to support undo/redo operations. Command pattern encapsulate each operation as object, so can store in history and execute/undo when needed.

### How It Works

**Components:**

1. **Command Interface** - defines `execute()`, `undo()`, `getDescription()`, `setManager()`

2. **Concrete Commands:**
   - CreateEnsembleCommand
   - AddMusicianCommand
   - ModifyInstrumentCommand
   - DeleteMusicianCommand
   - ChangeNameCommand

3. **Invoker (EnsembleManager)** - maintains two stacks: `history` and `redoStack`. Execute command and push to history. Pop from history when undo.

4. **Receiver (Ensemble, Musician)** - actual objects that do the work.

### Example Flow

```
User input "a" → Factory creates AddMusicianCommand
              → Manager.executeCommand()
              → command.execute() → ensemble.addMusician()
              → Push to history

User input "u" → Manager.undo()
              → Pop from history
              → command.undo() → ensemble.dropMusician()
              → Push to redoStack
```

### Benefits

- Easy undo/redo support
- Separate user input from actual operations
- Easy to add new commands
- Keep command history

---

## 3.2 Factory Pattern

### Why I Used It

Centralize object creation logic. Hide creation details and make easy to add new types.

### What It Creates

**CommandFactory creates:**

1. **Command Objects** based on user input:
   - "c" → CreateEnsembleCommand
   - "a" → AddMusicianCommand
   - "m" → ModifyInstrumentCommand
   - "d" → DeleteMusicianCommand
   - "cn" → ChangeNameCommand

2. **Ensemble Objects** based on type:
   - "o" → OrchestraEnsemble
   - "j" → JazzBandEnsemble

3. **Musician Objects** with correct instrument roles

### Implementation

Use switch statement in `createCommand()` method:
```java
switch (commandCode) {
    case "c": return createEnsembleCommand();
    case "a": return addMusicianCommand();
    // ...
}
```

Each method handles specific creation and input validation.

### Benefits

- Centralized creation logic
- Type safety
- Easy to extend
- Input validation in one place
- Loose coupling

---

## 3.3 Memento Pattern

### Why I Used It

Need to save old state for undo on "modify instrument" and "change name" operations. Memento pattern saves state without breaking encapsulation.

### How It Works

**Components:**

1. **Originators:**
   - `Musician` - saves/restores instrument role
   - `Ensemble` - saves/restores name

2. **Mementos (inner classes):**
   - `Musician.MusicianMemento` - stores role (int)
   - `Ensemble.EnsembleMemento` - stores name (String)
   
   Both use `private` constructor and getter, only originator can access.

3. **Caretakers (Commands):**
   - `ModifyInstrumentCommand` - saves MusicianMemento
   - `ChangeNameCommand` - saves EnsembleMemento

### Implementation

**ModifyInstrumentCommand:**
```java
private Musician.MusicianMemento memento;

execute() {
    memento = musician.save();    // Save old
    musician.setRole(newrole);    // Change
}

undo() {
    musician.restore(memento);    // Restore
}
```

**ChangeNameCommand:**
```java
private Ensemble.EnsembleMemento memento;

execute() {
    memento = ensemble.save();    // Save old
    ensemble.setName(newName);    // Change
}

undo() {
    ensemble.restore(memento);    // Restore
}
```

### Benefits

- Preserve encapsulation
- Simple undo implementation
- Type safe
- Easy to extend (can save more fields later)

---

## 3.4 How Patterns Work Together

These three patterns work together:

1. **Factory** creates Command objects
2. **Command** executes operations and supports undo
3. **Memento** helps Command save states

Example:
```
User input → Factory creates ModifyInstrumentCommand
          → Command saves Memento
          → Command executes
          → Manager adds to history
          
User undo → Manager gets Command
         → Command restores from Memento
```

---

## 3.5 Summary

These patterns make the system:
- More maintainable
- More flexible
- Easier to extend
- Better organized
