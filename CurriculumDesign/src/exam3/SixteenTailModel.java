package exam3;

import exam3.publicTools.*;

import java.util.*;

public class SixteenTailModel
{
    public final static int NUMBER_OF_NODES = 65536;    // 默认的顶点数
    protected AbstractGraph<Integer>.Tree tree; // 默认的树

    public SixteenTailModel()
    {
        List<AbstractGraph.Edge> edges = getEdges();

        UnweightedGraph<Integer> graph = new UnweightedGraph<Integer>(edges, NUMBER_OF_NODES);
        tree = graph.bfs(65535);
    }

    /**
     * 返回图的Edge对象的线性表
     * @return
     */
    private List<AbstractGraph.Edge> getEdges()
    {
        List<AbstractGraph.Edge> edges = new ArrayList<AbstractGraph.Edge>();

        for (int u = 0; u < NUMBER_OF_NODES; u++)
        {
            for (int k = 0; k < 16; k++)
            {
                char[] node = getNode(u);
                if (node[k] == 'H')
                {
                    int v = getFlippedNode(node, k);
                    edges.add(new AbstractGraph.Edge(v, u));
                }
            }
        }

        return edges;
    }

    /**
     * 翻转指定位置的结点并且返回被翻转结点的下标
     * @param node
     * @param postion
     * @return
     */
    public static int getFlippedNode(char[] node, int postion)
    {
        int row = postion / 4;
        int column = postion % 4;

        flipACell(node, row, column);
        flipACell(node, row - 1, column);
        flipACell(node, row + 1, column);
        flipACell(node, row, column - 1);
        flipACell(node, row, column + 1);

        return getIndex(node);
    }

    /**
     * 翻转指定行和列的硬币
     * @param node
     * @param row
     * @param colum
     */
    public static void flipACell(char[] node, int row, int colum)
    {
        if (row >= 0 && row <= 3 && colum >= 0 && colum<= 3)
        {
            if (node[row * 4 + colum] == 'H')
                node[row * 4 + colum] = 'T';
            else
                node[row * 4 + colum] = 'H';
        }
    }

    /**
     * 返回指定结点的下标
     * @param node
     * @return
     */
    public static int getIndex(char[] node)
    {
        int result = 0;

        for (int i = 0; i < 16; i++)
        {
            if (node[i] == 'T')
                result = result * 2 + 1;
            else
                result = result * 2 + 0;
        }

        return result;
    }

    /**
     * 返回指定下标的结点
     * @param index
     * @return
     */
    public static char[] getNode(int index)
    {
        char[] result = new char[16];

        for (int i = 0; i < 16; i++)
        {
            int digit = index % 2;
            if (digit == 0)
                result[15 - i] = 'H';
            else
                result[15 - i] = 'T';
            index = index / 2;
        }

        return result;
    }

    public List<Integer> getShortestPath(int nodeIndex)
    {
        return tree.getPath(nodeIndex);
    }

    /**
     * 打印结点
     * @param node
     */
    public static void printNode(char[] node)
    {
       for (int i = 0; i < 16; i++)
       {
           if (i % 4 != 3)
               System.out.print(node[i]);
           else
               System.out.println(node[i]);
       }
        System.out.println();
    }
}
