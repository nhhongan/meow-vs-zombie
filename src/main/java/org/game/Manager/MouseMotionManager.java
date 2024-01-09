package org.game.Manager;

import org.game.Scenes.Playing;
import org.game.graphic.Graphical;

import java.awt.*;

public class MouseMotionManager {
    private Playing playing;
    private int plantPickedByMouse = 0;
    private int tileSelectedByMouse;

    public void setPlantPickedByMouse(int plantPickedByMouse) {
        this.plantPickedByMouse = plantPickedByMouse;
    }

    private boolean isMouseMoveForFirstTime = true;
    private boolean isControlledByMouse = false;
    public MouseMotionManager(Playing playing){
        this.playing = playing;
    }
    public void returnToSelectPlantByMouse(){
        playing.getMeowManager().setSelected(false);
        playing.getMeowManager().setTimetoPlant(true);
        playing.getBarManager().setPlantLocked(false);
    }
    public void changeStatusToMouse(int x, int y, World w){
        Rectangle world = new Rectangle(w.getX(),w.getY(),w.getWidth(),w.getHeight());
        if(world.contains(x,y)){
            if(isMouseMoveForFirstTime){
                returnToSelectPlantByMouse();
                isMouseMoveForFirstTime = false;
            }
            isControlledByMouse = true;
        }
    }
    public void mouseTrackPlantBar(int x, int y){
        for(int i = 0;i<playing.getBarManager().getPickPlant().length;i++){
            Rectangle r = playing.getBarManager().getPickPlant()[i].getBounds();
            if(r.contains(x,y)){
                if(playing.getTileManager().isInTile()){
                    playing.getTileManager().setInTile(false);
                    playing.getMeowManager().setSelected(false);
                    playing.getBarManager().setPlantLocked(false);
                   // playing.getMeowManager().setShoveled(false);
                }
                plantPickedByMouse = i;
                //playing.getKeyBoardManager().setPlantPickedByKeyBoard(plantPickedByMouse);
            }
        }
    }
    public boolean getisControlledByMouse() {
        return isControlledByMouse;
    }

    public void setControlledByMouse(boolean controlledByMouse) {
        isControlledByMouse = controlledByMouse;
    }

    public int getTileSelectedByMouse() {
        return tileSelectedByMouse;
    }


    public void setTileSelectedByMouse(int tileSelectedByMouse) {
        this.tileSelectedByMouse = tileSelectedByMouse;
    }
    public void tileTrack(int x, int y){
        for(int i = 0;i<playing.getTileManager().getTile().length;i++){
            Rectangle r = new Rectangle((int)playing.getTileManager().getTile()[i].getBound().getX(),(int)playing.getTileManager().getTile()[i].getBound().getY(),playing.getTileManager().getTile()[i].getGrassTile() ,playing.getTileManager().getTile()[i].getGrassTile());
            if(r.contains(x,y)){
                playing.getTileManager().setInTile(true);
                tileSelectedByMouse = i;
                //playing.getKeyBoardManager().setTileSelectedByKeyBoard(tileSelectedByMouse);
            }
        }
    }
}