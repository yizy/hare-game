package yizy.HareGame.UI;

import javax.swing.JButton;

import yizy.HareGame.Square;

public class JGameButton extends JButton {

	private static final long serialVersionUID = 1777516711122537707L;
    private Square square;
    
	public Square getSquare() {
		return square;
	}
    
	public void setSquare(Square square) {
		this.square = square;
	}
}
