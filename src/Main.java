import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;
import com.unpri.charactermanager.view.MainWindow;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        CharacterManager manager = new CharacterManager();

        manager.addCharacter(new Character("Arthur", CharacterType.WARRIOR, 1));
        manager.addCharacter(new Character("Lyra", CharacterType.MAGE, 3));
        manager.addCharacter(new Character("Robin", CharacterType.ARCHER, 2));
        manager.addCharacter(new Character("Elena", CharacterType.HEALER, 2));

        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow(manager);
            window.setVisible(true);
        });
    }
}