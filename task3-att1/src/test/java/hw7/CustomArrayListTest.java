package hw7;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomArrayListTest {

    @Test
    void newListIsEmpty() {
        CustomList<String> list = new CustomArrayList<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void addAndGetWorkAndExpandBeyondTen() {
        CustomList<Integer> list = new CustomArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(i);
        }
        assertFalse(list.isEmpty());
        assertEquals(15, list.size());
        assertEquals(0, list.get(0));
        assertEquals(7, list.get(7));
        assertEquals(14, list.get(14));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3, 5})
    void invalidIndexThrowsException(int badIndex) {
        CustomList<String> list = new CustomArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(badIndex));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(badIndex));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void removeFirstMiddleAndLastWorks(int index) {
        CustomList<String> list = new CustomArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String expected = list.get(index);
        assertEquals(expected, list.remove(index));
        assertEquals(2, list.size());
    }

    @Test
    void addNullThrowsException() {
        CustomList<String> list = new CustomArrayList<>();
        assertThrows(NullPointerException.class, () -> list.add(null));
    }

    @Test
    void iteratorWorksAndThrowsAtEnd() {
        CustomList<String> list = new CustomArrayList<>();
        list.add("x");
        list.add("y");
        Iterator<String> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("x", it.next());
        assertEquals("y", it.next());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, () -> it.next());
    }
}