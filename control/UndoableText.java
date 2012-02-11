package control;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

@SuppressWarnings("serial")
public class UndoableText extends JTextArea implements UndoableEdit, UndoableEditListener, DocumentListener {
	private UndoManager um = new UndoManager();
	private boolean changed = false;
	
	public UndoableText() {
		getDocument().addDocumentListener(this);
		getDocument().addUndoableEditListener(this);
	}
	
	public void undoableEditHappened(UndoableEditEvent e) {
		um.undoableEditHappened(e);
	}
	
	public boolean addEdit(UndoableEdit anEdit) {
		return um.addEdit(anEdit);
	}
	
	public boolean canRedo() {
		return um.canRedo();
	}
	
	public boolean canUndo() {
		return um.canUndo();
	}
	
	public void die() {
		um.die();
	}
	
	public String getPresentationName() {
		return um.getPresentationName();
	}
	
	public String getRedoPresentationName() {
		return um.getRedoPresentationName();
	}
	
	public String getUndoPresentationName() {
		return um.getUndoOrRedoPresentationName();
	}
	
	public boolean isSignificant() {
		return um.isSignificant();
	}
	
	public void redo() {
		um.redo();
	}
	
	public boolean replaceEdit(UndoableEdit anEdit) {
		return um.replaceEdit(anEdit);
	}
	
	public void undo() {
		um.undo();
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	public void changedUpdate(DocumentEvent e) {
		setChanged(true);
	}
	
	public void insertUpdate(DocumentEvent e) {
		setChanged(true);
	}
	
	public void removeUpdate(DocumentEvent e) {
		setChanged(true);
	}
}