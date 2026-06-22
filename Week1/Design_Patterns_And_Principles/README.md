# Week 1 — Design Patterns & Principles

Java assignments covering SOLID design principles and classic Gang of Four (GoF) patterns applied to real-world scenarios: logging, document creation, payments, notifications, image loading, stock updates, and customer management.

## Prerequisites

- Java JDK 8 or later
- A terminal or IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

## SOLID Principles Overview

| Principle | Meaning | Example in This Module |
|-----------|---------|----------------------|
| **S** — Single Responsibility | A class should have one reason to change | `CustomerService` handles business logic; `CustomerRepository` handles data access |
| **O** — Open/Closed | Open for extension, closed for modification | `PaymentStrategy` allows new payment types without changing `PaymentContext` |
| **L** — Liskov Substitution | Subtypes must be substitutable for base types | `WordDocument`, `PdfDocument`, and `ExcelDocument` all implement `Document` |
| **I** — Interface Segregation | Clients should not depend on unused interfaces | `PaymentProcessor` exposes only `processPayment`; adapters wrap gateway-specific APIs |
| **D** — Dependency Inversion | Depend on abstractions, not concrete classes | `CustomerService` depends on `CustomerRepository` interface, not the implementation |

---

## Project Structure

```
Week1/Design_Patterns_And_Principles/
├── 01 Implementing the Singleton Pattern/
│   ├── Logger.java
│   └── SingletonTest.java
├── 02 Implementing the Factory Method Pattern/
│   ├── Document.java
│   ├── DocumentFactory.java
│   ├── WordDocument.java / WordDocumentFactory.java
│   ├── PdfDocument.java / PdfDocumentFactory.java
│   ├── ExcelDocument.java / ExcelDocumentFactory.java
│   └── Main.java
├── 03 Implementing the Builder Pattern/
│   ├── Computer.java
│   └── Main.java
├── 04 Implementing the Adapter Pattern/
│   ├── PaymentProcessor.java
│   ├── PayPalGateway.java / PayPalAdapter.java
│   ├── StripeGateway.java / StripeAdapter.java
│   └── Main.java
├── 05 Implementing the Decorator Pattern/
│   ├── Notifier.java
│   ├── EmailNotifier.java
│   ├── NotifierDecorator.java
│   ├── SMSNotifierDecorator.java
│   ├── SlackNotifierDecorator.java
│   └── DecoratorPatternDemo.java
├── 06 Implementing the Proxy Pattern/
│   ├── Image.java
│   ├── RealImage.java
│   ├── ProxyImage.java
│   └── ProxyPatternDemo.java
├── 07 Implementing the Observer Pattern/
│   ├── Observer.java
│   ├── Stock.java
│   ├── StockMarket.java
│   ├── MobileApp.java
│   ├── WebApp.java
│   └── ObserverPatternTest.java
├── 08 Implementing the Strategy Pattern/
│   ├── PaymentStrategy.java
│   ├── CreditCardPayment.java
│   ├── PayPalPayment.java
│   ├── PaymentContext.java
│   └── Main.java
├── 09 Implementing the Command Pattern/
│   ├── Command.java
│   ├── Light.java
│   ├── LightOnCommand.java
│   ├── LightOffCommand.java
│   ├── RemoteControl.java
│   └── TestCommandPattern.java
├── 10 Implementing the MVC Pattern/
│   ├── Student.java
│   ├── StudentView.java
│   ├── StudentController.java
│   └── MVCPatternDemo.java
└── 11 Implementing Dependency Injection/
    ├── Customer.java
    ├── CustomerRepository.java
    ├── CustomerRepositoryImpl.java
    ├── CustomerService.java
    └── Main.java
```

Each folder also contains sample output (`Output.jpg`).

---

## How to Run

Navigate to the exercise folder, compile all Java files, and run the main class:

```bash
# Example: Exercise 1
cd "01 Implementing the Singleton Pattern"
javac *.java
java SingletonTest
```

