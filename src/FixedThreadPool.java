import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FixedThreadPool implements ThreadPool{
    //очередь для заданий
    private final Queue<Runnable> fixedQueue = new ConcurrentLinkedQueue<>();

    public FixedThreadPool(int n){
        for (int i = 0; i < n; i++){//запускаем n потоков, которые берут задания из очереди
            new Thread(this::start).start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        fixedQueue.add(runnable);//добавляем задание в конец очереди
    }

    @Override
    public void start() {
        while (!fixedQueue.isEmpty()) {//если очередь заданий не пустая
            Runnable task = fixedQueue.poll();//берем и удаляем первое задание
            if (task != null) {//если задание не пустое
                task.run();//запускаем его
            }
        }
    }
}
