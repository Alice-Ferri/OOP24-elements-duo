package it.unibo.elementsduo.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.Box;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class LevelSelectionPanel extends JPanel {
    private static Box Box; 
    private static final int NUM_LEVELS = 3;
    private final Map<JButton, Integer> levelButtons;
    private final JButton backButton;
    private final Map<Integer, LevelDataPanel> levelDataPanels;

    private static final class LevelDataPanel extends JPanel {
        private final JButton levelButton;
        private final JLabel timeLabel;
        private final JLabel gemsLabel;

        LevelDataPanel(final int levelNumber, final JButton button) {
            this.levelButton = button;
            
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
            
            setBorder(BorderFactory.createEtchedBorder());

            this.levelButton.setAlignmentX(CENTER_ALIGNMENT); 
            
            this.timeLabel = new JLabel("Tempo: N/A", SwingConstants.CENTER);
            this.gemsLabel = new JLabel("Gemme: N/A", SwingConstants.CENTER);
            
            this.timeLabel.setAlignmentX(CENTER_ALIGNMENT);
            this.gemsLabel.setAlignmentX(CENTER_ALIGNMENT);

            add(this.levelButton);
            add(Box.createVerticalStrut(10));
            add(this.timeLabel);
            add(Box.createVerticalStrut(5));
            add(this.gemsLabel);
            add(Box.createVerticalStrut(5));
        }

        public JLabel getTimeLabel() {
            return this.timeLabel;
        }

        public JLabel getGemsLabel() {
            return this.gemsLabel;
        }
    }

    public LevelSelectionPanel() {
        setLayout(new BorderLayout(30, 30)); 
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        final JLabel title = new JLabel("SELEZIONA LIVELLO", JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); 
        add(title, BorderLayout.NORTH);

        final JPanel levelGrid = new JPanel(new GridLayout(0, 3, 30, 30)); 
        
        this.levelDataPanels = new LinkedHashMap<>(); 
        this.levelButtons = new LinkedHashMap<>(); 

        IntStream.rangeClosed(1, NUM_LEVELS).forEach(i -> {
            final JButton button = new JButton("Livello " + i);
            final LevelDataPanel dataPanel = new LevelDataPanel(i, button);
            
            this.levelButtons.put(button, i);
            this.levelDataPanels.put(i, dataPanel);
            
            levelGrid.add(dataPanel);
        });
        
        add(levelGrid, BorderLayout.CENTER);

        this.backButton = new JButton("Indietro");
        final JPanel southPanel = new JPanel();
        southPanel.add(backButton);
        add(southPanel, BorderLayout.SOUTH);
    }
    
    public void setBestTimes(final Map<Integer, Double> bestTimes) {
        bestTimes.forEach((levelNum, timeSeconds) -> {
            Optional.ofNullable(this.levelDataPanels.get(levelNum)).ifPresent(panel -> {
                panel.getTimeLabel().setText("Record: " + formatTime(timeSeconds));
            });
        });
    }

    public void setLevelGems(final Map<Integer, Integer> levelGems) {
        levelGems.forEach((levelNum, gemCount) -> {
             Optional.ofNullable(this.levelDataPanels.get(levelNum)).ifPresent(panel -> {
                 panel.getGemsLabel().setText("Gemme raccolte: " + gemCount);
             });
         });
    }

    private String formatTime(final double timeSeconds) {
 
        final long totalMillis = (long) (timeSeconds * 1000);

        final long minutes = TimeUnit.MILLISECONDS.toMinutes(totalMillis);
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(totalMillis) - 
                             TimeUnit.MINUTES.toSeconds(minutes);
        final long millis = totalMillis % 1000;
        
        return String.format("%02d:%02d.%03d", minutes, seconds, millis);
    }
    
    public Map<JButton, Integer> getLevelButtons() {
        return this.levelButtons;
    }

    public JButton getBackButton() {
        return this.backButton;
    }
}