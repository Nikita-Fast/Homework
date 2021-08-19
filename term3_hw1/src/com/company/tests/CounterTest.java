package com.company.tests;

import com.company.app.IncrementCounter;
import com.company.app.Solution;
import com.company.counters.ThreadSafeCounter;
import com.company.counters.ThreadUnsafeCounter;
import com.company.lock.BackoffLock;
import com.company.lock.PetersonLock;
import com.company.lock.TASLock;
import com.company.lock.TTASLock;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
class CounterTest {

    private final Lock lock = new ReentrantLock();
    private final ThreadUnsafeCounter threadUnsafeCounter = new ThreadUnsafeCounter();
    private final ThreadSafeCounter threadSafeCounter = new ThreadSafeCounter(lock);

    @ParameterizedTest
    @Order(1)
    @ValueSource(ints = {1,2,4,8,16})
    void threadUnsafeCounterTest(int threadsNumber) throws InterruptedException {
        int actual = Solution.incCounter(threadsNumber, threadUnsafeCounter);
        int expected = threadsNumber * IncrementCounter.ITERATIONS;
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @Order(2)
    @ValueSource(ints = {1,2,4,8,16})
    void threadSafeCounterTest(int threadsNumber) throws InterruptedException {
        int actual = Solution.incCounter(threadsNumber, threadSafeCounter);
        int expected = threadsNumber * IncrementCounter.ITERATIONS;
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @Order(3)
    @ValueSource(ints = {1,2,4,8,16})
    void testTAS(int threadsNumber) throws InterruptedException {
        ThreadSafeCounter tasCounter = new ThreadSafeCounter(new TASLock());
        int actual = Solution.incCounter(threadsNumber, tasCounter);
        int expected = threadsNumber * IncrementCounter.ITERATIONS;
    }

    @ParameterizedTest
    @Order(4)
    @ValueSource(ints = {1,2,4,8,16})
    void testTTAS(int threadsNumber) throws InterruptedException {
        ThreadSafeCounter ttasCounter = new ThreadSafeCounter(new TTASLock());
        int actual = Solution.incCounter(threadsNumber, ttasCounter);
        int expected = threadsNumber * IncrementCounter.ITERATIONS;
    }

    @ParameterizedTest
    @Order(5)
    @ValueSource(ints = {1,2,4,8,16})
    void testBackoffLock(int threadsNumber) throws InterruptedException {
        ThreadSafeCounter backoffCounter = new ThreadSafeCounter(new BackoffLock());
        int actual = Solution.incCounter(threadsNumber, backoffCounter);
        int expected = threadsNumber * IncrementCounter.ITERATIONS;
    }

    /* with 2 threads usually gets stuck at the middle of execution. Very rare the test successfully passed
       with 2 threads. If victim field in PetersonLock becomes volatile, then everything works fine */
    @ParameterizedTest
    @Order(6)
    @ValueSource(ints = {1,2})
    void petersonTest(int threadsNumber) throws InterruptedException {
        PetersonLock petersonLock = new PetersonLock();
        ThreadSafeCounter petersonCounter = new ThreadSafeCounter(petersonLock);
        int actual = Solution.incCounter(threadsNumber, petersonCounter);
        int expected = threadsNumber * IncrementCounter.ITERATIONS;
    }
}