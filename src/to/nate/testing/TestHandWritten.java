package to.nate.testing;

import to.nate.Colors;
import to.nate.Example;
import to.nate.NeuralNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TestHandWritten {
    /*
    Third, you should try your neural network on Alpaydin and Kaynak's Handwritten Digits dataset.

    Download the Training data (3823 examples) and the Testing data (1797 examples)
        These are plain text files
            Each line contains 65 comma separated integers
            The first 64 are the inputs (in the range 0...16) and the last one is the category (0...9)
        The data comes from images of handwritten digits from 43 people (30 in training set, 13 in testing set)
            Each handwritten sample was scanned into a 32x32 black and white bitmap
            Then, the bitmap was broken into non-overlapping 4x4 blocks, and they counted the number of black pixels in each block
            The end result is each sample is a 8x8 grid of integers in the range 0 ... 16
    Experiment to see how well your neural network can learn to classify the digits.  Try different numbers of hidden nodes and learning rates.
        How high an accuracy can you get?
        Remember to distinguish between training accuracy and testing accuracy.
        Can you get a training accuracy over 90%?  95%?  99%?
        Can you get a testing accuracy over 90%?  92%  96%?  97%?
     */

    private void loadExamples(ArrayList<Example> examples, String filename){
        try {
            FileReader file = new FileReader(filename);
            BufferedReader reader = new BufferedReader(file);
            String line = reader.readLine();

            while (line != null) {
                String[] tokens = line.split(",");

                ArrayList<Double> attribs = new ArrayList<Double>();
                final int category = Integer.parseInt(tokens[tokens.length - 1]);

                for (int i = 0; i < tokens.length - 1; i++) {
                    String token = tokens[i];

                    attribs.add(Double.parseDouble(token));
                }

//                System.out.println("Parsed " + attribs.size() + " attribs and 1 category.");

                examples.add(new Example(category, attribs)); // Create and append the example

                line = reader.readLine(); // Move on to the next line
            }
        } catch (IOException ioexception) {
            System.out.println("Ack! We had a problem: " + ioexception.getMessage());
        }
    }

    public TestHandWritten() {

        final int hidden = 20;
        final double learningRate = 0.01;

        ArrayList<Example> trainingExamples = new ArrayList<>();
        ArrayList<Example> testingExamples = new ArrayList<>();

        loadExamples(trainingExamples,"data/digits-train.txt");
        loadExamples(testingExamples,"data/digits-test.txt");

        NeuralNetwork net = new NeuralNetwork(64, hidden, 10);

        System.out.println("Learning Hand Written with " + hidden + " hidden and " + learningRate + " as a learning rate.");
        long start = System.currentTimeMillis();
        net.learnFromExamples(trainingExamples, learningRate, 0.999, 1000, 10);
        long end = System.currentTimeMillis();
        System.out.println(Colors.SUCCESS + " Learning finished in " + (end - start) * 0.001 + " s");
        System.out.println("Testing accuracy: " + net.calculateAccuracy(testingExamples));
    }
}
