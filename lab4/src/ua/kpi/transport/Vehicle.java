package ua.kpi.transport;

import ua.kpi.transport.ex.NoSeatAvailableException;
import ua.kpi.transport.ex.PassengerNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Vehicle<T extends Person> {
    private final int capacity;
    private final List<T> passengers = new ArrayList<>();

    protected Vehicle(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
    }

    public int getCapacity() { return capacity; }
    public int getOccupied() { return passengers.size(); }
    public List<T> getPassengers() { return Collections.unmodifiableList(passengers); }

    public void board(T p) {
        if (getOccupied() >= capacity)
            throw new NoSeatAvailableException(getClass().getSimpleName() + ": no free seats");
        if (passengers.contains(p)) return; // ідемпотентність
        passengers.add(p);
    }

    public void alight(T p) {
        if (!passengers.remove(p))
            throw new PassengerNotFoundException(getClass().getSimpleName() + ": passenger not found");
    }
}
