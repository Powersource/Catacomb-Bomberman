package com.mojang.mojam.math;

import com.mojang.mojam.level.tile.Tile;

public class Vec2 {

    public double x, y;

    public Vec2() {
        x = y = 0;
    }

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
        validate();
    }

    public Vec2 floor() {
        return new Vec2(Math.floor(x), Math.floor(y));
    }

    public boolean equals(Vec2 p) {
        return p.x == x && p.y == y;
    }

    public double distSqr(Vec2 to) {
        double xd = x - to.x;
        double yd = y - to.y;
        return xd * xd + yd * yd;
    }

    public double dist(Vec2 pos) {
        return Math.sqrt(distSqr(pos));
    }

    public Vec2 clone() {
        return new Vec2(x, y);
    }
    
    //my own that returns the middle of a tile
    public Vec2 cloneMiddleOfTile(){
    	double xA=(Math.floor(x/Tile.WIDTH)+0.5)*Tile.WIDTH;
    	double yA=(Math.floor(y/Tile.HEIGHT)+0.5)*Tile.HEIGHT;
    	System.out.println("x=" + xA + "y=" + yA);
    	return new Vec2(xA, yA);
    }

    public Vec2 add(Vec2 p) {
        return new Vec2(x + p.x, y + p.y);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
        validate();
    }

    public Vec2 sub(Vec2 p) {
        return new Vec2(x - p.x, y - p.y);
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public double dot(Vec2 v) {
        return x * v.x + y * v.y;
    }

    public void addSelf(Vec2 p) {
        x += p.x;
        y += p.y;
        validate();
    }

    public void copy(Vec2 pos) {
        this.x = pos.x;
        this.y = pos.y;
        validate();
    }

    public double lengthSqr() {
        return x * x + y * y;
    }

    public double length() {
        return Math.sqrt(lengthSqr());
    }

    public Vec2 normalizeSelf() {
        double nf = 1 / length();
        x *= nf;
        y *= nf;
        validate();
        return this;
    }

    public Vec2 rescaleSelf(double newLen) {
        double nf = newLen / length();
        x *= nf;
        y *= nf;
        validate();
        return this;
    }

    public Vec2 scale(double s) {
        return new Vec2(x * s, y * s);
    }

    public Vec2 mul(Vec2 v) {
        return new Vec2(x * v.x, y * v.y);
    }

    public void validate() {
        if (Double.isInfinite(x) || Double.isInfinite(y) || Double.isNaN(x) || Double.isNaN(y)) {
            System.out.println("Gahhh: " + toString());
        }
    }
}
