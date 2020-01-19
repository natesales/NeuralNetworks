package to.nate;

import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {

    double learningRate;

    private int numInput;  // Sensors
    private int numHidden; // Neurons
    private int numOutput; // Neurons

    //    private ArrayList<Neuron> input;
    private ArrayList<Neuron> hidden;
    private ArrayList<Neuron> output;

    Random r = new Random();

    public NeuralNetwork(int numInputs, int numHiddens, int numOutputs) {
        numInput = numInputs;
        numHidden = numHiddens;
        numOutput = numOutputs;

        buildNetwork();
    }

    public void buildNetwork() {
        hidden = new ArrayList<Neuron>();
        output = new ArrayList<Neuron>();

        for (int i = 0; i < numHidden; i++) {
            hidden.add(new Neuron(numInput, i)); // Fill up with empty neurons
        }

        for (int i = 0; i < numOutput; i++) {
            output.add(new Neuron(numHidden, i)); // Fill up with empty neurons
        }

        initRandWeights(hidden);
        initRandWeights(output);
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

        for (int i = 0; i < outputOutputs.size(); i++) {
            if (outputOutputs.get(i) > biggestOutputSoFar) {
                biggestOutputSoFar = outputOutputs.get(i);
                biggestCategorySoFar = i;
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
    public double calculateAccuracy(ArrayList<Example> examples) {
        double correct = 0;
        double total = 0;


        for (Example e : examples) {
            int real = classifyOneExample(e);

            if (real == e.category) {
                correct++;
            }

            total++;
        }

        return correct / total;
    }

    /**
     * Takes one pre-categorized example, classifies it, and then learns by modifying the network's weights
     *
     * @param example One pre-categorized example.
     * @return Category that it was assigned.
     */
    int learnOneExample(Example example) {
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
            double runningErrorSignal = 0;
            for (Neuron o : output) {
                runningErrorSignal += o.errorSignal * o.weights.get(h.index);
            }

            runningErrorSignal *= h.recentOutput * (1 - h.recentOutput);
            h.errorSignal = runningErrorSignal;
        }

        // Update the weights

        //noinspection DuplicatedCode
        for (Neuron o : output) {
            for (Neuron h : hidden) {
                o.weights.set(h.index, o.weights.get(h.index) + o.errorSignal * h.recentOutput * learningRate);
            }

            // Bias recent (and only) output is 1
            o.bias += o.errorSignal * 1 * learningRate;
        }

        //noinspection DuplicatedCode
        for (Neuron h : hidden) {
            for (int i = 0; i < numInput; i++) {
                h.weights.set(i, h.weights.get(i) + h.errorSignal * example.attributes.get(i) * learningRate);
            }

            // Bias recent (and only) output is 1
            h.bias += h.errorSignal * 1 * learningRate;
        }

        return prediction;
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
    public void learnFromExamples(ArrayList<Example> trainingExamples, double providedLearningRate, double desiredSuccessRate, int maxEpochsUntilReboot, int epochsBetweenMessages) {

        double currentSuccessRate = 0;
        int epochs = 0;
        int epochsUntilNextMessage = epochsBetweenMessages;
        int epochsUntilReboot = maxEpochsUntilReboot;
        learningRate = providedLearningRate;

        do {
            double correct = 0;
            double total = 0;

            for (Example examplei : trainingExamples) {   //  repeat for each examplei: (run the perceptron on examplei)
                int predictedCategory = learnOneExample(examplei);

//                System.out.println("Real category: " + examplei.category + ", Predicted: " + predictedCategory);

                if (predictedCategory == examplei.category) {
                    correct++;
                }
                total++;

                if (total % 10000 == 0){
                    System.out.println("Current total: " + total);
                }
            }


            epochs++;
            currentSuccessRate = correct / total; // Update current success rate

            if (epochsUntilNextMessage == 0) {
                System.out.println(Colors.INFO + "Epochs " + epochs + " Current Success Rate: " + currentSuccessRate);
                epochsUntilNextMessage = epochsBetweenMessages;
            } else {
                epochsUntilNextMessage--;
            }

            if (epochsUntilReboot == 0) { // Then reset everything.
                System.out.println(Colors.FAIL + " Rebooting network at " + epochs + " epochs.");
                epochs = 0;
                epochsUntilNextMessage = epochsBetweenMessages;
                epochsUntilReboot = maxEpochsUntilReboot;

                buildNetwork();

            } else {
                epochsUntilReboot--;
            }

        } while (currentSuccessRate < desiredSuccessRate);

        System.out.println(Colors.INFO + " Training complete with " + epochs + " epochs. Accuracy " + currentSuccessRate * 100 + "%");
    }

    /**
     * Display the test accuracy
     *
     * @param examples The examples.
     */
    void displayTestAccuracy(ArrayList<Example> examples) {
        System.out.println(Colors.INFO + " running..."); // TODO
    }


    /**
     * Initialize weights to small random values (say, ± 0.05)
     *
     * @param neurons ArrayList of neurons
     */
    private void initRandWeights(ArrayList<Neuron> neurons) {
        for (Neuron neuron : neurons) {
            neuron.initRandWeights(r);
        }
    }

}
