package com.company;

import java.util.concurrent.Semaphore;

public class Link {
    private Object message = null;
    private final Object sending = new Object();
    private final Object receiving = new Object();
    private final Semaphore sent = new Semaphore(0);
    private final Semaphore received = new Semaphore(0);

    private Thread sender = null;
    private Thread receiver = null;

    public void send(Object sentMsg) { // send a message object
        if (sentMsg == null) {throw new
                NullPointerException("Null message passed to send()");
        }
        synchronized (sending) {
            if (sender == null) { // save the first thread to call receive
                sender = Thread.currentThread();
            }
            // if currentThread() is not first thread to call send, throw an exception
            if (Thread.currentThread() != sender) {
                throw new InvalidLinkUsage("Attempted to use link with multiple senders");
            }

            message = sentMsg;
            sent.release(); // signal that the message is available
            try {
                received.acquire(); // wait until the message is received
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public final void send() {// acts as a signal to the receiver
        synchronized (sending) {
            message = new Object();// send a null message
            sent.release(); // signal that message is available
            try {
                received.acquire(); // wait until the message is received
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class InvalidLinkUsage extends RuntimeException {
        public InvalidLinkUsage(String str) {
            super(str);
        }
    }

    public Object receive() { // receive an object.
        synchronized(receiving) {
            if (receiver == null) { // save the first thread to call receive
                receiver = Thread.currentThread();
            }
            // if currentThread() is not first thread to call receive, throw an exception
            if (Thread.currentThread() != receiver) {
                throw new InvalidLinkUsage("Attempted to use link with multiple receivers");
            }

            Object receivedMessage = null;
            try {
                sent.acquire(); // wait for the message to be sent
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            receivedMessage = message;
            received.release(); // signal the sender that the message has
            return receivedMessage; // been received
        }
    }
}
