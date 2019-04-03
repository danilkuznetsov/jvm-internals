package buffer;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
public class Buffer {

    private Integer n;
    private boolean flag  = false;

    public synchronized void pull(Integer i ) throws InterruptedException {

        while (flag){
            // this if checks if thread was waked up right
            // can we write again
            if (n!=null){
                wait();
            }
        }

        n = i;
        flag = true;
        // without notifyAll we will get deadlock if we have many threads which write
//        notify();

        // should use notifyAll
        notifyAll();
    }

    public synchronized Integer get() throws InterruptedException {
        while (!flag){
            if (n == null){
                wait();
            }
        }
        int t = n;
        n = null;
        flag = false;
        // without notifyAll we will get deadlock if we have many threads which read
//        notify();

        // should use notifyAll
        notifyAll();

        return t;
    }
}
