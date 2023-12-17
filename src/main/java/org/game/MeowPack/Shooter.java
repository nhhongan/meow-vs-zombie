package org.game.MeowPack;

import org.game.graphic.Graphical;
import org.game.bullet.Bullet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Shooter extends Meow {

    private BufferedImage image;
    private Graphical graphical;
    private Bullet[] bullets;
    private boolean isAbleToFreeze;
    // true - SnowMeow, false - ShooterMeow

    public Shooter(int meowId, String meowName, float x, float y, int width, int height, int healthPoint, int price) {
        super(meowId, meowName, x, y, width, height, healthPoint, price);

    }
    public Shooter(Graphical graphical) {
        this.graphical = graphical;
        getMeowShooterImage();
    }

    public void getMeowShooterImage(){
        try {
            meow_1 = ImageIO.read(this.getClass().getResourceAsStream("/meow/meow_shooter.png"));
        } catch (IOException e){e.printStackTrace();}
    }

    public void shoot() {

    }

    @Override
    public void render(Graphics2D g2) {
        BufferedImage image = meow_1;


        g2.drawImage(image,140,100,graphical.tileSize,graphical.tileSize,null);





    }

    @Override
    public void update() {

    }
}
