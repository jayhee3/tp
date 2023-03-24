package seedu.address.model.module;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * Represents a Module's timeSlot in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimeSlot(String)}
 */

//Deadline is represented as a single LocalDateTime (eg. 070623 18:00). TimeSlot is represented as three parts,
//a DayOfWeek, a LocalTime for start time, and another LocalTime for end time.

public class TimeSlot {

    public static final String MESSAGE_CONSTRAINTS =
            "Timeslot should be of format 'Day StartTime EndTime' (Example: Tuesday 12:00 14:00)";
    public static final String VALIDATION_REGEX = "^(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)"
            + "\\s(([01]?[0-9]|2[0-3]):[0-5][0-9])\\s(([01]?[0-9]|2[0-3]):[0-5][0-9])$";
    public final DayOfWeek day;
    public final LocalTime startTime;
    public final LocalTime endTime;

    /**
     * Constructs an {@code TimeSlot}.
     *
     * @param timeSlot A valid timeSlot address.
     */
    public TimeSlot(String timeSlot) {
        requireNonNull(timeSlot);
        checkArgument(isValidTimeSlot(timeSlot), MESSAGE_CONSTRAINTS);
        Object[] TimeSlotValues = constructTimeSlotValues(timeSlot);
        day = (DayOfWeek) TimeSlotValues[0];
        startTime = (LocalTime) TimeSlotValues[1];
        endTime = (LocalTime) TimeSlotValues[2];
    }

    /**
     * Returns if a given string is a valid timeSlot.
     */
    public static boolean isValidTimeSlot(String timeSlot) {
        if (timeSlot.equals("None.")) {
            return true;
        }
        return timeSlot.matches(VALIDATION_REGEX);
    }

    /**
     * Construsts the values of day, startTime, and endTime for the TimeSlot.
     * @param timeSlot User string input.
     * @return An array of values
     */
    private Object[] constructTimeSlotValues(String timeSlot) {
        if (timeSlot.equals("None.")) {
            return new Object[]{null, null, null};
        }
        String[] timeSlotSplit = timeSlot.split(" ");
        DayOfWeek day = DayOfWeek.valueOf(timeSlotSplit[0].toUpperCase());
        LocalTime startTime = LocalTime.parse(timeSlotSplit[1]);
        LocalTime endTime = LocalTime.parse(timeSlotSplit[2]);
        return new Object[]{day, startTime, endTime};
    }

    /**
     * Returns String of desired display format
     * @return Display format String
     */
    @Override
    public String toString() {
        if (day == null) {
            return "None.";
        }
        //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyy HH:mm");
        return day.name().toLowerCase().substring(0, 1).toUpperCase() + day.name().toLowerCase().substring(1)
                + " " + startTime + " " + endTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeSlot // instanceof handles nulls
                && day.equals(((TimeSlot) other).day) && startTime.equals(((TimeSlot) other).startTime)
                && endTime.equals(((TimeSlot) other).endTime)); // state check
    }

    @Override
    public int hashCode() {
        return day.hashCode();
    }

    //The timeslot gives us the day and the starting time. We first need to get the time from the local machine.
    //then find out the date the day next happens. Then combine it with the starting time to get the localdatetime.
    public LocalDateTime getValue() {

        LocalDateTime now = LocalDateTime.now();

        // Get the day and starting time of the task
        DayOfWeek taskDay = day;
        LocalTime taskStartTime = startTime;

        // Get the next occurrence of the task day
        LocalDateTime nextTaskDay = now.with(TemporalAdjusters.next(taskDay));

        // Combine the task day and starting time to get the task date and time
        LocalDateTime taskDateTime = nextTaskDay.with(taskStartTime);

        // Return this LocalDateTime
        return taskDateTime;
    }
}
