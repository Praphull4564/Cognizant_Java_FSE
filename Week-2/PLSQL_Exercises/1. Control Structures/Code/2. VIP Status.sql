SET SERVEROUTPUT ON;

CREATE TABLE Customers (
  CustomerID NUMBER PRIMARY KEY,
  Name VARCHAR2(100),
  DOB DATE,
  Balance NUMBER,
  LastModified DATE
);

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified) 
VALUES (1, 'John Doe', TO_DATE('1985-05-15', 'YYYY-MM-DD'), 1000, SYSDATE); 

-- SELECT * FROM Customers;

ALTER TABLE Customers ADD IsVIP VARCHAR2(5);

BEGIN
  FOR cust IN (SELECT CustomerID, Balance FROM Customers) LOOP
    IF cust.Balance > 10000 THEN
      UPDATE Customers
      SET IsVIP = 'TRUE'
      WHERE CustomerID = cust.CustomerID;
      
      DBMS_OUTPUT.PUT_LINE('CustomerID: ' || cust.CustomerID || ' promoted to VIP.');
    ELSE
      UPDATE Customers
      SET IsVIP = 'FALSE'
      WHERE CustomerID = cust.CustomerID;
      
      DBMS_OUTPUT.PUT_LINE('CustomerID: ' || cust.CustomerID || '  is not a VIP.');
    END IF;
  END LOOP;
  COMMIT;
END;
/

-- SELECT * FROM Customers;

