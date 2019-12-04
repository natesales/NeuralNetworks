package to.nate;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Example> examples = new ArrayList<Example>();

        examples.add(new Example(0, new double[]{ 0, 0}));
        examples.add(new Example(0, new double[]{ 0, 1}));
        examples.add(new Example(0, new double[]{ 1, 0}));
        examples.add(new Example(1, new double[]{ 1, 1}));

        NeuralNetwork net = new NeuralNetwork(2, 2, 2);

        net.learnFromExamples(examples, 1.0, 10, 10, 10);

    }
}
