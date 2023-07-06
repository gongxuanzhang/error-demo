import org.junit.jupiter.api.Test;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ErrorTest {

    @Test
    public void test(){
        Cluster instance = Cluster.getInstance();
        instance.refresh();
    }

}
