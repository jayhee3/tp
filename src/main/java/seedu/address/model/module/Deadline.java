package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Module's deadline.
 */
public class Deadline {
    public static final String MESSAGE_CONSTRAINTS =
            "Deadlines should be of the form 'DDMMYY HH:MM' (Example: 250623 18:00)";
    public static final String VALIDATION_REGEX = "^[0-9]{6}\\s[0-9]{2}[:][0-9]{2}$";

    public final LocalDateTime value;

    /**
     * Constructs an {@code Deadline}.
     *
     * @param deadline A valid deadline.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValidDeadline(deadline), MESSAGE_CONSTRAINTS);
        value = convertStringToDate(deadline);
    }

    /**
     * Returns if a given string is a valid timeSlot.
     */
    public static boolean isValidDeadline(String test) {
        if (test.equals("None.")) {
            return true;
        }
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the conversion of String to a LocalDateTime
     * @return LocalDateTime instance
     */
    private LocalDateTime convertStringToDate(String deadline) {
        if (deadline.equals("None.")) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyy HH:mm");
        return LocalDateTime.parse(deadline, dateTimeFormatter);
    }

    /**
     * Returns String of desired display format
     * @return Display format String
     */
    @Override
    public String toString() {
        if (value == null) {
            return "None.";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd LLLL yyyy HH:mm");
        return dateTimeFormatter.format(value);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.module.Deadline // instanceof handles nulls
                && value.equals(((seedu.address.model.module.Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public LocalDateTime getValue() {
        return value;
    }
}
