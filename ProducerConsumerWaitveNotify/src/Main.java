public class Main {
    // Producer Consumer problemi wait ile notifyla yazılışı

    public static void main(String[] args){
        ProducerConsumer pc = new ProducerConsumer();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                pc.produce();
            }
        }); // anonim metoduyla thread tanımladık

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                pc.consume();
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }






    }
}
