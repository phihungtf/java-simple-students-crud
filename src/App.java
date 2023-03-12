import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

import hocsinh.*;

class HocSinhTableModel extends AbstractTableModel {
	private DanhSachHocSinh ds;
	private String[] columns;
	public boolean isUpdated = false;

	public HocSinhTableModel(DanhSachHocSinh ds) {
		super();
		this.ds = ds;
		columns = new String[] {"STT", "Ma HS", "Ten HS", "Diem", "Hinh anh", "Dia chi", "Ghi chu"};
	}

	// Number of column of your table
	public int getColumnCount() {
		return columns.length;
	}

	// Number of row of your table
	public int getRowCount() {
		return ds.size();
	}

	// The object to render in a cell
	public Object getValueAt(int row, int col) {
		if (col == 0) return row + 1; // STT
		return ds.getValueAt(row, col - 1);
	}

	/**
	 * This method returns true if the cell at the given row and column is
	 * editable. Otherwise, it returns false.
	 */
	public boolean isCellEditable(int row, int col) {
		if (col == 0) return false;
		return true;
	}

	/**
	 * This method is called whenever a value in the table is changed.
	 * 
	 * @param value the new value
	 * @param row the row of the cell that was changed
	 * @param col the column of the cell that was changed
	 */
	public void setValueAt(Object value, int row, int col) {
		ds.setValueAt(value, row, col - 1);
        fireTableCellUpdated(row, col);
		this.isUpdated = true;
    }

	// Optional, the name of your column
	public String getColumnName(int col) {
		return columns[col];
	}
}

public class App extends JPanel implements ActionListener {
	public static boolean IS_DEBUG = false;
	public static String COMMA_DELIMITER = ",";

	protected DanhSachHocSinh dshs = new DanhSachHocSinh();
	protected JButton openFileButton, addButton, removeButton, saveButton, saveAsButton, exitButton;
	protected JTextField fileAddrInput;
	protected JFileChooser fc;
	protected File file = null;
	protected HocSinhTableModel model;
	protected JTable jt;
	// protected boolean isUpdated = false;

