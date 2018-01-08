package exam5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FamerAcorssRiver
{
    public ArrayList<ArrayList<int[]>> findSolutions()
    {
        // 保存解决方案的
        ArrayList<ArrayList<int[]>> solutions = new ArrayList<ArrayList<int[]>>();
        // 保存已经处理过的
        ArrayList<int[]> haveAcess = new ArrayList<int[]>();
        // 保存状态的
        Queue<int[]> queueState = new LinkedList<int[]>();
        // 初始化状态 农夫、狼、白菜、羊 0 在南岸，1 在北岸
        int[] start = {0, 0, 0, 0};
        // 标志是否开始分支
        boolean isBranch = false;

        solutions.add(new ArrayList<int[]>());
        queueState.add(start);  // 把第一步加入队列中
        haveAcess.add(start);   // 加入队列后，表明已经访问该状态

        while (!queueState.isEmpty())
        {
            ArrayList<int[]> current = new ArrayList<int[]>();  // 记录当前的状态，好计算下一步的路径
            int size = queueState.size();
            while (!queueState.isEmpty())
            {
                int[] temp = queueState.remove();
                if (!toString(temp).equals("1111"))
                    current.add(temp);

                if (size == 1 && !isBranch) // 当没有分支时
                    solutions.get(0).add(temp);
                else if (size != 1 && !isBranch)    // 从这个开始会有分支
                {
                    solutions.add(new ArrayList<int[]>());
                    listCopy(solutions.get(0), solutions.get(1));   // 将前面相同的都复制进新的
                    solutions.get(0).add(temp);
                    int[] newTemp = queueState.remove();// 取出第二条支路
                    if (!toString(newTemp).equals("1111"))
                        current.add(newTemp);
                    solutions.get(1).add(newTemp);
                    isBranch = true;
                }else if (size != 1 && isBranch)
                {
                    solutions.get(0).add(temp);
                    int[] temp1 = queueState.remove();
                    if (!toString(temp1).equals("1111"))
                        current.add(temp1);
                    solutions.get(1).add(temp1);
                }else if (size == 1 && isBranch)
                {
                    solutions.get(0).add(temp);
                    solutions.get(1).add(temp);
                }
            }

            for (int i = 0; i < current.size(); i++)
            {
                // 获取下一步的所有情况
                Queue<int[]> next = getNext(current.get(i));
                while (!next.isEmpty())
                {
                    int[] temp = next.remove();
                    // 判断是否安全，以及是否已被访问
                    if (isSafe(temp) && !contains(haveAcess, temp))
                    {
                        queueState.add(temp);
                        haveAcess.add(temp);
                    }else if (!contains(haveAcess, temp))
                    {
                        haveAcess.add(temp);
                    }
                }
            }
        }

        return solutions;
    }

    /**
     * 复制一个list
     * @param src
     * @param pos
     * @return
     */
    private ArrayList<int[]> listCopy(ArrayList<int[]> src, ArrayList<int[]> pos)
    {
        for (int i = 0; i < src.size(); i++)
        {
            pos.add(src.get(i));
        }
        return pos;
    }

    /**
     * 判断在已经访问过的是否在此被访问
     * @param hasAcess
     * @param state
     * @return
     */
    private boolean contains(ArrayList<int[]> hasAcess, int[] state)
    {
        for (int i = 0; i < hasAcess.size(); i++)
        {
            if (toString(hasAcess.get(i)).equals(toString(state)))
                return true;
        }

        return false;
    }

    /**
     * 判断一个状态是否安全
     * 1100 羊和白菜同时在南岸
     * 1010 狼和羊同时在南岸
     * 0101 狼和羊同时在北岸
     * 0011 羊和白菜同时在北岸
     * @param state
     * @return
     */
    private boolean isSafe(int[] state)
    {
//        String temp = toString(state);
//        System.out.println(temp);
        if (state.length != 4)
        {
            System.out.println("数组长度不正确");
            return false;
        }else if ((state[1] == state[3] && state[1] != state[0])
                    || (state[2] == state[3] && state[2] != state[0]))
        {
            return false;
        }

        return true;
    }

    /**
     * 将数组转换成对应字符串
     * @param state
     * @return
     */
    private String toString(int[] state)
    {
        String result = "";
        for (int i = 0; i < state.length; i++)
        {
            result += state[i];
        }

        return result;
    }

    /**
     * 获取下一步的全部可能，不管是否安全
     * @param state
     * @return
     */
    private Queue<int[]> getNext(int[] state)
    {
        Queue<int[]> queue = new LinkedList<int[]>();
        for (int i = 0; i < state.length; i++)
        {
            // 如果物品和农夫在同一侧
            if (state[0] == state[i])
            {
                queue.add(passRiver(i, state));
            }
        }

        return queue;
    }

    /**
     * 带指定下标处的东西过河
     * @param index
     * @param state
     * @return
     */
    private int[] passRiver(int index, int[] state)
    {
        int[] newState = new int[state.length];
        System.arraycopy(state, 0, newState, 0, state.length);
        if (state[0] == 0)  // 如果农户在南岸
        {
            newState[0] = 1;
            newState[index] = 1;   // 将这个物品带到北岸
        }else   // 否则农夫在北岸
        {
            newState[0] = 0;
            newState[index] = 0;   // 将这个物品带到南岸
        }

        return newState;
    }

//    private static String describe(int[] state)
//    {
//        for (int i = 0; i < state.length; i++)
//        {
//            switch (i)
//            {
//                case 0:
//            }
//        }
//    }

    public static void main(String[] args)
    {
        FamerAcorssRiver s = new FamerAcorssRiver();
        ArrayList<ArrayList<int[]>> solutions = s.findSolutions();
        for (int i = 0; i < solutions.size(); i++)
        {
            System.out.println("方案：" + (i + 1));
            System.out.printf("%-15s%-25s%-15s\n", "步骤", "南岸", "北岸");
            for (int j = 0; j < solutions.get(i).size(); j++)
            {
                String south = "";
                String north = "";
                for (int k = 0; k < solutions.get(i).get(j).length; k++)
                {
                    switch (k)
                    {
                        case 0:
                            if (solutions.get(i).get(j)[0] == 0)
                                south += "农夫";
                            else
                                north += "农夫";
                            break;
                        case 1:
                            if (solutions.get(i).get(j)[1] == 0)
                                south += " 狼";
                            else
                                north += " 狼";
                            break;
                        case 2:
                            if (solutions.get(i).get(j)[2] == 0)
                                south += " 白菜";
                            else
                                north += " 白菜";
                            break;
                        case 3:
                            if (solutions.get(i).get(j)[3] == 0)
                                south += " 羊";
                            else
                                north += " 羊";
                            break;
                    }
                }
                String step = "第" + (j+1) + "步";
                System.out.printf("%-15s%-25s%-15s\n", step, south, north);
            }
            System.out.println();
        }
    }
}