package com.example.demo;

public class DailLock {

    private static final String A = "a";
    private static final String B = "b";

    public static void main(String[] args) {
        deadLock();

    }
    static void deadLock(){
        Thread t1 = new Thread(() -> {
            synchronized (A){
                try {
                    System.out.println(1);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println(e.toString());
                }
                synchronized (B){
                    System.out.println(2);
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (B){
                synchronized (A){
                    System.out.println(4);
                }
            }
        });
        Thread t3=new Thread(()->{
            while (true){
            }
        });
        t3.setName("T3");
        t1.start();
        t2.start();
        t3.start();
    }
}
