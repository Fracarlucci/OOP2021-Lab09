package it.unibo.oop.lab.reactivegui03;

import it.unibo.oop.lab.reactivegui02.ConcurrentGUI;

public class AnotherConcurrentGUI extends ConcurrentGUI {
    
    private static final long serialVersionUID = 1L;
    
   public AnotherConcurrentGUI(){
       super();
       final Agent1 agent1 = new Agent1();
       new Thread(agent1).start();
    }
    
    private class Agent1 implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(10_000);
                agent.stopCounting();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }            
        }
    }
}
