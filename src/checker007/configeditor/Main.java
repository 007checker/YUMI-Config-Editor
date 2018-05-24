package checker007.configeditor;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Main extends JFrame {

	private JPanel contentPane;
	private ArrayList<File> sortedPaths = new ArrayList<>();
	File actualPath = new File("");
	File actualFile = new File("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setResizable(false);
		setTitle("YUMI Config Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea(0, 40);
		textArea.setEnabled(false);
		//textArea.setBounds(125, 85, 664, 447);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(125, 85, 664, 447);
		//contentPane.add(textArea);
		contentPane.add(scroll);

		JLabel lblDrive = new JLabel("Drive:");
		lblDrive.setBounds(10, 11, 46, 14);
		contentPane.add(lblDrive);

		JLabel lblIsDriveYUMI = new JLabel("No Drive selected yet");
		lblIsDriveYUMI.setBounds(436, 11, 180, 14);
		contentPane.add(lblIsDriveYUMI);
		
		JButton btnSave = new JButton("Save");
		btnSave.setEnabled(false);
		btnSave.setBounds(10, 509, 105, 23);
		contentPane.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setEnabled(false);
		btnCancel.setBounds(10, 474, 105, 23);
		contentPane.add(btnCancel);

		JRadioButton rdbtnAntivirus = new JRadioButton("Antivirus");
		rdbtnAntivirus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadConfig(textArea, actualPath, btnSave, btnCancel, "multiboot\\menu\\antivirus.cfg");
			}
		});
		rdbtnAntivirus.setEnabled(false);
		rdbtnAntivirus.setBounds(10, 114, 109, 23);
		contentPane.add(rdbtnAntivirus);

		JRadioButton rdbtnLinux = new JRadioButton("Linux");
		rdbtnLinux.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadConfig(textArea, actualPath, btnSave, btnCancel, "multiboot\\menu\\linux.cfg");
			}
		});
		rdbtnLinux.setEnabled(false);
		rdbtnLinux.setBounds(10, 140, 109, 23);
		contentPane.add(rdbtnLinux);

		JRadioButton rdbtnPocketEdition = new JRadioButton("Pocket Edition");
		rdbtnPocketEdition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadConfig(textArea, actualPath, btnSave, btnCancel, "multiboot\\menu\\pe.lst");
			}
		});
		rdbtnPocketEdition.setEnabled(false);
		rdbtnPocketEdition.setBounds(10, 166, 109, 23);
		contentPane.add(rdbtnPocketEdition);

		JRadioButton rdbtnSystem = new JRadioButton("System");
		rdbtnSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadConfig(textArea, actualPath, btnSave, btnCancel, "multiboot\\menu\\system.cfg");
			}
		});
		rdbtnSystem.setEnabled(false);
		rdbtnSystem.setBounds(10, 192, 109, 23);
		contentPane.add(rdbtnSystem);

		JRadioButton rdbtnWindows = new JRadioButton("Windows");
		rdbtnWindows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadConfig(textArea, actualPath, btnSave, btnCancel, "multiboot\\menu\\win.lst");
			}
		});
		rdbtnWindows.setEnabled(false);
		rdbtnWindows.setBounds(10, 218, 109, 23);
		contentPane.add(rdbtnWindows);

		JRadioButton rdbtnSyslinux = new JRadioButton("SysLinux");
		rdbtnSyslinux.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadConfig(textArea, actualPath, btnSave, btnCancel, "multiboot\\syslinux.cfg");
			}
		});
		rdbtnSyslinux.setSelected(true);
		rdbtnSyslinux.setEnabled(false);
		rdbtnSyslinux.setBounds(10, 244, 109, 23);
		contentPane.add(rdbtnSyslinux);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnAntivirus);
		group.add(rdbtnLinux);
		group.add(rdbtnPocketEdition);
		group.add(rdbtnSystem);
		group.add(rdbtnWindows);
		group.add(rdbtnSyslinux);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getActionCommand().equals("comboBoxChanged")) {
					group.clearSelection();
					btnCancel.setEnabled(false);
					btnSave.setEnabled(false);
					if (comboBox.getSelectedIndex() > 0) {
						actualPath = sortedPaths.get(comboBox.getSelectedIndex() - 1);
						if (isYUMIDrive(actualPath)) {
							lblIsDriveYUMI.setText("Drive seems to be a YUMI Drive!");
							lblIsDriveYUMI.setForeground(new Color(0, 147, 0));
							
							textArea.setEnabled(true);
							
							rdbtnAntivirus.setEnabled(false);
							rdbtnLinux.setEnabled(false);
							rdbtnPocketEdition.setEnabled(false);
							rdbtnSystem.setEnabled(false);
							rdbtnWindows.setEnabled(false);
							rdbtnSyslinux.setEnabled(false);

							if (configExists(sortedPaths.get(comboBox.getSelectedIndex() - 1),
									"multiboot\\menu\\antivirus.cfg"))
								rdbtnAntivirus.setEnabled(true);
							if (configExists(sortedPaths.get(comboBox.getSelectedIndex() - 1),
									"multiboot\\menu\\linux.cfg"))
								rdbtnLinux.setEnabled(true);
							if (configExists(sortedPaths.get(comboBox.getSelectedIndex() - 1),
									"multiboot\\menu\\pe.lst"))
								rdbtnPocketEdition.setEnabled(true);
							if (configExists(sortedPaths.get(comboBox.getSelectedIndex() - 1),
									"multiboot\\menu\\system.cfg"))
								rdbtnSystem.setEnabled(true);
							if (configExists(sortedPaths.get(comboBox.getSelectedIndex() - 1),
									"multiboot\\menu\\win.lst"))
								rdbtnWindows.setEnabled(true);
							if (configExists(sortedPaths.get(comboBox.getSelectedIndex() - 1),
									"multiboot\\syslinux.cfg"))
								rdbtnSyslinux.setEnabled(true);
						} else {
							lblIsDriveYUMI.setText("Drive is not created with YUMI!");
							lblIsDriveYUMI.setForeground(new Color(187, 0, 0));

							textArea.setEnabled(false);
							
							rdbtnAntivirus.setEnabled(false);
							rdbtnLinux.setEnabled(false);
							rdbtnPocketEdition.setEnabled(false);
							rdbtnSystem.setEnabled(false);
							rdbtnWindows.setEnabled(false);
							rdbtnSyslinux.setEnabled(false);
						}
					} else {
						lblIsDriveYUMI.setText("No Drive selected");
						lblIsDriveYUMI.setForeground(new Color(0, 0, 0));

						textArea.setEnabled(false);
						
						rdbtnAntivirus.setEnabled(false);
						rdbtnLinux.setEnabled(false);
						rdbtnPocketEdition.setEnabled(false);
						rdbtnSystem.setEnabled(false);
						rdbtnWindows.setEnabled(false);
						rdbtnSyslinux.setEnabled(false);
					}
				}
			}
		});
		comboBox.setBounds(66, 8, 360, 20);
		contentPane.add(comboBox);

		comboBox.addItem("Please select a Drive!");

		getDrives(comboBox);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				writeFile(textArea, actualFile);
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFileAgain(textArea, actualFile);
			}
		});
	}

	public void getDrives(JComboBox<String> cb) {
		File[] paths;
		FileSystemView fsv = FileSystemView.getFileSystemView();

		ArrayList<String> highestChance = new ArrayList<>();

		// returns pathnames for files and directory
		paths = File.listRoots();

		// for each pathname in pathname array
		for (File path : paths) {
			if (fsv.getSystemDisplayName(path).toUpperCase().contains("MULTIBOOT")) {
				cb.addItem(path.toString() + " " + fsv.getSystemDisplayName(path));
				highestChance.add(path.toString());
				sortedPaths.add(path);
			}
		}

		for (File path : paths) {
			if ((fsv.getSystemTypeDescription(path).contains("USB")
					|| fsv.getSystemTypeDescription(path).contains("CD"))
					&& !fsv.getSystemDisplayName(path).equalsIgnoreCase("")
					&& !highestChance.contains(path.toString())) {
				cb.addItem(path.toString() + " " + fsv.getSystemDisplayName(path));
				sortedPaths.add(path);
			}
		}

	}

	public boolean isYUMIDrive(File f) {
		File g = new File(f.toString().concat("multiboot\\menu"));
		return (g.exists()) ? true : false;
	}

	public boolean configExists(File drive, String pathToFile) {
		File f = new File(drive.toString().concat(pathToFile));
		return (f.exists()) ? true : false;
	}
	
	public void loadConfig(JTextArea ta, File drive, JButton save, JButton cancel, String pathToFile) {
		ta.setText("");
		save.setEnabled(true);
		cancel.setEnabled(true);
		File f = new File(drive.toString().concat(pathToFile));
		actualFile = f;
		String line;
		FileReader fr = null;
		try {
			fr = new FileReader(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    BufferedReader reader = new BufferedReader(fr);
		try {
			while ((line = reader.readLine()) != null)
			{
			    if (!line.startsWith(">"))
			    {
			        ta.append(line + "\n");
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ta.setCaretPosition(0);
	}
	
	public void writeFile(JTextArea ta, File f) {
		try (BufferedWriter fileOut = new BufferedWriter(new FileWriter(f))) {
		    try {
				ta.write(fileOut);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void loadFileAgain(JTextArea ta, File pathToFile) {
		ta.setText("");
		actualFile = pathToFile;
		String line;
		FileReader fr = null;
		try {
			fr = new FileReader(pathToFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    BufferedReader reader = new BufferedReader(fr);
		try {
			while ((line = reader.readLine()) != null)
			{
			    if (!line.startsWith(">"))
			    {
			        ta.append(line + "\n");
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ta.setCaretPosition(0);
	}
}
