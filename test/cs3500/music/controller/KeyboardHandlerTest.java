package cs3500.music.controller;

import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

/**
 * This tests the key handler.
 */
public class KeyboardHandlerTest {
  @Test
  public void testKeyTypedOthers() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_1, '1');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_SPACE in keyTyped.");
      }
    };
    keyboardHandler.addToKeyTyped(KeyEvent.VK_SPACE, runnable);
    keyboardHandler.keyTyped(key);
    assertEquals("", out.toString());
  }

  @Test
  public void testKeyTypedSpace() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_SPACE, ' ');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_SPACE in keyTyped.");
      }
    };
    keyboardHandler.addToKeyTyped(KeyEvent.VK_SPACE, runnable);
    keyboardHandler.keyTyped(key);
    assertEquals("This should be for VK_SPACE in keyTyped.", out.toString());
  }

  @Test
  public void testKeyTypedComma() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_COMMA, ',');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_COMMA in keyTyped.");
      }
    };
    keyboardHandler.addToKeyTyped(KeyEvent.VK_COMMA, runnable);
    keyboardHandler.keyTyped(key);
    assertEquals("This should be for VK_COMMA in keyTyped.", out.toString());
  }

  @Test
  public void testKeyTypedPeriod() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_PERIOD, '.');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_PERIOD in keyTyped.");
      }
    };
    keyboardHandler.addToKeyTyped(KeyEvent.VK_PERIOD, runnable);
    keyboardHandler.keyTyped(key);
    assertEquals("This should be for VK_PERIOD in keyTyped.", out.toString());
  }

  @Test
  public void testKeyTypedR() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_R, 'r');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_R in keyTyped.");
      }
    };
    keyboardHandler.addToKeyTyped(KeyEvent.VK_R, runnable);
    keyboardHandler.keyTyped(key);
    assertEquals("This should be for VK_R in keyTyped.", out.toString());
  }

  @Test
  public void testKeyTypedE() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_E, 'e');
//
//    System.out.println("This is the key " + key.getKeyCode());

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_E in keyTyped.");
      }
    };
    keyboardHandler.addToKeyTyped(KeyEvent.VK_E, runnable);
    keyboardHandler.keyTyped(key);
    assertEquals("This should be for VK_E in keyTyped.", out.toString());
  }

  @Test
  public void testKeyTypedM() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_M, 'm');
//
//    System.out.println("This is the key " + key.getKeyCode());

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_M in keyTyped.");
      }
    };
    keyboardHandler.addToKeyTyped(KeyEvent.VK_M, runnable);
    keyboardHandler.keyTyped(key);
    assertEquals("This should be for VK_M in keyTyped.", out.toString());
  }

  @Test
  public void testKeyPressedOthers() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_1, '1');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_SPACE in keyTyped.");
      }
    };
    keyboardHandler.addToKeyPressed(KeyEvent.VK_SPACE, runnable);
    keyboardHandler.keyPressed(key);
    assertEquals("", out.toString());
  }

  @Test
  public void testKeyPressedSpace() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_SPACE, ' ');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_SPACE in keyPressed.");
      }
    };
    keyboardHandler.addToKeyPressed(KeyEvent.VK_SPACE, runnable);
    keyboardHandler.keyPressed(key);
    assertEquals("This should be for VK_SPACE in keyPressed.", out.toString());
  }

  @Test
  public void testKeyPressedComma() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_COMMA, ',');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_COMMA in keyPressed.");
      }
    };
    keyboardHandler.addToKeyPressed(KeyEvent.VK_COMMA, runnable);
    keyboardHandler.keyPressed(key);
    assertEquals("This should be for VK_COMMA in keyPressed.", out.toString());
  }

  @Test
  public void testKeyPressedPeriod() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_PERIOD, '.');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_PERIOD in keyPressed.");
      }
    };
    keyboardHandler.addToKeyPressed(KeyEvent.VK_PERIOD, runnable);
    keyboardHandler.keyPressed(key);
    assertEquals("This should be for VK_PERIOD in keyPressed.", out.toString());
  }

  @Test
  public void testKeyPressedR() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_R, 'r');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_R in keyPressed.");
      }
    };
    keyboardHandler.addToKeyPressed(KeyEvent.VK_R, runnable);
    keyboardHandler.keyPressed(key);
    assertEquals("This should be for VK_R in keyPressed.", out.toString());
  }

  @Test
  public void testKeyPressedE() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_E, 'e');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_E in keyPressed.");
      }
    };
    keyboardHandler.addToKeyPressed(KeyEvent.VK_E, runnable);
    keyboardHandler.keyPressed(key);
    assertEquals("This should be for VK_E in keyPressed.", out.toString());
  }

  @Test
  public void testKeyPressedM() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_M, 'm');
