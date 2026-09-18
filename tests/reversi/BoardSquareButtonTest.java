package reversi;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BoardSquareButton Tests")
class BoardSquareButtonTest {
	
	IModel model;
	IController controller;

	@Test
	@DisplayName("Updating button correctly draws either a black or white piece of the correct size")
	void UpdateButtonPieceTest() {
		BoardSquareButton white = new BoardSquareButton(model, controller);
		BoardSquareButton black = new BoardSquareButton(model, controller);
		white.updateButton(1);
		black.updateButton(2);
		assertEquals(Color.WHITE, white.drawDiskColor);
		assertEquals(Color.BLACK, white.diskBorderColor);
		assertEquals(Color.BLACK, black.drawDiskColor);
		assertEquals(Color.WHITE, black.diskBorderColor);
		assertEquals(46, white.diskWidth);
		assertEquals(46, black.diskWidth);
		assertEquals(46, white.diskHeight);
		assertEquals(46, black.diskHeight);
		
	}
	
	@Test
	@DisplayName("Updating button can remove a piece")
	void UpdateButtonClearTest() {
		BoardSquareButton button = new BoardSquareButton(model, controller);
		button.updateButton(1);
		button.updateButton(0);
		assertNull(button.drawDiskColor);
		assertEquals(0, button.diskWidth);
		assertEquals(0, button.diskHeight);
	}
	
	@Test
    @DisplayName("Getters and setters properly update button colors and border sizes")
    void GettersAndSettersTest() {
        BoardSquareButton button = new BoardSquareButton(model, controller);
        
        button.setDrawColor(Color.RED);
        button.setBorderColor(Color.BLUE);
        button.setBorderSize(2);
        
        assertEquals(Color.RED, button.getDrawColor());
        assertEquals(Color.BLUE, button.getBorderColor());
        assertEquals(2, button.getBorderSize());
    }
	
}
