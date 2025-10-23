package it.unibo.elementsduo.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;

public class MenuFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    public MenuFrame() {
        this.setTitle("Elements Duo");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 600));

        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(this.cardLayout);
        this.add(this.contentPanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(true);
    }

    public void addView(final JPanel view,final String key) {
        this.contentPanel.add(view,key);
    }

    public void showView(final String key) {
        this.cardLayout.show(this.contentPanel, key);
    }
}
