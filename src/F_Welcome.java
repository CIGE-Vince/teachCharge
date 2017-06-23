import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class F_Welcome extends JLabel {
	
	String str;
	static Icon icon=new ImageIcon("ico.gif");
	public F_Welcome(String str){
		this.str=str;
		this.initialFrame();
	}
	
	public void initialFrame(){
		this.setIcon(icon);
 		this.setHorizontalAlignment(JLabel.CENTER);
 		this.setVerticalAlignment(JLabel.CENTER);
	}
}