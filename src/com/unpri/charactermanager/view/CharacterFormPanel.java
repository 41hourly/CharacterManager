package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;

public class CharacterFormPanel extends JPanel {

    private final CharacterManager manager;
    private final Runnable onCharacterAdded;

    private final JTextField nameField;
    private final JComboBox<CharacterType> roleCombo;
    private final JSpinner levelSpinner;
    private final JLabel feedbackLabel;
    private final Border originalNameBorder;
    private final Border originalLevelBorder;

    public CharacterFormPanel(CharacterManager manager, Runnable onCharacterAdded) {
        this.manager = manager;
        this.onCharacterAdded = onCharacterAdded;

        setLayout(new BorderLayout(8, 8));
        setBackground(AppTheme.BG_MAIN);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.GOLD, 1),
                AppTheme.CARD_BORDER
        ));

        JLabel formTitle = new JLabel("TAMBAH KARAKTER BARU");
        formTitle.setFont(AppTheme.FONT_BODY);
        formTitle.setForeground(AppTheme.GOLD);
        add(formTitle, BorderLayout.NORTH);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(AppTheme.BG_MAIN);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Nama (JTextField)
        JLabel nameLabel = createFieldLabel("Nama:");
        nameField = new JTextField(16);
        nameField.setFont(AppTheme.FONT_MONO);
        nameField.setBackground(AppTheme.BG_MAIN);
        nameField.setForeground(AppTheme.TEXT_LIGHT);
        nameField.setCaretColor(AppTheme.GOLD);
        nameField.setBorder(AppTheme.INPUT_BORDER_DEFAULT);
        originalNameBorder = nameField.getBorder();

        // 2. Role (JComboBox<CharacterType>)
        JLabel roleLabel = createFieldLabel("Role:");
        roleCombo = new JComboBox<>(CharacterType.values());
        roleCombo.setFont(AppTheme.FONT_MONO);

        // 3. Level (JSpinner 1-99)
        JLabel levelLabel = createFieldLabel("Level (1–99):");
        levelSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        levelSpinner.setFont(AppTheme.FONT_MONO);
        originalLevelBorder = levelSpinner.getBorder();

        JButton addButton = new JButton("+ Tambah Karakter");
        addButton.setFont(AppTheme.FONT_MONO);
        addButton.setBackground(AppTheme.GOLD);
        addButton.setForeground(AppTheme.BG_MAIN);
        addButton.setFocusPainted(false);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        fieldsPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        fieldsPanel.add(nameField, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        fieldsPanel.add(roleLabel, gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        fieldsPanel.add(roleCombo, gbc);

        gbc.gridx = 4; gbc.weightx = 0;
        fieldsPanel.add(levelLabel, gbc);
        gbc.gridx = 5; gbc.weightx = 0.3;
        fieldsPanel.add(levelSpinner, gbc);

        gbc.gridx = 6; gbc.weightx = 0;
        fieldsPanel.add(addButton, gbc);

        add(fieldsPanel, BorderLayout.CENTER);

        // Panel Umpan Balik Inline
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        bottomPanel.setBackground(AppTheme.BG_MAIN);
        feedbackLabel = new JLabel("Isi data karakter lalu klik Tambah Karakter.");
        feedbackLabel.setFont(AppTheme.FONT_MONO);
        feedbackLabel.setForeground(AppTheme.TEXT_MAIN);
        bottomPanel.add(feedbackLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Kompromi sementara Sprint #6: ActionListener minimal langsung memanggil handler
        addButton.addActionListener(e -> handleAddCharacter());
    }

    private JLabel createFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(AppTheme.FONT_MONO);
        lbl.setForeground(AppTheme.TEXT_LIGHT);
        return lbl;
    }

    /**
     * Validasi nama berurutan:
     * 1. Kelengkapan -> 2. Panjang -> 3. Format -> 4. Keunikan.
     * Hanya mengembalikan pesan error (null jika valid) tanpa menyentuh komponen UI.
     */
    public String validateName(String rawName) {
        String trimmed = (rawName == null) ? "" : rawName.trim();

        // 1. Kelengkapan
        if (trimmed.isEmpty()) {
            return "Nama tidak boleh kosong. Masukkan 2–20 karakter.";
        }
        // 2. Panjang
        if (trimmed.length() < 2 || trimmed.length() > 20) {
            return "Panjang nama harus 2–20 karakter (saat ini " + trimmed.length() + " karakter).";
        }
        // 3. Format (Aturan pilihan di View: hanya huruf, angka, dan spasi tunggal)
        if (!trimmed.matches("^[A-Za-z0-9]+( [A-Za-z0-9]+)*$")) {
            return "Format nama tidak valid: gunakan huruf/angka tanpa simbol atau spasi ganda.";
        }
        // 4. Keunikan
        if (manager.isNameTaken(trimmed)) {
            return "Nama '" + trimmed + "' sudah terdaftar. Gunakan nama lain.";
        }
        return null;
    }

    private void resetBorders() {
        nameField.setBorder(originalNameBorder);
        levelSpinner.setBorder(originalLevelBorder);
    }

    private void handleAddCharacter() {
        resetBorders();

        // 1. Validasi nama di View
        String rawName = nameField.getText();
        String nameError = validateName(rawName);
        if (nameError != null) {
            showError(nameError, nameField);
            return;
        }

        // 2. Baca spinner setelah commitEdit()
        try {
            levelSpinner.commitEdit();
        } catch (ParseException ex) {
            showError("Level harus berupa angka bulat antara 1 sampai 99.", levelSpinner);
            return;
        }

        String cleanName = rawName.trim();
        CharacterType selectedRole = (CharacterType) roleCombo.getSelectedItem();
        int level = (Integer) levelSpinner.getValue();

        // Hitung stat dasar proporsional level
        int hp = 100 + (level * 10);
        int atk = 15 + (level * 5);
        int mana = 50;

        // 3. Validasi lapisan Model (menangkap IllegalArgumentException, termasuk MAGE > 60)
        try {
            Character newChar = new Character(
                    manager.getNextId(),
                    cleanName,
                    selectedRole,
                    level,
                    hp,
                    atk,
                    mana
            );
            // Kompromi sementara Sprint #6: pemanggilan manager langsung dari View
            manager.addCharacter(newChar);

            // Reset form & beri umpan balik sukses
            nameField.setText("");
            levelSpinner.setValue(1);
            resetBorders();
            feedbackLabel.setForeground(AppTheme.SUCCESS_GREEN);
            feedbackLabel.setText("Sukses: Karakter '" + cleanName + "' berhasil ditambahkan.");
            nameField.requestFocusInWindow();

            if (onCharacterAdded != null) {
                onCharacterAdded.run();
            }
        } catch (IllegalArgumentException ex) {
            // Menampilkan pesan langsung dari Model tanpa menulis ulang aturan
            if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("level")
                    || (ex.getMessage() != null && ex.getMessage().toUpperCase().contains("MAGE"))) {
                showError(ex.getMessage(), levelSpinner);
            } else {
                showError(ex.getMessage(), nameField);
            }
        }
    }

    private void showError(String message, javax.swing.JComponent targetField) {
        feedbackLabel.setForeground(AppTheme.ERROR_RED);
        feedbackLabel.setText("Error: " + message);
        targetField.setBorder(AppTheme.INPUT_BORDER_ERROR);
        targetField.requestFocusInWindow();
    }
}