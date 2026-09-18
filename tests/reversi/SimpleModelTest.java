package reversi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SimpleModel Tests")
class SimpleModelTest {
	
	private SimpleModel model;
    private ReversiController controller;
    private TestView view;
	
	@BeforeEach
	void gameSetup() {
		model = new SimpleModel();

        model.initialise(8, 8, view, controller);
	}

	@Test
	@DisplayName("Board width and height return correct values")
	void WidthHeightReturnTest() {
		assertEquals(8, model.getBoardHeight());
		assertEquals(8, model.getBoardWidth());
	}
	
	@Test
	@DisplayName("Board contents are correctly set")
	void SetBoardContentsTest() {
		model.setBoardContents(1, 1, 1);
		assertEquals(1, model.getBoardContents(1,1));
	}
	
	@Test
	@DisplayName("Clearing the board removes all pieces")
	void ClearTest() {
		model.clear(0);
		for(int j = 0; j < model.getBoardHeight(); j++) {
			for(int i = 0; i < model.getBoardWidth(); i++) {
				assertEquals(0, model.getBoardContents(i, j));
			}
		}
	}
	
	@Test
	@DisplayName("Clear function can set all pieces to a piece colour")
	void ClearWithPiecesTest() {
		model.clear(1);
		for(int j = 0; j < model.getBoardHeight(); j++) {
			for(int i = 0; i < model.getBoardWidth(); i++) {
				assertEquals(1, model.getBoardContents(i, j));
			}
		}
		
		model.clear(2);
		for(int j = 0; j < model.getBoardHeight(); j++) {
			for(int i = 0; i < model.getBoardWidth(); i++) {
				assertEquals(2, model.getBoardContents(i, j));
			}
		}
	}
	
	@Test
	@DisplayName("Player is correctly set")
	void SetPlayerTest() {
		model.setPlayer(2);
		assertEquals(2, model.getPlayer());
	}
	
	@Test
	@DisplayName("Finish flag is correctly set")
	void SetFinishTest() {
		model.setFinished(true);
		assertTrue(model.hasFinished());
	}
	
}
