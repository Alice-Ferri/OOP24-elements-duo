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
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * A panel that displays the grid for level selection.
 * It allows the user to choose which level to play and shows
 * stats (record time, gems) for each unlocked level.
 */
public final class LevelSelectionPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int NUM_LEVELS = 3;
    private static final int LAYOUT_GAP = 30;
    private static final int PANEL_PADDING = 40;
    private static final int TITLE_BOTTOM_MARGIN = 15;
    private static final int VERTICAL_GAP = 5;

    private final Map<JButton, Integer> levelButtons;
    private final JButton backButton;
    private final Map<Integer, LevelDataPanel> levelDataPanels;

    /**
     * Constructs the level selection panel.
     * Initializes the layout and creates the grid of level panels.
     */
    public LevelSelectionPanel() {
        setLayout(new BorderLayout(LAYOUT_GAP, LAYOUT_GAP));
        setBorder(BorderFactory.createEmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

        final JLabel title = new JLabel("SELEZIONA LIVELLO", JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, TITLE_BOTTOM_MARGIN, 0));
        add(title, BorderLayout.NORTH);

        final JPanel levelGrid = new JPanel(new GridLayout(0, NUM_LEVELS, LAYOUT_GAP, LAYOUT_GAP));
        this.levelDataPanels = new LinkedHashMap<>();
        this.levelButtons = new LinkedHashMap<>();

        IntStream.rangeClosed(0, NUM_LEVELS-1).forEach(i -> {
            final JButton button = new JButton("Livello " + (i+1));
            final LevelDataPanel dataPanel = new LevelDataPanel(button); 
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

    public void addButtonListeners(final Function<Integer, ActionListener> listenerProvider, ActionListener onMenu){
        this.levelButtons.forEach((button, levelNumber) -> {
            final ActionListener listener = listenerProvider.apply(levelNumber);
            button.addActionListener(listener);
        });
        this.backButton.addActionListener(onMenu);
    }

    public void removeButtonListeners(final Function<Integer, ActionListener> listenerProvider,final ActionListener onMenu){
        this.levelButtons.forEach((button, levelNumber) -> {
            button.removeActionListener(listenerProvider.apply(levelNumber));
        });
        this.backButton.addActionListener(onMenu);

    }

    /**
     * Updates the record time labels for each level.
     *
     * @param bestTimes A map associating the level index with the record time in seconds.
     */
    public void setBestTimes(final Map<Integer, Double> bestTimes) {
        bestTimes.forEach((levelNum, timeSeconds) -> {
            Optional.ofNullable(this.levelDataPanels.get(levelNum)).ifPresent(panel -> {
                panel.getTimeLabel().setText("Record: " + formatTime(timeSeconds));
            });
        });
    }

    /**
     * Updates the collected gems labels for each level.
     *
     * @param missionCompleted A map associating the level index with the gem count.
     */
    public void setMissionCompleted(final Map<Integer, String> missionCompleted) {
        missionCompleted.forEach((levelNum, status) -> {
             Optional.ofNullable(this.levelDataPanels.get(levelNum)).ifPresent(panel -> {
                 panel.getMissionLabel().setText(status);
             });
         });
    }

    /**
     * Formats a time in seconds into a "MM:SS.ms" string.
     *
     * @param timeSeconds The total time in seconds.
     * @return The formatted time string.
     */
    private String formatTime(final double timeSeconds) {
        final long totalMillis = (long) (timeSeconds * 1000);

        final long minutes = TimeUnit.MILLISECONDS.toMinutes(totalMillis);
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(totalMillis)
                               - TimeUnit.MINUTES.toSeconds(minutes);
        final long millis = totalMillis % 1000;
        return String.format("%02d:%02d.%03d", minutes, seconds, millis);
    }

    /**
     * An inner panel that displays data for a single level.
     * Includes the button to start the level, the record time, and gems collected.
     */
    private static final class LevelDataPanel extends JPanel {
        private static final String MISSION_STRING = "<html><div style='text-align: center;'>"
                 + "Missione:<br>"
                 + "Raccogli tutte le gemme,<br>"
                 + "uccidi tutti i nemici<br>"
                 + "e finisci prima di un minuto!"
                 + "</div></html>";
        private static final long serialVersionUID = 1L;
        private final JButton levelButton;
        private final JLabel timeLabel;
        private final JLabel missionLabel;

        LevelDataPanel(final JButton button) {
            this.levelButton = button;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEtchedBorder());

            this.levelButton.setAlignmentX(CENTER_ALIGNMENT);

            this.timeLabel = new JLabel("Tempo: N/A", SwingConstants.CENTER);
            this.missionLabel = new JLabel(MISSION_STRING, SwingConstants.CENTER);
            this.timeLabel.setAlignmentX(CENTER_ALIGNMENT);
            this.missionLabel.setAlignmentX(CENTER_ALIGNMENT);

            add(this.levelButton);
            add(Box.createVerticalStrut(10));
            add(this.timeLabel);
            add(Box.createVerticalStrut(VERTICAL_GAP));
            add(this.missionLabel);
            add(Box.createVerticalStrut(VERTICAL_GAP));
        }

        /**
         * Returns the label used to display the record time.
         *
         * @return The time JLabel.
         */
        public JLabel getTimeLabel() {
            return this.timeLabel;
        }

        /**
         * Returns the label used to display the collected gems.
         *
         * @return The gems JLabel.
         */
        public JLabel getMissionLabel() {
            return this.missionLabel;
        }


    }
}
