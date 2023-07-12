package org.example.counter;

public class RaceConditionDemo {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread t1 = new Thread(counter,"Thread-1");
        Thread t2 = new Thread(counter,"Thread-2");
        Thread t3 = new Thread(counter,"Thread-3");

        t1.start();
        t2.start();
        t3.start();
        /**
         * 실행 해보면 예상대로 1 과 0 이 찍혀야 하는데
         * 실제로는 아래와 같이 2도 찍히고 3도 찍히는 걸 볼 수 있다.
         * Value for Thread after incrementThread-3 3
         * Value for Thread after incrementThread-2 2
         * Value for Thread after incrementThread-1 1
         * Value for Thread after decrementThread-3 2
         * Value for Thread after decrementThread-2 1
         * Value for Thread after decrementThread-1 0
         * 즉 멀티스레드 환경에서 하나의 객체를 공유하면 위와 같은 문제가 생긴다.
         * */
    }
}
