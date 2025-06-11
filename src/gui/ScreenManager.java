package gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
    private final Map<String, Screen> map = new HashMap<>();
    private final JPanel container;
    private final CardLayout layout;

    public ScreenManager() {
        layout = new CardLayout();
        container = new JPanel(layout);
    }

    public JPanel getContainer() {
        return container;
    }

    public void register(String name, Screen s) {
        map.put(name, s);
        container.add(s.getPanel(), name);
    }

    public void show(String name) {
        layout.show(container, name);
        Screen s = map.get(name);
        if (s != null) {
            s.onShow();
        }
    }
}
