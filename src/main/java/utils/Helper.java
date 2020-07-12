package utils;

public class Helper {
    public static void sleepCurrentThread (int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
