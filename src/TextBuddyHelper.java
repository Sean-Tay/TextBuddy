/*
 *  TextBuddyHelper [Build Version 0.0]
 *  Tay Siang Meng Sean
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class TextBuddyHelper{
	
	static String filePath = System.getProperty("user.dir");
	
	//Constants
	static final String GEN_ERROR = "Something went wrong!";
	
	//File IO variables
	private static File file;
	private static File tempFile;
	
	private static FileWriter fileWriter;
	private static BufferedWriter writer;
	
	private static FileWriter tempWriter;
	private static BufferedWriter tWriter;
	
	private static FileReader fileReader;
	private static BufferedReader reader;
	
	//First-Tier Operations
	
	public TextBuddyHelper() { 
		
		/**
		 *  Constructor to initialize IO elements
		 */

		file = null;
		tempFile = null;
		
		fileWriter = null;
		writer = null;
		
		tempWriter = null;
		tWriter = null;
		
		fileReader = null;
		reader = null;
		
	}
	
	public static void start(String[] args) { 

		/**
		 * Program operation starts here
		 */

		preErrorChecks(args); 
		
	    createFileProcess(args); 
	    
		printWelcomeMessage();
	    
	    displayMenu(); 
		 
	}
	
	private static void preErrorChecks(String args[]) { 

		/**
		 *  Perform validity checks on given arguments
		 */
		
		if (args.length <= 0) {
	    	System.out.println("No command-line arguments given. Closing program.");
	        System.exit(0);
		}
	}
	
	private static void createFileProcess(String args[]) { 

		/**
		 * To start the file creation process
		 */
		
		file = new File(args[0]); 
		
    	//checkIfFileExists();
    	
		createTheFile();
	}

	private static void checkIfFileExists() { 

		/**
		 * Check if the file exists
		 */
		
		if (!file.exists()) {
			printNotFileFoundMsg(file);
    	}
	}
	
	private static void printNotFileFoundMsg(File file) { 

		/**
		 *  Function to print out a message stating where the newly created file will be
		 */
		
		System.out.print("No file found. Creating a file called \"" + file.toString() + "\" at: ");
		System.out.println(filePath + ".");
		System.out.println();
	}

	private static void createTheFile() { 

		/**
		 * Creates a new file based on given Command Line Argument
		 */
		
		try {	
			fileWriter = new FileWriter(file, file.exists());
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Error with Writer. Please restart the application.");
			System.exit(0);
		}
	}

	private static void printWelcomeMessage() { 

		/**
		 * Prints the message that is shown upon starting the application
		 */
		
		System.out.print("Welcome to TextBuddy. ");
		System.out.print("\"" + file.toString() + "\" ");
		System.out.println("is ready for use.");
	}
	
	//Second Tier Operations
	
	private static void displayMenu() { 

		/**
		 * Function to call for the UI
		 */
		
		boolean exit = false;
		Scanner sc = new Scanner(System.in);
		
		while (!exit) {	
			
			System.out.print("command: ");
			String command = sc.next();
			
			switch (command.toLowerCase()) {
			
				case("add"):
					if (sc.hasNext()) {
						
						String toAdd = sc.nextLine();
						
						if (!canAddText(toAdd)) {
							System.out.println(GEN_ERROR);
						}
					}
					break;
				
				case("display"):
					if (!canDisplayText()) {
						
						System.out.println(GEN_ERROR);
					}
					break;
				
				case("delete"):
					if (sc.hasNextInt()) {
						
						int delLineNum = sc.nextInt();
						
						if (!canDeleteText(delLineNum)) {
							
							System.out.println(GEN_ERROR);
						}
					}
					break;
				
				case("clear"):
					if (!canClearText()) {
						
						System.out.println(GEN_ERROR);
					}
					break;
				
				case("exit"):
					exit = true;
					break;
					
				case("sort"):
					if (!canSortText()) {
						
						System.out.println(GEN_ERROR);
					}
					
					break;
				
				case("search"):
					
					String searchItem = sc.nextLine();
					
					if (!canSearchText(searchItem)) {
						
						System.out.println(GEN_ERROR);
					}
					
					break;
				
				default:
					System.out.println("Unknown command. Please re-enter.");
					System.out.println("Available Commands: Add, Display, Delete, Clear, Exit");
					sc.nextLine();
					break;
			}
				
		}
		
		sc.close();
		resetIO();
		
	}
	
	//canAddText Operations
	
	private static boolean canAddText(String toAdd) { 

		/**
		 * Function to call when adding one line of text to document
		 */
		
		toAdd = toAdd.substring(1, toAdd.length());
		
		try {
			addTextInit();
			
			addingTheText(toAdd);
			
			return true;
			
		} catch (IOException e) {
			
		}
		return false;
	}

	private static void addTextInit() throws IOException { 

		/**
		 * Initializes canAddText variables
		 */
		
		resetIO();
		
		fileWriter = new FileWriter(file, true);
		writer = new BufferedWriter(fileWriter);
	}
		
	private static void addingTheText(String toAdd) throws IOException { 

		/**
		 * The code that actually adds the line to the file
		 */
		
		writer.write(toAdd);
		writer.newLine();
		writer.flush(); //Needed to reset stream
		
		System.out.println("added to " + file.toString() + ": " + "\"" + toAdd + "\"");
		
		resetIO();
	}
	
	//canDisplayText Operations
	
	private static boolean canDisplayText() { 

		/**
		 * Function to call for displaying contents of loaded text file
		 */
		
		displayTextInit();
		
		try {
			
			displayingTheText();

			return true;
			
		} catch (IOException e) {
			
			return false;
		}		
	}

	private static void displayTextInit() {
		
		/**
		 * Initializes IO variables for canDisplayText()
		 */
		
		resetIO();
		
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println("Error with Reader.");
		}
		reader = new BufferedReader(fileReader);
	}
	
	private static void displayingTheText() throws IOException {
		
		/**
		 * The code that actually displays the text to the user
		 */
		
		String line = reader.readLine();
		
		if (line == null) {
			
			System.out.println(file.toString() + " is empty");
		}
		
		int lineNum = 1;
		
		while (line != null) {
			
			System.out.println(lineNum + ". " + line);
			line = reader.readLine();
			lineNum++;
		}
		
		resetIO();
	}
	
	//canDeleteText Operations
	
	private static boolean canDeleteText(int givenLineNum) { 

		/**
		 * Function to call when a line is to be 'deleted' from the text file 
		 */
		
		String nameOfFile = file.toString();
		String deletedLine = null;
		String line = null;
		
		deleteTextInit();
		
		try {
			
			int currLineNum = 1;
			boolean invalidLineNumGiven = true;

			line = reader.readLine();
			
			while (line != null) {
				
				if (currLineNum == givenLineNum) {
					
					invalidLineNumGiven = false;
					deletedLine = line;
					line = reader.readLine();
					currLineNum++;
				}

				if (line != null) {
					
					tWriter.write(line);
					tWriter.newLine();
					line = reader.readLine();
				}
				
				currLineNum++;
				
			}
			
			deleteTextComplete(nameOfFile, deletedLine, invalidLineNumGiven);
			
			return true;
			
		}
		catch (IOException e) {
			
		}
		catch(NullPointerException e2) {
			
			System.out.println("Unable to continue reading. Line number given may have exceeded current number of lines in text file.");
		}
		return false;
	}

	private static void deleteTextInit() { 
		
		resetIO();
		
		/** 
		 * Initializes canDeleteText() IO variables
		 */
		
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println("Error with Reader.");
		}
		reader = new BufferedReader(fileReader);
		
		tempFile = new File("tempfile.txt");
		try {
			tempWriter = new FileWriter(tempFile);
		} catch (IOException e) {
			System.out.println("Unable to create temporary file. Recommend restarting program.");
		}
		tWriter = new BufferedWriter(tempWriter);
	}
	
	private static void deleteTextComplete(String nameOfFile, String deletedLine, boolean invalidLineNumGiven) throws IOException {
		
		/**
		 * Overwrites the old file with the new file that does not contain the deleted line
		 */
		
		resetIO();
		
		if (!invalidLineNumGiven)
		{
			//Given line number was valid. Proceed to replace original file with edited tempFile
			
			file.delete();
			
			boolean success = tempFile.renameTo(file); //Can only be done AFTER the writer closes
			
			file = new File(nameOfFile);
			
			System.out.println("deleted from " + file.toString() + " : " + "\"" + deletedLine + "\"");
		}
		else
		{
			System.out.println("Unable to delete line that does not exist.");
		}
		
		tempFile.delete();
	}
	
	//canClearText Operations
	
	private static boolean canClearText() { 

		/**
		 * Function called when deleting all content from loaded text file
		 */
		
		if (!canOverwrite()) {
			return false;
		}
		
		System.out.println("all content deleted from " + file.toString());
		return true;
		
	}

	private static boolean canOverwrite() {
		
		/**
		 * Overwrites main file
		 */
		
		resetIO();
		
		
		try {
			fileWriter = new FileWriter (file);
			fileWriter.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
		
	//canSortText Operations
	
	private static boolean canSortText() {
		
		return false;
	}
	
	//canSearchText Operations
	
	private static boolean canSearchText(String searchItem) {
		
		/**
		 * Searches for a given string within the file and prints the corresponding lines of text in the file that contains the searchItem
		 */
		
		System.out.println("Search Item Given:" + searchItem);
		
		if (checkValidSearchString(searchItem)) {
			
			List<String> searchResults = searchTextInit();
			
			try {
				
				searchResults = searchingTheText(searchResults, searchItem);
				searchTextComplete(searchResults);
				
			} catch (IOException e) {
				System.out.println("Unable to search text.");
			}
			
			return true;
			
		}
		
		return false;
	}
	
	private static boolean checkValidSearchString(String searchItem) {
		
		/**
		 * Actual checks for checking if searchItem given is valid
		 */
		
		if (searchItem.length()==0) {
			
			System.out.println("Please enter a valid non-empty search item.");
		}
		
		return true;
	}
	
	private static List<String> searchTextInit() {
		
		/**
		 * Initializes canSearchText() IO variables
		 */

		resetIO();
		
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println("Error with reader.");
		}
		reader = new BufferedReader (fileReader);
		
		List<String> searchResults = new ArrayList<String>();
		
		return searchResults;
	}
	
	private static List<String> searchingTheText(List<String> searchResults, String searchItem) throws IOException {
		
		/**
		 * Code that actually does the searching
		 */
		
		searchItem = searchItem.substring(1, searchItem.length());
		
		String line = reader.readLine();
		
		if (line == null) {
			
			System.out.println(file.toString() + " is empty");
		}
		
		int lineNum = 1;
		
		while (line != null) {
			
			if (line.contains(searchItem)) {
				
				searchResults.add(lineNum + ". " + line);
			}

			line = reader.readLine();
			lineNum++;
		}
		
		return searchResults;
		
	}
	
	private static void searchTextComplete(List<String> searchResults) {
		
		/**
		 * Prints out the list of search items found
		 */

		resetIO();
		
		if (searchResults.size() != 0) {
			printList(searchResults);
		}
		else {
			System.out.println("No items match or contain the search item.");
		}
	}
	
	//Additional Functions
	
	private static void printList(List list)
	{
		/**
		 * Given a list, prints all contents out
		 */
		
		System.out.println("List contents: ");
		
		for (int index=0; index<list.size(); index++) {
			
			System.out.println(list.get(index) + " ");
		}
		
	}
	
	private static void resetIO() {
		
		/**
		 * To be called to force reset all IO handlers, except file and tempFile
		 * Each try-catch has to be separated to ensure forceful nature
		 */
		
		try {
			reader.close();
		} 
		catch (IOException e) {
			
		}	
		catch (NullPointerException e2) {
			
		}
		
		try {
			fileReader.close();
		} 
		catch (IOException e) {
			
		}
		catch (NullPointerException e2)
		{
			
		}
		
		try {
			writer.close();
		} 
		catch (IOException e) {
			
		}
		catch (NullPointerException e2)
		{
			
		}
		
		try {
			fileWriter.close();
		} 
		catch (IOException e) {
			
		}
		catch (NullPointerException e2)
		{
			
		}
		
		try {
			tWriter.close();
		} 
		catch (IOException e) {
			
		}
		catch (NullPointerException e2)
		{
			
		}
		
		try {
			tempWriter.close();
		} 
		catch (IOException e) {
			
		}
		catch (NullPointerException e2)
		{
			
		}
		
		reader = null;
		fileReader = null;
		
		writer = null;
		fileWriter = null;
		
		tWriter = null;
		tempWriter = null;
		
		System.gc();
	}
}


