package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.Note;

/**
 * This is the GUI View Frame.
 */
public final class GuiViewFrame extends JFrame implements GuiView {

  private JFrame frame = new JFrame("View");
  ViewModel vm;
  JScrollPane pane;

  private GuiViewFrame(ViewModel vm, String name) {
    this.vm = vm;
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());


    ConcreteGuiViewPanel art = new ConcreteGuiViewPanel(vm);
    art.setPreferredSize(new Dimension(vm.getBeats() * 20 + 50, vm.getRange().size() * 20 + 50));


    pane = new JScrollPane(art, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    pane.setPreferredSize(new Dimension(new Dimension(vm.getBeats() * 20 + 50,
            vm.getRange().size() * 20 + 50)));
    frame.add(pane);
    frame.pack();

    frame.setPreferredSize(new Dimension(new Dimension(vm.getBeats() * 20 + 50,
            vm.getRange().size() * 20 + 50)));
    frame.setMinimumSize(new Dimension(160, 200));
    //frame.setSize(1200, 800);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  // todo this is really redundant.
  public void draw(ViewModel vm, String name) {
    new GuiViewFrame(vm, name);
  }

  /**
   * Construct a new GUI View.
   *
   * @return the builder
   */
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String name;
    private ViewModel vm;

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setViewModel(ViewModel vm) {
      this.vm = vm;
      return this;
    }

    public GuiViewFrame build() {
      return new GuiViewFrame(vm, name);
    }
  }

  @Override
  public synchronized void addKeyListener(KeyListener l) {
    this.frame.addKeyListener(l);
  }

  @Override
  public synchronized void removeKeyListener(KeyListener l) {
    this.frame.removeKeyListener(l);
  }

  @Override
  public synchronized void addMouseListener(MouseListener l) {
    this.pane.addMouseListener(l);
  }

  @Override
  public synchronized void removeMouseListener(MouseListener l) {
    this.pane.removeMouseListener(l);
  }

  @Override
  public Note getHighest() {
    return this.vm.getHighest();
  }

  @Override
  public Note getLowest() {
    return this.vm.getLowest();
  }
}