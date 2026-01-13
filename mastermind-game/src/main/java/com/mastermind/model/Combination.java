package com.mastermind.model;

import com.mastermind.util.Constants;
import java.util.Arrays;
import java.util.Random;

public class Combination {
    private final int[] values;
    private final int size;

    public Combination(int size) {
        this.size = size;
        this.values = new int[size];
    }

    public static Combination generateSecret(int size, int colorCount) {
        Combination cmb = new Combination(size);
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            cmb.values[i] = rand.nextInt(colorCount) + 1;
        }
        return cmb;
    }

    public void setValue(int index, int value) {
        if (index >= 0 && index < size) {
            values[index] = value;
        }
    }

    public int getValue(int index) {
        return values[index];
    }

    public int getSize() {
        return size;
    }

    public int[] getValues() {
        return Arrays.copyOf(values, size);
    }

    public String toColorString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(Constants.COLOR_NAMES[values[i]]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }
}
