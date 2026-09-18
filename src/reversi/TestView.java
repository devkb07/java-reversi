package reversi;

public class TestView implements IView{

	//Necessary to test ReversiController as it calls refreshView() and feedbackToUser()
	
	public String whiteCurrentMessage, blackCurrentMessage;
	public int refreshTotal = 0;
	
	
	@Override
	public void initialise(IModel model, IController controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		refreshTotal++;
	}

	@Override
	public void feedbackToUser(int player, String message) {
		// TODO Auto-generated method stub
		if(player == 1) {
			whiteCurrentMessage = message;
		}
		else {
			blackCurrentMessage = message;
		}
	}
	
}
