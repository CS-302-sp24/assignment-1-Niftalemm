import java.io.FileWriter;
import java.io.IOException;
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
  private FileWriter newText;

  //Variables for solution 2
  private boolean eating;
  private Philosopher leftP;
  private Philosopher rightP;
  private ReentrantLock table;
  private Condition condition;
  private Random rand;
  
  private int id; // this will help us identify the philospher number

  //Solution 1 constructor
  public Philosopher(Chopstick left, Chopstick right, int nc, int tt, int et, int handed, FileWriter newText ) {
    this.left = left;
    this.right = right;
    this.nc = nc;
    this.tt = tt;
    this.et = et;
    this.handed = handed;
    this.newText = newText;
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
      eating = false; //Hopefully this makes it work
      condition.await();
    }
    eating = true;
  }
    finally {table.unlock();}
    Thread.sleep(1000);
    
}

//solution 1 thread method
public void run() {
  try {
    long threadId = this.getId() - 13;
    if(nc != 0 ){
      for(int i = 0; i < nc; i++){

        ++thinkCount;
        if (thinkCount % 10 == 0) {
          newText.write("Philosopher " + threadId + " has thought " + thinkCount + " times");
          // Think for a while
          int randTT = 0;
          if (tt != 0){
            randTT = random.nextInt(tt);
          }
          Thread.sleep(randTT);
          newText.write("Philosopher " + threadId + " thinks for " + randTT + " units");


          if(handed != 0){
            newText.write("Philosopher " + threadId + " wants left chopstick");
            synchronized(left) {                    // Grab left chopstick 
              newText.write("Philosopher " + threadId + " has left chopstick");
              synchronized(right) {                 // Grab right chopstick 
                newText.write("Philosopher " + threadId + " has right chopstick");
                int randET = 0;
                if (et != 0) {
                  randET = random.nextInt(et);
                }
                Thread.sleep(randET); // Eat for a while
                newText.write("Philosopher " + threadId + " eats for " + randET + " units");
              }
            }
            
          }
          else{
            newText.write("Philosopher " + threadId + " wants right chopstick");
            synchronized(right) {                    // Grab left chopstick 
              newText.write("Philosopher " + threadId + " has right chopstick");
              newText.write("Philosopher " + threadId + " wants left chopstick");
              synchronized(left) {                 // Grab right chopstick 
                newText.write("Philosopher " + threadId + " has left chopstick");
                int randET = 0;
                if (et != 0) {
                  randET = random.nextInt(et);
                }
                Thread.sleep(randET); // Eat for a while
                newText.write("Philosopher " + threadId + " eats for " + randET + " units");
              }
            }
            
          }
        }
      }
      
    }

    else{
      while(true){
        ++thinkCount;
        if (thinkCount % 10 == 0) {
          newText.write("Philosopher " + threadId + " has thought " + thinkCount + " times");
          // Think for a while
          int randTT = 0;
          if (tt != 0){
            randTT = random.nextInt(tt);
          }
          Thread.sleep(randTT);
          newText.write("Philosopher " + threadId + " thinks for " + randTT + " units");


          if(handed == 0){
            newText.write("Philosopher " + threadId + " wants right chopstick");
            synchronized(right) {                    // Grab left chopstick 
              synchronized(left) {                 // Grab right chopstick 
                newText.write("Philosopher " + threadId + " has left chopstick");
                int randET = 0;
                if (et != 0) {
                  randET = random.nextInt(et);
                }
                Thread.sleep(randET); // Eat for a while
                newText.write("Philosopher " + threadId + " eats for " + randET + " units");
              }
            }
          }
          else{
            newText.write("Philosopher " + threadId + " wants left chopstick");
            synchronized(left) {                    // Grab left chopstick 
              newText.write("Philosopher " + threadId + " has left chopstick");
              newText.write("Philosopher " + threadId + " wants right chopstick");
              synchronized(right) {                 // Grab right chopstick 
                newText.write("Philosopher " + threadId + " has right chopstick");
                int randET = 0;
                if (et != 0) {
                  randET = random.nextInt(et);
                }
                Thread.sleep(randET); // Eat for a while
                newText.write("Philosopher " + threadId + " eats for " + randET + " units");
              }
            }
          }
        }
      }

      


    }


      
  } catch(InterruptedException e) {} catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  
}

}

 



