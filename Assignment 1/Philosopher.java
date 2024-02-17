import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher extends Thread {
  //variables for solution 1
  private Chopstick left, right;
  private Random random;
  private int thinkCount;
  private int tt;
  private int et;
  private int nc;
  private int handed; //0 = right handed, 1 = left handed.
  private Chopstick first, second;

  //Variables for solution 2
  private boolean eating;
  private Philosopher leftP;
  private Philosopher rightP;
  private ReentrantLock table;
  private Condition condition;
  private Random rand;
  
  private int id; // this will help us identify the philospher number

  //Solution 1 constructor
  public Philosopher(Chopstick left, Chopstick right, int nc, int tt, int et, int handed) {
    this.left = left;
    this.right = right;
    this.nc = nc;
    this.tt = tt;
    this.et = et;
    this.handed = handed;
    random = new Random();
  }

  //solution 2 constructor
  public Philosopher(ReentrantLock table){
    eating = false;
    this.table = table;
    condition = table.newCondition();
    rand = new Random();
  }

  public void setLeft(Philosopher leftP){this.leftP = leftP;}
  public void setRight(Philosopher rightP){this.rightP = rightP;}

  public void runSolutionTwo(){

    try{
      while(true){
        think();
        eat();
      }
    }catch(InterruptedException e){}
  //eating = false;
}

private void think() throws InterruptedException{
  table.lock();
  try{
    eating = false;
    leftP.condition.signal();
    rightP.condition.signal();
  }finally {table.unlock();}
  Thread.sleep(1000);
}




private void eat() throws InterruptedException{
  table.lock();
  try{
    while(leftP.eating || rightP.eating){
      eating = false; //Hopefully this makesit work
      condition.await();
    }
    eating = true;
  }
    finally {table.unlock();}
    Thread.sleep(1000);
    //eating = false;
}


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

  /*private void think() throws InterruptedException {
    System.out.println("Philispher: "+ id + "is thinking for the next ");
    Thread.sleep(random.nextInt(1000)); //This will simlutate thinking time
  }*/

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
