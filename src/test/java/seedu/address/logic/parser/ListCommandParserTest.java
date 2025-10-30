package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.person.Category;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, " c/student",
                new ListCommand(Category.STUDENT));

        assertParseSuccess(parser, " c/tutor",
                new ListCommand(Category.TUTOR));

        // leading and trailing spaces
        assertParseSuccess(parser, "    c/parent   ",
                new ListCommand(Category.PARENT));
    }

    @Test
    public void parse_missingPrefix_success() {
        assertParseSuccess(parser, "",
                new ListCommand());

        // whitespaces
        assertParseSuccess(parser, "   ",
                new ListCommand());
    }

    @Test
    public void parse_emptyCategory_failure() {
        // no category stated after c/
        String expectedMessage = ListCommand.MESSAGE_MISSING_CATEGORY;
        assertParseFailure(parser, " c/   ", expectedMessage);
    }

    @Test
    public void parse_invalidCategory_failure() {
        String invalidCategory = "studentt";
        String expectedMessage = String.format(ListCommand.MESSAGE_INVALID_CATEGORY, invalidCategory);

        assertParseFailure(parser, " c/" + invalidCategory, expectedMessage);
    }

    @Test
    public void parse_withPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " list c/tutor", expectedMessage);
    }
}
