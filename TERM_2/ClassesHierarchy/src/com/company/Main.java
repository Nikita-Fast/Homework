package com.company;

import com.company.cars.Ferrari;
import com.company.cars.Jaguar;

public class Main {

    public static void main(String[] args) {
        Ferrari laFerrari = new Ferrari(949, 2, "LaFerrari", 2.4f);
        System.out.println(laFerrari.getInfo());
        Jaguar xj = new Jaguar(340, 5, "XJ", "four zone");
        System.out.println(xj.getInfo());
    }
}
