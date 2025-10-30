package it.unibo.elementsduo.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;

public final class GameFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    public GameFrame() {
        this.setTitle("Elements Duo");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
