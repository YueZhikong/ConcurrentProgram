package 第一章;

public class MyThread extends Thread{
    public void run() {
        System.out.println("I am a child thread");
    }

    public static void main(String[] args) {
        MyThread thread = new MyThread();

        thread.start();
    }
}
