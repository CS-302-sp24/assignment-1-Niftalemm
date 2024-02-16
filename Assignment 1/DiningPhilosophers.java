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


    Philosopher[] philosophers = new Philosopher[np]; // The number of philosophers
    Chopstick[] chopsticks = new Chopstick[np]; // The number of Chopsticks
    
    for (int i = 0; i < np; ++i)
      chopsticks[i] = new Chopstick(i); //creats chopsticks
    for (int i = 0; i < np; ++i) {
      if(rl != 0){
        philosophers[i] = new Philosopher(chopsticks[i], chopsticks[(i + 1) % np], nc, tt, et, 0);
      }

      
      philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % 5]);
      philosophers[i].start(); // Philospher amount
    }
    for (int i = 0; i < np; ++i){
      philosophers[i].join();
  }
}
}

//Soultion

