package cs3500.music.controller;

import java.util.Set;

import cs3500.music.model.Note;
import cs3500.music.view.ViewModel;

/**
 * the controller interface. controls the model and the view.
 */
public interface Controller {
  /**
   * initializes all things needed for a controller.
   */
  void initialize();

  /**
   * get the state of the controller
   */
  ControllerImpl.State getState();

  /**
   * get the mode of the controller
   */
  ControllerImpl.Mode getMode();

  /**
   * Execute strings for mouse events.
   *
   * @param execute the string passed in from mouse events
   */
  void alter(String execute);

  /**
   * Adds a note to the model.
   */
  void addNote(int start, int dur, int oct, int midi);

  /**
   * Removes a note to the model.
   */
  void removeNote(int start, int dur, int oct, int midi);

  /**
   * Moves a note to the model.
   */
  void moveNote(int oldStart, int oldOct, int newStart, int newOct, int
          dur, int oldMidi, int newMidi);

  /**
   * Scrolls to the beginning of the piece.
   */
  void toBeginning();

  /**
   * Scrolls to the end of the piece.
   */
  void toEnd();

  /**
   * Get the keyHandler.
   */
  KeyboardHandler getKeyHandler();
}
