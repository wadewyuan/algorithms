import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {

    private Map<String, List<Integer>> nouns;
    private Map<Integer, String> synonyms;
    private Digraph g;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        // load the synsets
        In in = new In(synsets);
        nouns = new HashMap<String, List<Integer>>();
        synonyms = new HashMap<Integer, String>();
        while (in.hasNextLine()) {
            String line = in.readLine();
            int id = Integer.parseInt(line.substring(0, line.indexOf(',')));
            line = line.substring(line.indexOf(',') + 1);
            String synonym = line.substring(0, line.indexOf(','));
            String[] nounsInLine = synonym.split(" ");
            for (String noun : nounsInLine) {
                List<Integer> ids = nouns.get(noun);
                if (ids == null) ids = new ArrayList<Integer>();
                ids.add(id);
                nouns.put(noun, ids);
            }
            synonyms.put(id, synonym);
        }

        // build the graph
        g = new Digraph(synonyms.size());
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] numbers = line.split(",");
            for (int i = 1; i < numbers.length; i++) {
                g.addEdge(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[i]));
            }
        }

        sap = new SAP(g);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nouns.get(word) != null;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        int ancestor = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        // TODO: find a better way to get the noun string by ID instead of creating two maps
        return synonyms.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
        StdOut.println(wordNet.isNoun("worm"));
        StdOut.println(wordNet.distance("worm", "bird"));
        StdOut.println(wordNet.sap("worm", "bird"));
    }
}