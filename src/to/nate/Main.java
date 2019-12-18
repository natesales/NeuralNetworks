package to.nate;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static void testAND() {

        ArrayList<Example> examples = new ArrayList<Example>();

        examples.add(new Example(0, Arrays.asList(0d, 0d)));
        examples.add(new Example(0, Arrays.asList(0d, 1d)));
        examples.add(new Example(0, Arrays.asList(1d, 0d)));
        examples.add(new Example(1, Arrays.asList(1d, 1d)));

        NeuralNetwork net = new NeuralNetwork(2, 2, 2);

        System.out.print(Colors.INFO + "Building network...");
        net.buildNetwork();
        System.out.println(Colors.SUCCESS);

        System.out.println("Learning...");
        net.learnFromExamples(examples, 1.0, 1, 100000, 5000);
        System.out.println("Learning complete..." + Colors.SUCCESS);

    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            testAND();
        }
    }
}
