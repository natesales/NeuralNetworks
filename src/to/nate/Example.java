package to.nate;

import java.util.ArrayList;
import java.util.List;

public class Example {

    int category;
    ArrayList<Double> attributes;

    Example(int providedCategory, List<Double> data) {
        category = providedCategory;
        attributes = new ArrayList<Double>(data);
    }
}