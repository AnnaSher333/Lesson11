public class Main {
    public static void main(String[] args) {
        FixedThreadPool ftp = new FixedThreadPool(3);
        for (int i = 0; i < 5; i++){
            ftp.execute(() -> System.out.println("Задания для fixedThreadPool"));
        }
        ftp.start();

        System.out.println("---------------------------");

        ScalableThredPool stp = new ScalableThredPool(2, 5);
        for (int i = 0; i < 8; i++){
            stp.execute(() -> System.out.println("Задания для ScalableThreadPool"));
        }
        stp.start();

    }

}
