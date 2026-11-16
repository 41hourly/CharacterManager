package com.unpri.charactermanager.model;

public class Character {
    private final int id;
    private final String name;
    private final CharacterType type;
    private final int level;
    private final int hitPoints;
    private final int attackPower;
    private final int mana;

    public Character(int id, String name, CharacterType type, int level,
                     int hitPoints, int attackPower, int mana) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama karakter tidak boleh kosong.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Role karakter wajib dipilih.");
        }
        if (level < 1 || level > 99) {
            throw new IllegalArgumentException("Level karakter harus berada di antara 1 sampai 99.");
        }
        // Mini-Challenge: aturan domain MAGE maksimal level 60
        if (type == CharacterType.MAGE && level > 60) {
            throw new IllegalArgumentException("Role MAGE tidak boleh memiliki level di atas 60.");
        }

        this.id = id;
        this.name = name.trim();
        this.type = type;
        this.level = level;
        this.hitPoints = hitPoints;
        this.attackPower = attackPower;
        this.mana = mana;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public CharacterType getType() { return type; }
    public int getLevel() { return level; }
    public int getHitPoints() { return hitPoints; }
    public int getAttackPower() { return attackPower; }
    public int getMana() { return mana; }
}