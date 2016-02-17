package cs3500.music.view;

import org.junit.Test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import static org.junit.Assert.assertEquals;

/**
 * Tests to ensure the midi view works as expected
 */
public class MidiViewTest {

  @Test
  public void testMidiView() {
    int[] notes = new int[]{60, 62, 64, 65, 67, 69, 71, 72, 72, 71, 69, 67, 65, 64, 62, 60};
    StringBuilder log = new StringBuilder();
    try {
      Synthesizer synthesizer = new MockSynthesizer(log);
      Receiver receiver = synthesizer.getReceiver();
      synthesizer.open();

      for (int note : notes) {

        MidiMessage start1 = new ShortMessage(ShortMessage.NOTE_ON, 0, note, 64);
        MidiMessage end = new ShortMessage(ShortMessage.NOTE_OFF, 0, note, 64);
        receiver.send(start1, 2);
        receiver.send(end, 200000);
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          break;
        }
      }
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    assertEquals("opening...Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n" +
            "Sending message with timestamp: 2\n" +
            "Sending message with timestamp: 200000\n", log.toString());
  }
}
