package Function;
import Class.csvIO;
import Class.NaiveBayesNode;
import Class.Exchange;
import Class.Standardization;
import Class.Judge;
import java.util.ArrayList;

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
    }
}
