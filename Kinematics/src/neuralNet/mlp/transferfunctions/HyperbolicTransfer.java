package neuralNet.mlp.transferfunctions;


import mlp.mlp.TransferFunction;

public class HyperbolicTransfer implements TransferFunction
{

    @Override
    public double evalute(double value)
    {
        return Math.tanh(value);
    }

    @Override
    public double evaluteDerivate(double value)
    {
        return 1 - Math.pow(value, 2);
    }

}