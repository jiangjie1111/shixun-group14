package com.example.demo.service;

import java.util.Arrays;

/**
 * @program: algorithm
 * @description: Logistic Regression class
 * @create: 2022-10-03 22:41
 **/
public class logisticRegression {
    public double[] w;

    public logisticRegression() {
        this.w = new double[0];
    }

    // 计算sigmoid函数
    public double sigmoid(double z) {
        return 1 / (1 + Math.exp(-z));
    }

    // 计算损失函数, 为对数损失函数
    public double loss(double[][] train_set, double[] w) {
        double loss = 0;
        for (int i = 0; i < train_set.length; i++) {
            double[] x = new double[train_set[i].length - 1];
            for (int j = 0; j < train_set[i].length - 1; j++) {
                x[j] = train_set[i][j];
            }
            double y = train_set[i][train_set[i].length - 1];
            double z = 0;
            for (int j = 0; j < x.length; j++) {
                z += w[j] * x[j];
            }
            loss += y * Math.log(sigmoid(z)) + (1 - y) * Math.log(1 - sigmoid(z));
        }
        return -loss / train_set.length;
    }

    // 预测函数, 返回sigmoid函数的值
    public double[] predict(double[][] x) {
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            double z = 0;
            for (int j = 0; j < x[i].length - 1; j++) {
                z += x[i][j] * w[j];
            }
            result[i] = sigmoid(z);
        }
        return result;
    }

    // 训练函数, 极大似然估计 + 梯度下降
    public void train(double[][] x,double[] y, double learning_rate, int max_iter){
        // 初始化w
        w = new double[x[0].length];
        Arrays.fill(w, 0);
        // 训练
        for (int i = 0; i < max_iter; i++) {
            // 计算梯度
            double[] gradient = new double[w.length];
            for (int j = 0; j < x.length; j++) {
                double z = 0;
                for (int k = 0; k < x[j].length; k++) {
                    z += w[k] * x[j][k];
                }
                for (int k = 0; k < x[j].length; k++) {
                    gradient[k] += (sigmoid(z) - y[j]) * x[j][k];
                }
            }
            // 更新w
            for (int j = 0; j < w.length; j++) {
                w[j] -= learning_rate * gradient[j] / x.length;
            }
        }
    }

    // 计算准确率
    public double accuracy(double[] h, double[] y) {
        int correct = 0;
        for (int i = 0; i < h.length; i++) {
            if (h[i] >= 0.5 && y[i] == 1) {
                correct++;
            } else if (h[i] < 0.5 && y[i] == 0) {
                correct++;
            }
        }
        return (double) correct / h.length;
    }


}
