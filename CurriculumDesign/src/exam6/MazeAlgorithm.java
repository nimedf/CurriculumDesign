package exam6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class MazeAlgorithm {
    private int[][] maze = new int[9][8];   // 迷宫地图的二维数组的模型
    private int[][] mark = new int[9][8];   // 保存已经访问的位置
    private int endX = 8;   // 终点的横坐标
    private int endY = 7;   // 终点的纵坐标
    private int width = 9;  // 迷宫的宽度
    private int height = 8; // 迷宫的高度
    private boolean a = false;  // 递归的另一个终止条件，只要找出一条路径就OK了
    private ArrayList<String> postion = new ArrayList<String>();  // 保存一条通路的没一个位置到下一个位置的方向
    private LinkedList<Integer> map = new LinkedList<>();  // 递归中保存路径的
    private ArrayList<int[]> path = new ArrayList<int[]>(); // 保存为通路的路径

    private int[][] next = {    // 保存下一步的方向
            {1, 0},    // 向下移动一位
            {0, 1},     // 向右移动一位
            {-1, 0},    // 向上移动一位
            {0, -1}      // 向左移动一位
    };

    /**
     *  以一定的初始指初始化迷宫类
     * @param newMaze   迷宫地图的二维数组模型
     */
    public MazeAlgorithm(int[][] newMaze) {
        map.push(0);
        map.push(0);
        for (int i = 0; i < newMaze.length; i++) {
            for (int j = 0; j < newMaze[i].length; j++) {
                if (newMaze[i][j] == 1) // 如果这一个位置是障碍，就设定它已经被访问
                    mark[i][j] = 1;
                maze[i][j] = newMaze[i][j];
            }
        }
    }

    /**
     * 根据传进来的起始坐标查找到最后的可以到达终点的路径
     * @param startX
     * @param startY
     */
    public void bfsGetPath(int startX, int startY) {
        if (a){
            return;
        }
        int nextX, nextY;   // 保存下一步的、横纵坐标
        if (startX == endX && startY == endY) { // 递归的终止条件
            a = true;
            for(int i = map.size() - 1; i >= 0; i -= 2){
                nextX = map.get(i);
                nextY = map.get(i - 1);
                int[] postion = {nextX, nextY};
                path.add(postion);
            }
            return;
        }

        // 遍历四个方向,按照右，下，上，左的顺序
        for (int i = 0; i < next.length; i++) {
            nextX = startX + next[i][0];
            nextY = startY + next[i][1];
            // 判断下一步是否超出边界
            if (nextX < 0 || nextX >= width || nextY < 0 || nextY >= height) {
                continue;
            }
            // 判断下一个位置是否为通路，并且是否已经被访问
            if (maze[nextX][nextY] == 0 && mark[nextX][nextY] == 0) {
                map.push(nextX);
                map.push(nextY);
                mark[nextX][nextY] = 1; // 表明此位置已经被访问
                bfsGetPath(nextX, nextY);
                mark[nextX][nextY] = 0; //递归结束后清楚已经被访问的
                map.pop();
                map.pop();
            }
        }
    }

    /**
     * 获取走出迷宫的通路的每一步的方向，
     * 并返回一个线性表
     * @return
     */
    public ArrayList<String> getPostion() {
        for (int i = 0; i < path.size() - 1; i++) {
            int[] temp1 = path.get(i);  //  获取前后两个点
            int[] temp2 = path.get(i + 1);
            if (temp2[0] - temp1[0] == 0 && temp2[1] - temp1[1] == 1) {
                postion.add("right"); // 表示向右移动了一位
            } else if (temp2[0] - temp1[0] == 1 && temp2[1] - temp1[1] == 0) {
                postion.add("down"); // 表示向下移动了一位
            } else if (temp2[0] - temp1[0] == 0 && temp2[1] - temp1[1] == -1) {
                postion.add("left"); // 表示向左移动了一位
            } else {
                postion.add("up"); // 表示向上移动了一位
            }
        }
        return postion;
    }

    public ArrayList<int[]> getPath() {
        return path;
    }
}
