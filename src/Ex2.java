import api.DWG;
import api.DWGA;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return DirectedWeightedGraph object
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = null;
        try {
            ans = new DWG(json_file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return DWGA object
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DWG graph = null;
        try {
            graph = new DWG(json_file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DWGA(graph);
    }

    /**
     * This static function will run your GUI using the json fime.
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     */
    public static void runGUI(String json_file) throws IOException, FileNotFoundException {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        new runGui(alg);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            if (args.length > 0) {
                runGUI(args[0]);
            } else {
                System.out.print("Please insert a full directory for a json file of a graph to show in the GUI:");
                String str = sc.nextLine();
                runGUI(str);

            }
        } catch (FileNotFoundException ex) {
            System.out.println("\n Invalid input. Please run again");
        } catch (Exception e) {
            System.out.println("\n Invalid input. Please run again");
        } finally {
            sc.close();
        }
    }
}