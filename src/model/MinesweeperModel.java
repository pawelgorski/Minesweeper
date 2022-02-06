package model;

import controller.MinesweeperController;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class MinesweeperModel {
    private int width;
    private int height;
    private int numOfMinesOnBoard;
    private int flaggedCells;
    private Cell[][] boardArray;
    private char[][] visibleBoard;
    private State gameState = State.PLAY;
    private MinesweeperController controller;
    private HashSet<Coordinates> minesCoordinates;

    public void revealMines() {
        for(Coordinates coordinates: minesCoordinates) {
            setCellVisibility(coordinates.row, coordinates.col, true);
        }
    }

    public int getRemainingMines() {
        return numOfMinesOnBoard-flaggedCells;
    }

    public HashMap<String, JButton> buttons;

    public JButton getButtonByName(String name) {
        return buttons.get(name);
    }

    public void addButton(JButton button) {
        try {
            this.buttons.put(button.getName(), button);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException");
        }
    }

    public void setController(MinesweeperController controller) {
        this.controller = controller;
    }

    public int getFlaggedCells() {
        return flaggedCells;
    }

    public void setFlaggedCells(int flaggedCells) {
        this.flaggedCells = flaggedCells;
    }

    public State getGameState() {
        return gameState;
    }

    public void setGameState(State gameState) {
        this.gameState = gameState;
    }

    public enum State {
        GAME_OVER, WIN, PLAY
    }

    public void setCellFlagged(int row, int col, boolean flagged) {
        this.boardArray[row][col].setFlagged(flagged);
    }

    public MinesweeperModel(int width, int height, int numOfMines) {
        this.width = width + 2;
        this.height = height + 2;
        this.numOfMinesOnBoard = numOfMines;
        this.flaggedCells = 0;
        this.boardArray = new Cell[this.height][this.width];
        this.visibleBoard = new char[this.height][this.width];
        this.buttons = new HashMap<>();
        this.minesCoordinates = new HashSet<>();
        initializeBoard();
        putMinesOnBoard();
        countMinesOnBoard();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNumOfMines() {
        return numOfMinesOnBoard;
    }

    public void setNumOfMines(int numOfMines) {
        this.numOfMinesOnBoard = numOfMines;
    }

    private boolean isCellValid(int row, int col) {
        if(row < 1 || row > height - 2 || col < 1 || col > width - 2) {
            return false;
        }
        return true;
    }

    public boolean getCellVisibility(int row, int col) {

        return this.boardArray[row][col].isVisible();
    }

    public void setCellVisibility(int row, int col, boolean visible) {

        this.boardArray[row][col].setVisible(visible);
        this.visibleBoard[row][col] = (char)('0' + this.boardArray[row][col].getNumOfMines());
    }

    public int getCellMines(int row, int col) {
        if(!isCellValid(row, col)) {
            return -1;
        }
        return boardArray[row][col].getNumOfMines();
    }

    public void initializeBoard() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                boardArray[i][j] = new Cell();
            }
        }
    }

    public void putMinesOnBoard() {
        Random rand = new Random();

        for(int i = 0; i < numOfMinesOnBoard; i++) {
            int row = rand.nextInt(height - 2);
            int col = rand.nextInt(width - 2);
            if(boardArray[row+1][col+1].getNumOfMines() == 0) {
                boardArray[row+1][col+1].setNumOfMines(9);
                minesCoordinates.add(new Coordinates(row+1,col+1));
            } else {
                i--;
            }
        }
    }

    public void countMinesOnBoard() {
        for(int i = 1; i < height-1; i++) {
            for(int j = 1; j < width-1; j++) {
                countAdjacentMines(i,j);
            }
        }
    }

    public int isCellFlagged(int row, int col) {
        if(!isCellValid(row, col)) {
            return -1;
        }
        if (this.boardArray[row][col].isFlagged()) {
            return 1;
        }
        return 0;
    }

    private void countAdjacentMines(int row, int col) {
        if(row < 1 || row > height - 1 || col < 1 || col > width - 1) {
            return;
        }
        if(boardArray[row][col].getNumOfMines() == 9) {
            return;
        }
        int counter = 0;
        if(boardArray[row][col-1].getNumOfMines() == 9) counter++;
        if(boardArray[row-1][col-1].getNumOfMines() == 9) counter++;
        if(boardArray[row-1][col].getNumOfMines() == 9) counter++;
        if(boardArray[row-1][col+1].getNumOfMines() == 9) counter++;
        if(boardArray[row][col+1].getNumOfMines() == 9) counter++;
        if(boardArray[row+1][col+1].getNumOfMines() == 9) counter++;
        if(boardArray[row+1][col].getNumOfMines() == 9) counter++;
        if(boardArray[row+1][col-1].getNumOfMines() == 9) counter++;

        boardArray[row][col].setNumOfMines(counter);
    }

    public int revealCell(int row, int col, boolean userInput) {
        if (!isCellValid(row,col)) {
            return -1;
        }
        if (boardArray[row][col].isVisible()) {
            return -2;
        }

        int numOfMines = boardArray[row][col].getNumOfMines();

        if (numOfMines == 9) {
            if(userInput) {
                revealMines();
                controller.plotVisibleBoard();
            } else {
                return -3;
            }

        }
        boardArray[row][col].setVisible(true);
        visibleBoard[row][col] = (char)boardArray[row][col].getNumOfMines();

        if (numOfMines != 0) {

            return numOfMines;

        } else if (numOfMines >= 0){
            revealCell(row, col-1, false);
            revealCell(row-1, col, false);
            revealCell(row, col+1, false);
            revealCell(row+1, col,false);
            revealCell(row-1,col-1, false);
            revealCell(row+1,col+1, false);
            revealCell(row-1,col+1, false);
            revealCell(row+1,col-1, false);

        }
        return 0;
    }

    public char[][] getCurrentBoard() {
        for(int i = 0; i < height-2; i++) {
            for(int j = 0; j < width-2; j++) {
                Cell currentCell = boardArray[i+1][j+1];
                if(currentCell.isVisible()) {
                    this.visibleBoard[i + 1][j + 1] = (char)('0' + boardArray[i + 1][j + 1].getNumOfMines());
                } else {
                    if(boardArray[i + 1][j + 1].isFlagged()) {
                        this.visibleBoard[i + 1][j + 1] = 'f';
                    } else {
                        this.visibleBoard[i + 1][j + 1] = '#';
                    }
                }
            }
        }
        return this.visibleBoard;
    }

    public void printVisibleBoard() {
        for(int i = 0; i < height-2; i++) {
            for(int j = 0; j < width-2; j++) {
                Cell currentCell = boardArray[i+1][j+1];
                if(currentCell.isVisible()) {
                    System.out.print(boardArray[i + 1][j + 1].getNumOfMines() + " ");
                } else {
                    if(boardArray[i + 1][j + 1].isFlagged()) {
                        System.out.print("f ");
                    } else {
                        System.out.print("# ");
                    }
                }
            }
            System.out.println();
        }
    }

    public void printBoard() {
        for(int i = 0; i < height-2; i++) {
            for(int j = 0; j < width-2; j++) {
                Cell currentCell = boardArray[i+1][j+1];
                if(!currentCell.isVisible()) {
                    System.out.print(boardArray[i + 1][j + 1].getNumOfMines() + " ");
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println();
        }
    }

    private class Coordinates {
        private int row;
        private int col;

        public Coordinates(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }
    }


}
