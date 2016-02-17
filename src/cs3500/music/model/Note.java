package cs3500.music.model;

/**
 * This class includes the length of the note (integral number of 1/4 of a
 * 4-beat measure.
 */
public final class Note implements Comparable<Note> {
  /**
   * represents the pitch with no makes for octaves.
   */
  protected enum Pitches {
    A,
    /**
     * Represent the same pitch in music.
     */
    ASharp, BFlat,
    B,
    C,
    /**
     * Represent the same pitch in music.
     */
    CSharp, DFlat,
    D,
    /**
     * Represent the same pitch in music.
     */
    DSharp, EFlat,
    E,
    F,
    /**
     * Represent the same pitch in music.
     */
    FSharp, GFlat,
    G,
    /**
     * Represent the same pitch in music.
     */
    GSharp, AFlat;
  }

  /**
   * The duration of the note. The instrument, volume of this note.
   */
  private final int duration, instrument, volume;
  /**
   * The octave of this playable.
   */
  private int octave, startMeasure;
  /**
   * The pitch of this playable.
   */
  private Pitches pitch;
  /**
   * determines whether this note is a head note.
   */
  private boolean isHead;

  /**
   * Constructs a playable note.
   *
   * @param duration     the duration of the note (> 0)
   * @param startMeasure the starting measure of this note (non-negative)
   * @param octave       the octave of this note (non-negative)
   * @param pitch        the pitch of this note.
   * @param isHead       determines whether this note is a head note.
   * @param instrument   the instrument used.
   * @param volume       the instrument used.
   */
  public Note(int duration, int octave, int startMeasure, Pitches pitch,
              boolean isHead, int instrument, int volume) {
    if (octave < 0 || startMeasure < 0 || duration <= 0 || instrument < 0
            || instrument > 10 || volume < 0 || volume > 127) {
      throw new IllegalArgumentException("Invalid inputs.");
    }
    this.duration = duration;
    this.octave = octave;
    this.startMeasure = startMeasure;
    this.pitch = pitch;
    this.isHead = isHead;
    this.instrument = instrument;
    this.volume = volume;
  }

  /**
   * Compares this note with a given playable
   *
   * @param that the playable note that is either a note or a rest
   * @return false if they're not equal
   * @throws IllegalArgumentException if a rest is being compared to this.
   */

  public boolean equals(Note that) {
    return this.getStartMeasure() + this.getDuration() > that.getStartMeasure()
            && that.getStartMeasure() + that.getDuration() > this.getStartMeasure()
            && this.getOctave() == that.getOctave() && this.getPitch() == that.getPitch()
            && this.instrument == that.instrument;
  }

  /**
   * Move this note to a given pitch at a given measure
   *
   * @param pitch   the new pitch
   * @param measure the new measure
   * @return a copy of the new note
   */
  public Note moveTo(Pitches pitch, int octave, int measure) {
    if (!this.isHead) {
      throw new IllegalArgumentException("Not a head note");
    }
    this.octave = octave;
    this.pitch = pitch;
    this.startMeasure = measure;
    return this;
  }

  /**
   * Get the starting measure of this note.
   *
   * @return the starting measure of this note.
   */
  public int getStartMeasure() {
    return this.startMeasure;
  }

  /**
   * Gets the total duration of this note.
   *
   * @return the total duration of this note.
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * Gets the octave of this note.
   *
   * @return the octave of this note.
   */
  public int getOctave() {
    return this.octave;
  }

  /**
   * Gets the pitch of this note.
   *
   * @return the pitch of this note.
   */
  public Pitches getPitch() {
    return this.pitch;
  }

  /**
   * Sets the pitch of this note.
   */
  public void setPitch(Pitches p) {
    this.pitch = p;
  }

  /**
   * Sets the octave of this note.
   */
  public void setOctave(int o) {
    if (o < 0) {
      throw new IllegalArgumentException("Octave can't be negative");
    }
    this.octave = o;
  }

  /**
   * Sets the starting measure of this note.
   */
  public void setMeasure(int start) {
    if (start < 0) {
      throw new IllegalArgumentException("Starting measure can't be negative");
    }
    this.startMeasure = start;
  }

  /**
   * Get the isHead of this note.
   *
   * @return whether this note is a head.
   */
  public boolean getIsHead() {
    return this.isHead;
  }

  /**
   * Get the instrument used for this note.
   *
   * @return the instrument int.
   */
  public int getInstrument() {
    return this.instrument;
  }

