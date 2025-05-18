import org.junit.Test;
import static org.junit.Assert.*;

public class UnionFindTest {

    @Test
    public void testUnionFind() {
        UnionFind uf = new UnionFind(5);
        
        // Test początkowego stanu
        assertEquals(5, uf.getCount());
        assertFalse(uf.isConnected(0, 1));
        
        // Test union
        uf.union(0, 1);
        assertTrue(uf.isConnected(0, 1));
        assertEquals(4, uf.getCount());
        
        // Test find
        assertEquals(uf.find(0), uf.find(1));
        
        // Test większej liczby połączeń
        uf.union(1, 2);
        uf.union(3, 4);
        assertTrue(uf.isConnected(0, 2));
        assertFalse(uf.isConnected(0, 3));
        assertEquals(2, uf.getCount());
    }
}