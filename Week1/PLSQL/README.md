# Week 2 — PL/SQL Programming

Oracle PL/SQL assignments for a banking domain: control structures, error handling, stored procedures, functions, triggers, cursors, and packages. All solutions are in a single script with sample schema, data, and execution examples.

## Prerequisites

- Oracle Database (11g or later) or Oracle SQL Developer
- SQL*Plus or SQL Developer worksheet
- Basic SQL knowledge (DDL, DML, joins)

## Project Structure

```
Week1/PLSQL/
├── WEEK 2 PLSQL.sql       # Complete script (schema + all exercises)
├── Week 2_PLSQL.docx      # Assignment document
└── OUTPUT/                # Screenshot outputs for each scenario
    ├── 01 CREATE CUSTOMER TABLE.jpg
    ├── ...
    └── 31 EXERCISE 7 SCENARIO 3.jpg
```

---

## Database Schema

The script creates five core tables for a banking application:

| Table | Purpose | Key Columns |
|-------|---------|-------------|
| `CUSTOMERS` | Customer records | `CUSTOMERID`, `NAME`, `DOB`, `BALANCE`, `LASTMODIFIED`, `ISVIP` |
| `ACCOUNTS` | Customer bank accounts | `ACCOUNTID`, `CUSTOMERID`, `ACCOUNTTYPE`, `BALANCE` |
| `TRANSACTIONS` | Account transactions | `TRANSACTIONID`, `ACCOUNTID`, `AMOUNT`, `TRANSACTIONTYPE` |
| `LOANS` | Customer loans | `LOANID`, `CUSTOMERID`, `LOANAMOUNT`, `INTERESTRATE`, `ENDDATE` |
| `EMPLOYEES` | Bank staff | `EMPLOYEEID`, `NAME`, `POSITION`, `SALARY`, `DEPARTMENT` |

**Relationships**

```
CUSTOMERS ──< ACCOUNTS ──< TRANSACTIONS
    │
    └──< LOANS
```

Additional objects created during exercises:

| Object | Exercise | Purpose |
|--------|----------|---------|
| `AUDITLOG` | Exercise 5 | Audit trail for transactions |
| `AUDITLOG_SEQ` | Exercise 5 | Sequence for audit log IDs |
| `CustomerManagement` | Exercise 7 | Customer CRUD package |
| `EmployeeManagement` | Exercise 7 | Employee management package |
| `AccountOperations` | Exercise 7 | Account operations package |

---

## How to Run

1. Open **Oracle SQL Developer** or **SQL*Plus**.
2. Connect to your Oracle database schema.
3. Run the schema and sample data sections first (lines 1–79 in `WEEK 2 PLSQL.sql`).
4. Enable server output before running PL/SQL blocks:

```sql
SET SERVEROUTPUT ON;
```

5. Execute each exercise section in order — later exercises depend on objects created earlier.

**Tip:** Run sections individually rather than the entire file at once, since some exercises alter table structure (e.g., adding `ISVIP` column) or depend on prior procedure/trigger creation.

| Section | Lines (approx.) | Topic |
|---------|-----------------|-------|
| Schema & Data | 1–79 | Tables and INSERT statements |
| Exercise 1 | 85–196 | Control Structures |
| Exercise 2 | 198–342 | Error Handling |
| Exercise 3 | 344–460 | Stored Procedures |
| Exercise 4 | 462–598 | Functions |
| Exercise 5 | 600–702 | Triggers |
| Exercise 6 | 704–821 | Cursors |
| Exercise 7 | 823–1034 | Packages |

---

## Exercise 1: Control Structures

**Scenario:** Apply business rules using PL/SQL loops, cursors, and conditional logic.

### Scenario 1 — Senior Citizen Loan Discount

Loop through all customers, calculate age from `DOB`, and apply a **1% discount** to loan interest rates for customers **above 60 years old**.

**Key concepts:** `FOR` loop with cursor, `IF-ELSE`, `UPDATE`, `COMMIT`

```sql
FOR CUSTOMER_RECORD IN CUSTOMER_CURSOR LOOP
    IF VAR_AGE > 60 THEN
        UPDATE LOANS SET INTERESTRATE = INTERESTRATE - 1
        WHERE CUSTOMERID = VAR_CUSTOMER_ID;
    END IF;
END LOOP;
```

### Scenario 2 — VIP Customer Flag

Add an `ISVIP` column to `CUSTOMERS`. Iterate through customers and set `ISVIP = 'TRUE'` for balances **over $10,000**, otherwise `'FALSE'`.

**Key concepts:** `ALTER TABLE`, `CHECK` constraint, cursor loop, conditional update

### Scenario 3 — Loan Due Reminders

Fetch loans due within the **next 30 days** using an explicit cursor and print a reminder message for each customer.

**Key concepts:** Explicit cursor (`OPEN` / `FETCH` / `CLOSE`), `JOIN`, date range filter, `DBMS_OUTPUT`

