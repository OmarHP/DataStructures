import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private PriorityQueue<Worker> workersQueue;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        workersQueue = new PriorityQueue<>((Worker w1, Worker w2) -> {
            if (w1.nextFreeTime == w2.nextFreeTime) {
                return Integer.compare(w1.id, w2.id);
            } else{
                return Long.compare(w1.nextFreeTime,w2.nextFreeTime);
            }
        });
        for (int i = 0; i < numWorkers; i++) {
            workersQueue.add(new Worker(i));
        }
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
//        TODO: replace this code with a faster algorithm. (replaced)
//        assignedWorker = new int[jobs.length];
//        startTime = new long[jobs.length];
//        long[] nextFreeTime = new long[numWorkers];
//        for (int i = 0; i < jobs.length; i++) {
//            int duration = jobs[i];
//            int bestWorker = 0;
//            for (int j = 0; j < numWorkers; ++j) {
//                if (nextFreeTime[j] < nextFreeTime[bestWorker])
//                    bestWorker = j;
//            }
//            assignedWorker[i] = bestWorker;
//            startTime[i] = nextFreeTime[bestWorker];
//            nextFreeTime[bestWorker] += duration;
//        }

        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];

        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            Worker bestWorker = workersQueue.poll();
            assignedWorker[i] = bestWorker.getId();
            startTime[i] = bestWorker.getNextFreeTime();
            bestWorker.setNextFreeTime(startTime[i] + duration);
            workersQueue.add(bestWorker);
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private class Worker {

        private int id;
        private long nextFreeTime;

        public Worker(int id) {
            this.id = id;
            this.nextFreeTime = 0;
        }

        public int getId() {
            return this.id;
        }

        public long getNextFreeTime() {
            return nextFreeTime;
        }

        public void setNextFreeTime(long t) {
            this.nextFreeTime = t;
        }

    }


}
