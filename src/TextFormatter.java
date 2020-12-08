import java.io.*;
import java.util.Scanner;


public class TextFormatter {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner keyboard = new Scanner(System.in);

        /* Prompts for width of the output file, between 30 - 100 char */
        int outputW;
        do {
            System.out.println("Enter the maximum formatted output width (30-100 characters): ");
            outputW = keyboard.nextInt();
        } while( outputW < 30 || outputW > 100);

        /* Prompts for the input file name, checks to see if it exists, if it does not the exception throws a "try again message"
        and the input file name must be entered again
         */
        int fileName;
        BufferedReader inputFileReader = null;
        do {
            System.out.println("Enter the file name of the input text file: ");
            String inputFile = keyboard.next();
            try {
                inputFileReader = new BufferedReader(new FileReader(inputFile));
                fileName = 1;
            } catch(FileNotFoundException e) {
                System.out.println("The file could not be found. Enter the file name again: " + e.toString());
                fileName = 0;
            }
        } while(fileName ==0);



        fileName = 0;
        PrintWriter outputStream = null;
        /* Prompts for the name of the output file. Checks to see if file exists and if it does not the file is created. If the file does exist it
         * prompts the user and gives the option to overwrite. User may type yes or no, case insensitive. */
        do {
            System.out.println("Enter the output file name: ");
            String outputFileName = keyboard.next();
            File f = new File(outputFileName);
            if(f.exists()){
                System.out.println("This file exists already. Would you like to overwrite the file? Type yes or no: ");
                String choice = keyboard.next();
                if ("Yes".equals(choice) || "yes".equals(choice)){
                    try {
                        outputStream = new PrintWriter(new BufferedWriter(
                                new FileWriter(outputFileName)));
                        fileName = 1;
                    } catch(IOException e) {
                        fileName = 0;
                        System.out.println(e.toString());
                    }
                }
            }
            else {
                try {
                    outputStream = new PrintWriter(new BufferedWriter(
                            new FileWriter(outputFileName)));
                    fileName = 1;
                } catch(IOException e) {
                    fileName = 0;
                    System.out.println(e.toString());
                }
            }
        } while(fileName == 0);

        outputStream.println("Here is the formatted text: ");
        outputStream.println();
        for(int i = 0; i < outputW; i++)
            outputStream.print("*");
        outputStream.println();

        /* Scans the input file, reading words that are separated by spaces. Prints the words while
         * accounting for the previously determined max width. */
        Scanner keyboard2 = new Scanner(inputFileReader);
        String temp, line = "";
        StringReader s = null;
        while(keyboard2.hasNext()){
            temp = keyboard2.next();
            if(line.length() + temp.length() > outputW){
                outputStream.println(line);
                line = temp + " ";
            }
            else{
                line = line + temp + " ";
            }
        }
        outputStream.println(line); /* Prints the words that are left */
        outputStream.close();
    }
}