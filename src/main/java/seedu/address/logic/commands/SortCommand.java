package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.module.NameContainsKeywordsPredicate;

/**
 * Finds and lists all modules in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the modules based on their timeslot or "
            + "deadline. An optional keyword can be added to limit the displayed modules to be of a certain type only."
            + "Parameters: sort [Lecture/Deadline/Tutorial] \n"
            + "Example: " + COMMAND_WORD
            + "Example: " + COMMAND_WORD + " tutorial"
            + "Example: " + COMMAND_WORD + " exam";

    private final NameContainsKeywordsPredicate predicate;

    public SortCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.showSortedList();
        //model.updateFilteredModuleList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MODULES_LISTED_OVERVIEW, model.getDisplayedModules().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && predicate.equals(((SortCommand) other).predicate)); // state check
    }
}
