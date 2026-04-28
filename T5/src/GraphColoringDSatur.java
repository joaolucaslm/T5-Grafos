public class GraphColoringDSatur {
    private final Graph graph;
    private int[] color;         // color[v] = cor atribuida ao vertice v (-1 = nao colorido)
    private int[] coloringOrder; // ordem em que os vertices foram coloridos
    private int colorCount;      // total de cores utilizadas

    public GraphColoringDSatur(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph nao pode ser nulo");
        }
        this.graph = graph;
        this.color = new int[graph.V()];
        this.coloringOrder = new int[graph.V()];
        this.colorCount = 0;
        for (int v = 0; v < graph.V(); v++) {
            color[v] = -1;
        }
    }

    public Graph getGraph() {
        return graph;
    }

    public void color() {
        int V = graph.V();

        // Passo 1: colorir o vertice de maior grau com cor 1
        int start = 0;
        for (int v = 1; v < V; v++) {
            if (graph.degree(v) > graph.degree(start)) start = v;
        }
        color[start] = 1;
        coloringOrder[0] = start;
        colorCount = 1;

        // Passo 2: colorir os demais vertices
        for (int step = 1; step < V; step++) {
            // Escolher vertice nao colorido com maior saturacao (desempate: maior grau, depois menor indice)
            int chosen = -1;
            int maxSat = -1;
            int maxDeg = -1;
            for (int v = 0; v < V; v++) {
                if (color[v] != -1) continue;
                int sat = calcularSaturacao(v);
                int deg = graph.degree(v);
                if (sat > maxSat
                        || (sat == maxSat && deg > maxDeg)
                        || (sat == maxSat && deg == maxDeg && (chosen == -1 || v < chosen))) {
                    maxSat = sat;
                    maxDeg = deg;
                    chosen = v;
                }
            }

            // Atribuir a menor cor disponivel para chosen
            Bag<Integer> coresVizinhos = new Bag<>();
            for (int w : graph.adj(chosen)) {
                if (color[w] != -1) coresVizinhos.add(color[w]);
            }
            int k = 1;
            while (bagContains(coresVizinhos, k)) k++;

            color[chosen] = k;
            coloringOrder[step] = chosen;
            if (k > colorCount) colorCount = k;
        }
    }

    private int calcularSaturacao(int v) {
        Bag<Integer> coresVizinhos = new Bag<>();
        for (int w : graph.adj(v)) {
            if (color[w] != -1 && !bagContains(coresVizinhos, color[w]))
                coresVizinhos.add(color[w]);
        }
        return coresVizinhos.size();
    }

    private boolean bagContains(Bag<Integer> bag, int value) {
        for (int x : bag) {
            if (x == value) return true;
        }
        return false;
    }

    public int getColor(int vertex) {
        return color[vertex];
    }

    public int getColorCount() {
        return colorCount;
    }

    public int[] getColoringOrder() {
        return coloringOrder.clone();
    }

    public boolean isValidColoring() {
        for (int v = 0; v < graph.V(); v++) {
            for (int w : graph.adj(v)) {
                if (color[v] == color[w]) return false;
            }
        }
        return true;
    }

    public String getLabel(int vertex) {
        String[] labels = {
                "AC", "AL", "AM", "AP", "BA", "CE", "DF",
                "ES", "GO", "MA", "MG", "MS", "MT", "PA",
                "PB", "PE", "PI", "PR", "RJ", "RN", "RO",
                "RR", "RS", "SC", "SE", "SP", "TO"
        };
        if (graph.V() == labels.length) {
            return labels[vertex];
        }
        return String.valueOf(vertex);
    }
}