package Function;
import Class.csvIO;
import Class.LogisticNode;
import Class.Exchange;
import Class.LogisticTest;
import Class.Standardization;
import Class.Judge;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LogisticFunction {

    public static void main(String[] args) {
/**
 * 逻辑回归训练
 */
        ArrayList<String[]> csvList_train = new ArrayList<String[]>();
        ArrayList<String[]> csvList_test = new ArrayList<String[]>();

        ArrayList<String> type_train = new ArrayList<String>();
        ArrayList<String> type_test = new ArrayList<String>();
        ArrayList<String> type_result = new ArrayList<String>();

        csvIO csvio = new csvIO();
        csvList_train = csvio.readCsv("D:\\csvdata\\PDE.csv");
        csvList_test = csvio.readCsv("D:\\csvdata\\Lucene.csv");


         /*
        转换格式
         */
        Exchange change = new Exchange();
        double[][] dataList_train = change.exchange(csvList_train,type_train);
        double[][] dataList_test = change.exchange(csvList_test,type_test);

        //clean为0，buggy为1
        double[] train_type_double = change.type_change(type_train);
        double[] test_type_double = change.type_change(type_test);

        //标准化
//        dataList_train = Standardization.ZScore(dataList_train);
//        dataList_test = Standardization.ZScore(dataList_test);

        /*
        设置参数
         */
        int sample_num = dataList_train.length;  //训练集样本个数
        int para_num = dataList_train[0].length;  //训练集特征个数
        int test_sample_num = dataList_test.length;  //测试集样本个数
        int test_para_num = dataList_test[0].length;  //测试集特征个数
        double rate = 0.0003;  //学习率
        int maxCycle = 1000000;  //迭代次数

        //LR训练模型
        LogisticNode LR = new LogisticNode(dataList_train,train_type_double,para_num,rate,sample_num,maxCycle);
        double[] W = LR.Update(dataList_train,train_type_double,maxCycle,rate);

        /*
        保存模型
         */
        csvio.writeLRCsv("D:\\csvdata\\LR_W.csv",W);

        //预测
        double[] pre = LogisticTest.test(test_para_num,test_sample_num,dataList_test,W);
        System.out.println(Arrays.toString(pre));

        //计算准确率
        int countNum=0;
        for (int a=0;a<test_type_double.length;a++){
            if (pre[a]==test_type_double[a]){
                countNum++;
            }
        }
        DecimalFormat dF = new DecimalFormat("0.0000");  //精确度
        System.out.println("正确率为："+dF.format((float)countNum/test_type_double.length));



        /*
        保存预测结果
         */
        type_result = change.type_change_back(pre);
        //写入csv
        csvio.writeCsv("D:\\csvdata\\LRresult.csv",type_result);

        //计算得分
        Judge judge = new Judge();
        judge.score(type_test,type_result);
    }
}
