/**
 * Autores: Lucas e Nadim Mussá Daud
 */

import java.util.Scanner;

public class Main {

    //MESSAGES
    private static final  String  EXIT_MESSAGE = "Bye!";

    public static void main(String[] args) {
        commands();
    }

    /**
     * return command given a string
     */
    private static Commands getCommand(String command) {
        for (Commands c : Commands.values()) {
            if (c.getName().equals(command)) {
                return c;
            }
        }
        return Commands.UNKNOWN;
    }

    /**
     * Main command, reads command input and calls the appropriate method. Does not handle parameters.
     */
    private static void commands() {
        Scanner in = new Scanner(System.in);

        Commands command;
        do {
            command = getCommand(in.next().toLowerCase());
            switch(command){
                case HELP -> help();
                case UNKNOWN -> System.out.printf("%s %s\n",command.getName(), command.getDescription());



            }
        } while (command != Commands.EXIT);

        exit();
        in.close();
    }


    /**
     * Prints the help message
     */
    private static void help() {
        for (Commands c : Commands.values()) {
            if (c != Commands.UNKNOWN) {
                System.out.println(c.getName() + " - " + c.getDescription());
            }
        }
    }


    /**
     * Prints the exit message
     */
    private static void exit() {
        System.out.println(EXIT_MESSAGE);
    }

}














