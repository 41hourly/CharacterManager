package com.unpri.charactermanager.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharacterManager {
    public static final int MAX_ROSTER_SIZE = 20;
    private final List<Character> characters = new ArrayList<>();

    public boolean isNameTaken(String name) {
        if (name == null) {
            return false;
        }
        String target = name.trim();
        for (Character c : characters) {
            if (c.getName().equalsIgnoreCase(target)) {
                return true;
            }
        }
        return false;
    }

    public void addCharacter(Character character) {
        if (character == null) {
            throw new IllegalArgumentException("Objek karakter tidak boleh null.");
        }
        if (characters.size() >= MAX_ROSTER_SIZE) {
            throw new IllegalArgumentException("Roster penuh! Maksimal " + MAX_ROSTER_SIZE + " karakter.");
        }
        if (isNameTaken(character.getName())) {
            throw new IllegalArgumentException("Nama '" + character.getName() + "' sudah digunakan.");
        }
        characters.add(character);
    }

    public int getNextId() {
        return characters.size() + 1;
    }

    public List<Character> getAllCharacters() {
        return Collections.unmodifiableList(characters);
    }
}