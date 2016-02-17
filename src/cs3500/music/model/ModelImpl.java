package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs3500.music.util.CompositionBuilder;

/**
 * A model implementing the {@link Model} interface.
 */
public class ModelImpl implements Model {
  /**
   * The current measure
   */
  private int currentMeasure;
  private final int tempo;
  /**
   * The lowest and highest notes in the display.
   */
  private Note low, high;
  /**
   * The status of the music model.
   */
  private Status status;
  /**
   * The grid of the note. The outer list is the time.
   */
  private List<Set<Note>> sheet;
  /**
   * The notes passed in.
   */
  private List<Note> notes;
  /**
   * The start and end of a repeated section.
   */
  private int startRepeat, endRepeat;
  /**
   * The start and end of the skipped region.
   */
  private int startSkip, endSkip;
  /**
   * count how many times the repeat is played. should not exceed 1.
   */
  private int repeatedTimes;
  /**
   * the start and end of an ending.
   */
  private int endingStart, endingEnd;
  /**
   * the list of endings in this piece.
   */
  private List<List<Integer>> endings;

  /**
   * Constructs a new music model with the given parameters.
   *
   * @param tempo  the tempo (50000-250000).
   * @param notes, the notes passed in to the model.
   * @param notes  is empty.
   * @param startRepeat is the start of the repeat
   * @param endRepeat is the end of the repeat
   * @param endings the list of endings
   * @throws IllegalArgumentException when the tempo is invalid and when
   */
  // INVARIANT! tempo is between 50000 and 250000.
  private ModelImpl(int tempo, List<Note> notes, int startRepeat,
                    int endRepeat, List<List<Integer>> endings) {
    if (tempo < 50000 || tempo > 250000) {
      throw new IllegalArgumentException("Invalid tempo.");
    }
    if (notes.isEmpty()) {
      throw new IllegalArgumentException("This shouldn't happen.");
    }
    this.tempo = tempo;
    this.currentMeasure = DEFAULT_START;
    this.status = Status.Playing;
    this.low = new Note(1, 8, 0, Note.Pitches.C, true, 0, 1);
    this.high = new Note(1, 0, 0, Note.Pitches.A, true, 0, 1);
    this.sheet = new ArrayList<>();
    this.addAll(notes);
    this.startRepeat = startRepeat;
    this.endRepeat = endRepeat;
    this.repeatedTimes = 0;
    this.endings = endings;
    if (!endings.isEmpty()) {
      this.endingStart = endings.get(0).get(0);
      this.endingEnd = endings.get(0).get(1);
    }
  }

  /**
   * Constructs a new music model with the given parameters.
   *
   * @param tempo the tempo (50000-250000).
   * @throws IllegalArgumentException when the tempo is invalid.
   */
  // INVARIANT! tempo is between 50000 and 250000.
  private ModelImpl(int tempo) {
    if (tempo < 50000 || tempo > 250000) {
      throw new IllegalArgumentException("Invalid tempo.");
    }
    this.tempo = tempo;
    this.currentMeasure = DEFAULT_START;
    this.status = Status.Playing;
    this.low = new Note(1, 8, 0, Note.Pitches.C, true, 0, 1);
    this.high = new Note(1, 0, 0, Note.Pitches.A, true, 0, 1);
    this.sheet = new ArrayList<>();
    this.endings = new ArrayList<>();
  }

  /**
   * Constructs a builder for creating a {@code model} instance. Defaults are
   * shown above.
   *
   * @return the new builder
   */
  public static CompositionBuilder<Model> builder() {
    return new Builder();
  }

  /**
   * Add a note into the music, if the note is longer than 1 beat, the note
   * should be continuously added to the next measure until the note ends.
   *
   * also adding the note to the note list.
   *
   * @param n the note added.
   */
  @Override
  public void addNote(Note n) {
    if (this.sheet.size() < n.getDuration() + n.getStartMeasure()) {
      for (int i = this.sheet.size(); i < n.getDuration() + n.getStartMeasure(); i++) {
        sheet.add(new HashSet<>());
      }
    }
    for (int i = 0; i < n.getDuration(); i++) {
      for (Note t : this.sheet.get(i + n.getStartMeasure())) {
        if (t.equals(n)) {
          throw new IllegalArgumentException("Already placed a note.");
        }
      }
    }
    this.sheet.get(n.getStartMeasure()).add(n);
    int currentMeasure = n.getStartMeasure() + 1;
    for (int i = 1; i < n.getDuration(); i++) {
      Note hold = new Note(1, n.getOctave(), currentMeasure,
              n.getPitch(), false, n.getInstrument(), n.getVolume());
      this.sheet.get(currentMeasure).add(hold);
      currentMeasure++;
    }

    this.high = this.getHighest();
    this.low = this.getLowest();
  }

