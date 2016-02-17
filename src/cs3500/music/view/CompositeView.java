package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.Note;

/**
 * Class for composite view
 */
public class CompositeView implements GuiView {
  private GuiViewFrame guiViewImpl;
  private MidiViewImpl midiViewImpl;

  /**
   * Constructs a composite view.
   *
   * @param vm the view model
   */
  public CompositeView(ViewModel vm) {
    this.guiViewImpl = GuiViewFrame.builder().setViewModel(vm).build();
    this.midiViewImpl = new MidiViewImpl();
  }

  /**
   * Plays the notes at a certain beat
   *
   * @param vm   a view model
   * @param beat the beat number
   * @param file the file that is parsed
   */
  public void render(ViewModel vm, int beat, String file) throws InvalidMidiDataException {

    this.guiViewImpl.repaint();
    this.midiViewImpl.render(vm, beat, file);
  }

  @Override
  public void addKeyListener(KeyListener l) {
    this.guiViewImpl.addKeyListener(l);
  }

  @Override
  public void removeKeyListener(KeyListener l) {
    this.guiViewImpl.removeKeyListener(l);
  }

  @Override
  public void addMouseListener(MouseListener l) {
    this.guiViewImpl.addMouseListener(l);
  }

  @Override
  public void removeMouseListener(MouseListener l) {
    this.guiViewImpl.removeMouseListener(l);
  }

  @Override
  public Note getHighest() {
    return this.guiViewImpl.getHighest();
  }

  @Override
  public Note getLowest() {
    return this.guiViewImpl.getLowest();
  }

}
