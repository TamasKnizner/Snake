package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.SnakeDeadException;

public class TimerAction implements ActionListener {

    private SnakeController snakeController;
    private boolean stop;

    public TimerAction(SnakeController snakeController) {
        this.snakeController = snakeController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (!stop) {
                snakeController.step();
            }
        } catch (SnakeDeadException ex) {
            stop = true;
            snakeController.stop();
        }
    }

}
