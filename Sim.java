/**
 * The driver of the simulation 
 */

public class Sim {
    /**
     * Create all components and start all of the threads
     */
    public static void main(String[] args) {

        Animation a = new Animation();
        Belt belt = new Belt(5, a);
        ShortBelt shortBelt = new ShortBelt(2, a);
        Producer producer = new Producer(belt);
        Consumer consumer = new Consumer(belt,shortBelt);
        BeltMover mover = new BeltMover(belt);
        BeltMover shortBeltMover = new BeltMover(shortBelt);
        Robot robot = new Robot(a);
        Sensor sensor = new Sensor(belt, 3, robot, a);
        Scanner scanner = new Scanner(robot, shortBelt, a);
        belt.setSensor(sensor);

        producer.start();
        mover.start();
        shortBeltMover.start();
        sensor.start();
        scanner.start();
        consumer.start();

        while (
               producer.isAlive() &&
                shortBeltMover.isAlive() &&
                sensor.isAlive() &&
                scanner.isAlive() &&
                consumer.isAlive() &&
               mover.isAlive() )
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                BaggageHandlingThread.terminate(e);
            }

        // interrupt other threads
        producer.interrupt();
        mover.interrupt();
        shortBeltMover.interrupt();
        sensor.interrupt();
        scanner.interrupt();
        consumer.interrupt();

        System.out.println("Sim terminating");
        System.out.println(BaggageHandlingThread.getTerminateException());
        System.exit(0);
    }
}
