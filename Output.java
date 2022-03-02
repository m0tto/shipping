import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.FileWriter;
import java.util.Scanner;

// Contains the output information for the shipment
public class Output {

/** Constructor **/
    Output() {}

/*** Methods ***/

/* Outputs each leg of the package's journey, the total hours, and the total cost */
    public static void fileOutput(ArrayList cities, ArrayList companies, ArrayList transport, char way, double totalCost, double totalHours) throws FileNotFoundException, IOException {

// Loop for each leg and insert the leg's info into the file
        for(int i = 0; i < cities.size() / 2; i++) {
            int k = 2 * i + 1;
            char co = companies.get(i).toString().toUpperCase().charAt(0);
            String means = travelMeans(transport.get(i).toString().toLowerCase().charAt(0), way);

// Adds the origin city's airport code, the destination city's airport code, the transport type (A or G), and the company to the file
            insertToFile("output.txt", cities.get(i) + " to " + cities.get(k) + " by " + means + " with Company " + co);
        }

// Adds the total hours and cost
        insertToFile("output.txt", "Total hours: " + Math.round(totalHours * 100.0) / 100.0);
        insertToFile("output.txt", "Total Cost: $" + Math.round(totalCost * 100.0) / 100.0);

// Prints the file        
        Scanner input = new Scanner(new java.io.File("output.txt"));
        while (input.hasNextLine()) {
            System.out.println(input.nextLine());
        }
    }

/* Inserts the information into a file */
    public static void insertToFile(String file, String info) throws IOException, FileNotFoundException {
        java.io.File toFile = new java.io.File(file);

        Writer fileEdit;
        fileEdit = new BufferedWriter(new FileWriter(toFile, true));
 
        fileEdit.append(info + "\n");
        fileEdit.close(); 
    }

/* Converts the travel means abbreviation to the proper string */
    public static String travelMeans(char means, char way) {

// Cheapest way
        if(way == 'c') {
            if(means == 'a') {
                return "air";
            }
            else if(means == 'g') {
                return "ground";
            }
            else if(means == 'b') {
                return "ground";
            }
            else {
                return "elephant";
            }
        }
        
// Fastest way
        else if(way == 'f') {
            if(means == 'a') {
                return "air";
            }
            else if(means == 'g') {
                return "ground";
            }
            else if(means == 'b') {
                return "air";
            }
            else {
                return "elephant";
            }
        }
        else {
            return "elephant";
        }
    }

}
