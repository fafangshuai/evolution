package ffs.tenacity;

/**
 * 基于链表实现的队列
 */
public class FLinkedQueue {
  private FList items;

  public FLinkedQueue() {
    items = new FDoubleLinkedList();
  }

  public void enqueue(Object e) {
    items.add(e);
  }

  public Object dequeue() {
    if (items.isEmpty()) {
      throw new RuntimeException("Queue is empty.");
    }
    return items.remove(0);
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }

  public static void main(String[] args) {
    FLinkedQueue queue = new FLinkedQueue();
    queue.enqueue("Alice");
    queue.enqueue("Bob");
    System.out.println(queue.dequeue());
    queue.enqueue("Chris");
    System.out.println(queue.dequeue());
    System.out.println(queue.dequeue());
  }
}