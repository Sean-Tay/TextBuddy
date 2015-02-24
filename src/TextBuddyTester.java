import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TextBuddyTester {
	
	static TextBuddyHelper testInstance = null;
	
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
	//Also tests Add function incidentally.
	public void setUp() {
		
		TextBuddyHelper testInstance = new TextBuddyHelper ("mytestfile.txt", true);
		
		assertTrue(testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM1));
		assertTrue(testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM2));
		assertTrue(testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM3));
		assertTrue(testInstance.determineAndExecuteCommand(ADD_COMMAND, ITEM4));
	}

	@Test 
	//Tests and sees if TextBuddy can display things.
	public void testDisplay() {
		
		testInstance.determineAndExecuteCommand(DISPLAY_COMMAND, null);
	}

}
