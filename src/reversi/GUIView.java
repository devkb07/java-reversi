package reversi;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIView implements IView{

	IModel model;
	IController controller;
	JFrame frame1 = new JFrame();
	JFrame frame2 = new JFrame();
	JLabel label1 = new JLabel();
	JLabel label2 = new JLabel();
	JPanel board1 = new JPanel();
	JPanel board2 = new JPanel();
	BoardSquareButton[][] whiteBoard = new BoardSquareButton[8][8];
	BoardSquareButton[][] blackBoard = new BoardSquareButton[8][8];
	
	@Override
	public void initialise(IModel model, IController controller) {
		// TODO Auto-generated method stub
		this.model = model;
		this.controller = controller;
		
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame1.setTitle("Reversi - white player");
		frame1.setLayout(new BorderLayout());
		frame2.setTitle("Reversi - black player");
		frame2.setLayout(new BorderLayout());
		
		label1.setText("KUTAS");
		label2.setText("KUTAS");
		frame1.add(label1, BorderLayout.NORTH);
		frame2.add(label2, BorderLayout.NORTH);
		
		JPanel button_panel1 = new JPanel(new GridLayout(2,1));
		JPanel button_panel2 = new JPanel(new GridLayout(2,1));
		JButton ai_button1 = new JButton("Greedy AI (play white)");
		ai_button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.doAutomatedMove(1);
			}
		});
		JButton ai_button2 = new JButton("Greedy AI (play black)");
		ai_button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.doAutomatedMove(2);
			}
		});
		JButton restart1 = new JButton("Restart");
		restart1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.startup();
			}
		});
		JButton restart2 = new JButton("Restart");
		restart2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.startup();
			}
		});
		button_panel1.add(ai_button1);
		button_panel2.add(ai_button2);
		button_panel1.add(restart1);
		button_panel2.add(restart2);
		frame1.add(button_panel1, BorderLayout.SOUTH);
		frame2.add(button_panel2, BorderLayout.SOUTH);
		
		board1 = buildWhiteGrid();
		board2 = buildBlackGrid();
		frame1.add(board1, BorderLayout.CENTER);
		frame2.add(board2, BorderLayout.CENTER);
		
		frame1.setLocation(340, 100);
		frame2.setLocation(760, 100);
		
		frame1.pack();
		frame2.pack();
		
		frame1.setVisible(true);
		frame2.setVisible(true);
	}

	
	public JPanel buildWhiteGrid() {
		JPanel whiteGrid = new JPanel();
		whiteGrid.setLayout(new GridLayout(8,8));
		for(int i = 0; i < 8; i++) {
			int y = i;
			for(int j = 0; j < 8; j++) {
				int x = j;
				
				BoardSquareButton square = new BoardSquareButton(model, controller);
				whiteBoard[i][j] = square;
				whiteGrid.add(square);
				square.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						controller.squareSelected(1, x, y);
					}
				});
				
			}
		}
		return whiteGrid;
	}
	
	public JPanel buildBlackGrid() {
		JPanel blackGrid = new JPanel();
		blackGrid.setLayout(new GridLayout(8,8));
		for(int i = 7; i >= 0; i--) {
			int y = i;
			for(int j = 7; j >= 0; j--) {
				int x = j;
				
				BoardSquareButton square = new BoardSquareButton(model, controller);
				blackBoard[i][j] = square;
				blackGrid.add(square);
				square.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						controller.squareSelected(2, x, y);
					}
				});
				
			}
		}
		return blackGrid;
	}
	
	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		for(int y = 0; y < 8; y++) {
			for(int x = 0; x < 8; x++) {
				int player = model.getBoardContents(x, y);
				whiteBoard[y][x].updateButton(player);
				blackBoard[y][x].updateButton(player);
			}
		}

	}

	@Override
	public void feedbackToUser(int player, String message) {
		// TODO Auto-generated method stub
		if(player == 1) {
			label1.setText(message);
		}
		else {
			label2.setText(message);
		}
	}

}
