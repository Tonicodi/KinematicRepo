package neuralNet.mlp.transferfunctions;


import neuralNet.mlp.TransferFunction;

public class SigmoidalTransfer implements TransferFunction
{
    @Override
    public double evaluate(double value)
    {
        return 1 / (1 + Math.pow(Math.E, - value));
    }

    @Override
    public double evaluateDerivate(double value)
    {
        return (value - Math.pow(value, 2));
    }
}