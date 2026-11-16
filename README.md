## TP12 — Layout, UI Responsif & Analisis MVC (Sprint #5)

### Wireframe Tata Letak (`MainWindow v0.5`)

+-----------------------------------------------------------------------+
| [NORTH] Header: CHARACTER MANAGER — ROSTER                            |
|         Statistik (GridLayout 2x2): Total | Lv>=5 | HP | ATK          |
+---------------------------------------------------+-------------------+
| [CENTER] JScrollPane                              | [EAST] Legenda    |
|  +---------------------------------------------+  | (BoxLayout Y_AXIS)|
|  | [Wrapper BorderLayout -> NORTH]             |  | - WARRIOR         |
|  |  +------------------+  +------------------+ |  | - MAGE            |
|  |  | Kartu #1         |  | Kartu #2         | |  | - ARCHER          |
|  |  +------------------+  +------------------+ |  | - HEALER          |
|  |  +------------------+  +------------------+ |  |                   |
|  |  | Kartu #3         |  | Kartu #4         | |  | [Vertical Glue]   |
|  |  +------------------+  +------------------+ |  |                   |
|  +---------------------------------------------+  |                   |
+---------------------------------------------------+-------------------+
| [SOUTH] Bar Status: Total Karakter: 8 | Layout Responsif Aktif   v0.5 |
+-----------------------------------------------------------------------+

### Dokumentasi Screenshot
1. **Ukuran Awal (`pack()`):**
   ![Ukuran Awal](Screenshot 2026-11-16 214431.png)
2. **Jendela Dilebarkan:**
   ![Jendela Dilebarkan](Screenshot 2026-11-16 214504.png)
3. **Jendela Dikecilkan (Scrollbar Vertikal Tampak):**
   ![Jendela Dikecilkan](Screenshot 2026-11-16 214948.png)

### Tabel Pengamatan Resize (Prediksi vs Hasil)

| Kondisi Jendela | Prediksi Perilaku Layout | Hasil Aktual Saat Dijalankan |
| :--- | :--- | :--- |
| **Ukuran Awal** | Semua wilayah (`NORTH`, `CENTER`, `EAST`, `SOUTH`) tampil rapi sesuai ukuran awal dan *preferred size* komponen. | Seluruh bagian tampil sesuai prediksi; header, statistik 2x2, 8 kartu karakter dalam 2 kolom, legenda di kanan, serta label `"v0.5"` terlihat jelas[cite: 5]. |
| **Jendela Dilebarkan** | Wilayah `CENTER` akan melebar mengisi ruang tambahan, sedangkan panel legenda di `EAST` tetap tertata rapi dengan *vertical glue*. | Sesuai prediksi: ruang `CENTER` melebar mengikuti ukuran jendela dan tinggi kartu tidak memanjang berlebihan karena dibungkus ke `NORTH` panel pembungkus. |
| **Jendela Dikecilkan** | Saat tinggi `CENTER` menyusut lebih kecil dari tinggi daftar kartu, `JScrollPane` akan menampilkan *scrollbar* vertikal. | Sesuai prediksi: *scrollbar* vertikal muncul pada panel daftar karakter dan seluruh kartu tetap dapat digulir. |

### Struktur Panel Bersarang
Aplikasi menggunakan `rootPanel` berlayout `BorderLayout` sebagai wadah utama dengan empat wilayah aktif: `NORTH`, `CENTER`, `EAST`, dan `SOUTH`. Bagian `NORTH` menggabungkan header dan panel statistik (`GridLayout 2x2`), sedangkan bagian `CENTER` menempatkan grid kartu dua kolom di posisi `NORTH` panel pembungkus di dalam `JScrollPane`. Wilayah `EAST` menyusun legenda peran secara vertikal menggunakan `BoxLayout` (`Y_AXIS`) dengan *strut* dan *glue*, sementara wilayah `SOUTH` memakai `BorderLayout` untuk menempatkan teks status di kiri dan `"v0.5"` di kanan[cite: 5].

### Alasan Panel Tambahan & Layout yang Ditolak
Panel tambahan yang dipilih adalah **panel statistik ringkas** menggunakan **`GridLayout(2, 2)`** di bawah header karena keempat informasi ringkasan (`Total Karakter`, `Level >= 5`, `Total HP`, dan `Total ATK`) memiliki porsi yang setara sehingga tampak rapi dalam kotak 2x2[cite: 5]. Saya menolak **`FlowLayout`** untuk panel ini karena komponennya cenderung berpindah baris secara tidak konsisten saat lebar jendela berubah.

### Link Analisis Bagian D (Google Docs)
* [Analisis Layout & MVC — TP12 (Google Docs)](https://docs.google.com/document/d/11f6pME8ze9TykQ5CT3m9dmvUi5kF80PRzHWXUVmG1NQ/edit?usp=sharing)