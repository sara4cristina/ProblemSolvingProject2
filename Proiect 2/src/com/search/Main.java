package com.search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Graph graph = new Graph();
        //create the Graph from all the distances
        try {
            FileReader fileReader = new FileReader("D:\\facultate\\an 2 semestrul 2\\problem solving\\ProblemSolvingProject2\\Proiect 2\\resources\\distante.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            String src, desc;
            int weight;

            while (line != null) {
                String[] words = line.split(" ");
                src = words[0];
                desc = words[1];
                weight = Integer.valueOf(words[2]);
                graph.addEdge(src, desc, weight);

                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        graph.printGraph();

        TownDistanceMap townDistanceMap = new TownDistanceMap();

        try {
            FileReader fileReader = new FileReader("D:\\facultate\\an 2 semestrul 2\\problem solving\\ProblemSolvingProject2\\Proiect 2\\resources\\distanteFizice.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            String src, desc;
            int weight;

            while (line != null) {
                String[] words = line.split(" ");
                src = words[0];
                desc = words[1];
                weight = Integer.valueOf(words[2]);
                townDistanceMap.addDistance(src, desc, weight);

                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }



        String startLabel = "bucuresti";
        String endLabel = "botosani";

        System.out.println("\nA*Search Algorithm " );
        long startTime = System.nanoTime();
        List<String> path = graph.aSearch(startLabel, endLabel, townDistanceMap);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        if (path == null) {
            System.out.println("There is no path from " + startLabel + " to " + endLabel);
        } else {
            System.out.println("The path from " + startLabel + " to " + endLabel + " is: " + path);
            int cost = 0;
            for (int i = 0; i < path.size() - 1; i++) {
                int edgeWeight = graph.getEdgeWeight(path.get(i), path.get(i + 1));
                cost += edgeWeight;
            }
            System.out.println("The cost of the path is: " + cost);
            System.out.println("The algorithm took " + duration + " nanoseconds to find the path.");
        }
    }
}
