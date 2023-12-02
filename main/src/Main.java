import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static final String WRONG_INPUT_FILE_MESSAGE = "Darn those elves - they gave us the wrong input file!";

    public static void main(String[] args) throws IOException {
        int result = extractInputLines().stream()
                .map(Main::getDigitsFromLine)
                .mapToInt(Main::getNumberFromFirstAndLastDigits)
                .sum();

        System.out.println("Behold! The answer is: " + result);
    }

    /**
     * Gets a number made up of the first and last digits of a list of digits. Example: 1 and 2 will make it return 12.
     *
     * @param digits A list of digits.
     * @return First and last number combined into an integer.
     */
    public static int getNumberFromFirstAndLastDigits(final List<Integer> digits) {
        final int firstNumber = digits.get(0);
        final int lastNumber = digits.get(digits.size() - 1);

        return Integer.parseInt("" + firstNumber + lastNumber);
    }

    /**
     * Receives a quizzical line given by the elves and tries to obtain the many possible numbers it holds!
     * @param line the line given by the elves.
     * @return A magical number very important to unravel the mysteries unknown to the elves.
     */
    private static List<Integer> getDigitsFromLine(final String line) {
        final List<String> foundDigits = new ArrayList<>();

        final StringBuilder regexPatternBuilder = new StringBuilder("\\d");
        for (Digit digit : Digit.values()) {
            regexPatternBuilder.append("|(")
                    .append(digit.name().toLowerCase())
                    .append(")");
        }
        final String regexPattern = regexPatternBuilder.toString();
        final Pattern pattern = Pattern.compile(regexPattern);
        final Matcher matcher = pattern.matcher(line);

        if(matcher.find()) {
            do {
                foundDigits.add(matcher.group());
            } while(matcher.find(matcher.start()+1));
        }

        return foundDigits.stream()
                .map(Main::translateDigitToInteger)
                .toList();
    }

    private static Integer translateDigitToInteger(final String digit) {
        try {
            return Integer.parseInt(digit);
        } catch (final NumberFormatException exception) {
            return Digit.valueOf(digit.toUpperCase()).getIntegerRepresentation();
        }
    }

    /**
     * Reads the magical input and returns these so far intelligible... things.
     *
     * @return a list of mystical lines. Who knows what values they hold?
     */
    private static List<String> extractInputLines() throws IOException {
        try (InputStream resource = Main.class.getResourceAsStream("input")) {
            if (resource == null) {
                throw new RuntimeException(WRONG_INPUT_FILE_MESSAGE);
            }

            return new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8))
                    .lines()
                    .toList();
        }
    }

    private enum Digit {
        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9);

        private int integerRepresentation;

        Digit(final int integerRepresentation) {
            this.integerRepresentation = integerRepresentation;
        }

        public int getIntegerRepresentation() {
            return integerRepresentation;
        }
    }
}