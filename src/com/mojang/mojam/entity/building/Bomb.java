package com.mojang.mojam.entity.building;

import com.mojang.mojam.MojamComponent;
import com.mojang.mojam.entity.Entity;
import com.mojang.mojam.entity.Player;
import com.mojang.mojam.entity.animation.LargeBombExplodeAnimation;
import com.mojang.mojam.entity.mob.Mob;
import com.mojang.mojam.entity.mob.Team;
import com.mojang.mojam.level.tile.Tile;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Bitmap;


public class Bomb extends Building {
	//50
    public static final double BOMB_DISTANCE = 4*Tile.HEIGHT;

    public Bomb(double x, double y) {
        super(x, y, Team.Neutral);
        setStartHealth(50);
        yOffs = 2;
        setSize(7, 7);
    }

    public void die() {
        level.addEntity(new LargeBombExplodeAnimation(pos.x, pos.y));
        MojamComponent.soundPlayer.playSound("/sound/Explosion 2.wav", (float) pos.x, (float) pos.y);

        /*Set<Entity> verticale = level.getEntitiesCross(pos.x, pos.y - BOMB_DISTANCE, pos.x, pos.y + BOMB_DISTANCE, Mob.class);
        Set<Entity> horizontale = level.getEntitiesCross(pos.x - BOMB_DISTANCE, pos.y, pos.x + BOMB_DISTANCE, pos.y, Mob.class);*/
        
        /*Object[] entities;
        verticale = verticale.toArray();
        horizontale.toArray();
        entities[] = verticale[1];*/
        
        /*for (Object e : entities) {
                ((Mob) e).hurt(this, 5);
        }*/
        
        /*Set<Entity> entities = level.getEntitiesCross(pos.x - BOMB_DISTANCE, pos.y - BOMB_DISTANCE, pos.x + BOMB_DISTANCE, pos.y + BOMB_DISTANCE, Mob.class);
        for (Entity e : entities) {
            //double distSqr = pos.distSqr(e.pos);
            //if (distSqr < (BOMB_DISTANCE * BOMB_DISTANCE)) {
                ((Mob) e).hurt(this, 5);
            //}
        }*/
        //hurts player if standing south/west/north/east of the bomb but not diagonally
        for(Entity e : level.entities ){
        	if(e instanceof Player){
        		if(e.pos.x>pos.x-(Tile.WIDTH/2) && e.pos.x<pos.x+(Tile.WIDTH/2) && e.pos.y>pos.y-BOMB_DISTANCE && e.pos.y<pos.y+BOMB_DISTANCE){
        			((Player) e).hurt(this,5);
        		}else if(e.pos.y>pos.y-(Tile.HEIGHT/2) && e.pos.y<pos.y+(Tile.HEIGHT/2) && e.pos.x>pos.x-BOMB_DISTANCE && e.pos.x<pos.x+BOMB_DISTANCE){
        			((Player) e).hurt(this,5);
        		}
        	}
        }
        
        //spawns
        
        //old square damage
        /*Set<Entity> entities = level.getEntities(pos.x - BOMB_DISTANCE, pos.y - BOMB_DISTANCE, pos.x + BOMB_DISTANCE, pos.y + BOMB_DISTANCE, Mob.class);
        for (Entity e : entities) {
            double distSqr = pos.distSqr(e.pos);
            if (distSqr < (BOMB_DISTANCE * BOMB_DISTANCE)) {
                ((Mob) e).hurt(this, 5);
            }
        }*/
    }

    @Override
    public boolean isNotFriendOf(Mob m) {
        return true;
    }

    public void tick() {
        if (health <= 0) {
            if (--hurtTime <= 0) {
                die();
                remove();
            }
            return;
        }

        super.tick();
        if (--freezeTime > 0) return;

    }

    public Bitmap getSprite() {
        return Art.bomb;
    }

    @Override
    public void hurt(Entity source, int damage) {
        super.hurt(source, damage);
//        if (health <= 0) {
//            freezeTime = 30;
//        }
    }
}
