package org.game.Player;



import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import org.game.Manager.World;
import org.game.Scenes.GameScenes;


public class MyMouseListener implements MouseListener, MouseMotionListener {
    private World w;

    public MyMouseListener(World w) {
        this.w = w;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            switch (GameScenes.gameScenes) {
                case MENU:
                    w.getMenuGame().mouseClicked(e.getX(), e.getY());
                    break;
                case PLAYING:
                    w.getPlaying().mouseClicked(e.getX(), e.getY());
                    break;
                case LOSE:
                    w.getLose().mouseClicked(e.getX(), e.getY());
                    break;
                case SETTING:
                    w.getSetting().mouseClicked(e.getX(), e.getY());
                    break;
                case WIN:
                    w.getWin().mouseClicked(e.getX(), e.getY());
                    break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            switch (GameScenes.gameScenes) {
                case MENU:
                    w.getMenuGame().mousePressed(e.getX(), e.getY());
                    break;
                case PLAYING:
                    w.getPlaying().mousePressed(e.getX(), e.getY());
                    break;
                case LOSE:
                    w.getLose().mousePressed(e.getX(), e.getY());
                    break;
                case SETTING:
                    w.getSetting().mousePressed(e.getX(), e.getY());
                    break;
                case WIN:
                    w.getWin().mousePressed(e.getX(), e.getY());
                    break;
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            switch (GameScenes.gameScenes){
                case PLAYING:
                    w.getPlaying().MousePress();
                    break;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            switch (GameScenes.gameScenes) {
                case MENU:
                    w.getMenuGame().mouseReleased(e.getX(), e.getY());
                    break;
                case PLAYING:
                    w.getPlaying().mouseReleased(e.getX(), e.getY());
                    break;
                case LOSE:
                    w.getLose().mouseReleased(e.getX(), e.getY());
                    break;
                case SETTING:
                    w.getSetting().mouseReleased(e.getX(), e.getY());
                    break;
                case WIN:
                    w.getWin().mouseReleased(e.getX(), e.getY());
                    break;
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameScenes.gameScenes){
            case PLAYING:
                w.getPlaying().mouseMove(e.getX(),e.getY());
                break;
        }

    }
}
