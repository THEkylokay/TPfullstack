# TP fullstack
All fullstack TPs here

- TP1: Classes: Person, Employee, Manager, Director  
  Create a class hierarchy where Person is the base class, Employee inherits from Person, Manager inherits from Employee, and Director inherits from Manager. Implement constructors, properties, and a Display() method in each class. Test with an array of 8 people (5 employees, 2 managers, 1 director) and display them using both `for` and `foreach` loops.

- TP2: Vehicle classes  
  Create an abstract Vehicle class with private attributes (ID, model year, price), automatic ID generation, and abstract methods Start() and Accelerate(). Derive Car and Truck classes that implement these methods with custom messages. Include a ToString() method for vehicle info and a test class to instantiate and display cars and trucks.

- TP3: Building and House classes  
  Create a Building class with an address, constructors, properties, and a ToString() method. Create a House class that inherits from Building, adds a number of rooms, constructors, properties, and overrides ToString(). Test both classes in a program.

- TP Interfaces 1: Animals and Crying  
  Create an interface for animals that can "cry". Implement Cat and Dog classes that cry, and Rabbit class that is silent. Test with an array of animals that can cry and call their cry method to display their sounds.

- TP Interfaces 2: Convertible objects and comparison  
  Implement a Convertible interface with `toInt()` method. Modify Account and Date classes to implement it. Create a utility class with static methods to compare and sort arrays of convertible objects based on their integer representation.

- TP Interfaces 3: Translating shapes  
  Create an interface `WithTranslation` and a class to move objects implementing this interface at each clock tick. Implement translation logic and store movement data. Test with an array of four movable objects and display their positions over three clock ticks.

- TP Factory: Vehicle construction using Factory and Builder patterns  
  Implement a system to create and configure different types of vehicles, with classes for Car and Motorbike, a factory to produce vehicles, and a builder to set their details like color, wheels, and engine type.
