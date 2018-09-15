package neuralNet.mlp.transferfunctions;


import neuralNet.mlp.TransferFunction;

import java.io.Serializable;

public class HyperbolicTransfer implements TransferFunction,Serializable
{

    /**
     * Evalua la funcion tangente hiperbolica
     * @param value Valor de entrada
     * @return
     */
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

    /**
     * Evalua la derivada de la tangente hiperbolica
     * @param value Valor de entrada
     * @return
     */
    @Override
    public double evaluateDerivate(double value)
    {
        return 1 - Math.pow(value,2);
    }

}