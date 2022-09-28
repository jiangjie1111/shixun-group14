package Class;

public class LogisticTest {
    //预测
    public static double[] test(int para_num,int sample_num,double[][] feature,double[] W){
        double[] pre = new double[sample_num];
        for (int i=0;i<sample_num;i++){
            double tmp = 0;
            for (int j =0;j<para_num;j++){
                tmp += feature[i][j]*W[j];
            }
            if (tmp>=0.5){
                pre[i] = 1;
            }else {
                pre[i] = 0;
            }
        }
        return pre;
    }
}
