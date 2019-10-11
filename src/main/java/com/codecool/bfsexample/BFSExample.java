package com.codecool.bfsexample;

import com.codecool.bfsexample.model.UserNode;
import com.codecool.bfsexample.service.GraphMapping;

import java.util.List;
import java.util.Set;

public class BFSExample {

    private static List<UserNode> populateDB() {

        RandomDataGenerator generator = new RandomDataGenerator();
        List<UserNode> users = generator.generate();

        GraphPlotter graphPlotter = new GraphPlotter(users);
        UserNode user1 = GraphMapping.getUserByID(users, 47);
        UserNode user2 = GraphMapping.getUserByID(users, 118);
        GraphMapping graphMapping = new GraphMapping();
        List<List<UserNode>> shortestRoutes = graphMapping.getShortestNodeListBetweenNodes(user1, user2);
        graphPlotter.highlightRoute(shortestRoutes);
        for(List<UserNode> r: shortestRoutes){
            System.out.println("Shortest: --------> " + r);
        }


        /*Set<UserNode> usersInGivenDEistance = graphMapping.getNodesInDistance(user1, 4);
        graphPlotter.highlightNodes(usersInGivenDEistance, user1);*/


        
        System.out.println("Done!");
        return users;
    }

    public static void main(String[] args) {
        List<UserNode> users = populateDB();
        UserNode user1 = GraphMapping.getUserByID(users, 51);
        UserNode user2 = GraphMapping.getUserByID(users, 1);
        GraphMapping graphMapping = new GraphMapping();
        int distanceBetweenTwoUsers = graphMapping.getTheShortestDistanceBetweenTwoNodes(user1, user2);

    }
}
