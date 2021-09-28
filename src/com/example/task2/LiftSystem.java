package com.example.task2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LiftSystem {
    private int currentFloor;
    private int standbyMode;
    private final List<Call> calls = new ArrayList<>();

    private int searchCluster(){
        int floor = 1;
        // Алгоритм поиска скоплений вызовов, не реализован
        return floor;
    }

    // Алгоритм движения лифта
    private void sortCalls (List<Call> list) {
        if(list.size() < 2) { return; }

        boolean way = list.get(0).way;
        int lfloor = list.get(0).floor;
        List<Call> tmp = new ArrayList<>();

        Collections.sort(list.subList(1, list.size()));
        for(Call e : list) {
            if(e.way == way) {
                if(way) { if(e.floor >= lfloor) tmp.add(e); } else { if(e.floor <= lfloor) tmp.add(e); }
            }
        }
        for(Call e : list) { if(e.way != way) { tmp.add(e); } }
        for(Call e : list) {
            if(e.way == way) {
                if(way) { if(e.floor < lfloor) tmp.add(e); } else { if(e.floor > lfloor) tmp.add(e); }
            }
        }
        list.clear();
        list.addAll(tmp);
    }

    public LiftSystem () {
        this.currentFloor = 1;
        this.standbyMode = 0;
    }

    /**
     * @param currentFloor этаж лифта
     */
    public LiftSystem (int currentFloor) {
        this.currentFloor = currentFloor;
        this.standbyMode = 0;
    }

    /**
     * @param currentFloor этаж лифта
     * @param standbyMode режим ожидания (0 - без действия, 1 - первый этаж, 2 - поиск скоплений)
     */
    public LiftSystem (int currentFloor, int standbyMode) {
        this.currentFloor = currentFloor;
        this.standbyMode = standbyMode;
    }

    /** @return количество вызовов */
    public int getCallsSize() { return calls.size(); }

    /** @return следующий вызов */
    public Call getNextCall() {
        if (calls.size() == 0) return null;
        Call c = getAllCalls().get(0);
        calls.remove(0);
        return c;
    }

    /** @return последовательность исполнения вызовов */
    public List<Call> getAllCalls(){
        sortCalls(calls);
        List<Call> sortedCalls = calls;

        switch (standbyMode) {
            case (1) -> sortedCalls.add(new Call(currentFloor, false));
            case (2) -> {
                int st = searchCluster();
                sortedCalls.add(new Call(st, st > currentFloor));
            }
        }
        return sortedCalls;
    }

    /**
     * Вызывает лифт с этажа
     * @param floor этаж
     * @param way направление (false - вниз, true - вверх)
     */
    public void floorCall(int floor, boolean way){ calls.add(new Call(floor, way)); }
    /**
     * Вызывает лифт из кабины
     * @param floor этаж
     */
    public void insideCall(int floor) { calls.add(new Call(floor, floor>currentFloor)); }
    /**
     * Устанавливает положение лифта
     * @param floor этаж
     */
    public void sync(int floor) { this.currentFloor = floor; }
    /**
     * Устанавливает режим ожидания лифта
     * @param mode режим (0 - без действия, 1 - первый этаж, 2 - поиск скоплений)
     */
    public void setStandbyMode(int mode) { this.standbyMode = mode; }
}
