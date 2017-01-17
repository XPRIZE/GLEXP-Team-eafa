package com.motoli.apps.allsubjects;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/2/2016.
 */

class Point {
    boolean end;
    boolean in;
    boolean start;
    float f10x;
    float f11y;

    public Point(float x2, float y2, boolean in) {
        this.start = false;
        this.end = false;
        this.in = false;
        this.f10x = x2;
        this.f11y = y2;
        this.in = in;
    }

    public Point(float x2, float y2, boolean start, boolean end, boolean in) {
        this.start = false;
        this.end = false;
        this.in = false;
        this.f10x = x2;
        this.f11y = y2;
        this.start = start;
        this.end = end;
        this.in = in;
    }

    public boolean isIn() {
        return this.in;
    }

    public String toString() {
        return this.f10x + ", " + this.f11y;
    }
}
