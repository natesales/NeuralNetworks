package to.nate;

import java.util.ArrayList;
import java.util.Random;

class Neuron {

    double recentOutput;
    double errorSignal; // Error Signal
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
    void initRandWeights() {
        final double min = -0.05;
        final double max = 0.05;

        Random r = new Random();
        for (int i = 0; i < inputLength; i++) {
            weights.add(min + (max - min) * r.nextDouble());
        }
    }

    /**
     * Calculates the error signal and assigns it to a field
     */
//    void calculateErrorSignal() {
//        errorSignal = (CorrectResult - ActualResult) * ActualResult * (1 - ActualResult);
//    }

    /**
     * Calculate the final output
     */
    double calculateResult(ArrayList<Double> providedInputs) { // TODO: Bias needs to be already set.
        inputs = providedInputs;
        double product = 0;

        for (int i = 0; i < inputs.size(); i++) {
            product += weights.get(i) * inputs.get(i);
        }

        product += bias * 1; // * 1 because the bias weight.
        recentOutput = product;
        return product;
    }
}
