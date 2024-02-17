import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

  public static void main(String[] args) throws InterruptedException {

    String stringNp = args[0];  //Number of philosohpers
    String stringNc = args[1];  //Number of cycles
    String stringTt = args[2];  //Maximum thinking time
    String stringEt = args[3];  //Maximum eating time
    String stringRl = args[4];  //If 0, all right handed. If 1, then even # philosophers are right handed and odd # are left.

    //coverting to Int to make it easier
    int np = Integer.parseInt(stringNp);
    int nc = Integer.parseInt(stringNc);
    int tt = Integer.parseInt(stringTt);
    int et = Integer.parseInt(stringEt);
    int rl = Integer.parseInt(stringRl);


    // 1st Solution
    long startTime = System.currentTimeMillis();


    Philosopher[] philosophers = new Philosopher[np]; // The number of philosophers
    Chopstick[] chopsticks = new Chopstick[np]; // The number of Chopsticks
    
    for (int i = 0; i < np; ++i)
      chopsticks[i] = new Chopstick(i); //creats chopsticks
    for (int i = 0; i < np; ++i) {
      if(rl != 0){
        philosophers[i] = new Philosopher(chopsticks[i], chopsticks[(i + 1) % np], nc, tt, et, i % 2);
      }
      else{
        philosophers[i] = new Philosopher(chopsticks[i], chopsticks[(i + 1) % np], nc, tt, et, 0);
      }
      }

      
    for (int i = 0; i < np; ++i)
      philosophers[i].join();

    long endTime = System.currentTimeMillis();
    System.out.println("Time elapsed: " + (endTime - startTime) + "ms, First Solution");

    //Solution 2
    long startTimeTwo = System.currentTimeMillis();
    
    //Init arrys to the amount provided in args.
    Philosopher[] philosophersTwo = new Philosopher[np];
    ReentrantLock lockerson = new ReentrantLock();
    
    for (int i = 0; i < np; ++i)
      philosophersTwo[i] = new Philosopher(lockerson);
    for (int i = 0; i < np; ++i) {
      philosophersTwo[i].setLeft(philosophersTwo[(i + np-1) % np]);
      philosophersTwo[i].setRight(philosophersTwo[(i + 1) % np]);
      System.out.println("run?");
      philosophersTwo[i].runSolutionTwo();
      System.out.println("ran!");
    }
    for (int i = 0; i < np; ++i){
      System.out.println("join?");
      philosophersTwo[i].join();
      System.out.println("join!");
    }
    long endTimeTwo = System.currentTimeMillis();
    System.out.println("Time elapsed: " + (endTimeTwo - startTimeTwo) + "ms, First Solution");

  }
  
}
