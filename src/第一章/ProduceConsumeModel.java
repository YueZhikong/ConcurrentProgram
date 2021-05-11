package 第一章;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ProduceConsumeModel {
    private static Queue<Integer> queue = new LinkedList<>();

    static class ProduceTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            while (true){
                synchronized (queue){
                    while (queue.size() == 10){
                        try {
                            queue.wait();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    int num = (int)(Math.random()*100);
                    queue.add(num);
                    System.out.println("add: "+num);
                    queue.notifyAll();
                }
            }
        }
    }

    static class ConsumeTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            while (true){
                synchronized (queue){
                    while (queue.size() == 0){
                        try {
                            queue.wait();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    int num = queue.poll();
                    System.out.println("poll: "+num);
                    queue.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(new ProduceTask());
        FutureTask<Integer> futureTask1 = new FutureTask<>(new ConsumeTask());
        new Thread(futureTask).start();
        new Thread(futureTask1).start();
    }
}
