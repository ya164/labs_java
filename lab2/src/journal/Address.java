package journal;

import java.util.Objects;

public class Address {
    private final String street;
    private final String house;
    private final Integer apartment;

    public Address(String street, String house, Integer apartment) {
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    public String getStreet()     { return street; }
    public String getHouse()      { return house; }
    public Integer getApartment() { return apartment; }
    public boolean isApartment()  { return apartment != null; }

    @Override
    public String toString() {
        return isApartment()
                ? String.format("Квартира: %s, буд. %s, кв. %d", street, house, apartment)
                : String.format("Будинок: %s, буд. %s", street, house);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address a = (Address) o;
        return Objects.equals(street, a.street) &&
                Objects.equals(house, a.house) &&
                Objects.equals(apartment, a.apartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, house, apartment);
    }
}
