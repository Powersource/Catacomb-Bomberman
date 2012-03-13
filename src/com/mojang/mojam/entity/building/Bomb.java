package com.mojang.mojam.entity.building;

import com.mojang.mojam.MojamComponent;
import com.mojang.mojam.entity.Entity;
import com.mojang.mojam.entity.Player;
import com.mojang.mojam.entity.animation.LargeBombExplodeAnimation;
import com.mojang.mojam.entity.mob.Mob;
import com.mojang.mojam.entity.mob.Team;
import com.mojang.mojam.level.tile.DestroyableWallTile;
import com.mojang.mojam.level.tile.Tile;
import com.mojang.mojam.level.tile.WallTile;
import com.mojang.mojam.screen.Art;
import com.mojang.mojam.screen.Bitmap;


public class Bomb extends Building {
	//50
    public static final double BOMB_DISTANCE = 4*Tile.HEIGHT;

    public Bomb(double x, double y) {
        super(x, y, Team.Neutral);
        setStartHealth(150);
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
        System.out.println("#entities: "+level.entities.size());
        int blax = (int) (pos.x-16)/Tile.WIDTH;
        int blay = (int) (pos.y-16)/Tile.HEIGHT;
        for(Entity e : level.entities ){
            if(e instanceof Player){
            	int[] rangeCheck = new int[4];
            	//top
            	rangeCheck[0]=(int)BOMB_DISTANCE/Tile.HEIGHT;
            	//bottom
            	rangeCheck[1]=(int)BOMB_DISTANCE/Tile.HEIGHT;
            	//left
            	rangeCheck[2]=(int)BOMB_DISTANCE/Tile.HEIGHT;
            	//right
            	rangeCheck[3]=(int)BOMB_DISTANCE/Tile.HEIGHT;
            	
            	for(int i=(int)BOMB_DISTANCE/Tile.HEIGHT; i>0; i--){
            		//checking the range to the (possible) walls
            		//top
            		if(level.getTile(blax, blay-i) instanceof WallTile && !(level.getTile(blax, blay-i) instanceof DestroyableWallTile)){
            			rangeCheck[0]=i-1;
            		}
            		//bottom
            		if(level.getTile(blax, blay+i) instanceof WallTile && !(level.getTile(blax, blay+i) instanceof DestroyableWallTile)){
            			rangeCheck[1]=i-1;
            		}
            		//left
            		if(level.getTile(blax-i, blay) instanceof WallTile && !(level.getTile(blax-i, blay) instanceof DestroyableWallTile)){
            			rangeCheck[2]=i-1;
            		}
            		//right
            		if(level.getTile(blax+i, blay) instanceof WallTile && !(level.getTile(blax+i, blay) instanceof DestroyableWallTile)){
            			rangeCheck[3]=i-1;
            		}
            	}
            	//System.out.println("top:"+rangeCheck[0]+"bottom:"+rangeCheck[1]+"left:"+rangeCheck[2]+"right:"+rangeCheck[3]);
            		//damaging
            	rangeCheck[0]*=Tile.HEIGHT;
            	rangeCheck[1]*=Tile.HEIGHT;
            	rangeCheck[2]*=Tile.HEIGHT;
            	rangeCheck[3]*=Tile.HEIGHT;
            	
            	if( 
            				// top
                            (e.pos.x>pos.x-(Tile.WIDTH/2) && e.pos.x<pos.x+(Tile.WIDTH/2) && e.pos.y>pos.y-rangeCheck[0] && e.pos.y<pos.y) ||
                            //bottom
                            (e.pos.x>pos.x-(Tile.WIDTH/2) && e.pos.x<pos.x+(Tile.WIDTH/2) && e.pos.y<pos.y+rangeCheck[1] && e.pos.y>pos.y) ||
                            //left
                            (e.pos.y>pos.y-(Tile.HEIGHT/2) && e.pos.y<pos.y+(Tile.HEIGHT/2) && e.pos.x>pos.x-rangeCheck[2] && e.pos.x<pos.x) ||
                            //right
                            (e.pos.y>pos.y-(Tile.HEIGHT/2) && e.pos.y<pos.y+(Tile.HEIGHT/2) &&  e.pos.x<pos.x+rangeCheck[3] && e.pos.x>pos.x)                   
                        ) ((Player) e).hurt(this,5);
            		
                /*if( // top
                    (e.pos.x>pos.x-(Tile.WIDTH/2) && e.pos.x<pos.x+(Tile.WIDTH/2) && e.pos.y>pos.y-BOMB_DISTANCE && e.pos.y<pos.y &&
                    (!(level.getTile(blax, blay-i) instanceof WallTile) || level.getTile(blax, blay-i) instanceof DestroyableWallTile)) ||
                    //bottom
                    (e.pos.x>pos.x-(Tile.WIDTH/2) && e.pos.x<pos.x+(Tile.WIDTH/2) && e.pos.y<pos.y+BOMB_DISTANCE && e.pos.y>pos.y &&
                    (!(level.getTile(blax, blay+i) instanceof WallTile) || level.getTile(blax, blay+i) instanceof DestroyableWallTile)) ||
                    //right
                    (e.pos.y>pos.y-(Tile.HEIGHT/2) && e.pos.y<pos.y+(Tile.HEIGHT/2) && e.pos.x>pos.x-BOMB_DISTANCE && e.pos.x<pos.x &&
                    (!(level.getTile(blax-i, blay) instanceof WallTile) || level.getTile(blax-i, blay) instanceof DestroyableWallTile)) ||
                    //left
                    (e.pos.y>pos.y-(Tile.HEIGHT/2) && e.pos.y<pos.y+(Tile.HEIGHT/2) &&  e.pos.x<pos.x+BOMB_DISTANCE && e.pos.x>pos.x &&
                    (!(level.getTile(blax+i, blay) instanceof WallTile) || level.getTile(blax+i, blay) instanceof DestroyableWallTile))                   
                ) ((Player) e).hurt(this,5);*/
            	
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
