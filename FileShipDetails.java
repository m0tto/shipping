import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

// Contains the getters, setters, and other methods involving the shipping and order information pulled from the input.txt file
public class FileShipDetails {

/** Constructor **/
    FileShipDetails() {}

/******* Methods *******/   

/*** Deal with the File ***/

/* Checks if the file is valid and reads it into an array using fileToList() then returns it */
    public static ArrayList shipmentDetails() throws FileNotFoundException {
        String fileName = "input.txt";
        java.io.File file = new java.io.File(fileName);
        ArrayList fileList = new ArrayList();

        if(file.isFile()) {
            fileList = fileToList(file);
        }

        return fileList;
    }

/* Reads the shipping info file into an ArrayList */
    public static ArrayList fileToList(File file) throws FileNotFoundException {
        Scanner in = new Scanner(file);
        ArrayList<String> list = new ArrayList();

        while(in.hasNext()) {
            list.add(in.next());
        }

        in.close();
        return list;
    }

/*** Deal with Routes ***/

/* Selects a specific route's info */
    public static ArrayList routeInfo(ArrayList<String> list, int routeNum) {
        ArrayList certainRoute = new ArrayList<>();

        for(int i = 0; i < getRoutes(list) && i <= routeNum; i++) {
            certainRoute = route(list, i);
        }

        return certainRoute;
    }

/* Creates an ArrayList for a specific route */
    public static ArrayList route(ArrayList list, int routeNum) {
        ArrayList route = new ArrayList<>();

        route.add(getCompany(list, routeNum));
        route.add(getFirstCity(list, routeNum));
        route.add(getSecondCity(list, routeNum));
        route.add(getTransportType(list, routeNum));
        route.add(getMiles(list, routeNum));

        return route;
    }

/*** Deal with Orders ***/

/* Selects a specific order's info */
    public static ArrayList orderInfo(ArrayList<String> list, int orderNum) {
        ArrayList certainOrder = new ArrayList();

        for(int i = 0; i < (int)getOrders(list) && i <= orderNum; i++) {
            certainOrder = order(list, i);
        }

        return certainOrder;
    }

/* Creates an ArrayList for a specific order */
    public static ArrayList order(ArrayList list, int orderNum) {
        ArrayList order = new ArrayList();

        order.add(getAirportOrigin(list, orderNum));
        order.add(getAirportDest(list, orderNum));
        order.add(getWeight(list, orderNum));
        order.add(getCorF(list, orderNum));

        return order;
    }

/*** Getters ***/

/* Test cases */
    public static int getCases(ArrayList<String> list) {
        String testCase = list.get(0);
        return Integer.parseInt(testCase);
    }

/* Routes */
    public static int getRoutes(ArrayList<String> list) {
        String r = list.get(1);
        return Integer.parseInt(r);
    }

/* Orders */
    public static int getOrders(ArrayList<String> list) {
        String o = list.get(2);
        return Integer.parseInt(o);
    }

/* Company */
    public static char getCompany(ArrayList<String> list, int routeNum) {
        String co = list.get(routeNum * 5 + 3);
        return co.toLowerCase().charAt(0);
    }

/* First city */
    public static String getFirstCity(ArrayList<String> list, int routeNum) {
        String c1 = list.get(routeNum * 5 + 4);
        return c1;
    }

/* Second city */
    public static String getSecondCity(ArrayList<String> list, int routeNum) {
        String c2 = list.get(routeNum * 5 + 5);
        return c2;
    }

/* Transport type */
    public static String getTransportType(ArrayList<String> list, int routeNum) {
        String type = list.get(routeNum * 5 + 6);
        return type;
    }

/* Miles */
    public static double getMiles(ArrayList<String> list, int routeNum) {
        String miles = list.get(routeNum * 5 + 7);
        return Double.parseDouble(miles);
    }

/* Airport code of origin */
    public static String getAirportOrigin(ArrayList<String> list, int orderNum) {
        int routeCount = getRoutes(list);
        String origin = list.get((routeCount * 5 + 3) + (orderNum * 4));
        return origin;
    }

/* Airport code of destination */
    public static String getAirportDest(ArrayList<String> list, int orderNum) {
        int routeCount = getRoutes(list);        
        String dest = list.get((routeCount * 5 + 3) + (orderNum * 4) + 1);
        return dest;
    }

/* Weight */
    public static double getWeight(ArrayList<String> list, int orderNum) {
        int routeCount = getRoutes(list);
        String weight = list.get((routeCount * 5 + 3) + (orderNum * 4) + 2);
        return Double.parseDouble(weight);
    }

/* Cheapest or fastest */
    public static String getCorF(ArrayList<String> list, int orderNum) {
        int routeCount = getRoutes(list);
        String way = list.get((routeCount * 5) + (orderNum * 4) + 6);
        return way;
    }
    
}
