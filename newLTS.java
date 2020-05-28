import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class State {
    private int id;
    private String color;
    private String typeN;

    public State(int pId, String pColor, String ptypeN) {
        this.setId(pId);
        this.setColor(pColor);
        if (ptypeN == null) {
            this.setTypeN("");
        } else {
            this.setTypeN(ptypeN);
        }
    }

    public String getTypeN() {
        return typeN;
    }

    public void setTypeN(String typeN) {
        this.typeN = typeN;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        State tmp = (State) o;
        if (this.id != tmp.id) {
            return false;
        }
        return true;
    }
}

class Transition {
    private State source;
    private State target;
    private String label;
    private String color;

    public Transition(State pSource, State pTarget, String pLbl, String pColor) {
        this.setSource(pSource);
        this.setTarget(pTarget);
        this.setLabel(pLbl);
        this.setColor(pColor);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public State getTarget() {
        return target;
    }

    public void setTarget(State target) {
        this.target = target;
    }

    public State getSource() {
        return source;
    }

    public void setSource(State source) {
        this.source = source;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

// class LTS {
// private Map<State, List<Transition>> mapLTS;

// public LTS(Map<State, List<Transition>> pMapLTS) {
// this.mapLTS = new HashMap<State, List<Transition>>();
// }

// public Map<State, List<Transition>> getMapLTS() {
// return mapLTS;
// }
// }

class Cluster {
    private static int incrementId = 1;
    private int id;
    private int numberOfStates;
    private String neighType;
    private List<State> neighborhoods;
    private List<String> commonLabels;
    private List<String> allLabels;

    public Cluster(int pNumberOfStates, String pNeighType, List<State> pN,
            List<String> pCommonLabels, List<String> pAllLabels) {
        this.setId(incrementId++);
        this.setNumberOfStates(pNumberOfStates);
        this.setNeighType(pNeighType);
        this.setNeighborhoods(pN);
        this.setCommonLabels(pCommonLabels);
        this.setAllLabels(pAllLabels);
    }

    public List<String> getCommonLabels() {
        return commonLabels;
    }

    public void setCommonLabels(List<String> commonLabels) {
        this.commonLabels = commonLabels;
    }

    public List<String> getAllLabels() {
        return allLabels;
    }

    public void setAllLabels(List<String> allLabels) {
        this.allLabels = allLabels;
    }

    public String getNeighType() {
        return neighType;
    }

    public void setNeighType(String neighType) {
        this.neighType = neighType;
    }

    public int getNumberOfStates() {
        return numberOfStates;
    }

    public void setNumberOfStates(int numberOfStates) {
        this.numberOfStates = numberOfStates;
    }

    public List<State> getNeighborhoods() {
        return neighborhoods;
    }

    public void setNeighborhoods(List<State> neighborhoods) {
        this.neighborhoods = neighborhoods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}



public class newLTS { 
    static String getStateColor (int state) {
        String stateColor = "";
        if(state == 0) {
            stateColor = "RED";
        } else if (state < 0) {
            stateColor = "GREEN";
        } else {
            stateColor = "BLUE";
        }
        return stateColor;
    }

    static void printN(Map<Integer, String> mapN) {
        for (Map.Entry mapElement : mapN.entrySet()) {           
            int tmpId = (int) mapElement.getKey();
            String tmpN = mapN.get(tmpId);
            System.out.println(tmpId + " : " + tmpN);
        }
    }

    static void printLTS(Map<State, List<Transition>> lts) {
        int count = 0;
        for (Map.Entry mapElement : lts.entrySet()) {           
            State tmpState = (State)mapElement.getKey();
            List<Transition> tmpListTrans = lts.get(tmpState);
            System.out.println("State[" + tmpState.getId() + "] :");
            if(!tmpState.getTypeN().equals("")) {System.out.println("Is Neighborhood : " + tmpState.getTypeN());}
            if(tmpListTrans.size() > 0) {
                for (Transition trans : tmpListTrans) {
                    count++;
                    System.out.println(">> Source : " 
                    + trans.getSource().getId() + ", Label : "
                    + trans.getLabel() + " ("
                    + trans.getColor() + "), Target : "
                    + trans.getTarget().getId());
                } 
            } else {
                System.out.println("Empty (No Transitions)");
            }
            System.out.println();
        } 
        System.out.println("Number of Trans : " + count);
    }

    static void traverseCluster(Map <State, List<Transition>> lts, State state, State stateOne,
	   
    }
    
    static void traverseLTS(Map <State, List<Transition>> lts, State state, List <State> tmpListVisited, List <Cluster> tmpClusters) {
       
    }

    static void printClusters(List<Cluster> tmpClusters, String fileNameNew) {


        // write to file
        try {
            FileWriter fw = new FileWriter(fileNameNew, true);
            BufferedWriter bw = new BufferedWriter(fw);
            new PrintWriter(fileNameNew).close(); // empty the content 1st
            PrintWriter writer = new PrintWriter(bw);
            writer.println("Number of Clusters = " + tmpClusters.size()+"\n");
            for (Cluster cluster : tmpClusters) {
                writer.println("> Cluster Id = " + cluster.getId());
                writer.println(">>> Number of Faulty States  = " + cluster.getNumberOfStates());
                writer.println(">>> Faulty State Type = " + cluster.getNeighType());
                writer.println(">>> Faulty States =>");
                for (State state : cluster.getNeighborhoods()) {
                    writer.println(">>>>> State Id " + state.getId() + ", N = " + state.getTypeN());
                }
                writer.println(">>> Common Label => ");
                for (String commonlabel : cluster.getCommonLabels()) {
                    writer.println(">>>>> " + commonlabel);
                }
                writer.println(">>> All Labels =>");
                for (String label : cluster.getAllLabels()) {
                    writer.println(">>>>> " + label);
                }
                writer.println("");
            }
            writer.close();
            System.out.println("Succesfully created : " + fileNameNew);
        } catch (Exception ex) {
            System.out.println("Writing failed...!!!");
        }
    }

    public static void main(String[] args) {
        // Check input
        if (args.length < 1) {
            System.out.println("Usage : java newLTS <name>");
            System.exit(0);
        }

        // Init LTS and list of strings
        Map<State,List<Transition>> ltsMap = new HashMap<State, List<Transition>>();
        Map<Integer, String> mapN = new HashMap<Integer, String>();
        String fileName = args[0]+".autx";
        String fileNameNew = args[0]+"_clusters.txt";
        List <String> listring = new ArrayList<String>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.skip(1).forEach(listring::add);
        } catch (Exception ex) {
            System.out.println("Error reading file...!!!");
            System.exit(0);
        }

        // Loop through the list to get map of N
        for (String row : listring) {
            String[] rowArray = row.split(",");
            String[] rowArrayLeft = rowArray[0].split(":");
            if(rowArrayLeft.length > 1) {
                if(rowArrayLeft[1].equals("N")){
                    int tmpId = Integer.parseInt(rowArrayLeft[0].replaceAll("[^\\d-]", ""));
                    String tmpN = rowArrayLeft[2];
                    mapN.put(tmpId, tmpN);
                }
            }     
        }
        // printN(mapN);
        State firstState = new State(0, getStateColor(0), mapN.get(0));
        ltsMap.put(firstState, new ArrayList<Transition>());

        // Loop through the list
        for (String row : listring) {
            String[] rowArray = row.split(",");
            String[] rowArrayMid = rowArray[1].split(":");
            
            // Get the states
            int sourceStateId = Integer.parseInt(rowArray[0].replaceAll("[^\\d-]", ""));
            State sourceState = new State(sourceStateId, getStateColor(sourceStateId), mapN.get(sourceStateId));
            int targetStateId = Integer.parseInt(rowArray[2].replaceAll("[^\\d-]", ""));
            State targetState = new State(targetStateId, getStateColor(targetStateId), mapN.get(targetStateId));
            
            // Build the transition
            Transition newTrans = new Transition(sourceState, 
                        targetState, rowArrayMid[0].replaceAll("\\s+",""), rowArrayMid[1]);
            List<Transition> tmpList = new ArrayList<Transition>();

            // Add the states to Map
            if (!ltsMap.containsKey(sourceState)) {
                ltsMap.put(sourceState, tmpList);
            }
            if (!ltsMap.containsKey(targetState)) {
                ltsMap.put(targetState, tmpList);
            }

            // Add the List
            tmpList = ltsMap.get(sourceState);
            tmpList.add(newTrans);
            ltsMap.put(sourceState, tmpList);
        }

        // printLTS(ltsMap);
        List <State> tmpListVisited = new ArrayList<State>();
        List <Cluster> tmpClusters = new ArrayList<Cluster>();
        traverseLTS(ltsMap, firstState, tmpListVisited, tmpClusters);
        printClusters(tmpClusters, fileNameNew);
    }
}