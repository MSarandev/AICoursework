// Created by Maxim Sarandev - 1406519
// Last Edit - 7/11/17
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;
import java.util.Timer;

public class exec {
    // create the map to store the pos int & synapse dta
    private final Map<String, synapse> db = new HashMap<>();


    // generate an empty network based on num of synapses
    private void generate_network(int n) {
        if (n >= 4) {
            long startTime = System.nanoTime(); // init

            System.out.println("Generating Synapse Network");
            System.out.println("--------------------------");
            System.out.print("Working");

            // create the rows
            for (int x = 0; x < n / 2; x++) {
                startTime = System.currentTimeMillis();// init the timer
                // create the columns
                //System.out.print("| ");

                for (int y = 0; y < n / 2; y++) {
                    synapse ff1 = new synapse(0, 0); // create a new synapse

                    // push the synapse to the db
                    db.put(String.valueOf(y) + String.valueOf(x), ff1);

                    //System.out.print(ff1.displ() + " "); // print the synapse dta
                }
                System.out.print("."); // wait timer
            }
            long endTime = System.nanoTime(); // end the timer

            // alert the user to the progress
            System.out.println("\nSynapse network generated, nodes (X,Y): " + String.valueOf(n));
            // display timing message
            System.out.println("That took " + ((endTime - startTime)/1000000) + " milliseconds");
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
                String op_i;
                op_i = inp.replaceAll("\\s+", ""); //trim input
                String op_o;
                op_o = out.replaceAll("\\s+", ""); //trim output

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

                // print the trained network
                //ex_main.print_out(n);

            } else if (input == 2) {
                // Test

                //ask for input
                System.out.println("--------------------------");
                System.out.println("Enter the input (Enter X to test): ");

                String inp = reader.next(); // capture the string

                //ask for output
                System.out.println("--------------------------");
                System.out.println("Enter the output (Enter X to test): ");

                String out = reader.next(); // capture the string


                // check what we're looking for
                if (inp.equals("X")) {
                    // looking for input, from output

                    return;
                } else if (out.equals("X")) {
                    // looking for output

                    return;
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
