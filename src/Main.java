import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;
import com.unpri.charactermanager.view.AppTheme;
import com.unpri.charactermanager.view.MainWindow;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        UIManager.put("Panel.background", AppTheme.BG_MAIN);
        UIManager.put("Label.foreground", AppTheme.TEXT_MAIN);
        UIManager.put("Label.font", AppTheme.FONT_BODY);

        CharacterManager manager = new CharacterManager();

        // Minimal 8 karakter dengan variasi level dan role (WARRIOR, MAGE, ARCHER, HEALER)
        manager.addCharacter(new Character("Arthur", CharacterType.WARRIOR, 6));
        manager.addCharacter(new Character("Lyra", CharacterType.MAGE, 5));
        manager.addCharacter(new Character("Robin", CharacterType.ARCHER, 3));
        manager.addCharacter(new Character("Elena", CharacterType.HEALER, 4));
        manager.addCharacter(new Character("Garrick", CharacterType.WARRIOR, 2));
        manager.addCharacter(new Character("Seraphina", CharacterType.MAGE, 8));
        manager.addCharacter(new Character("Kael", CharacterType.ARCHER, 5));
        manager.addCharacter(new Character("Mirelle", CharacterType.HEALER, 1));

        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow(manager);
            window.setVisible(true);
        });
    }
}