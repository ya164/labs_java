package journal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.regex.Pattern;

public final class Validators {
    private Validators() {}

    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яІіЇїЄєҐґ'\\-]{2,40}$";

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final Set<String> UA_MOBILE_CODES = Set.of(
            "50","63","66","67","68","73","91","92","93","94","95","96","97","98","99"
    );

    private static final Pattern NON_DIGITS_EXCEPT_PLUS = Pattern.compile("[^0-9+]");

    /** Повертає незмінюваний набір дозволених кодів (для підказок користувачу). */
    public static Set<String> allowedUaMobileCodes() {
        return UA_MOBILE_CODES;
    }

    public static boolean isValidName(String s) {
        return s != null && s.matches(NAME_REGEX);
    }

    public static boolean isValidPhone(String s) {
        return normalizeUaPhoneE164(s) != null;
    }

    public static String normalizeUaPhoneE164(String raw) {
        if (raw == null) return null;

        String cleaned = NON_DIGITS_EXCEPT_PLUS.matcher(raw).replaceAll("");

        if (cleaned.indexOf('+') > 0) return null;

        String digits;
        if (cleaned.startsWith("+380")) {
            digits = cleaned.substring(4); // 9 цифр
        } else if (cleaned.startsWith("380")) {
            digits = cleaned.substring(3);
            cleaned = "+380" + digits;
        } else if (cleaned.startsWith("0")) {
            digits = cleaned.substring(1); // 9 цифр
            cleaned = "+380" + digits;
        } else {
            return null;
        }

        if (!digits.matches("\\d{9}")) return null;

        String op2 = digits.substring(0, 2);
        if (!UA_MOBILE_CODES.contains(op2)) return null;

        return cleaned;
    }

    public static LocalDate parseBirthDate(String s) {
        if (s == null) return null;
        try {
            LocalDate d = LocalDate.parse(s.trim(), DATE_FMT);
            LocalDate min = LocalDate.of(1900, 1, 1);
            LocalDate max = LocalDate.now().minusYears(10);
            if (d.isBefore(min) || d.isAfter(max)) return null;
            return d;
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean isNonEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isValidStreet(String s) {
        return s != null && s.trim().matches("[A-Za-zА-Яа-яІіЇїЄєҐґ0-9 .'\\-]{3,80}");
    }

    public static boolean isValidHouse(String s) {
        return s != null && s.trim().matches("\\d+[A-Za-zА-Яа-яІіЇїЄєҐґ]?(?:/\\d+)?(?:-[A-Za-zА-Яа-яІіЇїЄєҐґ])?");
    }

    public static Integer parseApartment(String s) {
        if (s == null) return null;
        String t = s.trim();
        if (t.isEmpty()) return null;
        try {
            int n = Integer.parseInt(t);
            return (n > 0 && n <= 10000) ? n : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
