package com.mojang.mojam.entity.building;

import java.util.Set;

import com.mojang.mojam.entity.*;
import com.mojang.mojam.entity.mob.*;
import com.mojang.mojam.level.tile.Tile;
import com.mojang.mojam.screen.*;


public class Turret extends Building {

    private int delayTicks = 0;
    private int delay;
    private double radius;
    private double radiusSqr;

    private int[] upgradeRadius = new int[] {
            3 * Tile.WIDTH, 5 * Tile.WIDTH, 7 * Tile.WIDTH
    };
    private int[] upgradeDelay = new int[] {
            24, 21, 18
    };

    private int facing = 0;

    public Turret(double x, double y, int team) {
        super(x, y, team);
        setStartHealth(20);
        freezeTime = 10;

        makeUpgradeableWithCosts(new int[] {
                1000, 3000, 9000
        });
    }

    public void init() {
    }

    public void tick() {
        super.tick();
        if (--freezeTime > 0) return;
        if (--delayTicks > 0) return;

        Set<Entity> entities = level.getEntities(pos.x - radius, pos.y - radius, pos.x + radius, pos.y + radius);

        Entity closest = null;
        double closestDist = 99999999.0f;
        for (Entity e : entities) {
            if (!(e instanceof Mob)) continue;
            if ((e instanceof RailDroid)) continue;
            //min rad, bsta raden ngonsin, fixar s turreten inte skjuter p skatten
            if ((e instanceof TreasurePile)) continue;
            if (!((Mob) e).isNotFriendOf(this)) continue;
            final double dist = e.pos.distSqr(pos);
            if (dist < radiusSqr && dist < closestDist) {
                closestDist = dist;
                closest = e;
            }
        }
        if (closest == null) return;

        double invDist = 1.0 / Math.sqrt(closestDist);
        double yd = closest.pos.y - pos.y;
        double xd = closest.pos.x - pos.x;
        double angle = (Math.atan2(yd, xd) + Math.PI * 1.625);
        facing = (8 + (int) (angle / Math.PI * 4)) & 7;
        Bullet bullet = new Bullet(this, xd * invDist, yd * invDist);
        bullet.pos.y -= 10;
        level.addEntity(bullet);
        delayTicks = delay;
    }

    public Bitmap getSprite() {
        return Art.turret[facing][0];
    }

    int upgradeLevel = 0;
    int maxUpgradeLevel = 2;

    protected void upgradeComplete(int upgradeLevel) {
        delay = upgradeDelay[upgradeLevel];
        radius = upgradeRadius[upgradeLevel];
        radiusSqr = radius * radius;
    }
}
