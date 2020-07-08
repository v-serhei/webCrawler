package utils;

public class SimpleHtmlParser implements Runnable {
    private String name;

    public SimpleHtmlParser(String str) {
        name = str;

    }

    @Override
    public void run() {
        //TODO stub
        System.out.println("start simple parser " + name);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end simple parser " + name);

    }
}
