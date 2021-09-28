package com.example.task2;

public class Call implements Comparable<Call> {
    int floor;
    boolean way;

    public Call(int floor, boolean way) { this.floor = floor; this.way = way; }

    @Override
    public int compareTo(Call o) {
        int c = Boolean.compare(this.way, o.way);
        return c==0 ? (this.way ? this.floor - o.floor : o.floor - this.floor) : c;
    }
}
