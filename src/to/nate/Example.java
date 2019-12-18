package to.nate;

import java.util.ArrayList;
import java.util.List;

public class Example {

    int category;
    ArrayList<Double> attributes;

    Example(int category, List<Double> data) {
        this.category = category;
        attributes = new ArrayList<Double>(data);
    }
}