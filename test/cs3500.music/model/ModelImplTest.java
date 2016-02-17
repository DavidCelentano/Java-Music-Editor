package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link ModelImpl}.
 */
public class ModelImplTest {
  /**
   * construct an empty music sheet.
   */
  @Test
  public void testConstructor0a() {
    Model m1 = ModelImpl.builder().build();
    assertEquals("", m1.toString());
  }

  /**
   * construct an empty music sheet with several measures and a range of
   * pitches.
   */
  @Test
  public void testConstructor0b() {
    Note low = new Note(1, 0, 0, Note.Pitches.A, true, 0, 1);
    Model m2 = ModelImpl.builder().build();
  }

  /**
   * test exceptions in constructors
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2() {
    Model m2 = ModelImpl.builder().setTempo(49).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3() {
    Model m2 = ModelImpl.builder().setTempo(251).build();
  }


  /**
   * test toString.
   */
  @Test
  public void testToString0() {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 1, 1);
    Note c3 = new Note(2, 3, 0, Note.Pitches.C, true, 2, 3);
    Note c2long = new Note(8, 2, 3, Note.Pitches.C, true, 0, 2);
    Note d2 = new Note(5, 2, 2, Note.Pitches.D, true, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(c3);
    m3.addNote(c2long);
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
            "10 | \n", m3.toString());
  }

  @Test
  public void testToString1() {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 0, 1);
    Note d2 = new Note(1, 2, 2, Note.Pitches.D, true, 1, 1);
    Note e3 = new Note(1, 3, 3, Note.Pitches.E, true, 2, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    assertEquals(2, m3.getHighest().howFarUp(m3.getLowest()));
    m3.addNote(e3);
    assertEquals(true, e3.compareTo(c2) == 1);
    assertEquals(16, m3.getHighest().howFarUp(m3.getLowest()));
    assertEquals("   C2C#2 D2D#2 E2 F2F#2 G2G#2 A2A#2 B2 C3C#3 D3D#3 E3\n" +
            "0  X \n" +
            "1  | \n" +
            "2        X \n" +
            "3                                                  X \n", m3.toString());
  }

  @Test
  public void testToString2() {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 0, 1);
    Note d2 = new Note(2, 2, 0, Note.Pitches.D, true, 0, 1);
    Note e2 = new Note(2, 2, 0, Note.Pitches.E, true, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(e2);
    assertEquals("   C2C#2 D2D#2 E2\n" +
            "0  X     X     X \n" +
            "1  |     |     | \n", m3.toString());
  }


  /**
   * checking addNote when the new note is overlapped with the old.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAdd1() {
    Note c2 = new Note(4, 2, 0, Note.Pitches.C, true, 0, 1);
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2Dup = new Note(1, 2, 2, Note.Pitches.C, true, 0, 1);
    assertTrue(c2.equals(c2Dup));
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(c2Dup);
  }

  /**
   * check when the new note is overlapping the last beat of an existing note.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAdd2() {
    Note c2 = new Note(4, 2, 0, Note.Pitches.C, true, 0, 1);
    Note c2Dup = new Note(1, 2, 3, Note.Pitches.C, true, 0, 1);
    assertTrue(c2.equals(c2Dup));
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(c2Dup);
  }

  /**
   * check when the new note is overlapping the first beat of an existing
   * note.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAdd3() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, true, 0, 1);
    Note c2Dup = new Note(2, 2, 1, Note.Pitches.C, true, 0, 1);
    assertTrue(c2.equals(c2Dup));
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(c2Dup);
  }

  /**
   * check when the new note is overlapping more than one existing note.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAdd4() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, true, 9, 1);
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 9, 1);
    Note c2Dup = new Note(2, 2, 1, Note.Pitches.C, true, 9, 1);
    assertTrue(c2.equals(c2Dup));
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(c2Dup);
  }

  /**
   * add melodies
   */
  @Test
  public void testAdd5() {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 0, 1);
    Note d2 = new Note(1, 2, 2, Note.Pitches.D, true, 9, 1);
    Note e2 = new Note(1, 2, 3, Note.Pitches.E, true, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(e2);
  }

  /**
   * add chords
   */
  @Test
  public void testAdd6() {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 0, 1);
    Note d2 = new Note(2, 2, 0, Note.Pitches.D, true, 1, 1);
    Note e2 = new Note(2, 2, 0, Note.Pitches.E, true, 2, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(e2);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddException1() {
    Set<Note> s = new HashSet<Note>();
    List<Set<Note>> sheet = new ArrayList<>();
    Note c2 = new Note(4, 2, 0, Note.Pitches.C, true, 0, 1);
    Note c2Dup = new Note(1, 2, 2, Note.Pitches.C, false, 0, 1);
    s.add(c2);
    sheet.add(s);
    assertTrue(c2.equals(c2Dup));
    for (Note n : s) {
      if (n.equals(c2Dup)) {
        throw new IllegalArgumentException("Already placed a note");
      }
    }
  }

  /**
   * test remove
   */
  @Test
  public void testRemove0() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, true, 1, 1);
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 2, 1);

    Note c2Dup = new Note(2, 2, 1, Note.Pitches.C, true, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.removeNote(c2);
    m3.addNote(c2Dup);
  }

  @Test
  public void testRemove1() {
    Note c2 = new Note(3, 2, 2, Note.Pitches.C, true, 1, 1);
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 2, 1);

    Note c2Dup = new Note(1, 2, 2, Note.Pitches.C, true, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.removeNote(c2);
    // wouldn't work if they're not identical:
    // m2.removeNote(c2Dup) will do nothing.
    assertTrue(m3.getHeadNotes().get(c2.getStartMeasure()).isEmpty());
  }

  /**
   * removing a tail note.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveException0() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 0, 1);
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.removeNote(c2);
  }

  /**
   * removing a non-existing note.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveException1() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 0, 1);
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.removeNote(d2);
  }

  /**
   * moving a note.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveException1() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 0, 1);
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.move(d2, Note.Pitches.F, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException2() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 0, 1);
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.move(d2, Note.Pitches.C, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException3() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 0, 1);
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.move(d2, Note.Pitches.E, 2, 2);
    m3.move(c2, Note.Pitches.D, 2, 0);
  }

  @Test
  public void testMove0() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, true, 1, 1);
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 1, 1);
    Note e2 = new Note(1, 2, 0, Note.Pitches.E, true, 1, 1);

    Note c2Old = new Note(4, 2, 2, Note.Pitches.C, true, 1, 1);
    Note d2Old = new Note(1, 2, 0, Note.Pitches.D, true, 1, 1);

    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    assertFalse(d2.equals(e2));
    m3.move(d2, Note.Pitches.E, 2, 0);

    m3.move(c2, Note.Pitches.D, 2, 0);
    m3.move(d2, Note.Pitches.C, 2, 2);
    assertTrue(c2.equals(d2Old));
    assertTrue(d2.equals(c2Old));

  }

  /**
   * Test find
   */

  @Test
  public void testFind0() {
    Note c2 = new Note(1, 2, 0, Note.Pitches.C, true, 1, 1);
    Note c2Dup = new Note(1, 2, 0, Note.Pitches.C, true, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    assertEquals(c2, m3.find(c2Dup.getStartMeasure(), c2Dup.getDuration(),
            c2Dup.getOctave(), c2Dup.toMidiIndex()));
  }

  @Test
  public void testFind1() {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 1, 1);
    Note c2Dup = new Note(1, 2, 0, Note.Pitches.C, true, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    Note c2tail = m3.find(1, 1, 2, 60);
    assertEquals(c2tail, m3.find(c2Dup.getStartMeasure() + 1, c2Dup.getDuration(),
            c2Dup.getOctave(), c2Dup.toMidiIndex()));
  }

  @Test
  public void testFind2() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, true, 1, 1);
    assertEquals(36, c2.toMidiIndex());
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    Note c2tail = m3.find(3, 1, 2, 36);
    Note c2tail2 = m3.find(4, 1, 2, 36);
    Note c2tail3 = m3.find(5, 1, 2, 36);
    assertTrue(c2.equals(c2tail));
    assertEquals(c2tail, m3.find(c2.getStartMeasure() + 1, 1, c2.getOctave(), c2
            .toMidiIndex()));
    assertEquals(c2tail2, m3.find(c2tail.getStartMeasure() + 1, 1, c2.getOctave
            (), c2
            .toMidiIndex()));
    assertEquals(c2tail3, m3.find(c2.getStartMeasure() + 3, 1, c2.getOctave
            (), c2
            .toMidiIndex()));
  }

  /**
   * Test getHighest()
   */
  @Test
  public void testGetHighest0() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 1, 1);
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 2, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    assertTrue(m3.getHighest().equals(d2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetHighestException1() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.getHighest();
  }

  /**
   * Test getLowest()
   */
  @Test
  public void testGetLowest0() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 2, 1);
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    assertTrue(m3.getLowest().equals(c2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLowestException1() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.getHighest();
  }

  /**
   * Tests for setting the high and low in the addNote.
   */
  @Test
  public void testSet0() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    assertEquals(c2.toString(), m3.getHigh());
    assertEquals(c2.toString(), m3.getLow());
  }

  @Test
  public void testSet1() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 0, 1);
    Note d2 = new Note(2, 2, 0, Note.Pitches.D, true, 0, 1);
    Note e2 = new Note(2, 2, 0, Note.Pitches.E, true, 0, 1);
    Note c1 = new Note(1, 1, 0, Note.Pitches.C, false, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(e2);
    m3.addNote(c1);
    assertEquals(e2.toString(), m3.getHigh());
    assertEquals(c1.toString(), m3.getLow());
  }

  /**
   * test get pitches.
   */
  @Test
  public void testGetPitches0() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 1, 1);
    Note d2 = new Note(2, 2, 0, Note.Pitches.D, true, 2, 1);
    Note e2 = new Note(2, 2, 0, Note.Pitches.E, true, 3, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(e2);
    List<String> list = new ArrayList<String>();
    list.add(c2.toString());
    list.add("C#2");
    list.add(d2.toString());
    list.add("D#2");
    list.add(e2.toString());
    assertEquals(list, m3.getPitches());
  }

  /**
   * test get beats.
   */
  @Test
  public void testGetBeats0() {
    Note c2 = new Note(4, 2, 2, Note.Pitches.C, false, 0, 1);
    Note d2 = new Note(2, 2, 0, Note.Pitches.D, true, 0, 1);
    Note e2 = new Note(2, 2, 0, Note.Pitches.E, true, 3, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(d2);
    m3.addNote(e2);
    assertEquals(6, m3.getBeats());
  }

  /**
   * test get notes.
   */
  @Test
  public void testGetNotes0() {
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note e2 = new Note(1, 2, 1, Note.Pitches.E, true, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(d2);
    m3.addNote(e2);
    List<Set<Note>> list = new ArrayList<>();
    HashSet<Note> measure1 = new HashSet<>();
    HashSet<Note> measure2 = new HashSet<>();
    measure1.add(d2);
    measure2.add(e2);
    list.add(measure1);
    list.add(measure2);
    assertEquals(list, m3.getNotes());
  }

  /**
   * test get head notes.
   */
  @Test
  public void testGetHeadNotes0() {
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(3, 2, 1, Note.Pitches.C, true, 0, 1);
    Note e2 = new Note(1, 2, 1, Note.Pitches.E, false, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(d2);
    m3.addNote(e2);
    m3.addNote(c2);
    List<Set<Note>> list = new ArrayList<>();
    HashSet<Note> measure1 = new HashSet<>();
    HashSet<Note> measure2 = new HashSet<>();
    HashSet<Note> measure3 = new HashSet<>();
    measure1.add(d2);
    measure2.add(c2);
    list.add(measure1);
    list.add(measure2);
    list.add(measure3);
    list.add(measure3);
    assertEquals(list, m3.getHeadNotes());
  }

  /**
   * test get current measure.
   */
  @Test
  public void testGetCurrentMeasure0() {
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(3, 2, 1, Note.Pitches.C, true, 0, 1);
    Note e2 = new Note(1, 2, 1, Note.Pitches.E, false, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(d2);
    m3.addNote(e2);
    m3.addNote(c2);
    assertEquals(0, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(1, m3.getCurrentMeasure());
  }

  @Test
  public void testGetCurrentMeasure1() {
    Model m3 = ModelImpl.builder().build();
    assertEquals(0, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(0, m3.getCurrentMeasure());
  }

  /**
   * Test set current measure.
   */
  @Test
  public void testSetCurrentMeasure0() {
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(3, 2, 1, Note.Pitches.C, true, 0, 1);
    Note e2 = new Note(1, 2, 1, Note.Pitches.E, false, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(d2);
    m3.addNote(e2);
    m3.addNote(c2);
    assertEquals(4, m3.getHeadNotes().size());
    assertEquals(0, m3.getCurrentMeasure());
    m3.setCurrentMeasure(3);
    assertEquals(3, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(3, m3.getCurrentMeasure());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentMeasure1() {
    Model m3 = ModelImpl.builder().build();
    assertEquals(0, m3.getCurrentMeasure());
    m3.updateMeasure();
    m3.setCurrentMeasure(1);
  }
}
