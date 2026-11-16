package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

public class MainWindow extends JFrame {

    private final CharacterManager manager;

    public MainWindow(CharacterManager manager) {
        this.manager = manager;
        initComponents();
    }

    private void initComponents() {
        setTitle("Character Manager v0.5");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel rootPanel = new JPanel(new BorderLayout(10, 10));
        rootPanel.setBackground(AppTheme.BG_MAIN);
        rootPanel.setBorder(AppTheme.CARD_BORDER);

        // 1. NORTH: Header + Panel Statistik Tambahan (GridLayout 2x2)
        JPanel northContainer = new JPanel(new BorderLayout(8, 8));
        northContainer.setBackground(AppTheme.BG_MAIN);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppTheme.BG_MAIN);
        headerPanel.setBorder(AppTheme.CARD_BORDER);

        JLabel titleLabel = new JLabel("CHARACTER MANAGER — ROSTER", SwingConstants.LEFT);
        titleLabel.setFont(AppTheme.FONT_TITLE);
        titleLabel.setForeground(AppTheme.GOLD);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel statsPanel = buildStatsPanel();

        northContainer.add(headerPanel, BorderLayout.NORTH);
        northContainer.add(statsPanel, BorderLayout.CENTER);
        rootPanel.add(northContainer, BorderLayout.NORTH);

        // 2. CENTER: Daftar Kartu Karakter di dalam JScrollPane
        JPanel cardsGridPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        cardsGridPanel.setBackground(AppTheme.BG_MAIN);

        List<Character> allCharacters = manager.getAllCharacters();
        for (Character character : allCharacters) {
            cardsGridPanel.add(createCharacterCard(character));
        }

