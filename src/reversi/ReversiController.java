package reversi;

public class ReversiController implements IController{

	IModel model;
	IView view;
	
	
	@Override
	public void initialise(IModel model, IView view) {
		// TODO Auto-generated method stub
		this.model = model;
		this.view = view;
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		//System.out.println("Startup/Reset pressed");
		
		model.clear(0);
		model.setPlayer(1);
		model.setFinished(false);
		model.setBoardContents(3, 3, 1);
		model.setBoardContents(3, 4, 2);
		model.setBoardContents(4, 3, 2);
		model.setBoardContents(4, 4, 1);
		
		view.feedbackToUser(1, "White player - choose where to put your piece");
		view.feedbackToUser(2, "Black player - not your turn");
		view.refreshView();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		model.setFinished(false);
		
		int player = model.getPlayer();
		int opposite;
		String valid, invalid;
		if(player == 1) {
			opposite = 2;
			valid = "White player - choose where to put your piece";
			invalid = "Black player - not your turn";
		}
		else {
			opposite = 1;
			valid = "Black player - choose where to put your piece";
			invalid = "White player - not your turn";
		}
		
		view.feedbackToUser(player, valid);
		view.feedbackToUser(opposite, invalid);
				
		int white_score = 0;
		int black_score = 0;
		boolean playerMove = false;
		boolean oppositeMove = false;
		
		//Check if game is finished
		for(int y = 0; y < 8; y++) {
			for(int x = 0; x < 8; x++) {
				int current_square = model.getBoardContents(x, y);
				
				if(current_square == 1) {
					white_score++;
				}
				else if(current_square == 2) {
					black_score++;
				}
				else {
					if(count(player, x, y, false) > 0) {
						playerMove = true;
					}
					if(count(opposite, x, y, false) > 0) {
						oppositeMove = true;
					}
				}
			}
		}
		
		if(!playerMove && !oppositeMove) {
			model.setFinished(true);
		}
		
		if(!playerMove) {
			if(player == 1) {
				view.feedbackToUser(player, "White player - not your turn");
				view.feedbackToUser(opposite, "Black player - choose where to put your piece");
			}
			else {
				view.feedbackToUser(player, "Black player - not your turn");
				view.feedbackToUser(opposite, "White player - choose where to put your piece");
			}
			model.setPlayer(opposite);
		}
		
		if(model.hasFinished()) {
			if(white_score > black_score) {
				view.feedbackToUser(1, "White won. White " + white_score + " to Black " + black_score + ". Restart to continue.");
				view.feedbackToUser(2, "White won. White " + white_score + " to Black " + black_score + ". Restart to continue.");
			}
			
			else if(black_score > white_score) {
				view.feedbackToUser(1, "Black won. Black " + black_score + " to White " + white_score + ". Restart to continue.");
				view.feedbackToUser(2, "Black won. Black " + black_score + " to White " + white_score + ". Restart to continue.");
			}
			
			else {
				view.feedbackToUser(1, "Draw. Both players ended with " + white_score + " pieces. Restart to continue.");
				view.feedbackToUser(2, "Draw. Both players ended with " + white_score + " pieces. Restart to continue.");
			}
		}
	}

	@Override
	public void squareSelected(int player, int x, int y) {
		// TODO Auto-generated method stub
		//System.out.println("Button pressed: Player " + player + " X " + x + " Y " + y);
		int opposite;
		if(player == 1) {
			opposite = 2;
		}
		else {
			opposite = 1;
		}
		
		if(model.hasFinished()) {
			return;
		}
		
		if(model.getPlayer() != player) {
			view.feedbackToUser(player, "It is not your turn!");
			return;
		}
		
		if(model.getBoardContents(x, y) > 0) {
			view.feedbackToUser(player, "Invalid location to play a piece");
		}
		else if(count(player, x, y, false) > 0) {
			count(player, x, y, true);
			model.setPlayer(opposite);
			update();
		}
		else {
			view.feedbackToUser(player, "Invalid location to play a piece");
		}
	}

	@Override
	public void doAutomatedMove(int player) {
		// TODO Auto-generated method stub
		//System.out.println("AI move pressed by " + player);
		
		int finalX = 0;
		int finalY = 0;
		int bestSquare = 0;
		
		for(int y = 0; y < 8; y++) {
			for(int x = 0; x < 8; x++) {
				if(model.getBoardContents(x, y) == 0 && count(player, x, y, false) > bestSquare) {
					bestSquare = count(player, x, y, false);
					finalX = x;
					finalY = y;
				}
			}
		}
		
		squareSelected(player, finalX, finalY);
	}
	
	private int count(int player, int x, int y, boolean flip) {
		int opposite;
		int total = 0;
		if(player == 1) {
			opposite = 2;
		}
		else {
			opposite = 1;
		}
		
		int[] xArray = new int[64];
		int[] yArray = new int[64];
		
		for(int x_offset = -1; x_offset <= 1; x_offset++) {
			for(int y_offset = -1; y_offset <= 1; y_offset++) {
				int direction_total = 0;
				int x_checked = x;
				int y_checked = y;
				
				if(x_offset == 0 && y_offset == 0) {
					continue;
				}

				boolean check = true;
				while(check) {
					x_checked += x_offset;
					y_checked += y_offset;
					
					if(x_checked < 0 || y_checked < 0 || x_checked > 7 || y_checked > 7) {
						direction_total = 0;
						break;
					}
					
					int current_square = model.getBoardContents(x_checked, y_checked);
					
					if(current_square == opposite) {
						xArray[direction_total] = x_checked;
						yArray[direction_total] = y_checked;
						direction_total++;
					}
					
					else if(current_square == player) {
						if(direction_total > 0 && flip == true) {
							for(int i = 0; i < direction_total; i++) {
								model.setBoardContents(xArray[i], yArray[i], player);
							}
							model.setBoardContents(x, y, player);
						}
						check = false;
					}
					else {
						direction_total = 0;
						check = false;
					}
				}
				total += direction_total;
				
			}
		}
		
		if(flip) {
			view.refreshView();
		}
		
		return total;
	}

}
