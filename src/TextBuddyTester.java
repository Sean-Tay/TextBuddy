import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TextBuddyTester {
	
	static TextBuddyHelper testInstance = new TextBuddyHelper ("mytestfile.txt", true);
	
	static String ADD_COMMAND = "add";
	static String DISPLAY_COMMAND = "display";
	static String DELETE_COMMAND = "delete";
	static String CLEAR_COMMAND = "clear";
	static String SEARCH_COMMAND = "search";
	static String SORT_COMMAND = "sort";
	
	static String ITEM1 = "Red Nocturne";
	static String ITEM2 = "Blue Rhapsody";
	static String ITEM3 = "Green Requiem";
	static String ITEM4 = "Yellow Opera";
	static String ITEM5 = "Power Stone";
	static String ITEM6 = "Emerald Blues";
	static String ITEM7 = "Crimson Jazz";
	
	@Before
	//Adds a single empty line first
	public void setUp() {
		System.out.println();
	}
	
	@Test
	//Tests the Add function.
	public void test001() {
		
		assertEquals("Added: " + ITEM1 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM1));
		assertEquals("Added: " + ITEM2 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM2));
		assertEquals("Added: " + ITEM3 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM3));
		assertEquals("Added: " + ITEM4 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM4));
		
	}

	@Test 
	//Tests the Display function.
	public void test002() {

		assertEquals("All related content: " + "\n1. " + ITEM1 + "\n2. " + ITEM2 + "\n3. " + ITEM3 +"\n4. " + ITEM4 + "\n", testInstance.determineAndExecuteCommand(DISPLAY_COMMAND, null));
	}
	
	@Test
	//Tests the Delete function in a variety of settings (first line in file, middle line in file, last line in file, last item in the file)
	public void test003() {
		
		assertEquals("Deleted: " + ITEM1 + ". \n", testInstance.determineAndExecuteCommand(DELETE_COMMAND, "1"));
		assertEquals("Deleted: " + ITEM3 + ". \n", testInstance.determineAndExecuteCommand(DELETE_COMMAND, "2"));
		assertEquals("Deleted: " + ITEM4 + ". \n", testInstance.determineAndExecuteCommand(DELETE_COMMAND, "2"));
		
		
		assertEquals("All related content: " + "\n1. " + ITEM2 + "\n", testInstance.determineAndExecuteCommand(DISPLAY_COMMAND, null));
		
		
		assertEquals("Deleted: " + ITEM2 + ". \n", testInstance.determineAndExecuteCommand(DELETE_COMMAND, "1"));
		
		
		assertEquals("Added: " + ITEM1 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM1));
		assertEquals("Added: " + ITEM2 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM2));
		assertEquals("Added: " + ITEM3 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM3));
		assertEquals("Added: " + ITEM4 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM4));
	}
	
	
	@Test
	//Tests the Clear function.
	public void test004() {
		
		assertEquals("All content cleared. \n", testInstance.determineAndExecuteCommand(CLEAR_COMMAND, null));
		
	}
	
	@Test
	//Tests the Clear function's error message.
	public void test005() {
		
		assertEquals("File is already empty. \n", testInstance.determineAndExecuteCommand(CLEAR_COMMAND, null));
		
	}
	
	@Test
	//Tests the Display function's error message.
	public void test006() {
		
		assertEquals("No content to display. \n", testInstance.determineAndExecuteCommand(DISPLAY_COMMAND, null));
	}
	
	@Test
	//Test the Delete function's error messages.
	public void test007() {
		
		assertEquals("File is already empty. \n", testInstance.determineAndExecuteCommand(DELETE_COMMAND, "5"));
		
		assertEquals("Added: " + ITEM1 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM1));
		
		assertEquals("Unable to remove non-existent line. Please input a number from: 1 to 1. \n", testInstance.determineAndExecuteCommand(DELETE_COMMAND, "5"));
		
		assertEquals("Added: " + ITEM2 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM2));
		assertEquals("Added: " + ITEM3 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM3));
		
		assertEquals("Unable to remove non-existent line. Please input a number from: 1 to 3. \n", testInstance.determineAndExecuteCommand(DELETE_COMMAND, "5"));
		
		assertEquals("All content cleared. \n", testInstance.determineAndExecuteCommand(CLEAR_COMMAND, null));	
	}
	
	@Test
	//Test for unexpected Commands.
	public void test008() {
		
		assertEquals("Unknown command. Please re-enter. " + "\n" + "Available Commands: Add, Display, Delete, Clear, Exit" + ". \n", testInstance.determineAndExecuteCommand("lalala", null));
	}

	@Test
	//Test for Search.
	public void test009() {
		
		assertEquals("Added: " + ITEM1 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM1));
		assertEquals("Added: " + ITEM2 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM2));
		assertEquals("Added: " + ITEM3 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM3));
		assertEquals("Added: " + ITEM4 + ". \n", testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM4));
		
		assertEquals("Results: " + "\n1. " + ITEM1 + "\n2. " + ITEM2 + "\n3. " + ITEM3 + "\n", testInstance.determineAndExecuteCommand(SEARCH_COMMAND, "R"));
		
	}
	
	@Test
	//Test for Search error messages
	public void test010() {
		
		assertEquals("Please make sure your input contains at least one letter or number" + ". \n", testInstance.determineAndExecuteCommand(SEARCH_COMMAND, " "));
	}
	
	@Test
	//Test for Sort.
	public void test011() {
		
		assertEquals("Items sorted. \n", testInstance.determineAndExecuteCommand(SORT_COMMAND, null));
	}
}
