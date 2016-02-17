package cs3500.music.controller;

import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.junit.Assert.assertEquals;

/**
 * This tests the mouse handler.
 */
public class MouseHandlerTest {

  @Test
  public void testMouseClicked() throws Exception {

    MouseHandler mouseHandler = new MouseHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    MouseEvent mouse = new MouseEvent(a, 2, 3, 1, 500, 500, 1, true);

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This shouldn't be appended.");
      }
    };
    mouseHandler.addToMouseButton("Click", runnable);
    mouseHandler.mouseClicked(mouse);
    assertEquals("", out.toString());
  }

  @Test
  public void testMousePressed() throws Exception {

    MouseHandler mouseHandler = new MouseHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    MouseEvent mouse = new MouseEvent(a, 2, 3, 1, 500, 500, 1, true);

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This shouldn't be appended");
      }
    };
    mouseHandler.addToMouseButton("Press", runnable);
    mouseHandler.mousePressed(mouse);
    assertEquals("", out.toString());
  }

  @Test
  public void testMouseReleased() throws Exception {


    MouseHandler mouseHandler = new MouseHandler();
    Button a = new Button("click");
    StringBuilder out = new StringBuilder();

    MouseEvent mouse = new MouseEvent(a, 2, 3, 1, 500, 500, 1, true);

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be appended for running Press");
      }
    };
    mouseHandler.addToMouseButton("Press", runnable);
    mouseHandler.mouseReleased(mouse);
    assertEquals("This should be appended for running Press", out.toString());
  }

}