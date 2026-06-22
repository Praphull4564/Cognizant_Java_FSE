# Week 1 — Algorithms & Data Structures

Java assignments covering core data structures and algorithms: inventory management, search, sorting, arrays, linked lists, and recursion.

## Prerequisites

- Java JDK 8 or later
- A terminal or IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

## Project Structure

```
Week1/Algorithms_Data_Structures/
├── 01 Inventory Management System/
│   ├── Product.java
│   └── Inventory.java
├── 02 Ecommerce Platform Search Function/
│   └── ProductSearch.java
├── 03 Sorting Customer Orders/
│   └── OrderSorting.java
├── 04 Employee Management System/
│   ├── Employee.java
│   ├── EmployeeManager.java
│   └── Main.java
├── 05 Task Management System/
│   └── TaskManager.java
├── 06 Library Management System/
│   ├── Book.java
│   └── BookSearch.java
└── 07 Financial Forecasting/
    └── FinancialForecasting.java
```

Each folder also contains assignment documentation (`.docx`) and sample output (`Output.jpg`).

---

## How to Run

Navigate to the exercise folder, compile, and run the main class:

```bash
# Example: Exercise 1
cd "01 Inventory Management System"
javac Product.java Inventory.java
java Inventory
```

| Exercise | Folder | Main Class |
|----------|--------|------------|
| 1 | `01 Inventory Management System` | `Inventory` |
| 2 | `02 Ecommerce Platform Search Function` | `ProductSearch` |
| 3 | `03 Sorting Customer Orders` | `OrderSorting` |
| 4 | `04 Employee Management System` | `Main` |
| 5 | `05 Task Management System` | `TaskManager` |
| 6 | `06 Library Management System` | `BookSearch` |
| 7 | `07 Financial Forecasting` | `FinancialForecasting` |

---

## Exercise 1: Inventory Management System

**Scenario:** Warehouse inventory system with efficient product storage and retrieval.

### Implementation

- **`Product`** — Attributes: `productId`, `productName`, `quantity`, `price`
- **`Inventory`** — Uses `ArrayList<Product>` to store products
- **Operations:** `addProduct`, `updateProduct`, `deleteProduct`, `displayProducts`

### Analysis

**Why data structures and algorithms matter for large inventories**

Large inventories require fast lookups, updates, and deletions. Without the right structure, every operation scans the entire catalog, which does not scale.

**Suitable data structures**

| Structure | Use Case |
|-----------|----------|
| `ArrayList` | Sequential storage; good when order matters or dataset is small |
| `HashMap` | O(1) average lookup by `productId` — ideal for frequent updates/deletes by ID |

**Time complexity (current `ArrayList` implementation)**

| Operation | Time Complexity | Notes |
|-----------|-----------------|-------|
| Add | O(1) amortized | Append to end of list |
| Update | O(n) | Linear scan to find product by ID |
| Delete | O(n) | Linear scan + shift on remove |

**Optimization**

Replace `ArrayList` with `HashMap<Integer, Product>` keyed by `productId` to achieve O(1) average time for add, update, and delete by ID.

---

## Exercise 2: E-commerce Platform Search Function

**Scenario:** Product search optimized for performance on an e-commerce platform.

### Implementation

- **`Product`** — Attributes: `productId`, `productName`, `category`
- **`linearSearch`** — Searches an unsorted array sequentially
- **`binarySearch`** — Searches a sorted array by `productId`

### Analysis

**Big O notation**

Big O describes how runtime or space grows with input size *n*, focusing on worst-case growth rather than exact timings.

**Search scenarios**

| Case | Linear Search | Binary Search |
|------|---------------|---------------|
| Best | O(1) — target is first element | O(1) — target is middle element |
| Average | O(n) | O(log n) |
| Worst | O(n) — target absent or last | O(log n) |

**Comparison**

| Algorithm | Time Complexity | Requires Sorted Data |
|-----------|-----------------|----------------------|
| Linear Search | O(n) | No |
| Binary Search | O(log n) | Yes |

**Recommendation:** Binary search is preferred for large, frequently searched catalogs because O(log n) scales far better than O(n). Linear search is acceptable for small or unsorted datasets where sorting overhead is not justified.

---

## Exercise 3: Sorting Customer Orders

**Scenario:** Sort customer orders by `totalPrice` to prioritize high-value orders.

### Implementation

- **`Order`** — Attributes: `orderId`, `customerName`, `totalPrice`
- **`bubbleSort`** — Compares adjacent elements and swaps until sorted
- **`quickSort`** — Partition-based divide-and-conquer sort

### Analysis

**Sorting algorithms overview**

| Algorithm | Time Complexity | Space | Stable |
|-----------|-----------------|-------|--------|
| Bubble Sort | O(n²) | O(1) | Yes |
| Insertion Sort | O(n²) worst, O(n) best | O(1) | Yes |
| Quick Sort | O(n log n) average, O(n²) worst | O(log n) | No |
| Merge Sort | O(n log n) | O(n) | Yes |

**Bubble Sort vs Quick Sort**

