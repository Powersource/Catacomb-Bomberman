package com.mojang.mojam.entity.animation;

import com.mojang.mojam.entity.Entity;
import com.mojang.mojam.entity.building.Bomb;
import com.mojang.mojam.level.tile.DestroyableWallTile;
import com.mojang.mojam.level.tile.Tile;
import com.mojang.mojam.level.tile.WallTile;
import com.mojang.mojam.network.TurnSynchronizer;
import com.mojang.mojam.screen.Screen;

public class LargeBombExplodeAnimation extends Animation {
    public LargeBombExplodeAnimation(double x, double y) {
        super(x, y, TurnSynchronizer.synchedRandom.nextInt(10) + 30); // @random
    }

    @Override
    public void tick() {
        super.tick();
        double dir = TurnSynchronizer.synchedRandom.nextDouble() * Math.PI * 2;

        int maxRadius = (32 - life * 32 / duration) + 16;

        double dist = TurnSynchronizer.synchedRandom.nextDouble() * maxRadius;

        double x = pos.x + Math.cos(dir) * dist;
        double y = pos.y + Math.sin(dir) * dist;

        if (TurnSynchronizer.synchedRandom.nextInt(duration) <= life) level.addEntity(new BombExplodeAnimation(x, y));
        else level.addEntity(new BombExplodeAnimationSmall(x, y));

        if (life == 25) {
            for (Entity e : level.getEntities(getBB().grow(4 * 32))) {
                e.bomb(this);
            }
            int xt = (int) (pos.x / Tile.WIDTH);
            int yt = (int) (pos.y / Tile.WIDTH);
            
            //this is the range for the bomb's block destroying. i think it was 2 at first
            int r = (int)Bomb.BOMB_DISTANCE/Tile.HEIGHT;

            //this new explosion is shaped like a cross
            //top 
            for (int yy = yt; yy >= yt - r; yy--) {
                try {
                    Tile t = level.getTile(xt, yy);
                    if(t instanceof WallTile && !((t instanceof DestroyableWallTile))) break;
                    t.bomb(this);
            	} catch (Exception herpderp) {
                    System.out.println("It tried to blow something up that is outside the level.");
            	}            
            }
            //bottom
            for (int yy = yt; yy <= yt + r; yy++) {
                try {
                    Tile t = level.getTile(xt, yy);
                    if(t instanceof WallTile && !((t instanceof DestroyableWallTile))) break;
                    t.bomb(this);
            	} catch (Exception herpderp) {
                    System.out.println("It tried to blow something up that is outside the level.");
            	}            
            }
            // left
            for (int xx = xt; xx >= xt - r; xx--) {
            	try {
                    Tile t = level.getTile(xx, yt);
                    if(t instanceof WallTile && !((t instanceof DestroyableWallTile))) break;
                    t.bomb(this);
                } catch (Exception herpderp) {
                    System.out.println("It tried to blow something up that is outside the level.");
            	} 
            }
            // right
            for (int xx = xt; xx <= xt + r; xx++) {
            	try {
                    Tile t = level.getTile(xx, yt);
                    if(t instanceof WallTile && !((t instanceof DestroyableWallTile))) break;
                    t.bomb(this);
                } catch (Exception herpderp) {
                    System.out.println("It tried to blow something up that is outside the level.");
            	} 
            }
            
            //this is the original square explosion
            /*for (int yy = yt - r; yy <= yt + r; yy++) {
                for (int xx = xt - r; xx <= xt + r; xx++) {
                    level.getTile(xx, yy).bomb(this);
                }
            }*/
            
            //This was commented out before I got here
            //level.bomb(pos.x, pos.y, 16);
        }
    }

    public void render(Screen screen) {
    }
}
