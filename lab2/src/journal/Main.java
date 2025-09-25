package journal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final List<CuratorRecord> journal = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== Журнал куратора ===");
        while (true) {
            System.out.println();
            System.out.println("Меню:");
            System.out.println("1 - Додати запис");
            System.out.println("2 - Показати всі записи");
            System.out.println("0 - Вихід");
            System.out.print("Ваш вибір: ");

            String choice = in.nextLine().trim();
            switch (choice) {
                case "1":
                    addRecordFlow();
                    break;
                case "2":
                    showAll();
                    break;
                case "0":
                    System.out.println("До нових зустрічей!");
                    return;
                default:
                    System.out.println("Невідома команда. Спробуйте ще раз.");
            }
        }
    }

    private static void addRecordFlow() {
        System.out.println("\n— Додавання нового запису —");

        String lastName  = promptName("Введіть прізвище студента: ");
        String firstName = promptName("Введіть ім'я студента: ");
        LocalDate birthDate = promptBirthDate("Введіть дату народження (yyyy-MM-dd): ");
        String phone = promptPhone("Введіть телефон (+380XXXXXXXXX або 0XXXXXXXXX): ");

        boolean isApartment = chooseHousingType();

        Address addr = promptAddressCombined(isApartment);

        CuratorRecord rec = new CuratorRecord(lastName, firstName, birthDate, phone, addr);

        journal.add(rec);
        System.out.println("Запис додано!");
    }

    private static void showAll() {
        System.out.println("\n— Всі записи журналу —");
        if (journal.isEmpty()) {
            System.out.println("(поки немає записів)");
            return;
        }
        int i = 1;
        for (CuratorRecord r : journal) {
            System.out.println(i++ + ") " + r);
        }
    }

    private static boolean chooseHousingType() {
        while (true) {
            System.out.println("Оберіть тип житла:");
            System.out.println("1 - Квартира");
            System.out.println("2 - Приватний будинок");
            System.out.print("Ваш вибір: ");
            String choice = in.nextLine().trim();
            if ("1".equals(choice)) return true;
            if ("2".equals(choice)) return false;
            System.out.println("Невірний вибір. Спробуйте ще раз.");
        }
    }

    private static Address promptAddressCombined(boolean isApartment) {
        while (true) {
            if (isApartment) {
                System.out.println("Введіть адресу у форматі: вулиця, номер будинку, номер квартири");
            } else {
                System.out.println("Введіть адресу у форматі: вулиця, номер будинку");
            }
            System.out.print("Адреса: ");
            String line = in.nextLine();

            if (!Validators.isNonEmpty(line)) {
                System.out.println("Поле не може бути порожнім.");
                continue;
            }

            String[] parts = line.split(",");
            for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();

            if (isApartment) {
                if (parts.length != 3) {
                    System.out.println("Некоректний формат. Приклад: \"вул. Шевченка, 12А, 45\"");
                    continue;
                }
                String street = parts[0];
                String house = parts[1];
                String aptStr = parts[2];

                if (!Validators.isValidStreet(street)) {
                    System.out.println("Некоректна назва вулиці (3–80 символів, літери/цифри/пробіли/.-').");
                    continue;
                }
                if (!Validators.isValidHouse(house)) {
                    System.out.println("Некоректний номер будинку. Приклади: 12, 12А, 7/3, 10-Б.");
                    continue;
                }
                Integer apt = Validators.parseApartment(aptStr);
                if (apt == null) {
                    System.out.println("Некоректний номер квартири. Введіть додатне число.");
                    continue;
                }
                return new Address(street, house, apt);
            } else {
                if (parts.length != 2) {
                    System.out.println("Некоректний формат. Приклад: \"вул. Шевченка, 12А\"");
                    continue;
                }
                String street = parts[0];
                String house = parts[1];

                if (!Validators.isValidStreet(street)) {
                    System.out.println("Некоректна назва вулиці (3–80 символів, літери/цифри/пробіли/.-').");
                    continue;
                }
                if (!Validators.isValidHouse(house)) {
                    System.out.println("Некоректний номер будинку. Приклади: 12, 12А, 7/3, 10-Б.");
                    continue;
                }
                return new Address(street, house, null);
            }
        }
    }

    private static String promptName(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = in.nextLine().trim();
            if (Validators.isValidName(s)) return s;
            System.out.println("Некоректне ім'я/прізвище. Дозволені літери укр/лат, апостроф, дефіс, 2–40 символів.");
        }
    }

    private static LocalDate promptBirthDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = in.nextLine().trim();
            LocalDate d = Validators.parseBirthDate(s);
            if (d != null) return d;
            System.out.println("Некоректна дата. Формат yyyy-MM-dd, від 1900-01-01 до не пізніше ніж 10 років тому.");
        }
    }

    private static String promptPhone(String prompt) {
        String allowed = Validators.allowedUaMobileCodes()
                .stream().sorted()
                .collect(Collectors.joining(", "));
        while (true) {
            System.out.print(prompt);
            String s = in.nextLine().trim();
            String normalized = Validators.normalizeUaPhoneE164(s);
            if (normalized != null) return normalized;
            System.out.println("Некоректний телефон. Приклади: +380931234567, 0931234567, (093) 123-45-67.");
            System.out.println("Дозволені мобільні коди: " + allowed + ". Зверніть увагу: MNP можливе.");
        }
    }
}