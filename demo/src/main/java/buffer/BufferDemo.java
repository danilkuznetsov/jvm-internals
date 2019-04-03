package buffer;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
public class BufferDemo {
    public static void main(String[] args) {
        final Buffer buf = new Buffer();

        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    buf.pull(++i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    buf.pull(++i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Integer integer = buf.get();

                    System.out.println(integer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            while (true) {
                try {
                    Integer integer = buf.get();

                    System.out.println(integer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
