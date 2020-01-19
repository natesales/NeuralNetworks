package to.nate.testing;

import to.nate.Colors;
import to.nate.Example;
import to.nate.NeuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;

public class TestXOR {
    public TestXOR() {

        final int hidden = 10;
        final double learningRate = 0.3;

        ArrayList<Example> examples = new ArrayList<Example>();

        examples.add(new Example(0, Arrays.asList(0d, 0d)));
        examples.add(new Example(1, Arrays.asList(0d, 1d)));
        examples.add(new Example(1, Arrays.asList(1d, 0d)));
        examples.add(new Example(0, Arrays.asList(1d, 1d)));

        NeuralNetwork net = new NeuralNetwork(2, hidden, 2);

        System.out.print(Colors.INFO + "Building network...");
        net.buildNetwork();
        System.out.println(Colors.SUCCESS);

        System.out.println("Learning XOR with " + hidden + " hidden and " + learningRate + " as a learning rate.");
        long start = System.currentTimeMillis();
        net.learnFromExamples(examples, learningRate, 1, 1000000, 10000);
        long end = System.currentTimeMillis();
        System.out.println(Colors.SUCCESS + " Learning finished in " + (end - start) * 0.001 + " s");
        System.out.println("Learning complete..." + Colors.SUCCESS);
    }
}

