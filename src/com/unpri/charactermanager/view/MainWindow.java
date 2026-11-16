package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {

    private final CharacterManager manager;
    private final DefaultTableModel tableModel;
    private final JTable characterTable;

    private final JTextField nameField = new JTextField(12);
    private final JComboBox<CharacterType> typeComboBox = new JComboBox<>(CharacterType.values());
    private final JTextField levelField = new JTextField("1", 5);
    private final JTextField manaField = new JTextField("50", 5);
    private final JTextField searchField = new JTextField(12);

    public MainWindow(CharacterManager manager) {
        this.manager = manager;

        String[] columns = {"ID", "Name", "Type", "Level", "HP", "ATK", "Mana", "Alive"};
        this.tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.characterTable = new JTable(tableModel);

        initComponents();
        refreshTable(manager.getAllCharacters());
    }

    private void initComponents() {
        setTitle("Character Manager");
        setSize(850, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Atas: Cari Karakter
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton searchNameButton = new JButton("Cari Nama");
        JButton showAllButton = new JButton("Tampilkan Semua");
        topPanel.add(new JLabel("Cari Nama:"));
        topPanel.add(searchField);
        topPanel.add(searchNameButton);
        topPanel.add(showAllButton);
        add(topPanel, BorderLayout.NORTH);

        // Panel Tengah: Tabel Karakter
        add(new JScrollPane(characterTable), BorderLayout.CENTER);

        // Panel Bawah: Form Tambah & Aksi Update / Hapus
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Tambah Karakter");
        formPanel.add(new JLabel("Nama:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Tipe:"));
        formPanel.add(typeComboBox);
        formPanel.add(new JLabel("Level:"));
        formPanel.add(levelField);
        formPanel.add(new JLabel("Mana:"));
        formPanel.add(manaField);
        formPanel.add(addButton);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton updateLevelButton = new JButton("Update Level");
        JButton updateManaButton = new JButton("Update Mana");
        JButton deleteButton = new JButton("Hapus Karakter");
        actionPanel.add(updateLevelButton);
        actionPanel.add(updateManaButton);
        actionPanel.add(deleteButton);

        bottomPanel.add(formPanel);
        bottomPanel.add(actionPanel);
        add(bottomPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(e -> handleAddCharacter());
        updateLevelButton.addActionListener(e -> handleUpdateLevel());
        updateManaButton.addActionListener(e -> handleUpdateMana());
        deleteButton.addActionListener(e -> handleDeleteCharacter());
        searchNameButton.addActionListener(e -> refreshTable(manager.findByName(searchField.getText())));
        showAllButton.addActionListener(e -> {
            searchField.setText("");
            refreshTable(manager.getAllCharacters());
        });
    }

    private void handleAddCharacter() {
        try {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama karakter tidak boleh kosong.");
                return;
            }
            CharacterType type = (CharacterType) typeComboBox.getSelectedItem();
            int level = Integer.parseInt(levelField.getText().trim());
            int mana = Integer.parseInt(manaField.getText().trim());

            Character character = new Character(name, type, level);
            character.setMana(mana);
            manager.addCharacter(character);

            nameField.setText("");
            levelField.setText("1");
            manaField.setText("50");
            refreshTable(manager.getAllCharacters());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Level dan Mana harus berupa angka bulat.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private Integer getSelectedCharacterId() {
        int selectedRow = characterTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih salah satu karakter pada tabel terlebih dahulu.");
            return null;
        }
        return (Integer) tableModel.getValueAt(selectedRow, 0);
    }

    private void handleUpdateLevel() {
        Integer id = getSelectedCharacterId();
        if (id == null) return;

        String input = JOptionPane.showInputDialog(this, "Masukkan level baru (minimal 1):");
        if (input == null) return;

        try {
            int newLevel = Integer.parseInt(input.trim());
            if (manager.setCharacterLevel(id, newLevel)) {
                refreshTable(manager.getAllCharacters());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Level harus berupa angka bulat.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void handleUpdateMana() {
        Integer id = getSelectedCharacterId();
        if (id == null) return;

        String input = JOptionPane.showInputDialog(this, "Masukkan mana baru (>= 0):");
        if (input == null) return;

        try {
            int newMana = Integer.parseInt(input.trim());
            if (manager.setCharacterMana(id, newMana)) {
                refreshTable(manager.getAllCharacters());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Mana harus berupa angka bulat.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void handleDeleteCharacter() {
        Integer id = getSelectedCharacterId();
        if (id == null) return;

        if (manager.removeCharacter(id)) {
            refreshTable(manager.getAllCharacters());
        }
    }

    private void refreshTable(List<Character> characters) {
        tableModel.setRowCount(0);
        for (Character c : characters) {
            tableModel.addRow(new Object[]{
                c.getId(),
                c.getName(),
                c.getType(),
                c.getLevel(),
                c.getHitPoints(),
                c.getAttackPower(),
                c.getMana(),
                c.isAlive()
            });
        }
    }
}