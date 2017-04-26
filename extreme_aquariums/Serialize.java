package extreme_aquariums;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Serializes and save the customer's tank to a file or
 * deserializes and opens a customer's existing tank from a file
 * 
 * @author Jared Wyce
 * @author Jared Hamlin
 * @author Josh Ginchereau
 * @author Will Hou
 * @see CustomerTank
 * @version 4.25.2017
 *
 */

public class Serialize {

	public static CustomerTank open() {
		String file = null;
		CustomerTank tank = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open a Tank File");
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter("aqua file", "aqua"));
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile().getAbsolutePath();
			try(ObjectInput input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
				//deserialize the file
				tank = (CustomerTank) input.readObject();
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Error: Class Not Found!", "An Error Has Occurred", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Error: Problem Opening! (IOException)", "An Error Has Occurred", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			} 
		}
		return tank; 
	}

	/**
	 * @param tank is the customer's tank
	 */
	public static void save(CustomerTank tank) {
		JFileChooser saveTank = new JFileChooser();
		saveTank.setDialogTitle("Save a Tank");
		saveTank.setDialogType(JFileChooser.SAVE_DIALOG);
		saveTank.setFileSelectionMode(JFileChooser.FILES_ONLY);
		saveTank.setFileFilter(new FileNameExtensionFilter("aqua file", "aqua"));
		
		int saveOption = saveTank.showSaveDialog(null);
		if(saveOption == JFileChooser.APPROVE_OPTION){
			String savePath = saveTank.getSelectedFile().getAbsolutePath();
			if (!savePath.endsWith(".aqua"))
				savePath += ".aqua";

			try (ObjectOutput output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(savePath))))) {
				output.writeObject(tank);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Error: IOException on save!", "An Error Has Occurred", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
	}
}
