package reversi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ReversiController Class Tests")
class ReversiControllerTest {
	
	private SimpleModel model;
    private ReversiController controller;
    private TestView view;
	
	@BeforeEach
	void gameSetup() {
		model = new SimpleModel();
        controller = new ReversiController();
        view = new TestView();

        model.initialise(8, 8, view, controller);
        controller.initialise(model, view);
        controller.startup();
	}
	
	
	@Test
	@DisplayName("Setup creates 4 correctly coloured pieces in centre")
	void CentrePiecesTest() {
		assertEquals(1, model.getBoardContents(3, 3));
        assertEquals(2, model.getBoardContents(3, 4));
        assertEquals(2, model.getBoardContents(4, 3));
        assertEquals(1, model.getBoardContents(4, 4));
	}
	
	@Test
	@DisplayName("Setup makes white play first and sets hasFinished flag false")
	void WhiteFirstTest() {
		assertEquals(1, model.getPlayer());
		assertFalse(model.hasFinished());
		assertEquals(view.whiteCurrentMessage, "White player - choose where to put your piece");
		assertEquals(view.blackCurrentMessage, "Black player - not your turn");
	}
	
	@Test
	@DisplayName("Valid move causes new piece to spawn and flip relevant pieces")
	void ValidMoveTest() {
		controller.squareSelected(1, 2, 4);
		assertEquals(1, model.getBoardContents(2, 4));
		assertEquals(1, model.getBoardContents(3, 4));
		assertEquals(view.whiteCurrentMessage, "White player - not your turn");
		assertEquals(view.blackCurrentMessage, "Black player - choose where to put your piece");
		
	}
	
	@Test
	@DisplayName("Pieces in different directions can flip at once")
	void MultipleDirectionsTest() {
		controller.squareSelected(1, 5, 3);
		controller.squareSelected(2, 5, 2);
		controller.squareSelected(1, 4, 2);
		controller.squareSelected(2, 5, 4);
		assertEquals(2, model.getBoardContents(5, 3));
		assertEquals(2, model.getBoardContents(4, 4));
	}
	
	@Test
	@DisplayName("Runs longer than one can flip at once")
	void LongRunTest() {
		controller.squareSelected(1, 5, 3);
		controller.squareSelected(2, 5, 2);
		controller.squareSelected(1, 2, 4);
		controller.squareSelected(2, 2, 3);
		controller.squareSelected(1, 1, 3);
		assertEquals(1, model.getBoardContents(2, 3));
		assertEquals(1, model.getBoardContents(3, 3));
		assertEquals(1, model.getBoardContents(4, 3));
	}
	
	@Test
	@DisplayName("Invalid move causes no change")
	void InvalidMoveTest() {
		controller.squareSelected(1, 0, 0);
		assertEquals(0, model.getBoardContents(0, 0));
		assertEquals(1, model.getPlayer());
		assertEquals(view.whiteCurrentMessage, "Invalid location to play a piece");
		assertEquals(view.blackCurrentMessage, "Black player - not your turn");
		
	}
	
	@Test
	@DisplayName("Clicking on existing piece makes no change")
	void ExistingPieceTest() {
		controller.squareSelected(1, 3, 3);
		controller.squareSelected(1, 4, 3);
		assertEquals(1, model.getBoardContents(3, 3));
		assertEquals(2, model.getBoardContents(4, 3));
		assertEquals(1, model.getPlayer());
		assertEquals(view.whiteCurrentMessage, "Invalid location to play a piece");
		assertEquals(view.blackCurrentMessage, "Black player - not your turn");
	}
	
	@Test
	@DisplayName("Moving out of turn makes no change")
	void OutOfTurnMoveTest() {
		controller.squareSelected(2, 5, 4);
		assertEquals(0, model.getBoardContents(5, 4));
		assertEquals(1, model.getBoardContents(4, 4));
		assertEquals(1, model.getPlayer());
		assertEquals(view.whiteCurrentMessage, "White player - choose where to put your piece");
		assertEquals(view.blackCurrentMessage, "It is not your turn!");
	}
	
	@Test
	@DisplayName("Valid moves cause the view to refresh")
	void ValidMoveRefreshViewTest() {
		int initialRefreshTotal = view.refreshTotal;
		controller.squareSelected(1, 2, 4);
		int newRefreshTotal = view.refreshTotal;
		assertTrue(initialRefreshTotal < newRefreshTotal);
	}
	
	@Test
	@DisplayName("Invalid moves don't cause the view to refresh")
	void InvalidMoveRefreshViewTest() {
		int initialRefreshTotal = view.refreshTotal;
		controller.squareSelected(1, 6, 4);
		controller.squareSelected(2, 2, 4);
		int newRefreshTotal = view.refreshTotal;
		assertFalse(initialRefreshTotal < newRefreshTotal);
	}
	
	@Test
	@DisplayName("AI can move and player swaps")
	void AIMoveTest() {
		controller.doAutomatedMove(1);
		assertEquals(2, model.getPlayer());
		assertEquals(view.whiteCurrentMessage, "White player - not your turn");
		assertEquals(view.blackCurrentMessage, "Black player - choose where to put your piece");
	}
	
	@Test
	@DisplayName("Wrong AI playing doesn't swap player")
	void AIInvalidMoveTest() {
		controller.doAutomatedMove(2);
		assertEquals(1, model.getPlayer());
		assertEquals(view.whiteCurrentMessage, "White player - choose where to put your piece");
		assertEquals(view.blackCurrentMessage, "It is not your turn!");
	}
	
	@Test
	@DisplayName("Finish flag gets set once no moves left")
	void SuccessfulFinishTest() {
		for(int i = 0; i < 100; i++) {
			controller.doAutomatedMove(1);
			controller.doAutomatedMove(2);
		}
		assertTrue(model.hasFinished());
		assertTrue(view.whiteCurrentMessage.contains("won."));
	}

}
