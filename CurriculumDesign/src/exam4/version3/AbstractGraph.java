package exam4.version3;

import java.util.ArrayList;
import java.util.List;

import java.util.*;

public abstract class AbstractGraph<V> implements Graph<V>
{
    protected List<V> vertices = new ArrayList<V>(); // 存顶点
    protected List<List<Integer>> neighbors = new ArrayList<List<Integer>>(); // 存邻居(边)

    //空的构造方法
    protected AbstractGraph()
    {
    }

    //根据存在数组中的边和顶点构造一个图
    protected AbstractGraph(int[][] edges, V[] vertices)
    {
        for (int i = 0; i < vertices.length; i++)
        {
            this.vertices.add(vertices[i]);
        }

        createAdjacencyLists(edges, vertices.length);
    }

    //根据存在list中的边和顶点构造一个图
    protected AbstractGraph(List<Edge> edges, List<V> vertices)
    {
        for (int i = 0; i < vertices.size(); i++)
        {
            this.vertices.add(vertices.get(i));
        }

        createAdjacencyLists(edges, vertices.size());
    }

    //根据存在list中的边和顶点个数构造一个顶点为0-numberOfVertices的图
    @SuppressWarnings("unchecked")
    protected AbstractGraph(List<Edge> edges, int numberOfVertices)
    {
        for (int i = 0; i < numberOfVertices; i++)
        {
            vertices.add((V) (new Integer(i))); // 顶点为{0, 1, ...}
        }
        createAdjacencyLists(edges, numberOfVertices);
    }

    //根据存在二维数组中的边构造一个顶点为0-numberOfVertices的图
    @SuppressWarnings("unchecked")
    protected AbstractGraph(int[][] edges, int numberOfVertices)
    {
        for (int i = 0; i < numberOfVertices; i++)
        {
            vertices.add((V) (new Integer(i))); // 顶点为{0, 1, ...}
        }
        createAdjacencyLists(edges, numberOfVertices);
    }

    //通过每个顶点创建一个邻接列表
    private void createAdjacencyLists(int[][] edges, int numberOfVertices)
    {
        // 将每个顶点存入列表中
        for (int i = 0; i < numberOfVertices; i++)
        {
            neighbors.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < edges.length; i++)
        {
            int u = edges[i][0];
            int v = edges[i][1];
            neighbors.get(u).add(v);  //注意顺序
        }
    }

    //通过每个顶点创建一个邻接列表
    private void createAdjacencyLists(List<Edge> edges, int numberOfVertices)
    {
        for (int i = 0; i < numberOfVertices; i++)
        {
            neighbors.add(new ArrayList<Integer>());
        }

        for (Edge edge : edges)
        {
            neighbors.get(edge.u).add(edge.v);
        }
    }

    //放回图中顶点的个数
    public int getSize()
    {
        return vertices.size();
    }

    //返回图的顶点
    public List<V> getVertices()
    {
        return vertices;
    }

    //返回指定下标的顶点
    public V getVertex(int index)
    {
        return vertices.get(index);
    }

    //返回指定顶点的下标
    public int getIndex(V v)
    {
        return vertices.indexOf(v);
    }

    //返回指定下标的邻接矩阵
    public List<Integer> getNeighbors(int index)
    {
        return neighbors.get(index);
    }

    //返回指定下标顶点的度
    public int getDegree(int v)
    {
        return neighbors.get(v).size();
    }

    //控制台打印边
    public void printEdges()
    {
        for (int u = 0; u < neighbors.size(); u++)
        {
            System.out.print(getVertex(u) + " (" + u + "): ");
            for (int j = 0; j < neighbors.get(u).size(); j++)
            {
                System.out.print("(" + u + ", " + neighbors.get(u).get(j)
                        + ") ");
            }
            System.out.println();
        }
    }

    //清空地图
    public void clear()
    {
        vertices.clear();
        neighbors.clear();
    }

    //向图中加入顶点
    public void addVertex(V vertex)
    {
        vertices.add(vertex);
        neighbors.add(new ArrayList<Integer>());
    }

    //向图中加入边
    public void addEdge(int u, int v)
    {
        neighbors.get(u).add(v);
        neighbors.get(v).add(u);
    }

    public static class Edge
    {
        public int u; // 边的起点
        public int v; // 边的终点

        public Edge(int u, int v)
        {
            this.u = u;
            this.v = v;
        }
    }

    /** To be discussed in Section 27.6 */
    //得到一个深度优先搜索树
    public Tree dfs(int v)
    {
        List<Integer> searchOrders = new ArrayList<Integer>();
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++)
        {
            parent[i] = -1; // 初始化父节点为 -1
        }

        // 标记顶点是否访问
        boolean[] isVisited = new boolean[vertices.size()];

        // 递归搜索
        dfs(v, parent, searchOrders, isVisited);

