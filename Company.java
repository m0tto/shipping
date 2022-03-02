import java.util.ArrayList;

// Sets the air and ground info for each company
public class Company {

// Creates basic arrays for air and ground shipping
    static double[] airList = new double[3];
    static double[] groundList = new double[3];
    
/*** Constructor ***/
    Company() {}

/*** Methods ***/

/* Adds the elements to a basic array */
    public static double[] listAdd(double cost, double speed, double wtLimit) {
        double[] list = {cost, speed, 0};

        if(wtLimit == -1) {
            list[2] = 100000.0;
            
        } else {
            list[2] = wtLimit;
        }

        return list;   
    }

/* Adds elements from both basic arrays to an ArrayList */
    public static ArrayList combine(double[] air, double[] ground) {
        ArrayList combinedLists = new ArrayList();

        for(int i = 0; i < air.length; i++) {
            combinedLists.add(air[i]);
        }

        for(int k = 0; k < ground.length; k++) {
            combinedLists.add(ground[k]);
        }

        return combinedLists;
    }

/*** Setters ***/

/* Sets the air information */
    public static void setAir(double cost, double speed, double wtLimit) {
        airList = listAdd(cost, speed, wtLimit);
    }

/* Sets the ground information */
    public static void setGround(double cost, double speed, double wtLimit) {
        groundList = listAdd(cost, speed, wtLimit);
    }

/*** Getters ***/

/* Returns the air info */
    public static double[] getAir() {
        return airList;
    }

/* Returns the ground info */
    public static double[] getGround() {
        return groundList;
    }

/* Returns a company's specific air cost, speed, or weight limit info */
    public static double selectAirInfo(char co, int spec) {
        ArrayList company = companyList(co);
        String info = company.get(spec).toString();
        return Double.parseDouble(info);
    }

/* Returns a company's specific ground cost, speed, or weight limit info */
    public static double selectGroundInfo(char co, int spec) {
        ArrayList company = companyList(co);
        String info = company.get(spec + 3).toString();
        return Double.parseDouble(info);
    }

/** Company Information **/

/* Declares company air and ground shipping information and returns the requested company's info */
    public static ArrayList companyList(char co) {
        ArrayList companies = new ArrayList<>();

// Company A
        Company a = new Company();
        a.setAir(0.0105, 0.16, 500);
        a.setGround(0.003, 1.1, 5000);

        ArrayList coA = a.combine(a.getAir(), a.getGround());
        companies.add(a);

// Company B
        Company b = new Company();
        b.setAir(0.012, 0.125, 1000);
        b.setGround(0.0035, 1.0, 2000);

        ArrayList coB = b.combine(b.getAir(), b.getGround());
        companies.add(b);

// Company C
        Company c = new Company();
        c.setAir(0.095, 0.165, 100);
        c.setGround(0.002, 1.05, -1);

        companies.add(c);
        ArrayList coC = c.combine(c.getAir(), c.getGround());

// Company D
        Company d = new Company();
        d.setAir(0.025, 0.15, -1);
        d.setGround(0.0025, 1.25, 2500);

        companies.add(d);
        ArrayList coD = d.combine(d.getAir(), d.getGround());

// Company E
        Company e = new Company();
        e.setAir(0.040, 0.13, 750);
        e.setGround(0.004, 1.15, -1);

        companies.add(e);
        ArrayList coE = e.combine(e.getAir(), e.getGround());

// Company F
        Company f = new Company();
        f.setAir(0.067, 0.145, -1);
        f.setGround(0.0015, 0.95, 3000);

        companies.add(f);
        ArrayList coF = f.combine(f.getAir(), f.getGround());

// Determines which company was requested
        if(co == 'a') {
            return coA;
        }
        else if(co == 'b') {
            return coB;
        }
        else if(co == 'c') {
            return coC;
        }
        else if(co == 'd') {
            return coD;
        }
        else if(co == 'e') {
            return coE;
        }
        else if(co == 'f') {
            return coF;
        }
        else {
            return companies;
        }
    }

}
