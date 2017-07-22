package ffs.research;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AddTitleForMd {

  public static void main(String[] args) throws IOException {
    File target = new File(args[0]);
    long s = System.currentTimeMillis();
    if (target.isDirectory()) {
      File[] files = target.listFiles((dir, name) -> name.endsWith(".md"));
      if (files == null || files.length == 0) {
        return;
      }
      ExecutorService pool = Executors.newFixedThreadPool(8);
      for (File file : files) {
        pool.execute(new InsertTask(file.getAbsolutePath(), getContent(file.getName())));
      }
      shutdownAndAwaitTermination(pool);
    } else {
      insert(target.getAbsolutePath(), 0L, getContent(target.getName()));
    }
    System.out.printf("Spend: %s ms\n", System.currentTimeMillis() - s);
  }

  private static class InsertTask implements Runnable {
    private String filePath;
    private byte[] content;
    private final long offset = 0L;

    InsertTask(String filePath, byte[] content) {
      this.filePath = filePath;
      this.content = content;
    }

    @Override
    public void run() {
      try {
        insert(filePath, offset, content);
      } catch (IOException e) {
        System.err.printf("执行出错，文件路径：%s\n", filePath);
      }
    }
  }

  /**
   * 组装内容
   *
   * @param filename 文件名
   * @return 内容字节数组
   */
  private static byte[] getContent(String filename) throws UnsupportedEncodingException {
    return ("### " + filename.substring(0, filename.lastIndexOf(".")) + "\n\n").getBytes("utf-8");
  }

  /**
   * 在指定位置插入内容 1 把指定位置之后的内容复制到临时文件，2 在指定位置插入内容，3 从临时文件把暂存的内容复制回原文件
   *
   * @param filePath 文件路径
   * @param offset   偏移
   * @param content  插入内容
   */
  private static void insert(String filePath, long offset, byte[] content) throws IOException {
    String tempFilePath = filePath + "~";
    RandomAccessFile r = new RandomAccessFile(filePath, "rw");
    RandomAccessFile rTemp = new RandomAccessFile(tempFilePath, "rw");
    long fileSize = r.length();
    FileChannel sourceChannel = r.getChannel();
    FileChannel targetChannel = rTemp.getChannel();
    sourceChannel.transferTo(offset, fileSize - offset, targetChannel);
    sourceChannel.truncate(offset);
    r.seek(offset);
    r.write(content);
    long newOffset = r.getFilePointer();
    targetChannel.position(0L);
    sourceChannel.transferFrom(targetChannel, newOffset, fileSize - offset);
    sourceChannel.close();
    targetChannel.close();
    boolean delFlag = new File(tempFilePath).delete();
    if (!delFlag) {
      System.err.printf("删除临时文件：[%s]失败" + tempFilePath);
    }
  }

  /**
   * 停止线程并等待所有任务执行完毕
   */
  private static void shutdownAndAwaitTermination(ExecutorService pool) {
    pool.shutdown();
    try {
      pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
