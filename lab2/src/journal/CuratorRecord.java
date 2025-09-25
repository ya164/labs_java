package journal;

import java.time.LocalDate;
import java.util.Objects;

public class CuratorRecord {
    private final String lastName;
    private final String firstName;
    private final LocalDate birthDate;
    private final String phone;
    private final Address address;

    public CuratorRecord(String lastName, String firstName,
                         LocalDate birthDate, String phone, Address address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
    }

    public String getLastName()   { return lastName; }
    public String getFirstName()  { return firstName; }
    public LocalDate getBirthDate(){ return birthDate; }
    public String getPhone()      { return phone; }
    public Address getAddress()   { return address; }

    @Override
    public String toString() {
        return "Студент: " + lastName + " " + firstName +
                ", Дата народження: " + birthDate +
                ", Телефон: " + phone +
                ", Адреса: " + address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuratorRecord)) return false;
        CuratorRecord that = (CuratorRecord) o;
        return Objects.equals(lastName, that.lastName) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(birthDate, that.birthDate) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, birthDate, phone, address);
    }
}
