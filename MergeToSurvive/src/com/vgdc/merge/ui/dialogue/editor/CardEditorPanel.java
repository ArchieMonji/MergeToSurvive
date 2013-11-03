package com.vgdc.merge.ui.dialogue.editor;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class CardEditorPanel extends JPanel implements DocumentListener {
	private static final long serialVersionUID = 1L;

	private JLabel cardTypeLabel;
	private DialogueCard card;
	private JTextArea textArea;

	private DialogueScriptEditor callingFrame;

	private UndoManager undoManager;

	private Document defaultDocument;

	private Document currDoc;

	public CardEditorPanel(DialogueScriptEditor callingFrame) {
		this.callingFrame = callingFrame;

		create();
	}

	private void create() {
		this.setFont(DialogueScriptEditor.DEFAULT_FONT);
		this.setLayout(new BorderLayout());

		cardTypeLabel = new JLabel("No card selected.");
		{
			cardTypeLabel.setFont(DialogueScriptEditor.DEFAULT_FONT);
			// enable card type change
			cardTypeLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						if (card != null) {
							CardType option = (CardType) JOptionPane.showInputDialog(CardEditorPanel.this,
									"Change card type:", "Dialogue Editor: Change Card Type", 1, null,
									CardType.values(), CardType.DIALOGUE);
							// selection wasn't cancelled
							if (option != null) {
								card.setCardType(option);
								cardTypeLabel.setText("CardType: " + card.getType());
								callingFrame.output("Card Type changed to: " + option.name());
								callingFrame.update();
							}
						}
					}
				}
			});
			add(cardTypeLabel, BorderLayout.NORTH);
		}

		textArea = new JTextArea();
		{
			defaultDocument = textArea.getDocument();
			undoManager = new UndoManager();

			InputMap im = textArea.getInputMap(JComponent.WHEN_FOCUSED);
			ActionMap am = textArea.getActionMap();

			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

			am.put("Undo", new AbstractAction() {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						if (undoManager.canUndo()) {
							undoManager.undo();
						}
					} catch (CannotUndoException exp) {
						exp.printStackTrace();
					}
				}
			});
			am.put("Redo", new AbstractAction() {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						if (undoManager.canRedo()) {
							undoManager.redo();
						}
					} catch (CannotUndoException exp) {
						exp.printStackTrace();
					}
				}
			});
			textArea.setFont(DialogueScriptEditor.DEFAULT_FONT);
		}

		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		{
			this.add(scrollPane, BorderLayout.CENTER);
		}
	}

	public void loadCard(DialogueCard card) {
		System.out.println(card);
		if (currDoc != null) {
			currDoc.removeDocumentListener(this);
		}
		this.card = card;
		if (card == null) {
			textArea.setDocument(defaultDocument);
			undoManager = new UndoManager();
			cardTypeLabel.setText("No card selected.");
			textArea.setText("");
		}
		else {
			currDoc = card.getDocument();
			textArea.setDocument(currDoc);
			currDoc.addDocumentListener(this);
			undoManager = card.getUndoManager();
			cardTypeLabel.setText("CardType: " + card.getType());
		}
	}

	public DialogueCard getActiveCard() {
		return card;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		callingFrame.update();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		callingFrame.update();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		callingFrame.update();
	}

	public void setFocus() {
		textArea.requestFocus();
	}
}
