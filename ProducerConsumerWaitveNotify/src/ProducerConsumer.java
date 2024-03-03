import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;

public class ProducerConsumer {
    Random random = new Random();
    Object lock = new Object(); // wait ve notify için lock(anahtar) oluşturduk giriş için
    Queue<Integer> queue = new LinkedList<Integer>();
    private int limit = 10;

    //Queue'umuzu linkedlist şeklinde oluşturduk
    public void produce(){
        while (true)
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Sorun oluştu Threadimiz kesildi.");            }

        synchronized (lock){
            if (queue.size() == limit ){
                try {
                    lock.wait();
                    // bu şekilde producer threadini beklettik
                    //consumerden notify ile uyandırılması gerekiyor.
                    //metodumuzu lockumuz üzerinden çağrıyoruz
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Integer value = random.nextInt(100);  //0-100 arası rastgele sayılar üretir
            queue.offer(value); //offer metodu değer ekler
            System.out.println("Producer Üretiyor : " + value);
            lock.notify(); // güvenlik açısından uyuyan consumeri uyandırır.
        }
        }
    }
    public void consume(){
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (lock){
                if (queue.size() == 0){ //producer değer koymadıysa bekliyoruz
                    try {
                        lock.wait(); // producerin uyandırması gerekiyo
                        //anahtarı teslim ediyorum
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                Integer value = queue.poll();//queue.poll() metodu queue'dan ilk değerini alır
                System.out.println("Consumer Tüketiyor : " + value);
                System.out.println("Queue Size : " + queue.size());
                lock.notify(); // bekleyen producer varsa güvenlik açısandan uyandırıyoruz.
            }
        }
    }
}
