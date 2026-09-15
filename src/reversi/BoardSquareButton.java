package reversi;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class BoardSquareButton extends JButton {

	Color drawColor; 
	Color borderColor;
	Color drawDiskColor;
	Color diskBorderColor;
	int borderSize; 
	int diskBorderSize;
	int diskWidth;
	int diskHeight;
	
	public BoardSquareButton( int width, int height, Color color, int borderWidth, Color borderCol, 
			int diskWid, int diskHei, Color diskColor, int diskBorderWidth, Color diskBorderCol, IModel model, IController controller )
	{
		borderSize = borderWidth;
		drawColor = color;
		borderColor = borderCol;
		diskBorderSize = diskBorderWidth;
		diskWidth = diskWid;
		diskHeight = diskHei;
		drawDiskColor = diskColor;
		diskBorderColor = diskBorderCol;
		
		
		setMinimumSize( new Dimension(width, height) );
		setPreferredSize( new Dimension(width, height) );
	}

	public BoardSquareButton( IModel model, IController controller, Color diskColor, Color diskBorder ){
			// Call the other constructor with some default values
		this( 50, 50, Color.GREEN, 1, Color.BLACK, 46, 46, diskColor, 1, diskBorder,  model, controller);
			
	}
	
	public BoardSquareButton( IModel model, IController controller )
	{
		// Call the other constructor with some default values
		this( 50, 50, Color.GREEN, 1, Color.BLACK, 0, 0, null, 0, null, model, controller );
	}
	
	public void updateButton(int player) {
		if(player == 1) {
			drawDiskColor = Color.WHITE;
			diskBorderColor = Color.BLACK;
			diskWidth = 46;
			diskHeight = 46;
			diskBorderSize = 1;
		}
		else if(player == 2) {
			drawDiskColor = Color.BLACK;
			diskBorderColor = Color.WHITE;
			diskWidth = 46;
			diskHeight = 46;
			diskBorderSize = 1;
		}
		else {
			drawDiskColor = null;
			diskBorderColor = null;
			diskWidth = 0;
			diskHeight = 0;
			diskBorderSize = 0;
		}
		repaint();
	}
	
	

	public Color getDrawColor()
	{
		return drawColor;
	}

	public void setDrawColor(Color drawColor)
	{
		this.drawColor = drawColor;
	}

	public Color getBorderColor()
	{
		return borderColor;
	}

	public void setBorderColor(Color borderColor)
	{
		this.borderColor = borderColor;
	}

	public int getBorderSize()
	{
		return borderSize;
	}

	public void setBorderSize(int borderSize)
	{
		this.borderSize = borderSize;
	}

	protected void paintComponent(Graphics g)
	{
		//super.paintComponent(arg0);
		if ( borderColor != null )
		{
			g.setColor(borderColor);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if ( drawColor != null )
		{
			g.setColor(drawColor);
			g.fillRect(borderSize, borderSize, getWidth()-borderSize*2, getHeight()-borderSize*2);
		}
		if(diskBorderColor != null) {
			g.setColor(diskBorderColor);
			g.fillOval((getWidth() - diskWidth)/2 + 1 - diskBorderSize, (getHeight() - diskHeight)/2 + 1 - diskBorderSize, diskWidth, diskHeight);
		}
		if(drawDiskColor != null) {
			g.setColor(drawDiskColor);
			g.fillOval((getWidth() - diskWidth)/2 + 1, (getHeight() - diskHeight)/2 + 1, diskWidth-diskBorderSize*2, diskHeight-diskBorderSize*2);
		}
	}
}
