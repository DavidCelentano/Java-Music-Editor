package cs3500.music.model;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.ViewModel;

import static org.junit.Assert.assertEquals;

/**
 * Test class for console view.
 */
public class ConsoleViewTest {

  /**
   * Test draw.
   */
  @Test
  public void testDraw() {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 0, 1);
    Note c3 = new Note(2, 3, 0, Note.Pitches.C, true, 0, 1);
    Note c2long = new Note(8, 2, 3, Note.Pitches.C, true, 0, 1);
    Note d2 = new Note(5, 2, 2, Note.Pitches.D, true, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(c3);
    m3.addNote(c2long);
    ViewModel vm = new ViewModel(m3);
    ConsoleView cv = ConsoleView.builder().build();
    assertEquals("", cv.getOutput());
    cv.draw(vm);
    assertEquals("   C2C#2 D2D#2 E2 F2F#2 G2G#2 A2A#2 B2 C3\n" +
            "0  X                                   X \n" +
            "1  |                                   | \n" +
            "2        X \n" +
            "3  X     | \n" +
            "4  |     | \n" +
            "5  |     | \n" +
            "6  |     | \n" +
            "7  | \n" +
            "8  | \n" +
            "9  | \n" +
            "10 | \n", cv.getOutput());
  }

  /**
   * Empty model.
   */
  @Test
  public void testDraw1() {
    Model m3 = ModelImpl.builder().build();
    ViewModel vm = new ViewModel(m3);
    ConsoleView cv = ConsoleView.builder().build();
    cv.draw(vm);
    assertEquals("", cv.getOutput());
  }

