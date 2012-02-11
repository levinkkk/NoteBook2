package dialog;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class FindDialog extends JDialog {
	private JTextArea text;
	private JTextField findText;
	private JButton findNext = new JButton("查找下一个");
	private JButton cancel = new JButton("取消");
	private JCheckBox upperLowerCase = new JCheckBox("区分大小写");
	private JRadioButton upButton = new JRadioButton("向上");
	private JRadioButton downButton = new JRadioButton("向下");
	
	private void makeControl(final Component comp, final GridBagLayout gridbag, final GridBagConstraints c) {
		gridbag.setConstraints(comp, c);
		add(comp);
	}
	
	public FindDialog(JFrame frame, JTextArea textArea) {
		super(frame, "查找");
		text = textArea;
		findText = new JTextField(textArea.getSelectedText() == null ? "" : textArea.getSelectedText());
		
		findText.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				
			}
			
			public void insertUpdate(DocumentEvent e) {
				findNext.setEnabled(true);
			}

			public void removeUpdate(DocumentEvent e) {
				if (findText.getText().isEmpty()) {
					findNext.setEnabled(false);
				}
			}
		});
		
		findNext.setEnabled(!findText.getText().isEmpty());
		findNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				find();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		GridBagLayout gridbag = new GridBagLayout();
		setLayout(gridbag);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		c.gridwidth = 3;
		makeControl(new JLabel("查找内容："), gridbag, c);
		findText.setColumns(20);
		makeControl(findText, gridbag, c);
		c.gridwidth = GridBagConstraints.REMAINDER; //换行
		makeControl(findNext, gridbag, c);
		
		c.gridwidth = 3;
		makeControl(upperLowerCase, gridbag, c);
		JPanel upDownPanel = new JPanel();
		upDownPanel.add(upButton);
		upDownPanel.add(downButton);
		ButtonGroup upDownGroup = new ButtonGroup();
		upDownGroup.add(upButton);
		upDownGroup.add(downButton);
		downButton.setSelected(true);
		makeControl(upDownPanel, gridbag, c);
		c.gridwidth = GridBagConstraints.REMAINDER; //换行
		makeControl(cancel, gridbag, c);
		
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	public void find() {
		String textString = text.getText();
		String findString = findText.getText();
		
		int pos;
		if (downButton.isSelected()) {
			pos = text.getCaretPosition();
			if (upperLowerCase.isSelected()) {
				pos = textString.indexOf(findString, pos);
			}
			else {
				pos = textString.toLowerCase().indexOf(findString.toLowerCase(), pos);
			}
		} else {
			pos = (text.getSelectedText() == null ? text.getCaretPosition() : text.getSelectionStart()) - 1;
			if (upperLowerCase.isSelected()) {
				pos = textString.lastIndexOf(findString, pos);
			}
			else {
				pos = textString.toLowerCase().lastIndexOf(findString.toLowerCase(), pos);
			}
		}
		
		if (pos == -1) {
			JOptionPane.showMessageDialog(super.getRootPane(), "没有找到 \"" + findString + "\"");
		} else {
			text.select(pos, pos + findString.length());
		}
	}
}