package neuralNet.mlp.transferfunctions;


import neuralNet.mlp.TransferFunction;

public class HyperbolicTransfer implements TransferFunction
{

    @Override
    public double evaluate(double value)
    {
        return Math.tanh(value);
    }

    /*@Override
    public double evaluateDerivate(double value)
    {
        return 1 - Math.pow(value, 2);
    }
    */

    @Override
    public double evaluateDerivate(double value)
    {
        return 1 - Math.pow(value,2);
    }

}