package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.Note;

/**
 * The graphics that will be added to the JFrame in (Class: GuiViewFrame)
 */
public final class ConcreteGuiViewPanel extends JPanel {

  private ViewModel vm;

  public ConcreteGuiViewPanel(ViewModel vm) {
    this.vm = vm;
  }

  /**
   * Draws a graphical rendering of a composition
   */
  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);


    Graphics2D g2d = (Graphics2D) g.create();
    int offset = 30;
    int unit = 20;
    int measure = unit * 4;
    int x = vm.getBeats() * unit + offset;
    int y = vm.getRange().size() * unit + offset;

    int k = vm.getRange().size() - 1;

    g2d.drawRect(offset, offset, x - offset, y - offset);


    for (int i = 0; i < vm.getNotes().size(); i++) {
      for (Note n : vm.getNotes().get(i)) {
        for (int j = 0; j < vm.getRange().size(); j++) {
          if (vm.getRange().get(j).equals(n.toString()) && n.getIsHead()) {
            g2d.setColor(new Color(n.getInstrument() * 25, 10, 100));
            g2d.fillRect((i * unit) + offset, ((k -j) *
                    unit) + offset, unit,
                    unit);
          } else if (vm.getRange().get(j).equals(n.toString()) && !n.getIsHead
                  ()) {
            g2d.setColor(Color.GRAY);
            g2d.fillRect((i * unit) + offset, ((k -j) * unit) + offset,
                    unit, unit);
          }
        }
      }
    }

    for (int i = 42; k > -1; i += unit) {
      g2d.drawString(vm.getRange().get(k), 1, i);
      k -= 1;
    }

    for (int i = offset + unit; i <= y; i += unit) {
      g2d.drawLine(offset, i, x, i);
    }

    int beat = 0;
    for (int i = offset; i < x; i += measure) {
      g2d.drawString(beat + "", i, unit);
      beat += 4;
    }

    g2d.setColor(Color.GRAY);
    for (int i = offset + measure; i <= x; i += measure) {
      g2d.drawLine(i, offset, i, y);
    }

    g2d.setColor(Color.RED);
    g2d.drawLine((vm.getCurrentMeasure() * unit + offset), offset,
            (vm.getCurrentMeasure() * unit + offset), y);


    repaint();

    g2d.setColor(Color.BLACK);
    g2d.setStroke(new BasicStroke(2));
    //start line
    g2d.drawLine(vm.getStartRepeat() * unit + offset, offset,
            vm.getStartRepeat() * unit + offset, y);
    g2d.drawLine(vm.getStartRepeat() * unit + offset + 5, offset,
            vm.getStartRepeat() * unit + offset + 5, y);
    g2d.drawOval(offset + 10, offset + 10, 2, 2);
    g2d.drawOval(offset + 10, offset + 25, 2, 2);
    //end line
    g2d.drawLine(vm.getEndRepeat() * unit + offset, offset,
            vm.getEndRepeat() * unit + offset, y);
    g2d.drawLine(vm.getEndRepeat() * unit + offset - 5, offset,
            vm.getEndRepeat() * unit + offset - 5, y);
    g2d.drawOval(vm.getEndRepeat() * unit + offset - 13, offset + 10, 2, 2);
    g2d.drawOval(vm.getEndRepeat() * unit + offset - 13, offset + 25, 2, 2);
        
    for (int i = 0; i < vm.getEndings().size(); i++) {
      g2d.setColor(Color.BLACK);
      g2d.drawString(i + "", vm.getEndings().get(i).get(0) * unit +
              offset, offset);
//      g2d.drawLine(i, offset, i, y);
    }
    g2d.dispose();
  }



}
