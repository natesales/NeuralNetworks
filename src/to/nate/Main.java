package to.nate;

import to.nate.testing.TestAND;
import to.nate.testing.TestHandWritten;
import to.nate.testing.TestMNIST;
import to.nate.testing.TestXOR;

public class Main {

    public static void main(String[] args) {
        new TestAND();
        new TestXOR();
        new TestHandWritten();
        new TestMNIST();
//        for (int i = 0; i < 10; i++) {
//        }
    }

}
