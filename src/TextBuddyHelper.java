/*
 *  TextBuddyHelper [Build Version 1.0]
 *  Tay Siang Meng Sean 
 */

//@author A0121409R

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
	
	//First-Tier Functions
	
	/**
	 * Constructor to initialize elements.
	 * 
	 * @param fileName 	  The name of the file to be loaded/created.
	 * @param toCleanLoad If set to true, then a new file will be created, and any files of the same name will be overridden.
	 * 					  If set to false, then if there are files of the same name, that will be loaded instead.
	 */
	public TextBuddyHelper(String fileName, boolean toCleanLoad) {
		
		this.file = new File(fileName);
		this.fileContents = new ArrayList<String>();
		
		checkFileExists();
		
		createTheFile(toCleanLoad);
		
		fileContents = fillList(fileContents, file);
	}
	
	/**
	 * Checks if the file exists. If file does not exist, print file creation message. 
	 */
	private void checkFileExists() {
		
		if (!file.exists()) {
			
			System.out.println(file.toString() + " does not exist in root directory: " + filePath + ". Creating the file now.");
		}
	}
	
	/**
	 * Creates a new file based on given Command Line Argument.
	 * 
	 * @param toCleanLoad If set to true, function will create and overwrite any file with the same name, starting clean.
	 * 					  If set to false, function will instead load the file, and not overwrite.
	 */
	private void createTheFile(boolean toCleanLoad) { 
	
		try {	
			
			FileWriter fileWriter = new FileWriter(file, !toCleanLoad);
			fileWriter.close();
			
		} catch (IOException e) {
			
			System.out.println("Error with Writer. Please restart the application.");
			System.exit(0);
			
		}
	}
	
	/**
	 * Fills a given list with given file contents, and returns the list. If not given a list, creates a new list on the spot.
	 * 
	 * @param list The List<String> object to be filled with file contents.
	 * @param file The File object to be used to fill up list with its contents.
	 * @return 	   The List<String> object filled with the given file contents.
	 */
	private List<String> fillList(List<String> list, File file) {
		
		try {
			
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			String line = reader.readLine();
			
			if (line == null) {
				
				System.out.println(file.toString() + " is empty.");
				
			} else {	
				
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
			
			System.out.println("Unable to locate file.");
			
		} catch (IOException e) {
			
			System.out.println("Error with loading content from file.");
			
		} catch (NullPointerException e) {
			
			fillList(new ArrayList<String>(), file);
		}
		
		return list;
	}
	
	/**
	 * Called by driver class when starting up.
	 */
	public void startUp() {
		
		printWelcomeMessage();
		
		inputHandler();
	}

	/**
	 * Prints the message that is shown upon starting the application.
	 */
	private void printWelcomeMessage() { 
		
		System.out.print("Welcome to TextBuddy. ");
		System.out.print("\"" + file.toString() + "\" ");
		System.out.println("is ready for use.");
	}

	//Second-Tier Functions
	
	/**
	 * Function to handle input.
	 */
	private void inputHandler() { 
		
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

			if (command == null || command.equals("exit")) {
				
				toContinue = false;
			}
	
		}
		
		sc.close();
	}
	
	/**
	 * The function that determines the command-type.
	 * 
	 * @param command 		   	The type of command to be executed.
	 * @param trailingContent 	Any input entered after the command.
	 * @return				   	A ready-to-be-printed String object.
	 */
	public String determineAndExecuteCommand (String command, String trailingContent) {
		
		if (command == null) {
			
			return "Null command given. \n";
		}
		
		String result = "";
		
		switch (command.toLowerCase()) {
		
			case "add" :
				result = executeAddCommand(trailingContent);
				break;
			
			case "display" :			
				result = executeDisplayCommand();
				break;
			
			case "delete" :				
				result = executeDeleteCommand(trailingContent);
				break;
			
			case "clear" :
				result = executeClearCommand();
				break;
			
			case "exit" :
				break;
			
			case "search" :
				result = executeSearchCommand(trailingContent);					
				break;	
			
			case "sort" :			
				result = executeSortCommand();
				break;
			
			default :
				result = "Unknown command. Please re-enter. " + "\n" + "Available Commands: Add, Display, Delete, Clear, Exit, Search, Sort" + ". \n";
				break;
		}
		
		if (!result.equals("")) {
			System.out.print(result);
		}
		
		writeList(fileContents, file);
		
		return result;
	}
	
	/**
	 * Writes a given list of Strings to a file
	 * 
	 * @param list The List object contains the data to be written.
	 * @param file The File object for the data to be written to.
	 */
	private void writeList(List<String> list, File file){
		
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
	
	//add
	

	/**
	 * Appends a given line of text to fileContents. Returns a String to be printed as a confirmation message for the user.
	 * 
	 * @param toAdd String to be added into fileContents.
	 * @return 	 	A String object that acts as a confirmation message.
	 */
	private String executeAddCommand(String toAdd) {

		if (isValidString(toAdd).equals(SUCCESS_MSG)) {
			
			fileContents.add(toAdd);
			return ("Added: " + toAdd) + ". \n";
			
		}
		
		return isValidString(toAdd);
	}		
	
	//display
	
	
	/**
	 * Returns a String containing fileContents.
	 * 
	 * @return A String object that contains fileContents in a printable format. Also acts as a confirmation message.
	 */
	private String executeDisplayCommand() {

		return printList(fileContents, true, null);
	}		
	
	//delete
	
	/**
	 * Function to be called when executing Delete Command. Returns a String to be printed as a confirmation message for the user.
	 * Assumes user enters a line number
	 * Assumes user does not take into account that the starting index is 0 and not 1
	 * 
	 * @param toDelete A String containing the line number that is to be deleted.
	 * @return 		   A String object that acts as a confirmation message.
	 */	
	private String executeDeleteCommand(String toDelete) {
		
		try {
			
			if (isValidString(toDelete).equals(SUCCESS_MSG)) {
				
				int delLineNum = Integer.parseInt(toDelete);
			
				String removedLine = fileContents.remove(delLineNum - 1);
				
				return "Deleted: " + removedLine + ". \n";
				
			} else {
				
				return isValidString(toDelete);
			}
			
			
		} catch (NumberFormatException e) {
			
			return "Please only input a natural number in the range: 1 to " + fileContents.size() + ". \n";		
			
		} catch (IndexOutOfBoundsException e) {
			
			if (!fileContents.isEmpty()) {
				
				return "Unable to remove non-existent line. Please input a number from: 1 to " + fileContents.size() + ". \n";
				
			} else {
				
				return "File is already empty. \n";
			}
				
		}	
	}		
	
	//clear
	
	
	/**
	 * Function to be called when executing the Clear command. Returns a String to be printed as a confirmation message for the user.
	 * 
	 * @return A String object that acts as a confirmation message.
	 */
	private String executeClearCommand() {
		
		if (!fileContents.isEmpty()) {
			
			fileContents.clear();
			
			if (fileContents.isEmpty()) {
				
				return "All content cleared. \n";
				
			} else {
				return "Unable to clear content. \n";
				
			}
			
		} else {
			
			return "File is already empty. \n";
			
		}
	}
	
	//sort
	
	
    /**
     * Function to be called when executing the Sort command. Returns a String to be printed as a confirmation message for the user.
     * 
     * @return A String object that acts as a confirmation message.
     */	
	private String executeSortCommand() {

	    Collections.sort(fileContents);
	    return ("Items sorted. \n");
	}	
	
	//search
	
	
    /**
     * Code that actually handles the search function. Returns a String to be printed as a 'results' message for the user.
     * 
     * @param searchTerm String object containing string to be searched within fileContents.
     * @return			 A String object that contains all relevant items with respect to the searchItem given. 
     */
	private String executeSearchCommand(String searchTerm) {
			
			if (isValidString(searchTerm).equals(SUCCESS_MSG)) {
				
			      List<String> results = new ArrayList<String>();
		
			      for (int i=0; i<fileContents.size(); i++) {
		
			          if (fileContents.get(i).contains(searchTerm)) {
			        	  
			              results.add(fileContents.get(i));
			          }
			      }
			      
			      return printList(results, false, fileContents);
			      
			} else {
				
				return isValidString(searchTerm);
				
			}
		
	}	
	
	//Third-Tier Functions
	
	//isValidString()
	
	
	/**
	 * Check if given String is valid.
	 * 
	 * @throws NullPointerException If String given is null.
	 * @param toCheck			 	The String object to be checked.
	 * @return 						A String that states an error message or a SUCCESS_MSG.
	 */
	private String isValidString(String toCheck) throws NullPointerException {
		
		if (toCheck == null) {
			
			throw new NullPointerException(" 'toCheck' is null.");
			
		} else if (toCheck.trim().isEmpty()) {
			
			return "Please make sure your input contains at least one letter or number" + ". \n";
		}
		
		return SUCCESS_MSG;
		
	}
	
	//printList()
	
	/**
	 * Given a list, returns a String containing the list contents, behavior changes according to second argument.
	 * 
	 * @param list				 The List object to be printed.
	 * @param useDefaultBehavior Determines function behavior. If this is true, it will return each entry along with their accompanying indexes as per normal. 
	 * 						   	 If this is false, it will return each entry along with their accompanying indexes in relation to their position in the second list.
	 * @param secondList	 	 See defaultBehavior.
	 * @return 					 A String that contains the list contents in a printable format.
	 */	
		
	private String printList(List<String> list, boolean useDefaultBehavior, List<String> secondList) {
		
		checksForPrintList(list, useDefaultBehavior, secondList);
		
		String returnVal = "";
			
		if (!list.isEmpty()) {
			
			returnVal += "All related content: " + "\n";
			
			returnVal = printListWriteString(list, useDefaultBehavior, secondList, returnVal);
			
		} else {
			
			returnVal = "No available related content to show. \n";
		}
		
		return returnVal;
			
	}
	
	/**
	 * Checks for printList().
	 * 
	 * @throws NullPointerException	: If either the first list is null, or if both defaultBehavior is false and secondList is null.
	 */
	private void checksForPrintList(List<String> list, boolean useDefaultBehavior, List<String> secondList) throws NullPointerException {
		
		if (list == null) {
			
			throw new NullPointerException(" 'list' is null.");
		}
		
		if (useDefaultBehavior == false && secondList == null) {
			
			throw new NullPointerException(" 'secondList' is null.");
		}
	}
	
	/**
	 * The printList() code that actually appends list contents to the given String.
	 * 
	 * @return A String containing only the list contents in a printable format, without a header.
	 */
	
	private String printListWriteString(List<String> list, boolean useDefaultBehavior, List<String> secondList, String returnVal) {
		
		for (int index = 0; index < list.size(); index++) {
			
			if (useDefaultBehavior) {
				
				returnVal += (index+1) + ". " + list.get(index) + "\n";
				
			} else {
				
				returnVal += (secondList.indexOf(list.get(index))+1) + ". " + list.get(index) + "\n";
			}
		}
		
		return returnVal;
	}
}


