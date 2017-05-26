package ffs.tenacity;

/**
 * 基于链表的栈
 */
public class FLinkedStack {
  private FList items;

  public FLinkedStack() {
    items = new FDoubleLinkedList();
  }

  public void push(Object e) {
    items.add(e);
  }

  public Object pop() {
    if (items.isEmpty()) {
      throw new RuntimeException("Stack is empty");
    }
    return items.remove(items.size() - 1);
  }

  public Object peek() {
    if (items.isEmpty()) {
      throw new RuntimeException("Stack is empty");
    }
    return items.get(items.size() - 1);
  }

  public int size() {
    return items.size();
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }

  public static void main(String[] args) {
    FLinkedStack stack = new FLinkedStack();
    stack.push(3);
    stack.push(4);
    stack.push(5);
    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.peek());
  }
}
