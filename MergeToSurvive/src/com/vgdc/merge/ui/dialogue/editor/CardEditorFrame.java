package com.vgdc.merge.ui.dialogue.editor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class CardEditorFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private DialogueCard card;
	private JButton saveButton;
	private JTextArea textArea;

	private boolean isSaved = true;

	private DialogueScriptEditor callingFrame;
	private UndoManager undoManager;

	public CardEditorFrame(DialogueScriptEditor callingFrame, DialogueCard card) {
		this.callingFrame = callingFrame;
		this.card = card;

		create();
	}

	private void create() {
		this.setTitle("DialogueEditor: Card Editor");
		this.setFont(DialogueScriptEditor.DEFAULT_FONT);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());

		final JLabel cardTypeLabel = new JLabel("Card Type: " + card.getType().name());
		{
			cardTypeLabel.setFont(DialogueScriptEditor.DEFAULT_FONT);
			// enable card type change
			cardTypeLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						CardType option = (CardType) JOptionPane.showInputDialog(CardEditorFrame.this,
								"Change card type:", "Dialogue Editor: Change Card Type", 1, null, CardType.values(),
								CardType.DIALOGUE);
						// selection wasn't cancelled
						if (option != null) {
							card.setCardType(option);
							cardTypeLabel.setText("CardType: " + card.getType());
							callingFrame.output("Card Type changed to: " + option.name());
							callingFrame.update();
						}
					}
				}
			});
			add(cardTypeLabel, BorderLayout.NORTH);
		}

		textArea = new JTextArea();
		{
			textArea.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					isSaved = false;
					super.keyTyped(e);
				}
			});

			textArea.setText(card.getText());
			textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
				@Override
				public void undoableEditHappened(UndoableEditEvent e) {
					undoManager.addEdit(e.getEdit());
				}
			});
			textArea.setFont(DialogueScriptEditor.DEFAULT_FONT);

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
		}

		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		{
			this.add(scrollPane, BorderLayout.CENTER);
			scrollPane.setPreferredSize(new Dimension(500, 200));
		}

		JPanel buttonPanel = new JPanel();
		{
			saveButton = new JButton("Save");
			{
				saveButton.setFont(DialogueScriptEditor.DEFAULT_FONT);
				saveButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						save();
					}
				});
				buttonPanel.add(saveButton);
			}

			JButton saveAndCloseButton = new JButton("Save and Close");
			{
				saveAndCloseButton.setFont(DialogueScriptEditor.DEFAULT_FONT);
				saveAndCloseButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						save();
						close();
					}
				});
				buttonPanel.add(saveAndCloseButton);
			}
			this.add(buttonPanel, BorderLayout.SOUTH);
		}

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (!isSaved) {
					int option = JOptionPane.showConfirmDialog(CardEditorFrame.this,
							"You have unsaved changes. Save changes before closing?", "Dialogue Editor: Save changes",
							JOptionPane.YES_NO_CANCEL_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						save();
						close();
					}
					else if (option == JOptionPane.NO_OPTION) {
						close();
					}
					// else if(option == JOptionPane.CANCEL_OPTION) { don't
					// close }
				}
				else {
					close();
				}
			}
		});

		this.pack();
		this.setVisible(true);
	}

	public void save() {
		if (!card.isRemoved()) {
			System.out.println("Changes saved: " + textArea.getText());
			card.setText(textArea.getText());
			isSaved = true;
			callingFrame.update();
			callingFrame.output("Card saved.");
		}
	}
	
	public void close() {
		callingFrame.output("Editor window closed.");
		CardEditorFrame.this.dispose();
	}
}
