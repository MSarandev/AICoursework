// Created by Maxim Sarandev - 1406519
// Last Edit - 9/11/17
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;

public class exec {
    // create the map to store the pos int & synapse dta
    private final Map<String, synapse> db = new HashMap<>();

    // storage string for training output
    String train_output = "";
    String train_input = "";


    // menu printing
    private void print_menu(){
        // ask on how to proceed
        System.out.println("--------------------------");
        System.out.println("0. Display network (might take a long time)");
        System.out.println("1. Train");
        System.out.println("2. Test");
        System.out.println("3. Hamming difference");
        System.out.println("4. Exit");
    }

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

    // train the network
    private void train_network(String i1, String o1){
        String inp = i1; // define the input string
        String out = o1; // define the output string

        // TRAINING SCENARIO

        // FIRST - trim all spaces
        String op_i; // def
        String op_o; // def
        op_i = trim_custom(inp); // call the trim method
        op_o = trim_custom(out); // call the trim method

        // Store the output to the variable
        train_output = op_o;

        // Store the input to the variable
        train_input = op_i;

        for (int x = 0; x < inp.length(); x++) {
            // splice input
            op_i = inp.substring(x, x + 1);

            for (int y = 0; y < out.length(); y++) {
                // splice output
                op_o = out.substring(y, y + 1);

                // Generations
                if (op_i.equals("1") && op_o.equals("1")) {
                    // both nodes 1, set weight & fire to 1
                    // find the neuron and change states

                    if (db.get(String.valueOf(y) + String.valueOf(x)).getWeight() != 1 &
                            db.get(String.valueOf(y) + String.valueOf(x)).getFire_state() != 1) {
                        // change weight
                        db.get(String.valueOf(y) + String.valueOf(x)).setWeight(1);
                        // change fire-state
                        db.get(String.valueOf(y) + String.valueOf(x)).setFire_state(1);
                    }
                }else{
                    // change weight
                    db.get(String.valueOf(y) + String.valueOf(x)).setWeight(0);
                    // change fire-state
                    db.get(String.valueOf(y) + String.valueOf(x)).setFire_state(0);
                }
            }
        }
        // network trained
        System.out.println("--------");
        System.out.println("Network trained!");

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
            ex_main.print_menu(); // print the menu

            String inp;
            String out;

            int input = reader.nextInt(); // read the output

            if (input == 1) {
                // Train

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

                // call the train network method
                ex_main.train_network(inp, out);
            } else if (input == 2) {
                // Test

                // the user must parse an output
                // the algorithm then produces the input
                // based on the network

                System.out.println(n);

                String diff_indeces = ""; // define the string

                System.out.println("--------------------------");
                System.out.println("Enter the input: ");

                inp = reader.next(); // store the input

                System.out.println("--------------------------");
                System.out.println("Enter the output: ");

                out = reader.next(); // store the input

                // Check if the input matches the length
                if(inp.length() == n/2){
                    // matches

                    // time the execution
                    Instant startT = Instant.now(); // define the starter

                    // update UI
                    System.out.println("-------");
                    System.out.print("Working");

                    String gen_out = ""; // define the storage string
                    String current_out = ""; // overridable string

                    // push zeros to fill the output
                    for (int ch1 = 0; ch1 < n/2; ch1++){
                        gen_out+="0";
                    }

                    // create a char array
                    char[] toChar1 = gen_out.toCharArray();

                    // FIRST - trim all spaces
                    String op_i; // def
                    String op_o; // def
                    op_i = ex_main.trim_custom(inp); // call the trim method
                    op_o = ex_main.trim_custom(out); // call the trim method

                    for (int y = 0; y < n/2; y++) {
                        // for each column
                        for (int x = 0; x < n/2; x++) {
                            // for each row
                            op_i = inp.substring(y, y + 1); // splice the string

                            // get the current synapse
                            synapse s1 = ex_main.db.get(String.valueOf(x) + String.valueOf(y));

                            // check the synapse pointers
                            if (op_i.equals("1")) {
                                if(s1.getWeight() == 1){
                                    toChar1[x] = '1';
                                }else{
                                    toChar1[x] = '0';
                                }
                            }
                        }

                        // update the whole string
                        gen_out = String.valueOf(toChar1);

                        // update UI
                        System.out.print(".");
                    }

                    Instant endT = Instant.now(); // end the timer

                    System.out.println("\n--------------");
                    // update the UI
                    // display timing message
                    System.out.println("Time taken: " + (Duration.between(startT, endT)));
                    System.out.println("Generated output          --> " + gen_out);
                    System.out.println("Original output           --> " + op_o);
                }else{
                    // output must match
                    System.out.println("Input must match nodes. Halting.");
                }
            }else if (input == 0){
                ex_main.print_out(n); // call the print method

            }else if (input == 3){
                // Testing with Hamming differences
                System.out.println("------------");
                System.out.println("Start with empty network");
                System.out.println("Train at least 2 times !");

                // define the storage vars
                String inputs[]; // def
                String outputs[]; // def
                String inp_x; // def
                String out_x; // def


                // ask for the initial input/outputs
                System.out.println("------------");
                System.out.println("Enter the input: ");

                inp_x = reader.next(); // capture input

                System.out.println("------------");
                System.out.println("Enter the output: ");

                out_x = reader.next(); // capture output

                // call the train network method
                ex_main.train_network(inp_x, out_x);

            }else if(input == 4){
                n = 0;
                System.out.println("Exiting...");
            }

        }
    }
}