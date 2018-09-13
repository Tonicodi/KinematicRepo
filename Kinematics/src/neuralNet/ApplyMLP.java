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


    double Ymin= 0 ,Ymax= 110,Zmin= -32,Zmax= 138;


    public ApplyMLP(int[] layers, double learningRate, TransferFunction fun,ArrayList<double[]> inputs,ArrayList<double[]> outputs){

        mlp = new MultiLayerPerceptron(layers,learningRate,fun);
        Numerics.LAYER     = layers[1];
        this.layers        = layers;
        this.learningRate  = learningRate;
        this.fun           = fun;
        this.NN_input      = inputs;
        this.NN_target     = outputs;
    }

    public PRECISION getPrecision() {
        return precision;
    }

    public void setPrecision(PRECISION precision) {
        this.precision = precision;
    }




    /**
     * Carga una red MLP del archivo
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
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return error;


    }

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

            return Numerics.roundDecimals( angles );
        }else{
            //en caso de que no sea valido retorna null
            return null;
        }
    }


    public static double[][] getArrayInputs(ArrayList<double[][]> inputs){
        double data[][] = new double[inputs.size()][inputs.get(0)[0].length];
        for (int i=0;i<inputs.size();i++){
            data[i] = inputs.get(i)[0];
            //System.out.println("Input "+Arrays.toString(data[i]));
        }
        return data;
    }

    public static double[][] getArrayOutputs(ArrayList<double[][]> inputs){
        double data[][] = new double[inputs.size()][inputs.get(0)[0].length];
        for (int i=0;i<inputs.size();i++){
            data[i] = inputs.get(i)[1];
            //System.out.println("Ouput "+Arrays.toString(data[i]));
        }
        return data;
    }


    public int[] getLayers() {
        return layers;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public double getErrorTrain() {
        return errorTrain;
    }

    public int getEpochs() {
        return epochs;
    }
}
