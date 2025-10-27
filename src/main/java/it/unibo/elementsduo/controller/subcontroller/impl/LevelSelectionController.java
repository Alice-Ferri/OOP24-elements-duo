package it.unibo.elementsduo.controller.subcontroller.impl;

import javax.swing.JPanel;

import it.unibo.elementsduo.controller.maincontroller.api.LevelSelectionNavigation;
import it.unibo.elementsduo.controller.subcontroller.api.Controller;
import it.unibo.elementsduo.view.LevelSelectionPanel;

public class LevelSelectionController implements Controller{

    private final LevelSelectionPanel view;
    private final LevelSelectionNavigation controller;

    public LevelSelectionController (LevelSelectionPanel panel,LevelSelectionNavigation controller){
        this.view=panel;
        this.controller=controller;
    }

    @Override
    public void activate() {
        this.view.getLevelButtons().forEach((button, levelNumber) -> {
            button.addActionListener(e -> {
                this.controller.startGame(levelNumber);
            });
        });

        this.view.getBackButton().addActionListener(e -> {
            this.controller.goToMenu();
        });
    }

    @Override
    public void deactivate() {
        this.view.getLevelButtons().keySet().forEach(button -> button.removeActionListener(null));
        this.view.getBackButton().removeActionListener(null);
    }
    

    @Override
    public JPanel getPanel() {
        return this.view;
    }
    
}
