package to.nate;

import java.util.ArrayList;

public class NeuralNetwork {

    double learningRate;

    private int numInput;  // Sensors
    private int numHidden; // Neurons
    private int numOutput; // Neurons

    private ArrayList<Neuron> input = new ArrayList<Neuron>();
    private ArrayList<Neuron> hidden = new ArrayList<Neuron>();
    private ArrayList<Neuron> output = new ArrayList<Neuron>();

    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_BLUE = "\u001B[34m";

    static final String INFO = ANSI_BLUE + "[ℹ] " + ANSI_RESET;
    static final String SUCCESS = ANSI_GREEN + "✓" + ANSI_RESET;
    static final String FAIL = ANSI_RED + "X" + ANSI_RESET;

    NeuralNetwork(int numInputs, int numHiddens, int numOutputs) {
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
    private int classifyOneExample(Example example) {

        ArrayList<Double> hiddenOutputs = new ArrayList<Double>();
        ArrayList<Double> outputOutputs = new ArrayList<Double>(); // The outputs from the output layer. yeah...

        for (Neuron hiddeni : hidden) {
            hiddenOutputs.add(hiddeni.calculateResult(example.attributes));
        }

        for (Neuron outputi : output) {
            outputOutputs.add(outputi.calculateResult(hiddenOutputs));
        }

        double biggestOutputSoFar = Double.NEGATIVE_INFINITY;
        int biggestCategorySoFar = 0;
        for (double output : outputOutputs) {
            if (output > biggestOutputSoFar) {
                biggestOutputSoFar = output;
                biggestCategorySoFar = example.category;
            }
        }

        return biggestCategorySoFar; // In reality this isn't the biggest category SO FAR, it's the biggest category of all.
    }

    /**
     * Takes a list of pre-categorized testing examples, and returns the fraction (or percent) that are correctly classified by this network
     *
     * @param examples Pre-categorized list of testing examples.
     * @return percent of accuracy
     */
    double calculateAccuracy(ArrayList<Example> examples) {
        return 0.5; // TODO
    }

    /**
     * Takes one pre-categorized example, classifies it, and then learns by modifying the network's weights
     *
     * @param example One pre-categorized example.
     * @return boolean Did it get it right?
     */
    boolean learnOneExample(Example example) {
        int prediction = classifyOneExample(example);

        // Calculate error signals

        for (Neuron o : output) {
            int correctOutput;

            if (example.category == o.index) {
                correctOutput = 1;
            } else {
                correctOutput = 0;
            }

            o.errorSignal = (correctOutput - o.recentOutput) * o.recentOutput * (1 - o.recentOutput);
        }

        for (Neuron h : hidden) {
            double runningErrorSignal;
            for (Neuron o : output) {
                runningErrorSignal += o.errorSignal * outputWeight h -> o;
            }

            runningErrorSignal *= hiddenResult h * (1 - hiddenResult h);
            h.errorSignal = runningErrorSignal;
        }

        // Update the weights

        for (Neuron o : output) {
            o.weights.get(/*TODO*/) = o.weights.get(/*TODO*/) + o.errorSignal * hiddenResult * learningRate;
        }

        for (Neuron h : hidden) {
            h.weights.get(/*TODO*/) = h.weights.get(/*TODO*/) + h.errorSignal * inputi * learningRate;
        }

        return true; // TODO
    }

    /**
     * Learn from a given list of examples
     *
     * @param trainingExamples      The list of examples to train on.
     * @param providedLearningRate  Value that is multiplied each time. (How fast it learns)
     * @param desiredSuccessRate    The percentage that we will achieve before quitting.
     * @param maxEpochsUntilReboot  How many epochs will go by before we reset everything.
     * @param epochsBetweenMessages How many epochs will go by before we print out a status message.
     */
    void learnFromExamples(ArrayList<Example> trainingExamples, double providedLearningRate, int desiredSuccessRate, int maxEpochsUntilReboot, int epochsBetweenMessages) {
        ArrayList<Float> products = new ArrayList<Float>();
        double currentSuccessRate = 0;
        int epochs = 0;
        int epochsUntilNextMessage = epochsBetweenMessages;
        int epochsUntilReboot = maxEpochsUntilReboot;
        learningRate = providedLearningRate;

        do {
            for (Example examplei : trainingExamples) {   //  repeat for each examplei: (run the perceptron on examplei)
                learnOneExample(examplei);
            }

            if (epochsUntilNextMessage == 0) {
                System.out.println(INFO + "Running. Current Success Rate: " + currentSuccessRate);
                epochsUntilNextMessage = epochsBetweenMessages;
            } else {
                epochsUntilNextMessage--;
            }

            if (epochsUntilReboot == 0) { // Then reset everything.
                currentSuccessRate = 0;
                epochs = 0;
                epochsUntilNextMessage = epochsBetweenMessages;
                epochsUntilReboot = maxEpochsUntilReboot;
            } else {
                epochsUntilReboot--;
            }

            epochs++;
        } while (currentSuccessRate < desiredSuccessRate); // TODO: Update currentSuccessRate
    }

    /**
     * Display the test accuracy
     *
     * @param examples The examples.
     */
    void displayTestAccuracy(ArrayList<Example> examples) {
        System.out.println(INFO + " running..."); //TODO
    }

    /**
     * Takes a list of pre-categorized training examples (and maybe a list of pre-categorized validation examples), and a desired accuracy and/or time limit.
     * It repeatedly learns from the training examples until a termination condition is reached.
     *
     * @param examples Pre-categorized list of testing examples.
     */
    void learnManyExamples(Example[] examples) {
        for (Example e : examples) {
            learnOneExample(e); // TODO
        }
    }

    /**
     * Initialize weights to small random values (say, ± 0.05)
     *
     * @param neurons ArrayList of neurons
     */
    private static void initRandWeights(ArrayList<Neuron> neurons) {
        for (Neuron neuron : neurons) {
            neuron.initRandWeights();
        }
    }

    /**
     * Build the network, using numInputs, numHidden, and numOutputs.
     */
    private void buildNetwork() {
        for (int i = 0; i < numInput; i++) {
            input.add(new Neuron(0, i)); // Fill up with empty neurons
        }

        for (int i = 0; i < numHidden; i++) {
            hidden.add(new Neuron(numInput, i)); // Fill up with empty neurons
        }

        for (int i = 0; i < numOutput; i++) {
            output.add(new Neuron(numHidden, i)); // Fill up with empty neurons
        }

        initRandWeights(hidden);
        initRandWeights(output);
    }
}
