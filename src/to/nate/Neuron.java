package to.nate;

import java.util.ArrayList;
import java.util.Random;

public class Neuron {

    double recentOutput;
    double error; // Error Signal

    ArrayList<Double> weights = new ArrayList<Double>(); // These are the input weights.
    ArrayList<Double> inputs;

    Neuron() {
    }

    /**
     * Activation Function, using equation 1 / (1 + e⁻ˣ)
     * <p>
     * 1 / (1 + e<sup>-x</sup>)
     *
     * @param x Input to function
     * @return output of function
     */
    private static double activation(double x) {
        return 1 / (1 + Math.pow(Math.E, -x));
    }

    /**
     * Initialize weights to small random values (say, ± 0.05)
     *
     * @param neurons ArrayList of neurons
     */
    void initRandWeights() {
        final double min = -0.05;
        final double max = 0.05;

        Random r = new Random();
        for (double weight : weights) {
            weight = min + (max - min) * r.nextDouble();
        }
    }
}
