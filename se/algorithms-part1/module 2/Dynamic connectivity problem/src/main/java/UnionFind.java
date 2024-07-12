public class UnionFind {
    private int[] parent;  // tablica reprezentująca połączenia
    private int count;     // liczba rozłącznych zbiorów

    public UnionFind(int n) {
        // Konstruktor - inicjalizacja struktury
        parent = new int [n];
        count = n; // na początku każdy element jest osobnym zbiorem

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        // Indeks:  0  1  2  3  4
        // parent: [0, 1, 2, 3, 4]

        // Indeks:  0  1  2  3  4
        // parent: [0, 0, 2, 0, 4]

        // Indeks na który nie mamy wpływu, traktujemy jak wartość wierzchołka
        // Zaś wartośc tablicy traktujemy jako oznaczenie, do którego zbioru nalezy ten wierzchołek
    }

    // łączy dwa elementy
    public void union(int p, int q){
        if( p < 0 || p >= parent.length)return;
        else if( q < 0 || q >= parent.length)return;

        int rootP = find(p); // pobieram info o zbiorze
        int rootQ = find(q); // pobieram info o zbiorze
        if (rootP != rootQ) {// zbiory nie są takie same
            parent[rootQ] = rootP;
            count--;
        }
    }

    // znajduje reprezentnta dla danego elementu
    public int find(int p) {
        if (p != parent[p]) {
            parent[p] = find(parent[p]);  // kompresja ścieżki
        }
        return parent[p];
    }

    // sprawdza czy dwa elementy są połączone
    public boolean isConnected(int p, int q){
        return find(p) == find(q);
    }
    
    // zwraca liczbę rozłącznych zbiorów
    public int getCount() {
        return count;
    }
}
