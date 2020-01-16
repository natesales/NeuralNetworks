package to.nate;

import java.util.ArrayList;
import java.util.Random;

class Neuron {

    double recentOutput;
    double errorSignal;
    double bias;

    int inputLength;
    int index;

    ArrayList<Double> weights = new ArrayList<Double>(); // These are the input weights.
    ArrayList<Double> inputs;

    Neuron(int inputLength, int index) {
        this.inputLength = inputLength;
        this.index = index;
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
     */
    void initRandWeights(Random r) {
        final double min = -0.05;
        final double max = 0.05;

        for (int i = 0; i < inputLength; i++) {
            weights.add(min + (max - min) * r.nextDouble());
        }

        bias = min + (max - min) * r.nextDouble();
    }

    /**
     * Calculate the final output
     */
    double calculateResult(ArrayList<Double> providedInputs) {
        inputs = providedInputs;
        double product = 0;

        for (int i = 0; i < inputs.size(); i++) {
            product += weights.get(i) * inputs.get(i);
        }

        product += bias * 1; // * 1 because the bias value.
        recentOutput = activation(product);
        return recentOutput;
    }
}
