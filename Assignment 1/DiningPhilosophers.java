public class DiningPhilosophers {

  public static void main(String[] args) throws InterruptedException {

    Philosopher[] philosophers = new Philosopher[5]; // The number of philosophers
    Chopstick[] chopsticks = new Chopstick[5]; // The number of Chopsticks
    
    for (int i = 0; i < 5; ++i)
      chopsticks[i] = new Chopstick(i); //creats chopsticks
    for (int i = 0; i < 5; ++i) {
      philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % 5]);
      philosophers[i].start(); // Philospher amount
    }
    for (int i = 0; i < 5; ++i){
      philosophers[i].join();
  }
}
}
