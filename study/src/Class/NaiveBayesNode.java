package Class;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class NaiveBayesNode {
    int length;
    int train_length;
    ArrayList<int[]> category_0 = new ArrayList<>();//存储类别0的所有数据
    ArrayList<int[]> category_1 = new ArrayList<>();//存储类别1的所有数据
    ArrayList<String> result = new ArrayList<>();//存储结果
    public void train(double[][] data, double[] label){
        length = data[0].length;
        train_length = label.length;

        for (int i =0;i< label.length;i++){
            int[] tem = new int[length];
            if(label[i]==1.0){
                for (int j = 0;j<length;j++){
                    tem[j] = (int)data[i][j];
                }
                category_1.add(tem);

            }else {
                for (int j = 0;j<length;j++){
                    tem[j] = (int)data[i][j];
                }
                category_0.add(tem);

            }
        }
    }

    //x是一行数据
    public double bayes(int[] x,ArrayList<int[]> category){

        double[] ai_y = new double[length];
        int[] sum_ai = new int[length];

        for (int i =0;i<length;i++){

            for (int j =0;j<category.size();j++){
                if (x[i] == category.get(j)[i]){
                    sum_ai[i]++;
                }
            }

        }


        for (int i = 0;i<length;i++){
            ai_y[i] = (double) sum_ai[i]/(double) category.size();
            //System.out.println(""+sum_ai[i]+"  "+category.size());
        }

        double result=1;
        for (int k =0;k<length;k++){
            result = result*ai_y[k];
        }
        return result;
    }


    public ArrayList<String> getResult() {
        return result;
    }

    //预测
    public void test(double[][] data,double[] label){
        double p_y0 = (double) category_0.size()/(double) train_length;
        double p_y1 = (double) category_1.size()/(double) train_length;//表示概率p（1）
        int[] x = new int[length];
        double x_in_0,x_in_1;
        int correct = 0;

        for (int r =0;r<label.length;r++){
            for (int i =0;i<length;i++){
                x[i] = (int) data[r][i];
            }
            x_in_0 = bayes(x,category_0)*p_y0;
            x_in_1 = bayes(x,category_1)*p_y1;


            if (x_in_0>x_in_1){
                System.out.println("预测值为0 "+x_in_0);
                result.add("clean");
                if (label[r]==0.0){
                    correct++;

                }
            }else {
                System.out.println("预测值为1 "+x_in_1);
                result.add("buggy");
                if (label[r]==1.0){
                    correct++;
                }
            }
        }



        DecimalFormat dF = new DecimalFormat("0.0000");  //精确度
        System.out.println("正确率为："+dF.format((float)correct/label.length));

    }






}
