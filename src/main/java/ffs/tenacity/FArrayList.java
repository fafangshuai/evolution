package ffs.tenacity;

/**
 * 线性表，数组实现，顺序线性结构
 */
public class FArrayList implements FList {
  private Object[] elementData;
  private int size;

  public FArrayList(int capacity) {
    this.elementData = new Object[capacity];
  }

  public boolean add(Object e) {
    if (size == elementData.length) {
      throw new RuntimeException("List is full");
    }
    elementData[size++] = e;
    return true;
  }

  public boolean add(int idx, Object e) {
    if (idx > size || idx < 0) {
      throw new IndexOutOfBoundsException("index: " + idx + ", size: " + size);
    }
    if (idx == size) {
      return add(e);
    }
    if (size == elementData.length) {
      throw new RuntimeException("List is full");
    }
    for (int i = size; i > idx; i--) {
      elementData[i] = elementData[i - 1];
    }
    elementData[idx] = e;
    size++;
    return true;
  }

  public boolean remove(Object e) {
    if (isEmpty()) {
      throw new RuntimeException("List is empty");
    }
    for (int i = 0; i < size; i++) {
      if (e == elementData[i]) {
        for (int j = i; j < size - 1; j++) {
          elementData[j] = elementData[j + 1];
        }
        size--;
        return true;
      }
    }
    return false;
  }

  @Override
  public Object remove(int idx) {
    if (isEmpty()) {
      throw new RuntimeException("List is empty");
    }
    Object e = elementData[idx];
    for (int i = idx; i < size; i++) {
      elementData[i] = elementData[i + 1];
    }
    size--;
    return e;
  }

  public Object get(int idx) {
    return elementData[idx];
  }

  @Override
  public int indexOf(Object e) {
    for (int i = 0; i < size; i++) {
      if (elementData[i] == e) {
        return i;
      }
    }
    return -1;
  }

  public boolean contains(Object e) {
    return indexOf(e) > -1;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void clear() {
    for (int i = 0; i < size; i++) {
      elementData[i] = null;
    }
    size = 0;
  }

  public FIterator iterator() {
    return new FArrayListItr();
  }

  private class FArrayListItr implements FIterator {
    private int cursor;

    public boolean hasNext() {
      return cursor != size;
    }

    public Object next() {
      return elementData[cursor++];
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < size; i++) {
      if (i != 0) {
        sb.append(", ");
      }
      sb.append(elementData[i]);
    }
    sb.append("]");
    return sb.toString();
  }

  public static void main(String[] args) {
    FList list = new FArrayList(10);
    list.add(0, 1);
    list.add(3);
    list.add(5);
    list.add(8);
    list.add(2, 6);
    list.add(2, 7);
    list.add(2, 8);
    list.add(2, 9);
    System.out.println(list);
    System.out.println(list.remove(2));
    System.out.println(list);
    list.remove(3);
    System.out.println(list);
  }
}
