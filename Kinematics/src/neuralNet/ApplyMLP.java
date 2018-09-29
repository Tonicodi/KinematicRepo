package neuralNet;

import neuralNet.mlp.MultiLayerPerceptron;
import neuralNet.mlp.TransferFunction;
import util.Numerics;
import utils.PRECISION;


import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class ApplyMLP implements Serializable {

    MultiLayerPerceptron mlp;
    int[] layers;
    double learningRate;
    double errorTrain;
    int epochs;
    TransferFunction fun;
    ArrayList<double[]> NN_target;
    ArrayList<double[]> NN_input;
    PRECISION precision;
    public  boolean round=true;

    double Ymin= 0 ,Ymax= 110,Zmin= -32,Zmax= 138;


    /**
     * Constructor de ApplyMLP recibe parametros para crear la red neuronal
     * @param layers arreglo de enteros donde se establecen las capas de la red neuronal
     * @param learningRate factor de aprendizaje
     * @param fun funcion de transferencia
     * @param inputs entradas que se usaran para entrenamiento
     * @param outputs target u objetivo de entrenamiento
     */
    public ApplyMLP(int[] layers, double learningRate, TransferFunction fun,ArrayList<double[]> inputs,ArrayList<double[]> outputs){

        mlp = new MultiLayerPerceptron(layers,learningRate,fun);
        Numerics.LAYER     = layers[1];
        this.layers        = layers;
        this.learningRate  = learningRate;
        this.fun           = fun;
        this.NN_input      = inputs;
        this.NN_target     = outputs;
    }

    /**
     * Retorna la precision que se ha establecido para generar los datos de entrenamiento
     * @return
     */
    public PRECISION getPrecision() {
        return precision;
    }

    /**
     * Establece la precision para generar los datos de entrenamiento
     * @param precision
     */
    public void setPrecision(PRECISION precision) {
        this.precision = precision;
    }

    /**
     * Carga la red MLP del archivo
     * @param path Ruta de la cual se carga la red MLP
     * @return La red cargada o null si no se encontro
     */
    public static ApplyMLP load(String path)
    {
        try
        {
            ApplyMLP net;

            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream oos = new ObjectInputStream(fin);
            net = (ApplyMLP) oos.readObject();
            oos.close();
            return net;
        }
        catch (Exception e)
        {
            System.out.println("Error al cargar red "+e.getMessage());
            return null;
        }
    }

    /**
     * Guarda la red ya entrenada con las configuraciones establecidas
     * @param path ruta donde se guardara la red neuronal
     * @return el estado verdadero o falso si se pudo guardar
     */
    public boolean saveMLP(String path){
        try
        {
            FileOutputStream fout = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }

    /**
     * Entrenar la red neuronal se establecen el numero de epocas
     * y se evaluan los valores la cantidad de epocas establecidas
     * se muestra el error generado por cada epoca
     * Guarda en un archivo de texto el resultado de el error para cada iteracion
     * @param epoch
     * @return
     */
    public double train(int epoch){
        Numerics.EPOCH = epoch;
        epochs = epoch;

        int c=0;
        double error=1;
        PrintWriter fout = null;
        try {
            fout = new PrintWriter(new FileWriter("data_train.txt"));
            while (c<=epoch){
                double temp_error=error;
                for(int i=0;i<NN_input.size();i++){
                    error = mlp.backPropagate(NN_input.get(i),NN_target.get(i))/2;
                }
                error = (temp_error<error)?temp_error:error;
                //System.out.println("Error "+error+ " Epoca "+c);
                fout.println(c+","+error);
                c++;
            }
            errorTrain = error;
            Numerics.ERROR = errorTrain;
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return error;


    }

    /**
     * Funcion que ejecuta la red neuronal dado una coordenada de entrada establecida
     * evalua si la coordenada es valida y en caso de que no lo sea retorna null
     * Encuentra el valor del angulo de la base a traves de la tangente inversa doble
     * Se normalizan las entradas de la red para ser procesadas
     * Se llama a la funcion que ejecuta la red neuronal y se obtienen los angulos del brazo y antebrazo
     * Se denormalizan los angulos obtenidos de la red neuronal
     * se retorna los angulos obtenidos redondeados a cantidades legibles
     * @param test
     * @return
     */
    public double[] execute(double[] test){
        if(Numerics.isValidCoord(test)){

            double Q1 =  Math.toDegrees( Math.atan2(test[0],test[1]) );
            //Normalizando las entradas a la red neuronal
            double input_ANN[] = { Input.Normalize( test[1], Ymin,Ymax), Input.Normalize( test[2], Zmin,Zmax) };
            //obtengo angulos de la red neuronal
            double Angles_ANN[] = mlp.execute(input_ANN);
            //denormalizando los angulos
            Angles_ANN[0] = Input.DeNormalize(Angles_ANN[0],0,90);
            Angles_ANN[1] = Input.DeNormalize(Angles_ANN[1],-90,0);
            //hago un vector donde esten todos los angulos de normalizados
            double angles[] = {  Q1, Angles_ANN[0],Angles_ANN[1]  };

            return  (round==true)?Numerics.roundDecimals( angles ):angles;
            //return angles;
        }else{
            //en caso de que no sea valido retorna null
            return null;
        }
    }

    /**
     * Retorna el arreglo de enteros que guarda la cantidad de capas de la red
     * @return
     */
    public int[] getLayers() {
        return layers;
    }

    /**
     * Retorna el factor de aprendizaje usado para entrenamiento
     * @return
     */
    public double getLearningRate() {
        return learningRate;
    }

    /**
     * Retorna el ultimo error de entrenamiento generado
     * @return
     */
    public double getErrorTrain() {
        return errorTrain;
    }

    /**
     * Retorna la ultima cantidad de epocas de entrenamiento usadas
     * @return
     */
    public int getEpochs() {
        return epochs;
    }
}
