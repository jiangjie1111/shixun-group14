package Class;

import java.text.DecimalFormat;
import java.util.*;

public class KNNnode {

    //计算一条测试数据的相似度,选取距离中前k个最近的数据,得到一条数据的预测值
    public ArrayList<String> calcul(double[][] dataList_train, double[][] dataList_test, ArrayList<String> type, int K) {
        ArrayList<String> type_result = new ArrayList<String>();
        int len = dataList_train[0].length;
        for (int q = 0; q < dataList_test.length; q++) {  //测试集的第n行
            Map<Integer, Double> map = new HashMap<Integer, Double>();
            for (int s = 0; s < dataList_train.length; s++) {  //计算训练集第s行的数据
                double length = 0;
                for (int m = 0; m < len; m++) {  //计算所有点的距离
                    length = length + Math.pow(dataList_test[q][m] - dataList_train[s][m], 2);
                }

                map.put(s, Math.sqrt(length));  //将距离存在图中

            }
            List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(map.entrySet());

            //重写排序方法
            Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
                @Override
                public int compare(Map.Entry<Integer, Double> o1,
                                   Map.Entry<Integer, Double> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }

            });

            //选取前k个值
            String[] result_type = new String[K];
            double[] distance = new double[K];
            int k = 0;

            for (Map.Entry<Integer, Double> mapping : list) {
                if (k < K) {
                    result_type[k] = type.get(mapping.getKey());
                    distance[k] = mapping.getValue();
                    k++;
                } else {
                    break;
                }
            }

            //加权计算
            double[] P = new double[K]; //权重
            for (int n = 0; n < K; n++) {
                P[n] = gaussian(distance[n], 1, 0, 0.3);
            }

            //统计出现最多次数的type的权重
            double cleanNum = 0;
            double bugNum = 0;
            for (int n = 0; n < K; n++) {
                if (Objects.equals(result_type[n], "clean")) {
                    cleanNum++;
                } else {
                    bugNum++;
                }
            }

            if (cleanNum > bugNum) {
                type_result.add("clean");
            } else {
                type_result.add("buggy");
            }

        }
        return type_result;


    }
    public double gaussian ( double dist, double a, double b, double c){ //高斯函数，计算权重
        double z = 0 - (float) Math.pow(dist - b, 2) / (float) 2 * Math.pow(c, 2);
        double g = a * Math.pow(Math.E, z);
        return g;
    }
}