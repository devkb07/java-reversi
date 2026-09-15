package reversi;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class ReversiMain
{
	IModel model;
	IView view;
	IController controller;

	ReversiMain()
	{
		model = new SimpleModel();

		view = new GUIView();

		controller = new ReversiController();

		model.initialise(8, 8, view, controller);
		controller.initialise(model, view);
		view.initialise(model, controller);
		
		controller.startup();
	}
	
	public static void main(String[] args)
	{
		new ReversiMain();
	}
}
