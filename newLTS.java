import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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

    static void checkCluster (Map <State, List<Transition>> lts, Map <State, List<Transition>> ltsBack, State state, State stateOne,
        List <State> tmpVisited, List <State> tmpStates, List <String> labels) {

        // check if already visited, or its the first state (could be a loop)
        if (state.getId() != stateOne.getId() && tmpVisited.contains(state)) {
            return;
        } else {
            tmpVisited.add(state);
        }

        List <Transition> transitions = ltsBack.get(state);

        // check if we found a faulty state 'similar' to the first state
        if (!state.getTypeN().equals("") && state.getTypeN().equals(stateOne.getTypeN())) {
            List <Transition> transitionLabels = lts.get(state);
            List <String> tmpLabels = new ArrayList <String> ();
            List <String> tmpLabelsCommon = new ArrayList <String> ();
            tmpLabelsCommon.addAll(labels);
            for (Transition transition : transitionLabels) {
                tmpLabels.add(transition.getLabel());
            }
            tmpLabelsCommon.retainAll(tmpLabels);

            if (tmpLabelsCommon.size() != 0) {
                tmpStates.add(state);
            }
        }

        // go backward
        for (Transition transition : transitions) {
            if (transition.getTarget().getId() != state.getId()) {
                checkCluster(lts, ltsBack, transition.getTarget(), stateOne, tmpVisited, tmpStates, labels);   
            }
        }
    }

    static void traverseCluster(Map <State, List<Transition>> lts, State state, State stateOne,
        List <State> statesCluster, List <State> statesVisitedInCluster,
        List <String> labelsCommon, List <String> labelsAll, List <Cluster> clusters) {
            
            List <Transition> transitions = lts.get(state);

            if (statesVisitedInCluster.contains(state)) {
                // If the state already visited
                return;
            } else if (transitions.size() == 0) {
                // If the state has no transition
                statesVisitedInCluster.add(state);
                return;
            } else if(!statesVisitedInCluster.contains(state)) {
                // If the state is not visited yet
                statesVisitedInCluster.add(state);

                if (!state.getTypeN().equals("")){
                    // If the state is a Faulty State and IS the same type as the first state in the cluster

                    // Get common labels
                    List <String> tmpLabels = new ArrayList<String>();
                    List <String> tmpLabelsCommon = new ArrayList<String>();
                    tmpLabelsCommon.addAll(labelsCommon);
                    for (Transition stateLabels : lts.get(state)) {
                        if (!tmpLabels.contains(stateLabels.getLabel())) {
                            tmpLabels.add(stateLabels.getLabel());
                        }
                    }
                    tmpLabelsCommon.retainAll(tmpLabels);

                    // check if in a cluster
                    boolean isInCluster = false;
                    for (Cluster cluster : clusters) {
                        if (cluster.getNeighborhoods().contains(state)) {
                            isInCluster = true;
                        }
                    }

                    if (tmpLabelsCommon.size() != 0 && state.getTypeN().equals(stateOne.getTypeN()) && isInCluster == false) {
                        tmpLabels.removeAll(labelsAll);
                        labelsAll.addAll(tmpLabels);
                        labelsCommon.clear();
                        labelsCommon.addAll(tmpLabelsCommon);
                        statesCluster.add(state);
                    } 
                }

                for (Transition transition : transitions) {
                    if (transition.getColor().equals("BLACK") && transition.getTarget().getId() != state.getId()) {
                        traverseCluster(lts, transition.getTarget(), stateOne, statesCluster, statesVisitedInCluster,
                            labelsCommon, labelsAll, clusters);
                    }
                }
            }
    }
    
    static void traverseLTS(Map <State, List<Transition>> lts, Map <State, List<Transition>> ltsBack, State state,
        List <State> statesVisited, List <Cluster> clusters) {

        List <Transition> transitions = lts.get(state);
        if (statesVisited.contains(state)) {
            // If the state already visited
            return;
        } else if (transitions.size() == 0) {
            // If the state has no transition
            statesVisited.add(state);
            return;
        } else if(!statesVisited.contains(state)) {   
            // If the state is not visited yet
            statesVisited.add(state);
            boolean isInCluster = false;
            for (Cluster cluster : clusters) {
                if (cluster.getNeighborhoods().contains(state)) {
                    isInCluster = true;
                }
            }

            if (!state.getTypeN().equals("") && isInCluster == false) {
                // If the state IS a Faulty State AND IS NOT in a any cluster yet
                        
                List <String> labelsCommon = new ArrayList<String>();
                for (Transition stateLabel : lts.get(state)) {
                    if (!labelsCommon.contains(stateLabel.getLabel())) {
                        labelsCommon.add(stateLabel.getLabel());
                    }
                }
                
                List <State> tmpCheckCluster = new ArrayList<State>();
                List <State> tmpCheckVisited = new ArrayList<State>();
                checkCluster(lts, ltsBack, state, state, tmpCheckVisited, tmpCheckCluster, labelsCommon);

                // Check if there is a loop in a cluster
                int isLoop = 0;
                for (State stateCheckCluster : tmpCheckCluster) {
                    if (stateCheckCluster.getId() == state.getId()) {
                        isLoop++;
                    }
                }

                if (tmpCheckCluster.size() == 1 || isLoop > 1) {
                    List <State> statesCluster = new ArrayList<State>();
                    List <State> statesVisitedInCluster = new ArrayList<State>();
                    List <String> labelsAll = new ArrayList<String>();
                    traverseCluster(lts, state, state, statesCluster, statesVisitedInCluster, labelsCommon, labelsAll, clusters);
                    Cluster cluster = new Cluster(statesCluster.size(), state.getTypeN(), statesCluster, labelsCommon, labelsAll);
                    clusters.add(cluster);
                }
            }

            // Continue to the next state
            for (Transition transition : transitions) {
                if (transition.getColor().equals("BLACK") && transition.getTarget().getId() != state.getId()) {
                    traverseLTS(lts, ltsBack, transition.getTarget(), statesVisited, clusters);
                }
            }
        } 
    }

    static void printClusters(List<Cluster> tmpClusters, String fileNameNew) {
        try {
            
            FileWriter fw = new FileWriter(fileNameNew, true);
            BufferedWriter bw = new BufferedWriter(fw);
            new PrintWriter(fileNameNew).close(); // empty the content 1st
            PrintWriter writer = new PrintWriter(bw);
            writer.println("Number of Clusters = " + tmpClusters.size()+"\n");
            for (Cluster cluster : tmpClusters) {
                
                List <Integer> listOfFS = new ArrayList<Integer>();
                List <String> listOfCommonLabels = new ArrayList<String>();
                List <String> listOfInvLabels = new ArrayList<String>();
                for (State state : cluster.getNeighborhoods()) {
                    listOfFS.add(state.getId());
                }
                for (String cLbl : cluster.getCommonLabels()) {
                    listOfCommonLabels.add(cLbl);
                }
                for (String iLbl : cluster.getAllLabels()) {
                    listOfInvLabels.add(iLbl);
                }
                Collections.sort(listOfFS);
                Collections.sort(listOfCommonLabels);
                Collections.sort(listOfInvLabels);
                String type = "";
                if (cluster.getNeighType().equals("R")) {
                    type = "Neutral + Incorrect";
                } else if (cluster.getNeighType().equals("G")) {
                    type = "Neutral + Correct";
                } else if (cluster.getNeighType().equals("GRB")) {
                    type = "Neutral + Correct + Incorrect";
                } else if (cluster.getNeighType().equals("GR")) {
                    type = "Correct + Incorrect";
                }
                writer.println("> Cluster Id = " + cluster.getId());
                writer.println(">>> Number of Faulty States  = " + cluster.getNumberOfStates());
                writer.println(">>> Faulty State Type\t\t = " + type);
                writer.print(">>> Faulty States\t\t\t = ");
                for (int stateId : listOfFS) {
                    writer.print(stateId + ", ");
                }
                writer.println("");
                writer.print(">>> Common Label\t\t\t = ");
                // for (String commonlabel : cluster.getCommonLabels()) {
                //     writer.print(commonlabel + ", ");
                // }
                for (String commonlabel : listOfCommonLabels) {
                    writer.print(commonlabel + ", ");
                }
                writer.println("");
                writer.print(">>> Involved Labels\t\t\t = ");
                // for (String label : cluster.getAllLabels()) {
                //     writer.print(label + ", ");
                // }
                for (String label : listOfInvLabels) {
                    writer.print(label + ", ");
                }
                writer.println("\n");
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
        Map<State,List<Transition>> ltsMapBack = new HashMap<State, List<Transition>>();
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
        ltsMapBack.put(firstState, new ArrayList<Transition>());

        // Loop through the list
        for (String row : listring) {
            String[] rowArray = row.split(",");
            String[] rowArrayMid = rowArray[1].split(":");
            
            /* FORWARD */
            // Get the states
            int sourceStateId = Integer.parseInt(rowArray[0].replaceAll("[^\\d-]", ""));
            State sourceState = new State(sourceStateId, getStateColor(sourceStateId), mapN.get(sourceStateId));
            int targetStateId = Integer.parseInt(rowArray[2].replaceAll("[^\\d-]", ""));
            State targetState = new State(targetStateId, getStateColor(targetStateId), mapN.get(targetStateId));
            
            // Build the transition
            Transition newTrans = new Transition(sourceState, 
                        targetState, rowArrayMid[0].replaceAll("\\s+","") + " ("+rowArrayMid[1]+")", rowArrayMid[1]);
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

            /* BACKWARD */
            
            // Build the transition
            Transition newTransBack = new Transition(targetState, 
                sourceState, rowArrayMid[0].replaceAll("\\s+",""), rowArrayMid[1]);
            List<Transition> tmpListBack = new ArrayList<Transition>();

            // Add the states to Map
            if (!ltsMapBack.containsKey(targetState)) {
                ltsMapBack.put(targetState, tmpListBack);
            }
            if (!ltsMapBack.containsKey(sourceState)) {
                ltsMapBack.put(sourceState, tmpListBack);
            }

            // Add the List
            tmpListBack = ltsMapBack.get(targetState);
            tmpListBack.add(newTransBack);
            ltsMapBack.put(targetState, tmpListBack);
        }

        // for (Map.Entry <State, List<Transition>> mapElement  : ltsMap.entrySet()) {           
        //     List<Transition> transitions = mapElement.getValue();
        //     int transOrder = 0;
        //     int transOrderSwap = 0;
        //     if (transitions.size() > 1) {
        //         for (Transition transition : transitions) {
        //             if(transition.getTarget().getTypeN()!= ""){ // A faulty state
        //                 Transition tmpFirstTransition = transitions.get(transOrderSwap);
        //                 transitions.set(transOrderSwap, transition);
        //                 transitions.set(transOrder, tmpFirstTransition);
        //                 transOrderSwap++;
        //             }
        //             transOrder++;
        //         }
        //     }
        // }

        // printLTS(ltsMapBack);
        List <State> statesVisited = new ArrayList<State>();
        List <Cluster> clusters = new ArrayList<Cluster>();
        traverseLTS(ltsMap, ltsMapBack, firstState, statesVisited, clusters);
        printClusters(clusters, fileNameNew);
    }
}