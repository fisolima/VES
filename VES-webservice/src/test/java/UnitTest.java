/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.ves.helpers.JsonSerialization;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 *
 * @author pippo
 */
public class UnitTest {
    
    class TestClass
    {
        public String stringValue;
        public int intValue;

        public TestClass() {
            stringValue = "value";
            intValue = 100;
        }
    }
    
    public UnitTest() {
    }
    
    @Test
    public void JUnit_Works()
    {
        assertEquals(true, true);
    }
    
    @Test
    public void JsonHelper_ParseObject()
    {       
        
        
        TestClass obj = JsonSerialization.<TestClass>ParseObject("{\"stringValue\":\"value\",\"intValue\":100}",TestClass.class);
        
        assertEquals("value", obj.stringValue);
        assertEquals(100, obj.intValue);
    }
}
