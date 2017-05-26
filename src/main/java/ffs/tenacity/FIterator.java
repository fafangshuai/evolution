package ffs.tenacity;

public interface FIterator {

  boolean hasNext();

  Object next();

  default void print() {
    while (this.hasNext()) {
      System.out.print(this.next() + " ");
    }
    System.out.println();
  }
}
