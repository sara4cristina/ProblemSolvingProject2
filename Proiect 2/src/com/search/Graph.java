package com.search;

import java.util.*;

public class Graph {
    private Map<String, Map<String, Integer>> graph;

    public Graph() {
        graph = new HashMap<>();
    }

    public void addVertex(String label) {
        if (!graph.containsKey(label)) {
            graph.put(label, new HashMap<>());
        }
    }

    public void addEdge(String sourceLabel, String destLabel, int weight) {
        if (!graph.containsKey(sourceLabel)) {
            addVertex(sourceLabel);
        }
        if (!graph.containsKey(destLabel)) {
            addVertex(destLabel);
        }
        graph.get(sourceLabel).put(destLabel, weight);
        graph.get(destLabel).put(sourceLabel, weight);
    }

    public int getEdgeWeight(String sourceLabel, String destLabel) {
        if (graph.containsKey(sourceLabel) && graph.get(sourceLabel).containsKey(destLabel)) {
            return graph.get(sourceLabel).get(destLabel);
        }
        return -1; // indicates that the edge doesn't exist
    }

    public Set<String> getVertices() {
        return graph.keySet();
    }

    public Set<String> getNeighbors(String label) {
        if (graph.containsKey(label)) {
            return graph.get(label).keySet();
        }
        return new HashSet<>(); // empty set if label is not in graph
    }

    public void printGraph() {
        for (String vertex : graph.keySet()) {
            System.out.print(vertex + " -> ");
            for (String neighbor : graph.get(vertex).keySet()) {
                int weight = graph.get(vertex).get(neighbor);
                System.out.print("(" + neighbor + ", " + weight + ") ");
            }
            System.out.println();
        }
    }

    public List<String> aSearch(String startLabel, String endLabel, TownDistanceMap distanceMap) {
        // Initialize a priority queue for the frontier, with the start vertex as the initial node
        PriorityQueue<Node> frontier = new PriorityQueue<>();
        frontier.add(new Node(startLabel, 0, 0));

        // Initialize a map to keep track of the cost from start to each vertex
        Map<String, Integer> costs = new HashMap<>();
        costs.put(startLabel, 0);

        // Initialize a map to keep track of the parent of each vertex in the path
        Map<String, String> parents = new HashMap<>();
        parents.put(startLabel, null);

        // Start the search loop
        while (!frontier.isEmpty()) {
            // Get the node with the lowest f-score from the frontier
            Node current = frontier.poll();

            // Check if we have reached the goal vertex
            if (current.label.equals(endLabel)) {
                // Reconstruct the path from end to start using the parent map
                List<String> path = new ArrayList<>();
                String vertex = endLabel;
                while (vertex != null) {
                    path.add(vertex);
                    vertex = parents.get(vertex);
                }
                Collections.reverse(path);
                return path;
            }

            // Expand the current node by iterating through its neighbors
            for (String neighbor : getNeighbors(current.label)) {
                // Calculate the cost from start to the neighbor via the current node
                int cost = costs.get(current.label) + getEdgeWeight(current.label, neighbor);

                // Check if we have already visited this neighbor or if the new cost is lower
                if (!costs.containsKey(neighbor) || cost < costs.get(neighbor)) {
                    // Update the cost and parent maps for this neighbor
                    costs.put(neighbor, cost);
                    parents.put(neighbor, current.label);

                    // Calculate the heuristic score for the neighbor (in this case, just use 0)
                    double hScore = distanceMap.getDistance(current.getLabel(),neighbor);

                    // Add the neighbor to the frontier with its f-score (cost + heuristic)
                    frontier.add(new Node(neighbor, cost, hScore));
                }
            }
        }

        // If we reach this point, there is no path from start to end
        return null;
    }

    // Helper class for nodes in the A* search algorithm
    private class Node implements Comparable<Node> {
        public String label;
        public double cost;
        public double hScore;

        public Node(String label, double cost, double hScore) {
            this.label = label;
            this.cost = cost;
            this.hScore = hScore;
        }

        public double getFScore() {
            return cost + hScore;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(getFScore(), other.getFScore());
        }

        public String getLabel() {
            return label;
        }
    }

}
