package com.rajat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MyServiceTest {

    @Test
    public void testExternalApi() {
        ExternalApi mockApi = mock(ExternalApi.class);
        when(mockApi.getData()).thenReturn("Hello from Rajat!!!");
        MyService service = new MyService(mockApi);
        String result = service.fetchData();
        assertEquals("Hello from Rajat!!!", result);
    }
}
