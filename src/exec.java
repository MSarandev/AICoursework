// Created by Maxim Sarandev - 1406519
// Last Edit - 7/11/17
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;

public class exec {
    // create the map to store the pos int & synapse dta
    private final Map<String, synapse> db = new HashMap<>();


    // generate an empty network based on num of synapses
    private void generate_network(int n) {
        if (n >= 4) {
            Instant startT = Instant.now(); // define the starter

            System.out.println("Generating Synapse Network");
            System.out.println("--------------------------");
            System.out.print("Working");

            // create the rows
            for (int x = 0; x < n / 2; x++) {
                startT = Instant.now(); // start the timer


                for (int y = 0; y < n / 2; y++) {
                    synapse ff1 = new synapse(0, 0); // create a new synapse

                    // push the synapse to the db
                    db.put(String.valueOf(y) + String.valueOf(x), ff1);
                }
                System.out.print("."); // wait timer
            }
            Instant endT = Instant.now(); // end the timer

            // alert the user to the progress
            System.out.println("\nSynapse network generated, nodes (X,Y): " + String.valueOf(n));
            // display timing message
            System.out.println("Time taken: " + (Duration.between(startT, endT)));
        }
    }

    // print out the NN
    private void print_out(int n){
        // Print out the neural network
        for (int x = 0; x < n / 2; x++) {
            for (int y = 0; y < n / 2; y++) {
                System.out.print(
                        db.get(String.valueOf(y)+String.valueOf(x)).displ()
                                + " "
                );
            }
            System.out.println(); // new row
        }
    }

    // generate a random input/output string
    private String generate_me(int x){
        // define the container
        String returnX = "";

        // check if the limiter is >0
        if(x>1){
            int upper_bound = 1; //define the upper bound
            int lower_bound = 0; //define the lower bound
            Random random = new Random(); // define a new random var
            for (int idx = 1; idx <= x/2; ++idx){
                // generate a random bit and push to string
                returnX += random.nextInt(upper_bound - lower_bound + 1) + lower_bound;
            }

            // return the value
            return returnX;
        }else{
            return ">1 expected. Halting";
        }
    }

    // generic trim spaces method
    private String trim_custom(String x){
        x = x.replaceAll("\\s+", ""); //trim input

        return x; // return the string
    }

    // method to return the misakigned indeces
    // parse -> index y, input string, net size n, value to look for q_1
    private String ret_indeces(int y, String inp, int n, int q_1){
        String return_me = ""; // def the container

        // check if the input is full (i.e. equal to the nodes)
        if(inp.length() == n/2) {
            // input ok, check if it exists in the input
            if (!inp.substring(y, y + 1).equals(String.valueOf(q_1))) {
                // difference found
                // return the index
                return_me = String.valueOf(y);
            }
        }else{
            // input empty, return N/A
            return_me = "N/A";
        }

        // return the obj
        return return_me;
    }

