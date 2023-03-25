package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.controller.dto.FileVO;
import com.example.demo.entity.Book;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.knn;
import com.example.demo.service.logisticRegression;
import com.example.demo.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController extends BaseController {
    @Value("${server.port}")
    private String port;

    @Value("${file.ip}")
    private String ip;

    @Resource
    BookMapper bookMapper;

    @Resource
    OrderMapper OrderMapper;

    //return给前端的两个数组
    String[] KNNTrainData = new String[5];
    String[] LRTrainData = new String[9];
    Book KNNResult = new Book();
    Order LRResult = new Order();
    String temp = "";
    String originalFilename_temp = "";

    @PostMapping("/upload/KNNtrain")
    public Result<?> upload_KNNtrain(MultipartFile file) throws IOException {
        originalFilename_temp = file.getOriginalFilename();  // 获取源文件的名称
        // 定义文件的唯一标识（前缀）
        temp = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/files/" + temp + "_" + originalFilename_temp;  // 获取上传的路径
        File rootFile = new File(rootFilePath);
        if (!rootFile.getParentFile().exists()) {
            rootFile.getParentFile().mkdirs();
        }
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        return Result.success();
    }

    @PostMapping("/upload/LRtrain")
    public Result<?> upload_LRtrain(MultipartFile file) throws IOException {
        originalFilename_temp = file.getOriginalFilename();  // 获取源文件的名称
        // 定义文件的唯一标识（前缀）
        temp = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/files/" + temp + "_" + originalFilename_temp;  // 获取上传的路径
        File rootFile = new File(rootFilePath);
        if (!rootFile.getParentFile().exists()) {
            rootFile.getParentFile().mkdirs();
        }
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        return Result.success();
    }
    /**
     * 上传+knn训练接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/KNNtest")
    public Result<?> upload_KNN(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 获取源文件的名称
        // 定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/files/" + flag + "_" + originalFilename;  // 获取上传的路径
        File rootFile = new File(rootFilePath);
        if (!rootFile.getParentFile().exists()) {
            rootFile.getParentFile().mkdirs();
        }
        FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径
        //return Result.success("http://" + ip + ":" + port + "/files/" + flag);  // 返回结果 url
        //KNNResult.id=this.id;
        int k = 5;

        // 读取训练集csv，为Datasets/AEEEM/csv/JDT.csv
        double[][][] train_set = new double[0][][];
        try {
            train_set = readTrainSet(System.getProperty("user.dir") + "/files/" + temp + "_" + originalFilename_temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("训练集读取完成，训练集大小：" + train_set.length);
        KNNTrainData[0] = String.valueOf(train_set.length);
        KNNResult.name=String.valueOf(train_set.length);

        // 读取测试集csv，为Datasets/AEEEM/csv/Lucene.csv
        double[][][] test_set = new double[0][][];
        try {
            test_set = readTrainSet(System.getProperty("user.dir") + "/files/" + flag + "_" + originalFilename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("测试集读取完成，测试集大小：" + test_set.length);
        KNNTrainData[1] = String.valueOf(test_set.length);
        KNNResult.author=String.valueOf(test_set.length);
        // 测试集标签比例
        int buggy = 0;
        for (double[][] doubles : test_set) {
            if (doubles[1][0] == 1) {
                buggy++;
            }
        }
        System.out.println("测试集中buggy比例：" + buggy + "/" + test_set.length + "=" + buggy / (double) test_set.length);
        KNNTrainData[2] =String.valueOf(buggy);
        KNNResult.price=String.valueOf(buggy);
        KNNTrainData[3] =String.valueOf(test_set.length-buggy);
        KNNResult.createTime=String.valueOf(test_set.length-buggy);
        // 创建knn对象
        knn knn = new knn(k);
        // 训练
        System.out.println("开始训练");
        for (double[][] v : train_set) {
            knn.fit(v[0], (int)v[1][0]);
        }
        System.out.println("训练完成");


        System.out.println("开始测试");
        // 测试
        int[] result = new int[test_set.length];
        for (int i = 0; i < test_set.length; i++) {
            result[i] = knn.predict(test_set[i][0]);
            // System.out.println("第" + (i+1) + "个测试样本，预测结果为：" + result[i] + "，实际结果为：" + (int)test_set[i][1][0]);
        }
        System.out.println("测试完成");
        // 原本的标签
        int[] label = new int[test_set.length];
        for (int i = 0; i < test_set.length; i++) {
            label[i] = (int) test_set[i][1][0];
        }
        // 计算准确率
        double accuracy = 0;
        for (int i = 0; i < test_set.length; i++) {
            if (result[i] == label[i]) {
                accuracy++;
            }
        }
        accuracy /= test_set.length;
        System.out.println("准确率为：" + accuracy);
        KNNTrainData[4] =String.valueOf(accuracy);
        KNNResult.cover=String.valueOf(accuracy);
        bookMapper.insert(KNNResult);

        return Result.success(KNNTrainData);  // 返回结果 url

    }

    /**
     * 上传+knn训练接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/LRtest")
    public Result<?> upload_LR(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 获取源文件的名称
        // 定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/files/" + flag + "_" + originalFilename;  // 获取上传的路径
        File rootFile = new File(rootFilePath);
        if (!rootFile.getParentFile().exists()) {
            rootFile.getParentFile().mkdirs();
        }
        FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径
        //return Result.success("http://" + ip + ":" + port + "/files/" + flag);  // 返回结果 url

        double[][] lr_train_set = new double[0][];
        // System.out.println(System.getProperty("user.dir") + "/algorithm/Datasets/AEEEM/csv/PDE.csv");
        try {
            lr_train_set = readTrainSetLr(System.getProperty("user.dir") + "/files/" + temp + "_" + originalFilename_temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("训练集读取完成，训练集大小：" + lr_train_set.length);
        LRTrainData[0] = String.valueOf(lr_train_set.length);
        LRResult.name=String.valueOf(lr_train_set.length);
        // 读取测试集
        double[][] lr_test_set = new double[0][];
        try {
            lr_test_set = readTrainSetLr(System.getProperty("user.dir") + "/files/" + flag + "_" + originalFilename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("测试集读取完成，测试集大小：" + lr_test_set.length);
        LRTrainData[1] = String.valueOf(lr_test_set.length);
        LRResult.totalPrice=String.valueOf(lr_test_set.length);
        // 训练模型
        logisticRegression lr = new logisticRegression();
        // 训练集分为x和y
        double[][] x = new double[lr_train_set.length][lr_train_set[0].length - 1];
        double[] y = new double[lr_train_set.length];
        for (int i = 0; i < lr_train_set.length; i++) {
            for (int j = 0; j < lr_train_set[0].length - 1; j++) {
                x[i][j] = lr_train_set[i][j];
            }
            y[i] = lr_train_set[i][lr_train_set[0].length - 1];
        }

        System.out.println("debug");

        lr.train(x, y, 0.01, 1000);

        // 测试模型
        double[] predict = lr.predict(lr_test_set);
        int lr_correct = 0,tp=0,fn=0;
        for (int i = 0; i < predict.length; i++) {
            if (predict[i] >= 0.5 && y[i] == 1) {
                lr_correct++;
                fn++;
            } else if (predict[i] < 0.5 && y[i] == 0) {
                lr_correct++;
                tp++;
            }
        }
        int tn=0,fp=0;
        for (int i = 0; i < predict.length; i++) {
            if (predict[i] >= 0.5 && y[i] == 0) {
                tn++;
            } else if (predict[i] < 0.5 && y[i] == 1) {
                fp++;
            }
        }

        LRTrainData[2] = String.valueOf(lr_correct);
        LRResult.payPrice=String.valueOf(lr_correct);
        LRTrainData[3] = String.valueOf(predict.length-lr_correct);
        LRResult.discount=String.valueOf(predict.length-lr_correct);
        // 计算准确率
        double accuracy = lr.accuracy(predict, y);
        System.out.println("准确率为：" + accuracy);
        LRTrainData[4] = String.valueOf(accuracy);
        LRResult.transportPrice=String.valueOf(accuracy);

        LRTrainData[5] = String.valueOf(tp);
        LRResult.tp=tp;
        LRTrainData[6] = String.valueOf(tn);
        LRResult.tn=tn;
        LRTrainData[7] = String.valueOf(fp);
        LRResult.fp=fp;
        LRTrainData[8] = String.valueOf(fn);
        LRResult.fn=fn;

        OrderMapper.insert(LRResult);
        return Result.success(LRTrainData);  // 返回结果 url

    }

//    @GetMapping("/return/KNNtrain")
//    public Result<?> return_lr() {
//        return Result.success(KNNTrainData);
//    }
    /**
     * 富文本文件上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 获取源文件的名称
        // 定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/files/" + flag + "_" + originalFilename;  // 获取上传的路径
        File rootFile = new File(rootFilePath);
        if (!rootFile.getParentFile().exists()) {
            rootFile.getParentFile().mkdirs();
        }
        FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径
        String url = "http://" + ip + ":" + port + "/files/" + flag;
        JSONObject json = new JSONObject();
        json.set("errno", 0);
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        arr.add(data);
        data.set("url", url);
        json.set("data", arr);
        return json;  // 返回结果 url
    }

    /**
     * 下载接口
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/files/";  // 定义文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

    /**
     * OSS文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload/oss")
    public Result<?> ossUpload(MultipartFile file) {
        return Result.success(AliOssUtil.upload("test/", file));  // 返回结果 url
    }

    /**
     * OSS文件删除
     * @param fileVO 文件存储路径
     * @return
     */
    @DeleteMapping("/delete/oss")
    public Result<?> ossUpload(@RequestBody FileVO fileVO) {
        AliOssUtil.delete(fileVO.getFilekey());
        return Result.success();
    }
/**
     * readTrainSet for KNN
     */
    public static double[][][] readTrainSet(String path) throws IOException {
        // 读取csv文件
        BufferedReader br = new BufferedReader(new FileReader(path));
        // 读取第一行，获取列数
        String line = br.readLine();
        String[] firstLine = line.split(",");
        int colNum = firstLine.length;
        // 读取第一列，获取行数
        int rowNum = 0;
        while ((line = br.readLine()) != null) {
            rowNum++;
        }
        // 读取数据
        br = new BufferedReader(new FileReader(path));
        double[][][] train_set = new double[rowNum][2][colNum];
        line = br.readLine();
        for (int i = 1; i < rowNum; i++) {
            line = br.readLine();
            String[] temp = line.split(",");
            for (int j = 0; j < colNum - 1; j++) {
                train_set[i][0][j] = Double.parseDouble(temp[j]);
            }
            if(temp[colNum - 1].equals("buggy")){
                train_set[i][1][0] = 1;
            }else{
                train_set[i][1][0] = 0;
            }
        }
        return train_set;
    }
    /**
     * readTestSet for Lr
     */
    public static double[][] readTrainSetLr(String path) throws IOException {
        // 读取训练集
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine();
        String[] firstLine = line.split(",");
        int colNum = firstLine.length;
        int rowNum = 0;
        while ((line = br.readLine()) != null) {
            rowNum++;
        }
        br = new BufferedReader(new FileReader(path));
        double[][] train_set = new double[rowNum][colNum];
        line = br.readLine();
        // 最后一列为标签, 令buggy为1，clean为0
        for (int i = 0; i < rowNum; i++) {
            line = br.readLine();
            String[] temp = line.split(",");
            for (int j = 0; j < colNum - 1; j++) {
                train_set[i][j] = Double.parseDouble(temp[j]);
            }
            if(temp[colNum - 1].equals("buggy")){
                train_set[i][colNum - 1] = 1;
            }else{
                train_set[i][colNum - 1] = 0;
            }
        }
        return train_set;
    }
}
