package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.Note;

/**
 * This is for MIDI playback
 */
public final class MidiViewImpl implements View {
  private final Synthesizer synth;
  private Synthesizer x;
  private final Receiver receiver;
  private Receiver y;
  private StringBuilder log;

  public MidiViewImpl() {
    try {
      this.x = MidiSystem.getSynthesizer();
      this.y = x.getReceiver();
      this.x.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }

    this.synth = x;
    this.receiver = y;
  }

  public MidiViewImpl(StringBuilder stringBuilder) {
    this.log = stringBuilder;
    try {
      this.x = new MockSynthesizer(log);
      this.y = x.getReceiver();
      x.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.synth = x;
    this.receiver = y;
  }
  /**
   * Relevant classes and methods from the javax.sound.midi library: <ul>
   * <li>{@link MidiSystem#getSynthesizer()}</li> <li>{@link Synthesizer} <ul>
   * <li>{@link Synthesizer#open()}</li> <li>{@link Synthesizer#getReceiver()}</li>
   * <li>{@link Synthesizer#getChannels()}</li> </ul> </li> <li>{@link
   * Receiver} <ul> <li>{@link Receiver#send(MidiMessage, long)}</li>
   * <li>{@link Receiver#close()}</li> </ul> </li> <li>{@link MidiMessage}</li>
   * <li>{@link ShortMessage}</li> <li>{@link MidiChannel} <ul> <li>{@link
   * MidiChannel#getProgram()}</li> <li>{@link MidiChannel#programChange(int)}</li>
   * </ul> </li> </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   * https://en.wikipedia.org/wiki/General_MIDI </a>
   */
  /**
   * Plays the music based on the view model
   *
   * @param vm   the view model
   * @param file the name of the file
   * @param beat the beat
   */
  public void render(ViewModel vm, int beat, String file) throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
    if (beat == 0) {
      this.receiver.send(start, -1);
      this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
    }

    try {
      for (Note n : vm.getHeadNotes().get(beat)) {
        MidiMessage start1 = new ShortMessage(ShortMessage.NOTE_ON,
                n.getInstrument(), n.toMidiIndex(), 64);
        MidiMessage end = new ShortMessage(ShortMessage.NOTE_OFF,
                n.getInstrument(), n.toMidiIndex(), 64);
        this.receiver.send(start1, n.getDuration());
        this.receiver.send(end, this.synth.getMicrosecondPosition()
                + n.getDuration() * 200000);
      }
      Thread.sleep(100);

      // synth.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    //this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  public void play(ViewModel vm, String file) throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
    this.receiver.send(start, -1);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);

    try {
      for (int i = 0; i < vm.getHeadNotes().size(); i++) {
        for (Note n : vm.getHeadNotes().get(i)) {
          MidiMessage start1 = new ShortMessage(ShortMessage.NOTE_ON,
                  n.getInstrument(), n.toMidiIndex(), 64);
          MidiMessage end = new ShortMessage(ShortMessage.NOTE_OFF,
                  n.getInstrument(), n.toMidiIndex(), 64);
          this.receiver.send(start1, n.getDuration());
          this.receiver.send(end, this.synth.getMicrosecondPosition()
                  + n.getDuration() * 200000);
        }
        Thread.sleep(100);
      }
      synth.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

}

