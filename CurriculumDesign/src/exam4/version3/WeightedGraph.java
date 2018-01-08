package exam4.version3;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class WeightedGraph<V> extends AbstractGraph<V>
{
    protected List<PriorityQueue<WeightedEdge>> queues = new ArrayList<PriorityQueue<WeightedEdge>>();

    public WeightedGraph()
    {
    }

    //根据存在数组中的边和顶点建立一个带权图
    public WeightedGraph(int[][] edges, V[] vertices)
    {
        super(edges, vertices);
        createQueues(edges, vertices.length);
    }

    public WeightedGraph(int[][] edges, int numberOfVertices)
    {
        super(edges, numberOfVertices);
        createQueues(edges, numberOfVertices);
    }

    /** Construct a WeightedGraph for vertices 0, 1, 2 and edge list */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public WeightedGraph(List<WeightedEdge> edges, List<V> vertices)
    {
        super((List) edges, vertices);
        createQueues(edges, vertices.size());
    }

    /** Construct a WeightedGraph from vertices 0, 1, and edge array */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public WeightedGraph(List<WeightedEdge> edges, int numberOfVertices)
    {
        super((List) edges, numberOfVertices);
        createQueues(edges, numberOfVertices);
    }

    //根据边创建优先邻接矩阵
    private void createQueues(int[][] edges, int numberOfVertices)
    {
        for (int i = 0; i < numberOfVertices; i++)
        {
            queues.add(new PriorityQueue<WeightedEdge>()); // Create a queue
        }

        for (int i = 0; i < edges.length; i++)
        {
            int u = edges[i][0];
            int v = edges[i][1];
            int weight = edges[i][2];
            // 向队列中插入边
            queues.get(u).offer(new WeightedEdge(u, v, weight));
        }
    }

    //根据边创建优先邻接矩阵
    private void createQueues(List<WeightedEdge> edges, int numberOfVertices)
    {
        for (int i = 0; i < numberOfVertices; i++)
        {
            queues.add(new PriorityQueue<WeightedEdge>()); // Create a queue
        }

        for (WeightedEdge edge : edges)
        {
            queues.get(edge.u).offer(edge); // 向队列中插入边
        }
    }

    /** Display edges with weights */
    //画边
    public void printWeightedEdges()
    {
        for (int i = 0; i < queues.size(); i++)
        {
            System.out.print(getVertex(i) + " (" + i + "): ");
            for (WeightedEdge edge : queues.get(i))
            {
                System.out.print("(" + edge.u + ", " + edge.v + ", "
                        + edge.weight + ") ");
            }
            System.out.println();
        }
    }

    //得到图中的边
    public List<PriorityQueue<WeightedEdge>> getWeightedEdges()
    {
        return queues;
    }

    //清空图
    public void clear()
    {
        vertices.clear();
        neighbors.clear();
        queues.clear();
    }

    //添加顶点
    public void addVertex(V vertex)
    {
        super.addVertex(vertex);
        queues.add(new PriorityQueue<WeightedEdge>());
    }

    //添加边
    public void addEdge(int u, int v, int weight)
    {
        super.addEdge(u, v);
        queues.get(u).add(new WeightedEdge(u, v, weight));
        queues.get(v).add(new WeightedEdge(v, u, weight));
    }

    //得到根为 vertex 0 的最小生成树
    public MST getMinimumSpanningTree()
    {
        return getMinimumSpanningTree(0);
    }

    //得到根为指定顶点的最小生成树
    public MST getMinimumSpanningTree(int startingIndex)
    {
        List<Integer> T = new ArrayList<Integer>();
        //T最初只包含startingIndex
        T.add(startingIndex);

        int numberOfVertices = vertices.size();
        int[] parent = new int[numberOfVertices]; // 顶点符节点
        // 初始化父节点为-1
        for (int i = 0; i < parent.length; i++)
        {
            parent[i] = -1;
        }
        double totalWeight = 0; // 整棵树的权值相当于距离

        //赋值优先队列，用来保证原始队列不变
        List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

        //是否找到所有顶点
        while (T.size() < numberOfVertices)
        {
            //寻找与T相邻顶点的最小权值边
            int v = -1;
            double smallestWeight = Double.MAX_VALUE;
            for (int u : T)
            {
                while (!queues.get(u).isEmpty()
                        && T.contains(queues.get(u).peek().v))
                {
                    // Remove the edge from queues[u] if the adjacent
                    // vertex of u is already in T
                    queues.get(u).remove();
                }

                if (queues.get(u).isEmpty())
                {
                    continue; // Consider the next vertex in T
                }

                // Current smallest weight on an edge adjacent to u
                WeightedEdge edge = queues.get(u).peek();
                if (edge.weight < smallestWeight)
                {
                    v = edge.v;
                    smallestWeight = edge.weight;
                    // If v is added to the tree, u will be its parent
                    parent[v] = u;
                }
            } // End of for

            if (v != -1)
                T.add(v); // Add a new vertex to the tree
            else
                break; // The tree is not connected, a partial MST is found

            totalWeight += smallestWeight;
        } // End of while

        return new MST(startingIndex, parent, T, totalWeight);
    }

    /** Clone an array of queues */
    private List<PriorityQueue<WeightedEdge>> deepClone(
            List<PriorityQueue<WeightedEdge>> queues)
    {
        List<PriorityQueue<WeightedEdge>> copiedQueues = new ArrayList<PriorityQueue<WeightedEdge>>();

        for (int i = 0; i < queues.size(); i++)
        {
            copiedQueues.add(new PriorityQueue<WeightedEdge>());
            for (WeightedEdge e : queues.get(i))
            {
                copiedQueues.get(i).add(e);
            }
        }

        return copiedQueues;
    }

    //最小生成树
    public class MST extends Tree
    {
        private double totalWeight; // Total weight of all edges in the tree

        public MST(int root, int[] parent, List<Integer> searchOrder,
                   double totalWeight)
        {
            super(root, parent, searchOrder);
            this.totalWeight = totalWeight;
        }

        public double getTotalWeight()
        {
            return totalWeight;
        }
    }

    /** Find single source shortest paths */
    public ShortestPathTree getShortestPath(int sourceIndex)
    {
        // T stores the vertices whose path found so far
        List<Integer> T = new ArrayList<Integer>();
        // T initially contains the sourceVertex;
        T.add(sourceIndex);

        // vertices is defined in AbstractGraph
        int numberOfVertices = vertices.size();

        // parent[v] stores the previous vertex of v in the path
        int[] parent = new int[numberOfVertices];
        parent[sourceIndex] = -1; // The parent of source is set to -1

        // costs[v] stores the cost of the path from v to the source
        double[] costs = new double[numberOfVertices];
        for (int i = 0; i < costs.length; i++)
        {
            costs[i] = Double.MAX_VALUE; // Initial cost set to infinity
        }
        costs[sourceIndex] = 0; // Cost of source is 0

        // Get a copy of queues
        List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

        // Expand T
        while (T.size() < numberOfVertices)
        {
            int v = -1; // Vertex to be determined
            double smallestCost = Double.MAX_VALUE; // Set to infinity
            for (int u : T)
            {
                while (!queues.get(u).isEmpty()
                        && T.contains(queues.get(u).peek().v))
                {
                    queues.get(u).remove(); // Remove the vertex in queue for u
                }

                if (queues.get(u).isEmpty())
                {
                    // All vertices adjacent to u are in T
                    continue;
                }

                WeightedEdge e = queues.get(u).peek();
                if (costs[u] + e.weight < smallestCost)
                {
                    v = e.v;
                    smallestCost = costs[u] + e.weight;
                    // If v is added to the tree, u will be its parent
                    parent[v] = u;
                }
            } // End of for

            T.add(v); // Add a new vertex to T
            costs[v] = smallestCost;
        } // End of while

        // Create a ShortestPathTree
        return new ShortestPathTree(sourceIndex, parent, T, costs);
    }

    /** ShortestPathTree is an inner class in WeightedGraph */
    public class ShortestPathTree extends Tree
    {
        private double[] costs; // costs[v] is the cost from v to source

        /** Construct a path */
        public ShortestPathTree(int source, int[] parent,
                                List<Integer> searchOrder, double[] costs)
        {
            super(source, parent, searchOrder);
            this.costs = costs;
        }

        /** Return the cost for a path from the root to vertex v */
        public double getCost(int v)
        {
            return costs[v];
        }

        /** Print paths from all vertices to the source */
        public void printAllPaths()
        {
            System.out.println("All shortest paths from "
                    + vertices.get(getRoot()) + " are:");
            for (int i = 0; i < costs.length; i++)
            {
                printPath(i); // Print a path from i to the source
                System.out.println("(cost: " + costs[i] + ")"); // Path cost
            }
        }
    }

    @Override
    public int[][] getAdjacencyMatrix()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
