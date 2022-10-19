package com.example.demo.service;

import java.util.Arrays;

/**
 * @program: algorithm
 * @description: knn algorithm
 * @create: 2022-09-14 18:46
 **/
public class knn {
    // k个最近邻
    public int k;
    /* 训练集列表,列表元素为v：v[0]为训练集数据向量，v[1]为训练集标签
        * double[i]为第i个训练数据
        * double[i][0]为第i个训练数据中的数据向量
        * double[i][1][0]为第i个训练数据中的标签
        * double[i][0][n]为第i个训练数据中的数据向量中的第n个元素
    */
    public double[][][] train_set;

    // 构造函数
    public knn(int k) {
        this.k = k;
        this.train_set = new double[0][][];
    }

    // 计算两个向量的欧式距离
    // 函数假定向量是长度相等的数组
    public double dist(double[] v1, double[] v2){
        double sum = 0;
        for (int i = 0; i < v1.length; i++) {
            sum += Math.pow(v1[i] - v2[i], 2);
        }
        return Math.sqrt(sum);

    }

    // 更新最大值
    public double updateMax(double[] arr){
        double max = 0;
        for (double v : arr) {
            if (v >= max) {
                max = v;
            }
        }
        return max;
    }

    // 返回分类结果, 1为buggy, 0为clean
    public int mode(int[] arr){
        int[] frequency = new int[2];
        int max = 0;
        int result = -1;
        // 看arr[]中出现最多的数字是多少，返回result
        for (int j : arr) {
            frequency[j]++;
            if (frequency[j] > max) {
                max = frequency[j];
                result = j;
            }
        }
        //System.out.println(Arrays.toString(arr));
        //System.out.println(Arrays.toString(frequency));
        return result;
    }

    // 训练函数
    public void fit(double[] vector, int label){
        /* 将vector进行归一化
        double max = vector[0];
        for (double v : vector) {
            if (v >= max) {
                max = v;
            }
        }
        for (int i = 0; i < vector.length; i++) {
            vector[i] /= max;
        }
        */
        // 将训练数据添加到训练集中
        double[][][] new_train_set = new double[this.train_set.length + 1][2][];
        System.arraycopy(this.train_set, 0, new_train_set, 0, this.train_set.length);
        new_train_set[this.train_set.length][0] = vector;
        new_train_set[this.train_set.length][1] = new double[]{label};
        this.train_set = new_train_set;
    }

    // 预测函数
    public int predict(double[] vector){
        /* 将vector进行归一化
        double max = vector[0];
        for (double v : vector) {
            if (v >= max) {
                max = v;
            }
        }
        for (int i = 0; i < vector.length; i++) {
            // 若max为0，则不进行归一化
            if (max != 0) {
                vector[i] /= max;
            }
        }
        */
        double maxDistance = 0;
        double[][] voteBlock = new double[0][2];
        for (double[][] train_set : this.train_set) {


            double distance = this.dist(train_set[0], vector);
            int label = (int) train_set[1][0];

            if (voteBlock.length < this.k){
                double [][]new_voteBlock = new double[voteBlock.length + 1][2];
                System.arraycopy(voteBlock, 0, new_voteBlock, 0, voteBlock.length);
                new_voteBlock[voteBlock.length][0] = distance;
                new_voteBlock[voteBlock.length][1] = label;
                voteBlock = new_voteBlock;

                double []distArray = new double[voteBlock.length];
                for (int i = 0; i < voteBlock.length; i++) {
                    distArray[i] = voteBlock[i][0];
                }

                maxDistance = this.updateMax(distArray);
            }
            else{
                if(distance < maxDistance){
                    boolean flag = true;
                    int count = 0;
                    while (flag){
                        if(voteBlock[count][0] == maxDistance){
                            // System.out.println("delete");
                            // 删除第count个元素，将distance和label插入到第count个元素
                            voteBlock[count][0] = distance;
                            voteBlock[count][1] = label;

                            double []distArray = new double[voteBlock.length];
                            for (int i = 0; i < voteBlock.length; i++) {
                                distArray[i] = voteBlock[i][0];
                            }

                            maxDistance = this.updateMax(distArray);
                            flag = false;
                        }
                        else{
                            if(count < voteBlock.length - 1){
                                count++;
                            }
                            else{
                                flag = false;
                            }
                        }
                    }
                }
            }
        }
        int []votes = new int[voteBlock.length];
        for(int i = 0; i < voteBlock.length; i++){
            votes[i] = (int) voteBlock[i][1];
        }
        // System.out.println(voteBlock.length);
        // System.out.println(Arrays.deepToString(voteBlock));
        // System.out.println(Arrays.toString(votes));

        // 返回k个数据中出现最多的标签
        return this.mode(votes);
    }

    public void saveModel(String s) {
    }
}
