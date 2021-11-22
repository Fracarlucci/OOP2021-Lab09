package it.unibo.oop.lab.workers02;

import java.util.ArrayList;
import java.util.List;

import it.unibo.oop.lab.workers01.MultiThreadedListSumClassic.Worker;

public class MultiThreadedSumMatrix implements SumMatrix{
    
    private final int nthread;
    
    public MultiThreadedSumMatrix(final int nthread) {
        this.nthread = nthread;
        }

    private static class Worker extends Thread {
        private  List<Integer> list;
        private  int startpos;
        private  int nelem;
        private long res;
    
    
    Worker(final List<Integer> list, final int startpos, final int nelem) {
        super();
        this.list = list;
        this.startpos = startpos;
        this.nelem = nelem;
    }
    
    @Override
    public void run() {
        System.out.println("Working from position " + startpos + " to position " + (startpos + nelem - 1));
        for (int i = startpos; i < list.size() && i < startpos + nelem; i++) {
            this.res += this.list.get(i);
        }
    }

    /**
     * Returns the result of summing up the integers within the list.
     * 
     * @return the sum of every element in the array
     */
    public long getResult() {
        return this.res;
    }

}
    
    @Override
    public double sum(final double[][] matrix) {
        final int size = list.size() % nthread + list.size() / nthread;
        /*
         * Build a list of workers
         */
        final List<Worker> workers = new ArrayList<>(nthread);
        for (int start = 0; start < list.size(); start += size) {
            workers.add(new Worker(list, start, size));
        }
        /*
         * Start them
         */
        for (final Worker w: workers) {
            w.start();
        }
        /*
         * Wait for every one of them to finish. This operation is _way_ better done by
         * using barriers and latches, and the whole operation would be better done with
         * futures.
         */
        long sum = 0;
        for (final Worker w: workers) {
            try {
                w.join();
                sum += w.getResult();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
        /*
         * Return the sum
         */
        return sum;
    }
    }

}
