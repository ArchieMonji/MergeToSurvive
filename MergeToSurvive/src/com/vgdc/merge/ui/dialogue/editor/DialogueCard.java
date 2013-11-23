package com.vgdc.merge.ui.dialogue.editor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

public class DialogueCard extends JPanel {
	private static final long serialVersionUID = 1L;
	private CardType type;
	// private String name;
	private JLabel typeLabel;
	private JTextArea textArea;
	private UndoManager undoManager;
	private JPanel backgroundPane;

	private Color cardColor = new Color(0xF9F6F4);

	public DialogueCard(CardType type) {
		this.type = type;

		create();
	}

	public void create() {
		// outerpadding for creating separation between cards in JList
		Border externalPaddingBorder = BorderFactory.createLineBorder(Color.white, 2);
		// black stroke border for outside of card
		Border cardBorder = BorderFactory.createLineBorder(Color.black);
		this.setBorder(BorderFactory.createCompoundBorder(externalPaddingBorder, cardBorder));

		// for displaying bg color
		backgroundPane = new JPanel();
		backgroundPane.setOpaque(true);
		backgroundPane.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));

		JPanel contentsPanel = new JPanel();

		// for adding padding between card border and internal components
		Border internalPaddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		contentsPanel.setBorder(internalPaddingBorder);
		contentsPanel.setBackground(cardColor);

		typeLabel = new JLabel(type.name());
		{
			setCardType(type);
			// typeLabel.setOpaque(true);
			typeLabel.setFont(DialogueScriptEditor.DEFAULT_FONT);
			typeLabel.setBackground(cardColor);
		}

		textArea = new JTextArea();
		{
			textArea.setWrapStyleWord(true);
			textArea.setBackground(Color.white);
			textArea.setBorder(BorderFactory.createLineBorder(Color.black));
			textArea.setFont(DialogueScriptEditor.DEFAULT_FONT);

			undoManager = new UndoManager();
			textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
				@Override
				public void undoableEditHappened(UndoableEditEvent e) {
					undoManager.addEdit(e.getEdit());
				}
			});
		}
		// GridBagLayout layout = new GridBagLayout();
		// this.setLayout(layout);
		// GridBagConstraints c = new GridBagConstraints();
		// c.fill = GridBagConstraints.BOTH;
		// c.weightx = 1;
		// c.gridx = 0;
		// c.gridy = 0;
		// c.insets = new Insets(6, 6, 6, 6);
		// add(typeLabel, c);
		//
		// this.add(typeLabel);
		// c.fill = GridBagConstraints.BOTH;
		// c.weightx = 1;
		// c.gridx = 1;
		// c.gridy = 0;
		// c.insets = new Insets(6,6,6,6);
		// this.add(textArea,c);
		setLayout(new BorderLayout());
		add(backgroundPane, BorderLayout.CENTER);

		backgroundPane.setLayout(new BorderLayout());
		backgroundPane.add(contentsPanel, BorderLayout.CENTER);

		GroupLayout layout = new GroupLayout(contentsPanel);
		contentsPanel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		hGroup.addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(typeLabel));
		hGroup.addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(textArea));
		layout.setHorizontalGroup(hGroup);

		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(typeLabel).addComponent(textArea));
		layout.setVerticalGroup(vGroup);
	}

	public void setText(String text) {
		textArea.setText(text);
		repaint();
	}

	public CardType getType() {
		return type;
	}

	public String getText() {
		return textArea.getText();
	}

	@Override
	public String toString() {
		return type + " " + getText();
	}

	boolean isSelected;
	private boolean isRemoved;

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		repaint();
	}

	public void remove() {
		isRemoved = true;
	}

	public boolean isRemoved() {
		return isRemoved;
	}

	public void setSelectionColor(Color color) {
		typeLabel.setForeground(color);
		repaint();
	}

	public void setCardType(CardType type) {
		this.type = type;
		typeLabel.setText(type.name());
		switch (type) {
		case DIALOGUE:
			typeLabel.setMinimumSize(new Dimension(80, 15));
			backgroundPane.setBackground(Color.red);
			break;
		case EVENT:
			typeLabel.setMinimumSize(new Dimension(40, 15));
			backgroundPane.setBackground(Color.blue);
			break;
		case IMAGE:
			typeLabel.setMinimumSize(new Dimension(60, 15));
			backgroundPane.setBackground(Color.yellow);
			break;
		case NAME:
			typeLabel.setMinimumSize(new Dimension(40, 15));
			backgroundPane.setBackground(Color.green);
			break;
		default:
			break;
		}
		repaint();
	}

	public Document getDocument() {
		return textArea.getDocument();
	}

	public UndoManager getUndoManager() {
		return undoManager;
	}

	public void setDocument(Document document) {
		textArea.setDocument(document);
	}
}
