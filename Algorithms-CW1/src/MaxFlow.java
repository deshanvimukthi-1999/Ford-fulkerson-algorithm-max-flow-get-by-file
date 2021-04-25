import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;


class MaxFlow {
    static final int V = 6;

    //BFS search algorithm
    boolean bfs(int Graph[][], int source, int target, int parent[]) {
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i)
            visited[i] = false;

        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (visited[v] == false && Graph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        return (visited[target] == true);
    }

    // Implement FordFulkerson algorithm
    int fordFulkerson(int graph[][], int source, int target) {
        int u, v;
        int Graph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                Graph[u][v] = graph[u][v];

        int p[] = new int[V];

        int maxFlow = 0;

    // Update residual values of edges
        while (bfs(Graph, source, target, p)) {
            int pathFlow = Integer.MAX_VALUE;
            for (v = target; v != source; v = p[v]) {
                u = p[v];
                pathFlow = Math.min(pathFlow, Graph[u][v]);
            }

            for (v = target; v != source; v = p[v]) {
                u = p[v];
                Graph[u][v] -= pathFlow;
                Graph[v][u] += pathFlow;
            }

            // Add path flows
            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    private static void print(int[][] e) {
        for (int i = 0; i < e.length; i++) {
            for (int j = 0; j < e[i].length; j++)
                System.out.print(e[i][j] + " ");
            System.out.println();
        }
    }
    private static int nodes;
    private static int[][] mainGraph;
    public static void main(String[] args) throws IOException {

        //Getting System start time
        long start = System.nanoTime();

        BufferedReader br = new BufferedReader(new FileReader("src/bridge_1.txt"));
        Scanner read = new Scanner(br);
        int count = 0;
        while (read.hasNext()) {
            String record = read.nextLine();
            Scanner scanner= new Scanner(record);
            scanner.useDelimiter(" ");
            if (count==0){
                nodes = scanner.nextInt();
                mainGraph = new int[nodes][nodes];
                for (int i = 0 ; i < nodes ; i++){
                    for (int v = 0 ; v < nodes ; v++){
                        mainGraph[i][v] = 0;
                    }
                }
            }else{
                int source = scanner.nextInt();
                int target = scanner.nextInt();
                int capacity = scanner.nextInt();

                mainGraph[source][target] = capacity;
            }
            count++;
        }
//             int[][] edges;
//
//        int count1 = 0;
//
//            Scanner reader = new Scanner("./src/bridge_1.txt");
//            reader.nextLine();// Skips the first line (nameList)
//
//            // Calculate total number of 2 element arrays
//            while (reader.hasNextLine()) {
//                String passer = reader.nextLine();
//                count1 += passer.split(" ").length - 1;
//            }
//            reader.close();
//
//            edges = new int[count1][];
//            int i = 0;
//            reader = new Scanner("./src/bridge_1.txt");
//            reader.nextLine();// Skips the first line (nameList)
//            while (reader.hasNextLine()) {
//                String passer = reader.nextLine();
//
//                String[] split = passer.split(" ");
//                for (int j = 1; j < split.length; j++) {
//                    edges[i + j - 1] = new int[nodes];
//                    edges[i + j - 1][0] = Integer.parseInt(split[0]);
//                   edges[i + j - 1][1] = Integer.parseInt(split[j]);
//                }
//                i += split.length - 1;
//            }
//            reader.close();
//            print(edges);


        //Initializing MaxFLow object
        MaxFlow m = new MaxFlow();
        System.out.println("Adjacency Matrix  : ");
        print(mainGraph);
        System.out.println();
        System.out.println("Max Flow is : " + m.fordFulkerson(mainGraph, 0, 5));


        long end = System.nanoTime();

         //Calculating Elapsed time of the program
         // Elapsed time = End -  Start
        double elapsedNano = (end- start);
        double elapsedMilli = elapsedNano/1000000;
        System.out.println();
        System.out.println("Elapsed time: = " + elapsedNano + " ns");
        System.out.println("Elapsed time: = " + elapsedMilli + " ms");
        

    }
}
