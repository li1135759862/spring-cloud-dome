package com.example.demo;

public class Deadlock {
    static final String a ="a";
    static final String b ="b" ;

    public static void main(String[] args) {
//        for(int i=0;i<100;i++){
//////            new Thread(new Locka()).start();
//////            new Thread(new Lockb()).start();
//////        }
        while (true){
            System.out.println("循环");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
class Locka implements Runnable{

    @Override
    public void run() {
        synchronized (Deadlock.a){
            System.out.println("获得a锁");
            synchronized (Deadlock.b){
                System.out.println("获得b锁");
            }
        }
    }
}
class Lockb implements Runnable{

    @Override
    public void run() {
        synchronized (Deadlock.b){
            System.out.println("获得b锁");
            synchronized (Deadlock.a){
                System.out.println("获得a锁");
            }
        }
    }
}