  /**
   * Add all notes given.
   *
   * @param given the list of notes.
   */
  @Override
  public void addAll(List<Note> given) {
    for (Note n : given) {
      this.addNote(n);
    }
  }

  /**
   * Remove a note from this model.
   *
   * @param n the note removed.
   * @throws IllegalArgumentException if the note doesn't exist or it's not the
   *                                  start of the note.
   */
  @Override
  public void removeNote(Note n) {
    boolean result = false;
    for (int i = 0; i < n.getDuration(); i++) {
      for (Note t : this.sheet.get(i + n.getStartMeasure())) {
        if (t.equals(n)) {
          result = true;
        }
      }
    }
    if (result == false) {
      throw new IllegalArgumentException("note doesn't exist");
    }
    this.sheet.get(n.getStartMeasure()).remove(n);

    for (int i = 1; i < n.getDuration(); i++) {
      int currentMeasure = n.getStartMeasure() + i;

      Note hold = find(currentMeasure, 1, n.getOctave(), n
              .toMidiIndex());
      this.sheet.get(currentMeasure).remove(hold);
    }
  }


  @Override
  public Status getStatus() {
    return this.status;
  }

  @Override
  public boolean isEnded() {
    return this.currentMeasure == this.sheet.size();
  }

  @Override
  public void updateMeasure() {
    if (this.currentMeasure + 1 < this.getHeadNotes().size()) {

      if (this.currentMeasure == this.startSkip - 1) {
        this.currentMeasure = this.endSkip + 1;
      }
      // simple repeat should only be played once, where there's no
      // alternative endings.
      else if (this.currentMeasure == this.endRepeat && this.endings.isEmpty
              () && this
              .repeatedTimes < 1) {
        System.out.println("comparing the current with the end repeat");
        this.currentMeasure = startRepeat;
        this.repeatedTimes += 1;
      }
      // the first ending always ends with the end repeat, there should
      // always be more than 1 ending.
      else if (this.currentMeasure == this.endRepeat && this.endings.size() > 1
              && repeatedTimes < this.endings.size()) {
        List<Integer> first = this.endings.get(0);
        this.endings.remove(0);
        List<Integer> newFirst = this.endings.get(0);
        this.setSkipStart(first.get(0));
        this.setSkipEnd(first.get(1) - 1);
        this.endingStart = newFirst.get(0);
        this.endingEnd = newFirst.get(1);
        this.endRepeat = this.endingEnd;

        this.currentMeasure = this.startRepeat;
        System.out.println("This is the start of the old repeat: " + this
                .startRepeat);
        this.repeatedTimes += 1;
        System.out.println("This is the start of the new ending: " + this
                .endingStart);
        System.out.println("This is the end of the new ending: " + this
                .endingEnd);
        System.out.println("This is the start of the new skip: " + this
                .startSkip);
        System.out.println("This is the end of the new skip: " + this
                .endSkip);
        System.out.println("This is number of repeats: " + this
                .repeatedTimes);
      }
      else {
        this.currentMeasure++;
      }
    }
  }

  @Override
  public List<String> getPitches() {
    List<String> string = new ArrayList<>();
    Note temLow = new Note(this.low.getDuration(), this.low.getOctave(),
            this.low.getStartMeasure(), this.low.getPitch(), this.low.getIsHead(),
            this.low.getInstrument(), this.low.getVolume());
    for (int i = 0; this.high.compareTo(temLow) != -1; i++) {
      if (temLow.toString().length() == 3) {
        string.add(temLow.toString());
      } else {
        string.add(temLow.toString());
      }
      temLow.up();
    }
    return string;
  }

