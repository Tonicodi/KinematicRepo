package neuralNet.mlp;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public class MultiLayerPerceptron
{
    protected double			fLearningRate = 0.6;
    protected Layer[]			fLayers;
    protected TransferFunction fTransferFunction;


    /**
     * Crea una  nueva red neuronal mlp
     *
     * @param layers Numero de neuronas por cada capa
     * @param learningRate Costante de aprendizaje
     * @param fun Función de trasnferencia
     */
    public MultiLayerPerceptron(int[] layers, double learningRate, TransferFunction fun)
    {
        fLearningRate = learningRate;
        fTransferFunction = fun;

        fLayers = new Layer[layers.length];


        for(int i = 0; i < layers.length; i++)
        {
            if(i != 0)
            {
                fLayers[i] = new Layer(layers[i], layers[i - 1]);
            }
            else
            {
                fLayers[i] = new Layer(layers[i], 0);
            }
        }


        System.out.println("Longitud flayrs "+fLayers[0].Length);
    }



    /**
     * Ejecuta la red
     *
     * @param input Valor de entrada para el entrenamiento
     * @return Valori de salida de la red evaluada
     */
    public double[] execute(double[] input)
    {
        int i;
        int j;
        int k;
        double new_value;

        double output[] = new double[fLayers[fLayers.length - 1].Length];

        // Put input
        for(i = 0; i < fLayers[0].Length; i++)
        {
            fLayers[0].Neurons[i].Value = input[i];
        }

        // Execute - hiddens + output
        for(k = 1; k < fLayers.length; k++)
        {
            for(i = 0; i < fLayers[k].Length; i++)
            {
                new_value = 0.0;
                for(j = 0; j < fLayers[k - 1].Length; j++)
                    new_value += fLayers[k].Neurons[i].Weights[j] * fLayers[k - 1].Neurons[j].Value;

                new_value += fLayers[k].Neurons[i].Bias;

                fLayers[k].Neurons[i].Value = fTransferFunction.evaluate(new_value);
            }
        }

        // Get output
        for(i = 0; i < fLayers[fLayers.length - 1].Length; i++)
        {
            output[i] = fLayers[fLayers.length - 1].Neurons[i].Value;
        }
        //System.out.println(Arrays.toString(output));

        return output;
    }




    /**
     * Algoritmo de backpropagation para el aprendizaje supervisado
     * (Version un solo hilo)
     *
     *
     * @param input Valor de entrada (escala de 0 a 1)
     * @param output Valor de salida establecido (escala de  0 a 1)
     * @return Error delta en la salida generado de la evaluacion de la red
     */
    public double backPropagate(double[] input, double[] output)
    {
        double new_output[] = execute(input);
        double error;
        int i;
        int j;
        int k;

        /* doutput = correct output (output) */

        // Calcula el error de la salida
        for(i = 0; i < fLayers[fLayers.length - 1].Length; i++)
        {
            error = output[i] - new_output[i];
            fLayers[fLayers.length - 1].Neurons[i].Delta = error * fTransferFunction.evaluateDerivate(new_output[i]);
        }


        for(k = fLayers.length - 2; k >= 0; k--)
        {
            // Calculo del error y se aplica la funcion delta
            for(i = 0; i < fLayers[k].Length; i++)
            {
                error = 0.0;
                for(j = 0; j < fLayers[k + 1].Length; j++)
                    error += fLayers[k + 1].Neurons[j].Delta * fLayers[k + 1].Neurons[j].Weights[i];

                fLayers[k].Neurons[i].Delta = error * fTransferFunction.evaluateDerivate(fLayers[k].Neurons[i].Value);
            }

            // Asigna pesos y multiplica por el valor sucesivo
            for(i = 0; i < fLayers[k + 1].Length; i++)
            {
                for(j = 0; j < fLayers[k].Length; j++)
                    fLayers[k + 1].Neurons[i].Weights[j] += fLearningRate * fLayers[k + 1].Neurons[i].Delta *
                            fLayers[k].Neurons[j].Value;
                fLayers[k + 1].Neurons[i].Bias += fLearningRate * fLayers[k + 1].Neurons[i].Delta;
            }
        }

        // Calculo del error
        error = 0.0;

        for(i = 0; i < output.length; i++)
        {
            error += Math.abs(new_output[i] - output[i]);

            //System.out.println(output[i]+" "+new_output[i]);
        }

        error = error / output.length;
        return error;
    }


    /**
     * Guarda una red MLP en un archivo
     *
     * @param path Path el cual es la ruta donde se guardara la red MLP
     * @return true si se guarda correctamente
     */
    public boolean save(String path)
    {
        try
        {
            FileOutputStream fout = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }


    /**
     * Carga una red MLP del archivo
     * @param path Ruta de la cual se carga la red MLP
     * @return La red cargada o null si no se encontro
     */
    public static MultiLayerPerceptron load(String path)
    {
        try
        {
            MultiLayerPerceptron net;

            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream oos = new ObjectInputStream(fin);
            net = (MultiLayerPerceptron) oos.readObject();
            oos.close();

            return net;
        }
        catch (Exception e)
        {
            return null;
        }
    }



    /**
     * @return Constante de aprendizaje
     */
    public double getLearningRate()
    {
        return fLearningRate;
    }


    /**
     *
     * @param rate porcentaje de aprendizaje
     */
    public void	setLearningRate(double rate)
    {
        fLearningRate = rate;
    }


    /**
     * Implementa una nueva función de transferencia
     *
     * @param fun Función de transferencia
     */
    public void setTransferFunction(TransferFunction fun)
    {
        fTransferFunction = fun;
    }



    /**
     * @return Dimensión de la capa de entrada
     */
    public int getInputLayerSize()
    {
        return fLayers[0].Length;
    }


    /**
     * @return Dimensión de la capa de salida
     */
    public int getOutputLayerSize()
    {
        return fLayers[fLayers.length - 1].Length;
    }
}
