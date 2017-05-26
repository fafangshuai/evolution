package ffs.tenacity;

/**
 * 双链表
 */
public class FDoubleLinkedList implements FList {
  private int size;
  private Node head;
  private Node tail;

  private class Node {
    Object data;
    Node next;
    Node prev;

    Node(Object data) {
      this.data = data;
    }

    @Override
    public String toString() {
      return String.valueOf(data);
    }
  }

  @Override
  public boolean add(Object e) {
    addLast(e);
    return false;
  }

  private void addLast(Object e) {
    Node n = new Node(e);
    if (tail == null) {
      head = n;
      tail = n;
    } else {
      n.prev = tail;
      tail.next = n;
      tail = n;
    }
    size++;
  }

  private void addFirst(Object e) {
    Node n = new Node(e);
    if (head == null) {
      head = n;
      tail = n;
    } else {
      n.next = head;
      head.prev = n;
      head = n;
    }
    size++;
  }

  private Node findNode(int idx) {
    if (idx >= size || idx < 0) {
      throw new IndexOutOfBoundsException("index: " + idx + ", size: " + size);
    }
    Node cursor;
    if (idx < size / 2) {
      cursor = head;
      while (idx-- > 0) {
        cursor = cursor.next;
      }
    } else {
      cursor = tail;
      while (idx++ < size - 1) {
        cursor = cursor.prev;
      }
    }
    return cursor;
  }

  @Override
  public boolean add(int idx, Object e) {
    if (idx > size || idx < 0) {
      throw new IndexOutOfBoundsException("index: " + idx + ", size: " + size);
    }
    if (idx == size) {
      addLast(e);
    } else if (idx == 0) {
      addFirst(e);
    } else {
      Node t = findNode(idx);
      Node n = new Node(e);
      n.prev = t.prev;
      n.next = t;
      t.prev.next = n;
      t.prev = n;
      size++;
    }
    return true;
  }

  @Override
  public boolean remove(Object e) {
    Node cursor = head;
    for (int i = 0; i < size; i++) {
      if (cursor.data == e) {
        cursor.prev.next = cursor.next;
        cursor.next.prev = cursor.prev;
        size--;
        return true;
      }
      cursor = cursor.next;
    }
    return false;
  }

  @Override
  public Object remove(int idx) {
    if (isEmpty()) {
      throw new RuntimeException("List is empty.");
    }
    Node target;
    if (idx == 0) {
      target = head;
      if (size == 1) {
        clear();
      } else {
        head = head.next;
        head.prev = null;
      }
    } else if (idx == size - 1) {
      target = tail;
      tail = tail.prev;
      tail.next = null;
    } else {
      Node cursor = head;
      while (idx-- > 0) {
        cursor = cursor.next;
      }
      target = cursor;
      cursor.prev.next = cursor.next;
      cursor.next.prev = cursor.prev;
    }
    size--;
    return target.data;
  }

  @Override
  public Object get(int idx) {
    return findNode(idx).data;
  }

  @Override
  public int indexOf(Object e) {
    Node cursor = head;
    for (int i = 0; i < size; i++) {
      if (cursor.data == e) {
        return i;
      }
      cursor = cursor.next;
    }
    return -1;
  }

  @Override
  public boolean contains(Object e) {
    return indexOf(e) > -1;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public void clear() {
    head = tail = null;
  }

  @Override
  public FIterator iterator() {
    return new FDoubleLinkedListItr();
  }

  public FIterator reverseIterator() {
    return new FDoubleLinkedListItrReverse();
  }

  private class FDoubleLinkedListItr implements FIterator {
    private Node cursor = head;
    private int counter = 0;

    @Override
    public boolean hasNext() {
      return counter != size;
    }

    @Override
    public Object next() {
      Object data = cursor.data;
      cursor = cursor.next;
      counter++;
      return data;
    }
  }

  private class FDoubleLinkedListItrReverse implements FIterator {
    private Node cursor = tail;
    private int counter = 0;

    @Override
    public boolean hasNext() {
      return counter != size;
    }

    @Override
    public Object next() {
      Object data = cursor.data;
      cursor = cursor.prev;
      counter++;
      return data;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    Node cursor = head;
    for (int i = 0; i < size; i++) {
      if (i != 0) {
        sb.append(", ");
      }
      sb.append(cursor.data);
      cursor = cursor.next;
    }
    sb.append("]");
    return sb.toString();
  }

  public static void main(String[] args) {
    FDoubleLinkedList list = new FDoubleLinkedList();
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);
    list.add(6);
    list.iterator().print();
    list.add(0, 12);
    list.add(list.size(), 32);
    list.iterator().print();
    list.add(list.indexOf(6), 64);
    list.iterator().print();
    System.out.println(list.remove(5));
    list.iterator().print();
    list.reverseIterator().print();
    for (int i = 0; i < list.size(); i++) {
      System.out.print(list.get(i) + " ");
    }
    System.out.println();
  }
}
