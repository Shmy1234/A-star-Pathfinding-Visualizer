# Pathfinding Visualizer

## Running
From the project root (the folder that contains the `pathfinder/` directory):

```sh
javac $(find pathfinder -name "*.java")
java pathfinder.Game
```

Do not `cd` into `pathfinder/` before compiling; the Java packages assume the project root as the classpath base.

## Web (React/TypeScript)

The `web/` folder now contains a React + TypeScript build of the visualizer. From within `web/`:

```sh
npm install
npm run dev    # start the Vite dev server
npm run build  # produce a production build
```

Open the app in your browser at the dev server URL (defaults to http://localhost:5173).

### Vercel deployment

A Vercel config (`vercel.json`) is included. It builds the `web/` app with the static-build adapter and outputs `dist/`, with a route fallback to `index.html` for SPA behavior.

To deploy (using the Vercel CLI):

```sh
npm install -g vercel          # once, if you don't have it
vercel                         # select your scope; it will detect vercel.json
vercel --prod                  # for production
```

If prompted, set the project root to the repo root (the config points at `web/package.json`). The build runs `npm install` and `npm run build` inside `web/`.

## Migration notes: Swing to React/TypeScript

- **Why move**: The original UI was Java Swing (launched via `run.sh`). To make the visualizer browser-friendly, it was rewritten as a single-page app with React + TypeScript and Vite in `web/`.
- **What stayed**: Core pathfinding rules (BFS, DFS, Dijkstra, A*) and the waypoint interaction model (start + ordered waypoints, run segment by segment) were preserved. Algorithm descriptions and SVG diagrams were carried over for parity.
- **What changed**: UI rendering now lives in React components (`web/src/App.tsx` and `web/src/components/*`) with typed grid/algorithm logic in `web/src/logic/pathfinding.ts`. Styling moved to `web/src/styles.css`.
- **Build/run**: The desktop app still compiles via `javac` + `java pathfinder.Game`. The web app uses `npm install` + `npm run dev/build` inside `web/`, with Vercel configured for static deployment.

## Algorithms implemented

The app includes four pathfinding algorithms, each with its own panel under `pathfinder/`. All panels draw the chosen path by walking the stored parents from the goal back to the start once a solution is known.

- BFS (`pathfinder/bfs/BfsPanel.java`): Classic breadth-first search over an unweighted grid. A FIFO queue expands the frontier one “ring” at a time; the first time a cell is enqueued its parent is frozen, guaranteeing the path that reaches it is the shortest in edge count. The search stops as soon as the goal is dequeued, because no later ring can be shorter. Time: O(V + E) in grid size; Memory: O(V) for the queue/visited set.
- DFS (`pathfinder/dfs/DfsPanel.java`): Depth-first search using an explicit stack to avoid recursion limits. It dives along a single branch until it hits a dead end, then backtracks to the most recent fork. DFS does not promise the shortest path on unweighted graphs and can spend time in long cul-de-sacs, but it quickly finds *a* path if one exists and is memory-light (stack depth proportional to path length). Time: O(V + E); Memory: O(V) in the worst case but often closer to path depth.
- Dijkstra (`pathfinder/dijkstra/DijkstraPanel.java`): Uniform-cost search for non-negative edge weights (here every move costs 1). A min-priority queue orders nodes by their current best-known distance `gCost` from the start. When a node is popped from the queue its distance is finalized; relaxing neighbors may lower their distances and update parents, causing a decrease-key effect by re-adding them with the better score. Time: O((V + E) log V) with a binary heap; Memory: O(V) for distances, parents, and the open set.
- A* (`pathfinder/astar/AStarPanel.java`): Goal-directed variant of Dijkstra that adds a heuristic `hCost` (Manhattan distance on the grid) to form `fCost = gCost + hCost`. Nodes are expanded in order of `fCost`, biasing the search toward the goal while preserving optimality because the heuristic is admissible and consistent. If `hCost` were zero, A* would degenerate to Dijkstra; with a well-chosen heuristic it dramatically shrinks the explored area. Time matches Dijkstra in the worst case but is typically much lower; Memory: O(V) for open/closed sets and scores.

## Interaction model

- Select waypoints: first click sets the start, subsequent clicks add ordered waypoints.
- Press **Run** to compute paths segment by segment through the waypoints (paths between each pair are drawn in sequence).
- **Undo Last** removes the most recent waypoint; **Restart** clears the grid; **Toggle Coords** shows/hides coordinates.
- A **Home** button returns to the algorithm chooser. Buttons include hover/click feedback.