| Exercise | Folder | Main Class |
|----------|--------|------------|
| 1 | `01 Implementing the Singleton Pattern` | `SingletonTest` |
| 2 | `02 Implementing the Factory Method Pattern` | `Main` |
| 3 | `03 Implementing the Builder Pattern` | `Main` |
| 4 | `04 Implementing the Adapter Pattern` | `Main` |
| 5 | `05 Implementing the Decorator Pattern` | `DecoratorPatternDemo` |
| 6 | `06 Implementing the Proxy Pattern` | `ProxyPatternDemo` |
| 7 | `07 Implementing the Observer Pattern` | `ObserverPatternTest` |
| 8 | `08 Implementing the Strategy Pattern` | `Main` |
| 9 | `09 Implementing the Command Pattern` | `TestCommandPattern` |
| 10 | `10 Implementing the MVC Pattern` | `MVCPatternDemo` |
| 11 | `11 Implementing Dependency Injection` | `Main` |

---

## Exercise 1: Singleton Pattern

**Scenario:** Ensure a single shared `Logger` instance across the application (e.g., one database connection or log writer).

**Pattern type:** Creational

### Implementation

- **`Logger`** — Private constructor, static `getInstance()` with double-checked locking for thread safety
- **`SingletonTest`** — Verifies `logger1` and `logger2` reference the same instance

### Analysis

**Why Singleton?**

Some resources (loggers, configuration managers, connection pools) must exist exactly once to avoid inconsistent state or wasted memory.

**Key components**

| Component | Role |
|-----------|------|
| Private constructor | Blocks direct `new Logger()` |
| Static instance | Holds the single object |
| `getInstance()` | Global access point |

**Trade-offs**

| Advantage | Disadvantage |
|-----------|--------------|
| Controlled single instance | Harder to unit test (global state) |
| Lazy initialization possible | Can hide dependencies |
| Thread-safe with locking | Tight coupling to concrete class |

---

## Exercise 2: Factory Method Pattern

**Scenario:** Create different document types (Word, PDF, Excel) without the client knowing concrete classes.

**Pattern type:** Creational

### Implementation

- **`Document`** — Interface with `open()` and `close()`
- **`DocumentFactory`** — Abstract factory with `createDocument()`
- **Concrete factories** — `WordDocumentFactory`, `PdfDocumentFactory`, `ExcelDocumentFactory`
- **`Main`** — Each factory creates and uses its document type

### Analysis

**Why Factory Method?**

Object creation logic is centralized. Adding a new document type means adding a new factory and document class — existing client code stays unchanged (Open/Closed Principle).

**Structure**

```
Client → DocumentFactory.createDocument() → Document (Word / PDF / Excel)
```

**Benefits**

- Decouples client from concrete classes
- Supports polymorphism via the `Document` interface
- Easy to extend with new document types

---

## Exercise 3: Builder Pattern

**Scenario:** Construct a `Computer` with many optional parts (CPU, RAM, storage, GPU, SSD) without telescoping constructors.

**Pattern type:** Creational

### Implementation

- **`Computer`** — Immutable object with private constructor
- **`Computer.Builder`** — Fluent API with chained setters and `build()`
- **`Main`** — Builds a basic computer and a fully configured gaming computer

### Analysis

**Why Builder?**

When an object has many optional fields, the Builder pattern improves readability and avoids constructor overload explosion.

**Example usage**

```java
Computer gamingComputer = new Computer.Builder()
        .setCPU("AMD Ryzen 5")
        .setRAM("16GB")
        .setStorage("512GB SSD")
        .setGPU("NVIDIA GeForce RTX 3080")
        .setSSD(true)
        .build();
```

**Benefits**

| Benefit | Description |
|---------|-------------|
| Readable construction | Named, chainable setters |
| Immutable product | Fields set once at build time |
| Flexible configuration | Only set the fields you need |

---

## Exercise 4: Adapter Pattern

**Scenario:** Integrate incompatible payment gateways (PayPal, Stripe) behind a unified `PaymentProcessor` interface.

**Pattern type:** Structural

### Implementation

- **`PaymentProcessor`** — Target interface with `processPayment(double amount)`
- **`PayPalGateway` / `StripeGateway`** — Adaptee classes with incompatible APIs
- **`PayPalAdapter` / `StripeAdapter`** — Translate calls to the target interface
- **`Main`** — Processes payments through both adapters uniformly

### Analysis

**Why Adapter?**

