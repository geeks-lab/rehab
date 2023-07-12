package org.example.counter;

public class Counter implements Runnable{
    /**
     * 서블릿 객체는 싱글톤으로(인스턴스 하나만 생성하여 공유하는 방식) 관리해야 한다.
     * 상태를 유지(stateful)하게 설계하면 안된다.(Thread safe하지 않기 때문에)
     * stateful 하게 설계 하여 어떠한 문제점이 생기는지 직접 알아보자!
     * */

    private int count = 0;

    public void increment(){
        count++;
    }

    public void decrement(){
        count--;
    }

    public int getValue(){
        return count;
    }

    @Override
    public void run() {
        this.increment();
        System.out.println("Value for Thread after increment" + Thread.currentThread().getName() + " " + this.getValue());
        this.decrement();
        System.out.println("Value for Thread after decrement" + Thread.currentThread().getName() + " " + this.getValue());
    }
}
