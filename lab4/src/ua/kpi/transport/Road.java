package ua.kpi.transport;

import java.util.ArrayList;
import java.util.List;

public class Road {
    private final List<Vehicle<? extends Person>> carsInRoad = new ArrayList<>();

    public void addCarToRoad(Vehicle<? extends Person> vehicle) {
        carsInRoad.add(vehicle);
    }

    public int getCountOfHumans() {
        int total = 0;
        for (Vehicle<? extends Person> v : carsInRoad) {
            total += v.getOccupied();
        }
        return total;
    }

    public List<Vehicle<? extends Person>> getCarsInRoad() {
        return List.copyOf(carsInRoad);
    }
}
