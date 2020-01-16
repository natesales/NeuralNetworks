package to.nate.testing;

import to.nate.Colors;
import to.nate.Example;
import to.nate.NeuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;

public class TestXOR {
    public TestXOR() {
        ArrayList<Example> examples = new ArrayList<Example>();

        examples.add(new Example(0, Arrays.asList(0d, 0d)));
        examples.add(new Example(1, Arrays.asList(0d, 1d)));
        examples.add(new Example(1, Arrays.asList(1d, 0d)));
        examples.add(new Example(0, Arrays.asList(1d, 1d)));

        NeuralNetwork net = new NeuralNetwork(2, 2, 2);

        System.out.print(Colors.INFO + "Building network...");
        net.buildNetwork();
        System.out.println(Colors.SUCCESS);

        System.out.println("Learning...");
        net.learnFromExamples(examples, 0.05, 1, 1000000, 10000);
        System.out.println("Learning complete..." + Colors.SUCCESS);
    }
}

