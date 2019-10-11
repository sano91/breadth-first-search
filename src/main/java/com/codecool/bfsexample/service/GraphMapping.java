package com.codecool.bfsexample.service;

import com.codecool.bfsexample.model.UserGraphHelperData;
import com.codecool.bfsexample.model.UserNode;

import java.util.*;


public class GraphMapping {


    //yeah dude it works fine:D
    public int getTheShortestDistanceBetweenTwoNodes(UserNode userNode1, UserNode userNode2) {
        Map<String, Set<UserNode>> visitAndNext = new HashMap<>();
        Set<UserNode> visited = new HashSet<>();
        Set<UserNode> next = new HashSet<>();
        visitAndNext.put("visited", visited);
        visitAndNext.put("next", next);
        return counter(userNode2.getId(), visitAndNext, userNode1);
    }

    // yeah dude, it's also works
    public Set<UserNode> getNodesInDistance(UserNode targetUser, int distance) {
        Set<UserNode> resultUsers = new HashSet<>();
        if (distance < 0) {
            throw new DistanceBelowZeroException();
        } else if (distance == 0) {
            resultUsers.add(targetUser);
            return resultUsers;
        } else {
            return usersInDistance(targetUser, distance);
        }
    }

    private Set<UserNode> usersInDistance(UserNode target, int distance) {
        Map<String, Set<UserNode>> result = new HashMap<>();
        Set<UserNode> resultUsers = new HashSet<>();
        Set<UserNode> nextBatch = new HashSet<>();
        Set<UserNode> currentBatch = new HashSet<>();
        result.put("result", resultUsers);
        result.put("next", nextBatch);
        result.put("current", currentBatch);
        result.get("next").addAll(target.getFriends());


        for (int i = 0; i < distance; i++) {
            result.get("result").addAll(result.get("next"));
            if (i == distance - 1 && i > 0) {                                  //purpose move
                return result.get("result");
            } else if (distance == 1) {
                return target.getFriends();
            }
            result.get("current").clear();
            result.get("current").addAll(result.get("next"));
            result.get("next").clear();
            result.get("current").forEach(userNode -> userNode.getFriends()
                    .stream()
                    .filter(userNode1 -> !(result.get("result").contains(userNode1)))
                    .forEach(userNode2 -> result.get("next").add(userNode2)));
        }
        return null;
    }


    public List<List<UserNode>> getShortestNodeListBetweenNodes(UserNode userNode1, UserNode userNode2) {

        int shortestDist = getTheShortestDistanceBetweenTwoNodes(userNode1, userNode2);
        List<List<UserNode>> result = new ArrayList<>();

        if (userNode1.getFriends().contains(userNode2)) {
            List<UserNode> directFriends = new ArrayList<>();
            directFriends.add(userNode1);
            directFriends.add(userNode2);
            result.add(directFriends);
            return result;
        }

        UserGraphHelperData datas = new UserGraphHelperData(userNode2);
        datas.addVisited(userNode1);

        for (UserNode friend : userNode1.getFriends()) {
            datas.addUserAndRoute(friend, Arrays.asList(userNode1, friend));
            datas.addNewBatchUser(friend);
        }

        for (int i = 0; i < shortestDist - 1; i++) {
            datas = createRoute(datas);
            System.out.println("-" + i + "  " + datas.getAllRoutes());
        }



        datas.fillWinnerRoutes();
        return datas.getWinnerRoutes();
    }

    private UserGraphHelperData createRoute(UserGraphHelperData data) {
        List<UserNode> mainList = new ArrayList<>(data.getNewBatchUsers());
        List<UserNode> batchedUsers = new ArrayList<>(mainList);
        data.addVisiteds(data.getNewBatchUsers());   // added all batched to visited set
        Map<UserNode, List<UserNode>> userAndRoutes = new HashMap<>(data.getCurrentEndUserAndOwnedRoute());
        data.clearNewBatchUsers();
        data.clearAllRoutes();
        data.clearCurrentEndUserAndRoute();
        for (UserNode usernode : batchedUsers) {
            for (UserNode friend : usernode.getFriends()) {
                if (!(data.getVisitedNodes().contains(friend))) {
                    data.addNewBatchUser(friend);
                    List<UserNode> newUserRoute = new ArrayList<>();
                    newUserRoute.addAll(userAndRoutes.get(usernode));
                    newUserRoute.add(friend);
                    data.addNewUserNodeAndItsRoute(friend, newUserRoute);
                    data.addNewRouteToAllRoutes(newUserRoute);
                }
            }
        }
        return data;
    }


    private Map<String, Set<UserNode>> checking(long requestedID, Map<String, Set<UserNode>> map, UserNode currentNode) {
        for (UserNode friend : currentNode.getFriends()) {
            if (!(map.get("visited").contains(friend))) {
                map.get("next").add(friend);
                if (friend.getId() == requestedID) {
                    return null;
                }
            }
        }
        map.get("visited").add(currentNode);
        return map;
    }

    private int counter(long requestedID, Map<String, Set<UserNode>> visitedAndNext, UserNode currentNode) {
        Map<String, Set<UserNode>> currentVisitedAndNext = checking(requestedID, visitedAndNext, currentNode);
        int counter = 1;
        if (currentVisitedAndNext == null) {
            return counter;
        }
        while (currentVisitedAndNext != null) {
            Set<UserNode> belowFriend = currentVisitedAndNext.get("next");
            currentVisitedAndNext.replace("next", new HashSet<>());
            for (UserNode user : belowFriend) {
                currentVisitedAndNext = checking(requestedID, currentVisitedAndNext, user);
                if (currentVisitedAndNext == null) {
                    break;
                }
            }
            counter++;
        }
        return counter;
    }


    public static UserNode getUserByID(List<UserNode> friends, long id) {
        return friends.stream().filter(friend -> friend.getId() == id).findFirst().orElse(null);
    }


}
