package ffs.tenacity.alg;

import ffs.tenacity.FDoubleLinkedList;
import ffs.tenacity.FIterator;
import ffs.tenacity.FLinkedStack;
import ffs.tenacity.FList;

/**
 * 逆波兰四则运算
 * Reverse Polish Notation
 */
public class RPN {

  public static double calc(String expr) {
    FList infixList = convertFromStr(expr);
    FList suffixList = infixToSuffixExpr(infixList);
    System.out.printf("中缀表达式: %s\n", format(infixList));
    System.out.printf("后缀表达式: %s\n", format(suffixList));
    return calc(suffixList);
  }

  private static String format(FList list) {
    StringBuilder sb = new StringBuilder();
    FIterator itr = list.iterator();
    while (itr.hasNext()) {
      sb.append(itr.next()).append(" ");
    }
    return sb.toString();
  }

  /**
   * 字符串转换为操作符和操作数的列表
   *
   * @param s 算式
   * @return 列表
   */
  private static FList convertFromStr(String s) {
    FList expr = new FDoubleLinkedList();
    char[] a = s.toCharArray();
    int anchor = 0;
    for (int i = 0; i < a.length; i++) {
      if (isOperand(String.valueOf(a[i]))) {
        continue;
      }
      if (anchor != i) {
        expr.add(new String(a, anchor, i - anchor));
      }
      anchor = i + 1;
      expr.add(String.valueOf(a[i]));
    }
    expr.add(new String(a, anchor, a.length - anchor));
    return expr;
  }

  /**
   * 是否是操作数
   *
   * @param c 输入字符
   * @return bool
   */
  private static boolean isOperand(String c) {
    switch (c) {
      case "+":
      case "-":
      case "*":
      case "/":
      case "(":
      case ")":
        return false;
    }
    return true;
  }

  /**
   * 中缀转后缀
   * 1. 定义栈
   * 2. 遍历中缀表达式
   * - 遇到操作数直接插入后缀表达式
   * - 遇到左括号直接入栈
   * - 遇到右括号，将栈顶元素插入后缀表达式，直到将遇到的第一个左括号弹出
   * - 遇到操作符，先和栈顶操作符比较优先级，若小于栈顶操作符的优先级，则将栈顶操作符插入后缀表达式，重复该过程直到优先级大于栈顶。然后将该操作符入栈
   * 3. 输出后缀表达式
   *
   * @param infixExpr 中缀表达式列表
   * @return 后缀表达式列表
   */
  private static FList infixToSuffixExpr(FList infixExpr) {
    FList suffixExpr = new FDoubleLinkedList();
    FIterator itr = infixExpr.iterator();
    FLinkedStack stack = new FLinkedStack();
    while (itr.hasNext()) {
      String s = (String) itr.next();
      if (isOperand(s)) {
        suffixExpr.add(s);
      } else if ("(".equals(s)) {
        stack.push(s);
      } else if (")".equals(s)) {
        String top;
        while (!"(".equals(top = (String) stack.pop())) {
          suffixExpr.add(top);
        }
      } else {
        Opr opr = OprFactory.getOpr(s);
        String top;
        while (!stack.isEmpty()) {
          top = (String) stack.peek();
          Opr topOpr = OprFactory.getOpr(top);
          if (topOpr != null && topOpr.cmpPriority(opr) >= 0) {
            suffixExpr.add(stack.pop());
          } else {
            break;
          }
        }
        stack.push(s);
      }
    }
    while (!stack.isEmpty()) {
      suffixExpr.add(stack.pop());
    }
    return suffixExpr;
  }

  /**
   * 计算后缀表达式
   * 1. 输入后缀表达式
   * 2. 遍历后缀表达式
   * - 遇到的操作数直接入栈
   * - 遇到操作符，取出栈顶的两个元素进行运算，运算结果入栈
   * 3. 输出栈顶元素，即结果
   *
   * @param expr 后缀表达式
   * @return 结果
   */
  private static double calc(FList expr) {
    FIterator itr = expr.iterator();
    FLinkedStack stack = new FLinkedStack();
    while (itr.hasNext()) {
      String s = (String) itr.next();
      if (isOperand(s)) {
        stack.push(s);
      } else {
        Opr opr = OprFactory.getOpr(s);
        double y = Double.parseDouble((String) stack.pop());
        double x = Double.parseDouble((String) stack.pop());
        if (opr != null) {
          stack.push(String.valueOf(opr.calc(x, y)));
        }
      }
    }
    return Double.parseDouble((String) stack.pop());
  }

  public static void main(String[] args) {
    String s = "9+(3-1*5)*(3+10)/2";
    System.out.println(calc(s));
  }

  /**
   * 算数运算
   */
  private interface Opr {
    String id();

    int priority();

    double calc(double x, double y);

    default int cmpPriority(Opr opr) {
      return priority() - opr.priority();
    }
  }

  /**
   * 运算工厂类
   */
  private static class OprFactory {
    private static Opr addOpr = new Opr() {
      @Override
      public String id() {
        return "+";
      }

      @Override
      public int priority() {
        return 10;
      }

      @Override
      public double calc(double x, double y) {
        return x + y;
      }
    };
    private static Opr subOpr = new Opr() {
      @Override
      public String id() {
        return "-";
      }

      @Override
      public int priority() {
        return 10;
      }

      @Override
      public double calc(double x, double y) {
        return x - y;
      }
    };
    private static Opr mulOpr = new Opr() {
      @Override
      public String id() {
        return "*";
      }

      @Override
      public int priority() {
        return 20;
      }

      @Override
      public double calc(double x, double y) {
        return x * y;
      }
    };
    private static Opr divOpr = new Opr() {
      @Override
      public String id() {
        return "/";
      }

      @Override
      public int priority() {
        return 20;
      }

      @Override
      public double calc(double x, double y) {
        return x / y;
      }
    };

    private static Opr[] oprs;

    static {
      oprs = new Opr[]{addOpr, subOpr, mulOpr, divOpr};
    }

    static Opr getOpr(String id) {
      for (Opr opr : oprs) {
        if (opr.id().equals(id)) {
          return opr;
        }
      }
      return null;
    }

  }


}
