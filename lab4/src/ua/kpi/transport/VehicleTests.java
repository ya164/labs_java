package ua.kpi.transport;

import org.junit.jupiter.api.Test;
import ua.kpi.transport.ex.NoSeatAvailableException;
import ua.kpi.transport.ex.PassengerNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTests {

    @Test
    void bus_can_take_any_passengers() {
        Bus bus = new Bus(3);
        bus.board(new Person("A"));
        bus.board(new Firefighter("B"));
        bus.board(new PoliceOfficer("C"));
        assertEquals(3, bus.getOccupied());
    }

    @Test
    void firetruck_only_firefighters_compile_time() {
        FireTruck ft = new FireTruck(2);
        ft.board(new Firefighter("F1"));
        ft.board(new Firefighter("F2"));
        assertEquals(2, ft.getOccupied());
    }

    @Test
    void policecar_only_police_compile_time() {
        PoliceCar pc = new PoliceCar(1);
        pc.board(new PoliceOfficer("P1"));
        assertEquals(1, pc.getOccupied());
    }

    @Test
    void capacity_and_exceptions() {
        Taxi taxi = new Taxi(1);
        taxi.board(new Person("A"));
        assertThrows(NoSeatAvailableException.class, () -> taxi.board(new Person("B")));
    }

    @Test
    void alight_throws_if_not_inside() {
        Bus bus = new Bus(2);
        Person p = new Person("A");
        bus.board(p);
        assertThrows(PassengerNotFoundException.class, () -> bus.alight(new Person("A")));
    }

    @Test
    void road_counts_all_humans() {
        Bus bus = new Bus(3);
        bus.board(new Person("A"));
        bus.board(new Firefighter("B"));

        FireTruck ft = new FireTruck(2);
        ft.board(new Firefighter("C"));

        Taxi taxi = new Taxi(1);
        taxi.board(new PoliceOfficer("D"));

        Road road = new Road();
        road.addCarToRoad(bus);
        road.addCarToRoad(ft);
        road.addCarToRoad(taxi);

        assertEquals(4, road.getCountOfHumans());
    }
}
