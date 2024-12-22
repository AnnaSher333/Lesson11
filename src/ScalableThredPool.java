import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ScalableThredPool implements ThreadPool{
    //очередь для заданий
    private final Queue<Runnable> scalableQueue = new ConcurrentLinkedQueue<>();
    //количество потоков
    private int streams;
    private int min;
    private int max;

    public ScalableThredPool(int min, int max){
        this.min = min;
        this.max = max;
        for (int i = 0; i < streams; i++) {//запускаем нужное число потоков, которые берут задания из очереди
            new Thread(this::start).start();
        }
    }

    @Override
    public void start() {
        int tasks = scalableQueue.size();
        if (tasks > max){//если заданий больше, чем max кол-во потоков
            streams = max;//используем max кол-во потоков
        } else if (max > tasks && tasks > min){//если между max и min
            streams = scalableQueue.size();//используем кол-во потоков равое кол-ву заданий
        }else {//если очередь пустая или заданий меньше min потоков
            streams = min;//используем min кол-во потоков
        }
        System.out.println("Подключено потоков: " + streams);

        while (!scalableQueue.isEmpty()) {//если очередь заданий не пустая
            Runnable task = scalableQueue.poll();//берем и удаляем первое задание
            if (task != null) {//если задание не пустое
                task.run();//запускаем его
            }
        }
    }

    @Override
    public void execute(Runnable runnable) {
        scalableQueue.add(runnable);//добавляем задание в конец очереди
    }
}
