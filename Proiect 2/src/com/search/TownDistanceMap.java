package com.search;

import java.util.HashMap;
import java.util.Map;

public class TownDistanceMap {
    private Map<String, Map<String, Double>> distanceMap;

    public TownDistanceMap() {
        this.distanceMap = new HashMap<>();
    }

    public void addDistance(String town1, String town2, double distance) {
        // Create or update the distance between town1 and town2
        distanceMap.computeIfAbsent(town1, k -> new HashMap<>()).put(town2, distance);
        distanceMap.computeIfAbsent(town2, k -> new HashMap<>()).put(town1, distance);
    }

    public double getDistance(String town1, String town2) {
        // Return the distance between town1 and town2, or -1 if not found
        return distanceMap.getOrDefault(town1, new HashMap<>()).getOrDefault(town2, -1.0);
    }

}