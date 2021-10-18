/**
 *
 */
package com.murdock.books.multithread;

/**
 * @author weipeng
 */
public class ABCTest {

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        // a -> b -> c -> a
        Print p1 = new Print("A", a, b);
        Print p2 = new Print("B", b, c);
        Print p3 = new Print("C", c, a);

        Thread t = new Thread(p1);
        t.start();
        t = new Thread(p2);
        t.start();
        t = new Thread(p3);
        t.start();
        synchronized (a) {
            a.notifyAll();
        }
    }

    static class Print implements Runnable {
        String content;

        Object self;

        Object next;

        public Print(String content, Object self, Object next) {
            this.content = content;
            this.self = self;
            this.next = next;
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    synchronized (self) {
                        self.wait();
                        // 如果唤醒，则开始打印自己的内容
                        System.out.print(content);
                        // 睡眠，打印输出时有效果
                        Thread.sleep(300);
                        // 通知唤醒下一个节点
                        synchronized (next) {
                            next.notify();
                        }
                    }
                } catch (InterruptedException e) {
                }
            }

        }

    }
}
