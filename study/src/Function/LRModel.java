package Function;
import Class.csvIO;
import Class.Exchange;
import Class.Standardization;
import Class.LogisticTest;
import Class.Judge;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class LRModel {

    public static void main(String[] args){
/**
 * 通过训练模型预测,逻辑回归
 */
        ArrayList<String[]> Weight = new ArrayList<String[]>();
        ArrayList<String[]> csvList_test = new ArrayList<String[]>();
        ArrayList<String> type_test = new ArrayList<String>();
        ArrayList<String> type_result = new ArrayList<String>();
        csvIO csvio = new csvIO();
        Exchange change = new Exchange();
        csvList_test = csvio.readCsv("D:\\csvdata\\Lucene.csv"); //读取测试集
        Weight = csvio.readW("D:\\csvdata\\LR_W.csv");  //读取训练模型
        double[] W = change.W_exchange(Weight);  //转换格式
        double[][] dataList_test = change.exchange(csvList_test,type_test);
        double[] test_type_double = change.type_change(type_test);
        //标准化
//        dataList_test = Standardization.ZScore(dataList_test);


        //预测
        int test_sample_num = dataList_test.length;  //测试集样本个数
        int test_para_num = dataList_test[0].length;  //测试集特征个数
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
