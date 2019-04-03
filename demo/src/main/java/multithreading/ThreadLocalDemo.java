package multithreading;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
public class ThreadLocalDemo {

    public static void main(String[] args) {

        // simple example of dead lock
//        try {
//            Thread.currentThread().join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        // We only have one approach for stopping thread it is calling interrupt on Thread instance

        Thread thread = new Thread(
                ()->{

                    System.out.println("test");

                    new Service().put(5);
                    new Repository().print();
                }
        );

        thread.start();

    }

}

class Service {
    void put(int value){
        ThreadLocal<Integer> local = new ThreadLocal<>();
        local.set(value);
    }
}

class Repository {

    void print(){
        ThreadLocal<Integer> local = new ThreadLocal<>();
        System.out.println(local.get());
    }
}
