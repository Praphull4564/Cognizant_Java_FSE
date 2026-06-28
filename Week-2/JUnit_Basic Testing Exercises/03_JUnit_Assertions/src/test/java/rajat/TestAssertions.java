package rajat;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestAssertions {

    @Test
    public void testAssertions() {
        assertEquals(5, 2 + 3);
        System.out.println("assertEquals Successful");

        assertTrue(5 > 3);
        System.out.println("assertTrue Successful");

        assertFalse(5 < 3);
        System.out.println("assertFalse Successful");

        assertNull(null);
        System.out.println("assertNull Successful");

        assertNotNull(new Object());
        System.out.println("assertNotNull Successful");
    }
}
