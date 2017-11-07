// Created by Maxim Sarandev - 1406519
// Last Edit - 7/11/17
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class exec {
    // create the map to store the pos int & synapse dta
    private final Map<String, synapse> db = new HashMap<>();


    // generate an empty network based on num of synapses
    private void generate_network(int n) {
        if (n >= 4) {
            System.out.println("Generating Synapse Network");
            System.out.println("--------------------------");

            // create the rows
            for (int x = 0; x < n / 2; x++) {
                // create the columns
                System.out.print("| ");

                for (int y = 0; y < n / 2; y++) {
                    synapse ff1 = new synapse(0, 0); // create a new synapse

                    // push the synapse to the db
                    db.put(String.valueOf(y) + String.valueOf(x), ff1);

                    System.out.print(ff1.displ() + " "); // print the synapse dta
                }
                System.out.println(); // new row
            }
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



    public static void main(String args[]) {
        // init the class
        exec ex_main = new exec();

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("0 to exit");
        System.out.println("Enter a number (min 4): ");
        int n = reader.nextInt(); // Scans the next token of the input as an int.

        ex_main.generate_network(n); // gen the network

        while (n != 0){
            // continuous execution
            // ask on how to proceed
            System.out.println("--------------------------");
            System.out.println("1. Train");
            System.out.println("2. Test");
            System.out.println("3. Exit");

            int input = reader.nextInt(); // read the output

            if(input == 1){
                // Train

                //Synaptic network generated - ask for input
                System.out.println("--------------------------");
                System.out.println("Enter the input (No Commas): ");

                String inp = reader.next(); // capture the string

                //ask for output
                System.out.println("--------------------------");
                System.out.println("Enter the output (No Commas): ");

                String out = reader.next(); // capture the string

                // TRAINING SCENARIO

                // FIRST - trim all spaces
                String op_i;
                op_i = inp.replaceAll("\\s+",""); //trim input
                String op_o;
                op_o = out.replaceAll("\\s+",""); //trim output

                for(int x=0;x<inp.length();x++){

                    // splice input
                    op_i = inp.substring(x,x+1);

                    for(int y=0;y<out.length();y++){
                        // splice output

                        op_o = out.substring(y,y+1);

                        // Generations
                        if(op_i.equals("1") & op_o.equals("1")){
                            // both nodes 1, set weight & fire to 1
                            // find the neuron and change states

                            if(ex_main.db.get(String.valueOf(y)+String.valueOf(x)).getWeight() != 1){
                                ex_main.db.get(String.valueOf(y)+String.valueOf(x)).setWeight(1);
                            }

                            if(ex_main.db.get(String.valueOf(y)+String.valueOf(x)).getFire_state() != 1) {
                                ex_main.db.get(String.valueOf(y) + String.valueOf(x)).setFire_state(1);
                            }
                        }else if(op_i.equals("0") & op_o.equals("0")){
                            // same as 1 1, set weight/fire

                            if(ex_main.db.get(String.valueOf(y)+String.valueOf(x)).getWeight() != 1){
                                ex_main.db.get(String.valueOf(y)+String.valueOf(x)).setWeight(0);
                            }

                            if(ex_main.db.get(String.valueOf(y)+String.valueOf(x)).getFire_state() != 1) {
                                ex_main.db.get(String.valueOf(y) + String.valueOf(x)).setFire_state(1);
                            }
                        }
                    }
                }

                // print the trained network
                ex_main.print_out(n);

            }else if(input == 2){
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
                if(inp.equals("X")) {
                    // looking for input, from output

                    return;
                }else if(out.equals("X")){
                    // looking for output

                    return;
                }
            }else if(input == 3){
                n = 0;
                System.out.println("Exiting...");
            }

        }
    }
}
