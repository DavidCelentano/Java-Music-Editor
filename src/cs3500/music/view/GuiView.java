package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.Note;

/**
 * Sub-interface for Gui views. Contains methods for dealing with keyboard and
 * mouse.
 */
public interface GuiView extends View {
  // todo keyboard and mouse methods.
//
//  /**
//   * Renders the view
//   *
//   * @param vm   the view model being passed in.
//   * @param beat the beat measure that is sent to the view.
//   * @param name the name of the file.
//   */
//  void render(ViewModel vm, int beat, String name) throws InvalidMidiDataException;

  //
//  /**
//   * Adds a key listener
//   * @param listener a key listener
//   */
//  void addKeyListener(KeyListener listener);
  void addKeyListener(KeyListener l);

  void removeKeyListener(KeyListener l);

  void addMouseListener(MouseListener l);

  void removeMouseListener(MouseListener l);

  Note getHighest();

  Note getLowest();
}
