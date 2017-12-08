// Created by Maxim Sarandev - 1406519
// Last Edit - 9/12/17

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class auto_test {

    public void test_function(String gen_out, int sy_n, String test_inp, exec ex_main, String output_x){
        // push zeros to fill the output
        for (int ch1 = 0; ch1 < sy_n/2; ch1++){
            gen_out+="0";
        }

        // create a char array
        char[] toChar1 = gen_out.toCharArray();

        // FIRST - trim all spaces
        String op_i; // def

        for (int y = 0; y < sy_n/2; y++) {
            // for each column
            for (int x = 0; x < sy_n/2; x++) {
                // for each row
                try{
                    // try to fetch the next value

                    op_i = test_inp.substring(y, y + 1); // splice the string
                }catch(Exception e){
                    // failure, usually due to the length of the input
                    op_i = "0";
                }

                // get the current synapse
                synapse s1 = ex_main.db.get(String.valueOf(x) + String.valueOf(y));

                // check the synapse pointers
                if (op_i.equals("1")) {
                    if(s1.getWeight() == 1){
                        toChar1[x] = '1';
                    }
                }
            }

            // update the whole string
            gen_out = String.valueOf(toChar1);

            // update UI
            System.out.print(".");
        }

        System.out.println("\n------------------------");
        // update the UI
        System.out.println("> Generated output: " + gen_out);
        System.out.println("> Expected output:  " + output_x);

    }

    public static void main(String args[]) {

        // init the exec class
        exec ex_main = new exec();
        auto_test at_1 = new auto_test();

        // print the menu
        System.out.println("------------------------");
        System.out.println("AUTO TESTING UTIL");
        System.out.println("1. Start");
        System.out.println("0. EXIT");

        // get the user input
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        int n = reader.nextInt(); // Scans the next token of the input as an int.

        while(n != 0){
            System.out.println("------------------------");
            System.out.println("Auto Testing INITIATED");
            System.out.println("> Setting synapses to 16");
            System.out.println("> Generating neural network");

            // set the synapses
            int sy_n = 8;

            // Code snippet taken from exec.java

            System.out.print("> Working");

            // create the rows
            for (int x = 0; x < sy_n / 2; x++) {
                for (int y = 0; y < sy_n / 2; y++) {
                    synapse ff1 = new synapse(0, 0); // create a new synapse

                    // push the synapse to the db
                    ex_main.db.put(String.valueOf(y) + String.valueOf(x), ff1);
                }
                System.out.print("."); // wait timer
            }
            // display timing message
            System.out.println("\n> Done");

            // network generated, train with input/output pair
            System.out.println("> Setting input to:  1011");
            System.out.println("> Setting output to: 0110");
            System.out.println("> Training network");

            // call to training method
            ex_main.train_network("1011", "0110");

            // print the network with synapses
            System.out.println("> Printing network state");
            ex_main.print_out(sy_n);

            System.out.println("------------------------");


            // begin the testing
            System.out.println("> Test (1) - incomplete input");
            System.out.println("> Setting input to: 101 (original: 1011)");

            String test_inp = "101"; // set the val

            // code snippet from exec.java

            String gen_out = ""; // init

            // call the test function
            at_1.test_function(gen_out,sy_n,test_inp,ex_main,"0110");

            System.out.println("> Test (1) COMPLETE");
            System.out.println("------------------------");

            // Test case 2
            System.out.println("> Test (2) - noisy input");
            System.out.println("> Setting input to: 1001 (original: 1011)");

            test_inp = "1001"; // set the val

            // call the test function
            at_1.test_function(gen_out,sy_n,test_inp,ex_main,"0110");

            System.out.println("> Test (2) COMPLETE");
            System.out.println("------------------------");

            System.out.println("------------------------");
            System.out.println("PHASE 2");
            System.out.println("------------------------");

            // Enter phase 2 (36 synapses)
            System.out.println("> Setting synapses to 36");
            System.out.println("> Generating neural network");

            // set the synapses
            sy_n = 12;

            // Code snippet taken from exec.java

            System.out.print("> Working");

            // create the rows
            for (int x = 0; x < sy_n / 2; x++) {
                for (int y = 0; y < sy_n / 2; y++) {
                    synapse ff1 = new synapse(0, 0); // create a new synapse

                    // push the synapse to the db
                    ex_main.db.put(String.valueOf(y) + String.valueOf(x), ff1);
                }
                System.out.print("."); // wait timer
            }
            // display timing message
            System.out.println("\n> Done");

            System.out.println("------------------------");
            System.out.println("> Training network (1)");
            System.out.println("------------------------");

            // network generated, train with input/output pair
            System.out.println("> Setting input to:  101101");
            System.out.println("> Setting output to: 110010");
            System.out.println("> Training network");

            // call to training method
            ex_main.train_network("101101", "110010");

            System.out.println("------------------------");
            System.out.println("> Training network (2)");
            System.out.println("------------------------");

            // network generated, train with input/output pair
            System.out.println("> Setting input to:  010101");
            System.out.println("> Setting output to: 100000");
            System.out.println("> Training network");

            // call to training method
            ex_main.train_network("010101", "100000");

            // print the network with synapses
            System.out.println("> Printing network state");
            ex_main.print_out(sy_n);

            // Begin testing
            System.out.println("------------------------");
            System.out.println("> Testing Phase");
            System.out.println("------------------------");

            System.out.println("> Test (1) - distorted version");
            System.out.println("> Setting input to: 01010 (original: 010101)");

            test_inp = "01010"; // set the val

            // call the test function
            at_1.test_function(gen_out,sy_n,test_inp,ex_main,"110101");

            System.out.println("> Test (1) COMPLETE");
            System.out.println("------------------------");
            System.out.println("> Load parameter HIT");
            System.out.println("> Network saturated");

            System.out.println("<><><><><><><><><><><><>");
            System.out.println("  END OF AUTO TESTING");
            System.out.println("To change all values, use exec.java (M.S.)");
            // exit the loop
            n = 0;
        }
    }
}
