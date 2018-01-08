package exam4.version3;

import java.util.List;

public interface Graph<V> {
    /** Return the number of vertices in the graph */
    public int getSize();

    /** Return the vertices in the graph */
    public List<V> getVertices();

    /** Return the object for the specified vertex object */
    public V getVertex(int index);

    /** Return the index for the specified vertex object */
    public int getIndex(V v);

    /** Return the neighbors of vertex with the specified index */
    public List<Integer> getNeighbors(int index);

    /** Return the adjacency matrix */
    public int[][] getAdjacencyMatrix();

    /** Print the adjacency matrix */
    public void printEdges();

    /** Obtain a depth-first search tree */
    public AbstractGraph<V>.Tree dfs(int v);

    /** Obtain a breadth-first search tree */
    public AbstractGraph<V>.Tree bfs(int v);

}
