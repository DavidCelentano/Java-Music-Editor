package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;


/**
 * This handles key events.
 */
public final class KeyboardHandler implements KeyListener {
  private Map<Integer, Runnable> keyTyped;
  public Map<Integer, Runnable> keyPressed;
  private Map<Integer, Runnable> keyReleased;

  public KeyboardHandler() {
    this.keyTyped = new HashMap<>();
    this.keyPressed = new HashMap<>();
    this.keyReleased = new HashMap<>();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_UNDEFINED) {
      code = e.getExtendedKeyCode();
    }
    if (keyTyped.containsKey(code)) {

      this.keyTyped.get(code).run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_UNDEFINED) {
      code = e.getExtendedKeyCode();
    }
    if (keyPressed.containsKey(code)) {

      this.keyPressed.get(code).run();
    }

  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_UNDEFINED) {
      code = e.getExtendedKeyCode();
    }
    if (keyReleased.containsKey(code)) {

      this.keyReleased.get(code).run();
    }

  }

  /**
   * This adds the runnable to the keyPressed map
   *
   * @param key the key being assigned to
   * @param r   the runnable being added.
   */
  public void addToKeyPressed(int key, Runnable r) {
    this.keyPressed.put(key, r);
  }

  /**
   * This adds the runnable to the keyTyped map
   *
   * @param key the key being assigned to
   * @param r   the runnable being added.
   */
  public void addToKeyTyped(int key, Runnable r) {
    this.keyTyped.put(key, r);
  }

  /**
   * This adds the runnable to the keyReleased map
   *
   * @param key the key being assigned to
   * @param r   the runnable being added.
   */
  public void addToKeyReleased(int key, Runnable r) {
    this.keyReleased.put(key, r);
  }

}
