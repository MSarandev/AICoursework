// Created by Maxim Sarandev - 1406519

public class synapse {

    // define params
    int fire_state = 0;
    int weight = 0;

    // define constructor
    public synapse(int fire_state, int weight) {
        this.fire_state = fire_state;
        this.weight = weight;
    }

    // define methods
    public int getFire_state() {
        return fire_state;
    }

    public void setFire_state(int fire_state) {
        this.fire_state = fire_state;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    // custom methods
    public String displ(){
        return String.valueOf(fire_state)
                + "," +
                String.valueOf(weight);
    }
}
