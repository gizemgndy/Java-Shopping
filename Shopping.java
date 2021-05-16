import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Shopping extends JFrame implements ActionListener, Runnable {
	
	 //create variables
	 
	private int itemNumber;
	private int numberAgent;
	private int waitTime;
	private JFrame frame;
	private JTextField txtItems;
	private JTextField txtAgents;
	private JTextField txtTime;
	private JPanel pnlItem;
	private Items[] items;
	private Random ran = new Random();

	private int counter = 0;
	private Lock lock = new ReentrantLock();
	
	 
	public static void main(String[] args) {
		Shopping window = new Shopping();
		window.frame.setVisible(true);

	}


	//Create the application.
	public Shopping() {
		initialize();
	}

	// Initialize the contents of the frame.
	private void initialize() {
		frame = new JFrame();
		frame.setSize(1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel itemsLabel = new JLabel("Number of Items");
		panel.add(itemsLabel);

		txtItems = new JTextField();
		panel.add(txtItems);
		txtItems.setColumns(10);

		JLabel AgentsLabel = new JLabel("Number of Agents");
		panel.add(AgentsLabel);

		txtAgents = new JTextField();
		panel.add(txtAgents);
		txtAgents.setColumns(10);

		JLabel maxtxtTime = new JLabel("Maximum Waiting Time");
		panel.add(maxtxtTime);

		txtTime = new JTextField();
		panel.add(txtTime);
		txtTime.setColumns(10);

		//create items button 
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(this);
		panel.add(btnCreate);

		//button that makes the booked
		JButton btnGood = new JButton("Good");
		btnGood.addActionListener(this);
		panel.add(btnGood);
	
		
		//Seat area starts
		pnlItem = new JPanel();
		frame.getContentPane().add(pnlItem, BorderLayout.CENTER);
		pnlItem.setLayout(new GridLayout(8, 8, 10, 10));
		//Seat area ends
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		//function that makes  items
		if (arg0.getActionCommand().equals("Create")) {
			itemNumber = Integer.parseInt(txtItems.getText());
			items = new Items[itemNumber];
			for (int i = 0; i < itemNumber; i++) {
				Items s = new Items();
				items[i] = s;
				pnlItem.add(s.field);
			}
			
			frame.validate();
		}
		//function that makes booked
		else if (arg0.getActionCommand().equals("Good")) {
			numberAgent = Integer.parseInt(txtAgents.getText());
			waitTime = Integer.parseInt(txtTime.getText());
			for (int i = 1; i <= numberAgent; i++) {
				Thread t = new Thread(this);
				t.setName(i + "");
				t.start();

			}
		}

	}
	
	//Counter as index in here to set book items
	public void run() {
		while (counter<items.length) {
			try {
				Thread.sleep(ran.nextInt(waitTime));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lock.lock();
			if(counter<items.length) {
			items[counter].setBooked();
			
			
			counter++;}
			else {
				endMessage();
			}
			lock.unlock();
			
		}
	}

	 //End message and calculating booked items by agents
	
	private synchronized void endMessage() {
		// TODO Auto-generated method stub
		int [] agentsitems= new int[numberAgent];
		for (int i = 0; i < agentsitems.length; i++) {
			int counter=0;
			for (int j = 0; j < items.length; j++) {
				int bookedBy=Integer.parseInt(items[j].bookItem);
				if((bookedBy-1)==i) {
					counter++;
					agentsitems[i]=counter;
				}
			}
		}
		String str="";
		for (int i = 0; i < agentsitems.length; i++) {
			str=str+"Agent "+(i+1)+" took "+agentsitems[i]+" items.\n";
		}
		
		//Display messages about buy
		 JOptionPane.showMessageDialog(this, str);
		 int k= JOptionPane.showConfirmDialog(this, "Are you going to buy?", "Buy", JOptionPane.YES_NO_OPTION);
			if(k==JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(this, "Purchase Successful","Message",JOptionPane.INFORMATION_MESSAGE);
				System.exit(1);
			}
			else {
				System.exit(1);//terminate the applications
			}
	

	}
}