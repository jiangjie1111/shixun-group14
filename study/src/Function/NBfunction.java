package Function;
import Class.csvIO;
import Class.NaiveBayesNode;
import Class.Exchange;
import Class.Standardization;
import Class.Judge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class NBfunction {

    public static void main(String[] args){
        /**
         * 朴素贝叶斯训练
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

        //离散化
        dataList_train = Standardization.discrete(dataList_train);
        dataList_test = Standardization.discrete(dataList_test);

        NaiveBayesNode nb = new NaiveBayesNode();
        nb.train(dataList_train,train_type_double);
        nb.test(dataList_test,test_type_double);
        type_result = nb.getResult();

        //计算得分
        Judge judge = new Judge();
        judge.score(type_test,type_result);

        //写入csv
        csvio.writeCsv("D:\\csvdata\\NBresult.csv",type_result);



    }
}
