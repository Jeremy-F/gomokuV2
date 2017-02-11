package fr.esiee.core;

/**
 * Created by blay on 11/02/2017.
 * Modified by blay on 11/02/2017.
 */
public class CoupleMinMax {
    private int min;

    @Override
    public String toString() {
        return "CoupleMinMax{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    private int max;

    public CoupleMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
