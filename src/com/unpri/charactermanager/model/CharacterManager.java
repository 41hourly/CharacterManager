package com.unpri.charactermanager.model;

import java.util.ArrayList;
import java.util.List;

public class CharacterManager {

    private final List<Character> characters = new ArrayList<>();
    private int nextId = 1;

    // 1. Menambah karakter baru
    public Character addCharacter(Character character) {
        if (character == null) {
            throw new IllegalArgumentException("Karakter tidak boleh null.");
        }
        if (character.getId() == 0) {
            character.setId(nextId++);
        } else if (character.getId() >= nextId) {
            nextId = character.getId() + 1;
        }
        characters.add(character);
        return character;
    }

    // 2. Mencari karakter berdasarkan ID
    public Character findById(int id) {
        for (Character c : characters) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    // 3. Mencari karakter berdasarkan nama (tidak sensitif huruf besar/kecil)
    public List<Character> findByName(String name) {
        List<Character> results = new ArrayList<>();
        if (name == null || name.isBlank()) {
            return results;
        }
        for (Character c : characters) {
            if (c.getName() != null && c.getName().equalsIgnoreCase(name.trim())) {
                results.add(c);
            }
        }
        return results;
    }

    // 4. Mengambil seluruh daftar karakter
    public List<Character> getAllCharacters() {
        return new ArrayList<>(characters);
    }

    // 5. Mengubah level karakter berdasarkan ID
    public boolean setCharacterLevel(int id, int newLevel) {
        Character target = findById(id);
        if (target == null) {
            return false;
        }
        target.setLevel(newLevel);
        return true;
    }

    // 6. Mengubah mana karakter berdasarkan ID
    public boolean setCharacterMana(int id, int newMana) {
        Character target = findById(id);
        if (target == null) {
            return false;
        }
        target.setMana(newMana);
        return true;
    }

    // 7. Menghapus karakter berdasarkan ID
    public boolean removeCharacter(int id) {
        Character target = findById(id);
        if (target == null) {
            return false;
        }
        return characters.remove(target);
    }

    // 8. Menampilkan semua karakter ke konsol
    public void printAllCharacters() {
        if (characters.isEmpty()) {
            System.out.println("Belum ada karakter yang terdaftar.");
            return;
        }
        for (Character c : characters) {
            System.out.println(c);
        }
    }
}