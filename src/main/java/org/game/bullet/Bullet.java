package org.game.bullet;

import org.game.MeowPack.Shooter;
import org.game.Zombie.Zombie;
import org.game.Zombie.Zombie;
import org.game.graphic.Graphical;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class Bullet extends BulletManager {
    private Graphical graphical;
    private double speed;
    private int originalX;
    private int originalY;
    public BufferedImage bullet_1;
    private ArrayList<Bullet> bulletLists = new ArrayList<>();
    Thread gameThread;
    Bullet bullet;
    Zombie zombie;
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Bullet(Graphical graphical, int x, int y, int Dame){
        super(x,y,Dame);
        this.graphical = graphical;
        this.originalX = x;
        this.originalY = y;
        setPosition(x,y);
        getBulletImage();}

    public void getBulletImage() {
        try {
            bullet_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Bullet/wool.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void render(Graphics2D g2) {
        g2.drawImage(bullet_1, (int) this.x, this.y,graphical.getwoolWidth(), graphical.getwoolHeight(), null);
    }

    private void setLocation(double i, int y2) {
        this.x = i;
        this.y = y2;
    }
    private void move(double speed) {

        setLocation(this.x + speed, this.y);
    }

    public void bullet_update(Zombie zombie) {
        move(5);
        if (this.x >= graphical.getScreenWidth()) {
            this.x = originalX;
            this.y = originalY;
        }
        if (this.x < (zombie.getX()+graphical.getZomWidth()) & this.x > (zombie.getX() - graphical.getZomWidth())){
            this.x = originalX;
            this.y = originalY;
        }
    }
    public Rectangle getBoundary (){
        return new Rectangle((int) this.getX(), this.getY(), 5*graphical.scale, 5*graphical.scale) ;}

    public void addBullet() {
        long lastTime = System. nanoTime(); //check this moment time
        long currentTime; //the currentTime
        long timer = 0;
        while (gameThread != null){
            currentTime = System.nanoTime (); // Always update the currentTime after 1 Loop
            timer += (currentTime - lastTime);
            if (timer > 1000000000){
                bulletLists.add (bullet);}
        }
    }
}
