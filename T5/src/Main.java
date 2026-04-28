public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException(
                    "informe o arquivo de entrada. Ex.: java Main ../dados/brasil.txt"
            );
        }

        In in = new In(args[0]);
        Graph graph = new Graph(in);
        GraphColoringDSatur dsatur = new GraphColoringDSatur(graph);

        StdOut.println("Grafo carregado:");
        StdOut.println(graph);
        StdOut.println();

        dsatur.color();

        StdOut.println("Ordem de coloracao:");
        int[] order = dsatur.getColoringOrder();
        for (int i = 0; i < order.length; i++) {
            int v = order[i];
            StdOut.printf("Passo %2d: vertice %2d (%s) -> cor %d%n",
                    i + 1, v, dsatur.getLabel(v), dsatur.getColor(v));
        }
        StdOut.println();

        StdOut.println("Cores finais:");
        for (int v = 0; v < graph.V(); v++) {
            StdOut.printf("Vertice %2d (%s): cor %d%n",
                    v, dsatur.getLabel(v), dsatur.getColor(v));
        }
        StdOut.println();

        int totalColors = dsatur.getColorCount();
        StdOut.printf("Total de cores utilizadas: %d%n", totalColors);
        StdOut.println();

        boolean valid = dsatur.isValidColoring();
        StdOut.printf("Coloracao valida: %s%n", valid ? "SIM" : "NAO");
    }
}