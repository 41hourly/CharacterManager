import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;
import com.unpri.charactermanager.view.AppTheme;
import com.unpri.charactermanager.view.MainWindow;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        // Opsional: menyelaraskan warna dasar Swing dengan AppTheme
        UIManager.put("Panel.background", AppTheme.BG_MAIN);
        UIManager.put("Label.foreground", AppTheme.TEXT_MAIN);
        UIManager.put("Label.font", AppTheme.FONT_BODY);

        CharacterManager manager = new CharacterManager();

        // Data awal menggunakan CharacterType yang sesuai (WARRIOR, MAGE, ARCHER, HEALER)
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