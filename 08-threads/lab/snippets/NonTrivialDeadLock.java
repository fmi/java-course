/**
 * Complex dead-lock example.
 */
public class NonTrivialDeadLock {
  public static void main(String[] args) throws Exception {
    BankAccount ivan = new BankAccount("Ivan", 1000);
    BankAccount pesho = new BankAccount("Pesho", 1000);

    Thread t1 = new Thread() {
      public void run() {
        transfer(ivan, pesho, 100);
      };
    };

    Thread t2 = new Thread() {
      public void run() {
        transfer(pesho, ivan, 100);
      };
    };

    t1.start();
    t2.start();
    t1.join();
    t2.join();
    
    System.out.println(ivan);
    System.out.println(pesho);
  }

  // This is NOT thread safe!
  public static void transferBuggy(BankAccount source, BankAccount target, double amount) {
    synchronized (source) { 
      synchronized (target) {
        source.withdraw(amount);
        target.deposit(amount);
      }
    }
  }

  public static void transfer(BankAccount source, BankAccount target, double amount) {
    BankAccount first;
    BankAccount second;

    // The names are immutable so we can use them safely outside synchronized block
    if (source.getName().compareTo(target.getName()) == -1) {
      first = source;
      second = target;
    } else {
      first = target;
      second = source;
    }

    synchronized (first) { 
      synchronized (second) {
        source.withdraw(amount);
        target.deposit(amount);
      }
    }
  }
}
