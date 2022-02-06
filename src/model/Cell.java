package model;

public class Cell {
    private int numOfMines;
    private boolean visible;
    private boolean isFlagged;

    public Cell() {
        this.numOfMines = 0;
        this.visible = false;
        this.isFlagged = false;
    }

    public int getNumOfMines() {

        return numOfMines;
    }

    public void setNumOfMines(int numOfMines) {

        this.numOfMines = numOfMines;
    }

    public boolean isVisible() {

        return this.visible;
    }

    public void setVisible(boolean visible) {

        this.visible = visible;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
}
