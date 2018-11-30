

import java.util.concurrent.atomic.AtomicInteger;

public class BankAccount {

  // You can test removing static synchronization and use AtomicInteger
  private static AtomicInteger opCount = new AtomicInteger(0);

  // Holder's name
  protected String name;

  // Balance
  protected double balance = 0;

  public BankAccount(String name, double balance) {
    this.name = name;
    this.balance = balance;
  }

  public BankAccount(String name) {
    this.name = name;
  }

  public synchronized void deposit(double amount) {
    this.balance += amount;
    incrementOpCount();
  }

  public synchronized void withdraw(double amount) {
    this.balance -= amount;
    incrementOpCount();
  }

  public String getName() {
    return name;
  }

  public synchronized static void incrementOpCount() {
    opCount.incrementAndGet();
  }

  public synchronized static int getOpCount() {
    return opCount.get();
  }

  @Override
  public String toString() {
    return name + " balance is " + balance;
  }
}