//
//    System.out.println("This is the key " + key.getKeyCode());

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_M in keyPressed.");
      }
    };
    keyboardHandler.addToKeyPressed(KeyEvent.VK_M, runnable);
    keyboardHandler.keyPressed(key);
    assertEquals("This should be for VK_M in keyPressed.", out.toString());
  }

  @Test
  public void testKeyReleasedOthers() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_1, '1');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_SPACE in keyTyped.");
      }
    };
    keyboardHandler.addToKeyReleased(KeyEvent.VK_SPACE, runnable);
    keyboardHandler.keyReleased(key);
    assertEquals("", out.toString());
  }

  @Test
  public void testKeyReleasedSpace() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_SPACE, ' ');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_SPACE in keyReleased.");
      }
    };
    keyboardHandler.addToKeyReleased(KeyEvent.VK_SPACE, runnable);
    keyboardHandler.keyReleased(key);
    assertEquals("This should be for VK_SPACE in keyReleased.", out.toString());
  }

  @Test
  public void testKeyReleasedComma() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_COMMA, ',');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_COMMA in keyReleased.");
      }
    };
    keyboardHandler.addToKeyReleased(KeyEvent.VK_COMMA, runnable);
    keyboardHandler.keyReleased(key);
    assertEquals("This should be for VK_COMMA in keyReleased.", out.toString());
  }

  @Test
  public void testKeyReleasedPeriod() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_PERIOD, '.');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_PERIOD in keyReleased.");
      }
    };
    keyboardHandler.addToKeyReleased(KeyEvent.VK_PERIOD, runnable);
    keyboardHandler.keyReleased(key);
    assertEquals("This should be for VK_PERIOD in keyReleased.", out.toString());
  }

  @Test
  public void testKeyReleasedR() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_R, 'r');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_R in keyReleased.");
      }
    };
    keyboardHandler.addToKeyReleased(KeyEvent.VK_R, runnable);
    keyboardHandler.keyReleased(key);
    assertEquals("This should be for VK_R in keyReleased.", out.toString());
  }

  @Test
  public void testKeyReleasedE() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_E, 'e');

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_E in keyReleased.");
      }
    };
    keyboardHandler.addToKeyReleased(KeyEvent.VK_E, runnable);
    keyboardHandler.keyReleased(key);
    assertEquals("This should be for VK_E in keyReleased.", out.toString());
  }

  @Test
  public void testKeyReleasedM() throws Exception {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Button a = new Button("click");

    StringBuilder out = new StringBuilder();
    KeyEvent key = new KeyEvent(a, 2, 3, 0, KeyEvent.VK_M, 'm');
//
//    System.out.println("This is the key " + key.getKeyCode());

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This should be for VK_M in keyReleased" +
                ".");
      }
    };
    keyboardHandler.addToKeyReleased(KeyEvent.VK_M, runnable);
    keyboardHandler.keyReleased(key);
    assertEquals("This should be for VK_M in keyReleased.", out.toString());
  }

  @Test
  public void testAddToKeyPressed0() {
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    StringBuilder out = new StringBuilder();
    int key = 0;
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        out.append("This is a new runnable added to keyPressed");
      }
    };
    keyboardHandler.addToKeyPressed(key, runnable);
    keyboardHandler.keyPressed.get(key).run();
    assertEquals("This is a new runnable added to keyPressed", out.toString());
  }
}