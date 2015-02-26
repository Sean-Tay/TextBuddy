/*
 * [CE2 Code]
 * 
 * TextBuddy [Build Version 1.0] Tay Siang Meng Sean
 * 
 * This program allows the user to edit a text file via Command Line. Basic CRUD
 * operations.
 * 
 * Assumptions: 1) Will not overwrite the file the user specifies with each
 * startup (i.e. load file if file exists)
 */

// @author A0121409R

public class TextBuddy {

    public static String argChecker(String args[]) {

        if (args == null || args.length == 0) {

            return "mytextfile.txt";

        } else {

            return args[0];
        }

    }

    public static void main(String args[]) {

        TextBuddyHelper instance = new TextBuddyHelper(argChecker(args), false);
        instance.startUp();
    }
}