---

## Exercise 2: Error Handling

**Scenario:** Write stored procedures with robust exception handling for banking operations.

### Scenario 1 — `SafeTransferFunds`

Transfer funds between two accounts with row-level locking (`FOR UPDATE`). Roll back and log errors on failure (e.g., insufficient funds).

| Exception Handling | Action |
|--------------------|--------|
| Insufficient funds | `RAISE_APPLICATION_ERROR(-20001, ...)` |
| Any other error | `ROLLBACK` + log `SQLERRM` |

```sql
EXEC SAFETRANSFERFUNDS(2, 1, 500);
```

### Scenario 2 — `UpdateSalary`

Increase an employee's salary by a given percentage. Handle `NO_DATA_FOUND` when the employee ID does not exist.

| Exception | Handler |
|-----------|---------|
| `NO_DATA_FOUND` | Log "Employee ID does not exist" |
| `OTHERS` | `ROLLBACK` + log error |

```sql
EXEC UPDATESALARY(1, 5);   -- 5% raise
EXEC UPDATESALARY(2, 3);   -- 3% raise
```

### Scenario 3 — `AddNewCustomer`

Insert a new customer. Catch `DUP_VAL_ON_INDEX` when the customer ID already exists and prevent duplicate insertion.

```sql
EXEC ADDNEWCUSTOMER(3, 'ARKA PRATIM GHOSH', '21-10-2002', 50000);
```

---

## Exercise 3: Stored Procedures

**Scenario:** Encapsulate reusable banking operations as stored procedures.

### Scenario 1 — `ProcessMonthlyInterest`

Apply **1% monthly interest** to all savings accounts.

```sql
UPDATE ACCOUNTS
SET BALANCE = BALANCE * 1.01
WHERE ACCOUNTTYPE = 'Savings';
```

### Scenario 2 — `UpdateEmployeeBonus`

Apply a bonus percentage to all employees in a given department.

```sql
EXEC UPDATEEMPLOYEEBONUS('IT', 5);
EXEC UPDATEEMPLOYEEBONUS('HR', 3);
```

### Scenario 3 — `TransferFunds`

Transfer a specified amount between accounts after verifying sufficient balance in the source account.

```sql
EXEC TRANSFERFUNDS(1, 2, 100);
```

**Procedures created in this exercise**

| Procedure | Purpose |
|-----------|---------|
| `ProcessMonthlyInterest` | Apply 1% interest to savings accounts |
| `UpdateEmployeeBonus` | Department-wide salary bonus |
| `TransferFunds` | Account-to-account transfer with balance check |

---

## Exercise 4: Functions

**Scenario:** Create reusable functions that return computed values.

### Scenario 1 — `CalculateAge`

Takes a date of birth and returns age in years.

```sql
V_AGE := TRUNC((SYSDATE - P_DOB) / 365);
```

Used in an anonymous block with a cursor to print each customer's age.

### Scenario 2 — `CalculateMonthlyInstallment`

Computes monthly loan installment using the standard amortization formula:

```
M = P × r / (1 - (1 + r)^(-n))
```

Where `P` = loan amount, `r` = monthly interest rate, `n` = number of payments.

Handles zero-interest edge case separately.

### Scenario 3 — `HasSufficientBalance`

Returns `BOOLEAN` — `TRUE` if the account balance is at least the requested amount.

```sql
-- Prompts for account ID and amount at runtime
V_HAS := HASSUFFICIENTBALANCE(V_ACCOUNTID, V_AMOUNT);
```

**Note:** `BOOLEAN` return types cannot be used directly in SQL SELECT statements — they are intended for use inside PL/SQL blocks.

---

## Exercise 5: Triggers

**Scenario:** Automate auditing, timestamps, and business rule enforcement at the database level.

### Scenario 1 — `UpdateCustomerLastModified`

**Type:** `BEFORE UPDATE` on `CUSTOMERS`

Automatically sets `LASTMODIFIED := SYSDATE` whenever a customer record is updated.

```sql
UPDATE CUSTOMERS SET NAME = 'JOHN DOE' WHERE CUSTOMERID = 1;
```

### Scenario 2 — `LogTransactions`

**Type:** `AFTER INSERT` on `TRANSACTIONS`

Inserts a copy of each new transaction into the `AUDITLOG` table using `AUDITLOG_SEQ`.

| AuditLog Column | Source |
|-----------------|--------|
| `LOGID` | `AUDITLOG_SEQ.NEXTVAL` |
| Transaction fields | `:NEW` trigger variables |
| `LOGTIMESTAMP` | `SYSDATE` (default) |

### Scenario 3 — `CheckTransactionRules`

**Type:** `BEFORE INSERT` on `TRANSACTIONS`

Enforces business rules before a transaction is saved:

| Rule | Validation |
|------|------------|
| Withdrawal | Amount must not exceed account balance |
| Deposit | Amount must be positive |
| Invalid type | `RAISE_APPLICATION_ERROR(-20003, ...)` |

