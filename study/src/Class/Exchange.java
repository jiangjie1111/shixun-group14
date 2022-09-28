package Class;

import java.util.ArrayList;
import java.util.Objects;

public class Exchange {
    /**
     * 类型转换
     * @param csvList 读取的初始数据
     * @param type 标签数据
     * @return
     */
    //将string[]格式转换为double[][]格式，并且将类型存入type
    public double[][] exchange(ArrayList<String[]> csvList, ArrayList<String> type){
        ArrayList<double[]> dataList = new ArrayList<double[]>();
        for (int i=0;i<csvList.size();i++){  //i行 j列
            double[] list = new double[61]; //61位数

            for (int j=0;j<61;j++){
                list[j] = Double.parseDouble(csvList.get(i)[j]);
            }
            dataList.add(list);  //将double[]加入到array
            type.add(csvList.get(i)[61]);  //将末尾clean bug类型加入到array

        }
        double[][] data_List = dataList.toArray(new double[0][]);
        return data_List;

    }

    //将类型存储为0或1
    public double[] type_change(ArrayList<String> type){

        ArrayList<Double> t_num = new ArrayList<Double>();
        for (int i=0;i<type.size();i++){
            if(Objects.equals(type.get(i), "clean")){
                t_num.add(0.0);
            }else {
                t_num.add(1.0);
            }
        }
        double[] type_num = t_num.stream().mapToDouble(i->i).toArray();

        return type_num;
    }

    public ArrayList<String> type_change_back(double[] type){
        ArrayList<String> str = new ArrayList<String>();
        for (int i =0;i<type.length;i++){
            if (type[i]==0.0){
                str.add("clean");
            }else {
                str.add("buggy");
            }

        }
        return str;
    }

    public double[] W_exchange(ArrayList<String[]> Weight){  //转换训练模型
        double[] W = new double[Weight.get(0).length];
        for (int p=0;p<Weight.get(0).length;p++){
            W[p] = Double.parseDouble(Weight.get(0)[p]);
        }
        return W;
    }

}
