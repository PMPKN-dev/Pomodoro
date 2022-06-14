package TheTomatoCo.Foundation;

public class SyncIncrement implements Runnable{
    private static final int numberOfThreads = 4;
    private static final int numberIncrements = 1000;
    private int remainingTime;
    private Counter counter;

    public SyncIncrement(Counter counter){
        this.counter = counter;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[numberOfThreads];
        Counter counter = new Counter();

        for(int i = 0; i < numberOfThreads; i++){
            threads[i] = new Thread(new SyncIncrement(counter));
            threads[i].start();
        }

        for(int i = 0; i < numberOfThreads; i++){
            threads[i].join();
        }

        System.out.println("total count = " + counter.getCount() + " vs. expected = " + (numberOfThreads+numberIncrements));
        //it will count 1000 per thread(4000) instead of 1004 total


    }
    @Override
    public void run(){
        for(int i = 0; i < numberIncrements; i++){
            counter.incrementCount();
        }
    }
}
