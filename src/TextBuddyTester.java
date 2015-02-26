/*
 * TextBuddyTester [Build Version 1.0]
 * Tay Siang Meng Sean
 */

//@author A0121409R

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextBuddyTester {

    static TextBuddyHelper testInstance    = null;

    static String          ADD_COMMAND     = "add";
    static String          DISPLAY_COMMAND = "display";
    static String          DELETE_COMMAND  = "delete";
    static String          CLEAR_COMMAND   = "clear";
    static String          SEARCH_COMMAND  = "search";
    static String          SORT_COMMAND    = "sort";

    static String          ITEM1           = "Red Nocturne";
    static String          ITEM2           = "Blue Rhapsody";
    static String          ITEM3           = "Green Requiem";
    static String          ITEM4           = "Yellow Opera";
    static String          ITEM5           = "Power Stone";
    static String          ITEM6           = "Emerald Blues";
    static String          ITEM7           = "Crimson Jazz";

    @Before
    // Start each test with a fresh file. Also tests the add function.
    public void setUp() {

        testInstance = new TextBuddyHelper("mytestfile.txt", true);

        assertEquals("Added: " + ITEM1 + ". \n",
                     testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM1));
        assertEquals("Added: " + ITEM2 + ". \n",
                     testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM2));
        assertEquals("Added: " + ITEM3 + ". \n",
                     testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM3));
        assertEquals("Added: " + ITEM4 + ". \n",
                     testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM4));
    }

    @Test
    // Test the display function normally.
    public void testDisplayNormal() {

        assertEquals("All related content: " + "\n1. " + ITEM1 + "\n2. "
                             + ITEM2 + "\n3. " + ITEM3 + "\n4. " + ITEM4 + "\n",
                     testInstance.determineAndExecuteCommand(DISPLAY_COMMAND,
                                                             null));
    }

    @Test
    // Test the delete function's ability to delete the first line in a file
    // with more than one line. This then proceeds to check if it displays
    // correctly.
    public void testDeleteFirstNormal() {

        assertEquals("Deleted: " + ITEM1 + ". \n",
                     testInstance.determineAndExecuteCommand(DELETE_COMMAND,
                                                             "1"));
        assertEquals("All related content: " + "\n1. " + ITEM2 + "\n2. "
                             + ITEM3 + "\n3. " + ITEM4 + "\n",
                     testInstance.determineAndExecuteCommand(DISPLAY_COMMAND,
                                                             null));

    }

    @Test
    // Test the delete function's ability to delete a line in the middle of a
    // file with more than one line. This then proceeds to check if it displays
    // correctly.
    public void testDeleteMiddleNormal() {

        assertEquals("Deleted: " + ITEM2 + ". \n",
                     testInstance.determineAndExecuteCommand(DELETE_COMMAND,
                                                             "2"));
        assertEquals("All related content: " + "\n1. " + ITEM1 + "\n2. "
                             + ITEM3 + "\n3. " + ITEM4 + "\n",
                     testInstance.determineAndExecuteCommand(DISPLAY_COMMAND,
                                                             null));
    }

    @Test
    // Test the delete function's ability to delete a line at the end of a file
    // with more than one line. This then proceeds to check if it displays
    // correctly.
    public void testDeleteLastNormal() {

        assertEquals("Deleted: " + ITEM4 + ". \n",
                     testInstance.determineAndExecuteCommand(DELETE_COMMAND,
                                                             "4"));
        assertEquals("All related content: " + "\n1. " + ITEM1 + "\n2. "
                             + ITEM2 + "\n3. " + ITEM3 + "\n",
                     testInstance.determineAndExecuteCommand(DISPLAY_COMMAND,
                                                             null));
    }

    @Test
    // Test the delete function's ability to delete the only line in a file.
    public void testDeleteOneNormal() {

        testInstance.determineAndExecuteCommand(DELETE_COMMAND, "1");
        testInstance.determineAndExecuteCommand(DELETE_COMMAND, "1");
        testInstance.determineAndExecuteCommand(DELETE_COMMAND, "1");

        assertEquals("Deleted: " + ITEM4 + ". \n",
                     testInstance.determineAndExecuteCommand(DELETE_COMMAND,
                                                             "1"));
    }

    @Test
    // Test the delete function's ability to handle out of index inputs.
    public void testDeleteIndexBoundaries() {

        assertEquals("Unable to remove non-existent line. Please input a number from: 1 to 4. \n",
                     testInstance.determineAndExecuteCommand(DELETE_COMMAND,
                                                             "5"));
        assertEquals("Unable to remove non-existent line. Please input a number from: 1 to 4. \n",
                     testInstance.determineAndExecuteCommand(DELETE_COMMAND,
                                                             "-1"));
    }

    @Test
    // Test the delete function's ability to handle input strings which are not
    // in the set of whole natural numbers.
    public void testDeleteTypeMismatch() {

        assertEquals("Please only input a natural number in the range: 1 to 4. \n",
                     testInstance.determineAndExecuteCommand(DELETE_COMMAND,
                                                             "4.00"));
        assertEquals("Please only input a natural number in the range: 1 to 4. \n",
                     testInstance.determineAndExecuteCommand(DELETE_COMMAND,
                                                             "weyfgewiyfwegw"));

    }

    @Test
    // Test the delete function's ability to detect if the file is already
    // empty.
    public void testDeleteAlreadyClear() {

        testInstance.determineAndExecuteCommand(CLEAR_COMMAND, null);

        assertEquals("File is already empty. \n",
                     testInstance.determineAndExecuteCommand(DELETE_COMMAND,
                                                             "1"));
    }

    @Test
    // Test the clear function normally.
    public void testClearNormal() {

        assertEquals("All content cleared. \n",
                     testInstance.determineAndExecuteCommand(CLEAR_COMMAND,
                                                             null));
    }

    @Test
    // Test the clear function to respond to already empty files.
    public void testClearEmpty() {

        testInstance.determineAndExecuteCommand(CLEAR_COMMAND, null);

        assertEquals("File is already empty. \n",
                     testInstance.determineAndExecuteCommand(CLEAR_COMMAND,
                                                             null));
    }

    @Test
    // Test the display function's ability to respond to already empty files.
    public void testDisplayEmpty() {

        testInstance.determineAndExecuteCommand(CLEAR_COMMAND, null);

        assertEquals("No available related content to show. \n",
                     testInstance.determineAndExecuteCommand(DISPLAY_COMMAND,
                                                             null));

    }

    @Test
    // Test determineAndExecuteCommand function's ability to handle unexpected
    // command types.
    public void testUnexpectedCommandTypes() {

        assertEquals("Unknown command. Please re-enter. "
                             + "\n"
                             + "Available Commands: Add, Display, Delete, Clear, Exit, Search, Sort"
                             + ". \n",
                     testInstance.determineAndExecuteCommand("Not A Valid Command",
                                                             "Who cares?"));

        assertEquals("Null command given. \n",
                     testInstance.determineAndExecuteCommand(null, "Who cares?"));
    }

    @Test
    // Test for Search.
    public void testSearchNormal() {

        assertEquals("All related content: " + "\n1. " + ITEM1 + "\n2. "
                             + ITEM2 + "\n3. " + ITEM3 + "\n",
                     testInstance.determineAndExecuteCommand(SEARCH_COMMAND,
                                                             "R"));
    }

    @Test
    // Test the output executeSearchCommand gives when there are no hits for the
    // given search term.
    public void testSearchNoneFound() {

        assertEquals("No available related content to show. \n",
                     testInstance.determineAndExecuteCommand(SEARCH_COMMAND,
                                                             "asdioahidfh"));
    }

    @Test
    // Test for Search error messages
    public void testSearchError() {

        assertEquals("Please make sure your input contains at least one letter or number"
                             + ". \n",
                     testInstance.determineAndExecuteCommand(SEARCH_COMMAND,
                                                             " "));
    }

    @After
    // Prints a line after each test
    public void printSingleLine() {

        System.out.println();
    }

    @Test
    // Test for Sort.
    public void testSort() {

        assertEquals("Items sorted. \n",
                     testInstance.determineAndExecuteCommand(SORT_COMMAND, null));
        assertEquals("All related content: " + "\n1. " + ITEM2 + "\n2. "
                             + ITEM3 + "\n3. " + ITEM1 + "\n4. " + ITEM4 + "\n",
                     testInstance.determineAndExecuteCommand(DISPLAY_COMMAND,
                                                             null));

    }
}
