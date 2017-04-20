package extreme_aquariums;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GenerateTankDescription {

	public static void printFile(CustomerTank tank) {
		JFileChooser saveFile = new JFileChooser();
		saveFile.setDialogTitle("Save the Tank Description");
		saveFile.setDialogType(JFileChooser.SAVE_DIALOG);
		saveFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
		saveFile.setFileFilter(new FileNameExtensionFilter("txt file", "txt"));
		int saveOption = saveFile.showSaveDialog(null);
		
		if(saveOption == JFileChooser.APPROVE_OPTION) {
			String savePath = saveFile.getSelectedFile().getAbsolutePath();
			if (!savePath.endsWith(".txt"))
				savePath += ".txt";
				
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(savePath))) { 
				ArrayList<Fish> custFish = tank.getFish();
				writer.write("****Extreme Aquarium INC Receipt Printout***");
				writer.newLine();
				writer.write("Thank you for your business today. Your tank specifications are as follows: ");
				writer.newLine();
				writer.write("Dimensions: ");
				writer.write(tank.getHeight() + " x " + tank.getWidth());
				writer.newLine();
				writer.write("****************************");
				writer.newLine(); 
				
				writer.write("Fish: ");
				while (custFish.size() != 0) {
					String fishName = custFish.get(0).getName();
					int occurrences = 0;
					
					Iterator<Fish> itr = custFish.iterator();
					while(itr.hasNext()) {
						if(itr.next().getName().equals(fishName)) {
							occurrences++;
							itr.remove();
						}
					}
					
					writer.newLine();
					writer.write(fishName + " x" + occurrences);
				}
				writer.newLine();
				
				writer.write("****************************");
				writer.newLine();
				writer.write("System levels: ");
				writer.newLine();
				writer.write("CO2: " + tank.getCarbonDioxideLevel() + "%");
				writer.newLine();
				writer.write("O2: " + tank.getOxygenLevel() + "%");
				writer.newLine();
				writer.write("PH: " + String.format("%.1f", tank.getPHLevel()));
				writer.newLine();
				writer.write("Water Hardness: " + tank.getWaterHardess() + "mg/L");
				writer.newLine();
				writer.write("****************************");
				writer.newLine();
				writer.write("We hope you enjoyed our application! Have a great day.");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error: IOException on save!", "An Error Has Occurred", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
}
