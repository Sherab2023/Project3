package project3;


import java.io.File;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.Date;


/**
 * This is the class with the main function
 *
 * It is responsible for opening and reading files, obtaining user input,
 * performing data validation and handling errors
 *
 * It implements a quit command to terminate the program
 *
 * @author Sherab
 */

public class LoginStats {



    public static void main(String[] args) {
        if(args.length!=1) {
            System.err.println("Usage Error: The program expects a filename as an argument.");
            System.exit(1);
        }

        File file = new File(args[0]);
        RecordList recordList = new RecordList();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(" ");

                if (parts.length != 3) {
                    System.err.println("Error: Invalid record format - " + line);
                    continue; // Skip this record
                }

                int terminal = Integer.parseInt(parts[0]);

                //to check if its a positive value (login and logout)
                //boolean login value
                boolean login;
                if(terminal>0) {
                     login= true;
                }
                else{
                    login= false;
                }


                long timeMillis = Long.parseLong(parts[1]);
                String username = parts[2];


                // Use the absolute value of terminal to handle negative values
                int terminalAbs = Math.abs(terminal);
                Record record = new Record(terminalAbs, login, username, new Date(timeMillis));


                //error causing if block



                if (record.isLogin()) {
                    Session session = new Session(record, null);
                    recordList.addSession(session);
                } else {
                    recordList.updateSession(record);
                }

        }
            sc.close();
    } catch (FileNotFoundException e) {
        System.err.println("Error: The file " + args[0] + " cannot be opened.");
        System.exit(1);
    }
    catch (NumberFormatException e) {
        System.err.println("Error: Invalid number format - "+ e.getMessage());

    } catch (NoSuchElementException e) {
        System.err.println("Error: Input is missing or incorrect.");

    }


        System.out.println("Welcome to Login Stats!");
        System.out.println("\nAvailable commands:\nfirst USERNAME retrieves first login session for the USER ");
        System.out.println("last USERNAME - retrieces last login session for the USER ");
        System.out.println("quit - terminates the program");


        try (Scanner inputsc = new Scanner(System.in)) {
			boolean quit = false;
			while (!quit) {
			    System.out.println("\nEnter a command: ");
			    String command = inputsc.next();
			    String username;
			    switch (command) {
			        case "first":
			            username = inputsc.next();
			            try {
			                Session firstSession = recordList.getFirstSession(username);
			                System.out.println(firstSession);
			            } catch (NoSuchElementException e) {
			                System.out.println(e.getMessage());
			            }
			            break;
			        case "last":
			            username = inputsc.next();
			            try {
			                Session lastSession = recordList.getLastSession(username);
			                System.out.println(lastSession);
			            } catch (NoSuchElementException e) {
			                System.out.println(e.getMessage());
			            }
			            break;
			        case "all":
			            username = inputsc.next();
			            try {
			                SortedLinkedList<Session> userSessions = recordList.getAllSessions(username);
			                for (Session session : userSessions) {
			                    System.out.println(session);
			                }
			            } catch (NoSuchElementException e) {
			                System.out.println(e.getMessage());
			            }
			            break;
			        case "total":
			            username = inputsc.next();
			            try {
			                long totalTime = recordList.getTotalTime(username);
			                System.out.println(username + ", total duration:  " + formatDuration(totalTime));
			            } catch (NoSuchElementException e) {
			                System.out.println(e.getMessage());
			            }
			            break;
			        case "quit":
			            System.out.println("Goodbye!");
			            System.exit(0);
			            break;
			        default:
			            System.out.println("Error: this is not a valid command. Try again.");
			            break;
			    }
			}
		}

    }

    /**
     * Formats a duration in milliseconds into a human-readable string representation.
     *
     * @param milliseconds The duration in milliseconds to format.
     * @return A human-readable string representing the duration in days, hours, minutes, and seconds.
     */


    private static String formatDuration(long milliseconds) {
        if(milliseconds == -1) {
            return "active session";

        }

        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours/24;

        seconds%=60;
        minutes%=60;
        hours%=24;

        return String.format("%d days, %02d hours, %02d minutes, %02d seconds", days, hours, minutes, seconds);

    }

}