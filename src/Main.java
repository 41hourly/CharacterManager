import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;
import com.unpri.charactermanager.view.MainWindow;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CharacterManager manager = new CharacterManager();

            manager.addCharacter(new Character(
                    manager.getNextId(), "Arthur", CharacterType.WARRIOR, 6, 160, 40, 50));
            manager.addCharacter(new Character(
                    manager.getNextId(), "Lyra", CharacterType.MAGE, 5, 150, 35, 50));
            manager.addCharacter(new Character(
                    manager.getNextId(), "Robin", CharacterType.ARCHER, 3, 130, 25, 50));
            manager.addCharacter(new Character(
                    manager.getNextId(), "Elena", CharacterType.HEALER, 4, 140, 30, 50));
            manager.addCharacter(new Character(
                    manager.getNextId(), "Garrick", CharacterType.WARRIOR, 2, 120, 20, 50));
            manager.addCharacter(new Character(
                    manager.getNextId(), "Seraphina", CharacterType.MAGE, 8, 180, 50, 50));
            manager.addCharacter(new Character(
                    manager.getNextId(), "Kael", CharacterType.ARCHER, 5, 150, 35, 50));
            manager.addCharacter(new Character(
                    manager.getNextId(), "Mirelle", CharacterType.HEALER, 1, 110, 15, 50));

            MainWindow window = new MainWindow(manager);
            window.setVisible(true);
        });
    }
}