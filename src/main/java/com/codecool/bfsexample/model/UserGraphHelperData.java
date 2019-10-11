package com.codecool.bfsexample.model;

import java.util.*;

public class UserGraphHelperData {

    private Set<UserNode> visitedNodes = new HashSet<>();
    private UserNode destinationUser;
    private List<List<UserNode>> allRoutes = new ArrayList<>();
    private Map<UserNode, List<UserNode>> currentEndUserAndOwnedRoute = new HashMap<>();
    private List<List<UserNode>> winnerRoutes = new ArrayList<>();
    private Set<UserNode> newBatchUsers = new HashSet<>();

    public void clearNewBatchUsers(){
        newBatchUsers.clear();
    }

    public void clearAllRoutes(){
        this.allRoutes.clear();
    }



    public void fillWinnerRoutes(){
        for(List<UserNode> route : this.allRoutes){
            if(route.get(route.size()-1).equals(destinationUser)){
                winnerRoutes.add(route);
            }
        }
    }

    public void addNewUserNodeAndItsRoute(UserNode endUser, List<UserNode> rootRoute){
        this.currentEndUserAndOwnedRoute.put(endUser, rootRoute);
    }

    public void clearCurrentEndUserAndRoute(){
        currentEndUserAndOwnedRoute.clear();
    }

    public void addNewBatchUser(UserNode user){
        newBatchUsers.add(user);
    }

    public void addVisited(UserNode userNode){
        this.visitedNodes.add(userNode);
    }

    public void addVisiteds(Set<UserNode> users){
        visitedNodes.addAll(users);
    }

    public void addUserAndRoute(UserNode user, List<UserNode> route){
        currentEndUserAndOwnedRoute.put(user, route);
    }

    public void addNewRouteToAllRoutes(List<UserNode> route){
        allRoutes.add(route);
    }

    public Set<UserNode> getNewBatchUsers() {
        return newBatchUsers;
    }

    public void setNewBatchUsers(Set<UserNode> newBatchUsers) {
        this.newBatchUsers = newBatchUsers;
    }

    public void setDestinationUser(UserNode destinationUser) {
        this.destinationUser = destinationUser;
    }

    public List<List<UserNode>> getWinnerRoutes() {
        return winnerRoutes;
    }

    public void setWinnerRoutes(List<List<UserNode>> winnerRoutes) {
        this.winnerRoutes = winnerRoutes;
    }

    public Set<UserNode> getVisitedNodes() {
        return visitedNodes;
    }

    public UserGraphHelperData(UserNode user){
        this.destinationUser = user;
    }

    public UserGraphHelperData() {
    }

    public UserNode getDestinationUser() {
        return destinationUser;
    }

    public void setVisitedNodes(Set<UserNode> visitedNodes) {
        this.visitedNodes = visitedNodes;
    }

    public List<List<UserNode>> getAllRoutes() {
        return allRoutes;
    }

    public void setAllRoutes(List<List<UserNode>> allRoutes) {
        this.allRoutes = allRoutes;
    }

    public Map<UserNode, List<UserNode>> getCurrentEndUserAndOwnedRoute() {
        return currentEndUserAndOwnedRoute;
    }

    public void setCurrentEndUserAndOwnedRoute(Map<UserNode, List<UserNode>> currentEndUserAndOwnedRoute) {
        this.currentEndUserAndOwnedRoute = currentEndUserAndOwnedRoute;
    }
}
