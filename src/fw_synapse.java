// Created by Maxim Sarandev - 1406519
// Last Edit - 5/12/17

public class fw_synapse {

    // define params
    int fire_state = 0;
    float weight = 0;

    // define constructor
    public fw_synapse(int fire_state, float weight) {
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    // custom methods
    public String displ(){
        return String.valueOf(fire_state)
                + "," +
                String.valueOf(weight);
    }
}