        //返回搜索树
        return new Tree(v, parent, searchOrders);
    }

    private void dfs(int v, int[] parent, List<Integer> searchOrders,
                     boolean[] isVisited)
    {
        searchOrders.add(v);  //将节点加到结果中
        isVisited[v] = true; // 标记为已访问

        for (int i : neighbors.get(v))     //i为neighbors.get(v)里面的值
        {
            if (!isVisited[i])
            {
                parent[i] = v; // 顶点i的父节点为v
                dfs(i, parent, searchOrders, isVisited);
            }
        }
    }

    /** To be discussed in Section 27.7 */
    //得到一个广度优先遍历树
    public Tree bfs(int v)
    {
        List<Integer> searchOrders = new ArrayList<Integer>();
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++)
        {
            parent[i] = -1;
        }

        java.util.LinkedList<Integer> queue = new java.util.LinkedList<Integer>();
        boolean[] isVisited = new boolean[vertices.size()];
        queue.offer(v); //将v入队
        isVisited[v] = true; // 标记为已访问

        while(!queue.isEmpty())
        {
            int u = queue.poll(); // 出队并赋值给u
            searchOrders.add(u); //放入结果
            for (int w : neighbors.get(u))
            {
                if (!isVisited[w])
                {
                    queue.offer(w);
                    parent[w] = u;
                    isVisited[w] = true;
                }
            }
        }

        return new Tree(v, parent, searchOrders);
    }

    /** To be discussed in Section 27.5 */
    public class Tree
    {
        private int root;  //树根
        protected int[] parent; // 存储每个节点的父节点
        private List<Integer> searchOrders; // 存储顺序

        public Tree(int root, int[] parent, List<Integer> searchOrders)
        {
            this.root = root;
            this.parent = parent;
            this.searchOrders = searchOrders;
        }

        //返回树根
        public int getRoot()
        {
            return root;
        }

        //返回顶点V的父节点
        public int getParent(int v)
        {
            return parent[v];
        }

        //返回搜索顺序
        public List<Integer> getSearchOrders()
        {
            return searchOrders;
        }

        //返回找到的顶点个数
        public int getNumberOfVerticesFound()
        {
            return searchOrders.size();
        }

        //返回从顶点到根的路径
        public List<V> getPath(int index)
        {
            ArrayList<V> path = new ArrayList<V>();

            do
            {
                path.add(vertices.get(index));
                index = parent[index];
            } while (index != -1);
            return path;
        }

        public void printPath(int index)
        {
            List<V> path = getPath(index);
            System.out.print("A path from " + vertices.get(root) + " to "
                    + vertices.get(index) + ": ");
            for (int i = path.size() - 1; i >= 0; i--)
                System.out.println(path.get(i) + " ");
        }

        public void printTree()
        {
            System.out.println("Root is: " + vertices.get(root));
            System.out.print("Edges: ");
            for (int i = 0; i < parent.length; i++)
            {
                if (parent[i] != -1)
                {
                    // Display an edge
                    System.out.print("(" + vertices.get(parent[i]).toString() + ", "
                            + vertices.get(i).toString() + ") ");
                }
            }
            System.out.println();
        }
    }

    //返回一个指定顶点的哈密顿路径，如果没有返回null
    public List<Integer> getHamiltonianPath(V vertex)
    {
        return getHamiltonianPath(getIndex(vertex));
    }

    //返回一个指定下标的哈密顿路径，如果没有返回null
    public List<Integer> getHamiltonianPath(int v)
    {
        // 一个从V开始的路径. (i, next[i]) 代表路径中的一条边. isVisited[i]判断i是否在当前路径中.
        int[] next = new int[getSize()];
        for (int i = 0; i < next.length; i++)
        {
            next[i] = -1; //表明没有从i中发现
        }

        boolean[] isVisited = new boolean[getSize()];

        //在哈密顿路径中的顶点存入result中
        List<Integer> result = null;

        // 加速搜索,排序每个顶点的邻接表,以便列表中的顶点是他们度递增的顺序
        for (int i = 0; i < getSize(); i++)
        {
            reorderNeigborsBasedOnDegree(neighbors.get(i));
        }

        if (getHamiltonianPath(v, next, isVisited))
        {
            result = new ArrayList<Integer>();
            int vertex = v; // Starting from v
            while (vertex != -1)
            {
                result.add(vertex); // Add vertex to the result list
                vertex = next[vertex]; // Get the next vertex in the path
            }
        }

        return result; //如果没有找到哈密顿路径返回空
    }

    /** Reorder the adjacency list in increasing order of degrees */
    private void reorderNeigborsBasedOnDegree(List<Integer> list)
    {
        for (int i = list.size() - 1; i >= 1; i--)
        {
            // Find the maximum in the list[0..i]
            int currentMaxDegree = getDegree(list.get(0));
            int currentMaxIndex = 0;

            for (int j = 1; j <= i; j++)
            {
                if (currentMaxDegree < getDegree(list.get(j)))
                {
                    currentMaxDegree = getDegree(list.get(j));
                    currentMaxIndex = j;
                }
            }

            // Swap list[i] with list[currentMaxIndex] if necessary;
            if (currentMaxIndex != i)
            {
                int temp = list.get(currentMaxIndex);
                list.set(currentMaxIndex, list.get(i));
                list.set(i, temp);
            }
        }
    }

    //返回是否所有数组中的元素都被访问
    private boolean allVisited(boolean[] isVisited)
    {
        boolean result = true;

        for (int i = 0; i < getSize(); i++)
        {
            result = result && isVisited[i];
        }

        return result;
    }

    //从v开始搜索一个哈密顿路径
    private boolean getHamiltonianPath(int v, int[] next, boolean[] isVisited)
    {
        isVisited[v] = true;

        if (allVisited(isVisited))
        {
            return true;
        }

        for (int i = 0; i < neighbors.get(v).size(); i++)
        {
            int u = neighbors.get(v).get(i);
            if (!isVisited[u] && getHamiltonianPath(u, next, isVisited))
            {
                next[v] = u; // Edge (v, u) 在路径中
                return true;
            }
        }

        isVisited[v] = false; // 回溯, v 设为没访问
        return false; //从v开始搜索不存在哈密顿路径
    }
}
