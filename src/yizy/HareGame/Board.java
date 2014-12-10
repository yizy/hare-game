package yizy.HareGame;

import yizy.HareGame.Square.NeighborDirection;

public class Board {
    
	// constructor
    public Board(int row, int column) {
    	squares = new Square[row][column];
    }
    
    // method
    public boolean makeConnection(int row0, int column0, int row1, int column1) {
        int rowDiff = row0 - row1;
        int colDiff = column0 - column1;
        if (rowDiff > 1 || rowDiff < -1 || colDiff > 1 || colDiff < -1 
        		|| (rowDiff == 0 && colDiff == 0))
        	return false;
        
        Square sq0 = squares[row0][column0];
        Square sq1 = squares[row1][column1];
        
        if (colDiff == 0) {
            if (rowDiff > 0) {
                sq0.addNeghbor(NeighborDirection.DOWN, sq1);
                sq1.addNeghbor(NeighborDirection.UP, sq0);
            } else {
                sq0.addNeghbor(NeighborDirection.UP, sq1);
                sq1.addNeghbor(NeighborDirection.DOWN, sq0);
            }
        } else if (colDiff < 0) {
        	if (rowDiff > 0) {
                sq0.addNeghbor(NeighborDirection.UPPERRIGHT, sq1);
                sq1.addNeghbor(NeighborDirection.LOWERLEFT, sq0);
        	} else if (rowDiff < 0) {
                sq0.addNeghbor(NeighborDirection.LOWERRIGHT, sq1);
                sq1.addNeghbor(NeighborDirection.UPPERLEFT, sq0);
        	} else {
                sq0.addNeghbor(NeighborDirection.RIGHT, sq1);
                sq1.addNeghbor(NeighborDirection.LEFT, sq0);
        	}
        } else {
        	if (rowDiff > 0) {
                sq0.addNeghbor(NeighborDirection.UPPERLEFT, sq1);
                sq1.addNeghbor(NeighborDirection.LOWERRIGHT, sq0);
        	} else if (rowDiff < 0) {
                sq0.addNeghbor(NeighborDirection.LOWERLEFT, sq1);
                sq1.addNeghbor(NeighborDirection.UPPERRIGHT, sq0);
        	} else {
                sq0.addNeghbor(NeighborDirection.LEFT, sq1);
                sq1.addNeghbor(NeighborDirection.RIGHT, sq0);
        	}
        }
        
        return true;
    }
    
    // access methods
    public void addSquare(int row, int column) {
    	squares[row][column] = new Square(row, column);
    }
    
    public Square getSquare(int row, int column) {
    	return squares[row][column];
    }

    // members
    private Square[][] squares;
}
