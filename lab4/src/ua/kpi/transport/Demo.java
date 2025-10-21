package ua.kpi.transport;

public class Demo {
    public static void main(String[] args) {
        Bus bus = new Bus(3);
        Taxi taxi = new Taxi(2);
        FireTruck ft = new FireTruck(2);
        PoliceCar pc = new PoliceCar(2);

        bus.board(new Person("Alice"));
        bus.board(new Firefighter("Bob"));
        taxi.board(new PoliceOfficer("Carl"));

        ft.board(new Firefighter("Dima"));
        pc.board(new PoliceOfficer("Eva"));

        Road r = new Road();
        r.addCarToRoad(bus);
        r.addCarToRoad(taxi);
        r.addCarToRoad(ft);
        r.addCarToRoad(pc);

        System.out.println("Total humans on this road: " + r.getCountOfHumans());
    }
}
