package TheTomatoCo.Foundation;


public class Counter{

    private int count = 0;
    public int getCount(){
        return count;
    }

    /**
     * The "synchronized" keyword is crucial here; removing it will lead to serious
     * race conditions on multiple-core computers. Having it ensures that two threads
     * can't enter this method at the same time, making sure that only one thread passes
     * through this method at a time, which ensures that the increments are handled
     * correctly.
     */
    public synchronized void incrementCount(){
        --count;
    }
}
