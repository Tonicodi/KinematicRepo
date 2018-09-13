package neuralNet.mlp;


import java.io.Serializable;

public class Layer implements Serializable
{
    public Neuron Neurons[];
    public int 		Length;

    /**
     * Capas de la neurona
     *
     * @param l tamaño de la capa
     * @param prev Tamaño de la capa anterior
     */
    public Layer(int l, int prev)
    {
        Length = l;
        Neurons = new Neuron[l];

        for(int j = 0; j < Length; j++)
            Neurons[j] = new Neuron(prev);
    }
}