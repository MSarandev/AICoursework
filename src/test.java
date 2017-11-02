// Created by Maxim Sarandev - 1406519
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class test {

    public static void main(String args[]) {
        // create the map to store the pos int & synapse dta
        Map<String, synapse> db = new HashMap<String, synapse>();

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a number (min 4): ");
        int n = reader.nextInt(); // Scans the next token of the input as an int.

        if(n >= 4) {

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

        }else{
            System.out.println("n less than 2, halting");
        }

        //Synaptic network generated - ask for input
        System.out.println("--------------------------");
        System.out.println("Enter the input (No Commas): ");

        String inp = reader.next(); // capture the string

        // check input
        if(inp.length() > 0){
            //proceed

            //ask for output
            System.out.println("--------------------------");
            System.out.println("Enter the output (No Commas): ");

            String out = reader.next(); // capture the string

            // MAIN BODY

            // usage cases
            if((inp.length() >= n/2) & (out.length() >= n/2)){
                // TRAINING SCENARIO

                // splice the string
                String op_i = ""; // current vars
                String op_o = ""; // current vars

                // FIRST - trim all spaces
                op_i = inp.replaceAll("\\s+",""); //trim input
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

                            if(db.get(String.valueOf(y)+String.valueOf(x)).getWeight() != 1){
                                db.get(String.valueOf(y)+String.valueOf(x)).setWeight(1);
                            }

                            if(db.get(String.valueOf(y)+String.valueOf(x)).getFire_state() != 1) {
                                db.get(String.valueOf(y) + String.valueOf(x)).setFire_state(1);
                            }
                        }else if(op_i.equals("0") & op_o.equals("0")){
                            // same as 1 1, set weight/fire

                            if(db.get(String.valueOf(y)+String.valueOf(x)).getWeight() != 1){
                                db.get(String.valueOf(y)+String.valueOf(x)).setWeight(0);
                            }

                            if(db.get(String.valueOf(y)+String.valueOf(x)).getFire_state() != 1) {
                                db.get(String.valueOf(y) + String.valueOf(x)).setFire_state(1);
                            }
                        }
                    }
                }

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
            }else{
                // OTHER SCENARIO
            }
        }else{
            System.out.println("Input null, halting.");
        }
        reader.close();
    }
}