---

## Exercise 6: Cursors

**Scenario:** Process row-by-row data using explicit cursors in anonymous PL/SQL blocks.

### Scenario 1 — `GenerateMonthlyStatements`

Cursor joins `CUSTOMERS`, `ACCOUNTS`, and `TRANSACTIONS` to retrieve all transactions for the **current month** and print a statement per customer.

```sql
WHERE TRUNC(T.TRANSACTIONDATE, 'MM') = TRUNC(SYSDATE, 'MM')
```

### Scenario 2 — `ApplyAnnualFee`

Cursor iterates all accounts and deducts a **$50 annual maintenance fee** from each balance.

```sql
V_ANNUAL_FEE CONSTANT NUMBER := 50;
UPDATE ACCOUNTS SET BALANCE = BALANCE - V_ANNUAL_FEE ...
```

### Scenario 3 — `UpdateLoanInterestRates`

Cursor fetches all loans and applies a **2% policy increase** to each interest rate using a local function:

```sql
RETURN OLD_RATE * (1 + (V_NEW_POLICY / 100));
```

**Cursor types used**

| Type | Used In |
|------|---------|
| Implicit (`FOR` loop) | Exercise 1 |
| Explicit (`OPEN`/`FETCH`/`CLOSE`) | Exercises 1, 4, 6 |

---

## Exercise 7: Packages

**Scenario:** Group related procedures and functions into modular, reusable packages with separate specification and body.

### Scenario 1 — `CustomerManagement`

| Member | Type | Purpose |
|--------|------|---------|
| `AddNewCustomer` | Procedure | Insert new customer |
| `UpdateCustomerDetails` | Procedure | Update name, DOB, balance |
| `GetCustomerBalance` | Function | Return customer balance by ID |

### Scenario 2 — `EmployeeManagement`

| Member | Type | Purpose |
|--------|------|---------|
| `HireEmployee` | Procedure | Insert new employee |
| `UpdateEmployeeDetails` | Procedure | Update employee record |
| `CalculateAnnualSalary` | Function | Return `salary × 12` |

### Scenario 3 — `AccountOperations`

| Member | Type | Purpose |
|--------|------|---------|
| `OpenNewAccount` | Procedure | Create a new account |
| `CloseAccount` | Procedure | Delete an account |
| `GetTotalBalance` | Function | Sum balances across all customer accounts |

**Package structure**

```sql
CREATE OR REPLACE PACKAGE PackageName IS
    -- Specification: public interface
END PackageName;
/

CREATE OR REPLACE PACKAGE BODY PackageName IS
    -- Body: implementation
END PackageName;
/
```

**Benefits of packages**

| Benefit | Description |
|---------|-------------|
| Modularity | Related logic grouped together |
| Encapsulation | Hide internal implementation details |
| Performance | Loaded into memory once per session |
| Maintainability | Change body without altering specification |

---

## PL/SQL Concepts Summary

| Concept | Exercises | Description |
|---------|-----------|-------------|
| Control Structures | 1 | `IF`, loops, cursors for conditional logic |
| Exception Handling | 2 | `EXCEPTION` blocks, `RAISE_APPLICATION_ERROR`, `ROLLBACK` |
| Stored Procedures | 2, 3 | Named reusable blocks with `CREATE OR REPLACE PROCEDURE` |
| Functions | 4 | Named blocks that return a value |
| Triggers | 5 | Automatic actions on DML events (`BEFORE`/`AFTER`) |
| Cursors | 1, 4, 6 | Row-by-row processing of query results |
| Packages | 7 | Grouped spec + body for modular design |

---

## Key Oracle Features Used

| Feature | Usage |
|---------|-------|
| `%TYPE` / `%ROWTYPE` | Anchor variables to table column/row types |
| `DBMS_OUTPUT.PUT_LINE` | Console output in PL/SQL blocks |
| `SYSDATE` | Current date/time |
| `FOR UPDATE` | Row-level lock during transfers |
| `RAISE_APPLICATION_ERROR` | Custom error codes and messages |
| `DUP_VAL_ON_INDEX` | Handle duplicate primary key inserts |
| `NO_DATA_FOUND` | Handle missing records in SELECT INTO |
| `SEQUENCE` | Auto-increment audit log IDs |
| `:NEW` / `:OLD` | Trigger pseudo-records for row data |

---

## Sample Data

The script includes seed data for testing:

| Table | Sample Records |
|-------|----------------|
| `CUSTOMERS` | John Doe (ID 1), Jane Smith (ID 2) |
| `ACCOUNTS` | Savings (ID 1), Checking (ID 2) |
| `TRANSACTIONS` | Deposit $200, Withdrawal $300 |
| `LOANS` | $5,000 loan at 5% interest |
| `EMPLOYEES` | Alice Johnson (HR), Bob Brown (IT) |

---

## Author

Cognizant Full Stack Engineer — Week 2 Assignment
