/* Morgan Otto
 * CSC161101
 * Dec 3, 2021
 * Final Program - Let's Ship */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {

// Initializes the shipping constructor and requests the order number  
        Shipping shipping = new Shipping();
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter order number (example: 0): ");
        int orderNum = input.nextInt();

// Writes the order info into file
        java.io.File file = new java.io.File("output.txt");

        if(file.isFile()) {
            shipping.shipping(orderNum);
        }
        else {
            System.out.println("File 'output.txt' does not exist.");
            throw new IOException();
        }

        input.close();
    }

}
