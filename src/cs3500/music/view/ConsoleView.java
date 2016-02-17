package cs3500.music.view;


/**
 * This is the console view (all notes in texts).
 */
public final class ConsoleView implements View {

  private String output;

  /**
   * Creates a new console view using the given input and output.
   *
   * @param output where to send output for the user to see
   */
  private ConsoleView(String output) {
    this.output = output;
  }

  /**
   * Construct a new Console View.
   *
   * @return the builder
   */
  public static Builder builder() {
    return new Builder();
  }

  public void draw(ViewModel vm) {
    this.output = vm.toString();
  }

  public String getOutput() {
    return this.output;
  }

  public static final class Builder {
    private final String out = "";

    public ConsoleView build() {
      return new ConsoleView(out);
    }
  }
}
