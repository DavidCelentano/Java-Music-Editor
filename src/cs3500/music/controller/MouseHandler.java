package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Point;
import java.util.HashMap;

/**
 * class for mouse listener
 */
public final class MouseHandler implements MouseListener {

  private int xPress;
  private int yPress;
  private int xRelease;
  private int yRelease;
  private Point loc;
  private HashMap<String, Runnable> mouseButton;


  public MouseHandler() {
    this.xPress = -1;
    this.yPress = -1;
    this.xRelease = -1;
    this.yRelease = -1;
    this.loc = new Point(-1, -1);
    this.mouseButton = new HashMap<>();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.loc = e.getLocationOnScreen();
    System.out.println("this is the loc" + loc);

  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.xPress = e.getX();
    this.yPress = e.getY();

  }

  @Override
  public void mouseReleased(MouseEvent e) {
    this.xRelease = e.getX();
    this.yRelease = e.getY();
    this.mouseButton.get("Press").run();
  }

  @Override
  public void mouseEntered(MouseEvent e) {
//    System.out.println("Mouse Has Entered");
//    System.out.println("This is where it has entered: " +e
//            .getLocationOnScreen());
  }

  @Override
  public void mouseExited(MouseEvent e) {
//    System.out.println("Mouse Has Exited");
//    System.out.println("This is where it has exited: " +e
//            .getLocationOnScreen());
  }

  public int getXPress() {
    return this.xPress;
  }

  public int getYPress() {
    return this.yPress;
  }

  public int getXRelease() {
    return this.xRelease;
  }

  public int getYRelease() {
    return this.yRelease;
  }

  public void addToMouseButton(String name, Runnable run) {
    this.mouseButton.put(name, run);
  }
}
