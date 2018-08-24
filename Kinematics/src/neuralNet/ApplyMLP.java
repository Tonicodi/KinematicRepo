package neuralNet;

import neuralNet.mlp.MultiLayerPerceptron;
import neuralNet.mlp.TransferFunction;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ApplyMLP {

    MultiLayerPerceptron mlp[];
    int[] layers;
    double learningRate;
    TransferFunction fun;

    public ApplyMLP(int[] layers, double learningRate, TransferFunction fun){
        mlp = new MultiLayerPerceptron[2];

        mlp[0] = new MultiLayerPerceptron(layers,learningRate,fun);
        mlp[1] = new MultiLayerPerceptron(layers,learningRate,fun);

        this.layers = layers;
        this.learningRate = learningRate;
        this.fun = fun;
    }

    public ApplyMLP(String path){
        mlp[0] = MultiLayerPerceptron.load("0"+path);
        mlp[1] = MultiLayerPerceptron.load("1"+path);
    }

    public boolean saveMLP(String path){
        return mlp[0].save("0"+path) && mlp[1].save("1"+path);
    }

    public double train(int pos_mlp,int epoch,double minError,ArrayList<double[][]> inputs){
        int c=0;
        double error=1;
        PrintWriter fout = null;
        try {
            fout = new PrintWriter(new FileWriter("data_train.txt"));
            while (c<=epoch){
                for(int i=0;i<inputs.size();i++){
                    error = mlp[pos_mlp].backPropagate(inputs.get(i)[0],inputs.get(i)[1]);
                }
                System.out.println("Error "+error+ " Epoca "+c);
                fout.println(c+","+error);
                c++;
            }
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return error;


    }

    public double[] execute(int pos_mlp,double[] test){
        return mlp[pos_mlp].execute(test);
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

}
