package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.Category.PARENT;
import static seedu.address.model.person.Category.STUDENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;

/**
 * Contains integration tests and unit tests for GetParentCommand.
 */
public class GetParentCommandTest {
    private Model model;
    private Parent parent;
    private Student studentWithParent;
    private Student studentWithoutParent;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        parent = new Parent(PARENT,
                new Name("Reyna Bong"),
                new Phone("98765432"),
                new Email("reyna@example.com"),
                new Address("123 Street"),
                new HashSet<>());

        studentWithParent = new Student(STUDENT,
                new Name("John Doe"),
                new Phone("87654321"),
                new Email("john@example.com"),
                new Address("456 Street"),
                new HashSet<>());
        studentWithParent.setParent(parent);

        studentWithoutParent = new Student(STUDENT,
                new Name("Mary Jane"),
                new Phone("87654321"),
                new Email("mary@example.com"),
                new Address("789 Street"),
                new HashSet<>());

        model.addPerson(parent);
        model.addPerson(studentWithParent);
        model.addPerson(studentWithoutParent);
    }

    @Test
    public void execute_studentWithParent_success() throws CommandException {
        GetParentCommand command = new GetParentCommand(new Name("John Doe"));

        String expectedMessage = String.format(GetParentCommand.MESSAGE_SUCCESS,
                studentWithParent.getName(), parent.getName());

        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        assertEquals(1, model.getFilteredPersonList().size());
        assertEquals(parent, model.getFilteredPersonList().get(0));
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        GetParentCommand command = new GetParentCommand(new Name("Random Name"));

        CommandException exception = assertThrows(CommandException.class, () ->
                command.execute(model));

        assertEquals(String.format(GetParentCommand.MESSAGE_STUDENT_NOT_FOUND,
                new Name("Random Name")),
                exception.getMessage());
    }

    @Test
    public void execute_studentWithoutParent_throwsCommandException() {
        GetParentCommand command = new GetParentCommand(new Name("Mary Jane"));

        CommandException exception = assertThrows(CommandException.class, () ->
                command.execute(model));

        assertEquals(String.format(GetParentCommand.MESSAGE_NO_PARENT_LINKED,
                        new Name("Mary Jane")), exception.getMessage());
    }

    @Test
    public void execute_filteredListBeforeCommand_stillFindsStudent() throws CommandException {
        model.updateFilteredPersonList(person -> person.getCategory().equals(PARENT));

        assertFalse(model.getFilteredPersonList().isEmpty(), "Should have at least one parent");

        GetParentCommand command = new GetParentCommand(new Name("John Doe"));
        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("Reyna Bong"));

        assertEquals(1, model.getFilteredPersonList().size());
        assertEquals("Reyna Bong", model.getFilteredPersonList().get(0).getName().fullName);
    }

    @Test
    public void execute_multiplePersonsInList_filtersToShowOnlyParent() throws CommandException {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        int initialSize = model.getFilteredPersonList().size();
        assertTrue(initialSize > 1, "Should have multiple persons in the list");

        GetParentCommand command = new GetParentCommand(new Name("John Doe"));
        command.execute(model);

        assertEquals(1, model.getFilteredPersonList().size());
        assertEquals(parent, model.getFilteredPersonList().get(0));
    }

    @Test
    public void equals() {
        GetParentCommand a = new GetParentCommand(new Name("John Doe"));
        GetParentCommand b = new GetParentCommand(new Name("Carol Lim"));

        // same values -> returns true
        assertTrue(a.equals(new GetParentCommand(new Name("John Doe"))));

        // same object -> returns true
        assertTrue(a.equals(a));

        // null -> returns false
        assertFalse(a.equals(null));

        // different types -> returns false
        assertFalse(a.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(a.equals(b));
    }
}
