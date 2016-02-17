package cs3500.music;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.controller.Controller;
import cs3500.music.controller.ControllerImpl;
import cs3500.music.model.Model;
import cs3500.music.model.ModelImpl;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.CompositeView;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.ViewModel;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {

    String file = args[0];
    String in = args[1];

    MusicReader reader = new MusicReader();
    CompositionBuilder<Model> compositionBuilder = ModelImpl.builder();
    try {
      reader.parseFile(new FileReader(args[0]),
              compositionBuilder);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    Model model = compositionBuilder.build();
    ViewModel vm = new ViewModel(model);

    if (in.equals("console")) {
      ConsoleView cv = ConsoleView.builder().build();
      cv.draw(vm);
      System.out.println(cv.getOutput());
    } else if (in.equals("gui")) {
      GuiViewFrame gui = GuiViewFrame.builder().setName(args[0]).setViewModel(vm).build();

    } else if (in.equals("midi")) {
      //string builder for testing.
      StringBuilder stringBuilder = new StringBuilder();
      MidiViewImpl midiView = new MidiViewImpl();
      midiView.play(vm, args[0]);
    } else if (in.equals("composite")) {
      CompositeView compositeView = new CompositeView(vm);
      ControllerImpl controller = new ControllerImpl(compositeView, model, args[0]);
      controller.initialize();
    } else {
      System.out.println("Invalid View");
    }
  }
}
