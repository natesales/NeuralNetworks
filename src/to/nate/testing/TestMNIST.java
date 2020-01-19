package to.nate.testing;

import to.nate.Colors;
import to.nate.Example;
import to.nate.NeuralNetwork;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestMNIST {
    public TestMNIST() {

        final int hidden = 100;
        final double learningRate = 0.05;

        NeuralNetwork net = new NeuralNetwork(28*28, hidden, 10);

        List<Example> trainingExamples = readData("data/train-labels.idx1-ubyte", "data/train-images.idx3-ubyte");
        List<Example> testingExamples = readData("t10k-labels.idx1-ubyte", "t10k-images.idx3-ubyte");


        System.out.println("Learning MNIST with " + hidden + " hidden and " + learningRate + " as a learning rate.");

        long start = System.currentTimeMillis();
        net.learnFromExamples((ArrayList<Example>) trainingExamples, learningRate, 0.95, 500, 0);
        long end = System.currentTimeMillis();
        System.out.println(Colors.SUCCESS + " Learning finished in " + (end - start)*0.001 + " s");

        System.out.println("Learning complete..." + Colors.SUCCESS);

        System.out.println("Testing accuracy: " + net.calculateAccuracy((ArrayList<Example>) testingExamples));


    }

    static List<Example> readData(String labelFileName, String imageFileName) {
        DataInputStream labelStream = openFile(labelFileName, 2049);
        DataInputStream imageStream = openFile(imageFileName, 2051);

        List<Example> examples = new ArrayList<>();

        try {
            int numLabels = labelStream.readInt();
            int numImages = imageStream.readInt();
            assert (numImages == numLabels) : "lengths of label file and image file do not match";

            int rows = imageStream.readInt();
            int cols = imageStream.readInt();
            assert (rows == cols) : "images in file are not square";
            assert (rows == 28) : "images in file are wrong size";

            for (int i = 0; i < numImages; i++) {
                int categoryLabel = Byte.toUnsignedInt(labelStream.readByte());
                List<Double> inputs = new ArrayList<Double>();
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        int pixel = 255 - Byte.toUnsignedInt(imageStream.readByte());
                        inputs.add(r * rows + c, pixel / 255.0);
                    }
                }
                examples.add(new Example(categoryLabel, inputs));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return examples;
    }

    static DataInputStream openFile(String fileName, int expectedMagicNumber) {
        DataInputStream stream = null;
        try {
            stream = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            int magic = stream.readInt();
            if (magic != expectedMagicNumber) {
                throw new RuntimeException("file " + fileName + " contains invalid magic number");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("file " + fileName + " was not found");
        } catch (IOException e) {
            throw new RuntimeException("file " + fileName + " had exception: " + e);
        }
        return stream;
    }
}
