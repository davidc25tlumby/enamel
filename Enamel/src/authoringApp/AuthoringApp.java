package authoringApp;

//edited by QASIM Ahmed
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.apache.commons.io.FilenameUtils;

import enamel.ScenarioParser;
import enamel.ToyAuthoring;

public class AuthoringApp extends JFrame {

	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu runMenu = new JMenu("Run");
	private JMenuItem newFile, loadFile, saveFile, saveAsFile, exit, runFile, runSelectFile;
	private JFileChooser fileChooser = new JFileChooser();
	File currentFile;

	public static void main(String[] args) {
		AuthoringApp gui = new AuthoringApp();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		gui.setSize(960, 540);
		gui.setTitle("Authoring App");
	}

	public AuthoringApp() {
		drawMenuBar();
		addActionListeners();
		setAccessible();
	}

	// Sets accessibility features of objects.
	private void setAccessible() {
		// TODO Auto-generated method stub
		setAccessible(fileMenu, "File", "drop down menu");
		setAccessible(loadFile, "Load", "a scenario file");
	}

	// Set name and description of a JMenuItem object.
	private void setAccessible(JMenuItem o, String s, String s2) {
		o.getAccessibleContext().setAccessibleName(s);
		o.getAccessibleContext().setAccessibleDescription(s2);
	}

	// Set name and description of a JMenu object.
	private void setAccessible(JMenu o, String s, String s2) {
		o.getAccessibleContext().setAccessibleName(s);
		o.getAccessibleContext().setAccessibleDescription(s2);
	}

	private void addActionListeners() {
		loadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadFileClicked();
			}
		});
		runSelectFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				runSelectFileClicked();
			}

		});
	}

	protected void runSelectFileClicked() { 	
		File f = new File("FactoryScenarios/");
		f = openFileChooser(f, "txt");
		if (f != null) {
			currentFile = f;
			ToyAuthoring ta = new ToyAuthoring(f.getAbsolutePath());
			ta.start();
		}
	}

	protected void loadFileClicked() {
		File f = new File("FactoryScenarios/");
		f = openFileChooser(f, "txt");
		if (f != null) {
			currentFile = f;
			this.setTitle("Authoring App - " + currentFile.getName());
		}
	}
	
	//Opens a file chooser @ the specified directory and expects the file selected
	//to be of the extension 'ext'. Returns the selected file. If extension is of
	//wrong type, return null.
	public File openFileChooser(File currentDir, String ext) {
		Component parent = null;    	

		fileChooser.setCurrentDirectory(currentDir);
		int returnVal = fileChooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) { 
			   String selectedExt = FilenameUtils.getExtension(fileChooser.getSelectedFile().getName());
               if (!ext.equals(selectedExt)) {
            	   final JPanel panel = new JPanel();
            	   JOptionPane.showMessageDialog(panel, "Could not open file, Wrong file type", "Error", JOptionPane.ERROR_MESSAGE);
            	   return null;
            }
               else
               {
            	   return fileChooser.getSelectedFile();
               }
		}
		return null;
	}

	private void drawMenuBar() {
		setJMenuBar(menuBar);
		newFile = fileMenu.add("New");
		loadFile = fileMenu.add("Open");
		fileMenu.addSeparator();
		saveFile = fileMenu.add("Save");
		saveAsFile = fileMenu.add("Save as..");
		fileMenu.addSeparator();
		exit = fileMenu.add("Exit");
		runFile = runMenu.add("Run");
		runSelectFile = runMenu.add("Run..");

		menuBar.add(fileMenu);
		menuBar.add(runMenu);
	}

}
