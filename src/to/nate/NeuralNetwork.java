package to.nate;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {

    double learningRate;

    private int numInput;  // Sensors
    private int numHidden; // Neurons
    private int numOutput; // Neurons

    private ArrayList<Neuron> input = new ArrayList<Neuron>();
    private ArrayList<Neuron> hidden = new ArrayList<Neuron>();
    private ArrayList<Neuron> output = new ArrayList<Neuron>();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static final String INFO = ANSI_BLUE + "[ℹ] " + ANSI_RESET;
    public static final String SUCCESS = ANSI_GREEN + "✓" + ANSI_RESET;
    public static final String FAIL = ANSI_RED + "X" + ANSI_RESET;

    public NeuralNetwork(int numInputs, int numHiddens, int numOutputs) {
        numInput = numInputs;
        numHidden = numHiddens;
        numOutput = numOutputs;

        System.out.print(INFO + "Building network...");
        buildNetwork();
        System.out.println(SUCCESS);
    }

    /**
     * Takes one example and returns the category that the neural net thinks it belongs to. The feed-forward part.
     *
     * @param example The example
     * @return category that example belongs to
     */
    int classifyOneExample(Example example) {
        for (Neuron inputi : input) { // for each input i:
            // compute the product of inputi × weighti
            // compute the sum of the products
            // apply the activation function to the sum
            // the answer is the ActualResult computed by the perceptron

          double errorSignal = (CorrectResult - ActualResult) * ActualResult * (1 - ActualResult);

          double weighti = weighti + errorSignal * inputi * learningRate;
        }
    }

    /**
     * Takes a list of pre-categorized testing examples, and returns the fraction (or percent) that are correctly classified by this network
     *
     * @param examples Pre-categorized list of testing examples.
     * @return percent of accuracy
     */
    double calculateAccuracy(ArrayList<Example> examples) {
        return 0.5;
    }

    /**
     * Takes one pre-categorized example, classifies it, and then learns by modifying the network's weights
     *
     * @param example One pre-categorized example.
     */
    void learnOneExample(Example example) {
        classifyOneExample(example);
    }

    /**
     * Calculate sum of Doubles in a given ArrayList.
     *
     * @param arraylist
     */
    static double sumOf(ArrayList<Double> arraylist) {
        double sum = 0;

        for (Double i : arraylist) {
            sum += i;
        }

        sum /= arraylist.size();
        return sum;
    }

    /**
     * Learn from a given list of examples
     *
     * @param trainingExamples      The list of examples to train on.
     * @param learningRate          Value that is multiplied each time. (How fast it learns)
     * @param desiredSuccessRate    The percentage that we will achieve before quitting.
     * @param maxEpochsUntilReboot  How many epochs will go by before we reset everything.
     * @param epochsBetweenMessages How many epochs will go by before we print out a status message.
     */
    void learnFromExamples(ArrayList<Example> trainingExamples, double learningRate, int desiredSuccessRate, int maxEpochsUntilReboot, int epochsBetweenMessages) {
        ArrayList<Float> products = new ArrayList<Float>();
        double currentSuccessRate = Double.POSITIVE_INFINITY;
        int epochs = 0;

        while (desiredSuccessRate < currentSuccessRate) { // repeat until training is complete:
            for (Example examplei : trainingExamples) {   //    repeat for each examplei: (run the perceptron on examplei)
                learnOneExample(examplei);
            }
        }
    }

    /**
     * Display the test accuracy
     *
     * @param examples The examples.
     */
    void displayTestAccuracy(ArrayList examples) {
        System.out.println(INFO + " running...");
    }

    /**
     * Takes a list of pre-categorized training examples (and maybe a list of pre-categorized validation examples), and a desired accuracy and/or time limit.
     * It repeatedly learns from the training examples until a termination condition is reached.
     *
     * @param example Pre-categorized list of testing examples.
     */
    void learnManyExamples(Example[] example) {
    }

    /**
     * Initialize weights to small random values (say, ± 0.05)
     *
     * @param neurons ArrayList of neurons
     */
    private static void initRandWeights(ArrayList<Neuron> neurons) {
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).initRandWeights();
        }
    }

    /**
     * Build the network, using numInputs, numHidden, and numOutputs.
     */
    private void buildNetwork() {
        for (int i = 0; i < numInput; i++) {
            input.add(new Neuron()); // Fill up hidden with empty neurons
        }

        for (int i = 0; i < numHidden; i++) {
            hidden.add(new Neuron()); // Fill up hidden with empty neurons
        }

        for (int j = 0; j < numOutput; j++) {
            output.add(new Neuron()); // Fill up hidden with empty neurons
        }

        initRandWeights(input);
        initRandWeights(hidden);
        initRandWeights(output);
    }
}