| | Bubble Sort | Quick Sort |
|---|-------------|------------|
| Average time | O(n²) | O(n log n) |
| Best case | O(n) | O(n log n) |
| In-place | Yes | Yes |

**Why Quick Sort is preferred:** It performs significantly fewer comparisons on large datasets. Bubble Sort is mainly educational; production systems use Quick Sort, Merge Sort, or built-in library sorts.

---

## Exercise 4: Employee Management System

**Scenario:** Manage employee records using a fixed-size array.

### Implementation

- **`Employee`** — Attributes: `employeeId`, `name`, `position`, `salary`
- **`EmployeeManager`** — Fixed-capacity `Employee[]` with a `size` counter
- **Operations:** `addEmployee`, `searchEmployee`, `traverseEmployees`, `deleteEmployee`

### Analysis

**Array representation in memory**

Arrays store elements in contiguous memory. Each element is accessed by index in O(1) using base address + offset, which enables fast random access and cache-friendly traversal.

**Time complexity**

| Operation | Time Complexity | Notes |
|-----------|-----------------|-------|
| Add | O(1) | Append at `size` index if capacity remains |
| Search | O(n) | Linear scan by `employeeId` |
| Traverse | O(n) | Visit each occupied slot |
| Delete | O(n) | Find element + shift remaining left |

**Limitations of arrays**

- Fixed capacity — resizing requires a new array and copying
- Insert/delete in the middle is O(n) due to shifting
- Best when size is known and changes are infrequent

Use dynamic structures (`ArrayList`, linked lists) when size is unpredictable or frequent insertions/deletions are needed.

---

## Exercise 5: Task Management System

**Scenario:** Dynamic task list with add, search, traverse, and delete operations.

### Implementation

- **`Task`** — Attributes: `taskId`, `taskName`, `status`
- **`Node`** — Wraps a `Task` and a `next` reference
- **`SinglyLinkedList`** — Singly linked list with `addTask`, `searchTask`, `traverse`, `deleteTask`

### Analysis

**Linked list types**

| Type | Structure | Traversal |
|------|-----------|-----------|
| Singly Linked List | Each node points to next only | Forward only |
| Doubly Linked List | Each node points to next and previous | Forward and backward |

**Time complexity (singly linked list)**

| Operation | Time Complexity |
|-----------|-----------------|
| Add (at end) | O(n) — must traverse to tail |
| Search | O(n) |
| Traverse | O(n) |
| Delete | O(n) — find node + update pointer |

**Advantages over arrays for dynamic data**

- No fixed capacity — grow by allocating new nodes
- Insert/delete at a known position is O(1) once the node is found (no shifting)
- Memory grows incrementally with each task

---

## Exercise 6: Library Management System

**Scenario:** Search books by title using linear and binary search.

### Implementation

- **`Book`** — Attributes: `bookId`, `title`, `author`
- **`linearSearch`** — Finds a book by title in an unsorted array
- **`binarySearch`** — Finds a book by title in an array sorted by title

### Analysis

**Time complexity comparison**

| Algorithm | Time Complexity | Sorted Required |
|-----------|-----------------|-----------------|
| Linear Search | O(n) | No |
| Binary Search | O(log n) | Yes |

**When to use each**

| Condition | Recommended Algorithm |
|-----------|----------------------|
| Small collection (< ~50 items) | Linear search — simplicity outweighs cost |
| Large, sorted collection | Binary search |
| Unsorted, infrequent searches | Linear search |
| Frequent searches on large data | Sort once, then binary search (or use a hash map for O(1) lookup) |

---

## Exercise 7: Financial Forecasting

**Scenario:** Predict future value from an initial amount and annual growth rate using recursion.

### Implementation

- **`calculateFutureValueMemoized`** — Recursively applies compound growth:  
  `futureValue = principal × (1 + rate)^years`
- Uses a `HashMap` memo table to cache results and avoid redundant computation

### Analysis

**Recursion**

A recursive method solves a problem by breaking it into smaller subproblems of the same type. A base case (`years == 0`) stops the recursion.

**Time complexity**

| Approach | Time Complexity |
|----------|-----------------|
| Naive recursion (no memo) | O(2^n) — overlapping subproblems |
| Memoized recursion | O(n) — each year computed once |
| Iterative loop | O(n) — equivalent result, no call stack overhead |

**Optimization**

- **Memoization** — Cache computed values (implemented in this project)
- **Iteration** — Replace recursion with a simple loop: `for (int i = 0; i < years; i++) principal *= (1 + rate);`
- **Closed-form formula** — `principal × Math.pow(1 + rate, years)` in O(1)

---

## Key Concepts Summary

| Topic | Exercises | Core Idea |
|-------|-----------|-----------|
| Lists & Maps | 1 | Choose structure based on access pattern |
| Search | 2, 6 | Linear O(n) vs binary O(log n) |
| Sorting | 3 | O(n²) vs O(n log n) algorithms |
| Arrays | 4 | Contiguous memory, fixed capacity |
| Linked Lists | 5 | Dynamic size, pointer-based nodes |
| Recursion | 7 | Divide problem + base case + memoization |

---

## Author

Cognizant Full Stack Engineer — Week 1 Assignment
