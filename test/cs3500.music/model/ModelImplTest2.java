package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * test on new functionality.
 */
public class ModelImplTest2 {

  @Test
  public void testSetSkip() throws Exception {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 1, 1);
    Note c3 = new Note(2, 3, 0, Note.Pitches.C, true, 2, 3);
    Note c2long = new Note(8, 2, 3, Note.Pitches.C, true, 0, 2);
    Note d2 = new Note(5, 2, 2, Note.Pitches.D, true, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(c3);
    m3.addNote(c2long);
    m3.addNote(d2);
    m3.setCurrentMeasure(1);
    m3.setSkipStart(2);
    m3.setSkipEnd(2); //skip the second measure
    m3.updateMeasure();
    assertEquals(3, m3.getCurrentMeasure());
  }


  @Test
  public void testSetSkip1() throws Exception {
    Note c2 = new Note(2, 2, 5, Note.Pitches.C, true, 1, 1);
    Note c3 = new Note(2, 3, 0, Note.Pitches.C, true, 2, 3);
    Note c2long = new Note(8, 2, 3, Note.Pitches.C, true, 0, 2);
    Note d2 = new Note(5, 2, 2, Note.Pitches.D, true, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(c3);
    m3.addNote(c2long);
    m3.addNote(d2);
    m3.setCurrentMeasure(1);
    m3.setSkipStart(2);
    m3.setSkipEnd(4); //skip the 2-4 measures
    m3.updateMeasure();
    assertEquals(5, m3.getCurrentMeasure());
    assertEquals(2, m3.getStartSkip());
    assertEquals(4, m3.getEndSkip());
  }

  @Test
  public void testSetSkip2() throws Exception {
    Note c2 = new Note(2, 2, 0, Note.Pitches.C, true, 1, 1);
    Note c3 = new Note(2, 3, 0, Note.Pitches.C, true, 2, 3);
    Note c2long = new Note(8, 2, 3, Note.Pitches.C, true, 0, 2);
    Note d2 = new Note(5, 2, 2, Note.Pitches.D, true, 1, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(c2);
    m3.addNote(c3);
    m3.addNote(c2long);
    m3.addNote(d2);
    m3.setCurrentMeasure(1);
    m3.setSkipStart(3);
    m3.setSkipEnd(3); //skip the third measure
    m3.updateMeasure();
    assertEquals(2, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(4, m3.getCurrentMeasure());
  }

  @Test
  public void testGetStartRepeat() throws Exception {
    Note c4 = new Note(2, 4, 5, Note.Pitches.C, true, 1, 1);
    Note c3 = new Note(2, 3, 0, Note.Pitches.C, true, 2, 3);
    Note c2long = new Note(8, 2, 3, Note.Pitches.C, true, 0, 2);
    Note d2 = new Note(5, 2, 2, Note.Pitches.D, true, 1, 1);
    Model m3 = ModelImpl.builder().addNote(2, 5, 1, 60, 80).setStartRepeat(2)
            .setEndRepeat(3).build();
    assertEquals(2, m3.getStartRepeat());
    assertEquals(3, m3.getEndRepeat());
  }

  @Test
  public void testGetStartRepeat0() throws Exception {
    Model m3 = ModelImpl.builder().addNote(2, 5, 1, 60, 80).setEndRepeat(2)
            .build();
    assertEquals(2, m3.getEndRepeat());
    assertEquals(0, m3.getCurrentMeasure());
    m3.updateMeasure(); //beat 1
    assertEquals(1, m3.getCurrentMeasure());
    m3.updateMeasure(); //beat 2
    assertEquals(2, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(0, m3.getCurrentMeasure());
    // repeat the second time=
    m3.updateMeasure(); //beat 1
    assertEquals(1, m3.getCurrentMeasure());
    m3.updateMeasure(); //beat 2
    assertEquals(2, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(3, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(4, m3.getCurrentMeasure());
  }

  @Test
  public void testEndings0() throws Exception {
    Model m3 = ModelImpl.builder().addNote(2, 5, 1, 60, 80).setEndRepeat(2)
            .addEnding(1, 2).addEnding(2, 3)
            .build();
    Note c4 = new Note(2, 4, 5, Note.Pitches.C, true, 1, 1);
    Note c3 = new Note(2, 3, 0, Note.Pitches.C, true, 2, 3);
    Note c2long = new Note(8, 2, 3, Note.Pitches.C, true, 0, 2);
    Note d2 = new Note(5, 2, 2, Note.Pitches.D, true, 1, 1);
    m3.addNote(c4);
    m3.addNote(c3);
    m3.addNote(c2long);
    m3.addNote(d2);

    assertEquals(0, m3.getStartSkip());
    assertEquals(0, m3.getEndSkip());

    assertEquals(0, m3.getCurrentMeasure());
    m3.updateMeasure(); //beat 1
    assertEquals(1, m3.getCurrentMeasure());
    m3.updateMeasure(); //beat 2
    assertEquals(2, m3.getCurrentMeasure());

    assertEquals(0, m3.getStartSkip());
    assertEquals(0, m3.getEndSkip());

    m3.updateMeasure();
    assertFalse(m3.getEndings().isEmpty());
    assertEquals(0, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(2, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(3, m3.getCurrentMeasure());

    assertEquals(1, m3.getStartSkip());
    assertEquals(1, m3.getEndSkip());

    m3.updateMeasure();
    assertEquals(4, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(5, m3.getCurrentMeasure());
  }

  @Test
  public void testEndings1() throws Exception {
    Model m3 = ModelImpl.builder().addNote(2, 5, 1, 60, 80).setEndRepeat(2).build();
    Note c4 = new Note(2, 4, 5, Note.Pitches.C, true, 1, 1);
    Note c3 = new Note(2, 3, 0, Note.Pitches.C, true, 2, 3);
    Note c2long = new Note(8, 2, 3, Note.Pitches.C, true, 0, 2);
    Note d2 = new Note(5, 2, 2, Note.Pitches.D, true, 1, 1);
    m3.addNote(c4);
    m3.addNote(c3);
    m3.addNote(c2long);
    m3.addNote(d2);

    assertTrue(m3.getEndings().isEmpty());
    m3.addEnding(1, 2);
    m3.addEnding(2, 3);
    assertTrue(!m3.getEndings().isEmpty());

    assertEquals(0, m3.getCurrentMeasure());
    m3.updateMeasure(); //beat 1
    assertEquals(1, m3.getCurrentMeasure());
    m3.updateMeasure(); //beat 2
    assertEquals(2, m3.getCurrentMeasure());

    m3.updateMeasure();
    assertFalse(m3.getEndings().isEmpty());
    assertEquals(0, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(2, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(3, m3.getCurrentMeasure());

    assertEquals(1, m3.getStartSkip());
    assertEquals(1, m3.getEndSkip());

    m3.updateMeasure();
    assertEquals(4, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(5, m3.getCurrentMeasure());
  }


  @Test
  public void testEndings2() throws Exception {
    Model m3 = ModelImpl.builder().addNote(2, 5, 1, 60, 80).setEndRepeat(2).build();
    Note c4 = new Note(2, 4, 5, Note.Pitches.C, true, 1, 1);
    Note c3 = new Note(2, 3, 0, Note.Pitches.C, true, 2, 3);
    Note c2long = new Note(8, 2, 3, Note.Pitches.C, true, 0, 2);
    Note d2 = new Note(5, 2, 2, Note.Pitches.D, true, 1, 1);
    m3.addNote(c4);
    m3.addNote(c3);
    m3.addNote(c2long);
    m3.addNote(d2);

    assertTrue(m3.getEndings().isEmpty());

    assertEquals(0, m3.getCurrentMeasure());
    m3.updateMeasure(); //beat 1
    assertEquals(1, m3.getCurrentMeasure());
    m3.updateMeasure(); //beat 2
    assertEquals(2, m3.getCurrentMeasure());

    m3.addEnding(1, 2);
    m3.addEnding(3, 4);
    assertTrue(!m3.getEndings().isEmpty());

    m3.updateMeasure();
    assertFalse(m3.getEndings().isEmpty());
    assertEquals(0, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(2, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(3, m3.getCurrentMeasure());
    m3.updateMeasure();
    assertEquals(4, m3.getCurrentMeasure());

    m3.updateMeasure();
    assertEquals(5, m3.getCurrentMeasure());
  }


}