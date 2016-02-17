package cs3500.music.controller;

import java.awt.*;
import java.awt.event.KeyEvent;

import cs3500.music.model.Model;
import cs3500.music.model.Note;
import cs3500.music.view.CompositeView;
import cs3500.music.view.ViewModel;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;

/**
 * Concrete controller class.
 */
public final class ControllerImpl implements Controller {
  private final KeyboardHandler keyHandler;
  private final MouseHandler mouseHandler;
  private final Model model;
  private final CompositeView view;
  // either adding or removing or moving
  private Mode mode;
  private State state;


  // pause/resume state
  protected enum State {
    Paused, Playing;
  }

  protected enum Mode {
    Adding, Removing, Moving, Resting, AddEnding;
  }

  private Timer timer;
  private int currentMeasure;
  private final String fileName;
//  private int numberOfRepeats;
//  private int numberOfEndings;


  /**
   * Constructs an implementation of a controller
   *
   * @param model    the model
   * @param view     the view
   * @param fileName the name of the file that is being rendered
   */
  public ControllerImpl(CompositeView view, Model model, String fileName)
          throws InvalidMidiDataException {
    this.view = view;
    this.keyHandler = new KeyboardHandler();
    this.mouseHandler = new MouseHandler();
    this.model = model;
    this.mode = Mode.Resting;
    this.state = State.Paused;
    this.timer = new Timer();
    this.currentMeasure = this.model.getCurrentMeasure();
    this.fileName = fileName;
  }


