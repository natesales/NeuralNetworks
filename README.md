Fifth, write up a concise summary report describing what your neural network was able to do and what experiments you ran.  Assume the reader is familiar with machine learning and neural network terminology at the level we've covered in class.  Make sure you include enough information that someone else in the class could reproduce your results.


On the small handwritten digits dataset, 

[ℹ]  Training complete with 436 epochs. Accuracy 99.71226785247188%
✓ Learning finished in 87.52 s

All tests are performed on a single i7 6700k at 4.00ghz with minimal background processes.

I was able to consistently achieve 100% accuracy in 

| Dataset            | Epochs | Time (seconds) | Testing Accuracy (%) | Training Accuracy (%) | Hidden Layers | Learning Rate |
| :----------------- | :----- | :------------- | :------------------- | :-------------------- | :------------ | :------------ |
| AND                | 19     | 0.04           | 100                  | 100                   | 10            | 10            |
| XOR                | 2500   | 0.05           | 100                  | 100                   | 10            | 0.3           |
| Handwritten Digits | 184    | 45             | 96.4                 | 99.9                  | 20            | 0.01          |
| MNIST              |        |                |                      |                       |               |               |


For XOR, I was able to consistently achieve 100% accuracy in under 3,000 epochs with a learning rate of 0.3 and 10 hidden layers.

The first dataset I used was Alpaydin and Kaynak's Handwritten Digits. This consists of 3823 training and 1797 examples.
I was able to consistently achieve greater than 99.9% accuracy in 30 seconds with a learning rate of 0.01 and 50 hidden layers.