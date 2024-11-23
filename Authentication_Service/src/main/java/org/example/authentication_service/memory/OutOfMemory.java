package org.example.authentication_service.memory;

import java.util.ArrayList;
import java.util.List;

public class OutOfMemory {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        while (true){
            Integer i = 10000000;
            list.add(i);
        }
    }
}
