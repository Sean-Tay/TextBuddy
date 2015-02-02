/*
 *  TextBuddy [Build Version 0.0]
 *  Tay Siang Meng Sean
 *  
 *  This program allows the user to edit a text file via Command Line. 
 *  Basic CRUD operations.
 *  
 *  Assumptions:
 *  1) Will not overwrite the file the user specifies with each startup (i.e. load file if file exists)
 *  
 */

public class TextBuddy {
	public static void main(String args[])
	{
		new TextBuddyHelper();
		TextBuddyHelper.start(args);
	}
}
