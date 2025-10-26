package it.unibo.elementsduo.controller.subcontroller.impl;


import javax.swing.JPanel;

import it.unibo.elementsduo.controller.maincontroller.api.HomeNavigation;
import it.unibo.elementsduo.controller.subcontroller.api.Controller;
import it.unibo.elementsduo.view.MenuPanel;

public class HomeController implements Controller{

    private final MenuPanel view;
    private final HomeNavigation controller;

    public HomeController(MenuPanel panel,HomeNavigation controller){
        this.view=panel;
        this.controller=controller;
    }
    @Override
    public void activate() {
        this.view.getStartButton().addActionListener(e -> {
            System.out.println("ciao");
            this.controller.goToLevelSelection();
        });
    }

    @Override
    public void deactivate() {
        this.view.getStartButton().removeActionListener(null);
    }

    @Override
    public JPanel getPanel() {
        return this.view;
    }
    
}
