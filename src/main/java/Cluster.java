import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 集群状态
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class Cluster {

    private static final Cluster INSTANCE = new Cluster();

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private List<String> list;

    public static Cluster getInstance() {
        return INSTANCE;
    }


    private Cluster() {
        list = List.of("a", "b");
        refresh();
    }


    public void refresh() {
        try {
            readWriteLock.writeLock().lock();
            if (list == null || list.isEmpty()) {
                return;
            }
            CountDownLatch latch = new CountDownLatch(list.size());
            for (String s : list) {
                CompletableFuture.runAsync(() -> {
                    System.out.println("do some thing");
                    latch.countDown();
                });
            }
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        Cluster instance = Cluster.getInstance();
        instance.list = List.of("a", "b");
        instance.refresh();
    }


}
