import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Browse extends JPanel {
	private static final long serialVersionUID = 1L;
	static JButton jbt1 = new JButton("Choose New I/P Sheet");
	static JButton jbt2 = new JButton("Choose Previous O/P Sheet");
	static JButton jbt3 = new JButton("Generate Final I/P Sheet");
	static JTextArea textArea = new JTextArea();
	static JScrollPane scrollPane = new JScrollPane(textArea);
	static JTextArea textArea2 = new JTextArea(1, 10);
	static JScrollPane scrollPane2 = new JScrollPane(textArea2);
	static JTextArea textArea3 = new JTextArea(1, 10);
	static JScrollPane scrollPane3 = new JScrollPane(textArea2);

	static BufferedReader csvreader;
	static String csvSplitBy = ",";

	// To store CSV data into ArrayList
	static ArrayList<String> inputcsv = new ArrayList<>();
	static ArrayList<String> outputcsv = new ArrayList<>();

	// To store processed output
	static ArrayList<String> comparecsv = new ArrayList<>();
	static ArrayList<String> finalunique = new ArrayList<>();

	public Browse() {
		Font font = new Font("Calibri", Font.PLAIN, 12);
		Font font1 = new Font("Arial Black", Font.PLAIN, 15);
		jbt1.setFont(font);
		jbt2.setFont(font);
		jbt3.setFont(font1);
		jbt1.setMaximumSize(new Dimension(300, 300));
		jbt2.setMaximumSize(new Dimension(300, 300));
		jbt3.setMaximumSize(new Dimension(400, 400));
		
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(20));
		box.add(jbt1);
		box.add(textArea);
		box.add(Box.createVerticalStrut(20));
		box.add(jbt2);
		box.add(textArea2);
		box.add(Box.createVerticalStrut(30));
		box.add(jbt3);
		box.add(textArea3);
		box.add(Box.createVerticalStrut(20));
		add(box);

	}

	public static void createAndShowGui() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Final Input Sheet");
		SwingUtilities.updateComponentTreeUI(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 280);
		frame.setLocationRelativeTo(null);
		frame.add(new Browse());
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});

		jbt1.addActionListener(new Action1());
		jbt2.addActionListener(new Action2());
		jbt3.addActionListener(new Action3());
	}

	static class Action1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Dimension screenSize;
			JDialog.setDefaultLookAndFeelDecorated(true);
			String owner = System.getProperty("user.name");
			JFileChooser fileChooser = new JFileChooser("C:\\Users\\" + owner + "\\Desktop");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "csv");
			fileChooser.setFileFilter(filter);
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			fileChooser.setPreferredSize(new java.awt.Dimension(screenSize.width / 2, screenSize.height / 2));
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					File inputloc = fileChooser.getSelectedFile();
					String csvFile = inputloc.toString();
					String line;
					csvreader = new BufferedReader(new FileReader(csvFile));

					while ((line = csvreader.readLine()) != null) {
						inputcsv.add(line);
					}
					csvreader.close();
					textArea.setText("I/P Uploaded");

				} catch (Exception e1) {
					System.out.println("Error Reading New Input Sheet");
				}
			}
			

		}
	}

	static class Action2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Dimension screenSize;
			JDialog.setDefaultLookAndFeelDecorated(true);
			String owner = System.getProperty("user.name");
			JFileChooser fileChooser = new JFileChooser("C:\\Users\\" + owner + "\\Desktop");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "csv");
			fileChooser.setFileFilter(filter);
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			fileChooser.setPreferredSize(new java.awt.Dimension(screenSize.width / 2, screenSize.height / 2));
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					File inputloc = fileChooser.getSelectedFile();
					String csvFile = inputloc.toString();
					String line;
					csvreader = new BufferedReader(new FileReader(csvFile));
					while ((line = csvreader.readLine()) != null) {
						outputcsv.add(line);
					}
					csvreader.close();
					textArea2.setText("O/P Uploaded");

				} catch (Exception e2) {
					System.out.println("Error Reading Output Sheet");
				}
			}
		}

	}

	static class Action3 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			jbt3.setEnabled(false);
			// Remove the duplicates based on column
			for (int i = 0; i < inputcsv.size(); i++) {
				String[] data = inputcsv.get(i).split(csvSplitBy);
				String col1 = data[0].trim();
				String col2 = data[1].trim();
				String col3 = data[2].trim();
				String col4 = data[3].trim();
				String col5 = data[4].trim();

				boolean uniquecolumn = true;
				for (int j = 0; j < outputcsv.size(); j++) {
					String[] tmp = outputcsv.get(j).split(csvSplitBy);

					if (col1.contentEquals(tmp[0].trim()) && col2.contentEquals(tmp[1].trim())
							&& col3.contentEquals(tmp[2].trim()) && col4.contentEquals(tmp[3].trim())
							&& col5.contentEquals(tmp[4].trim())) {
						uniquecolumn = false;
						break;
					}

				}
				if (uniquecolumn) {
					comparecsv.add(inputcsv.get(i));
				}
			}

			// Remove duplicates from FinaList
			for (int j = 0; j < comparecsv.size(); j++) {
				String[] data = comparecsv.get(j).split(csvSplitBy);
				String col1 = data[0].trim();
				String col2 = data[1].trim();
				String col3 = data[2].trim();
				String col4 = data[3].trim();
				String col5 = data[4].trim();

				boolean uniquecolumn1 = true;
				for (int k = 0; k < comparecsv.size(); k++) {
					String[] tmp = comparecsv.get(k).split(csvSplitBy);
					if (j != k) {
						if (col1.equals(tmp[0].trim()) && col2.equals(tmp[1].trim()) && col3.equals(tmp[2].trim())
								&& col4.equals(tmp[3].trim()) && col5.equals(tmp[4].trim())) {
							uniquecolumn1 = false;
							break;
						}
					}
				}
				if (uniquecolumn1) {
					finalunique.add(comparecsv.get(j));
				}
			}

			if (finalunique.isEmpty()) {
				textArea3.setText("No Data Found, Please try again");
			} else {
				try {

					FileWriter outputFile = new FileWriter("BalancingInput.csv");
					 outputFile.append("ACCT_ID");
					 outputFile.append(csvSplitBy);
					 outputFile.append("KNOWN_ISSUE_QUAL");
					 outputFile.append(csvSplitBy);
					 outputFile.append("BILLING_ENTITY");
					 outputFile.append(csvSplitBy);
					 outputFile.append("CLIENT");
					 outputFile.append(csvSplitBy);
					 outputFile.append("DATE");
					 outputFile.append(csvSplitBy);
					 outputFile.append("BILLING_ENTITY");

					PrintWriter out = new PrintWriter(outputFile);
					for (int i = 0; i < finalunique.size(); i++) {
						String[] data = finalunique.get(i).split(csvSplitBy);
						String col1 = data[0].trim(); // Client
						String col2 = data[1].trim(); // KIQ
						String col3 = data[2].trim(); // Comments
						String col4 = data[3].trim(); // BE
						String col5 = data[4].trim(); // Account ID
						String col6 = data[5].trim(); // Date

						out.write('\n');
						out.write(col5);
						out.write(',');
						out.write(col2);
						out.write(',');
						out.write(col3);
						out.write(',');
						out.write(col1);
						out.write(',');
						out.write(col6);
						out.write(',');
						out.write(col4);
						out.write(',');
					

						// out.write(String.valueOf(finalunique.get(i)));
						// out.write(",");

					}

					out.close();
					textArea3.setText("Your Final I/P File Created");

				}

				catch (Exception e3) {
					System.out.println("Error while creating the file");
					textArea3.setText("Please try again");

				}

			}
			jbt3.setEnabled(true);
		}

	}

}
