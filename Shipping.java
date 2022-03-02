import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Ships the package: switches the carriers to get to the desired destination, and stores them
class Shipping {

/** Constructor **/
    Shipping() {}

/*** Methods ***/

/* Processes the order requested and stores its shipping path */ 
    public static ArrayList shipping(int orderNum) throws FileNotFoundException, IOException {
// Sets up base ArrayLists and initial variables
        FileShipDetails shipment = new FileShipDetails();
        ArrayList shipmentDetails = shipment.shipmentDetails();

// Lists to store the info for each leg
        ArrayList city = new ArrayList();
        ArrayList companies = new ArrayList();
        ArrayList means = new ArrayList();
        ArrayList<Double> cost = new ArrayList();
        ArrayList<Double> time = new ArrayList();     

// Variables for each city, will change in the loop        
        String startingCity = shipment.getAirportOrigin(shipmentDetails, orderNum);
        String endCity = shipment.getAirportDest(shipmentDetails, orderNum);
        String routeCity1 = shipment.getFirstCity(shipmentDetails, 0);
        String routeCity2 = shipment.getSecondCity(shipmentDetails, 0);
        String nextCity = startingCity;

        char way = shipment.getCorF(shipmentDetails, orderNum).toLowerCase().charAt(0);

        city.add(nextCity);

// Loop through all routes
        for(int k = 0; k < shipment.getRoutes(shipmentDetails) + 1; k++) {

            String cityCheck = nextCity;
            nextCity = citySwitch(nextCity, routeCity1, routeCity2);

// Switches the route cities to the next route
            routeCity1 = shipment.getFirstCity(shipmentDetails, k + 1);
            routeCity2 = shipment.getSecondCity(shipmentDetails, k + 1);

// If the city did change, add the new info to the ArrayLists            
            if(cityCheck.equalsIgnoreCase(nextCity) == false) {
                city.add(nextCity);
                companies.add(shipment.getCompany(shipmentDetails, k)); 
                means.add(shipment.getTransportType(shipmentDetails, k)); 

                cost.add(legCost(k, orderNum, way));
                time.add(travelTime(k, orderNum, way));
            }
            else {
                continue;
            }

// End the loop if the destination is reached
            if(nextCity.equals(endCity)) {

// Outputs the package's shipment
                Output out = new Output();

// Tallies the total cost and time for the shipment       
                double costSum = 0;
                double timeSum = 0;

                for(int i = 0; i < cost.size(); i++) {
                    costSum = costSum + cost.get(i);
                    timeSum = timeSum + time.get(i);
                }

              // Makes output better to read
                if(city.size() / 2 == 1) {
                    city.add(endCity);
                }

                out.fileOutput(city, companies, means, way, costSum, timeSum);

                break;
            }
        }
   // End loop //

        return city;
    }

/* Determines if the starting-point city is either of the route's cities */
    public static String citySwitch(String startingCity, String routeCity1, String routeCity2) {
        String newStartingCity = startingCity;

// Determines which city, if either, the starting city will switch to
        if(startingCity.equals(routeCity1)) {
            newStartingCity = routeCity2;
        }
        else if(startingCity.equals(routeCity2)) {
            newStartingCity = routeCity1;
        }
        else {
            newStartingCity = startingCity;
        }

        return newStartingCity;
    }

/* Determines the cost for the specified leg */
    public static double legCost(int routeNum, int orderNum, char corf) throws FileNotFoundException {
// Initial ArrayLists and variables
        Company company = new Company();
        FileShipDetails shipment = new FileShipDetails();
        ArrayList shipmentDetails = shipment.shipmentDetails();

// Initial variables
        String means = shipment.getTransportType(shipmentDetails, routeNum).toString();
        char co = shipment.getCompany(shipmentDetails, routeNum);
        int valid = isValidWeight(orderNum, co);
        double miles = shipment.getMiles(shipmentDetails, routeNum);
        double cost = 0;
        double weight = shipment.getWeight(shipmentDetails, orderNum);
        double airPrice;
        double groundPrice;

// Fastest //
        if(corf == 'f') {
// Selects faster option 
            if(means.charAt(0) == 'B' || means.charAt(0) == 'b') {
                if(valid == 1) {
                    airPrice = company.selectAirInfo(co, 0);
                    cost = airPrice * miles * weight;
                }
                else if(valid == -1) {
                    groundPrice = company.selectGroundInfo(co, 0);
                    cost = groundPrice * miles * weight;
                }
                else if(valid == 0) {
                    airPrice = company.selectAirInfo(co, 0);
                    cost = airPrice * miles * weight;
                }
            }

// Ground cost
            else if(means.charAt(0) == 'G' || means.charAt(0) == 'g' && valid == -1) {
                groundPrice = company.selectGroundInfo(co, 0);
                cost = groundPrice * miles * weight;
            }

// Air cost        
            else if(means.charAt(0) == 'A' || means.charAt(0) == 'a' && valid == 1) {
                airPrice = company.selectAirInfo(co, 0);
                cost = airPrice * miles * weight;
            }
        }

// Cheapest //
        else if(corf == 'c') {
// Select cheaper option if both is means
            if(means.charAt(0) == 'B' || means.charAt(0) == 'b') {
                if(valid == 1) {
                    airPrice = company.selectAirInfo(co, 0);
                    cost = airPrice * miles * weight;
                }
                else if(valid == -1) {
                    groundPrice = company.selectGroundInfo(co, 0);
                    cost = groundPrice * miles * weight;
                }
                else if(valid == 0) {
                    groundPrice = company.selectGroundInfo(co, 0);
                    cost = groundPrice * miles * weight;
                }
            }

// Ground cost
            else if(means.charAt(0) == 'G' || means.charAt(0) == 'g' && valid == -1) {
                groundPrice = company.selectGroundInfo(co, 0);
                cost = groundPrice * miles * weight;
            }

// Air cost        
            else if(means.charAt(0) == 'A' || means.charAt(0) == 'a' && valid == 1) {
                airPrice = company.selectAirInfo(co, 0);
                cost = airPrice * miles * weight;
            }
        }

        return Math.round(cost * 100.0) / 100.0;
    }

/* Determines the time for the specified leg */
    public static double travelTime(int routeNum, int orderNum, char corf) throws FileNotFoundException {
// Initial ArrayList and constructor
        Company company = new Company();
        FileShipDetails shipment = new FileShipDetails();
        ArrayList shipmentDetails = shipment.shipmentDetails();

// Initial variables
        String means = shipment.getTransportType(shipmentDetails, routeNum);
        char co = shipment.getCompany(shipmentDetails, routeNum);
        int valid = isValidWeight(orderNum, co);      
        double miles = shipment.getMiles(shipmentDetails, routeNum);
        double time = 0;
        double airSpeed;
        double groundSpeed;

// Fastest //
        if(corf == 'f') {
// Select the faster option if means is both
            if(means.charAt(0) == 'B' || means.charAt(0) == 'b') {
                if(valid == 1) {
                    airSpeed = company.selectAirInfo(co, 1);
                    time = airSpeed * miles;
                }
                else if(valid == -1) {
                    groundSpeed = company.selectGroundInfo(co, 1);
                    time = groundSpeed * miles;
                }
                else if(valid == 0) {
                    airSpeed = company.selectAirInfo(co, 1);
                    time = airSpeed * miles;
                }
            } 

// Air time 
            else if(means.charAt(0) == 'A' || means.charAt(0) == 'a' && valid == 1) {
                airSpeed = company.selectAirInfo(co, 1);
                time = airSpeed * miles;
            }

// Ground time
            else if(means.charAt(0) == 'G' || means.charAt(0) == 'g' && valid == -1) {
                groundSpeed = company.selectGroundInfo(co, 1);
                time = groundSpeed * miles;
            }
        }

// Cheapest //
        else if(corf == 'c'){
// Selects cheaper option
            if(means.charAt(0) == 'B' || means.charAt(0) == 'b') {
                if(valid == 1) {
                    airSpeed = company.selectAirInfo(co, 1);
                    time = airSpeed * miles;
                }
                else if(valid == -1) {
                    groundSpeed = company.selectGroundInfo(co, 1);
                    time = groundSpeed * miles;
                }
                else if(valid == 0) {
                    groundSpeed = company.selectGroundInfo(co, 1);
                    time = groundSpeed * miles;
                }
            } 

// Air time 
            else if(means.charAt(0) == 'A' || means.charAt(0) == 'a' && valid == 1) {
                airSpeed = company.selectAirInfo(co, 1);
                time = airSpeed * miles;
            }

// Ground time
            else if(means.charAt(0) == 'G' || means.charAt(0) == 'g' && valid == -1) {
                groundSpeed = company.selectGroundInfo(co, 1);
                time = groundSpeed * miles;
            }
        }

// Change minutes to hours and return time
        time = time / 60;
        return Math.round(time * 100.0) / 100.0;
    }

/* Determines if the package's weight is valid for the company (air = 1, ground = -1, both = 0) */
    public static int isValidWeight(int orderNum, char co) throws FileNotFoundException {
        FileShipDetails shipment = new FileShipDetails();
        ArrayList shipDetails = shipment.shipmentDetails();

        Company company = new Company();

// Initializes the basic variables
        double wt = shipment.getWeight(shipDetails, orderNum);
        boolean isValidAir = false;
        boolean isValidGround = false;
        int isValid = 0;

// Determines if the order's weight is valid for air shipment
        if(wt > company.selectAirInfo(co, 2)) {
            isValidAir = false;
        }
        else if(wt == company.selectAirInfo(co, 2) || wt < company.selectAirInfo(co, 2)) {
            isValidAir = true;
        }

// Determines if the order's weight is valid for ground shipment
        if( wt > company.selectGroundInfo(co, 2)) {
            isValidGround = false;
        }
        else if(wt == company.selectGroundInfo(co, 2) || wt < company.selectGroundInfo(co, 2)) {
            isValidGround = true;
        }

// If air or ground is valid, return the determining int
        if(isValidAir == true || isValidGround == false) {
            isValid = 1;
        }
        else if(isValidAir == false || isValidGround == true) {
            isValid = -1;
        }

        return isValid;
    }

}
