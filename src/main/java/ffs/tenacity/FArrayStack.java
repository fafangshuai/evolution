package ffs.tenacity;

/**
 * 栈
 */
public class FArrayStack {
  private Object[] elements;
  private int size;

  public FArrayStack(int capacity) {
    elements = new Object[capacity];
  }

  public int size() {
    return size;
  }

  public void push(Object e) {
    if (size == elements.length) {
      throw new RuntimeException("Stack is full.");
    }
    elements[size++] = e;
  }

  public Object pop() {
    if (isEmpty()) {
      throw new RuntimeException("Stack is empty.");
    }
    return elements[--size];
  }

  public Object peek() {
    if (isEmpty()) {
      throw new RuntimeException("Stack is empty.");
    }
    return elements[size - 1];
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public static void main(String[] args) {
    FArrayStack stack = new FArrayStack(5);
    stack.push(3);
    stack.push(4);
    stack.push(5);
    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.peek());
  }
}
