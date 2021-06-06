package com.company.threads;

import com.company.auxiliaries.Cell;
import com.company.auxiliaries.Result;
import com.company.operations.Addition;
import com.company.operations.FindMin;

public class BracketsMatchingThread extends MyThread<Integer> {
    private Result<Integer> result;
    private Cell<Integer>[][] sendReceiveTable2;

    public BracketsMatchingThread(int minIndex, int maxIndex, Integer[] sharedGenericArray, int pid,
                                  Cell<Integer>[][] sendReceiveTable1, Cell<Integer>[][] sendReceiveTable2,
                                  int threadsNumber, Result<Integer> result) {
        super(minIndex, maxIndex, sharedGenericArray, pid, sendReceiveTable1, threadsNumber);
        this.result = result;
        this.sendReceiveTable2 = sendReceiveTable2;
    }

    @Override
    public void run() {
        doParallelPrefixScan(new Addition());
        //нужно убедиться что префикс скан закончится до начала параллел скана. А не выполняется ли это автоматом?
        //т.к у этого потока есть pid и та часть всего массива, которую обрабатывает именно он, так что он попадет в свою часть,
        //разумеется после выполнения метода parallelPrefScan
        //здесь может пригодиться 2 sendReceiveTable чтобы не мог поток на втором этапе(parallelScan) послать какое-то значение
        //потоку на 1-ом этапе

        //вторая таблица нужна, чтобы более быстрые потоки, которые уже завершили prefixScan и попали в метод parallelScan
        //не отправляли данные потокам, что еще выполняют метод parallelPrefixScan
        //я не уверен, что подобное пересечение вообще может произойти, но стоит подстраховаться хотя я еще и попробую
        //выполнить тесты на длинных строках
        setSendReceiveTable(sendReceiveTable2);
        doParallelScan(new FindMin(), result);
    }
}