  /**
   * Initializes the maps that are used in the keyboard handler.
   */
  @Override
  public void initialize() {
//    // todo where should i put this?
//    if (currentMeasure == model.getEndRepeat() &&
//            numberOfRepeats == 0) {
//      currentMeasure = model.getStartRepeat();
//      numberOfRepeats += 1;
//    }

    keyHandler.addToKeyTyped(KeyEvent.VK_E, () -> {
      if (mode == Mode.Adding) {
        mode = Mode.Resting;
      } else {
        mode = Mode.Adding;
      }
    });
    keyHandler.addToKeyTyped(KeyEvent.VK_R, () -> {
      if (this.mode == Mode.Removing) {
        this.mode = Mode.Resting;
      } else {
        this.mode = Mode.Removing;
      }
    });
    keyHandler.addToKeyTyped(KeyEvent.VK_M, () -> {
      if (this.mode == Mode.Moving) {
        this.mode = Mode.Resting;
      } else {
        this.mode = Mode.Moving;
      }
    });
    keyHandler.addToKeyTyped(KeyEvent.VK_W, () -> {
      if (this.mode == Mode.AddEnding) {
        this.mode = Mode.Resting;
      } else {
        this.mode = Mode.AddEnding;
      }
    });
    keyHandler.addToKeyTyped(KeyEvent.VK_COMMA, () -> {
      this.toBeginning();
    });
    keyHandler.addToKeyTyped(KeyEvent.VK_PERIOD, () -> {
      this.toEnd();
    });

    keyHandler.addToKeyPressed(KeyEvent.VK_SPACE, () -> {
      if (this.state.equals(State.Playing)) {
        this.state = State.Paused;
      } else {
        this.state = State.Playing;
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
            if (currentMeasure >= model.getHeadNotes().size() || state.equals(State.Paused)) {
              timer.cancel();
            } else {
              try {
                view.render(new ViewModel(model), model.getCurrentMeasure(), fileName);
                model.updateMeasure();
                currentMeasure = model.getCurrentMeasure();
              } catch (InvalidMidiDataException e) {
                e.printStackTrace();
              }
            }
          }
        }, model.getTempo()/1000, model.getTempo()/1000);
      }
    });
    view.addKeyListener(keyHandler);

    mouseHandler.addToMouseButton("Press", ()-> {

      if (this.mode == Mode.Adding) {
        this.alter("add");
      } else if (this.mode == Mode.Removing) {
        this.alter("remove");
      } else if (this.mode == Mode.Moving) {
        this.alter("move");
      } else if (this.mode == Mode.AddEnding) {
        this.alter("addEnding");
      }
    });
    view.addMouseListener(mouseHandler);

  }


  public void alter(String execute) {
    int x1 = (mouseHandler.getXPress() - 30) / 20;
    int x2 = (mouseHandler.getXRelease() - 30) / 20;
    int y1 = (mouseHandler.getYPress() - 30) / 20;
    int y2 = (mouseHandler.getYRelease() - 30) / 20;
    Note base = this.view.getLowest();
    for (int i = model.getPitches().size()-1; i > y1; i--) {
      base.up();
      System.out.println("This is base: " + base.toString());
    }
    Note base2 = this.view.getHighest();

    for (int i = 0; i < y2; i++) {
      base2.down();
    }

    if (execute.equals("add")) {
      this.addNote(x1, x2 - x1, base.getOctave(), base.toMidiIndex());
    }
    if (execute.equals("remove")) {
      this.removeNote(x1, x2 - x1, Note.intToOctave(base.toMidiIndex()), base.toMidiIndex());
    }
    if (execute.equals("move")) {
      Set<Note> beat = this.model.getHeadNotes().get(x1);
      this.moveNote(x1, Note.intToOctave(base.toMidiIndex()), x2, Note
              .intToOctave(base2.toMidiIndex()), base
              .getDuration(), base.toMidiIndex(), base2
              .toMidiIndex());
    }
    if (execute.equals("addEnding")) {
      this.addEnding(x1, x2);
    }
  }

  /**
   * Adds a note to the model.
   */
  public void addNote(int start, int dur, int oct, int midi) {
    int duration = dur;
    int octave = oct;
    int startingMeasure = start;
    int midiPitch = midi;
    int instrument = 1;
    int volume = 70;
    Note n = new Note(duration, octave, startingMeasure, Note.intToPitch(midiPitch),
            true, instrument, volume);
    this.model.addNote(n);
    this.mode = Mode.Resting;
  }

  /**
   * Removes a note to the model.
   */
  public void removeNote(int start, int dur, int oct, int midi) {
    try {
      int duration = dur;
      int octave = oct;
      int startingMeasure = start;
      int midiPitch = midi;
      int instrument = 1;
      int volume = 70;
      Note n = this.model.find(start, dur, oct, midi);
      this.model.removeNote(n);
    } catch (IllegalArgumentException e) {

    }

    this.mode = Mode.Resting;
  }

  /**
   * Moves a note to the model.
   */
  public void moveNote(int oldStart, int oldOct, int newStart, int newOct, int
          dur, int oldMidi, int newMidi) {
    try {
      int instrument = 1;
      int volume = 70;
      Note n = this.model.find(oldStart, dur, oldOct, oldMidi);
      this.model.move(n, Note.intToPitch(newMidi), newOct, newStart);
    } catch (IllegalArgumentException e) {

    }
    this.mode = Mode.Resting;
  }
  /**
   * add an another ending to the model.
   */
  public void addEnding(int start, int end) {
    this.model.addEnding(start, end);
  }

  /**
   * Scrolls to the beginning of the piece.
   */
  public void toBeginning() {
    this.model.setCurrentMeasure(0);
  }

  /**
   * Scrolls to the end of the piece.
   */
  public void toEnd() {
    if (this.model.getHeadNotes().isEmpty()) {
      this.model.setCurrentMeasure(0);
    } else {

      this.model.setCurrentMeasure(this.model.getHeadNotes().size() - 1);
    }
  }

  @Override
  public State getState() {
    return this.state;
  }

  @Override
  public Mode getMode() {
    return this.mode;
  }

  @Override
  public KeyboardHandler getKeyHandler() {
    return this.keyHandler;
  }

}