  /**
   * Get the volume used for this note.
   *
   * @return the volume int.
   */
  public int getVolume() {
    return this.volume;
  }

  /**
   * Make this note half a step down.
   */
  public void down() {
    switch (this.pitch) {
      case A:
        this.setPitch(Pitches.AFlat);
        break;
      case AFlat:
        this.setPitch(Pitches.G);
        break;
      case ASharp:
        this.setPitch(Pitches.A);
        break;
      case B:
        this.setPitch(Pitches.BFlat);
        break;
      case BFlat:
        this.setPitch(Pitches.A);
        break;
      case C:
        this.setPitch(Pitches.B);
        this.setOctave(this.getOctave() - 1);
        break;
      case CSharp:
        this.setPitch(Pitches.C);
        break;
      case D:
        this.setPitch(Pitches.DFlat);
        break;
      case DFlat:
        this.setPitch(Pitches.C);
        break;
      case DSharp:
        this.setPitch(Pitches.D);
        break;
      case E:
        this.setPitch(Pitches.EFlat);
        break;
      case EFlat:
        this.setPitch(Pitches.D);
        break;
      case F:
        this.setPitch(Pitches.E);
        break;
      case FSharp:
        this.setPitch(Pitches.F);
        break;
      case G:
        this.setPitch(Pitches.GFlat);
        break;
      case GFlat:
        this.setPitch(Pitches.F);
        break;
      case GSharp:
        this.setPitch(Pitches.G);
        break;
      default:
        throw new IllegalArgumentException("Invalid pitch");
    }
  }

  /**
   * Make this note half a step up.
   */
  public void up() {
    switch (this.pitch) {
      case A:
        this.setPitch(Pitches.ASharp);
        break;
      case AFlat:
        this.setPitch(Pitches.A);
        break;
      case ASharp:
        this.setPitch(Pitches.B);
        break;
      case B:
        this.setPitch(Pitches.C);
        this.setOctave(this.getOctave() + 1);
        break;
      case BFlat:
        this.setPitch(Pitches.B);
        break;
      case C:
        this.setPitch(Pitches.CSharp);
        break;
      case CSharp:
        this.setPitch(Pitches.D);
        break;
      case D:
        this.setPitch(Pitches.DSharp);
        break;
      case DFlat:
        this.setPitch(Pitches.D);
        break;
      case DSharp:
        this.setPitch(Pitches.E);
        break;
      case E:
        this.setPitch(Pitches.F);
        break;
      case EFlat:
        this.setPitch(Pitches.E);
        break;
      case F:
        this.setPitch(Pitches.FSharp);
        break;
      case FSharp:
        this.setPitch(Pitches.G);
        break;
      case G:
        this.setPitch(Pitches.GSharp);
        break;
      case GFlat:
        this.setPitch(Pitches.G);
        break;
      case GSharp:
        this.setPitch(Pitches.A);
        break;
      default:
        throw new IllegalArgumentException("Invalid pitch");
    }
  }

  /**
   * Computes an index value of this note.
   */
  public int toIndex() {
    switch (this.pitch) {
      case A:
        return 9;
      case AFlat:
        return 8;
      case ASharp:
        return 10;
      case B:
        return 11;
      case BFlat:
        return 10;
      case C:
        return 0;
      case CSharp:
        return 1;
      case D:
        return 2;
      case DFlat:
        return 1;
      case DSharp:
        return 3;
      case E:
        return 4;
      case EFlat:
        return 3;
      case F:
        return 5;
      case FSharp:
        return 6;
      case G:
        return 7;
      case GFlat:
        return 6;
      case GSharp:
        return 8;
      default:
        throw new IllegalArgumentException("Invalid pitch");
    }
  }

  /**
   * This calculates the range between the lower note to this note.
   *
   * @param lower the lower note being compared
   * @return how far up this note is from the lower note (excluding this note).
   * the given lower note stays the same.
   */
  public int howFarUp(Note lower) {
    int step = 0;
    Note temp = new Note(lower.getDuration(), lower.getOctave(),
            lower.getStartMeasure(), lower.getPitch(), lower.getIsHead(),
            lower.getInstrument(), lower.getVolume());
    if (this.compareTo(lower) < 0) {
      throw new IllegalArgumentException("The given note has to be lower than the current note.");
    }
    while (temp.getOctave() < 9 && this.compareTo(temp) != 0) {
      temp.up();
      step++;
    }
    return step;
  }