    public static void main(String args[]) {
        // init the class
        exec ex_main = new exec();

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("0 to exit");
        System.out.println("Enter a number (min 4): ");
        int n = reader.nextInt(); // Scans the next token of the input as an int.

        ex_main.generate_network(n); // gen the network

        while (n != 0) {
            // continuous execution
            // ask on how to proceed
            System.out.println("--------------------------");
            System.out.println("0. Display network (might take a long time)");
            System.out.println("1. Train");
            System.out.println("2. Test");
            System.out.println("3. Exit");

            int input = reader.nextInt(); // read the output

            if (input == 1) {
                // Train

                String inp = ""; // define the input string
                String out = ""; // define the output string

                //Synaptic network generated - ask for input
                System.out.println("--------------------------");
                System.out.println("Enter the input (No Commas): ");
                System.out.println("T for random generation");

                inp = reader.next(); // capture the string

                //check if random or not
                if (inp.equals("T")) {
                    // random requested
                    inp = ex_main.generate_me(n); // call the generation method
                    System.out.println(inp);
                }

                //ask for output
                System.out.println("--------------------------");
                System.out.println("Enter the output (No Commas): ");
                System.out.println("T for random generation");

                out = reader.next(); // capture the string

                //check if random or not
                if (out.equals("T")) {
                    // random requested
                    out = ex_main.generate_me(n); // call the generation method
                    System.out.println(out);
                }

                // TRAINING SCENARIO

                // FIRST - trim all spaces
                String op_i; // def
                String op_o; // def
                op_i = ex_main.trim_custom(inp); // call the trim method
                op_o = ex_main.trim_custom(out); // call the trim method

                for (int x = 0; x < inp.length(); x++) {

                    // splice input
                    op_i = inp.substring(x, x + 1);

                    for (int y = 0; y < out.length(); y++) {
                        // splice output

                        op_o = out.substring(y, y + 1);

                        // Generations
                        if (op_i.equals("1") & op_o.equals("1")) {
                            // both nodes 1, set weight & fire to 1
                            // find the neuron and change states

                            if (ex_main.db.get(String.valueOf(y) + String.valueOf(x)).getWeight() != 1) {
                                ex_main.db.get(String.valueOf(y) + String.valueOf(x)).setWeight(1);
                            }

                            if (ex_main.db.get(String.valueOf(y) + String.valueOf(x)).getFire_state() != 1) {
                                ex_main.db.get(String.valueOf(y) + String.valueOf(x)).setFire_state(1);
                            }
                        } else if (op_i.equals("0") & op_o.equals("0")) {
                            // same as 1 1, set weight/fire

                            if (ex_main.db.get(String.valueOf(y) + String.valueOf(x)).getWeight() != 1) {
                                ex_main.db.get(String.valueOf(y) + String.valueOf(x)).setWeight(0);
                            }

                            if (ex_main.db.get(String.valueOf(y) + String.valueOf(x)).getFire_state() != 1) {
                                ex_main.db.get(String.valueOf(y) + String.valueOf(x)).setFire_state(1);
                            }
                        }
                    }
                }
                // network trained
                System.out.println("--------");
                System.out.println("Network trained!");

            } else if (input == 2) {
                // Test

                // the user must parse an output
                // the algorithm then produces the input
                // based on the network

                System.out.println(n);

                String inp = ""; // define the input string
                String out = ""; // define the output string

                String diff_indeces = ""; // define the string

                System.out.println("--------------------------");
                System.out.println("Enter the input (can be incomplete/null): ");

                inp = reader.next(); // store the input

                System.out.println("--------------------------");
                System.out.println("Enter the output: ");

                out = reader.next(); // store the input


                // Check if the output matches
                if(out.length() == n/2){
                    // matches

                    // time the execution
                    Instant startT = Instant.now(); // define the starter

                    // update UI
                    System.out.println("-------");
                    System.out.print("Working");

                    String gen_inp = ""; // define the storage string

                    // FIRST - trim all spaces
                    String op_i; // def
                    String op_o; // def
                    op_i = ex_main.trim_custom(inp); // call the trim method
                    op_o = ex_main.trim_custom(out); // call the trim method

                    for (int y = 0; y < n/2; y++) {
                        // for each column
                        for (int x = 0; x < n/2; x++) {
                            // for each row

                            op_o = out.substring(y, y + 1); // splice the string

                            // get the current synapse
                            synapse s1 = ex_main.db.get(String.valueOf(y) + String.valueOf(x));

                            // check the synapse pointers
                            if(s1.getFire_state() == 1){
                                // the value is either 11 or 00

                                if(s1.getWeight() == 1){
                                    // the val is 1 in input

                                    gen_inp += "1"; // append to string

                                    // check if the input has the same bit
                                    diff_indeces = ex_main.ret_indeces(y,inp,n,1);
                                }else if(s1.getWeight() == 0){
                                    // the val is 0 in input

                                    gen_inp += "0";

                                    // check if the input has the same bit
                                    diff_indeces = ex_main.ret_indeces(y,inp,n,0);
                                }
                            }else{
                                // value is not 11, or 00

                                // check the output for clues
                                if(op_o.equals("0")){
                                    // input == 1

                                    gen_inp += "1";

                                    // check if the input has the same bit
                                    diff_indeces = ex_main.ret_indeces(y,inp,n,1);
                                }else if(op_o.equals("1")){
                                    // input == 0

                                    gen_inp += "0";

                                    // check if the input has the same bit
                                    diff_indeces = ex_main.ret_indeces(y,inp,n,0);
                                }
                            }

                        }

                        // update UI
                        System.out.print(".");
                    }

                    Instant endT = Instant.now(); // end the timer

                    // trim generated string
                    gen_inp = gen_inp.substring(0,n/2);

                    System.out.println("\n--------------");
                    // update the UI
                    // display timing message
                    System.out.println("Time taken: " + (Duration.between(startT, endT)));
                    System.out.println("Generated input          --> " + gen_inp);
                    System.out.println("Original input           --> " + op_i);
                    System.out.println("Difference (len)         --> " + (gen_inp.length()-op_i.length()));
                    System.out.println("Difference at last index --> " + diff_indeces);

                }else{
                    // output must match
                    System.out.println("Output must match nodes. Halting.");
                }
            }else if (input == 0){
                ex_main.print_out(n); // call the print method

            }else if(input == 3){
                n = 0;
                System.out.println("Exiting...");
            }

        }
    }
}
