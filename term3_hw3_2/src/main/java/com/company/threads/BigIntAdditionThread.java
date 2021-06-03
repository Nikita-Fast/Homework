package com.company.threads;

import com.company.auxiliaries.CarryState;
import com.company.auxiliaries.Cell;
import com.company.operations.CarryAddition;

public class BigIntAdditionThread extends MyThread<CarryState> {
    private int[] number1;
    private int[] number2;
    private int[] carries;
    private int[] result;

    public BigIntAdditionThread(int minIndex, int maxIndex, CarryState[] c, int pid,
                                Cell<CarryState>[][] sendReceiveTable, int threadsNumber,
                                int[] number1, int[] number2, int[] carries, int[] result) {
        super(minIndex, maxIndex, c, pid, sendReceiveTable, threadsNumber);
        this.number1 = number1;
        this.number2 = number2;
        this.carries = carries;
        this.result = result;
    }

    //array здесь это пустой массив c[], который сначала заполним, а потом применим на нём prefixScan
    @Override
    public void run() {
        //filling c[]
        CarryState[] array = getArray();
        int minIndex = getMinIndex();
        int maxIndex = getMaxIndex();
        for (int i = minIndex; i <= maxIndex; i++) { //в более коротком числе, в месте где цифр нет, должны быть выставлены нули
            array[i] = calculateCarryState(number1[i], number2[i]);
        }
        //do parallelPrefixScan
        doParallelPrefixScan(new CarryAddition());
        //map c[] with 1 and 0
        for (int i = minIndex; i <= maxIndex; i++) {
            carries[i + 1] = (array[i] == CarryState.C) ? 1 : 0;
        }
        //sum up
        if (isLastThread()) {
            for (int i = minIndex; i < maxIndex; i++) {
                result[i + 1] = (number1[i + 1] + number2[i + 1] + carries[i + 1]) % 10;
            }
            result[maxIndex + 1] = carries[maxIndex + 1];
        }
        else {
            for (int i = minIndex; i <= maxIndex; i++) {
                result[i + 1] = (number1[i + 1] + number2[i + 1] + carries[i + 1]) % 10;
            }
        }
        if (getPid() == 0) {
            result[0] = (number1[0] + number2[0]) % 10;
        }
    }

    private CarryState calculateCarryState(int x, int y) {
        if (x + y < 9) {
            return CarryState.N;
        }
        if (x + y == 9) {
            return CarryState.M;
        }
        if (x + y > 9) {
            return CarryState.C;
        }
        return null;
    }

}