  /**
   * Render a console view
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (this.high.compareTo(low) == -1) {
      return "";
    }
    Note temLow = new Note(this.low.getDuration(), this.low.getOctave(),
            this.low.getStartMeasure(), this.low.getPitch(), this.low.getIsHead(),
            this.low.getInstrument(), this.low.getVolume());
    builder.append("  ");
    for (int i = 0; this.high.compareTo(temLow) != -1; i++) {
      if (temLow.toString().length() == 3) {
        builder.append(temLow.toString());
      } else {
        builder.append(" " + temLow.toString());
      }
      temLow.up();
    }
    builder.append("\n");

    for (int i = 0; i < this.sheet.size(); i++) {
      if (i < 10) {
        builder.append(i + " ");
      } else {
        builder.append(i);
      }
      List<Note> list = new ArrayList<Note>(this.sheet.get(i));
      Collections.sort(list);
      int listIndex = 0;
      Note comparator = new Note(this.low.getDuration(), this.low.getOctave(),
              this.low.getStartMeasure(), this.low.getPitch(), this.low.getIsHead(),
              this.low.getInstrument(), this.low.getVolume());

      // when the measure is empty.
      if (list.size() == 0) {
        builder.append("   ");
      }
      for (int j = 0; listIndex < list.size() && j <= this.high.howFarUp(this.low); j++) {
        if (list.get(listIndex).compareTo(comparator) == 0) {
          if (list.get(listIndex).getIsHead()) {
            builder.append(" X ");
            listIndex++;
          } else {
            builder.append(" | ");
            listIndex++;
          }
        } else {
          builder.append("   ");
        }
        comparator.up();
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  @Override
  public List<Set<Note>> getHeadNotes() {
    ArrayList<Set<Note>> copy = new ArrayList<>();

    for (Set<Note> s : this.sheet) {
      Set<Note> measure = new HashSet<>();
      for (Note n : s) {
        if (n.getIsHead()) {
          measure.add(n);
        }
      }
      copy.add(measure);
    }
    return copy;
  }

  @Override
  public Set<Note> notesAtBeat(int beat) {
    Set<Note> result = new HashSet<>();

    for (int i = 0; i < this.sheet.size(); i++) {
      for (Note n : this.sheet.get(beat)) {
        if (n.getIsHead()) {
          result.add(n);
        }
      }
    }
    return result;

  }

  /**
   * Move a note to a given pitch at a given measure
   *
   * @param n       the note being moved
   * @param pitch   the new pitch
   * @param measure the new measure
   */
  public void move(Note n, Note.Pitches pitch, int octave, int measure) {
    if (!n.getIsHead()) {
      throw new IllegalArgumentException("Can't move a tail note");
    }
    boolean result = false;
    for (int i = 0; i < n.getDuration(); i++) {
      for (Note t : this.sheet.get(i + n.getStartMeasure())) {
        if (t.equals(n)) {
          result = true;
        }
      }
    }
    if (result == false) {
      throw new IllegalArgumentException("note doesn't exist");
    } else {
      this.removeNote(n);
    }
    Note n2 = n.moveTo(pitch, octave, measure);
    for (int i = 0; i < n2.getDuration(); i++) {
      for (Note t : this.sheet.get(i + n2.getStartMeasure())) {
        if (t.equals(n2)) {
          throw new IllegalArgumentException("Already placed a note.");
        }
      }
    }
    this.addNote(n2);
  }

  @Override
  /**
   * Find a note with given parameters
   * @param start the start measure
   * @param dur duration
   * @param oct octave number
   * @param pitch pitch number
   * @return the note found
   */
  public Note find(int start, int dur, int oct, int pitch) {
    Note n = new Note(dur, oct, start, Note.intToPitch(pitch),
            true, 1, 70);
    Note result = null;
    for (Note note : this.sheet.get(start)) {
      if (note.getStartMeasure() == n.getStartMeasure() &&
      note.getOctave() == n.getOctave()  &&
      note.getPitch() == n.getPitch()) {
        result = note;
      }
    }
    if (result == null) {
      throw new IllegalArgumentException("This note does not exist");
    } else {
      return result;
    }
  }

  /**
   * Get the highest note in this piece.
   *
   * @return the highest note in this piece.
   * @throws IllegalArgumentException if there's no note in the piece.
   */
  @Override
  public Note getHighest() {
    if (this.sheet.isEmpty()) {
      throw new IllegalArgumentException("No note is added.");
    }
    Note currentHigh = new Note(this.high.getDuration(), this.high.getOctave
            (), this.high.getStartMeasure(), this.high.getPitch(), this.high
            .getIsHead(), this.high.getInstrument(), this.high.getVolume());
    for (int i = 0; i < this.sheet.size(); i++) {
      for (Note n : this.sheet.get(i)) {
        if (currentHigh.compareTo(n) == -1) {
          currentHigh = n;
        }
      }
    }
    return currentHigh;
  }

