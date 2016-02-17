package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link Note}
 */
public class NoteTest {

  /**
   * Test exceptions in the constructor
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor0() {
    Note n = new Note(0, 1, 0, Note.Pitches.A, true, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2() {
    Note n = new Note(1, -1, 0, Note.Pitches.A, false, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3() {
    Note n = new Note(1, 0, -2, Note.Pitches.AFlat, false, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor4() {
    Note n = new Note(1, 0, -2, Note.Pitches.AFlat, false, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor5() {
    Note n = new Note(1, 0, -2, Note.Pitches.AFlat, false, 11, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor6() {
    Note n = new Note(1, 0, -2, Note.Pitches.AFlat, false, 11, 128);
  }

  /**
   * Test for method equals
   */
  @Test
  public void testEquals0() {
    Note AFlat = new Note(1, 0, 1, Note.Pitches.AFlat, false, 0, 1);
    Note AFlat1 = new Note(2, 0, 1, Note.Pitches.AFlat, true, 0, 1);
    assertTrue(AFlat.equals(AFlat1));
  }

  @Test
  public void testEquals1() {
    Note AFlat = new Note(1, 0, 1, Note.Pitches.AFlat, false, 0, 1);
    Note AFlat1 = new Note(2, 0, 4, Note.Pitches.AFlat, true, 0, 2);
    assertFalse(AFlat.equals(AFlat1));
  }

  @Test
  public void testEquals2() {
    //some parts overlap
    Note AFlat = new Note(4, 0, 1, Note.Pitches.AFlat, false, 0, 1);
    Note AFlat1 = new Note(2, 0, 3, Note.Pitches.AFlat, true, 0, 3);
    assertTrue(AFlat.equals(AFlat1));
  }

  @Test
  public void testEquals3() {
    //some parts overlap
    Note c2 = new Note(4, 2, 0, Note.Pitches.C, true, 0, 1);
    Note c2Dup = new Note(1, 2, 2, Note.Pitches.C, false, 0, 2);
    assertTrue(c2.equals(c2Dup));
  }

  @Test
  public void testEquals4() {
    Note c2 = new Note(1, 2, 0, Note.Pitches.C, true, 0, 1);
    Note c3 = new Note(1, 3, 0, Note.Pitches.C, true, 0, 1);
    assertFalse(c2.equals(c3));
  }

  @Test
  public void testEquals5() {
    //some parts overlap
    Note c2 = new Note(4, 2, 0, Note.Pitches.C, true, 0, 2);
    Note c2Dup = new Note(1, 2, 2, Note.Pitches.C, false, 1, 2);
    assertFalse(c2.equals(c2Dup));
  }

  /**
   * Test moveTo.
   */
  public void testMoveTo() {

    Note AFlat = new Note(1, 0, 1, Note.Pitches.AFlat, false, 0, 1);
    Note A = new Note(1, 0, 1, Note.Pitches.A, false, 0, 1);
    AFlat.moveTo(Note.Pitches.A, AFlat.getOctave(), AFlat.getStartMeasure());
    assertTrue(AFlat.equals(A));
  }

  /**
   * Test up
   */
  @Test
  public void testUp0() {
    Note b0 = new Note(1, 0, 0, Note.Pitches.B, false, 0, 2);
    Note b1 = new Note(1, 1, 0, Note.Pitches.B, false, 0, 3);
    b0.up();
    b0.up();
    b0.up();
    b0.up();
    b0.up();
    b0.up();
    b0.up();
    b0.up();
    b0.up();
    b0.up();
    b0.up();
    b0.up();
    assertEquals(true, b0.equals(b1));
  }

  @Test
  public void testUp1() {
    Note b0 = new Note(1, 0, 0, Note.Pitches.B, false, 0, 1);
    Note e1 = new Note(1, 1, 0, Note.Pitches.E, false, 0, 1);
    b0.up();
    b0.up();
    b0.up();
    b0.up();
    b0.up();
    assertEquals(true, b0.equals(e1));
  }

  /**
   * Test HowFarUp.
   */
  @Test
  public void testHowFarUp0() {
    Note AFlat = new Note(1, 0, 1, Note.Pitches.AFlat, false, 0, 1);
    Note A = new Note(1, 0, 1, Note.Pitches.A, false, 0, 2);
    // duration, octave, starting measure
    assertEquals("G#0", AFlat.toString());
    assertEquals(1, A.howFarUp(AFlat));
  }

  @Test
  public void testHowFarUp1() {
    Note AFlat = new Note(1, 1, 1, Note.Pitches.AFlat, false, 0, 4);
    Note AFlatOctaveDown = new Note(1, 0, 1, Note.Pitches.AFlat, false, 0, 1);

    assertEquals(12, AFlat.howFarUp(AFlatOctaveDown));
  }

