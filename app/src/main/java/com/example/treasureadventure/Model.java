package com.example.treasureadventure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class Model {

    Player player;
    Goblin currentGoblin;
    boolean gameOver = false;
    boolean gameWin = false;
    Room startRoom;
    HashSet<Room> rooms = new HashSet<>();
    HashSet<Room> clearedRooms = new HashSet<>();
    boolean isItemSelected = false;
    Item selectedItem;

    Model() {
        startGame();
    }

    private void startGame(){
        startRoom = generateAndConstructDungeon();

        player = new Player(startRoom);

        player.addItemToInventory();
        player.addItemToInventory();
        player.addItemToInventory();
    }

    private Room generateAndConstructDungeon(){
        int roomsNeeded = 6;
        DungeonGenerator generator = new DungeonGenerator();
        generator.generateDungeon(roomsNeeded);

        int[][] dungeonMap = generator.getDungeonMap();
        DungeonConstructor constructor = new DungeonConstructor();
        constructor.constructRooms(dungeonMap);

        rooms = constructor.getRoomsSet();

        return constructor.getStartRoom();
    }

    // To know which move button to deactivate
    public int[] checkAvailableRoomsToMoveTo(){
        int[] availableDirections = {0, 0, 0, 0};

        Room currentRoom = player.getCurrentRoom();
        Map<Direction, Room> connections = currentRoom.getConnections();

        if(connections.get(Direction.UP) != null) availableDirections[0] = 1;
        if(connections.get(Direction.DOWN) != null) availableDirections[1] = 1;
        if(connections.get(Direction.LEFT) != null) availableDirections[2] = 1;
        if(connections.get(Direction.RIGHT) != null) availableDirections[3] = 1;

        return availableDirections;
    }

    public void movePlayer(Direction direction){
        Room currentRoom = player.getCurrentRoom();
        Map<Direction, Room> connections = currentRoom.getConnections();

        Room newRoom = connections.get(direction);
        player.moveTo(newRoom);
        if (player.getCurrentRoom().hasGoblin()) startFightState();
        else if (!clearedRooms.contains(newRoom) && !newRoom.equals(startRoom)) clearedRooms.add(newRoom); // WEIRD WARNING

        checkIfGameWin();
    }

    private void startFightState(){
        currentGoblin = player.getCurrentRoom().getGoblin();
        player.setFightState(true);
    }

    private void stopFightState(){
        player.setFightState(false);

        Room currentRoom = player.getCurrentRoom();
        if (!clearedRooms.contains(currentRoom) && !currentRoom.equals(startRoom)) clearedRooms.add(currentRoom); // WEIRD WARNING

        checkIfGameWin();
    }

    public void fightOrUse(){
        if (isItemSelected && selectedItem.getType() == ItemType.Sword){
            if (currentGoblin.getHP() > selectedItem.getValue()) {
                currentGoblin.setHP(currentGoblin.getHP() - selectedItem.getValue());
                selectedItem.setValue(0);
            }else if (currentGoblin.getHP() == selectedItem.getValue()){
                currentGoblin.setHP(0);
                selectedItem.setValue(0);
                stopFightState();
            } else {
                int difference = selectedItem.getValue() - currentGoblin.getHP(); // can be problem here because not isitemselected = false
                currentGoblin.setHP(0);
                selectedItem.setValue(difference);
                stopFightState();
            }
        } else if (isItemSelected && selectedItem.getType() == ItemType.HealthPotion){
            int playerHealthDifference = player.getMaxHP() - player.getHP();
            if (playerHealthDifference > selectedItem.getValue()){
                player.setHP(player.getHP() + selectedItem.getValue());
                selectedItem.setValue(0);
            }else if (playerHealthDifference <= selectedItem.getValue()){
                int difference = selectedItem.getValue() - playerHealthDifference;
                player.setHP(player.getMaxHP());
                selectedItem.setValue(difference);
            }
        } else if (isItemSelected && selectedItem.getType() == ItemType.EscapePortal){
            player.moveTo(startRoom);
            selectedItem.setValue(0);
            stopFightState();
        } else {
            int savedPlayerHP = player.getHP();
            player.setHP(savedPlayerHP - currentGoblin.getHP());
            currentGoblin.setHP(currentGoblin.getHP() - savedPlayerHP);
            if (currentGoblin.getHP() <= 0) stopFightState();
            else if (player.getHP() <= 0) gameOver();
        }
    }

    private void checkIfGameWin(){
        if (clearedRooms.size() == rooms.size() - 1) gameWin();
    }

    private void gameOver(){
        gameOver = true;
        System.out.println("Game Over");
    }

    private void gameWin(){
        gameWin = true;
        System.out.println("Game Win");
    }

    public int getNumberOfAllRooms(){
        return rooms.size() - 1; // minus start room
    }

    public int getNumberOfclearedRooms(){
        return clearedRooms.size();
    }

    public ArrayList<Item> getInventory(){
        return player.getInventory();
    }
    public void itemSelected(int id){
        isItemSelected = true;
        ArrayList<Item> inventory = getInventory();
        selectedItem = inventory.get(id);
   }

   public void itemDeselected(){
        isItemSelected = false;
   }
}







