package CapstonePackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GraphUnitTests {


    public static String getResult(boolean result, boolean expected){
        if(result == expected){
            System.out.println("Pass");
            return "Pass";
        }
        System.out.println("Fail");
        return "Fail";
    }

    public static void main(String[] args) {
        Parser testParser = new Parser();
        Map<String, Graph> graphs = testParser.parseFile(new File("C:\\Users\\Jared\\Desktop\\testFile.dmrs"));

        ArrayList<ArrayList<String>> testResults = new ArrayList<>();
        for(int i = 0; i<27; i++){
            testResults.add(new ArrayList<String>());
        }

        // toString Tests
        String toStringTest1 = "toString sentance ID 20001001";
        String toStringTest2 = "toString sentance ID 20003013";
        String toStringTest3 = "toString with unpopulated graph";

        Graph graph = graphs.get("20001001_Pierre Vinken, 61 years old, will join the board as a nonexecutive director Nov. 29.");
        Graph graph1 = graphs.get("20003013_Among 33 men who worked closely with the substance, 28 have died -- more than three times the expected number.");
        Graph graph4 = new Graph("This is a bad graph");

        String expectedTest1 = "Key: Pierre Vinken, 61 years old, will join the board as a nonexecutive director Nov. 29.\n" +
                "Top: 10\n" +
                "Nodes: [{ID: 0, label: proper_q, anchors: [{from=0, end=0}]}, {ID: 1, label: named, anchors: [{from=0, end=0}]}, {ID: 2, label: named, anchors: [{from=1, end=1}]}, {ID: 3, label: compound, anchors: [{from=0, end=1}]}, {ID: 4, label: card, anchors: [{from=2, end=2}]}, {ID: 5, label: _year_n_1, anchors: [{from=3, end=3}]}, {ID: 6, label: measure, anchors: [{from=2, end=3}]}, {ID: 7, label: udef_q, anchors: [{from=2, end=3}]}, {ID: 8, label: _old_a_1, anchors: [{from=4, end=4}]}, {ID: 9, label: proper_q, anchors: [{from=0, end=4}]}, {ID: 10, label: _join_v_1, anchors: [{from=6, end=6}]}, {ID: 11, label: _the_q, anchors: [{from=7, end=7}]}, {ID: 12, label: _board_n_of, anchors: [{from=8, end=8}]}, {ID: 13, label: _as_p, anchors: [{from=9, end=9}]}, {ID: 14, label: _a_q, anchors: [{from=10, end=10}]}, {ID: 15, label: _nonexecutive_u_unknown, anchors: [{from=11, end=11}]}, {ID: 16, label: _director_n_of, anchors: [{from=12, end=12}]}, {ID: 17, label: mofy, anchors: [{from=13, end=13}]}, {ID: 18, label: def_explicit_q, anchors: [{from=13, end=13}]}, {ID: 19, label: of_p, anchors: [{from=13, end=13}]}, {ID: 20, label: def_implicit_q, anchors: [{from=13, end=13}]}, {ID: 21, label: dofm, anchors: [{from=14, end=14}]}, {ID: 22, label: loc_nonsp, anchors: [{from=13, end=14}]}]\n" +
                "Edges: [[{source=0, target=1, label=RSTR, post-label=H}], [], [], [{source=3, target=2, label=ARG1, post-label=EQ}, {source=3, target=1, label=ARG2, post-label=NEQ}], [{source=4, target=5, label=ARG1, post-label=EQ}], [], [{source=6, target=8, label=ARG1, post-label=EQ}, {source=6, target=5, label=ARG2, post-label=NEQ}], [{source=7, target=5, label=RSTR, post-label=H}], [{source=8, target=2, label=ARG1, post-label=EQ}], [{source=9, target=2, label=RSTR, post-label=H}], [{source=10, target=2, label=ARG1, post-label=NEQ}, {source=10, target=12, label=ARG2, post-label=NEQ}], [{source=11, target=12, label=RSTR, post-label=H}], [], [{source=13, target=10, label=ARG1, post-label=EQ}, {source=13, target=16, label=ARG2, post-label=NEQ}], [{source=14, target=16, label=RSTR, post-label=H}], [{source=15, target=16, label=ARG1, post-label=EQ}], [], [], [{source=18, target=21, label=RSTR, post-label=H}], [{source=19, target=21, label=ARG1, post-label=EQ}, {source=19, target=17, label=ARG2, post-label=NEQ}], [{source=20, target=17, label=RSTR, post-label=H}], [], [{source=22, target=10, label=ARG1, post-label=EQ}, {source=22, target=21, label=ARG2, post-label=NEQ}]]\n" +
                "Tokens: [{index=0, form=pierre, lemma=Pierre, carg=Pierre}, {index=1, form=Vinken,, lemma=Vinken, carg=Vinken}, {index=2, form=61, lemma=61, carg=61}, {index=3, form=years, lemma=year}, {index=4, form=old,, lemma=old}, {index=5, form=will, lemma=will}, {index=6, form=join, lemma=join}, {index=7, form=the, lemma=the}, {index=8, form=board, lemma=board}, {index=9, form=as, lemma=as}, {index=10, form=a, lemma=a}, {index=11, form=nonexecutive, lemma=nonexecutive}, {index=12, form=director, lemma=director}, {index=13, form=nov., lemma=Nov, carg=Nov}, {index=14, form=29., lemma=29, carg=29}]\n";
        String expectedTest2 = "Key: Among 33 men who worked closely with the substance, 28 have died -- more than three times the expected number.\n" +
                "Top: 27\n" +
                "Nodes: [{ID: 0, label: _among_p_state, anchors: [{from=0, end=0}]}, {ID: 1, label: udef_q, anchors: [{from=1, end=1}]}, {ID: 2, label: card, anchors: [{from=1, end=1}]}, {ID: 3, label: _man_n_1, anchors: [{from=2, end=2}]}, {ID: 4, label: _work_v_1, anchors: [{from=4, end=4}]}, {ID: 5, label: _close_a_to, anchors: [{from=5, end=5}]}, {ID: 6, label: _with_p, anchors: [{from=6, end=6}]}, {ID: 7, label: _the_q, anchors: [{from=7, end=7}]}, {ID: 8, label: _substance_n_1, anchors: [{from=8, end=8}]}, {ID: 9, label: generic_entity, anchors: [{from=9, end=9}]}, {ID: 10, label: udef_q, anchors: [{from=9, end=9}]}, {ID: 11, label: card, anchors: [{from=9, end=9}]}, {ID: 12, label: _die_v_1, anchors: [{from=11, end=11}]}, {ID: 13, label: focus_d, anchors: [{from=0, end=12}]}, {ID: 14, label: much-many_a, anchors: [{from=13, end=13}]}, {ID: 15, label: comp, anchors: [{from=13, end=13}]}, {ID: 16, label: udef_q, anchors: [{from=15, end=15}]}, {ID: 17, label: card, anchors: [{from=15, end=15}]}, {ID: 18, label: _times_n_1, anchors: [{from=16, end=16}]}, {ID: 19, label: _the_q, anchors: [{from=17, end=17}]}, {ID: 20, label: _expect_v_1, anchors: [{from=18, end=18}]}, {ID: 21, label: parg_d, anchors: [{from=18, end=18}]}, {ID: 22, label: _number_n_of, anchors: [{from=19, end=19}]}, {ID: 23, label: appos, anchors: [{from=15, end=19}]}, {ID: 24, label: unknown, anchors: [{from=13, end=19}]}, {ID: 25, label: generic_entity, anchors: [{from=13, end=19}]}, {ID: 26, label: udef_q, anchors: [{from=13, end=19}]}, {ID: 27, label: implicit_conj, anchors: [{from=0, end=19}]}]\n" +
                "Edges: [[{source=0, target=12, label=ARG1, post-label=EQ}, {source=0, target=3, label=ARG2, post-label=NEQ}], [{source=1, target=3, label=RSTR, post-label=H}], [{source=2, target=3, label=ARG1, post-label=EQ}], [], [{source=4, target=3, label=ARG1, post-label=EQ}], [{source=5, target=4, label=ARG1, post-label=EQ}], [{source=6, target=4, label=ARG1, post-label=EQ}, {source=6, target=8, label=ARG2, post-label=NEQ}], [{source=7, target=8, label=RSTR, post-label=H}], [], [], [{source=10, target=9, label=RSTR, post-label=H}], [{source=11, target=9, label=ARG1, post-label=EQ}], [{source=12, target=9, label=ARG1, post-label=NEQ}], [{source=13, target=12, label=ARG1, post-label=EQ}, {source=13, target=0, label=ARG2, post-label=EQ}], [{source=14, target=25, label=ARG1, post-label=EQ}], [{source=15, target=14, label=ARG1, post-label=EQ}, {source=15, target=18, label=ARG2, post-label=NEQ}], [{source=16, target=18, label=RSTR, post-label=H}], [{source=17, target=18, label=ARG1, post-label=EQ}], [], [{source=19, target=22, label=RSTR, post-label=H}], [{source=20, target=22, label=ARG2, post-label=EQ}], [{source=21, target=20, label=ARG1, post-label=EQ}, {source=21, target=22, label=ARG2, post-label=EQ}], [], [{source=23, target=18, label=ARG1, post-label=NEQ}, {source=23, target=22, label=ARG2, post-label=NEQ}, {source=23, target=25, label=MOD, post-label=EQ}], [{source=24, target=25, label=ARG, post-label=NEQ}], [], [{source=26, target=25, label=RSTR, post-label=H}], [{source=27, target=12, label=L-HNDL, post-label=H}, {source=27, target=12, label=L-INDEX, post-label=NEQ}, {source=27, target=24, label=R-HNDL, post-label=H}, {source=27, target=24, label=R-INDEX, post-label=NEQ}]]\n" +
                "Tokens: [{index=0, form=among, lemma=among}, {index=1, form=33, lemma=33, carg=33}, {index=2, form=men, lemma=man}, {index=3, form=who, lemma=who}, {index=4, form=worked, lemma=work}, {index=5, form=closely, lemma=close}, {index=6, form=with, lemma=with}, {index=7, form=the, lemma=the}, {index=8, form=substance,, lemma=substance}, {index=9, form=28, lemma=28, carg=28}, {index=10, form=have, lemma=have}, {index=11, form=died, lemma=die}, {index=12, form=–, lemma=–}, {index=13, form=more, lemma=more}, {index=14, form=than, lemma=than}, {index=15, form=three, lemma=3, carg=3}, {index=16, form=times, lemma=times}, {index=17, form=the, lemma=the}, {index=18, form=expected, lemma=expect}, {index=19, form=number., lemma=number}]\n";
        String expectedTest3 = "Key: This is a bad graph\n" +
                "Top: 0\n" +
                "Nodes: []\n" +
                "Edges: []\n" +
                "Tokens: null\n";


        boolean result1 = graph.toString().equals(expectedTest1);
        boolean result2 = graph1.toString().endsWith(expectedTest2);
        boolean result3 = graph4.toString().endsWith(expectedTest3);
        System.out.print(toStringTest1+" : ");
        testResults.get(0).add("toString()");
        testResults.get(0).add(toStringTest1);
        testResults.get(0).add(getResult(result1, true));

        System.out.print(toStringTest2+" : ");
        testResults.get(1).add("toString()");
        testResults.get(1).add(toStringTest2);
        testResults.get(1).add(getResult(result2, true));

        System.out.print(toStringTest3+" : ");
        testResults.get(2).add("toString()");
        testResults.get(2).add(toStringTest3);
        testResults.get(2).add(getResult(result3, true));

        // Equals Test
        Graph graph3 = graphs.get("20001033_Pierre Vinken, 61 years old, will join the board as a nonexecutive director Nov. 29.");
        String equalsTest1 = "Equals with two equal graphs";
        String equalsTest2 = "Equals with two unequal graphs";
        String equalsTest3 = "Equals to unpopulated graph";

        boolean equalsTestResult1 = graph.equals(graph3);
        boolean equalsTestResult2 = graph.equals(graph1);
        boolean equalsTestResult3 = graph.equals(graph4);

        System.out.print(equalsTest1+" : ");
        testResults.get(3).add("equals()");
        testResults.get(3).add(equalsTest1);
        testResults.get(3).add(getResult(equalsTestResult1, true));

        System.out.print(equalsTest2+" : ");
        testResults.get(4).add("equals()");
        testResults.get(4).add(equalsTest2);
        testResults.get(4).add(getResult(equalsTestResult2, false));

        System.out.print(equalsTest3+" : ");
        testResults.get(5).add("equals()");
        testResults.get(5).add(equalsTest3);
        testResults.get(5).add(getResult(equalsTestResult3, false));


        // isPlanar Tests
        String isPlanarTest1 = "isPlanar on planar graph";
        String isPlanarTest2 = "isPlanar on non-planar graph";
        String isPlanarTest3 = "isPlanar on unpopulated graph";

        Graph graph5 = graphs.get("20001034_Pierre Vinken, 61 years old, will join the board as a nonexecutive director Nov. 29.");
        Boolean isPlanarTestResult1 = graph5.isPlanar();
        Boolean isPlanarTestResult2 = graph.isPlanar();
        Boolean isPlanarTestResult3 = graph4.isPlanar();


        System.out.print(isPlanarTest1+" : ");
        testResults.get(6).add("isPlanar()");
        testResults.get(6).add(isPlanarTest1);
        testResults.get(6).add(getResult(isPlanarTestResult1, true));

        System.out.print(isPlanarTest2+" : ");
        testResults.get(7).add("isPlanar()");
        testResults.get(7).add(isPlanarTest2);
        testResults.get(7).add(getResult(isPlanarTestResult2, false));

        System.out.print(isPlanarTest3+" : ");
        testResults.get(8).add("isPlanar()");
        testResults.get(8).add(isPlanarTest3);
        testResults.get(8).add(getResult(isPlanarTestResult3, false));

        // isConnected Tests

        String isConnectedTest1 = "isConnected on connected graph";
        String isConnectedTest2 = "isConnected on unconnected graph";
        String isConnectedTest3 = "isConnected on unpopulated graph";

        Graph graph6 = graphs.get("20001035_Pierre Vinken, 61 years old, will join the board as a nonexecutive director Nov. 29.");

        Boolean isConnectedTestResult1 = graph5.isConnected();
        Boolean isConnectedTestResult2 = graph6.isConnected();
        Boolean isConnectedTestResult3 = graph4.isConnected();


        System.out.print(isConnectedTest1+" : ");
        testResults.get(9).add("isConnected()");
        testResults.get(9).add(isConnectedTest1);
        testResults.get(9).add(getResult(isConnectedTestResult1, true));

        System.out.print(isConnectedTest2+" : ");
        testResults.get(10).add("isConnected()");
        testResults.get(10).add(isConnectedTest2);
        testResults.get(10).add(getResult(isConnectedTestResult2, false));

        System.out.print(isConnectedTest3+" : ");
        testResults.get(11).add("isConnected()");
        testResults.get(11).add(isConnectedTest3);
        testResults.get(11).add(getResult(isConnectedTestResult3, false));


        // getDesNodes Tests

        ArrayList<Node> nodes1 = new ArrayList<Node>();
        nodes1.add(graph.nodes.get(3));
        nodes1.add(graph.nodes.get(2));
        nodes1.add(graph.nodes.get(1));

        String getDesNodesTest1 = "getDesNodes for node 3 of populated graph";
        String getDesNodesTest2 = "getDesNodes on unpopulated graph";

        Boolean getDesNodesTestResult1 = graph.getDesNodes(3).equals(nodes1);
        Boolean getDesNodesTestResult2 = graph4.getDesNodes(3).equals(new ArrayList<Node>());

        System.out.print(getDesNodesTest1+" : ");
        testResults.get(12).add("getDesNodes()");
        testResults.get(12).add(getDesNodesTest1);
        testResults.get(12).add(getResult(getDesNodesTestResult1, true));

        System.out.print(getDesNodesTest2+" : ");
        testResults.get(13).add("getDesNodes()");
        testResults.get(13).add(getDesNodesTest2);
        testResults.get(13).add(getResult(getDesNodesTestResult2, true));

        // getAdjNodes Tests

        ArrayList<Node> nodes2 = new ArrayList<Node>();
        nodes2.add(graph.nodes.get(3));
        nodes2.add(graph.nodes.get(2));
        nodes2.add(graph.nodes.get(1));

        String getAdjNodesTest1 = "getAdjNodes for node 3 of populated graph";
        String getAdjNodesTest2 = "getAdjNodes on unpopulated graph";

        Boolean getAdjNodesTestResult1 = graph.getAdjNodes(3).equals(nodes2);
        Boolean getAdjNodesTestResult2 = graph4.getAdjNodes(3).equals(new ArrayList<Node>());

        System.out.print(getAdjNodesTest1+" : ");
        testResults.get(14).add("getAdjNodes()");
        testResults.get(14).add(getAdjNodesTest1);
        testResults.get(14).add(getResult(getAdjNodesTestResult1, true));

        System.out.print(getAdjNodesTest2+" : ");
        testResults.get(15).add("getAdjNodes()");
        testResults.get(15).add(getAdjNodesTest2);
        testResults.get(15).add(getResult(getAdjNodesTestResult2, true));

        // LabelInGraph Tests

        String labelInGraphTest1 = "label in graph";
        String labelInGraphTest2 = "label not in graph";
        String labelInGraphTest3 = "unpopulated graph";


        Boolean labelInGraphTestResult1 = graph.labelInGraph("proper_q");
        Boolean labelInGraphTestResult2 = graph.labelInGraph("label");
        Boolean labelInGraphTestResult3 = graph4.labelInGraph("proper_q");


        System.out.print(labelInGraphTest1+" : ");
        testResults.get(16).add("labelInGraph()");
        testResults.get(16).add(labelInGraphTest1);
        testResults.get(16).add(getResult(labelInGraphTestResult1, true));

        System.out.print(labelInGraphTest2+" : ");
        testResults.get(17).add("labelInGraph()");
        testResults.get(17).add(labelInGraphTest2);
        testResults.get(17).add(getResult(labelInGraphTestResult2, false));

        System.out.print(labelInGraphTest3+" : ");
        testResults.get(18).add("labelInGraph()");
        testResults.get(18).add(labelInGraphTest3);
        testResults.get(18).add(getResult(labelInGraphTestResult3, false));

        // getSimilarities Tests

        String getSimilaritiesTest1 = "getSimilarities matching sentences";
        String getSimilaritiesTest2 = "getSimilarities for mismatch sentences";
        //String getSimilaritiesTest3 = "getSimilarities on unpopulated graph";
        Boolean getSimilaritiesTestResult1 = false;
        Boolean getSimilaritiesTestResult2 = true;
        try {
            getSimilaritiesTestResult1 = graph.getSimilarities(graph3).toString().equals("{0=9, 1=2, 2=1, 3=3, 4=4, 5=5, 6=6, 7=7, 8=8, 9=0, 10=10, 11=11, 12=12, 13=13, 14=14, 15=15, 16=16, 17=17, 18=18, 19=19, 20=20, 21=21, 22=22}");
        }
        catch (Exception e){

        }
        try {
            graph4.getSimilarities(graph).toString().equals("");
            getSimilaritiesTestResult2 = false;

        }
        catch (Exception e){
        }
        System.out.print(getSimilaritiesTest1+" : ");
        testResults.get(19).add("getSimilarities()");
        testResults.get(19).add(getSimilaritiesTest1);
        testResults.get(19).add(getResult(getSimilaritiesTestResult1, true));

        System.out.print(getSimilaritiesTest2+" : ");
        testResults.get(20).add("getSimilarities()");
        testResults.get(20).add(getSimilaritiesTest2);
        testResults.get(20).add(getResult(getSimilaritiesTestResult2, true));

        // getShortestPath Tests
        ArrayList<Node> nodes3 = new ArrayList<Node>();
        nodes3.add(graph.nodes.get(10));
        nodes3.add(graph.nodes.get(12));
        nodes3.add(graph.nodes.get(22));
        ArrayList<Node> nodes4 = new ArrayList<Node>();
        nodes4.add(graph.nodes.get(0));
        nodes4.add(graph.nodes.get(1));
        nodes4.add(graph.nodes.get(2));
        nodes4.add(graph.nodes.get(3));
        nodes4.add(graph.nodes.get(10));


        String getShortestPathTest1 = "getShortestPath from node 22 to node 12 of a directed graph";
        String getShortestPathTest2 = "getShortestPath from 0 to 10 of an undirected graph";
        String getShortestPathTest3 = "Try to find path between two nodes with no path between them on populated graph";
        String getShortestPathTest4 = "getShortestPath from node 22 to node 12 of an unpopulated directed graph";
        String getShortestPathTest5 = "getShortestPath from 0 to 10 of an unpopulated undirected graph";
        String getShortestPathTest6 = "Try to find path between two nodes with no path between them on unpopulated graph graph";


        Boolean getShortestPathTestResult1 = false;
        Boolean getShortestPathTestResult2 = false;
        Boolean getShortestPathTestResult3 = true;
        Boolean getShortestPathTestResult4 = true;
        Boolean getShortestPathTestResult5 = true;
        Boolean getShortestPathTestResult6 = true;
        try {
            getShortestPathTestResult1 = graph.getShortestPath(22, 12, true).equals(nodes3);
        }
        catch (Exception e){

        }
        try {
            getShortestPathTestResult2 = graph.getShortestPath(0,10,false).equals(nodes4);
        }
        catch (Exception e){

        }
        try {
            graph.getShortestPath(7,8,true).equals(nodes4);
            getShortestPathTestResult3 = false;
        }
        catch (Exception e){

        }
        try {
            graph4.getShortestPath(22,12,true).equals(nodes4);
            getShortestPathTestResult4 = false;
        }
        catch (Exception e){

        }
        try {
            graph4.getShortestPath(0,10,true).equals(nodes4);
            getShortestPathTestResult4 = false;
        }
        catch (Exception e){

        }
        try {
            graph4.getShortestPath(7,8,true).equals(nodes4);
            getShortestPathTestResult4 = false;
        }
        catch (Exception e){

        }

        System.out.print(getShortestPathTest1+" : ");
        testResults.get(21).add("getShortestPath()");
        testResults.get(21).add(getShortestPathTest1);
        testResults.get(21).add(getResult(getShortestPathTestResult1, true));

        System.out.print(getShortestPathTest2+" : ");
        testResults.get(22).add("getShortestPath()");
        testResults.get(22).add(getShortestPathTest2);
        testResults.get(22).add(getResult(getShortestPathTestResult2, true));

        System.out.print(getShortestPathTest3+" : ");
        testResults.get(23).add("getShortestPath()");
        testResults.get(23).add(getShortestPathTest3);
        testResults.get(23).add(getResult(getShortestPathTestResult3, true));

        System.out.print(getShortestPathTest4+" : ");
        testResults.get(24).add("getShortestPath()");
        testResults.get(24).add(getShortestPathTest4);
        testResults.get(24).add(getResult(getShortestPathTestResult4, true));

        System.out.print(getShortestPathTest5+" : ");
        testResults.get(25).add("getShortestPath()");
        testResults.get(25).add(getShortestPathTest5);
        testResults.get(25).add(getResult(getShortestPathTestResult5, true));

        System.out.print(getShortestPathTest6+" : ");
        testResults.get(26).add("getShortestPath()");
        testResults.get(26).add(getShortestPathTest6);
        testResults.get(26).add(getResult(getShortestPathTestResult6, true));

        try {
            writeToCSV(testResults);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void writeToCSV(ArrayList<ArrayList<String>> testResults) throws IOException {


        FileWriter csvWriter = new FileWriter("TestResults.csv");
        csvWriter.append("Method");
        csvWriter.append(",");
        csvWriter.append("TestCase");
        csvWriter.append(",");
        csvWriter.append("Result");
        csvWriter.append("\n");

        for (ArrayList<String> test : testResults) {
            csvWriter.append(test.get(0));
            csvWriter.append(",");
            csvWriter.append(test.get(1));
            csvWriter.append(",");
            csvWriter.append(test.get(2));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
        System.out.println("Data entered");
    }

}
