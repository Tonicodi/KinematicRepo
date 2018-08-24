package neuralNet.mlp;


public interface TransferFunction
{
    /**
     * Función de transferencia
     * @param value Valor de entrada
     * @return Valor de la funcion
     */
    public double evaluate(double value);


    /**
     * Derivada de la función
     * @param value Valor de entrada
     * @return Valor de la función derivada
     */
    public double evaluateDerivate(double value);
}