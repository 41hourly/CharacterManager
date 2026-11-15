import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        CharacterManager manager = new CharacterManager();

        System.out.println("=== 1. Menambah Karakter Baru ===");
        Character c1 = new Character("Arthur", CharacterType.WARRIOR, 1);
        Character c2 = new Character("Lyra", CharacterType.MAGE, 3);
        Character c3 = new Character("Robin", CharacterType.ARCHER, 2);
        Character c4 = new Character("Elena", CharacterType.HEALER, 2);

        manager.addCharacter(c1);
        manager.addCharacter(c2);
        manager.addCharacter(c3);
        manager.addCharacter(c4);

        manager.printAllCharacters();

        System.out.println("\n=== 2. Mencari Karakter Berdasarkan ID ===");
        Character foundById = manager.findById(2);
        System.out.println("Hasil cari ID 2: " + foundById);

        System.out.println("\n=== 3. Mencari Karakter Berdasarkan Nama ===");
        List<Character> foundByName = manager.findByName("arthur");
        for (Character c : foundByName) {
            System.out.println("Hasil cari nama 'arthur': " + c);
        }

        System.out.println("\n=== 4. Update Level dan Mana ===");
        boolean levelUpdated = manager.setCharacterLevel(1, 5);
        boolean manaUpdated = manager.setCharacterMana(2, 120);
        System.out.println("Update level Arthur (ID 1): " + levelUpdated);
        System.out.println("Update mana Lyra (ID 2): " + manaUpdated);
        System.out.println("Status terbaru Arthur: " + manager.findById(1));
        System.out.println("Status terbaru Lyra: " + manager.findById(2));

        System.out.println("\n=== 5. Menguji Validasi Error (Level & Mana) ===");
        try {
            manager.setCharacterLevel(1, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Tertangkap error level valid: " + e.getMessage());
        }

        try {
            manager.setCharacterMana(2, -10);
        } catch (IllegalArgumentException e) {
            System.out.println("Tertangkap error mana valid: " + e.getMessage());
        }

        System.out.println("\n=== 6. Menghapus Karakter (ID 3) ===");
        boolean removed = manager.removeCharacter(3);
        System.out.println("Berhasil hapus ID 3? " + removed);

        System.out.println("\n=== Daftar Karakter Akhir ===");
        manager.printAllCharacters();
    }
}