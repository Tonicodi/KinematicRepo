package neuralNet.mlp.transferfunctions;


import neuralNet.mlp.TransferFunction;

import java.io.Serializable;

public class SigmoidalTransfer implements TransferFunction,Serializable
{

    /**
     * Evalua la funcion sigmoide
     * @param value Valor de entrada
     * @return
     */
    @Override
    public double evaluate(double value)
    {
        return 1 / (1 + Math.pow(Math.E, - value));
    }

    /**
     * Evalua la derivada de la funcion sigmoide
     * @param value Valor de entrada
     * @return
     */
    @Override
    public double evaluateDerivate(double value)
    {
        return (value - Math.pow(value, 2));
    }
}