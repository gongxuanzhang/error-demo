import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 集群状态
 *
 * @author gongxuanzhangmelt@gmail.com
 **/
public class Cluster {

    private final static Cluster INSTANCE = new Cluster();

    public static Cluster getInstance() {
        return INSTANCE;
    }

    private List<String> list;


    private Cluster() {
        list = List.of("a", "b");
        refresh();
    }


    public void refresh() {
        try {
            if (list == null || list.isEmpty()) {
                return;
            }
            CountDownLatch latch = new CountDownLatch(list.size());
            ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 10, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<>(1024), new ConvenienceThreadFactory());
            for (String s : list) {
                CompletableFuture.runAsync(() -> {
                    System.out.println("do some thing");
                    latch.countDown();
                }, executor);
            }
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Cluster instance = Cluster.getInstance();
        instance.list = List.of("a", "b");
        instance.refresh();
    }


    public static class ConvenienceThreadFactory implements ThreadFactory {

        AtomicInteger index = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "方便线程-" + index.getAndIncrement());
        }
    }

}
