import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class SwingProgram extends JFrame implements ActionListener{
	JFrame f;
	JComboBox barSymDrop;
	JTextField totalText;
	JTextField barText;
	JLabel finBar;
	JLabel finData;
	JLabel checkDigLab;
	JLabel checkDig;
	
	public SwingProgram() {
		//barcode symbology label
		JLabel barSymLabel = new JLabel("Barcode Symbology: ");
		barSymLabel.setBounds(30,20,150,50);
		
		//barcode symbology dropdown
		String[] barStrings = {"Code 39 Mod 10", "Code 39 Mod 43"};
		barSymDrop = new JComboBox(barStrings);
		barSymDrop.setSelectedIndex(0);
		barSymDrop.setBounds(200,35,150,20);
		
		//total number of digits label
		JLabel totalNumLabel = new JLabel("Total length of desired barcode: ");
		totalNumLabel.setBounds(30,60,200,20);
		
		//total length input
		totalText = new JTextField(10);
		totalText.setBounds(250,60,40,20);
		
		//barcode to calculate label
		JLabel barField = new JLabel("Barcode without ending single check digit: ");
		barField.setBounds(30,85,300,20);
		
		//barcode to calculate field
		barText = new JTextField(20);
		barText.setBounds(300,85,150,20);
		
		//calculate the barcode button
		JButton calcButt = new JButton("Calculate Barcode");
		calcButt.setBounds(30,110,300,20);
		calcButt.addActionListener(this);
		
		//*****invisible section until button is clicked to calculate*****
		checkDigLab = new JLabel("Check digit: ");
		checkDigLab.setBounds(30,135,90,30);
		checkDigLab.setVisible(false);
		add(checkDigLab);
		
		checkDig = new JLabel("4");
		checkDig.setBounds(150,135,90,30);
		checkDig.setVisible(false);
		add(checkDig);
		
		finBar = new JLabel("Final Barcode: ");
		finBar.setBounds(30,160,90,30);
		finBar.setVisible(false);
		add(finBar);
		
		finData = new JLabel("1234567890");
		finData.setBounds(150,160,160,30);
		finData.setVisible(false);
		add(finData);
		//*****end of invisible section****
		
		//add to the window
		add(barSymLabel);
		add(barSymDrop);
//		add(totalNumLabel);
//		add(totalText);
		add(barField);
		add(barText);
		add(calcButt);
		
		setSize(700,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		new SwingProgram();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//get the information from the length, the barcode, and the code symbology fields
		String strLength = totalText.getText();
		int length = 10;
		
		String barcode = barText.getText();
		String symb = (String)barSymDrop.getSelectedItem();
		
		//error check for populated fields
		if(strLength.length() > 0 && barcode.length() > 0) {
			//do a check on the length to make sure it is acceptable to the symbology
			length = Integer.parseInt(strLength);
			if(length > 7 && length < 15) {
				//error check for whether the length matches the end resulting barcode length (barcode +1)
				if(barcode.length() + 1 == length) {
					//error check for which barcode symbology is selected
					
					int checkDigit = c39m10(barcode);
					String finalBarcode = barcode + checkDigit;
					
					checkDig.setText(Integer.toString(checkDigit));
					finData.setText(finalBarcode);
					
					checkDigLab.setVisible(true);
					checkDig.setVisible(true);
					finBar.setVisible(true);
					finData.setVisible(true);
				}
				else {
					//error check display for length of barcode not matching end resulting barcode (barcode + 1)
//					JLabel error = new JLabel("<html><center>Your entered barcode length " + strLength + " does not match ")
				}
			}
			else {
				//error check display for length of barcode
				JLabel error = new JLabel("<html><center>Your entered barcode length " + strLength + " is not valid. <br>Please enter a number from 8 to 14.");
				error.setHorizontalAlignment(SwingConstants.CENTER);
				JOptionPane.showMessageDialog(f, error);
			}
		}
		else {
			//error check display for all populated fields
			JLabel error = new JLabel("<html><center>Please fill out both fields: length of barcode and your <br>desired barcode without the check digit.\"\r\n"
					+ " If you have any <br>questions, please refer to the help button in the <br>bottom right corner.");
			error.setHorizontalAlignment(SwingConstants.CENTER);
			JOptionPane.showMessageDialog(f, error,null, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static int c39m10(String barcode) {
		int total = 0;
		
		//for the length of the barcode
		for(int i = 0; i < barcode.length(); i++) {
			if(i%2 == 1) {
				total = total + Integer.parseInt(barcode.substring(i, i+1));
			}
			else {
				int twice = 2*(Integer.parseInt(barcode.substring(i, i+1)));
				
				if(twice > 9) {
					twice = (twice % 10) + 1;
				}
				total = total + twice;
			}
		}
		
		int fin = total % 10;
		if(fin >= 1)
			fin = 10 - fin;
		
		return fin;
	}

}
