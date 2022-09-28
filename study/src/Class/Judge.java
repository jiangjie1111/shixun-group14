package Class;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class Judge {
    /**
     * 评价训练效果
     */
    public void score(ArrayList<String> type_test,ArrayList<String> type_result){
        int scoreNum = 0;
        for (int b = 0;b<type_test.size();b++){
            if (Objects.equals(type_test.get(b), type_result.get(b))){  //正确诊断
                if (Objects.equals(type_result.get(b), "buggy")){  //正确诊断出bug加分，正确诊断出clean不得分
                    scoreNum++;
                }
            }else {  //诊断错误扣分
                scoreNum--;
            }
        }
        int scoreTotal = 0;
        for (int i =0;i<type_test.size();i++){
            if (Objects.equals(type_test.get(i), "buggy")){
                scoreTotal++;
            }
        }
        DecimalFormat dF = new DecimalFormat("0.0000");  //精确度
        System.out.println("得分为:"+dF.format((float)scoreNum/scoreTotal));
    }
}
