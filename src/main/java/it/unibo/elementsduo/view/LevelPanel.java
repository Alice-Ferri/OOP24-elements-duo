package it.unibo.elementsduo.view;

import it.unibo.elementsduo.view.api.Renderable;
import it.unibo.elementsduo.view.api.ShapeType;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Main panel for the game level, combining the game area and a top control bar.
 * This panel holds the {@link GameAreaPanel} (where the game is rendered)
 * and navigation buttons.
 */
public final class LevelPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final GameAreaPanel gameArea;
    private final JButton homeButton;
    private final JButton levelSelectButton;

    /**
     * Constructs a new LevelPanel for the given level.
     *
     * @param level The game level model to be rendered.
     */
    public LevelPanel() {
        this.setLayout(new BorderLayout());

        this.homeButton = new JButton("Menu Principale");
        this.levelSelectButton = new JButton("Selezione Livello");

        final JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topBar.setBackground(Color.DARK_GRAY);
        topBar.add(homeButton);
        topBar.add(levelSelectButton);

        this.gameArea = new GameAreaPanel();

        this.add(topBar, BorderLayout.NORTH);
        this.add(gameArea, BorderLayout.CENTER);
    }

    /**
     * Called by the GameController during the render phase.
     * Passes the list of renderable objects and the grid
     * dimensions to the game area for drawing.
     *
     * @param renderables    The list of objects to draw.
     * @param gridDimensions The map dimensions for scaling.
     */
    public void updateRenderData(final List<Renderable> renderables, final Dimension gridDimensions) {
        this.gameArea.setDataToRender(renderables, gridDimensions);
    }


    /**
     * Add the action listeners from the gamecontroller.
     *
     * @param start          go to the menu of the game
     * @param levelSelection go to the level selection
     */
    public void addButtonsListeners(final ActionListener start, final ActionListener levelSelection) {
        this.homeButton.addActionListener(start);
        this.levelSelectButton.addActionListener(levelSelection);
    }

    /**
     * Removes the action listeners.
     *
     * @param start          remove the action listener for the menu
     * @param levelSelection remove the action listener for the level selection
     */
    public void removeButtonsListeners(final ActionListener start, final ActionListener levelSelection) {
        this.homeButton.removeActionListener(start);
        this.levelSelectButton.removeActionListener(levelSelection);
    }

   /**
     * Inner class representing the "canvas" on which the game state
     * is rendered.
     */
    private final class GameAreaPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        private List<Renderable> dataToRender = new ArrayList<>();
        private Dimension gridDimensions = new Dimension(0, 0);

        GameAreaPanel() {
            this.setBackground(Color.white);
        }

        /**
         * receive new data to render from levelPanel.
         */
        public void setDataToRender(final List<Renderable> data, final Dimension gridDims) {
            this.dataToRender = Objects.requireNonNull(data);
            this.gridDimensions = Objects.requireNonNull(gridDims);
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);

            if (gridDimensions.width == 0 || gridDimensions.height == 0 || dataToRender.isEmpty()) {
                return;
            }

            final int panelWidth = getWidth();
            final int panelHeight = getHeight();

            final int sizeBasedOnWidth = panelWidth / gridDimensions.width;
            final int sizeBasedOnHeight = panelHeight / gridDimensions.height;
            final int elementSize = Math.min(sizeBasedOnWidth, sizeBasedOnHeight);

            final int renderedWidth = elementSize * gridDimensions.width;
            final int renderedHeight = elementSize * gridDimensions.height;
            final int offsetX = (panelWidth - renderedWidth) / 2;
            final int offsetY = (panelHeight - renderedHeight) / 2;

            for (final Renderable r : this.dataToRender) {
                final int x = toPx(r.x() - r.halfWidth(), elementSize) + offsetX;
                final int y = toPx(r.y() - r.halfHeight(), elementSize) + offsetY;
                final int w = toPx(r.halfWidth() * 2.0, elementSize);
                final int h = toPx(r.halfHeight() * 2.0, elementSize);

                g.setColor(r.color());

                if (r.shape() == ShapeType.OVAL) {
                    g.fillOval(x, y, w, h);
                } else {
                    g.fillRect(x, y, w, h);
                }

                g.setColor(Color.BLACK);
                if (r.shape() == ShapeType.OVAL) {
                    g.drawOval(x, y, w, h);
                } else {
                    g.drawRect(x, y, w, h);
                }
            }
        }

        /**
         * convert coordinates in pixel.
         */
        private int toPx(final double worldCoord, final int elementSize) {
            return (int) Math.round(worldCoord * elementSize);
        }
    }
}