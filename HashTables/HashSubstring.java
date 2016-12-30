import java.io.*;
import java.util.*;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    //RabinKarp algorithm
    private static List<Integer> getOccurrences(Data input) {

        int prime = 1000000007;
        int x= new Random().nextInt(prime-1)+1;
        List<Integer> occurrences = new ArrayList<Integer>();
        int pHash=polyHash(input.pattern,prime,x);
        int[] h=precomputeHashes(input.text, input.pattern.length(), prime, x);
        for(int i=0; i<=input.text.length()-input.pattern.length(); i++){
            if(pHash==h[i]){
                if(input.text.substring(i,i+input.pattern.length()).equals(input.pattern))
                    occurrences.add(i);
            }
        }
        return occurrences;
    }

    private static int polyHash(String str, int p, int x) {
        int hash = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            hash = (int)(((long)hash * x + str.charAt(i)) % p);
        }
        return hash;
    }

    private static int[] precomputeHashes(String text, int patternLength, int prime, int multiplier) {
        int[] h = new int[text.length() - patternLength + 1];
        String sub = text.substring(text.length() - patternLength, text.length());
        h[text.length() - patternLength] = polyHash(sub, prime, multiplier);
        int y = 1;
        for (int i = 1; i <= patternLength; i++)
            y = (int)(((long)y * multiplier) % prime);
        for (int i = text.length() - patternLength - 1; i >= 0; i--) {
//            long a = multiplier *(long)h[i+1];
//            long b=text.charAt(i);
//            long c=text.charAt(i+patternLength);
//            long d=-y*c;
//            h[i]=(int)((((a+b+d)%prime)+prime)%prime);
            h[i] = (int) (((multiplier * (long) h[i + 1] + (long) text.charAt(i) - (long) y * text.charAt(i + patternLength)) % prime+prime)%prime);
        }
        return h;
    }

    static class Data {
        String pattern;
        String text;

        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
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
}

