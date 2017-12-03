package swing;

import javax.swing.*;
import java.awt.*;

public class PointFrame extends JFrame {
    public PointFrame(Component component) throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(component);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