  /**
   * Get the lowest note in this piece.
   *
   * @return the lowest note in this piece.
   * @throws IllegalArgumentException if there's no note in the piece.
   */
  @Override
  public Note getLowest() {
    if (this.sheet.isEmpty()) {
      throw new IllegalArgumentException("No note is added.");
    }
    Note currentLow = new Note(this.low.getDuration(), this.low.getOctave(),
            this.low.getStartMeasure(), this.low.getPitch(), this.low
            .getIsHead(), this.low.getInstrument(), this.low.getVolume());
    for (int i = 0; i < this.sheet.size(); i++) {
      for (Note n : this.sheet.get(i)) {
        if (currentLow.compareTo(n) == 1) {
          currentLow = n;
        }
      }
    }
    return currentLow;
  }

  /**
   * Get the highest note stored in this piece.
   *
   * @return the string form of high note.
   */
  public String getHigh() {
    return this.high.toString();
  }

  /**
   * Get the highest note stored in this piece.
   *
   * @return the string form of high note.
   */
  public String getLow() {
    return this.low.toString();
  }

  /**
   * Get the number of beats stored in this piece.
   *
   * @return the number of beats stored in this piece
   */
  @Override
  public int getBeats() {
    return this.sheet.size();
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  /**
   * Get the current measure
   *
   * @return the current measure
   */
  @Override
  public int getCurrentMeasure() {
    return this.currentMeasure;
  }

  /**
   * Sets the current measure
   *
   * @param beat the beat number
   * @throws IllegalArgumentException if the beat is greater than the last
   *                                  measure of the piece.
   */
  @Override
  public void setCurrentMeasure(int beat) {
    if (beat >= this.getHeadNotes().size() && beat != 0) {
      throw new IllegalArgumentException("Beat out of bound");
    }
    this.currentMeasure = beat;
  }

  /**
   * Get notes.
   *
   * @return the notes stored in this piece
   */
  @Override
  public List<Set<Note>> getNotes() {
    ArrayList<Set<Note>> copy = new ArrayList<>();
    for (Set<Note> measure : this.sheet) {
      copy.add(measure);
    }
    return copy;
  }

  @Override
  public void setSkipStart(int start) {
    this.startSkip += start;
  }

  @Override
  public void setSkipEnd(int end) {
    this.endSkip += end;
  }

  @Override
  public void setEndingStart(int start) {
    this.endingStart = start;
  }

  @Override
  public void setEndingEnd(int end) {
    this.endingEnd = end;
  }


  @Override
  public int getStartRepeat() {
    return this.startRepeat;
  }

  @Override
  public int getEndRepeat() {
    return this.endRepeat;
  }

  @Override
  public int getStartSkip() {
    return this.startSkip;
  }

  @Override
  public int getEndSkip() {
    return this.endSkip;
  }

  /**
   * get the number of times the repeat section is played
   */
  @Override
  public int getRepeatedTimes() {
    return this.repeatedTimes;
  }


  @Override
  public void addEnding(int start, int end) {
    ArrayList<Integer> ending = new ArrayList<>();
    ending.add(start);
    ending.add(end);
    if (endings.isEmpty()) {
      this.setEndingStart(start);
      this.setEndingEnd(end);
    }
    endings.add(ending);
  }
  /**
   * get the endings
   */
  @Override
  public List<List<Integer>> getEndings() {
    return new ArrayList<>(this.endings);
  };
  /**
   * Builds a {@link Model}.
   */
  public static final class Builder implements CompositionBuilder<Model> {
    private int length = DEFAULT_LENGTH_OF_BEATS;
    private int tempo = 130000;
    private List<Note> notes = new ArrayList<>();
    private List<List<Integer>> endings = new ArrayList<>();
    private int startRepeat = 0;
    private int endRepeat = 0;

    @Override
    public ModelImpl build() {
      if (notes.isEmpty()) {
        return new ModelImpl(this.tempo);
      }
      return new ModelImpl(this.tempo, this.notes, startRepeat, endRepeat,
              endings);
    }

    @Override
    public CompositionBuilder<Model> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<Model> addNote(int start, int end, int instrument,
                                             int pitch, int volume) {
      Note n = new Note(end - start, Note.intToOctave(pitch), start,
              Note.intToPitch(pitch), true, instrument, volume);
      this.notes.add(n);
      return this;
    }
    @Override
    public CompositionBuilder<Model> setStartRepeat(int start) {
      this.startRepeat = start;
      return this;
    }

    @Override
    public CompositionBuilder<Model> setEndRepeat(int end) {
      this.endRepeat = end;
      return this;
    }

    @Override
    public CompositionBuilder<Model> addEnding(int start, int end) {
      ArrayList<Integer> ending = new ArrayList<>();
      ending.add(start);
      ending.add(end);
      endings.add(ending);
      return this;
    }

  }
}

