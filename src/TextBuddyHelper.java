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
	static final String GEN_ERROR_MSG = "Something went wrong!";
	
	private static File file;
	private static List<String> fileContents;
	
	//First-Tier Operations
	
	private static void TextBuddyConstructor(String fileName) {
		
		/**
		 * Pseudo-Constructor to initialize elements. Should not be called by any other file.
		 * 
		 * @param fileName: The name of the file to be loaded.
		 */
		
		file = new File(fileName);
		fileContents = new ArrayList<String>();
		
		fileContents = fillList(fileContents, file);
		
		
	}
	
	public static void startUp(String fileName) {
		
		/**
		 * Called by driver class when starting up. This is the regular startup route.
		 * 
		 * @param fileName: Name of file to be loaded.
		 */
		
		TextBuddyConstructor(fileName);

		checkFileExists();
		
		createTheFile();
		
		printWelcomeMessage();
		
		displayMenu();
	}
	
	public static void testStartUp(String fileName) {
		
		/**
		 * Solely used by TextBuddyTester.java for testing purposes.
		 * 
		 * @param fileName: The name of the file to be loaded.
		 */
		
		TextBuddyConstructor(fileName);
		
		createTheFile();
		
	}

	private static List<String> fillList(List<String> list, File file) {
		
		/**
		 * Fills a given list with given file contents, and returns the list.
		 * 
		 * @param list: The List<String> object to be filled with file contents.
		 * @param file: the File object to be used to fill up list with its contents.
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
	
	private static void checkFileExists() {
		
		/**
		 * Checks if the file exists. If file does not exist, print file creation message. 
		 */
		
		if (!file.exists()) {
			
			System.out.println(file.toString() + " does not exist in root directory: " + filePath + ". Creating the file now.");
		}
	}
	
	private static void createTheFile() { 

		/**
		 * Creates a new file based on given Command Line Argument.
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
		 * Prints the message that is shown upon starting the application.
		 */
		
		System.out.print("Welcome to TextBuddy. ");
		System.out.print("\"" + file.toString() + "\" ");
		System.out.println("is ready for use.");
	}

	//Second-Tier Operations
	
	private static void displayMenu() { 

		/**
		 * Function to handle the UI.
		 */
		
		boolean toContinue = true;
		Scanner sc = new Scanner(System.in);
		
		while (toContinue) {	
			
			
			System.out.print("command: ");
			
			String command = sc.next();
			String trailingContent = sc.nextLine();
			
			if (!trailingContent.isEmpty()) {
				
				trailingContent = trailingContent.substring(1, trailingContent.length());
			}
			
			boolean isSuccessful = determineAndExecuteCommand(command, trailingContent);
			
			if (!isSuccessful) {
				
				System.out.println(GEN_ERROR_MSG);
			}
			
			writeList(fileContents, file);

			if (command.equals("exit")) {
				
				toContinue = false;
			}
	
		}
		
		sc.close();
	}

	public static boolean determineAndExecuteCommand(String command,
			String trailingContent) {
		
		/**
		 * The function that determines the command-type.
		 * 
		 * @param command: The type of command to be executed.
		 * @param trailingContent: Any input entered after the command.
		 */
		
		boolean isSuccessful = false;
		
		switch (command.toLowerCase()) {
		
			case ("add") :
				isSuccessful = executeAddCommand(trailingContent);
				break;
			
			case ("display") :			
				isSuccessful = executeDisplayCommand();
				break;
			
			case ("delete") :				
				isSuccessful = executeDeleteCommand(trailingContent);
				break;
			
			case ("clear") :
				isSuccessful = executeClearCommand();
				break;
			
			case ("exit") :
				isSuccessful = true;
				break;
				
			case ("sort") :			
				isSuccessful = executeSortCommand();
				break;
			
			case ("search") :
				isSuccessful = executeSearchCommand(trailingContent);					
				break;
			
			default :
				System.out.println("Unknown command. Please re-enter.");
				System.out.println("Available Commands: Add, Display, Delete, Clear, Exit");
				break;
		}
		
		return isSuccessful;
	}

	private static void writeList(List<String> list, File file){
		
		/**
		 * Writes a given list of Strings to a file
		 * 
		 * @param list: The List object that is the reference for the content to be written into file.
		 * @param file: The File object for the contents of list to be written to.
		 */
		
		try {
			FileWriter fileWriter = new FileWriter(file, false);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			
			for (int index = 0; index < list.size(); index++) {
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

	//executeAddCommand()
	
	private static boolean executeAddCommand(String toAdd) {
		
		/**
		 * Appends a given line of text to fileContents
		 * 
		 * @param toAdd: String to be added into fileContents.
		 */
		
		if (isValidString(toAdd)) {
			
			fileContents.add(toAdd);
			System.out.println("Added: " + toAdd);
			return true;
		}
		
		return false;
	}
	
	//executeDisplayCommand()
	
	private static boolean executeDisplayCommand() {
		
		/**
		 * Prints fileContents.
		 */
		
		printList(fileContents, true, null);
		return true;
	}
	
	//executeDeleteCommand()
	
	private static boolean executeDeleteCommand(String toDelete) {
		
		/**
		 * Function to be called when executing Delete Command. Returns a success boolean upon completion.
		 * Assumes user enters a Line Number
		 * Assumes user does not take into account that the starting index is 0 and not 1
		 * 
		 * @param toDelete: A string containing the line number that is to be deleted.
		 */
		
		try {
			
			if (isValidString(toDelete)) {
				
				int delLineNum = Integer.parseInt(toDelete);
			
				String removedLine = fileContents.remove(delLineNum - 1);
				System.out.println("Deleted: " + removedLine);
				return true;
			}
			
			
		} catch (NumberFormatException e) {
			
			System.out.println("Please only input a natural number in the range: 1 to " + fileContents.size() + ".");		
			
		} catch (IndexOutOfBoundsException e) {
			
			System.out.println("Unable to remove non-existent line. Please input a number from: 1 to " + fileContents.size() + ".");
		}
		
		return false;
	}
	
	//executeClearCommand()
	
	private static boolean executeClearCommand() {
		
		/**
		 * Function to be called when executing the Clear command. Returns a success boolean upon completion.
		 */
		
		fileContents.clear();
		System.out.println("All content cleared.");
		return fileContents.isEmpty();
	}
	
	//executeSortCommand()
	
	private static boolean executeSortCommand() {
		
		/**
		 * Function to be called when executing the Sort command. Returns a success boolean upon completion.
		 */
		
		Collections.sort(fileContents);
		System.out.println("Items sorted.");
		return true;
	}

	//executeSearchCommand()
	
	private static boolean executeSearchCommand(String searchItem) {
		
		/**
		 * Function to be called when executing the Search command. Returns a success boolean upon completion.
		 * 
		 * @param searchItem: String object containing string to be searched within fileContents.
		 */
		
		return canSearchText(searchItem);
	}
	
	private static boolean canSearchText(String searchItem) {
		
		/**
		 * Given a searchItem, checks if it exists in fileContents.
		 * 
		 * @param searchItem: String object containing string to be searched within fileContents.
		 */
		
		if (isValidString(searchItem)) {
			
			obtainingSearchResults(searchItem);
			return true;
		}
		return false;
	}

	private static void obtainingSearchResults(String searchItem) {
		
		/**
		 * Code that actually handles the search function.
		 * 
		 * @param searchItem: String object containing string to be searched within fileContents.
		 */
		
		List<String> results = new ArrayList<String>();
		
		for (int i=0; i<fileContents.size(); i++) {
			
			if (fileContents.get(i).contains(searchItem)) {
				results.add(fileContents.get(i));
			}
		}
		
		printList(results, false, fileContents);
	}
	
	//Additional Functions
	
	private static boolean isValidString(String toCheck) {
		
		/**
		 * Check if given String is valid.
		 * 
		 * @param toCheck: The String object to be checked.
		 */
		
		if (toCheck.trim().isEmpty()) {
			
			System.out.println("Please make sure your input contains at least one letter or number."); 
			return false;
		}
		
		return true;
		
	}
		
	private static void printList(List<String> list, boolean defaultBehavior, List<String> secondList) {
		
		/**
		 * Given a list, prints all contents out, behavior changes according to second argument
		 * 
		 * @param list: The List object to be printed.
		 * @param defaultBehavior: Determines function behavior. If this is true, it will print accompanying indexes of each entry as per normal. 
		 * 						   If this is false, it will print accompanying indexes of each entry in relation to their position in the second list.
		 * @param secondList: See defaultBehavior.
		 */
		
		if (defaultBehavior) {
			
			/*
			 * Prints accompanying index as per normal
			 */
			
			if (!list.isEmpty()) {
				
				System.out.println("All related content: ");
				
				for (int index = 0; index < list.size(); index++) {
					
					System.out.println((index+1) + ". " + list.get(index));
				}
			}
			else {
				
				System.out.println("No content to display.");
			}
		}
		else {
			
			/*
			 * Prints accompanying index in relation to secondList
			 */
			if (secondList != null) {
				
				System.out.println("Results: ");
				
				for (int index = 0; index < list.size(); index++) {
					
					System.out.println((secondList.indexOf(list.get(index))+1) + ". " + list.get(index));
				}
			
			}
			else {
				
				System.out.println("Null list given.");
			}
			
		}
			
	}
	
}


