package Function;
import Class.csvIO;
import Class.KNNnode;
import Class.Exchange;
import Class.Standardization;
import Class.Judge;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class KNNfunction {

    public static void main(String[] args){
        /**
         * KNN训练
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

        //标准化
        dataList_train = Standardization.ZScore(dataList_train);
        dataList_test = Standardization.ZScore(dataList_test);

        //计算距离
        KNNnode knn = new KNNnode();
        int K = 10; //K值
        type_result = knn.calcul(dataList_train,dataList_test,type_train,K);

        //写入csv
        csvio.writeCsv("D:\\csvdata\\KNNresult.csv",type_result);

        //计算正确率
        int countNum=0;
        for (int a=0;a<type_test.size();a++){
            if (Objects.equals(type_test.get(a), type_result.get(a))){
                countNum++;
            }
        }
        DecimalFormat dF = new DecimalFormat("0.0000");  //精确度
        System.out.println("正确率为："+dF.format((float)countNum/type_test.size()));

        //计算得分
        Judge judge = new Judge();
        judge.score(type_test,type_result);


    }



}