  /**
   * Empty model.
   */
  @Test
  public void testDraw2() {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 0, 1);
    Note c3 = new Note(2, 3, 0, Note.Pitches.C, true, 0, 1);
    Note c2long = new Note(8, 2, 3, Note.Pitches.C, true, 0, 1);
    Note d2 = new Note(5, 2, 2, Note.Pitches.D, true, 0, 1);
    List<Note> notes = new ArrayList<>();
    notes.add(c2);
    notes.add(c3);
    notes.add(c2long);
    notes.add(d2);
    Model m3 = ModelImpl.builder().build();
    m3.addAll(notes);
    ViewModel vm = new ViewModel(m3);
    ConsoleView cv = ConsoleView.builder().build();
    cv.draw(vm);
    assertEquals("   C2C#2 D2D#2 E2 F2F#2 G2G#2 A2A#2 B2 C3\n" +
            "0  X                                   X \n" +
            "1  |                                   | \n" +
            "2        X \n" +
            "3  X     | \n" +
            "4  |     | \n" +
            "5  |     | \n" +
            "6  |     | \n" +
            "7  | \n" +
            "8  | \n" +
            "9  | \n" +
            "10 | \n", cv.getOutput());
  }

  /**
   * Parsing Mary Little Lamb.
   */
  @Test
  public void testParsingFile0() {
    MusicReader reader = new MusicReader();
    CompositionBuilder<Model> readMLL = ModelImpl.builder();
    try {
      reader.parseFile(new FileReader(
              "/Users/rosaline17/Desktop/REAL OOD/hw07/mary-little-lamb.txt"), readMLL);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    ViewModel vm = new ViewModel(readMLL.build());
    ConsoleView cv = ConsoleView.builder().build();
    cv.draw(vm);
    assertEquals("   E3 F3F#3 G3G#3 A3A#3 B3 C4C#4 D4D#4 E4 F4F#4 G4\n" +
            "0           X                          X \n" +
            "1           |                          | \n" +
            "2           |                    X \n" +
            "3           |                    | \n" +
            "4           |              X \n" +
            "5           |              | \n" +
            "6           |                    X \n" +
            "7                                | \n" +
            "8           X                          X \n" +
            "9           |                          | \n" +
            "10          |                          X \n" +
            "11          |                          | \n" +
            "12          |                          X \n" +
            "13          |                          | \n" +
            "14          |                          | \n" +
            "15   \n" +
            "16          X                    X \n" +
            "17          |                    | \n" +
            "18          |                    X \n" +
            "19          |                    | \n" +
            "20          |                    X \n" +
            "21          |                    | \n" +
            "22          |                    | \n" +
            "23          |                    | \n" +
            "24          X                          X \n" +
            "25          |                          | \n" +
            "26                                              X \n" +
            "27                                              | \n" +
            "28                                              X \n" +
            "29                                              | \n" +
            "30                                              | \n" +
            "31                                              | \n" +
            "32          X                          X \n" +
            "33          |                          | \n" +
            "34          |                    X \n" +
            "35          |                    | \n" +
            "36          |              X \n" +
            "37          |              | \n" +
            "38          |                    X \n" +
            "39          |                    | \n" +
            "40          X                          X \n" +
            "41          |                          | \n" +
            "42          |                          X \n" +
            "43          |                          | \n" +
            "44          |                          X \n" +
            "45          |                          | \n" +
            "46          |                          X \n" +
            "47          |                          | \n" +
            "48          X                    X \n" +
            "49          |                    | \n" +
            "50          |                    X \n" +
            "51          |                    | \n" +
            "52          |                          X \n" +
            "53          |                          | \n" +
            "54          |                    X \n" +
            "55          |                    | \n" +
            "56 X                       X \n" +
            "57 |                       | \n" +
            "58 |                       | \n" +
            "59 |                       | \n" +
            "60 |                       | \n" +
            "61 |                       | \n" +
            "62 |                       | \n" +
            "63 |                       | \n", cv.getOutput());
  }

  @Test
  public void testDraw3() {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 0, 1);
    Note c3 = new Note(2, 3, 0, Note.Pitches.C, true, 0, 1);
    Note c2long = new Note(8, 2, 3, Note.Pitches.C, true, 0, 1);
    Note d2 = new Note(5, 2, 2, Note.Pitches.D, true, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(c3);
    m3.addNote(c2long);
    ViewModel vm = new ViewModel(m3);
    ConsoleView cv = ConsoleView.builder().build();
    cv.draw(vm);
    assertEquals("   C2C#2 D2D#2 E2 F2F#2 G2G#2 A2A#2 B2 C3\n" +
            "0  X                                   X \n" +
            "1  |                                   | \n" +
            "2        X \n" +
            "3  X     | \n" +
            "4  |     | \n" +
            "5  |     | \n" +
            "6  |     | \n" +
            "7  | \n" +
            "8  | \n" +
            "9  | \n" +
            "10 | \n", cv.getOutput());
  }

  @Test
  public void testDraw4() {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 0, 1);
    Note d2 = new Note(1, 2, 2, Note.Pitches.D, true, 0, 1);
    Note e3 = new Note(1, 3, 3, Note.Pitches.E, true, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(e3);
    ViewModel vm = new ViewModel(m3);
    ConsoleView cv = ConsoleView.builder().build();
    cv.draw(vm);
    assertEquals("   C2C#2 D2D#2 E2 F2F#2 G2G#2 A2A#2 B2 C3C#3 D3D#3 E3\n" +
            "0  X \n" +
            "1  | \n" +
            "2        X \n" +
            "3                                                  X \n", cv.getOutput());
  }

  @Test
  public void testDraw5() {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 0, 1);
    Note d2 = new Note(2, 2, 0, Note.Pitches.D, true, 0, 1);
    Note e2 = new Note(2, 2, 0, Note.Pitches.E, true, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(e2);
    ViewModel vm = new ViewModel(m3);
    ConsoleView cv = ConsoleView.builder().build();
    cv.draw(vm);
    assertEquals("   C2C#2 D2D#2 E2\n" +
            "0  X     X     X \n" +
            "1  |     |     | \n", cv.getOutput());
  }

//  /**
//   * Parsing Mystery 2.
//   */
//  @Test
//  public void testParsingFile1() {
//    MusicReader reader = new MusicReader();
//    CompositionBuilder<Model> readMLL = ModelImpl.builder();
//    try {
//      reader.parseFile(new FileReader("music-editor-skeleton-master/mystery-2.txt"), readMLL);
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    }
//    ViewModel vm = new ViewModel(readMLL.build());
//    ConsoleView cv = ConsoleView.builder().build();
//    cv.draw(vm);
//    assertEquals("", cv.getOutput());
//  }
}
