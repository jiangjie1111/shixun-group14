package Class;

import com.csvreader.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class csvIO {
    /**
     * 文件读取保存操作
     * @param csvFilePath 文件路经
     * @return
     */
    //读取
    public ArrayList<String[]> readCsv(String csvFilePath) {
        ArrayList<String[]> csvList = null;
        try {
            csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(csvFilePath);
            reader.readHeaders(); //跳过表头，如果需要表头的话，不要写这句。
            while (reader.readRecord()) { //逐行读入数据
                csvList.add(reader.getValues());
            }
            reader.close();
            //System.out.println(Arrays.toString(csvList.get(0)));  //打印第一行数据
            //System.out.println(csvList.get((csvList.size()-1))[0]);//打印最后一行第一列的数据
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvList;

    }

    public void writeCsv(String csvFilePath, ArrayList<String> type_result) {
        try {
            // 创建CSV写对象
            //CsvWriter csvWriter = new CsvWriter(csvFilePath,',', StandardCharsets.UTF_8);
            CsvWriter csvWriter = new CsvWriter(csvFilePath);

            // 写表头
            String[] headers = {"class"};
            csvWriter.writeRecord(headers);
            String[] content = {""};
            for (String s : type_result) {
                content[0] = s;
                csvWriter.writeRecord(content);
            }
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void writeLRCsv(String csvFilePath, double[] file) {  //保存逻辑回归模型
        try {
            CsvWriter csvWriter = new CsvWriter(csvFilePath);
            String[] str = new String[file.length];
            for (int i = 0; i < file.length; i++) {
                str[i] = String.valueOf(file[i]);
            }
            System.out.println(Arrays.toString(str));
            csvWriter.writeRecord(str);
            csvWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String[]> readW(String csvFilePath) {  //读取训练模型
        ArrayList<String[]> csvList = null;
        try {
            csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(csvFilePath);
            while (reader.readRecord()) { //逐行读入数据
                csvList.add(reader.getValues());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvList;

    }
}