# 🌌 A* Pathfinding Visualizer (Java/Swing)

**A\*** (A-star) Visualizer is a Java-based interactive application that demonstrates how heuristic search finds an optimal path on a grid. You can place obstacles, set start and goal nodes, and watch A* explore the grid in real time.

> 🧭 Great for learning pathfinding, heuristics, and GUI programming with Swing/AWT.

---

## ✨ Features

- 🎯 **Interactive grid** — click to place **start**, **goal**, and **walls**
- ⚡ **A\*** algorithm visualized step‑by‑step (open/closed sets, final path)
- 🧠 **Manhattan heuristic** by default (easy to swap out)
- 🎨 Clear color scheme (start/goal/visited/open/wall/path)
- 🧩 Simple code structure (ideal for class demos and portfolios)

---

## 📦 Project Layout

```
src/
├─ Main.java      # Entry point – creates JFrame, mounts panel
├─ Panel.java     # Drawing, mouse/keyboard input, A* animation
├─ Game.java      # Simple loop/controller (timing, state)
└─ Node.java      # Grid cell model: costs, parent, flags (wall, open, closed)
(out)/            # Compiled .class files (if present)
README.md
```

> You may also see `Panel.class`, `Game.class`, `Node.class`, `Main.class` if you’ve compiled already.

---

## 🛠 Requirements

- **JDK 11+** (Java Development Kit)
- Any OS with Java (Windows/macOS/Linux)
- An IDE (IntelliJ / VS Code / Eclipse) *or* the command line

---

## ▶️ Build & Run

### Option A — Command line
```bash
# From the folder containing the .java files
javac *.java
java Main
```

### Option B — IntelliJ IDEA / VS Code
1. Create a new Java project and add `Main.java`, `Panel.java`, `Game.java`, `Node.java` to `src/`.
2. Mark `src/` as **Sources Root** (IntelliJ) if needed.
3. Run the `Main` class.

> If you keep class files in a separate `out/` folder, ensure your IDE/module uses that as the build output.

---

## 🕹 Controls

| Action | Description |
|---|---|
| **Left‑Click** | Place **Start**, **Goal**, or draw **Walls** (order depends on implementation) |
| **Right‑Click** | Erase a cell |
| **Enter** | Start the A* visualization |
| **R** | Reset/Clear the grid |
| **Esc** | Quit |

> You can tweak key bindings and mouse modes in `Panel.java`.

---

## 🎨 Color Legend

| Color | Meaning |
|---|---|
| 🟩 **Green** | Start node |
| 🟥 **Red** | Goal node |
| 🟦 **Blue** | Closed (visited) nodes |
| 🟨 **Yellow** | Open set (frontier) nodes |
| ⚫ **Black** | Wall / obstacle |
| 🟢 **Bright Green** | Final shortest path |

---

## 🧠 How A* Works

A* prioritizes nodes using:
```
f(n) = g(n) + h(n)
```
- `g(n)`: exact cost from **start** to `n`  
- `h(n)`: heuristic estimate from `n` to **goal** (default: **Manhattan distance**)  
- The node with the **lowest f(n)** is expanded first.

### Default heuristic (Manhattan)
```java
int heuristic(Node a, Node b) {
    return Math.abs(a.col - b.col) + Math.abs(a.row - b.row);
}
```

> Replace with diagonal/Euclidean if you allow diagonal movement.

---

## 🧩 Key Classes (at a glance)

### `Node`
- Coordinates: `row`, `col`
- Costs: `gCost`, `hCost`, `fCost = g + h`
- Links: `parent` (for path reconstruction)
- Flags: `isWall`, `inOpenSet`, `inClosedSet`, `isStart`, `isGoal`

### `Panel`
- Renders the grid and nodes
- Handles mouse/keyboard input
- Runs the A* step loop with a small delay for animation

### `Game`
- (If used) Manages timing/state and ties the UI to algorithm ticks

### `Main`
- Creates the `JFrame` and attaches the `Panel`
