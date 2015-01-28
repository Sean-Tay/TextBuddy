/*
 *  TextBuddy [Build Version 0.0]
 *  Tay Siang Meng Sean
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public TextBuddyHelper(String[] args) { 
		
		//Constructor to initialize IO elements

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

		//Starts here

		preErrorChecks(args); //Error checks to be done before creating the file
		
	    startUp(args); //Creates the file
	    
		printWelcomeMessage();
	    
	    menu(); //Opens up UI for further user input
		 
	}
	
	private static void preErrorChecks(String args[]) { 

		//Add validity checks here
		
		if (args.length <= 0) 
		{
	    	System.out.println("No command-line arguments given. Closing program.");
	        System.exit(0);
		}
	}
	
	private static void startUp(String args[]) { 

		//To be called upon startup of the application
		
		file = new File(args[0]); //Initializes File Handle
		
    	checkIfFileExists();
    	
		createTheFile();
	}

	private static void checkIfFileExists() { 

		//If file does not exist, print message
		
		if (!file.exists())
    	{
    		//File does not exist.
			//printNotFileFoundMsg(file);
    	}
	}
	
	private static void printNotFileFoundMsg(File file) { 

		//Optional: Prints file not found message
		
		System.out.print("No file found. Creating a file called \"" + file.toString() + "\" at: ");
		System.out.println(filePath + ".");
		System.out.println();
	}

	private static void createTheFile() { 

		//Create a new file.
		
		try {
			
			fileWriter = new FileWriter(file, file.exists());
			fileWriter.close();
			
		} catch (IOException e) {
			
			System.out.println("Error with Writer. Please restart the application.");
			System.exit(0);
			
		}
	}

	private static void printWelcomeMessage() { 

		//Prints welcome message
		
		System.out.print("Welcome to TextBuddy. ");
		System.out.print("\"" + file.toString() + "\" ");
		System.out.println("is ready for use.");
	}
	
	//Second Tier Operations
	
	private static void menu() { 

		//Function to call for UI
		
		boolean exit = false;
		Scanner sc = new Scanner(System.in);
		
		while (!exit)
		{	
			System.out.print("command: ");
			String command = sc.next();
			
			switch (command.toLowerCase())
			{
				case("add"):
					if (sc.hasNext())
					{
						String toAdd = sc.nextLine();
						
						if (!addText(toAdd))
						{
							System.out.println(GEN_ERROR);
						}
					}
					break;
				
				case("display"):
					if (!displayText())
					{
						System.out.println(GEN_ERROR);
					}
					break;
				
				case("delete"):
					if (sc.hasNextInt())
					{
						int i = sc.nextInt();
						
						if (!deleteText(i))
						{
							System.out.println(GEN_ERROR);
						}
					}
					break;
				
				case("clear"):
					if (!clearText())
					{
						System.out.println(GEN_ERROR);
					}
					break;
				
				case("exit"):
					exit = true;
					break;
				
				default:
					System.out.println("Unknown command. Please re-enter.");
					System.out.println("Available Commands: Add, Display, Delete, Clear, Exit");
					break;
			}
				
		}
		
		sc.close();
		
	}
	
	//addText Operations
	
	private static boolean addText(String toAdd) { 

		//Function to call when adding one line of text to document
		
		toAdd = toAdd.substring(1, toAdd.length());
		
		//Start of actual function
		try {
			addTextInit();
			
			addingTheText(toAdd);
			
			addTextComplete();
			
			return true;
			
		} catch (IOException e) {
			System.out.println(GEN_ERROR);
		}
		return false;
	}

	private static void addTextInit() throws IOException { 

		//Initializes addText variables
		resetIO();
		
		fileWriter = new FileWriter(file, true);
		writer = new BufferedWriter(fileWriter);
	}
		
	private static void addingTheText(String toAdd) throws IOException { 

		//Actually adds the line to the file
		writer.write(toAdd);
		writer.newLine();
		writer.flush(); //Needed to reset stream
		
		System.out.println("added to " + file.toString() + ": " + "\"" + toAdd + "\"");
	}
	
	private static void addTextComplete() throws IOException { 

		//Operations done after addText() completes
		
		writer.close();
		fileWriter.close();
	}
	
	//displayText Operations
	
	private static boolean displayText() { 

		//Function to call for displaying contents of loaded text file
		
		displayTextInit();
		
		int i = 1;
		
		//Start of actual functions
		try {
			
			displayingTheText(i);

			displayTextComplete();
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(GEN_ERROR);
			return false;
		}		
	}

	private static void displayTextInit() {
		
		//Initializes IO variables for displayText()
		resetIO();
		
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println("Error with Reader.");
		}
		reader = new BufferedReader(fileReader);
	}
	
	private static void displayingTheText(int i) throws IOException {
		String line = reader.readLine();
		
		if (line == null)
		{
			System.out.println(file.toString() + " is empty");
		}
		
		while (line!= null)
		{
			System.out.println(i + ". " + line);
			line = reader.readLine();
			i++;
		}
	}

	private static void displayTextComplete() throws IOException {
		reader.close();
		fileReader.close();
	}
	
	//deleteText Operations
	
	private static boolean deleteText(int lineNum) { 

		//Function to call when a line is to be 'deleted' from the text file 
		
		String nameOfFile = file.toString();
		String deletedLine = null;
		String line = null;
		
		boolean toContinue = false;
		boolean checkForLine1 = true;
		
		deleteTextInit();
		
		//Start of actual functions
		
		try{
			
			int i = 1;

			line = reader.readLine();
			
			while (line != null)
			{
				if (i == lineNum)
				{
					deletedLine = line;
					line = reader.readLine();
					i++;
				}

				if (line != null)
				{
					tWriter.write(line);
					tWriter.newLine();
					line = reader.readLine();
				}
				
				i++;
				
			}
			
			deleteTextComplete(nameOfFile, deletedLine);
			
			return true;
			
		}
		catch (IOException e)
		{
			System.out.println(GEN_ERROR);
		}
		catch(NullPointerException e2)
		{
			System.out.println("Unable to continue reading. Line number given may have exceeded current number of lines in text file.");
			e2.printStackTrace();
		}
		return false;
	}

	private static void deleteTextInit() { 
		
		//Initialized deleteText() IO variables
		resetIO();
		
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
	
	private static void deleteTextComplete(String nameOfFile, String deletedLine) throws IOException {
		
		//Given line number was valid. Proceed to replace original file with edited tempfile
		
		resetIO();
		
		file.delete();
		
		boolean success = tempFile.renameTo(file); //Can only be done AFTER the writer closes
		tempFile.delete();
		
		file = new File(nameOfFile);
		
		System.out.println("deleted from " + file.toString() + " : " + "\"" + deletedLine + "\"");
	}
	
	//clearText Operations
	
	private static boolean clearText() { 

		//Function called when deleting all content from loaded text file
		
		if (!clearTextOverwrite()) return false;
		
		System.out.println("all content deleted from " + file.toString());
		return true;
		
	}

	private static boolean clearTextOverwrite() {
		
		//Overwrites main file with temp file
		resetIO();
		
		
		try {
			fileWriter = new FileWriter (file);
			fileWriter.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	private static void resetIO() {
		
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


