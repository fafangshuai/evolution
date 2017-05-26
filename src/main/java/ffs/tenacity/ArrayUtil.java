package ffs.tenacity;

import java.util.Arrays;

public class ArrayUtil {

  public static void addAt(Object[] a, int idx, Object o) {
    for (int i = a.length - 1; i > idx; i--) {
      a[i] = a[i - 1];
    }
    a[idx] = o;
  }

  public static Object removeAt(Object[] a, int idx) {
    Object o = a[idx];
    for (int i = idx; i < a.length - 1; i++) {
      a[i] = a[i + 1];
    }
    a[a.length - 1] = null;
    return o;
  }

  public static void main(String[] args) {
    Integer[] a = new Integer[10];
    for (int i = 0; i < a.length / 2; i++) {
      a[i] = i * 2 + 1;
    }
    System.out.println(Arrays.toString(a));
    addAt(a, 9, 4);
    System.out.println(Arrays.toString(a));
    System.out.println(removeAt(a, 2));
    System.out.println(Arrays.toString(a));
  }
}
