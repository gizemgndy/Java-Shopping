import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

public class Items {

	 //Setting up the variables
	public JTextField field;
	String bookItem;
	
	public Items() {
		// TODO Auto-generated constructor stub
		field = new JTextField();
		field.setEditable(false);
		field.setText("Not booked yet ");
		field.setBackground(Color.pink);
		field.setFont(new Font("Tahoma", Font.PLAIN, 15));
		field.setEditable(false);
	}
	
	//Edit the display and text of booked products
	public void setBooked() {
			field.setForeground(Color.WHITE);
			field.setFont(new Font("Tahoma", Font.PLAIN, 15));
			field.setBackground(Color.orange);
			field.setEditable(false);
			bookItem = Thread.currentThread().getName();
			field.setText("Booked by Agent " + bookItem);
	}
}