        // Grid kartu diletakkan di NORTH panel pembungkus sebelum masuk JScrollPane
        JPanel listWrapperPanel = new JPanel(new BorderLayout());
        listWrapperPanel.setBackground(AppTheme.BG_MAIN);
        listWrapperPanel.setBorder(AppTheme.CARD_BORDER);
        listWrapperPanel.add(cardsGridPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(listWrapperPanel);
        scrollPane.setPreferredSize(new Dimension(560, 380));
        scrollPane.getViewport().setBackground(AppTheme.BG_MAIN);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppTheme.TEXT_MAIN, 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        rootPanel.add(scrollPane, BorderLayout.CENTER);

        // 3. EAST: Legenda (BoxLayout Y_AXIS dari CharacterType.values() + Strut + Glue)
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setBackground(AppTheme.BG_MAIN);
        legendPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.GOLD, 1),
                AppTheme.CARD_BORDER
        ));

        JLabel legendTitle = new JLabel("LEGENDA ROLE");
        legendTitle.setFont(AppTheme.FONT_BODY);
        legendTitle.setForeground(AppTheme.GOLD);
        legendPanel.add(legendTitle);
        legendPanel.add(Box.createVerticalStrut(10));

        for (CharacterType type : CharacterType.values()) {
            JPanel itemRow = new JPanel(new BorderLayout(8, 0));
            itemRow.setBackground(AppTheme.BG_MAIN);
            itemRow.setBorder(AppTheme.borderFor(type));
            itemRow.setMaximumSize(new Dimension(260, 82));

            JPanel colorBadge = new JPanel();
            colorBadge.setBackground(AppTheme.colorFor(type));
            colorBadge.setPreferredSize(new Dimension(14, 14));

            JLabel roleLabel = new JLabel("<html><b>" + type.name() + "</b><br/>"
                    + AppTheme.describe(type) + "</html>");
            roleLabel.setFont(AppTheme.FONT_MONO);
            roleLabel.setForeground(AppTheme.TEXT_MAIN);

            itemRow.add(colorBadge, BorderLayout.WEST);
            itemRow.add(roleLabel, BorderLayout.CENTER);

            legendPanel.add(itemRow);
            legendPanel.add(Box.createVerticalStrut(8));
        }

        legendPanel.add(Box.createVerticalGlue());
        rootPanel.add(legendPanel, BorderLayout.EAST);

        // 4. SOUTH: Bar Status (Teks di kiri, "v0.5" di kanan)
        JPanel statusBarPanel = new JPanel(new BorderLayout());
        statusBarPanel.setBackground(AppTheme.BG_MAIN);
        statusBarPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, AppTheme.TEXT_MAIN),
                AppTheme.CARD_BORDER
        ));

        JLabel statusLeftLabel = new JLabel("Total Karakter: " + allCharacters.size() + " | Layout Responsif Aktif");
        statusLeftLabel.setFont(AppTheme.FONT_MONO);
        statusLeftLabel.setForeground(AppTheme.TEXT_MAIN);

        JLabel statusRightLabel = new JLabel("v0.5");
        statusRightLabel.setFont(AppTheme.FONT_MONO);
        statusRightLabel.setForeground(AppTheme.GOLD);

        statusBarPanel.add(statusLeftLabel, BorderLayout.WEST);
        statusBarPanel.add(statusRightLabel, BorderLayout.EAST);

        rootPanel.add(statusBarPanel, BorderLayout.SOUTH);

        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel buildStatsPanel() {
        List<Character> list = manager.getAllCharacters();
        int total = list.size();
        int highLevelCount = 0;
        int totalHp = 0;
        int totalAtk = 0;

        for (Character c : list) {
            if (c.getLevel() >= AppTheme.HIGH_LEVEL_THRESHOLD) {
                highLevelCount++;
            }
            totalHp += c.getHitPoints();
            totalAtk += c.getAttackPower();
        }

        JPanel statsGrid = new JPanel(new GridLayout(2, 2, 8, 8));
        statsGrid.setBackground(AppTheme.BG_MAIN);
        statsGrid.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.TEXT_MAIN, 1),
                AppTheme.CARD_BORDER
        ));

        statsGrid.add(createStatLabel("Total Karakter: " + total));
        statsGrid.add(createStatLabel("Level >= " + AppTheme.HIGH_LEVEL_THRESHOLD + ": " + highLevelCount));
        statsGrid.add(createStatLabel("Total HP: " + totalHp));
        statsGrid.add(createStatLabel("Total ATK: " + totalAtk));

        return statsGrid;
    }

    private JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(AppTheme.FONT_MONO);
        label.setForeground(AppTheme.TEXT_LIGHT);
        return label;
    }

    private JPanel createCharacterCard(Character c) {
        JPanel card = new JPanel(new BorderLayout(4, 4));
        card.setBackground(AppTheme.BG_MAIN);
        card.setBorder(AppTheme.borderFor(c.getType()));

        String badge = (c.getLevel() >= AppTheme.HIGH_LEVEL_THRESHOLD) ? " ★" : "";
        JLabel nameLabel = new JLabel("#" + c.getId() + " " + c.getName() + badge);
        nameLabel.setFont(AppTheme.FONT_BODY);
        nameLabel.setForeground(c.getLevel() >= AppTheme.HIGH_LEVEL_THRESHOLD
                ? AppTheme.GOLD
                : AppTheme.TEXT_LIGHT);

        JLabel roleLabel = new JLabel(c.getType().name() + " · Lv." + c.getLevel());
        roleLabel.setFont(AppTheme.FONT_MONO);
        roleLabel.setForeground(AppTheme.colorFor(c.getType()));

        JLabel statsLabel = new JLabel(
                "HP: " + c.getHitPoints() + " | ATK: " + c.getAttackPower() + " | Mana: " + c.getMana()
        );
        statsLabel.setFont(AppTheme.FONT_MONO);
        statsLabel.setForeground(AppTheme.TEXT_MAIN);

        card.add(nameLabel, BorderLayout.NORTH);
        card.add(roleLabel, BorderLayout.CENTER);
        card.add(statsLabel, BorderLayout.SOUTH);

        return card;
    }
}
