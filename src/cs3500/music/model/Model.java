package cs3500.music.model;

import java.util.List;
import java.util.Set;

/**
 * This interface is designed to represent a piece of music. It includes a
 * collection of pitches that fits a piece of music, and the beat numbers.
 *
 * This interface is in charge of reading notes (the pitch and the duration)
 * and adding or removing a note from the stored piece.
 */
public interface Model {
  /**
   * The default length of beats, can be changed if the music piece exceeds
   * that length. INVARIANT! Has to be a multiple of 4.
   */
  int DEFAULT_LENGTH_OF_BEATS = 64;

  /**
   * The starting measure of this piece.
   */
  int DEFAULT_START = 0;


  /**
   * Get the highest note.
   *
   * @return the highest note.
   */
  String getHigh();

  /**
   * Get the lowest note.
   *
   * @return the lowest note.
   */
  String getLow();

  /**
   * add a new note to the model
   *
   * @param n the note added.
   */
  void addNote(Note n);


  /**
   * add a list of notes to the model
   *
   * @param n the list added.
   */
  void addAll(List<Note> n);

  /**
   * remove a note from the model
   *
   * @param n the note removed.
   */
  void removeNote(Note n);

  /**
   * represent the status of the model
   */
  enum Status {
    /**
     * The model is playing.
     */
    Playing,
    /**
     * The model has finished playing.
     */
    Ended;
  }

  /**
   * Get the current status of the model
   *
   * @return the status.
   */
  Status getStatus();

  /**
   * Determines if the model has finished playing
   *
   * @return whether the model has ended
   */
  default boolean isEnded() {
    return getStatus() == Status.Ended;
  }

  /**
   * Updates the measure.
   *
   * <p><strong>PRECONDITION:</strong> the music isn't over
   *
   * @throws IllegalStateException if {@code isEnded()}
   */
  void updateMeasure();

  /**
   * Move a note to a given pitch at a given measure
   *
   * @param n       the note being moved
   * @param pitch   the new pitch
   * @param measure the new measure
   */
  void move(Note n, Note.Pitches pitch, int octave, int measure);


  /**
   * Find a note with given parameters
   *
   * @param start the start measure
   * @param dur   duration
   * @param oct   octave number
   * @param pitch pitch number
   * @return the note found
   */
  Note find(int start, int dur, int oct, int pitch);

  /**
   * Return a copy of the highest note in this piece.
   */
  Note getHighest();

  /**
   * Return a copy of the lowest note in this piece.
   */
  Note getLowest();

  /**
   * get the list of pitches.
   */
  List<String> getPitches();

  /**
   * get the number of beats.
   */
  int getBeats();
  /**
   * get the tempo.
   */
  int getTempo();
  /**
   * get notes.
   */
  List<Set<Note>> getNotes();

  /**
   * get head notes
   */

  List<Set<Note>> getHeadNotes();

  /**
   * get all head notes at a given beat.
   */
  Set<Note> notesAtBeat(int beat);

  /**
   * get the current measure.
   */
  int getCurrentMeasure();

  /**
   * set the current measure.
   */
  void setCurrentMeasure(int beat);

  /**
   * set the skipped region with a clear start.
   */
  void setSkipStart(int start);

  /**
   * set the skipped region with a clear end.
   */
  void setSkipEnd(int end);

  /**
   * get the start of the repeat;
   */
  int getStartRepeat();

  /**
   * get the end of the repeat;
   */
  int getEndRepeat();
  /**
   * get the start of the skip;
   */
  int getStartSkip();
  /**
   * get the end of the skip;
   */
  int getEndSkip();
  /**
   * get the number of times the repeat section is played
   */
  int getRepeatedTimes();
  /**
   * get the endings
   */
  List<List<Integer>> getEndings();
  /**
   * adds an ending.
   */
  void addEnding(int start, int end);

  void setEndingStart(int start);

  void setEndingEnd(int end);
}
