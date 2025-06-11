<h1 align="center">
  <img src="https://github.com/user-attachments/assets/6de1c04a-c452-49b6-822b-8d32270c7a9c" height="40" />
  Notoid - Note Taking App
</h1>

Notoid is a clean and modern note-taking app built using **Kotlin**, following **MVVM architecture** and powered by **Room Database** for offline persistence. It lets users **add, update, delete, and search notes** efficiently with a smooth and responsive UI.

---

## 🚀 Features

- 🧠 Add, edit, delete personal notes
- 🗂 Notes displayed in a responsive **Staggered Grid RecyclerView**
- 🔍 **Real-time search** functionality
- 💾 **Offline-first app** using Room database
- ✅ Clean UI with custom fonts and icons
- 🧭 Navigation using Android's Navigation Component
- 💬 User-friendly dialogs and toast messages

---

## 📸 Screenshots

### 🧾 App Layout
![App Layout](https://github.com/user-attachments/assets/bd80cfc9-42f0-4fcc-90ef-872c963e5783)

### 🗑️ Delete Note Function
![Delete Note](https://github.com/user-attachments/assets/4a2bd273-e7e8-4a59-8be3-05e368a9e74c)

### ✏️ Update/Edit Note
![Update/Edit Note](https://github.com/user-attachments/assets/45fc8fac-963d-4e1b-9774-c59c023988fb)

### ➕ Insert/Add New Note
![Insert Note](https://github.com/user-attachments/assets/63358772-64ea-4aef-be06-06f69685482b)

### 🔍 Search Notes
![Search Notes](https://github.com/user-attachments/assets/61937e78-2254-45b4-ba16-8d1e103d3895)

---

## 🛠 Tech Stack

**Language & Framework:**
- Kotlin
- MVVM Architecture

**Jetpack Libraries:**
- ViewModel
- LiveData
- Room
- Navigation Component
- ViewBinding

---

## 📁 Project Structure

```
├── adapter/
│ └── NoteAdapter.kt
├── database/
│ ├── NoteDAO.kt
│ └── NoteDatabase.kt
├── fragments/
│ ├── HomeFragment.kt
│ ├── NewNoteFragment.kt
│ └── UpdateNoteFragment.kt
├── model/
│ └── Note.kt
├── repository/
│ └── NoteRepository.kt
├── viewmodel/
│ ├── NoteViewmodel.kt
│ └── NoteViewmodelFactory.kt
└── MainActivity.kt
```
## 📚 What I Learned

✅ Built a full-featured offline-first app from scratch  
✅ Implemented **MVVM architecture** and the **repository pattern**  
✅ Managed UI state using **ViewModel** and **LiveData**  
✅ Created and managed a **Room Database**  
✅ Used **Navigation Component** with `navArgs` for safe argument passing  
✅ Applied **StaggeredGridLayoutManager** for better note visuals  
✅ Implemented **SearchView** to filter notes live  
✅ Used **ViewBinding** instead of `findViewById()`  
✅ Created and applied custom **Material themes, icons, and fonts**
## 🧪 How to Run

1. Clone this repository:
   ```bash
   git clone https://github.com/morish276/NoteApp.git
   ```
2. Open in Android Studio Arctic Fox or later.
3. Connect an emulator or device, and Run the project.

> No internet connection required – all data is stored locally.

---

## 📦 Dependencies
```
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1'
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.6.1'
implementation 'androidx.navigation:navigation-fragment-ktx:2.5.3'
implementation 'androidx.navigation:navigation-ui-ktx:2.5.3'
implementation 'androidx.room:room-runtime:2.6.1'
kapt 'androidx.room:room-compiler:2.6.1'
implementation 'androidx.recyclerview:recyclerview:1.3.1'
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.11.0'
```

---

## 🤝 Contribution
Have an idea or want to improve something? Feel free to open issues or pull requests. All contributions are welcome!

---

## 🙏 Thank You

Thank you for checking out this project!  
If you liked it or found it helpful, feel free to ⭐ the repo and contribute!

Happy Coding! 🚀
