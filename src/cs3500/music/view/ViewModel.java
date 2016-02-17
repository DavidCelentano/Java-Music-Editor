package cs3500.music.view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs3500.music.model.Model;
import cs3500.music.model.Note;

/**
 * Wrapper class view model. Wraps the composition so that the data can be
 * passed on to the view without modifying the model.
 */
public final class ViewModel {
  private final Model model;

  public ViewModel(Model model) {
    this.model = model;
  }

  @Override
  public String toString() {
    return model.toString();
  }

  public List<String> getRange() {
    return model.getPitches();
  }

  public int getBeats() {
    return model.getBeats();
  }

  public List<Set<Note>> getNotes() {
    return model.getNotes();
  }

  public Note getHighest() {
    return model.getHighest();
  }

  public Note getLowest() {
    return model.getLowest();
  }

  public List<Set<Note>> getHeadNotes() {
    return model.getHeadNotes();
  }

  public Set<Note> notesAtBeat(int beat) {
    return model.notesAtBeat(beat);
  }

  public int getCurrentMeasure() {
    return model.getCurrentMeasure();
  }

  public List<List<Integer>> getEndings() {
    return model.getEndings();
  }

  public int getStartRepeat() {
    return model.getStartRepeat();
  }

  public int getEndRepeat() {
    return model.getEndRepeat();
  }

}
