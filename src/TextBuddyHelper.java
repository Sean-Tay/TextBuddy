/*
 *  TextBuddyHelper [Build Version 1.0]
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
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TextBuddyHelper {
	
	static String filePath = System.getProperty("user.dir");
	
	//Constants
	static final String GEN_ERROR = "Something went wrong!";
	
	private static File file;
	private static List<String> fileContents;
	
	//First-Tier Operations
	
	public TextBuddyHelper(String fileName) {
		
		/**
		 * Initialize elements
		 */
		
		file = new File(fileName);
		fileContents = new ArrayList<String>();
		
	}
	
	public static void startUp() {
		
		/**
		 * Called by driver class when starting up
		 */

		checkFileExists();
		
		createTheFile();
		
		printWelcomeMessage();
		
		displayMenu();
	}

	private static void checkFileExists() {
		
		/**
		 * Checks if the file exists. If file exists, read in contents of file and store in fileContents list. Else, print not found message, and leave fileContents to be empty. 
		 */
		
		if (!file.exists()) {
			
			System.out.println(file.toString() + " does not exist in root directory: " + filePath + ". Creating the file now.");
		}
		else {
			
			fileContents = fillList(fileContents);
		}
	}
	
	private static List<String> fillList(List<String> list) {
		
		/**
		 * Fills a given list with file contents, and returns the list
		 */
		
		try {
			
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			String line = reader.readLine();
			
			if (line == null) {
				
				System.out.println(file.toString() + " is empty.");
			}
			
			int lineNum = 1;
			
			while (line != null) {
				
				list.add(line);
				line = reader.readLine();
				lineNum++;
			}
			
			reader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error with reader.");
		} catch (IOException e) {
			System.out.println("Error with loading content from file.");
		}
		
		return list;
	}
	
	private static void createTheFile() { 

		/**
		 * Creates a new file based on given Command Line Argument
		 */
		
		try {	
			FileWriter fileWriter = new FileWriter(file, file.exists());
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

	//Second-Tier Operations
	
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
			
				case ("add") :
					if (sc.hasNext()) {
						
						String toAdd = sc.nextLine();
						
						if (!canAddText(toAdd)) {
							System.out.println(GEN_ERROR);
						}
					}
					break;
				
				case ("display") :
					if (!canDisplayText()) {
						
						System.out.println(GEN_ERROR);
					}
					break;
				
				case ("delete") :
					if (sc.hasNextInt()) {
						
						int delLineNum = sc.nextInt();
						
						if (!canDeleteText(delLineNum)) {
							
							System.out.println(GEN_ERROR);
						}
					}
					break;
				
				case ("clear") :
					if (!canClearText()) {
						
						System.out.println(GEN_ERROR);
					}
					break;
				
				case ("exit") :
					exit = true;
					break;
					
				case ("sort") :
					if (!canSortText()) {
						
						System.out.println(GEN_ERROR);
					}
					
					break;
				
				case ("search") :
					
					String searchItem = sc.nextLine();
					
					if (!canSearchText(searchItem)) {
						
						System.out.println(GEN_ERROR);
					}
					
					break;
				
				default :
					System.out.println("Unknown command. Please re-enter.");
					System.out.println("Available Commands: Add, Display, Delete, Clear, Exit");
					sc.nextLine();
					break;
			}
			
			writeList(fileContents);
	
		}
		
		sc.close();
		
	}

	private static void writeList(List<String> list){
		
		/**
		 * Writes a given list of Strings to the file
		 */
		
		try {
			FileWriter fileWriter = new FileWriter(file, false);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			
			for (int index=0; index<list.size(); index++) {
				writer.write(list.get(index) + "\n");
			}
			
			writer.close();
			fileWriter.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("Error with writing content to file.");
		}
	}

	//canAddText()
	
	private static boolean canAddText(String toAdd) {
		
		/**
		 * Appends a given line of text to fileContents
		 */
		
		if (checkValidString(toAdd)) {
			
			toAdd = toAdd.substring(1, toAdd.length());
			fileContents.add(toAdd);
			System.out.println("Added: " + toAdd);
			return true;
		}
		
		return false;
		
	}
	
	//canDisplayText()
	
	private static boolean canDisplayText() {
		
		/**
		 * Prints fileContents.
		 */
		
		printList(fileContents, true, fileContents);
		return true;
	}
	
	//canDeleteText()
	
	private static boolean canDeleteText(int lineNum) {
		
		/**
		 * Deletes a line from fileContents
		 * Assumes user does not take into account that the starting index is 0 and not 1
		 */
		
		try {
			
			
			String removedLine = fileContents.remove(lineNum - 1);
			System.out.println("Deleted: " + removedLine);
			
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Unable to remove non-existent line. Please input a number from: 1 to " + fileContents.size() + ".");
			return false;
		}
		return true;
	}

	//canClearText()
	
	private static boolean canClearText() {
		
		/**
		 * Deletes all content from fileContents
		 */
		
		fileContents.clear();
		System.out.println("All content cleared.");
		return fileContents.isEmpty();
	}
	
	//canSortText()
	
	private static boolean canSortText() {
		
		/**
		 * Sorts fileContents
		 */
		
		Collections.sort(fileContents);
		System.out.println("Items sorted.");
		return true;
	}

	//canSearchText()
	
	private static boolean canSearchText(String searchItem) {
		
		/**
		 * Given a searchItem, checks if it exists in fileContents.
		 */
		
		if (checkValidString(searchItem)) {
			
			obtainingSearchResults(searchItem);
			return true;
		}
		else {

			System.out.println("Please enter a non-empty search term.");
			return false;
		}
	}

	private static void obtainingSearchResults(String searchItem) {
		
		/**
		 * Code that actually handles the search function.
		 */
		
		List<String> results = new ArrayList<String>();
		searchItem = searchItem .substring(1, searchItem.length());
		
		for (int i=0; i<fileContents.size(); i++) {
			
			if (fileContents.get(i).contains(searchItem)) {
				results.add(fileContents.get(i));
			}
		}
		
		printList(results, false, fileContents);
	}
	
	//Additional Functions
	private static boolean checkValidString(String toCheck) {
		
		/**
		 * Check if given String is valid.
		 */
		
		if (toCheck.isEmpty()) {
			return false;
		}
		
		if (toCheck.equals(" ")) {
			return false;
		}
		
		return true;
	}
		
	private static void printList(List<String> list, boolean defaultBehavior, List<String> secondList)
	{
		/**
		 * Given a list, prints all contents out, behavior changes according to second argument
		 */
		
		if (defaultBehavior) {
			
			/**
			 * Prints accompanying index as per normal
			 */
			
			if (!list.isEmpty()) {
				System.out.println("All related content: ");
				
				for (int index=0; index<list.size(); index++) {
					
					System.out.println((index+1) + ". " + list.get(index));
				}
			}
			else
			{
				System.out.println("No content to display.");
			}
		}
		else {
			
			/**
			 * Prints accompanying index in relation to secondList
			 */
			
			System.out.println("Results: ");
			
			for (int index=0; index<list.size(); index++) {
				
				System.out.println((secondList.indexOf(list.get(index))+1) + ". " + list.get(index));
			}
			
			
		}
			
	}
	
}