Legacy or third-party APIs often do not match your application's interface. Adapters bridge that gap without modifying existing gateway code.

**Structure**

```
Client → PaymentProcessor → Adapter → Gateway (PayPal / Stripe)
```

**Benefits**

- Reuses existing gateway classes
- Client code depends on one interface
- New gateways can be added with new adapters

---

## Exercise 5: Decorator Pattern

**Scenario:** Send notifications through multiple channels (Email, SMS, Slack) by wrapping notifiers dynamically.

**Pattern type:** Structural

### Implementation

- **`Notifier`** — Component interface with `send(String message)`
- **`EmailNotifier`** — Concrete component
- **`NotifierDecorator`** — Abstract decorator that wraps a `Notifier`
- **`SMSNotifierDecorator` / `SlackNotifierDecorator`** — Add SMS and Slack behavior
- **`DecoratorPatternDemo`** — Chains Email → SMS → Slack decorators

### Analysis

**Why Decorator?**

Add responsibilities to an object at runtime without subclass explosion. Each channel is a separate wrapper.

**Structure**

```
SlackNotifierDecorator → SMSNotifierDecorator → EmailNotifier
```

**Benefits**

| Benefit | Description |
|---------|-------------|
| Flexible composition | Mix and match channels at runtime |
| Open/Closed | Add new decorators without changing core notifier |
| Single Responsibility | Each decorator handles one channel |

---

## Exercise 6: Proxy Pattern

**Scenario:** Lazy-load expensive images — display only creates the real image when first needed.

**Pattern type:** Structural

### Implementation

- **`Image`** — Subject interface with `display()`
- **`RealImage`** — Real subject that loads from disk on creation
- **`ProxyImage`** — Proxy that creates `RealImage` only on first `display()` call
- **`ProxyPatternDemo`** — Second `display()` on the same image skips reloading

### Analysis

**Why Proxy?**

Control access to a resource. Common uses: lazy loading, access control, caching, and remote proxies.

**Behavior in this project**

| Call | Action |
|------|--------|
| First `display()` | Creates `RealImage`, loads file, displays |
| Subsequent `display()` | Reuses cached `RealImage` |

**Benefits**

- Defers costly operations until needed
- Transparent to the client (same `Image` interface)
- Can add logging, caching, or security without changing `RealImage`

---

## Exercise 7: Observer Pattern

**Scenario:** Notify mobile and web apps when a stock price changes in the market.

**Pattern type:** Behavioral

### Implementation

- **`Observer`** — Interface with `update(stockName, stockPrice)`
- **`Stock`** — Subject interface for register/deregister/notify
- **`StockMarket`** — Maintains observer list and broadcasts price changes
- **`MobileApp` / `WebApp`** — Concrete observers
- **`ObserverPatternTest`** — Registers observers, updates price, deregisters `WebApp`

### Analysis

**Why Observer?**

One-to-many dependency: when the subject changes, all registered observers are notified automatically.

**Structure**

```
StockMarket (Subject) ──notify──► MobileApp (Observer)
                              └──► WebApp (Observer)
```

**Benefits**

- Loose coupling between subject and observers
- Dynamic subscribe/unsubscribe at runtime
- Supports multiple independent observers

---

## Exercise 8: Strategy Pattern

**Scenario:** Switch payment methods (Credit Card, PayPal) at runtime without changing checkout logic.

**Pattern type:** Behavioral

### Implementation

- **`PaymentStrategy`** — Strategy interface with `pay(int amount)`
- **`CreditCardPayment` / `PayPalPayment`** — Concrete strategies
- **`PaymentContext`** — Context that delegates to the current strategy
- **`Main`** — Executes payments with different strategies

### Analysis

**Why Strategy?**

Encapsulate interchangeable algorithms. The client selects behavior at runtime instead of using conditionals.

**Structure**

```
PaymentContext → PaymentStrategy → CreditCardPayment / PayPalPayment
```

**Benefits**

| Benefit | Description |
|---------|-------------|
| Eliminates if/else chains | Behavior selected via strategy object |
| Easy to extend | Add new payment type by implementing interface |
| Open/Closed Principle | Context unchanged when adding strategies |

---

## Exercise 9: Command Pattern

