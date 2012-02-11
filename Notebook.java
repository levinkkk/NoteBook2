import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import control.UndoableText;
import dialog.FindDialog;

@SuppressWarnings("serial")
public class Notebook extends JFrame implements WindowListener, ActionListener, MenuListener, PopupMenuListener {
	private final JTabbedPane tabbedText = new JTabbedPane();
	
	private final JMenuBar mainMenuBar = new JMenuBar();
	private final JMenu fileMenu = new JMenu("文件");
	private final JMenu editMenu = new JMenu("编辑");
	private final JMenu formatMenu = new JMenu("格式");
	private final JMenu helpMenu = new JMenu("帮助");
	private final JMenuItem newMenuItem = new JMenuItem("新建");
	private final JMenuItem openMenuItem = new JMenuItem("打开");
	private final JMenuItem saveMenuItem = new JMenuItem("保存");
	private final JMenuItem closeMenuItem = new JMenuItem("关闭");
	private final JMenuItem exitMenuItem = new JMenuItem("退出");
	private final JMenuItem undoMenuItem = new JMenuItem("撤消");
	private final JMenuItem redoMenuItem = new JMenuItem("重做");
	private final JMenuItem selectAllMenuItem = new JMenuItem("全选");
	private final JMenuItem cutMenuItem = new JMenuItem("剪切");
	private final JMenuItem copyMenuItem = new JMenuItem("复制");
	private final JMenuItem pasteMenuItem = new JMenuItem("粘贴");
	private final JMenuItem findMenuItem = new JMenuItem("查找");
	private final JCheckBoxMenuItem wrapMenuCheckBox = new JCheckBoxMenuItem("自动换行");
	private final JMenuItem aboutMenuItem = new JMenuItem("关于");
	
	private final JPopupMenu mainPopMenu = new JPopupMenu();
	private final JMenuItem closePopItem = new JMenuItem("关闭");
	private final JMenuItem selectAllPopItem = new JMenuItem("全选");
	private final JMenuItem cutPopItem = new JMenuItem("剪切");
	private final JMenuItem copyPopItem = new JMenuItem("复制");
	private final JMenuItem pastePopItem = new JMenuItem("粘贴");
	
