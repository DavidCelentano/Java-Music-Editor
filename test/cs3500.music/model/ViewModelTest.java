package cs3500.music.model;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.music.view.ViewModel;


/**
 * Ensures that the viewModel works as expected
 */
public class ViewModelTest {

  @Test
  public void testConstructor() {
    ViewModel vm = new ViewModel(null);
  }

  @Test
  public void testConstructor2() {
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(3, 2, 1, Note.Pitches.C, true, 0, 1);
    Note e2 = new Note(1, 2, 1, Note.Pitches.E, false, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(d2);
    m3.addNote(e2);
    m3.addNote(c2);
    ViewModel vm = new ViewModel(m3);
  }

  @Test
  public void testToString() {
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(3, 2, 1, Note.Pitches.C, true, 0, 1);
    Note e2 = new Note(1, 2, 1, Note.Pitches.E, false, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(d2);
    m3.addNote(e2);
    m3.addNote(c2);
    ViewModel vm = new ViewModel(m3);
    assertEquals(vm.toString(), m3.toString());
  }

  @Test
  public void testGetRange() {
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(3, 2, 1, Note.Pitches.C, true, 0, 1);
    Note e2 = new Note(1, 2, 1, Note.Pitches.E, false, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(d2);
    m3.addNote(e2);
    m3.addNote(c2);
    ViewModel vm = new ViewModel(m3);
    assertEquals(vm.getRange(), m3.getPitches());
  }

  @Test
  public void testGetBeats() {
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(3, 2, 1, Note.Pitches.C, true, 0, 1);
    Note e2 = new Note(1, 2, 1, Note.Pitches.E, false, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(d2);
    m3.addNote(e2);
    m3.addNote(c2);
    ViewModel vm = new ViewModel(m3);
    assertEquals(vm.getBeats(), m3.getBeats());
  }

  @Test
  public void testGetNotes() {
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(3, 2, 1, Note.Pitches.C, true, 0, 1);
    Note e2 = new Note(1, 2, 1, Note.Pitches.E, false, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(d2);
    m3.addNote(e2);
    m3.addNote(c2);
    ViewModel vm = new ViewModel(m3);
    assertEquals(vm.getNotes(), m3.getNotes());
  }

  @Test
  public void testGetHighest() {
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(3, 2, 1, Note.Pitches.C, true, 0, 1);
    Note e2 = new Note(1, 2, 1, Note.Pitches.E, false, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(d2);
    m3.addNote(e2);
    m3.addNote(c2);
    ViewModel vm = new ViewModel(m3);
    assertTrue(vm.getHighest().equals(m3.getHighest()));
  }

  @Test
  public void testGetLowest() {
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(3, 2, 1, Note.Pitches.C, true, 0, 1);
    Note e2 = new Note(1, 2, 1, Note.Pitches.E, false, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(d2);
    m3.addNote(e2);
    m3.addNote(c2);
    ViewModel vm = new ViewModel(m3);
    assertTrue(vm.getLowest().equals(m3.getLowest()));
  }

  @Test
  public void testGetHeadNotes() {
    Note d2 = new Note(1, 2, 0, Note.Pitches.D, true, 0, 1);
    Note c2 = new Note(3, 2, 1, Note.Pitches.C, true, 0, 1);
    Note e2 = new Note(1, 2, 1, Note.Pitches.E, false, 0, 1);
    Model m3 = ModelImpl.builder().build();
    m3.addNote(d2);
    m3.addNote(e2);
    m3.addNote(c2);
    ViewModel vm = new ViewModel(m3);
    assertEquals(vm.getHeadNotes(), m3.getHeadNotes());
  }
}


