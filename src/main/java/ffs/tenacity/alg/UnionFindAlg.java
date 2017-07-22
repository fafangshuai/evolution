package ffs.tenacity.alg;

/**
 * union-find algorithm with rank
 */
public class UnionFindAlg {
  private class Subset {
    int parent;
    int rank;
  }

  private UnionFindAlg.Subset[] subsets;

  public UnionFindAlg(int v) {
    subsets = new Subset[v];
    for (int i = 0; i < v; i++) {
      Subset subset = new Subset();
      subset.parent = i;
      subset.rank = 0;
      subsets[i] = subset;
    }
  }

  /**
   * find with path compress
   *
   * @param i index
   * @return root index
   */
  public int find(int i) {
    if (subsets[i].parent != i) {
      subsets[i].parent = find(subsets[i].parent);
    }
    return subsets[i].parent;
  }

  /**
   * union x and y
   *
   * @param x x subset
   * @param y y subset
   */
  public void union(int x, int y) {
    int xRoot = find(x);
    int yRoot = find(y);
    if (subsets[x].rank < subsets[y].rank) {
      subsets[xRoot].parent = yRoot;
    } else if (subsets[x].rank > subsets[y].rank) {
      subsets[yRoot].parent = xRoot;
    } else {
      subsets[yRoot].parent = xRoot;
      subsets[xRoot].rank += 1;
    }
  }

  public void printSet() {
    for (int i = 0; i < subsets.length; i++) {
      System.out.printf("%d\t\t", i);
    }
    System.out.println();
    for (int i = 0; i < subsets.length; i++) {
      System.out.printf("%d,%d\t\t", subsets[i].parent, subsets[i].rank);
    }
  }
}
