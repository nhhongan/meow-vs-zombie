package org.game.Zombie;


import org.game.MeowPack.Meow;

import java.awt.*;

public class Zombie {
    private int hp, dmg, type;
    private int width = 55, height = 110;
    private float spd = 0.8f;
    private boolean isCollided = false;

    public boolean isSlowed() {
        return isSlowed;
    }

    public void setSlowed(boolean slowed) {
        isSlowed = slowed;
    }

    public void setSpd(float spd) {
        this.spd = spd;
    }

    private double x, y;
    private int frameCountMove = 0;
    private double frameCDMove = 0;
    private int frameCountMoveLimit;
    private int frameCountEat = 0;
    private int frameCDEat = 0;
    private int frameCountEatLimit;
    private boolean isAlived = true;
    private boolean isDead = false;
    private Rectangle bound;
    private boolean isSlowed = false;
    public boolean isDead() {
        return isDead;
    }
    public void setDead(boolean dead) {
        isDead = dead;
    }
    public Zombie(double x, double y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.bound = new Rectangle((int)this.x+20,(int)this.y,width-20,height);
        setStatus(this.type);
        if(this.type == 0){
            frameCountMoveLimit = 51;
            frameCountEatLimit = 23;
        } else if (this.type == 1) {
            frameCountMoveLimit = 65;
            width += 10;
            height += 20;
            this.y -= 20;
        }
    }

    private void setStatus(int type) {
        switch (type) {
            case 0: //normal
                hp = 70;
                dmg = 20;
                break;
            case 1: //catear
                hp = 100;
                dmg = 20;
                break;
            case 2: //helmet
                hp = 130;
                dmg = 20;
                break;
        }
    }

    public void dead() {
        isAlived = false;
    }

    public int getHp() {
        return hp;
    }

    public int getDmg() {
        return dmg;
    }

    public float getSpd() {
        return spd;
    }

    public int getType() {
        return type;
    }

    public void setCollided(boolean collided) {
        isCollided = collided;
    }

    public boolean isAlived() {
        return isAlived;
    }
    public double X() {
        return x;
    }

    public double Y() {
        return y;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Rectangle getBound() {
        return bound;
    }

    public void move() {
        if(!isCollided){
            x -= spd;
        }
    }

    public boolean isCollided() {
        return isCollided;
    }

    public int getFrameCountEat() {
        return frameCountEat;
    }

    public void updateFrameCountMove(){
        if(!isCollided){
            frameCDMove++;
            if (frameCDMove > 20 && frameCountMove != 2){
                frameCountMove++;
                frameCDMove =0;
            } else if(frameCDMove>20 && frameCountMove == 2){
                frameCountMove = 0;
                frameCDMove = 0;
            }
        }
    }
    public void updateFrameCountEat(){
        if(isCollided){
            frameCDEat++;
            if (frameCDEat >20 && frameCountEat != 1){
                frameCountEat++;
                frameCDEat =0 ;
            } else if (frameCDEat >20 && frameCountEat ==1){
                frameCountEat =0;
                frameCDEat = 0;
            }
        } else {
            frameCountEat = 0;
        }
    }


    public int getFrameCountMove() {
        return frameCountMove;
    }

    public void attackMeow(Meow meow){
        meow.setHealthPoint(meow.getHealthPoint() - dmg);
    }
    public void defeatMeow(Meow meow){
        if(meow.getHealthPoint() <= 0){
            isCollided = false;
        }
    }

    public int getWidth() {
        return width+5;
    }

    public int getHeight() {
        return height+8;
    }

    public boolean isWalking() {
        return !isCollided;
    }

}