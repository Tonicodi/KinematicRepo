package neuralNet;

import neuralNet.mlp.MultiLayerPerceptron;
import neuralNet.mlp.TransferFunction;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ApplyMLP {

    MultiLayerPerceptron mlp;
    int[] layers;
    double learningRate;
    TransferFunction fun;

    public ApplyMLP(int[] layers, double learningRate, TransferFunction fun){
        mlp = new MultiLayerPerceptron(layers,learningRate,fun);
        this.layers = layers;
        this.learningRate = learningRate;
        this.fun = fun;
    }

    public ApplyMLP(String path){
        mlp = MultiLayerPerceptron.load(path);
    }

    public void train(int epoch,double minError,ArrayList<double[][]> inputs){
        int c=0;
        while (c<=epoch){
            for(int i=0;i<inputs.size();i++){
                double error = mlp.backPropagate(inputs.get(i)[0],inputs.get(i)[1]);
                System.out.println("Error "+error+ "Epoca "+c +" coord "+ Arrays.toString(inputs.get(i)[0])+" angulo "+ Arrays.toString(inputs.get(i)[1]));
            }
            System.out.println();
            c++;
        }
    }

}
