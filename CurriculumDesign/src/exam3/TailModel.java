package exam3;

import exam3.publicTools.AbstractGraph;
import exam3.publicTools.UnweightedGraph;

import java.util.ArrayList;
import java.util.List;

public class TailModel {
    private int numberOfNodes = 0;  // 默认的结点数
    private static AbstractGraph.Tree tree;  // 默认的树
    private static int numberOfCoin = 0;   // 保存硬币的数量
    private static int numOfRow = 0;    // 硬币的行数
    private static int numOfColumn = 0; // 硬币的列数

    /**
     * 通过指定行和列创建一个指定的对象
     * @param row
     * @param column
     */
    public TailModel(int row, int column, String type) {
        numOfRow = row; // 初始化行
        numOfColumn = column;   // 初始话列
        numberOfCoin = numOfRow * numOfColumn;  // 得到有多少枚硬币
        this.numberOfNodes = (int)(Math.pow(2, numberOfCoin));  // 得到图中应该有多少结点

        List<AbstractGraph.Edge> edges = getEdges(type);    // 获取邻接线性表

        // 以特定的结点数和临界线性表创建一个图
        UnweightedGraph<Integer> graph = new UnweightedGraph<Integer>(edges, numberOfNodes);
        // 获取生成树
        tree = graph.bfs(numberOfNodes - 1);
    }

    /**
     * 得到邻接线性表
     * @param type  // 翻转硬币的规则
     * @return
     */
    private List<AbstractGraph.Edge> getEdges(String type) {
        List<AbstractGraph.Edge> edges = new ArrayList<AbstractGraph.Edge>();
        int v = 0;
        for (int u = 0; u < numberOfNodes; u++) {
            for (int k = 0; k < numberOfCoin; k++) {
                char[] node = getNode(u);
                if (node[k] == 'H') {
                    if (type.equals("Default")) {   // 默认的规则
                        v = getFlippedNode(node, k);
                    } else if (type.equals("Row")) {    // 每次翻转一行
                        v = getRowFlippedNode(node, k);
                    } else if (type.equals("Column")) { // 每次翻转一列
                        v = getColumnFlippedNode(node, k);
                    }else if (type.equals("RightInclined")){    // 每次翻转一个右斜线
                        v = getRightInclinedFlippedNode(node, k);
                    }else if (type.equals("LeftInclined")){ // 每次翻转一个左斜线
                        v = getLeftInclinedFlippedNode(node, k);
                    }else if (type.equals("DoubleInclined")){   // 每次翻转两个斜线
                        v = getDubbleInclinedFlippedNode(node, k);
                    }
                    edges.add(new AbstractGraph.Edge(v, u));
                }
            }
        }

        return edges;
    }

    /**
     * 翻转指定行和列的硬币
     * @param node
     * @param row
     * @param column
     */
    public static void flipCell(char[] node, int row, int column) {
        if (row >= 0 && row < numOfRow && column >= 0 && column < numOfColumn) {
            if (node[row * numOfRow + column] == 'H')
                node[row * numOfRow + column] = 'T';
            else
                node[row * numOfRow + column] = 'H';
        }
    }

    public static int getDubbleInclinedFlippedNode(char[] node, int postion) {
        int row = postion / numOfRow;
        int column = postion % numOfRow;

        flipCell(node, row, column);
        flipCell(node, row - 1, column - 1);
        flipCell(node, row + 1, column + 1);
        flipCell(node, row + 1, column - 1);
        flipCell(node, row - 1, column + 1);
        flipCell(node, row - 2, column - 2);
        flipCell(node, row + 2, column + 2);
        flipCell(node, row + 2, column - 2);
        flipCell(node, row - 2, column + 2);

        return getIndex(node);
    }

    public static int getLeftInclinedFlippedNode(char[] node, int postion) {
        int row = postion / numOfRow;
        int column = postion % numOfRow;

        flipCell(node, row, column);
        flipCell(node, row - 1, column - 1);
        flipCell(node, row + 1, column + 1);
        flipCell(node, row - 2, column - 2);
        flipCell(node, row + 2, column + 2);

        return getIndex(node);
    }

    public static int getRightInclinedFlippedNode(char[] node, int postion) {
        int row = postion / numOfRow;
        int column = postion % numOfRow;

        flipCell(node, row, column);
        flipCell(node, row + 1, column - 1);
        flipCell(node, row - 1, column + 1);
        flipCell(node, row + 2, column - 2);
        flipCell(node, row - 2, column + 2);

        return getIndex(node);
    }

    public static int getColumnFlippedNode(char[] node, int postion) {
        int row = postion / numOfRow;
        int column = postion % numOfRow;

        flipCell(node, row, column);
        flipCell(node, row + 2, column);
        flipCell(node, row - 2, column);
        flipCell(node, row + 1, column);
        flipCell(node, row - 1, column);

        return getIndex(node);
    }

    public static int getRowFlippedNode(char[] node, int postion) {
        int row = postion / numOfRow;
        int column = postion % numOfRow;

        flipCell(node, row, column);
        flipCell(node, row, column + 2);
        flipCell(node, row, column - 2);
        flipCell(node, row, column + 1);
        flipCell(node, row, column - 1);

        return getIndex(node);
    }

    public static int getFlippedNode(char[] node, int postion) {
        int row = postion / numOfRow;
        int column = postion % numOfRow;

        flipCell(node, row, column);
        flipCell(node, row + 1, column);
        flipCell(node, row - 1, column);
        flipCell(node, row, column + 1);
        flipCell(node, row, column - 1);

        return getIndex(node);
    }

    public static int getIndex(char[] node) {
        int result = 0;

        for (int i = 0; i < numberOfCoin; i++) {
            if (node[i] == 'T')
                result = result * 2 + 1;
            else
                result = result * 2 + 0;
        }

        return result;
    }

    public static char[] getNode(int index) {
        char[] result = new char[numberOfCoin];

        for (int i = 0; i < numberOfCoin; i++) {
            int digit = index % 2;
            if (digit == 0)
                result[numberOfCoin - 1 - i] = 'H';
            else
                result[numberOfCoin - 1 - i] = 'T';

            index = index / 2;
        }

        return result;
    }

    public List<Integer> getShortestPath(int nodeIndex) {
        return tree.getPath(nodeIndex);
    }
}