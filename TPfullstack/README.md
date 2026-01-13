# Fullstack TPs

A curated collection of fullstack programming exercises covering OOP, interfaces, design patterns, and MVC.  

| TP | Goal | Key Features |
|----|------|--------------|
| TP1 – Class Hierarchy: Person → Director | Build a class hierarchy from `Person` to `Director` | Constructors, properties, `Display()` method; test with 8 people; display using `for` and `foreach`. |
| TP2 – Vehicles: Abstract and Derived | Design an abstract `Vehicle` class and subclasses | Abstract methods `Start()` and `Accelerate()`; auto-generated ID, model year, price; `Car` and `Truck` implement behavior; `ToString()` for display. |
| TP3 – Building & House | Create a `Building` class and `House` subclass | `Building`: address, constructors, `ToString()`; `House`: adds rooms, overrides `ToString()`; test both. |
| TP Interfaces 1 – Animal Sounds | Implement polymorphism via interfaces | `Cryable` interface; `Cat` and `Dog` cry, `Rabbit` silent; test array of animals calling cry. |
| TP Interfaces 2 – Convertible & Sorting | Enable numeric comparison of objects | `Convertible` interface with `toInt()`; implemented in `Account` and `Date`; utility class to sort arrays. |
| TP Interfaces 3 – Translating Shapes | Animate objects with translation | `WithTranslation` interface; move objects per clock tick; store positions; test with multiple objects. |
| TP Factory – Vehicle Builder | Apply Factory and Builder patterns | Create `Car` and `Motorbike`; customize color, wheels, engine; streamline production. |
| TP 2022 – Computer Factory (Abstract Factory) | Practice Abstract Factory pattern | Create computers using Abstract Factory; scalable and flexible design. |
| TP Swing | Build a Swing application for library management | Manage books (add, modify, delete, search) via GUI using `JFrame`, `JTable`, `JPanel`, `JButton`, `JTextField`, `JOptionPane`. Data stored in an `ArrayList`. |
| TP Student | Implement MVC pattern in Java | Console application demonstrating MVC: classes `Student`, `StudentView`, `StudentController`, and `MVCPatternDemo`. Displays and updates student information. |
| TP Player | Manage players with MVC and Factory | Swing MVC application to add, edit, delete, and display players (`name`, `surname`, `score`) in a table via `PlayerModel`, `PlayerView`, `PlayerController`, and `PlayerFactory`. |
| TP Mastermind | Play a Mastermind game in console | Single-file Java CLI application; generates a secret color combination; player guesses with feedback (# for correct position, o for correct color wrong position); supports standard and custom game settings; saves game history in JSON; includes test methods for combination generation and validation; displays history of current and past games. |
