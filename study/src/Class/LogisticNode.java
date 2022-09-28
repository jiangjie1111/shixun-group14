package Class;

import java.text.DecimalFormat;

public class LogisticNode {
    int paraNum; //权重参数个数
    double rate; //学习率
    int sample_num;  //样本个数
    double[][] feature;  //样本特征矩阵
    double[] label;  //样本标签
    int maxCycle;  //最大迭代次数

    public LogisticNode(double[][] feature,double[] label,int paraNum,double rate,int sample_num,int maxCycle){
        this.feature = feature;
        this.label =label;
        this.paraNum =paraNum;
        this.rate =rate;
        this.sample_num = sample_num;
        this.maxCycle = maxCycle;
    }

    //矩阵初始化
    public double[] paraInit(int paraNum){
        double[] W = new double[paraNum];
        for (int i=0;i<paraNum;i++){
            W[i] = 1.0;
        }
        return W;
    }

    //计算每次迭代后的预测误差
    public double[] PreVal(int sample_num,int paraNum,double[][] feature,double[] W){
        double[] Preval = new double[sample_num];
        for (int i=0;i<sample_num;i++){
            double tmp = 0;
            for (int j=0;j<paraNum;j++){
                tmp += feature[i][j]*W[j];
            }
            Preval[i] = sigmoid(tmp);
        }
        return Preval;
    }

    //计算误差率
    public double error_rate(int sample_num,double[] label,double[] Preval){
        double sum_err = 0.0;
        for (int i=0;i<sample_num;i++){
            sum_err += Math.pow(label[i] - Preval[i],2);

        }
        return sum_err;
    }

    //LR模型训练
    public double[] Update(double[][] feature,double[] label,int maxCycle,double rate){

        //计算样本个数与特征个数
        int sample_num = feature.length;
        int para_num = feature[0].length;

        //初始化权重矩阵
        double[] W = paraInit(para_num);
        //收敛
        double[] S = new double[2];
        S[0] = 999999999;
        S[1] = 0;

        //循环迭代优化权重矩阵
        for (int i=0;i<maxCycle;i++){
            //每次迭代计算样本
            double[] Preval = PreVal(sample_num,para_num,feature,W);
            double sum_err = error_rate(sample_num,label,Preval);
            if (i%100 == 0){
//                System.out.println("第"+i+"次迭代的预测误差为:"+sum_err);
                DecimalFormat dF = new DecimalFormat("0.0000");  //精确度
                System.out.println("进度："+dF.format((float)i/maxCycle));
                //收敛
                S[1] = sum_err;
                double abs = Math.abs(S[0] - S[1]);
                System.out.println(abs);
                if (abs <0.000001){
                    System.out.println("收敛！");
                    break;
                }
                S[0] = sum_err;
            }
            //预测与标签的误差
            double[] err = new double[sample_num];
            for (int j =0;j<sample_num;j++){
                err[j] = label[j] - Preval[j];
            }
            //计算权重矩阵的梯度方向
            double[] gra_W = new double[para_num];
            for (int n =0;n<para_num;n++){
                double tmp = 0;
                for (int m =0;m<sample_num;m++){
                    tmp += feature[m][n]*err[m];

                }
                gra_W[n] = tmp/sample_num;
            }

            for (int s=0;s<para_num;s++){
                W[s] = W[s] + rate*gra_W[s];
            }


        }
        return W;
    }

    //Sigmoid
    public static double sigmoid(double x){
        double i = 1.0;
        double y = i/(i+Math.exp(-x));
        return y;
    }

}
