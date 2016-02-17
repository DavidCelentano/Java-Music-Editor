package cs3500.music.controller;

import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.Model;
import cs3500.music.model.ModelImpl;
import cs3500.music.model.Note;
import cs3500.music.view.CompositeView;
import cs3500.music.view.ViewModel;

import static org.junit.Assert.*;

/**
 * Test the controller
 */
public class ControllerImplTest {

  @Test
  public void testInitialize0() throws Exception {

    Model m3 = ModelImpl.builder().build();
    ViewModel vm = new ViewModel(m3);
    CompositeView cv = new CompositeView(vm);
    Controller controller = new ControllerImpl(cv, m3, "test 1");
    controller.initialize();
    assertEquals(ControllerImpl.State.Paused, controller.getState());
  }

  @Test
  public void testAddNote0() throws Exception {
    Model m3 = ModelImpl.builder().build();
    Note c4 = new Note(4, 4, 0, Note.intToPitch(60), true, 1, 100);
    ViewModel vm = new ViewModel(m3);
    CompositeView cv = new CompositeView(vm);
    Controller controller = new ControllerImpl(cv, m3, "test 1");
    assertTrue(m3.getHeadNotes().isEmpty());
    controller.addNote(0, 4, 4, 60);
    assertTrue(c4.equals(m3.find(0, 4, 4, 60)));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNote0() throws Exception {
    Model m3 = ModelImpl.builder().build();
    Note c4 = new Note(4, 4, 0, Note.intToPitch(60), true, 1, 100);
    ViewModel vm = new ViewModel(m3);
    CompositeView cv = new CompositeView(vm);
    Controller controller = new ControllerImpl(cv, m3, "test 1");
    assertTrue(m3.getHeadNotes().isEmpty());
    controller.addNote(0, 4, 4, 60);
    assertTrue(c4.equals(m3.find(0, 4, 4, 60)));
    controller.removeNote(0, 4, 4, 60);
    m3.find(0, 4, 4, 60);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNote0() throws Exception {
    Model m3 = ModelImpl.builder().build();
    Note c4 = new Note(4, 4, 0, Note.intToPitch(60), true, 1, 100);
    Note b3 = new Note(10, 4, 4, Note.intToPitch(59), true, 1, 100);
    assertTrue(m3.getHeadNotes().isEmpty());
    m3.addNote(c4);
    m3.addNote(b3);
    ViewModel vm = new ViewModel(m3);
    CompositeView cv = new CompositeView(vm);
    Controller controller = new ControllerImpl(cv, m3, "test 1");
    controller.moveNote(0, 4, 2, 4, 4, 60, 60);
    m3.find(0, 4, 4, 60);
  }

  @Test
  public void testMoveNote1() throws Exception {
    Model m3 = ModelImpl.builder().build();
    Note c4 = new Note(4, 4, 0, Note.intToPitch(60), true, 1, 100);
    Note b3 = new Note(10, 4, 4, Note.intToPitch(59), true, 1, 100);
    assertTrue(m3.getHeadNotes().isEmpty());
    m3.addNote(c4);
    m3.addNote(b3);
    ViewModel vm = new ViewModel(m3);
    CompositeView cv = new CompositeView(vm);
    Controller controller = new ControllerImpl(cv, m3, "test 1");
    controller.moveNote(0, 4, 2, 4, 4, 60, 60);
    assertTrue(c4.equals(m3.find(2, 4, 4, 60)));
  }

  @Test
  public void testToBeginning() throws Exception {
    Model m3 = ModelImpl.builder().build();
    ViewModel vm = new ViewModel(m3);
    CompositeView cv = new CompositeView(vm);
    Controller controller = new ControllerImpl(cv, m3, "test 1");
    controller.toBeginning();
    assertEquals(0, vm.getCurrentMeasure());
  }

  @Test
  public void testToEnd() throws Exception {
    Model m3 = ModelImpl.builder().build();
    ViewModel vm = new ViewModel(m3);
    CompositeView cv = new CompositeView(vm);
    Controller controller = new ControllerImpl(cv, m3, "test 1");
    controller.toEnd();
    assertEquals(0, vm.getCurrentMeasure());
  }

}