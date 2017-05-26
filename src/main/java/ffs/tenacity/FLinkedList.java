package ffs.tenacity;

import java.io.UnsupportedEncodingException;

/**
 * 单链表
 */
public class FLinkedList implements FList {
  private int size;
  private Node head;
  private Node tail;

  private class Node {
    Object data;
    Node next;

    Node(Object data) {
      this.data = data;
    }
  }

  public boolean add(Object e) {
    Node n = new Node(e);
    if (tail == null) {
      head = n;
      tail = n;
    } else {
      tail.next = n;
      tail = n;
    }
    size++;
    return true;
  }

  public boolean add(int idx, Object e) {
    if (idx > size || idx < 0) {
      throw new IndexOutOfBoundsException("index: " + idx + ", size: " + size);
    }
    if (idx == size) {
      return add(e);
    }
    Node n = new Node(e);
    if (idx == 0) {
      n.next = head.next;
      head = n;
    } else {
      Node cursor = head;
      while (idx-- > 1) {
        cursor = cursor.next;
      }
      n.next = cursor.next;
      cursor.next = n;
    }
    size++;
    return true;
  }

  public boolean remove(Object e) {
    Node cursor = head;
    Node prev = null;
    for (int i = 0; i < size; i++) {
      if (cursor.data == e) {
        if (prev == null) {
          head = cursor.next;
        } else {
          prev.next = cursor.next;
        }
        size--;
        return true;
      }
      prev = cursor;
      cursor = cursor.next;
    }
    return false;
  }

  @Override
  public Object remove(int idx) {
    Node target;
    if (idx == 0) {
      target = head;
      head = head.next;
    } else {
      Node cursor = head;
      while (idx-- > 1) {
        cursor = cursor.next;
      }
      target = cursor.next;
      cursor.next = target.next;
    }
    size--;
    return target.data;
  }

  public Object get(int idx) {
    if (idx >= size || idx < 0) {
      throw new IndexOutOfBoundsException("index: " + idx + ", size: " + size);
    }
    Node cursor = head;
    while (idx-- > 0) {
      cursor = cursor.next;
    }
    return cursor.data;
  }

  @Override
  public int indexOf(Object e) {
    Node cursor = head;
    for (int i = 0; i < size; i++, cursor = cursor.next) {
      if (cursor.data == e) {
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
    head = tail = null;
  }

  public FIterator iterator() {
    return new FLinkedListItr();
  }

  private class FLinkedListItr implements FIterator {
    private Node cursor = head;
    private int counter = 0;

    public boolean hasNext() {
      return counter != size;
    }

    public Object next() {
      Object data = cursor.data;
      cursor = cursor.next;
      counter++;
      return data;
    }
  }

  public static void main(String[] args) throws UnsupportedEncodingException {
    FLinkedList list = new FLinkedList();
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);
    list.add(2, 22);
    list.add(list.size(), 12);
    list.iterator().print();
    System.out.println(list.remove((Object)2));
    list.iterator().print();
    System.out.println(list.remove(list.size() - 1));
    list.iterator().print();
    for (int i = 0; i < list.size(); i++) {
      System.out.print(list.get(i) + " ");
    }
    System.out.println();
  }
}
