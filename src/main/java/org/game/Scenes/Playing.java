package org.game.Scenes;

import org.game.Fish.Shark;
import org.game.Manager.*;
import org.game.bullet.Bullet;
import org.game.Component.MyButtons;
import org.game.Audio.*;
import org.game.Timer.*;

import static org.game.Scenes.GameScenes.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class Playing implements SceneMethods {
    private BulletManager bulletManager;
    private NotifManager notifManager;
    private FishManager fishManager;
    private MeowManager meowManager;
    private ButtonManager buttonManager;
    private WaveManager waveManager;
    private MouseMotionManager mouseMotionManager;
    private TileManager tileManager;
    private ZombieManager zombieManager;
    private BarManager barManager;
    private SharkManager sharkManager;
    private Toolkit t = Toolkit.getDefaultToolkit();
    private World w;
    private boolean startWave = false, callHorde = false, zombieApproaching = false;
    private boolean startWaveForCD = false;
    private int counter = 0;
    public Playing(World w) {
        this.w = w;
        initManagers();
    }
    private void initManagers() {
        buttonManager = new ButtonManager(this);
        mouseMotionManager = new MouseMotionManager(this);
        //singleton application
        notifManager = NotifManager.createNotifManager(this);
        zombieManager = ZombieManager.createZombieManager(this);
        waveManager = WaveManager.createWaveManager(this);
        barManager = BarManager.createBar(this);
        tileManager = TileManager.createTileManager(this);
        meowManager = MeowManager.createMeowManager(this);
        fishManager = FishManager.createFishManager(this);
        bulletManager = new BulletManager();
        sharkManager = new SharkManager(this);
    }

    public void mouseMove(int x, int y){
        mouseMotionManager.changeStatusToMouse(x,y,w);
        mouseMotionManager.mouseTrackMeowBar(x,y);
        mouseMotionManager.tileTrack(x,y);
    }


    @Override
    public void mouseClicked(int x, int y) {
        changeScene(x,y);
        chooseMeow(x,y);
        fishManager.clickFish(x,y);

    }

    @Override
    public void mousePressed(int x, int y) {

    }

    @Override
    public void mouseReleased(int x, int y) {
        meowManager.mouse(x, y);
        meowManager.removeMeowByBag(x,y);


    }

    public MeowManager getMeowManager() {
        return meowManager;
    }

    public BarManager getBarManager() {
        return barManager;
    }
    public MouseMotionManager getMouseMotionManager() {
        return mouseMotionManager;
    }
    public NotifManager getNotifManager() {
        return notifManager;
    }
    public TileManager getTileManager(){
        return tileManager;
    }

    public FishManager getFishManager() {
        return this.fishManager;
    }

    public SharkManager getSharkManager() {
        return sharkManager;
    }

    public ZombieManager getZombieManager() {
        return this.zombieManager;
    }
    public WaveManager getWaveManager(){  return this.waveManager;}
    public BulletManager getBulletManager(){return  this.bulletManager;}
    public boolean isStartWaveForCD() {
        return startWaveForCD;
    }
    public boolean isStartWave() {
        return startWave;
    }
    public void MousePress(){
        mouseMotionManager.returnToSelectPlantByMouse();
    }



    public void updates() {
        setupZombie();
        meowManager.update();
        barManager.update();
        fishManager.update(this);
        zombieManager.updates();
        sharkManager.update(this);
        zombieManager.ZombieCollideMeow();
        sharkManager.SharkCollideZombie(this);
        waveManager.updates();
        bulletManager.bulletCollideZombie(this);
        bulletManager.update(this);
        zombieAppear();
    }
    public void setStartWaveForCD(boolean startWaveForCD) {
        this.startWaveForCD = startWaveForCD;
    }
    private void startGame() {
        startWave = true;
        callHorde = false;
        startWaveForCD = true;
        meowManager.setSelected(false);
        meowManager.setForbidden(false);
        System.out.println("start");
        waveManager.readyNewWave();
        notifManager.reset();
    }
    private boolean isTimeForNewZombie() {
        if (waveManager.isTimeForNewZombie()) {
            if (waveManager.isThereMoreZombieInWave()) {
                return true;
            }
        }
        return false;
    }
    private void spawnZombie() {
        if (counter >= 800 && zombieManager.getTotalZom() == 10){
            zombieManager.spawnRandomZombiesIn5RandomRows(5,3);
        }
        else if (counter >= 900){
        zombieManager.createSingleZombie();
        counter=601;
        }
    }
    public void setupZombie(){
        if(zombieManager.iszReachedEnd()) {
            setGameScenes(LOSE);
          /*  Audio.lose();
            Audio.stopRoof();
            Audio.stopReadySetPlant();*/
        }
        if(getNotifManager().getWaveCDTime().isEndCDWave()) { // change wave
            System.out.println("startGame");
            startGame();
        }
        if (startWave) {
            if (isTimeForNewZombie()) {
                spawnZombie();
            }
            if (waveManager.hordeDead() && callHorde == true) {
                startWave = false;
            }
            if (zombieManager.getTotalZom() <= 0 && zombieManager.allZombieDead()) {
                System.out.println("You wins");
                setGameScenes(WIN);
                /*if (waveManager.isEndWaves()) {
                    setGameScenes(WIN);
                    Audio.stopRoof();
                    Audio.stopReadySetPlant();
                    Audio.win();
                } else {
                    waveManager.createHorde();
                    callHorde = true;
                    zombieApproaching = true;
                }*/
//                notifManager.setNotif(new PlayingNotif(0));
            }
        }
    }

    public void chooseMeow(int x, int y){
        for (MyButtons b2 : barManager.getPickMeow()) {
            if (b2.getBounds().contains(x, y)) {
                System.out.println("You choose " + b2.getText());
                //Audio.tapPlantBar();
                meowManager.setSelected(true);
                if(!barManager.isMeowLocked()){
                    if (b2.getText().contains("Bucket")) {
                        meowManager.setForbidden(false);
                        barManager.bucket();

                    } else if (b2.getText().contains("Meow")) {
                        meowManager.setForbidden(false);
                        barManager.Meow();
                    } else if (b2.getText().contains("Stinky Pate")) {
                        meowManager.setForbidden(false);
                        barManager.StinkyPate();
                    } else if (b2.getText().contains("IceCat")) {
                        meowManager.setForbidden(false);
                        barManager.IceMeow();
                        System.out.println("Contain");
                    } else if (b2.getText().contains("Pate Bomb")) {
                        meowManager.setForbidden(false);
                        barManager.PateBomb();
                    } else if(b2.getText().contains("Bag")){
                        meowManager.setSelected(false);
                        meowManager.setBagged(true);
                    }
                }
                barManager.setMeowLocked(true);
            }
        }
    }
    public boolean isZombieApproaching() {
        return zombieApproaching;
    }
    public void changeScene(int x, int y){
        if (buttonManager.getbSetting().getBounds().contains(x, y)) {
           Audio.setting();
            Audio.stopRoof();
            Audio.stopReadySetPlant();
            setGameScenes(SETTING);
        }
    }
    public void zombieAppear(){
       if (counter >= 600) {
            if (!startWave && zombieManager.allZombieDead()) {
                startGame();
            }
        }
        counter ++;

    }
    @Override
    public void render(Graphics g, Image img) {
        g.drawImage(img, 0, 0, w.getWidth(), w.getHeight(), null);
//        buttonManager.drawButtons(g);
//        tileManager.drawTiles(g, meowManager);
//        tileManager.drawTiles(g, houseOwnerManager);
        barManager.draw(g);
        mouseMotionManager.drawMeowSelectedByMouse(g);
        tileManager.draw(g);
        tileManager.drawBagSprite(g);
        meowManager.draw(g);
        zombieManager.render(g);
        fishManager.drawFish(g);
        notifManager.drawNotif(g);
        bulletManager.drawBullet(g);
        buttonManager.drawImg(g);
        sharkManager.draw(g);


    }
}

