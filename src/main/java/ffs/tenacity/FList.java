package ffs.tenacity;

public interface FList {
  boolean add(Object e);

  boolean add(int idx, Object e);

  boolean remove(Object e);

  Object remove(int idx);

  Object get(int idx);

  int indexOf(Object e);

  boolean contains(Object e);

  int size();

  boolean isEmpty();

  void clear();

  FIterator iterator();
}
