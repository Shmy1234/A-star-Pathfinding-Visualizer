# Pathfinding Visualizer

## Running
From the project root (the folder that contains the `pathfinder/` directory):

```sh
javac $(find pathfinder -name "*.java")
java pathfinder.Game
```

Do not `cd` into `pathfinder/` before compiling; the Java packages assume the project root as the classpath base.

## Algorithms implemented

The app includes four pathfinding algorithms, each with its own panel under `pathfinder/`:

- BFS (`pathfinder/bfs/BfsPanel.java`): Uses a queue (FIFO) to explore neighbors in widening layers. It marks visited nodes and stores each node’s parent; when the goal is found, the path is reconstructed by following parents backward.
- DFS (`pathfinder/dfs/DfsPanel.java`): Uses a stack (LIFO) to dive deep along one path before backtracking. Parents are tracked similarly to recover the found path.
- Dijkstra (`pathfinder/dijkstra/DijkstraPanel.java`): Uses a min-priority queue ordered by the node’s current `distance` from the start (edge cost = 1). When a shorter route to a neighbor is found, the neighbor’s distance and parent are updated. The path is reconstructed once the goal is dequeued.
- A* (`pathfinder/astar/AStarPanel.java`): Uses a min-priority queue ordered by `fCost = gCost + hCost`, where `gCost` is path cost from the start and `hCost` is the Manhattan heuristic to the goal. Parents track the best known route; when the goal is reached, the path is rebuilt via parents.

## Interaction model

- Select waypoints: first click sets the start, subsequent clicks add ordered waypoints.
- Press **Run** to compute paths segment by segment through the waypoints (paths between each pair are drawn in sequence).
- **Undo Last** removes the most recent waypoint; **Restart** clears the grid; **Toggle Coords** shows/hides coordinates.
- A **Home** button returns to the algorithm chooser. Buttons include hover/click feedback.
