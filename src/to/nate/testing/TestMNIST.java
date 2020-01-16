package to.nate.testing;

import to.nate.Example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestMNIST {
    public static void main(String[] args) {
        List<Example> trainingExamples = readData("train-labels-idx1-ubyte", "train-images-idx3-ubyte");
        List<Example> testingExamples = readData("t10k-labels-idx1-ubyte", "t10k-images-idx3-ubyte");
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
