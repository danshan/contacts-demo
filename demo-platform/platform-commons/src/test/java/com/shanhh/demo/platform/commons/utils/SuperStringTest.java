/**
 * 
 */
package com.shanhh.demo.platform.commons.utils;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.AssertJUnit.*;

/**
 * @author dan.shan
 *
 */
public class SuperStringTest {

    @Test
    public void testNotNull() {
        assertEquals("", SuperString.notNull(null));
        assertEquals("", SuperString.notNull(""));
        assertEquals("test", SuperString.notNull("test"));
        assertEquals("  test  ", SuperString.notNull("  test  "));
    }

    @Test
    public void testNotNullTrim() {
        assertEquals("", SuperString.notNullTrim(null));
        assertEquals("", SuperString.notNullTrim(""));
        assertEquals("test", SuperString.notNullTrim("test"));
        assertEquals("test", SuperString.notNullTrim("  test "));
    }

    @Test
    public void testIsBlank() {
        assertTrue(SuperString.isBlank(null));
        assertTrue(SuperString.isBlank(""));
        assertTrue(SuperString.isBlank("   "));
        assertFalse(SuperString.isBlank("0"));
    }

    @Test
    public void testGetQuoteDel() {
        assertEquals("test", SuperString.getQuoteDel("\"test\""));
        assertEquals(" test ", SuperString.getQuoteDel(" \"test\" "));
        assertEquals("'test'", SuperString.getQuoteDel("'test'"));
    }

    @Test
    public void testGetInt() {
        assertEquals(0, SuperString.getInt(null));
        assertEquals(0, SuperString.getInt(""));
        assertEquals(123, SuperString.getInt("123"));
        assertEquals(-123, SuperString.getInt("-123"));
        assertEquals(0, SuperString.getInt("abc"));
        
        assertEquals(1, SuperString.getInt("1.2"));
        assertEquals(1, SuperString.getInt("1.2d"));
        assertEquals(1, SuperString.getInt("1.2f"));
        assertEquals(1, SuperString.getInt("1.7d"));
        assertEquals(1, SuperString.getInt("1.7f"));
        
        assertEquals(1, SuperString.getInt("abc", 1));
        assertEquals(123, SuperString.getInt("123", 1));
    }

    @Test
    public void testGetLong() {
        assertEquals(0L, SuperString.getLong(null));
        assertEquals(0L, SuperString.getLong(""));
        assertEquals(123L, SuperString.getLong("123"));
        assertEquals(-123L, SuperString.getLong("-123"));
        assertEquals(0L, SuperString.getLong("abc"));
        
        assertEquals(1L, SuperString.getLong("1.2"));
        assertEquals(1L, SuperString.getLong("1.2d"));
        assertEquals(1L, SuperString.getLong("1.2f"));
        assertEquals(1L, SuperString.getLong("1.7d"));
        assertEquals(1L, SuperString.getLong("1.7f"));
        
        assertEquals(1L, SuperString.getLong("abc", 1));
        assertEquals(123L, SuperString.getLong("123", 1));
    }

    @Test
    public void testGetFloat() {
        assertTrue(Math.abs(0F - SuperString.getFloat(null)) < 0.1F);
        assertTrue(Math.abs(0F - SuperString.getFloat("")) < 0.1F);
        assertTrue(Math.abs(12.3F - SuperString.getFloat("12.3")) < 0.1F);
        assertTrue(Math.abs(-12.3F - SuperString.getFloat("-12.3")) < 0.1F);
        assertTrue(Math.abs(0F - SuperString.getFloat("abc")) < 0.1F);
    }

    @Test
    public void testGetDouble() {
        assertTrue(Math.abs(0D - SuperString.getDouble(null)) < 0.1D);
        assertTrue(Math.abs(0D - SuperString.getDouble("")) < 0.1D);
        assertTrue(Math.abs(12.3D - SuperString.getDouble("12.3d")) < 0.1D);
        assertTrue(Math.abs(-12.3D - SuperString.getDouble("-12.3")) < 0.1D);
        assertTrue(Math.abs(0D - SuperString.getDouble("abc")) < 0.1D);
        assertTrue(Math.abs(1.2D - SuperString.getDouble("abc", 1.2D)) < 0.1D);
    }

    @Test
    public void testGetRandString() {
        assertTrue(SuperString.getRandString(4, 1).length() == 1);
        int length = SuperString.getRandString(3, 4).length();
        assertTrue(length == 3 || length == 4);
    }

    @Test
    public void testIsNumeric() {
        assertTrue(SuperString.isNumeric("1.1D", SuperString.NUMERIC_DOUBLE));
        assertTrue(SuperString.isNumeric("1.1F", SuperString.NUMERIC_FLOAT));
        assertTrue(SuperString.isNumeric("1", SuperString.NUMERIC_INT));
        assertTrue(SuperString.isNumeric("1", SuperString.NUMERIC_LONG));
        assertTrue(SuperString.isNumeric("1", SuperString.NUMERIC_SHORT));
        assertFalse(SuperString.isNumeric("111111111111111111111", SuperString.NUMERIC_INT));
        assertFalse(SuperString.isNumeric("abc", SuperString.NUMERIC_INT));
    }

    @Test
    public void testReplaceTemplateTag() {
        assertEquals("hello world", SuperString.replaceTemplateTag("hello ${name}", "name", "world"));
        Map<String, String> replace = new HashMap<String, String>();
        replace.put("name", "Dan");
        replace.put("site", "Vipshop");
        assertEquals("hello Dan, welcome to Vipshop", 
                SuperString.replaceTemplateTag("hello ${name}, welcome to ${site}", replace));
    }

    @Test
    public void testClearText() {
        assertEquals("abcdef", SuperString.clearText("a\'b\"c\rd\ne\tf"));
    }

    @Test
    public void testStrToList() {
        List<String> list = SuperString.strToList("axbxcdxe", "x");
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("cd", list.get(2));
        assertEquals("e", list.get(3));
    }
}