**Scenario:** Control a light using a remote — encapsulate on/off actions as command objects.

**Pattern type:** Behavioral

### Implementation

- **`Command`** — Interface with `execute()`
- **`LightOnCommand` / `LightOffCommand`** — Concrete commands wrapping a `Light` receiver
- **`RemoteControl`** — Invoker that holds and executes a command
- **`TestCommandPattern`** — Sets command on remote and presses button

### Analysis

**Why Command?**

Turn a request into a stand-alone object. Supports undo, queuing, logging, and macro commands.

**Structure**

```
RemoteControl (Invoker) → Command → Light (Receiver)
```

**Roles**

| Role | Class |
|------|-------|
| Command | `LightOnCommand`, `LightOffCommand` |
| Receiver | `Light` |
| Invoker | `RemoteControl` |
| Client | `TestCommandPattern` |

**Benefits**

- Decouples invoker from receiver
- Commands can be stored, queued, or undone
- Easy to add new commands without changing remote

---

## Exercise 10: MVC Pattern

**Scenario:** Manage student data with clear separation between data, presentation, and control logic.

**Pattern type:** Architectural

### Implementation

- **`Student`** — Model (name, id, grade)
- **`StudentView`** — View (displays student details)
- **`StudentController`** — Controller (updates model, refreshes view)
- **`MVCPatternDemo`** — Creates model, view, controller; updates and displays student

### Analysis

**Why MVC?**

Separates concerns so UI, business logic, and data can evolve independently.

**Structure**

```
View ← Controller → Model
```

**Responsibilities**

| Layer | Responsibility |
|-------|----------------|
| Model (`Student`) | Holds data and state |
| View (`StudentView`) | Renders data to the user |
| Controller (`StudentController`) | Handles input, updates model, refreshes view |

**Benefits**

- Single Responsibility for each layer
- Easier testing of model and controller without UI
- Multiple views can share the same model

---

## Exercise 11: Dependency Injection

**Scenario:** `CustomerService` retrieves customers without creating its own repository — dependencies are injected.

**Pattern type:** Design principle / IoC

### Implementation

- **`CustomerRepository`** — Abstraction for data access
- **`CustomerRepositoryImpl`** — Concrete repository implementation
- **`CustomerService`** — Business logic with constructor-injected repository
- **`Main`** — Wires repository into service and fetches a customer

### Analysis

**Why Dependency Injection?**

Classes should not create their own dependencies. Injection improves testability, flexibility, and adherence to the Dependency Inversion Principle.

**Constructor injection (used here)**

```java
CustomerRepository customerRepository = new CustomerRepositoryImpl();
CustomerService customerService = new CustomerService(customerRepository);
```

**Benefits**

| Benefit | Description |
|---------|-------------|
| Loose coupling | Service depends on interface, not implementation |
| Testability | Mock repository can be injected in unit tests |
| Flexibility | Swap implementations without changing service |

**DI vs Singleton**

| Approach | Use When |
|----------|----------|
| Singleton | Exactly one shared instance is required globally |
| Dependency Injection | Dependencies should be supplied and swappable |

---

## Pattern Classification Summary

| Category | Patterns in This Module |
|----------|-------------------------|
| **Creational** | Singleton, Factory Method, Builder |
| **Structural** | Adapter, Decorator, Proxy |
| **Behavioral** | Observer, Strategy, Command |
| **Architectural** | MVC |
| **Principles** | SOLID, Dependency Injection |

---

## When to Use Each Pattern

| Pattern | Use When |
|---------|----------|
| Singleton | Exactly one instance must exist (logger, config) |
| Factory Method | Object type chosen at runtime; hide creation logic |
| Builder | Complex object with many optional parameters |
| Adapter | Integrate incompatible third-party or legacy APIs |
| Decorator | Add behavior dynamically without subclassing |
| Proxy | Control access, lazy load, or cache expensive resources |
| Observer | One source must notify many dependents of changes |
| Strategy | Swap algorithms/behaviors at runtime |
| Command | Encapsulate actions as objects (undo, queue, remote) |
| MVC | Separate data, UI, and control in applications |
| Dependency Injection | Decouple classes from concrete dependencies |

---

## Author

Cognizant Full Stack Engineer — Week 1 Assignment
