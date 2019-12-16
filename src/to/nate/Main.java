package to.nate;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        ArrayList<Example> examples = new ArrayList<Example>();

        examples.add(new Example(0, Arrays.asList(0d, 0d)));
        examples.add(new Example(0, Arrays.asList(0d, 1d)));
        examples.add(new Example(0, Arrays.asList(1d, 0d)));
        examples.add(new Example(1, Arrays.asList(1d, 1d)));

        NeuralNetwork net = new NeuralNetwork(2, 2, 2);

        net.learnFromExamples(examples, 1.0, 1, 1000, 100);
    }
}
