package ffs.tenacity.alg;

import java.util.Arrays;

/**
 * KMP算法
 */
public class StringSearch {
  /**
   * 原始匹配方法
   * @param source 源
   * @param target 目标
   * @return 匹配的位置
   */
  public static int primitive(String source, String target) {
    char[] s = source.toCharArray();
    char[] t = target.toCharArray();
    boolean match = false;
    for (int i = 0; i < s.length; i++) {
      int k = i;
      for (int j = 0; j < t.length; j++) {
        if (s[k++] != t[j]) {
          match = false;
          break;
        }
        match = true;
      }
      if (match) {
        return i;
      }
    }
    return -1;
  }

  /**
   * 计算next数组：
   * - 遍历计算字符串长度为i+1的子串的next值
   * - 连续对子串的前缀和后缀进行匹配
   *   - 匹配成功继续匹配，失败时停止
   *   - 返回匹配成功的次数作为位置i的next值
   * @param target 目标串
   * @return next数组
   */
  public static int[] next(String target) {
    char[] t = target.toCharArray();
    int[] next = new int[t.length];
    for (int i = 0; i < t.length; i++) {
      // 子串前后缀长度
      int len = i;
      boolean match = true;
      while (len > 0) {
        for (int j = 0; j < len; j++) {
          if (t[j] != t[i - len + 1 + j]) {
            match = false;
            break;
          }
        }
        if (match) {
          break;
        }
        match = true;
        len--;
      }
      next[i] = len;
    }
    return next;
  }

  /**
   * KMP匹配：
   * - 遍历对比源和目标的字符，源串游标i，目标串游标j
   *   - 如果源串和目标串首字符不匹配则i++
   *   - 如果匹配了n个字符后匹配失败(j>n>0,i>0)，则i不变，j=next[n]，然后继续匹配
   * - 匹配结束，结果为 i - j
   * @param source 源
   * @param target 目标
   * @return 匹配的位置，-1为不匹配
   */
  public static int kmp(String source, String target) {
    char[] s = source.toCharArray();
    char[] t = target.toCharArray();
    int[] next = next2(target);
    int i = 0, j = 0;
    boolean match = true;
    while (i < s.length - t.length + 1) {
      while (j < t.length) {
        if (s[i] != t[j]) {
          match = false;
          if (j == 0) {
            ++i;
          } else {
            j = next[j - 1];
          }
        } else {
          i++;
          j++;
        }
      }
      if (match) {
        return i - j;
      }
      match = true;
    }
    return -1;
  }

  /**
   * next array optimize
   */
  public static int[] next2(String target) {
    char[] t = target.toCharArray();
    int[] next = new int[t.length];
    next[0] = 0;
    for (int i = 1, j = 0; i < t.length; i++) {
      if (t[i] == t[j]) {
        next[i] = ++j;
      } else {
        j = 0;
      }
    }
    return next;
  }

  /**
   * kmp optimize
   */
  public static int kmp2(String source, String target) {
    char[] s = source.toCharArray();
    char[] t = target.toCharArray();
    int[] next = next2(target);
    int i = 0, j = 0;
    while (i < s.length - t.length + 1 && j < t.length) {
      if (s[i] == t[j]) {
        ++i;
        ++j;
      } else {
        if (j == 0) {
          ++i;
        } else {
          j = next[j - 1];
        }
      }
    }
    if (j == t.length) {
      return i - t.length;
    }
    return -1;
  }

  public static void main(String[] args) {
    System.out.println(primitive("baigoogledu", "gle"));
    System.out.println(Arrays.toString(next("ABCABCAB")));
    System.out.println(Arrays.toString(next2("ABCABCAB")));
    System.out.println(kmp2("CDABAABCABCABCABADABABD", "ABCABCAB"));
  }
}
