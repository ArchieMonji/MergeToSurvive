package com.vgdc.merge.ui.dialogue.editor;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.MouseInputAdapter;

public class ListReorderListener extends MouseInputAdapter {
	private DialogueScriptEditor editor;
	private JList<DialogueCard> list;
	private int pressIndex = 0;
	private int releaseIndex = 0;

	public ListReorderListener(DialogueScriptEditor editor, JList<DialogueCard> list) {
		this.editor = editor;
		this.list = list;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			int index = list.locationToIndex(e.getPoint());
			ListModel<DialogueCard> dlm = list.getModel();
			DialogueCard item = dlm.getElementAt(index);
			list.ensureIndexIsVisible(index);
			editor.loadCardIntoEditor(item);
			editor.output("Editing: " + item.getType().name() + " card.");
			editor.update();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressIndex = list.locationToIndex(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		releaseIndex = list.locationToIndex(e.getPoint());
		if (releaseIndex != pressIndex && releaseIndex != -1) {
			reorder();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// mouseReleased(e);
		// pressIndex = releaseIndex;
	}

	private void reorder() {
		DefaultListModel<DialogueCard> model = (DefaultListModel<DialogueCard>) list.getModel();
		DialogueCard elementToDrag = model.elementAt(pressIndex);
		model.removeElementAt(pressIndex);
		model.insertElementAt(elementToDrag, releaseIndex);
		editor.output("Script reordered.");
	}
}