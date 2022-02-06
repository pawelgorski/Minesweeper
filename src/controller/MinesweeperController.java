package controller;

import model.MinesweeperModel;
import view.MinesweeperGUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MinesweeperController {
    private MinesweeperModel model;
    private MinesweeperGUI gui;


    public void addButtonToMap(JButton button) {
        model.addButton(button);
    }

    public void setGUI (MinesweeperGUI gui) {
        this.gui = gui;
    }

    public void setModel (MinesweeperModel model) {

        this.model = model;
    }

    public int getWidth() {
        return this.model.getWidth();
    }

    public int getHeight() {

        return this.model.getHeight();
    }

    public int getCellState(int row, int col) {

        return this.model.getCellMines(row, col);
    }

    public boolean getVisibility(int row, int col) {

        return this.model.getCellVisibility(row, col);
    }

    public void changeBoardToBeginner(){
        this.model = new MinesweeperModel(9,9,10);
        model.setController(this);
        gui.createButtons();
    }
    public void changeBoardToIntermediate(){
        this.model = new MinesweeperModel(16,16,40);
        model.setController(this);
        gui.createButtons();
    }
    public void changeBoardToExpert(){
        this.model = new MinesweeperModel(30,16,99);
        model.setController(this);
        gui.createButtons();
    }

    public void rightMouseButtonClicked(JButton button) {
        if(model.getGameState() == MinesweeperModel.State.PLAY) {
            String[] buttonName = button.getName().split(";");
            int row = Integer.valueOf(buttonName[0]);
            int col = Integer.valueOf(buttonName[1]);

            int isFlagged = model.isCellFlagged(row, col);
            if (!model.getCellVisibility(row, col)) {
                if (isFlagged == 1) {
                    model.setCellFlagged(row, col, false);
                    model.setFlaggedCells(model.getFlaggedCells() - 1);
                    button.setText("");
                } else if (isFlagged == 0 && model.getFlaggedCells() < model.getNumOfMines()) {
                    button.setText("\u2691");
                    model.setCellFlagged(row, col, true);
                    model.setFlaggedCells(model.getFlaggedCells() + 1);
                }
            }
        }
    }

    public void plotVisibleBoard() {
        if(model.getGameState() == MinesweeperModel.State.PLAY) {
            char[][] board = model.getCurrentBoard();
            int invisibleCounter = 0;

            for (int i = 0; i < board.length - 2; i++) {
                for (int j = 0; j < board[0].length - 2; j++) {
                    int row = i + 1;
                    int col = j + 1;
                    char currentCell = board[row][col];
                    String buttonName = String.valueOf(row) + ";" + String.valueOf(col);

                    Border empty = BorderFactory.createLineBorder(new Color(180, 180, 200));
                    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
                    JButton button = model.getButtonByName(buttonName);
                    button.setBorder(empty);

                    switch (currentCell) {
                        case 'f':
                            button.setText("\u2691");
                            button.setForeground(Color.BLACK);
                            button.setBorder(raisedbevel);
                            break;
                        case '#':
                            button.setText("");
                            button.setForeground(Color.BLACK);
                            button.setBorder(raisedbevel);
                            invisibleCounter++;
                            break;
                        case '0':
                            button.setText(" ");
                            break;
                        case '1':
                            button.setText("1");
                            button.setForeground(Color.BLUE);
                            break;
                        case '2':
                            button.setText("2");
                            button.setForeground(new Color(0, 100, 0));
                            break;
                        case '3':
                            button.setText("3");
                            button.setForeground(Color.RED);
                            break;
                        case '4':
                            button.setText("4");
                            button.setForeground(new Color(0, 0, 150));
                            break;
                        case '5':
                            button.setText("5");
                            button.setForeground(new Color(102, 51, 0));
                            break;
                        case '6':
                            button.setText("6");
                            button.setForeground(Color.CYAN);
                            break;
                        case '7':
                            button.setText("7");
                            button.setForeground(Color.BLACK);
                            break;
                        case '8':
                            button.setText("8");
                            button.setForeground(Color.DARK_GRAY);
                            break;
                        case '9':
                            model.setGameState(MinesweeperModel.State.GAME_OVER);
                            button.setText("\uD83D\uDCA3");
                            button.setForeground(Color.BLACK);
                            button.setBackground(Color.RED);
                    }
                }
            }

            if(model.getGameState() == MinesweeperModel.State.GAME_OVER) {
                System.out.println("Game Over!");
            }

            if (invisibleCounter == 0 && model.getFlaggedCells() == model.getNumOfMines()) {
                model.setGameState(MinesweeperModel.State.WIN);
                System.out.println("You win!");
            }
        }
    }


    public void leftMouseButtonClicked(JButton button) {
        if(model.getGameState() == MinesweeperModel.State.PLAY) {
            Border empty = BorderFactory.createEmptyBorder();
            button.setBorder(empty);

            String[] buttonName = button.getName().split(";");

            int row = Integer.valueOf(buttonName[0]);
            int col = Integer.valueOf(buttonName[1]);

            model.revealCell(row,col,true);
        }

    }


}
