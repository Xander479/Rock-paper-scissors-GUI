import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.Random;
import javax.swing.*;

public class JankenGUI {
	enum Choice {
		ROCK(0),
		PAPER(1),
		SCISSORS(2);
		
		private int value;
		Choice(int value) {
			this.value = value;
		}
		int getValue() {
			return this.value;
		}
	}
	static Choice myChoice;
	static Choice cpuChoice;
	static int myWins;
	static int cpuWins;
	static JLabel myWinsText;
	static JLabel cpuWinsText;
	static JLabel log = new JLabel();
	public static void main(String[] args) {
		// Set up frame
		JFrame frame = new JFrame("Rock, Paper, Scissors");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500,300));
		Container pane = frame.getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c;
		
		// Create text to keep scores
		myWinsText = new JLabel("Your wins: " + myWins);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		pane.add(myWinsText, c);
		
		cpuWinsText = new JLabel("CPU wins: " + cpuWins);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		pane.add(cpuWinsText, c);
		
		JLabel text = new JLabel("Which do you choose?");
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridwidth = 3;
		c.gridy = 3;
		c.weightx = 0.5;
		c.weighty = 0.5;
		pane.add(text, c);
		
		// Create buttons for player to press
		JButton rock = new JButton("Rock");
		rock.setPreferredSize(new Dimension(100, 30));
		rock.setMnemonic(KeyEvent.VK_R);
		rock.addActionListener(new RockEvent());
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 4;
		c.weightx = 0.5;
		c.weighty = 0.8;
		pane.add(rock, c);
		
		JButton paper = new JButton("Paper");
		paper.setPreferredSize(new Dimension(100, 30));
		paper.setMnemonic(KeyEvent.VK_P);
		paper.addActionListener(new PaperEvent());
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.weightx = 0.5;
		c.weighty = 0.8;
		pane.add(paper, c);
		
		JButton scissors = new JButton("Scissors");
		scissors.setPreferredSize(new Dimension(100, 30));
		scissors.setMnemonic(KeyEvent.VK_S);
		scissors.addActionListener(new ScissorsEvent());
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 4;
		c.weightx = 0.5;
		c.weighty = 0.8;
		pane.add(scissors, c);
		
		// log is instantiated as a static class member so event listeners can set its text
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridwidth = 3;
		c.gridy = 5;
		c.weightx = 0.5;
		c.weighty = 0.5;
		pane.add(log, c);
		
		frame.setJMenuBar(createMenuBar());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	// Returns a MenuBar ready to be added
	static JMenuBar createMenuBar() {		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("About");
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);
		JMenuItem menuItem = new JMenuItem("GitHub repo link");
		menuItem.setMnemonic(KeyEvent.VK_G);
		menuItem.addActionListener(new GitHubEvent());
		menu.add(menuItem);
		return menuBar;
	}
	
	// Selects CPU's choice, and calculates the winner
	static void calculate(Choice choice) {
		Random r = new Random();
		// Randomly selects a choice for the cpu
		myChoice = choice;
		cpuChoice = Choice.values()[r.nextInt(Choice.values().length)];
		// 1 if player wins, 2 if cpu wins, 0 if tie
		int outcome = (Choice.values().length + myChoice.getValue() - cpuChoice.getValue()) % Choice.values().length;
		switch(outcome) {
			case 0:
				log.setText("CPU also chose " + cpuChoice + ". It's a tie!");
				break;
			case 1:
				myWins++;
				myWinsText.setText("Your wins: " + myWins);
				log.setText("CPU chose " + cpuChoice + ". You win!");
				break;
			case 2:
				cpuWins++;
				cpuWinsText.setText("CPU wins: " + cpuWins);
				log.setText("CPU chose " + cpuChoice + ". You lose!");
				break;
			default:
				System.err.println("Error in winner calculation.");
		}
	}
}

// Event takes you to the GitHub repo
class GitHubEvent implements ActionListener {
	public void actionPerformed(ActionEvent event) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(new URI("https://github.com/Xander479/Rock-paper-scissors-GUI"));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			JFrame frame = new JFrame("Error");
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			JLabel label = new JLabel("Your platform doesn't support this action.", JLabel.CENTER);
			label.setPreferredSize(new Dimension(350, 50));
			frame.getContentPane().add(label);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
	}
}

// Event for Rock button
class RockEvent implements ActionListener {
	public void actionPerformed(ActionEvent event) {
		JankenGUI.calculate(JankenGUI.Choice.ROCK);
	}
}

// Event for Paper button
class PaperEvent implements ActionListener {
	public void actionPerformed(ActionEvent event) {
		JankenGUI.calculate(JankenGUI.Choice.PAPER);
	}
}

// Event for Scissors button
class ScissorsEvent implements ActionListener {
	public void actionPerformed(ActionEvent event) {
		JankenGUI.calculate(JankenGUI.Choice.SCISSORS);
	}
}
