package org.game.Manager;


import org.game.Fish.Fish;
import org.game.MeowPack.Meow;
import org.game.Scenes.Playing;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FishManager {
    private Playing playing;
    private Toolkit t = Toolkit.getDefaultToolkit();
    private Image fishImage = t.getImage(getClass().getResource("/Fish/fish.png"));;
    private List<Fish> listOfFish = new ArrayList<>();
    private int realTimeCounter = 0;
    private int fishHold = 1500;
    private Random random = new Random();
    private int randomTimeFishDrop = 600;
    private int fakeSize = 6000;
    private static FishManager instance;
    private FishManager(Playing playing){
        this.playing = playing;
        fishInit();
    }

    public static FishManager createFishManager(Playing playing) {
        if(instance == null) {
            instance = new FishManager(playing);
        } else {
            System.out.println("Cannot create another TileManager");
        }
        return instance;
    }
    private void fishInit(){
        for(int i = 0;i< 6000;i++){
            int randx = random.nextInt(300)+550;
            listOfFish.add(new Fish(randx,-300,70,70,400));
        }
    }

    public void fishCreation(){
        int randx = random.nextInt(fakeSize);
        listOfFish.get(randx).setY(150);
        listOfFish.get(randx).setBound(new Rectangle((int)listOfFish.get(randx).getX(),150,70,70));
        listOfFish.get(randx).setThere(true);
        fakeSize--;
    }
    public void fishCreatedByFishBucket(Meow meow){
        int randx = random.nextInt(fakeSize);
        listOfFish.get(randx).setX(meow.getX());
        listOfFish.get(randx).setY(meow.getY()-30);
        listOfFish.get(randx).setBound(new Rectangle(meow.getX(),meow.getY(),70,70));
        listOfFish.get(randx).setBoundaryDrop(meow.getY()+30);
        listOfFish.get(randx).setThere(true);
        fakeSize--;
    }
    public int getFishHold() {
        return fishHold;
    }
    public void setFishHold(int fish){
        this.fishHold = fish;
    }
    public void fishConsumed(int fishConsumed){
        fishHold -= fishConsumed;
    }
    public void frameCount(){
        realTimeCounter++;
    }
    private void collectFish(Fish fish){
        fish.setFishCLicked(true);
        fish.setDistanceTOMoveToStorage(fish.calculateDistanceMoveToStorage());
        fishHold += 25;
        fish.setCollected(true);
    }
    public void clickFish(int x, int y){
        Iterator<Fish> iterator = listOfFish.iterator();
        while (iterator.hasNext()){
            Fish fish = iterator.next();
            Rectangle rFish = fish.getBounds();
            if(rFish.contains(x,y) && !fish.isFishCLicked() && fish.isThere()){
                collectFish(fish);
            }
        }
    }


    public void removeFish(){
        Rectangle holder = new Rectangle(355,-70,90,90);
        Iterator<Fish> iterator = listOfFish.iterator();
        while (iterator.hasNext()){
            Fish fish = iterator.next();
            Rectangle rFish = new Rectangle((int)fish.getX(),(int)fish.getY(),fish.getWidth(),fish.getHeight());
            if(holder.intersects(rFish) && fish.isFishCLicked() && fish.isThere()){
                fish.setThere(false);
            }
        }
    }
    public void drawFishHolder(Graphics g){
        Graphics2D g2n = (Graphics2D) g;
        g2n.setFont(new Font("Arial",Font.BOLD,16));
        g2n.setColor(Color.BLACK);
        g2n.drawString(String.format("%d",fishHold),getAlignment(),85);
    }
    public int getAlignment(){
        int x=43;
        if(fishHold == 0){
            return x;
        } else if(fishHold < 100){
            return x-5;
        } else if(fishHold < 1000){
            return x-10;
        } else {
            return x-15;
        }
    }
    public void drawFish(Graphics g){
        drawFishHolder(g);
        Graphics2D g2d = (Graphics2D) g;
        Iterator<Fish> iterator = listOfFish.iterator();
        while (iterator.hasNext()){
            Fish fish = iterator.next();
            if(fish.isThere()){
                g2d.drawImage(fishImage,(int)fish.getX(),(int)fish.getY(),fish.getWidth(),fish.getHeight(),null);
            }
        }
    }

    public void collectAllFish(){
        Iterator<Fish> iterator = listOfFish.iterator();
        while (iterator.hasNext()){
            Fish fish = iterator.next();
            if(!fish.isCollected() && fish.isThere()){
//                Audio.sunCollected();
                collectFish(fish);
            }
            fish.moveToStorage();
        }
    }

    public void update(Playing playing){
        if(playing.isStartWaveForCD()){
            frameCount();
            if(realTimeCounter == randomTimeFishDrop){
                fishCreation();
                realTimeCounter = 0;
                randomTimeFishDrop = random.nextInt(300)+900;
            }

            Iterator<Fish> iterator= listOfFish.iterator();
            while (iterator.hasNext()){
                Fish fish = iterator.next();
                if(fish.isThere()){
                    fish.move();
                    fish.moveToStorage();
                }
            }
        } else {
            collectAllFish();
        }
        removeFish();
    }
}