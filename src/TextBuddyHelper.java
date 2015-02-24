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
	
	String filePath = System.getProperty("user.dir");
	
	//Constants
	static final String GEN_ERROR_MSG = "Something went wrong!";
	static final String SUCCESS_MSG = "Success";
	
	private File file;
	private List<String> fileContents;
	
	//First-Tier Operations
	
	public TextBuddyHelper(String fileName, boolean toCleanLoad) {
		
		/**
		 * Constructor to initialize elements.
		 * 
		 * @param fileName: The name of the file to be loaded/created.
		 * @param toCleanLoad: If set to true, then a new file will be created, and any files of the same name will be overridden.
		 * 					   If set to false, then if there are files of the same name, that will be loaded instead.
		 */
		
		file = new File(fileName);
		fileContents = new ArrayList<String>();
		
		checkFileExists();
		
		createTheFile(toCleanLoad);
		
		fileContents = fillList(fileContents, file);
		
		
	}
	
	private void checkFileExists() {
		
		/**
		 * Checks if the file exists. If file does not exist, print file creation message. 
		 */
		
		if (!file.exists()) {
			
			System.out.println(file.toString() + " does not exist in root directory: " + filePath + ". Creating the file now.");
		}
	}
	
	private void createTheFile(boolean toCleanLoad) { 

		/**
		 * Creates a new file based on given Command Line Argument.
		 * 
		 * @param toCleanLoad: If set to true, function will create and overwrite any file with the same name, starting clean.
		 * 					   If set to false, function will instead load the file, and not overwrite.
		 */
		
		try {	
			
			FileWriter fileWriter = new FileWriter(file, !toCleanLoad);
			fileWriter.close();
			
		} catch (IOException e) {
			
			System.out.println("Error with Writer. Please restart the application.");
			System.exit(0);
			
		}
	}
	
	private List<String> fillList(List<String> list, File file) {
		
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
			else {	
				
				int lineNum = 1;
				
				while (line != null) {
					
					list.add(line);
					line = reader.readLine();
					lineNum++;
				}
			}
			
			reader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("Error with reader.");
			
		} catch (IOException e) {
			
			System.out.println("Error with loading content from file.");
			
		} catch (NullPointerException e) {
			
			fillList(new ArrayList<String>(), file);
		}
		
		return list;
	}
	
	public void startUp() {
		
		/**
		 * Called by driver class when starting up. This is the regular startup route.
		 * 
		 */
		printWelcomeMessage();
		
		displayMenu();
	}

	private void printWelcomeMessage() { 

		/**
		 * Prints the message that is shown upon starting the application.
		 */
		
		System.out.print("Welcome to TextBuddy. ");
		System.out.print("\"" + file.toString() + "\" ");
		System.out.println("is ready for use.");
	}

	//Second-Tier Operations
	
	private void displayMenu() { 

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
			
			determineAndExecuteCommand(command, trailingContent);
			
			writeList(fileContents, file);

			if (command.equals("exit")) {
				
				toContinue = false;
			}
	
		}
		
		sc.close();
	}

	public String determineAndExecuteCommand(String command,
			String trailingContent) {
		
		/**
		 * The function that determines the command-type.
		 * 
		 * @param command: The type of command to be executed.
		 * @param trailingContent: Any input entered after the command.
		 */
		
		String result = "";
		
		switch (command.toLowerCase()) {
		
			case ("add") :
				result = executeAddCommand(trailingContent);
				break;
			
			case ("display") :			
				result = executeDisplayCommand();
				break;
			
			case ("delete") :				
				result = executeDeleteCommand(trailingContent);
				break;
			
			case ("clear") :
				result = executeClearCommand();
				break;
			
			case ("exit") :
				break;
			
			case ("search") :
				result = executeSearchCommand(trailingContent);					
				break;	
			
			
			default :
				result = "Unknown command. Please re-enter. " + "\n" + "Available Commands: Add, Display, Delete, Clear, Exit" + ". \n";
				break;
		}
		
		if (!result.equals("")) {
			System.out.print(result);
		}
		
		return result;
	}

	private void writeList(List<String> list, File file){
		
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
	
	private String executeAddCommand(String toAdd) {
		
		/**
		 * Appends a given line of text to fileContents. Returns a String to be printed for confirmation message.
		 * 
		 * @param toAdd: String to be added into fileContents.
		 */
		
		if (isValidString(toAdd).equals(SUCCESS_MSG)) {
			
			fileContents.add(toAdd);
			return ("Added: " + toAdd) + ". \n";
			
		}
		
		return isValidString(toAdd);
	}
	
	//executeDisplayCommand()
	
	private String executeDisplayCommand() {
		
		/**
		 * Returns a string containing fileContents. This String will then be printed.
		 */
		
		String returnVal = printList(fileContents, true, null);
		return returnVal;
	}
	
	//executeDeleteCommand()
	
	private String executeDeleteCommand(String toDelete) {
		
		/**
		 * Function to be called when executing Delete Command. Returns a String as a confirmation message.
		 * Assumes user enters a Line Number
		 * Assumes user does not take into account that the starting index is 0 and not 1
		 * 
		 * @param toDelete: A string containing the line number that is to be deleted.
		 */
		
		try {
			
			if (isValidString(toDelete).equals(SUCCESS_MSG)) {
				
				int delLineNum = Integer.parseInt(toDelete);
			
				String removedLine = fileContents.remove(delLineNum - 1);
				
				return "Deleted: " + removedLine + ". \n";
			}
			else {
				
				return isValidString(toDelete);
			}
			
			
		} catch (NumberFormatException e) {
			
			return "Please only input a natural number in the range: 1 to " + fileContents.size() + ". \n";		
			
		} catch (IndexOutOfBoundsException e) {
			
			if (!fileContents.isEmpty()) {
				
				return "Unable to remove non-existent line. Please input a number from: 1 to " + fileContents.size() + ". \n";
			}
			else {
				
				return "File is already empty. \n";
			}
				
		}
	}
	
	//executeClearCommand()
	
	private String executeClearCommand() {
		
		/**
		 * Function to be called when executing the Clear command. Returns a String as a completion message.
		 */
		if (!fileContents.isEmpty()) {
			
			fileContents.clear();
			
			if (fileContents.isEmpty()) {
				return "All content cleared. \n";
			}
			else {
				return "Unable to clear content. \n";
			}
		}
		else {
			return "File is already empty. \n";
		}
	}
	
	//executeSortCommand()
	


	//executeSearchCommand()
	
	private String executeSearchCommand(String searchTerm) {
		
	      /**
	       * Code that actually handles the search function.
	       * 
	       * @param searchItem: String object containing string to be searched within fileContents.
	       */
			
			if (isValidString(searchTerm).equals(SUCCESS_MSG)) {
				
			      List<String> results = new ArrayList<String>();
		
			      for (int i=0; i<fileContents.size(); i++) {
		
			          if (fileContents.get(i).contains(searchTerm)) {
			              results.add(fileContents.get(i));
			          }
			      }
			      
			      return printList(results, false, fileContents);
			}
			else {
				
				return isValidString(searchTerm);
			}

	      
	}
	
	//Additional Functions
	
	private String isValidString(String toCheck) {
		
		/**
		 * Check if given String is valid.
		 * 
		 * @param toCheck: The String object to be checked.
		 */
		
		if (toCheck.trim().isEmpty()) {
			
			return "Please make sure your input contains at least one letter or number" + ". \n";
		}
		
		return SUCCESS_MSG;
		
	}
		
	private String printList(List<String> list, boolean defaultBehavior, List<String> secondList) {
		
		/**
		 * Given a list, returns a String containing the list contents, behavior changes according to second argument
		 * 
		 * @param list: The List object to be printed.
		 * @param defaultBehavior: Determines function behavior. If this is true, it will return each entry along with their accompanying indexes as per normal. 
		 * 						   If this is false, it will return each entry along with their accompanying indexes in relation to their position in the second list.
		 * @param secondList: See defaultBehavior.
		 */
		
		String returnVal = "";
		
		if (defaultBehavior) {
			
			/*
			 * Prints accompanying index as per normal
			 */
			
			if (!list.isEmpty()) {
				
				returnVal += "All related content: " + "\n";
				
				for (int index = 0; index < list.size(); index++) {
					
					returnVal += (index+1) + ". " + list.get(index) + "\n";
				}
			}
			else {
				
				returnVal = "No content to display. \n";
			}
		}
		else {
			
			/*
			 * Prints accompanying index in relation to secondList
			 */
			if (secondList != null) {
				
				returnVal += "Results: " + "\n";
				
				for (int index = 0; index < list.size(); index++) {
					
					returnVal += (secondList.indexOf(list.get(index))+1) + ". " + list.get(index) + "\n";
				}
			
			}
			else {
				
				returnVal = "Null list given. \n";
			}
			
		}
		
		return returnVal;
			
	}
	
}


