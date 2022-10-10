package Class;

public class Standardization {
    /**
     * 线性归一化 公式：X(norm) = (X - min) / (max - min)
     *
     * @param points 原始数据
     * @return 归一化后的数据
     */
    public static double[][] MaxMin(double[][] points) {
        if (points == null || points.length < 1) {
            return points;
        }
        double[][] p = new double[points.length][points[0].length];
        double[] matrixJ;
        double maxV;
        double minV;
        for (int j = 0; j < points[0].length; j++) {
            matrixJ = getMatrixCol(points, j);
            maxV = maxV(matrixJ);
            minV = minV(matrixJ);
            for (int i = 0; i < points.length; i++) {
                p[i][j] = maxV == minV ? minV : (points[i][j] - minV) / (maxV - minV);
            }
        }
        return p;
    }

    /**
     * 获取矩阵的某一列
     *
     * @param points points
     * @param column column
     * @return double[]
     */
    public static double[] getMatrixCol(double[][] points, int column) {
        double[] matrixJ = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            matrixJ[i] = points[i][column];
        }
        return matrixJ;
    }

    /**
     * 获取数组中的最小值
     *
     * @param matrixJ matrixJ
     * @return v
     */
    public static double minV(double[] matrixJ) {
        double v = matrixJ[0];
        for (int i = 0; i < matrixJ.length; i++) {
            if (matrixJ[i] < v) {
                v = matrixJ[i];
            }
        }
        return v;
    }

    /**
     * 获取数组中的最大值
     *
     * @param matrixJ matrixJ
     * @return v
     */
    public static double maxV(double[] matrixJ) {
        double v = matrixJ[0];
        for (int i = 0; i < matrixJ.length; i++) {
            if (matrixJ[i] > v) {
                v = matrixJ[i];
            }
        }
        return v;
    }







    /**
     * 0均值\标准差归一化 公式：X(norm) = (X - μ) / σ
     * X(norm) = (X - 均值) / 标准差
     *
     * @param points 原始数据
     * @return 归一化后的数据
     */
    public static double[][] ZScore(double[][] points) {
        if (points == null || points.length < 1) {
            return points;
        }
        double[][] p = new double[points.length][points[0].length];
        double[] matrixJ;
        double avg;
        double std;
        for (int j = 0; j < points[0].length; j++) {
            matrixJ = getMatrixCol(points, j);
            avg = average(matrixJ);
            std = standardDeviation(matrixJ);
            for (int i = 0; i < points.length; i++) {
                p[i][j] = std == 0 ? points[i][j] : (points[i][j] - avg) / std;
            }
        }
        return p;
    }

    /**
     * 方差s^2=[(x1-x)^2 +...(xn-x)^2]/n
     *
     * @param x x
     * @return 方差
     */
    public static double variance(double[] x) {
        int m = x.length;
        double sum = 0;
        for (int i = 0; i < m; i++) {//求和
            sum += x[i];
        }
        double dAve = sum / m;//求平均值
        double dVar = 0;
        for (int i = 0; i < m; i++) {//求方差
            dVar += (x[i] - dAve) * (x[i] - dAve);
        }
        return dVar / m;
    }

    /**
     * 标准差σ=sqrt(s^2)
     *
     * @param x x
     * @return 标准差
     */
    public static double standardDeviation(double[] x) {
        return Math.sqrt(variance(x));
    }

    /**
     * 平均值
     *
     * @param x x
     * @return 平均值
     */
    public static double average(double[] x) {
        int m = x.length;
        double sum = 0;
        for (int i = 0; i < m; i++) {
            sum += x[i];
        }
        double dAve = sum / m;
        return dAve;
    }

    /**
     * 朴素贝叶斯离散化
     * 将小数变成个位数，再四舍五入
     */
    public static double[][] discrete(double[][] points){
        if (points == null || points.length < 1) {
            return points;
        }
        double[][] p = new double[points.length][points[0].length];
        for (int j = 0; j < points[0].length; j++) {
            for (int i = 0; i < points.length; i++) {
                p[i][j] = cal(points[i][j]);
            }
        }
        return p;
    }

    public static double cal(double x){
        int s = 0;
        for (;x<1&&x!=0;){
            x = x*10;
        }
        if(x==0||x>=1){
            s = Math.round((float) x);
        }
        return s;
    }
}
