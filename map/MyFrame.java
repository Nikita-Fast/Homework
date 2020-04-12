package map;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyFrame extends JFrame {
	
	private JPanel panel;
	
	public MyFrame() {
		super("map");
		
		panel = new MyPanel();
		Dimension dim = panel.getPreferredSize();
		dim.width = 500;
		dim.height = 500;
		panel.setPreferredSize(dim);
		
		setLayout(new BorderLayout());
		
		add(panel, BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(520, 520);
		setVisible(true);
	}
}