  @Test
  public void testHowFarUp2() {
    Note d2 = new Note(3, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(1, 2, 0, Note.Pitches.C, true, 0, 1);
    assertEquals(2, d2.howFarUp(c2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHowFarUp3() {
    Note d2 = new Note(3, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(1, 2, 0, Note.Pitches.C, true, 0, 1);
    assertEquals(2, c2.howFarUp(d2));
  }

  @Test
  public void testHowFarUp4() {
    Note c3 = new Note(1, 3, 0, Note.Pitches.C, true, 0, 1);
    Note c2 = new Note(1, 2, 0, Note.Pitches.C, true, 1, 2);
    assertEquals(12, c3.howFarUp(c2));
  }

  /**
   * Test compare to
   */
  @Test
  public void testCompareTo0() {
    Note d2 = new Note(3, 2, 0, Note.Pitches.D, true, 0, 3);
    Note c2 = new Note(1, 2, 0, Note.Pitches.C, true, 2, 2);
    assertEquals(1, d2.compareTo(c2));
    assertEquals(0, d2.compareTo(d2));
    assertEquals(-1, c2.compareTo(d2));
  }

  @Test
  public void testCompareTo1() {
    Note c3 = new Note(3, 3, 0, Note.Pitches.C, true, 3, 1);
    Note c3tail = new Note(1, 3, 3, Note.Pitches.C, false, 1, 2);
    Note c2 = new Note(1, 2, 0, Note.Pitches.C, true, 2, 1);
    assertEquals(1, c3.compareTo(c2));
    assertEquals(0, c3.compareTo(c3tail));
    assertEquals(-1, c2.compareTo(c3));
  }

  @Test
  public void testCompareTo2() {
    Note c8 = new Note(1, 8, 0, Note.Pitches.C, true, 1, 1);
    Note c1 = new Note(1, 1, 0, Note.Pitches.C, true, 2, 1);
    assertEquals(-1, c1.compareTo(c8));
    assertEquals(1, c8.compareTo(c1));
  }

  /**
   * Test intToOctave.
   */
  @Test
  public void testIntToOctave0() {
    Note c4 = new Note(1, 4, 0, Note.Pitches.C, true, 3, 1);
    assertEquals(4, Note.intToOctave(60));
    assertEquals(4, c4.getOctave());
  }

  /**
   * Test intToPitch.
   */
  @Test
  public void testIntToPitch0() {
    Note c4 = new Note(1, 4, 0, Note.Pitches.C, true, 5, 2);
    assertEquals(Note.Pitches.C, Note.intToPitch(60));
    assertEquals(Note.Pitches.C, c4.getPitch());
  }

  @Test
  public void testIntToPitch1() {

    Note b5 = new Note(1, 5, 0, Note.Pitches.B, true, 6, 1);
    assertEquals(83, b5.toMidiIndex());
    assertEquals(b5.getPitch(), Note.intToPitch(83));
  }


  @Test
  public void testIntToPitch2() {

    Note e5 = new Note(1, 5, 0, Note.Pitches.E, true, 4, 1);
    assertEquals(76, e5.toMidiIndex());
    assertEquals(e5.getPitch(), Note.intToPitch(76));
  }

  /**
   * Test toMidiIndex.
   */
  @Test
  public void testToMidiIndex0() {
    Note c4 = new Note(1, 4, 0, Note.Pitches.C, true, 6, 2);
    assertEquals(60, c4.toMidiIndex());
  }

  @Test
  public void testToMidiIndex1() {
    Note c3 = new Note(1, 3, 0, Note.Pitches.C, true, 7, 1);
    assertEquals(48, c3.toMidiIndex());
  }

  @Test
  public void testToMidiIndex2() {
    Note b5 = new Note(1, 5, 0, Note.Pitches.B, true, 5, 1);
    assertEquals(83, b5.toMidiIndex());
  }

  @Test
  public void testToMidiIndex3() {
    Note fs2 = new Note(1, 2, 0, Note.Pitches.FSharp, true, 5, 1);
    assertEquals(42, fs2.toMidiIndex());
  }

  /**
   * Test down
   */
  @Test
  public void testDown0() {
    Note c4 = new Note(1, 4, 0, Note.Pitches.C, true, 1, 10);
    Note b3 = new Note(1, 3, 0, Note.Pitches.B, true, 1, 10);
    c4.down();
    assertTrue(b3.equals(c4));
  }
  //todo test string to octave, string to pitch.

}

