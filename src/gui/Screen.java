package gui;

import javax.swing.*;

public interface Screen {
    JPanel getPanel();

    void onShow();
}