	public App() {
		setBorder(new EmptyBorder(8, 8, 8, 8));
		setLayout(new BorderLayout());
		JPanel openFilePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		openFilePanel.add(new JLabel("File: "), gbc);
		gbc.gridx++;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		fileAddrInput = new JTextField(20);
		fileAddrInput.setEditable(false);
		openFilePanel.add(fileAddrInput, gbc);
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		openFileButton = new JButton("Open");
		openFileButton.setActionCommand("Open");
		openFilePanel.add(openFileButton, gbc);

		model = new HocSinhTableModel(dshs);
		jt = new JTable(model);
		jt.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jt.setCellSelectionEnabled(true);
		jt.getTableHeader().setReorderingAllowed(false);
		jt.setAutoCreateRowSorter(true);

		int[] preferredWidth = {50, 75, 150, 50, 100, 100, 100};
		for (int i = 0; i < 7; i++) {
			TableColumn column = jt.getColumnModel().getColumn(i);
			column.setPreferredWidth(preferredWidth[i]);
		}
		JScrollPane tablePanel = new JScrollPane(jt);

		JPanel buttonsPane = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		addButton = new JButton("Add");
		addButton.setActionCommand("Add");
		buttonsPane.add(addButton, gbc);
		gbc.gridx++;
		gbc.weightx = 1;
		removeButton = new JButton("Remove");
		removeButton.setActionCommand("Remove");
		buttonsPane.add(removeButton, gbc);
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.EAST;

		saveButton = new JButton("Save");
		saveButton.setActionCommand("Save");
		buttonsPane.add(saveButton, gbc);
		gbc.gridx++;
		saveAsButton = new JButton("Save as");
		saveAsButton.setActionCommand("SaveAs");
		buttonsPane.add(saveAsButton, gbc);
		gbc.gridx++;
		exitButton = new JButton("Exit");
		exitButton.setActionCommand("Exit");
		buttonsPane.add(exitButton, gbc);

		openFileButton.addActionListener(this);
		addButton.addActionListener(this);
		removeButton.addActionListener(this);
		saveButton.addActionListener(this);
		saveAsButton.addActionListener(this);
		exitButton.addActionListener(this);

		fc = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(workingDirectory);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Binary File", "bin"));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
		fc.setAcceptAllFileFilterUsed(false);

		
		add(openFilePanel, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
		add(buttonsPane, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "Open":
				openFile();
				break;
			case "Add":
				addRow();
				break;
			case "Remove":
				removeRow();
				break;
			case "Save":
				saveFile();
				break;
			case "SaveAs":
				saveAsFile();
				break;
			case "Exit":
				if (isUpdated()) {
					int result = JOptionPane.showConfirmDialog(App.this, "Do you want to save before exit?", "Save before exit", JOptionPane.YES_NO_CANCEL_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						if (IS_DEBUG) System.out.println("Save file");
						saveFile();
					} else if (result == JOptionPane.CANCEL_OPTION) {
						if (IS_DEBUG) System.out.println("Cancel exit");
						break;
					} else {
						if (IS_DEBUG) System.out.println("Exit without saving");
					}
				}
				System.exit(0);
				break;
			default:
				if (IS_DEBUG) System.out.println("Action not implemented yet");
				break;
		}
	}

	public void openFile() {
		if (isUpdated()) {
			int result = JOptionPane.showConfirmDialog(App.this, "Do you want to save before open?", "Save before open", JOptionPane.YES_NO_CANCEL_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				if (IS_DEBUG) System.out.println("Save file");
				saveFile();
			} else if (result == JOptionPane.CANCEL_OPTION) {
				if (IS_DEBUG) System.out.println("Cancel open");
				return;
			} else {
				if (IS_DEBUG) System.out.println("Open without saving");
			}
		}

		int returnVal = this.fc.showOpenDialog(App.this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = this.fc.getSelectedFile();
			if (IS_DEBUG) System.out.println("Opening: " + this.file.getName() + ".");
			fileAddrInput.setText(this.file.getAbsolutePath());
			if (this.fc.getFileFilter().getDescription().equals("CSV File")) {
				if (IS_DEBUG) System.out.println("CSV file");
				readCSVFile(file);
				if (IS_DEBUG) System.out.println(dshs);
				model.fireTableDataChanged();
			} else {
				if (IS_DEBUG) System.out.println("Binary file");
				readFile(file);
				if (IS_DEBUG) System.out.println(dshs);
				model.fireTableDataChanged();
			}
			this.setUpdated(false);
		} else {
			if (IS_DEBUG) System.out.println("Open command cancelled by user.");
		}
	}

	public void addRow() {
		dshs.add();
		model.fireTableRowsInserted(dshs.size() - 1, dshs.size() - 1);
		// isUpdated = true;
		this.setUpdated(true);
	}

	public void removeRow() {
		int selectedRow = jt.getSelectedRow();
		if (selectedRow != -1) {
			dshs.remove(selectedRow);
			model.fireTableRowsDeleted(selectedRow, selectedRow);
		}
		// isUpdated = true;
		this.setUpdated(true);
	}

	public void saveFile() {
		if (!isUpdated()) {
			if (IS_DEBUG) System.out.println("Data is not updated");
			return;
		}
		if (this.file == null) {
			saveAsFile();
		} else {
			if (this.file.toString().endsWith(".csv")) {
				if (IS_DEBUG) System.out.println("CSV file");
				writeCSVFile(file);
			} else {
				if (IS_DEBUG) System.out.println("Binary file");
				writeFile(file);
			}
			// isUpdated = false;
			this.setUpdated(false);
		}
	}

	public void saveAsFile() {
		int returnVal = this.fc.showSaveDialog(App.this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = this.fc.getSelectedFile();
			if (IS_DEBUG) System.out.println("Saving: " + this.file.getName() + ".");
			if (fc.getFileFilter().getDescription().equals("CSV File")) {
				if (IS_DEBUG) System.out.println("CSV file");
				// add .csv extension if not exist
				if (!file.getName().endsWith(".csv")) {
					file = new File(file.getAbsolutePath() + ".csv");
				}
				writeCSVFile(file);
			} else {
				if (IS_DEBUG) System.out.println("Binary file");
				// add .bin extension if not exist
				if (!file.getName().endsWith(".bin")) {
					file = new File(file.getAbsolutePath() + ".bin");
				}
				writeFile(file);
			}
			fileAddrInput.setText(this.file.getAbsolutePath());
			// isUpdated = false;
			this.setUpdated(false);
		} else {
			if (IS_DEBUG) System.out.println("Save command cancelled by user.");
		}
	}

	public void readFile(File file) {
		try {
			FileInputStream inStream = new FileInputStream(file);
			DataInputStream input = new DataInputStream(inStream);

			try {
				while (true) {
					dshs.read(input);
				}
			} catch (Exception e) {
				throw e;
			} finally {
				inStream.close();
			}
		} catch (EOFException e) {
			if (IS_DEBUG) System.out.println("End of file");
		} catch (Exception e) {
			if (IS_DEBUG) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			System.exit(0);
		} finally {
			if (IS_DEBUG) System.out.println("Read file done");
		}
	}

	public void readCSVFile(File file) {
		dshs.clear();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER, -1);
				dshs.addFromStrings(values);
			}
			br.close();
		} catch (Exception e) {
			if (IS_DEBUG) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			System.exit(0);
		} finally {
			if (IS_DEBUG) System.out.println("Read file done");
		}
	}

	public void writeFile(File file) {
		try {
			FileOutputStream outStream = new FileOutputStream(file);
			DataOutputStream output = new DataOutputStream(outStream);
			try {
				dshs.write(output);
			} catch (Exception e) {
				throw e;
			} finally {
				outStream.close();
			}
		} catch (Exception e) {
			if (IS_DEBUG) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			System.exit(0);
		} finally {
			if (IS_DEBUG) System.out.println("Write file done");
		}
	}

	public void writeCSVFile(File file) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
			for (int i = 0; i < dshs.size(); i++) {
				pw.println(dshs.getCSVString(i, COMMA_DELIMITER));
			}
			pw.close();
		} catch (Exception e) {
			if (IS_DEBUG) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			System.exit(0);
		} finally {
			if (IS_DEBUG) System.out.println("Write file done");
		}
	}

	public boolean isUpdated() {
		return this.model.isUpdated;
	}

	public void setUpdated(boolean isUpdated) {
		this.model.isUpdated = isUpdated;
	}
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
		JFrame frame = new JFrame("Quan Ly Hoc Sinh");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new BorderLayout());
		App app = new App();
		frame.add(app);
		
		//Display the window.
		frame.pack();
		// frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (app.isUpdated()) {
					int result = JOptionPane.showConfirmDialog(app, "Do you want to save before exit?", "Save before exit", JOptionPane.YES_NO_CANCEL_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						if (IS_DEBUG) System.out.println("Save file");
						app.saveFile();
					} else if (result == JOptionPane.CANCEL_OPTION) {
						if (IS_DEBUG) System.out.println("Cancel exit");
						return;
					} else {
						if (IS_DEBUG) System.out.println("Exit without saving");
					}
				}
				System.exit(0);
			}
		});
    }

    public static void main(String[] args) {
        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
}