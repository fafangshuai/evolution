package ffs.tenacity.alg;

/**
 * use union-find algorithm to detect cycle in a graph
 */
public class UnionFind {
  static class Graph {
    int V, E; // V-> no. of vertices & E-> no. of edges
    Edge edges[];  // collection of all edges

    class Edge {
      int src, dest, weight;
    }

    class Subset {
      int parent, rank;
    }

    Graph(int v, int e) {
      V = v;
      E = e;
      edges = new Edge[E];
      for (int i = 0; i < E; ++i) {
        edges[i] = new Edge();
      }
    }

    int find(int[] parent, int i) {
      if (parent[i] == -1)
        return i;
      return find(parent, parent[i]);
    }

    void union(int[] parent, int x, int y) {
      int xSet = find(parent, x);
      int ySet = find(parent, y);
      parent[xSet] = ySet;
    }

    int isCycle(Graph graph) {
      int parent[] = new int[graph.V];
      for (int i = 0; i < graph.V; i++) {
        parent[i] = -1;
      }
      for (int i = 0; i < graph.E; i++) {
        int x = graph.find(parent, graph.edges[i].src);
        int y = graph.find(parent, graph.edges[i].dest);
        if (x == y)
          return 1;
        graph.union(parent, x, y);
      }
      return 0;
    }

    int find2(Subset[] subsets, int i) {
      if (subsets[i].parent != i)
        subsets[i].parent = find2(subsets, subsets[i].parent);
      return subsets[i].parent;
    }

    void union2(Subset[] subsets, int x, int y) {
      int xRoot = find2(subsets, x);
      int yRoot = find2(subsets, y);
      if (subsets[x].rank < subsets[y].rank) {
        subsets[xRoot].parent = yRoot;
      } else if (subsets[x].rank > subsets[y].rank) {
        subsets[yRoot].parent = xRoot;
      } else {
        subsets[yRoot].parent = xRoot;
        subsets[xRoot].rank++;
      }
    }

    int isCycle2(Graph graph) {
      Subset[] subsets = new Subset[graph.V];
      for (int i = 0; i < graph.V; i++) {
        Subset subset = new Subset();
        subset.parent = i;
        subset.rank = 0;
        subsets[i] = subset;
      }
      for (int i = 0; i < graph.E; i++) {
        int x = find2(subsets, graph.edges[i].src);
        int y = find2(subsets, graph.edges[i].dest);
        if (x == y)
          return 1;
        union2(subsets, x, y);
      }
      return 0;
    }

  }

  public static void main(String[] args) {
    int V = 6, E = 8;
    Graph graph = new Graph(V, E);
    graph.edges[0].src = 2;
    graph.edges[0].dest = 3;
    graph.edges[0].weight = 4;

    graph.edges[1].src = 0;
    graph.edges[1].dest = 3;
    graph.edges[1].weight = 6;

    graph.edges[2].src = 4;
    graph.edges[2].dest = 5;
    graph.edges[2].weight = 6;

    graph.edges[3].src = 3;
    graph.edges[3].dest = 5;
    graph.edges[3].weight = 7;

    graph.edges[4].src = 3;
    graph.edges[4].dest = 4;
    graph.edges[4].weight = 8;

    graph.edges[5].src = 0;
    graph.edges[5].dest = 1;
    graph.edges[5].weight = 10;

    graph.edges[6].src = 0;
    graph.edges[6].dest = 2;
    graph.edges[6].weight = 12;

    graph.edges[7].src = 1;
    graph.edges[7].dest = 5;
    graph.edges[7].weight = 15;

    System.out.println(graph.isCycle(graph));
    System.out.println(graph.isCycle2(graph));
    System.out.println();


    UnionFindAlg unionFindAlg = new UnionFindAlg(V);
    int leastEdges = V - 1;
    for (int i = 0; i <= leastEdges; i++) {
      Graph.Edge edge = graph.edges[i];
      int xRoot = unionFindAlg.find(edge.src);
      int yRoot = unionFindAlg.find(edge.dest);
      if (xRoot != yRoot) {
        System.out.printf("(%d, %d) %d\n", edge.src, edge.dest, edge.weight);
        unionFindAlg.union(xRoot, yRoot);
      }
    }
    System.out.println();
    unionFindAlg.printSet();
  }
}
