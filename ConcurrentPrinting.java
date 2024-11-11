//Ausaf
//2022F-BSE-007

public class ConcurrentPrinting {

    private static final Object lock = new Object();
    
    public static void printRollNumbers() {
        String[] rollNumbers = {
            "2022F-SE-007", "2022F-SE-008", "2022F-SE-009",
            "2022F-SE-010", "2022F-SE-011"
        };

        for (String rollNumber : rollNumbers) {
            synchronized (lock) {
                System.out.println(rollNumber);
                try {
                    lock.notify();
                    if (Thread.currentThread().isAlive()) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    System.err.println("Thread interrupted.");
                }
            }
        }
    }

    public static void printDatesOfBirth() {
        String[] datesOfBirth = {
            "01-January", "15-February", "03-March",
            "22-April", "10-May"
        };

        for (String dob : datesOfBirth) {
            synchronized (lock) {
                System.out.println(dob);
                try {
                    lock.notify();
                    if (Thread.currentThread().isAlive()) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    System.err.println("Thread interrupted.");
                }
            }
        }
    }

    public static void main(String[] args) {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                printRollNumbers();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                printDatesOfBirth();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted.");
        }

        System.out.println("\nBoth tables printed concurrently.");
    }
}