	private void createMenu() {
		fileMenu.addMenuListener(this);
		editMenu.addMenuListener(this);
		formatMenu.addMenuListener(this);
		mainPopMenu.addPopupMenuListener(this);
		
		newMenuItem.addActionListener(this);
		openMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		closeMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
		
		undoMenuItem.addActionListener(this);
		redoMenuItem.addActionListener(this);
		selectAllMenuItem.addActionListener(this);
		cutMenuItem.addActionListener(this);
		copyMenuItem.addActionListener(this);
		pasteMenuItem.addActionListener(this);
		findMenuItem.addActionListener(this);
		
		wrapMenuCheckBox.addActionListener(this);
		
		aboutMenuItem.addActionListener(this);
		
		closePopItem.addActionListener(this);
		selectAllPopItem.addActionListener(this);
		cutPopItem.addActionListener(this);
		copyPopItem.addActionListener(this);
		pastePopItem.addActionListener(this);
		
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(closeMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		
		editMenu.add(undoMenuItem);
		editMenu.add(redoMenuItem);
		editMenu.addSeparator();
		editMenu.add(selectAllMenuItem);
		editMenu.addSeparator();
		editMenu.add(cutMenuItem);
		editMenu.add(copyMenuItem);
		editMenu.add(pasteMenuItem);
		editMenu.addSeparator();
		editMenu.add(findMenuItem);
		
		formatMenu.add(wrapMenuCheckBox);
		
		helpMenu.add(aboutMenuItem);
		
		mainMenuBar.add(fileMenu);
		mainMenuBar.add(editMenu);
		mainMenuBar.add(formatMenu);
		mainMenuBar.add(helpMenu);
		setJMenuBar(mainMenuBar);
		
		mainPopMenu.add(closePopItem);
		mainPopMenu.addSeparator();
		mainPopMenu.add(selectAllPopItem);
		mainPopMenu.addSeparator();
		mainPopMenu.add(cutPopItem);
		mainPopMenu.add(copyPopItem);
		mainPopMenu.add(pastePopItem);
	}
	
	public Notebook() {
		super("记事本");
		addWindowListener(this);
		addWindowListener(this);
		add(tabbedText);
		setSize(640, 480);
		createMenu();
		createText();
		setVisible(true);
	}
	
	private UndoableText getSelectedText() {
		return ((UndoableText)((JScrollPane)tabbedText.getSelectedComponent()).getViewport().getView());
	}
	
	private void createText(String title) {
		UndoableText txt = new UndoableText();
		txt.setComponentPopupMenu(mainPopMenu);
		tabbedText.addTab(title, new JScrollPane(txt));
		tabbedText.setSelectedIndex(tabbedText.getTabCount() - 1);
	}
	
	private void createText() {
		createText("新建文本");
	}
	
	private void openText() {
		final JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				final FileInputStream in = new FileInputStream(chooser.getSelectedFile());
				createText(chooser.getSelectedFile().getName());
				getSelectedText().read(new InputStreamReader(in), null);
				in.close();
			}
			catch (final Exception exception) {
				JOptionPane.showMessageDialog(this, "读取失败", "错误", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void saveText() {
		final JFileChooser chooser = new JFileChooser();
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			if (chooser.getSelectedFile().exists()) {
				if (JOptionPane.showConfirmDialog(this, "文件存在，是否覆盖", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
					return;
				}
			}
			try {
				final FileOutputStream out = new FileOutputStream(chooser.getSelectedFile());
				getSelectedText().write(new OutputStreamWriter(out));
				out.close();
				getSelectedText().setChanged(false);
				tabbedText.setTitleAt(tabbedText.getSelectedIndex(), chooser.getSelectedFile().getName());
			}
			catch (final Exception exception) {
				JOptionPane.showMessageDialog(this, "保存失败", "错误", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void closeText() {
		if (tabbedText.getSelectedIndex() == -1) {
			return;
		}
		if (getSelectedText().isChanged() && JOptionPane.showConfirmDialog(this, "文本已修改，是否保存", "提示", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			saveText();
		}
		((JScrollPane)tabbedText.getSelectedComponent()).removeAll();
		tabbedText.remove(tabbedText.getSelectedIndex());
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newMenuItem) {
			createText();
		}
		else if (e.getSource() == openMenuItem) {
			openText();
		}
		else if (e.getSource() == saveMenuItem) {
			saveText();
		}
		else if (e.getSource() == closeMenuItem || e.getSource() == closePopItem) {
			closeText();
		}
		else if (e.getSource() == exitMenuItem) {
			this.windowClosing(null);
		}
		else if (e.getSource() == undoMenuItem) {
			getSelectedText().undo();
		}
		else if (e.getSource() == redoMenuItem) {
			getSelectedText().redo();
		}
		else if (e.getSource() == selectAllMenuItem || e.getSource() == selectAllPopItem) {
			getSelectedText().selectAll();
		}
		else if (e.getSource() == cutMenuItem || e.getSource() == cutPopItem) {
			getSelectedText().cut();
		}
		else if (e.getSource() == copyMenuItem || e.getSource() == copyPopItem) {
			getSelectedText().copy();
		}
		else if (e.getSource() == pasteMenuItem || e.getSource() == pastePopItem) {
			getSelectedText().paste();
		}
		else if (e.getSource() == findMenuItem) {
			new FindDialog(this, getSelectedText());
		}
		else if (e.getSource() == wrapMenuCheckBox) {
			getSelectedText().setLineWrap(wrapMenuCheckBox.getState());
		}
		else if (e.getSource() == aboutMenuItem) {
			JOptionPane.showMessageDialog(this, "作者：魏鹏\n湘潭大学兴湘学院05计算机", "关于", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void updateFileMenu() {
		if (tabbedText.getSelectedIndex() == -1) {
			newMenuItem.setEnabled(true);
			openMenuItem.setEnabled(true);
			saveMenuItem.setEnabled(false);
			closeMenuItem.setEnabled(false);
			exitMenuItem.setEnabled(true);
		} else {
			newMenuItem.setEnabled(true);
			openMenuItem.setEnabled(true);
			saveMenuItem.setEnabled(getSelectedText().isChanged());
			closeMenuItem.setEnabled(true);
			exitMenuItem.setEnabled(true);
		}
	}
	
	private void updateEditMenu() {
		if (tabbedText.getSelectedIndex() == -1) {
			undoMenuItem.setEnabled(false);
			redoMenuItem.setEnabled(false);
			selectAllMenuItem.setEnabled(false);
			cutMenuItem.setEnabled(false);
			copyMenuItem.setEnabled(false);
			pasteMenuItem.setEnabled(false);
			findMenuItem.setEnabled(false);
		} else {
			undoMenuItem.setEnabled(getSelectedText().canUndo());
			redoMenuItem.setEnabled(getSelectedText().canRedo());
			selectAllMenuItem.setEnabled(getSelectedText().getText().length() != 0);
			cutMenuItem.setEnabled(getSelectedText().getSelectedText() != null);
			copyMenuItem.setEnabled(cutMenuItem.isEnabled());
			pasteMenuItem.setEnabled(true);
			findMenuItem.setEnabled(selectAllMenuItem.isEnabled());
		}
	}
	
	private void updateFormatMenu() {
		if (tabbedText.getSelectedIndex() == -1) {
			wrapMenuCheckBox.setSelected(false);
			wrapMenuCheckBox.setEnabled(false);
		} else {
			wrapMenuCheckBox.setSelected(getSelectedText().getLineWrap());
			wrapMenuCheckBox.setEnabled(true);
		}
	}
	
	public void menuSelected(MenuEvent e) {
		if (e.getSource() == fileMenu) {
			updateFileMenu();
		}
		else if (e.getSource() == editMenu) {
			updateEditMenu();
		}
		else if (e.getSource() == formatMenu) {
			updateFormatMenu();
		}
    }

	public void menuCanceled(MenuEvent e) {
		
	}
	
	public void menuDeselected(MenuEvent e) {
    	
    }
	
	public void popupMenuCanceled(PopupMenuEvent e) {
		
	}
	
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		
	}
	
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		closePopItem.setEnabled(true);
		selectAllPopItem.setEnabled(getSelectedText().getText().length() != 0);
		cutPopItem.setEnabled(getSelectedText().getSelectedText() != null);
		copyPopItem.setEnabled(cutPopItem.isEnabled());
		pastePopItem.setEnabled(true);
	}
	public void windowActivated(WindowEvent e) {
		
	}
	
	public void windowClosed(WindowEvent e) {
		
	}

	public void windowClosing(WindowEvent e) {
		while (tabbedText.getSelectedIndex() != -1) {
			closeText();
		}
		System.exit(0);
	}
	
	public void windowDeactivated(WindowEvent e) {
		
	}
	
	public void windowDeiconified(WindowEvent e) {
		
	}
	
	public void windowIconified(WindowEvent e) {
		
	}
	
	public void windowOpened(WindowEvent e) {
		
	}
	
	public static void main(String[] args) {
		new Notebook();
	}
}