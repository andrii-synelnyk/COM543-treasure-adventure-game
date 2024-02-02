package com.example.treasureadventure;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Model {
    @XmlElement
    Player player;
    @XmlElement
    Goblin currentGoblin;
    @XmlElement
    boolean gameOver = false;
    @XmlElement
    boolean gameWin = false;
    @XmlElement
    Room startRoom;
    @XmlElement
    HashSet<Room> rooms = new HashSet<>();
    @XmlElement
    HashSet<Room> clearedRooms = new HashSet<>();
    @XmlElement
    boolean isItemSelected = false;
    @XmlElement
    Item selectedItem;
    private final Random random = new Random();
    @XmlElement
    Direction directionBack;

    Model() {
        startGame();
    }

    private void startGame(){
        startRoom = generateAndConstructDungeon();
        spawnGoblins();

        player = new Player(startRoom);

        player.addItemToInventory();
    }

    private Room generateAndConstructDungeon(){
        int roomsNeeded = 10;
        DungeonGenerator generator = new DungeonGenerator();
        generator.generateDungeon(roomsNeeded);

        int[][] dungeonMap = generator.getDungeonMap();
        DungeonConstructor constructor = new DungeonConstructor();
        constructor.constructRooms(dungeonMap);

        rooms = constructor.getRoomsSet();

        return constructor.getStartRoom();
    }

    private void spawnGoblins(){
        int goblinsNeedsToSpawn = (int) Math.ceil((rooms.size() - 1) * 0.3);
        ArrayList<Room> roomsArray = new ArrayList<>(rooms);
        ArrayList<Room> roomsAlreadySpawned = new ArrayList<>();
        System.out.println("Goblins needs to be spawned: " + goblinsNeedsToSpawn);

        for (int spawned = 0; spawned < goblinsNeedsToSpawn; spawned++){
            int roomToSpawnGoblinIn = random.nextInt(roomsArray.size());
            Room roomGrabbed = roomsArray.get(roomToSpawnGoblinIn);

            if(roomsAlreadySpawned.contains(roomGrabbed) || roomGrabbed.isStartRoom()){
                spawned--;
            } else {
                roomGrabbed.spawnGoblin();
                roomsAlreadySpawned.add(roomGrabbed);
            }
        }
    }

    // To know which move button to deactivate
    public int[] checkAvailableRoomsToMoveTo(){
        int[] availableDirections = {0, 0, 0, 0};

        Room currentRoom = player.getCurrentRoom();
        Map<Direction, Integer> connections = currentRoom.getConnections();

        if(connections.get(Direction.UP) != -1) availableDirections[0] = 1;
        if(connections.get(Direction.DOWN) != -1) availableDirections[1] = 1;
        if(connections.get(Direction.LEFT) != -1) availableDirections[2] = 1;
        if(connections.get(Direction.RIGHT) != -1) availableDirections[3] = 1;

        return availableDirections;
    }

    public void movePlayer(Direction direction){
        saveDirectionBack(direction);

        Room currentRoom = player.getCurrentRoom();
        Map<Direction, Integer> connections = currentRoom.getConnections();
        Room newRoom = null;
        int newRoomId = connections.get(direction);
        for (Room room : rooms){
            if (room.getId() == newRoomId) newRoom = room;
        }
        player.moveTo(newRoom);
        if (player.getCurrentRoom().hasGoblin()) startFightState();
        else if (!clearedRooms.contains(newRoom) && !newRoom.equals(startRoom)) {
            clearedRooms.add(newRoom);
            dropItem(0.3f);
        }

        checkIfGameWin();
    }

    private void startFightState(){
        currentGoblin = player.getCurrentRoom().getGoblin();
        player.setFightState(true);
    }

    private void stopFightState(boolean killedGoblin){
        player.setFightState(false);

        if (killedGoblin) {
            Room currentRoom = player.getCurrentRoom();
            clearedRooms.add(currentRoom);
            dropItem(0.6f);

            checkIfGameWin();
        }
    }

    public void fightOrUse(){
        if (isItemSelected && selectedItem.getType() == ItemType.Sword){
            if (currentGoblin.getHP() > selectedItem.getValue()) {
                currentGoblin.setHP(currentGoblin.getHP() - selectedItem.getValue());
                selectedItem.setValue(0);
            }else if (currentGoblin.getHP() == selectedItem.getValue()){
                currentGoblin.setHP(0);
                selectedItem.setValue(0);
                stopFightState(true);
            } else {
                int difference = selectedItem.getValue() - currentGoblin.getHP();
                currentGoblin.setHP(0);
                selectedItem.setValue(difference);
                stopFightState(true);
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
            escape();
            selectedItem.setValue(0);
            stopFightState(false);
            System.out.println("Portal value: " + selectedItem.getValue());
        } else {
            int savedPlayerHP = player.getHP();
            player.setHP(savedPlayerHP - currentGoblin.getHP());
            currentGoblin.setHP(currentGoblin.getHP() - savedPlayerHP);
            if (currentGoblin.getHP() <= 0) stopFightState(true);
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

   private void dropItem(float chance){
       Random random = new Random();

       if (random.nextFloat() <= chance) {
           player.addItemToInventory();
       }
   }

   private void escape(){
        movePlayer(directionBack);
   }

   private void saveDirectionBack(Direction directionForward){
        switch (directionForward){
            case UP:
                directionBack = Direction.DOWN;
                break;
            case DOWN:
                directionBack = Direction.UP;
                break;
            case LEFT:
                directionBack = Direction.RIGHT;
                break;
            case RIGHT:
                directionBack = Direction.LEFT;
                break;
        }
   }
}







