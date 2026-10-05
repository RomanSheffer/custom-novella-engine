# 🐈 Visual Novel Creator (CNEngine)

This is a ready-to-use, standalone No-Code engine for creating your own visual novels and text quests. The product is fully focused on ordinary users and requires no programming skills — the game is built by simply renaming files according to the numbering rule and is launched with a single click.

At the same time, this project is a clean architectural Pet project written according to strict code quality standards and SonarQube recommendations.

---

## 🕹️ USER GUIDE

### ❗ To get started
Copy the folder "custom_engine_output/CustomNovellaEngine" to your desktop. Add the "levels" folder from the project root into it. There is already a demonstration novella written by me inside. When creating your own novella, you can delete everything in the "levels" folder and put your own files there.

### 📌 Main principle: The Single-Number Rule
The entire logic of the engine is built on continuous file numbering. For the game to understand which resources belong to a specific screen (slide), all files for that screen must be named with the exact same number.

To create the first level (starting screen), you need to put the following into the game folder:
* `1.json` — Button logic and transitions (required file)
* `1.txt` — The story text for this scene
* `1.jpg` — Background picture for this screen
* `1.wav` — A short sound effect when the slide opens (e.g., a door creaking)
* `1theme.wav` — Background music that will loop continuously on this level

> ❗ Only one sound and one music theme can be applied to a single level at the same time. File formats must be strictly observed.

### 📁 Structure of the levels folder
Create a folder named "levels" right next to the game's executable file and collect all your story files there:
```text
📁 levels/
├── 📄 1.json          # Button logic for the 1st level
├── 📝 1.txt           # Text for the 1st level
├── 🖼️ 1.jpg           # Background for the 1st level
├── 🎵 1theme.wav      # Music for the 1st level
├── 🔊 1.wav           # Sound for the 1st level
├── 📄 2.json          # Transitions for the 2nd level...
└── 📝 2.txt           # Text for the 2nd level...
```

### 📝 Logic file structure (JSON)
The configuration file (.json) has a crystal-clear and understandable structure. It specifies the ID of the current level and a button map: the text that the player will see, and the slide number that this button will send them to.

Example of a correct 1.json file:
```json
{
  "levelId": 1,
  "buttonsMap": {
    "Пойти искать еду": 2,
    "Осмотреться в комнате": 21
  }
}
```
> ❗ The number of buttons can be any. Just add a comma and write a similar button description structure below.

### 🏁 How to make the end of the game?
When the story comes to an end and you need to finish the game (show the final screen or credits), specify the special reserved ID 999 in the transition button. The engine will automatically understand that the plot is finished and will smoothly stop the game session:
```json
"buttonsMap": {
  "Выйти к свету (Завершить игру)": 999
}
```

### 🚀 Instructions for launching the game
1. Format your levels in the "levels" folder according to the instructions above.
2. Launch the NovellaEngine.exe file by double-clicking it.
3. The engine itself will scan the files, link them together, and start the game from the starting 1st level.

> ❗ If there is an error in the JSON structure, you will see information about it on the starting screen along with the number of the file with the error.

---

## 🛠️ DEVELOPER GUIDE

This section is intended for describing the architectural decisions of the project and conducting a Code Review.

### 🏗️ Architectural Features and Refactoring
The project's codebase complies with modern engineering practices:
* **Inversion of Control (IoC):** The NovellaController class is completely freed from hidden states. All key dependencies (GameDataLoader, SoundPlayer, etc.) are injected into it from the outside through the constructor.
* **Smart Interception and Isolation of JSON Errors:** The engine implements a fault tolerance system when working with user content. If a novella creator makes a syntax error in a JSON file (for example, forgets a quotation mark or a comma), the application will intercept the error and display the information and the file number on the starting screen. Application logs are written to a text file logs.txt for easy debugging.
* **Resource Safety (Java NIO):** Scanning the "levels" file directory is implemented using reactive Java Streams (Files.list). The mandatory use of the try-with-resources structure guarantees immediate release of operating system descriptors when reading folders, completely eliminating resource leaks.
* **Cognitive Load Reduction:** Heavy nested try-catch blocks inside loops were encapsulated into separate private methods with a single area of responsibility, which made the main execution flow linear and easy to read.

### 💻 Technology Stack
* **Development Language:** Java 21 LTS (using modern compiler optimizations)
* **Architectural Pattern:** MVC (Model-View-Controller)
* **Data Format:** JSON (dynamic transition mapping)
* **Logging:** Built-in file logging system with outputting reports to logs.txt
