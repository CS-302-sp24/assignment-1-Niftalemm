import java.util.Random;

class Philosopher extends Thread {
  private Chopstick first, second;
  private Chopstick left, right; 
  private Random random;
  private int thinkCount;
  private int id; // this will help us identify the philospher number

  public Philosopher(int id, Chopstick left, Chopstick right) {

    if(left.getId() < right.getId()){ //Instead of holding to left and right chopsticks, we now hold on to first and second
      first = left; second = right;
    }else{
      first = right; second = left;
    }
    random = new Random();



    this.left = left; this.right = right;
    random = new Random();
    this.id = id;
  }

  private void think() throws InterruptedException {
    System.out.println("Philispher: "+ id + "is thinking for the next ");
    Thread.sleep(random.nextInt(1000)); //This will simlutate thinking time
  }

  public void run() {
    try {
      while(true) {
        ++thinkCount;
        if (thinkCount % 10 == 0)
          System.out.println("Philosopher " + this + " has thought " + thinkCount + " times");
          think(); // This will use our think method to make our philospher think 
        synchronized(left) { 
          System.out.println("Philosopher " + id + " wants left chopstick");  // Grab left chopstick 
          synchronized(right) { 
            System.out.println("Philosopher " + id + " wants right chopstick"); // Grab right chopstick 
            Thread.sleep(random.nextInt(1000)); // Eat for a while
          }
        }
      }
    } catch(InterruptedException e) {
    }
  }
}