  /**
   * Compares this to another note. If this is smaller than the other note:
   * return -1. If bigger than the other note: return 1. If two notes are
   * equal: return 0.
   *
   * @param n the other note being compared to
   * @return the designated integer.
   */
  @Override
  public int compareTo(Note n) {
    if (this.equals(n)) {
      return 0;
    } else if (n.getOctave() == this.getOctave()) {
      if (n.toIndex() - this.toIndex() > 0) {
        return -1;
      } else if (n.toIndex() == this.toIndex()) {
        return 0;
      } else {
        return 1;
      }
    } else {
      if (n.getOctave() > this.getOctave()) {
        return -1;
      } else {
        return 1;
      }
    }
  }

  /**
   * Return the string form of this note's pitch. Avoid confusions between a
   * sharp and a flat.
   *
   * @return the pitch of the note.
   * @throws IllegalArgumentException when the pitch is null.
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    switch (this.pitch) {
      case A:
        builder.append("A");
        break;
      case AFlat:
        builder.append("G#");
        break;
      case ASharp:
        builder.append("A#");
        break;
      case B:
        builder.append("B");
        break;
      case BFlat:
        builder.append("A#");
        break;
      case C:
        builder.append("C");
        break;
      case CSharp:
        builder.append("C#");
        break;
      case D:
        builder.append("D");
        break;
      case DFlat:
        builder.append("C#");
        break;
      case DSharp:
        builder.append("D#");
        break;
      case E:
        builder.append("E");
        break;
      case EFlat:
        builder.append("D#");
        break;
      case F:
        builder.append("F");
        break;
      case FSharp:
        builder.append("F#");
        break;
      case G:
        builder.append("G");
        break;
      case GFlat:
        builder.append("F#");
        break;
      case GSharp:
        builder.append("G#");
        break;
      default:
        throw new IllegalArgumentException("Invalid pitch");
    }
    builder.append(this.octave);
    return builder.toString();
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Return the corresponding octave. Used for addNote in composition builder.
   *
   * @param index the index of the pitch
   * @return the index of octave in Note's representation.
   * @throws IllegalArgumentException when the index is less than A0 on a piano
   *                                  keyboard.
   */
  public static int intToOctave(int index) {
    int noteVersion = index - 21;
    if (noteVersion < 0) {
      throw new IllegalArgumentException("Lower than A0.");
    }
    if (noteVersion < 3) {
      return 0;
    } else {
      noteVersion -= 3;
      return (int) noteVersion / 12 + 1;
    }
  }

  /**
   * Return the corresponding pitch. Used for addNote in composition builder.
   *
   * @param index the index of the pitch
   * @return the index of octave in Note's representation.
   * @throws IllegalArgumentException when the index is less than A0 on a piano
   *                                  keyboard.
   */
  public static Pitches intToPitch(int index) {
    int noteVersion = index - 21;
    if (noteVersion < 0) {
      throw new IllegalArgumentException("Lower than A0.");
    }
    switch (noteVersion % 12) {
      case 0:
        return Pitches.A;
      case 1:
        return Pitches.ASharp;
      case 2:
        return Pitches.B;
      case 3:
        return Pitches.C;
      case 4:
        return Pitches.CSharp;
      case 5:
        return Pitches.D;
      case 6:
        return Pitches.DSharp;
      case 7:
        return Pitches.E;
      case 8:
        return Pitches.F;
      case 9:
        return Pitches.FSharp;
      case 10:
        return Pitches.G;
      case 11:
        return Pitches.GSharp;
      default:
        throw new IllegalArgumentException("Invalid pitch");
    }
  }

  /**
   * Make this note's index into MIDI index.
   *
   * @return the MIDI index.
   */
  public int toMidiIndex() {
    if (this.getOctave() < 1) {
      switch (this.getPitch()) {
        case A:
          return 21;
        case ASharp:
          return 22;
        case BFlat:
          return 23;
        case B:
          return 24;
        default:
          throw new IllegalArgumentException("Shouldn't come here.");
      }
    }
    return (this.getOctave() - 1) * 12 + this.toIndex() + 24;
  }

  /**
   * Interprets a note in a string format (that is generated from toString). No
   * need to worry about exceptions because this is only used with a string
   * note generated from toString().
   *
   * @param note the toString result.
   * @return the int represents the octave
   */
  public int stringToOctave(String note) {
    if (note.length() == 3) {
      return note.charAt(2);
    } else {
      return note.charAt(1);
    }
  }
}
