package Business;

import Business.Characters.Character;
import Persistance.MonsterDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CombatManager {
    private Combat combat;
    private List<Integer> allInitiatives;
    private List<String> allParticipants;

    private List<Integer> actualLifeMonsters;
    private List<String> onlyMonsters;
    private List<Integer> onlyMonstersInitiatives;

    /**
     * Function to get the monsters from the json
     * @return list of monsters
     * @throws IOException needed to read the json
     */
    public List<Monster> getMonsters() throws IOException {
        MonsterDAO monsterDAO = new MonsterDAO();
        return monsterDAO.readMonstersFromJson();
    }

    /**
     * Function to resize the combat quantity
     * @param combat combat
     * @param i number to remove of combat's quantity (-1)
     */
    private void resizeCombatQuantityLength(Combat combat, int i) {
        int[] auxArray = Arrays.copyOf(combat.getMonstersQuantity(), combat.getMonstersQuantity().length + i);
        combat.setMonstersQuantity(auxArray);
    }

    /**
     * Function to remove monster from a combat
     * @param combat the combat
     * @param index number of combat
     */
    private void removeElementFromArray(Combat combat, int index) {
        int[] auxArray = new int[combat.getMonstersQuantity().length];
        int j = 0;
        for (int i = 0; i < combat.getMonstersQuantity().length; i++) {
            if (i != index) {
                auxArray[j] = combat.getMonstersQuantity()[i];
                j++;
            }

        }
        combat.setMonstersQuantity(auxArray);
        resizeCombatQuantityLength(combat, -1);

    }

    /**
     * Function to get the name of the monsters from the json
     * @return list of monsters' name
     * @throws FileNotFoundException needed to read the json
     */
    public List<String> listMonsterNames() throws FileNotFoundException {
        MonsterDAO monsterDAO = new MonsterDAO();
        List<Monster> monsters = monsterDAO.readMonstersFromJson();
        List<String> monsterNames = new ArrayList<>();

        for (Monster monster : monsters) {
            monsterNames.add(monster.getName());
        }
        return monsterNames;
    }

    /**
     * Function to list the type of monster
     * @return list of type of the monsters
     * @throws FileNotFoundException needed to read the json
     */
    public List<String> listMonsterChallenges() throws FileNotFoundException {
        MonsterDAO monsterDAO = new MonsterDAO();
        List<Monster> monsters = monsterDAO.readMonstersFromJson();
        List<String> monsterChallenge = new ArrayList<>();

        for (Monster monster : monsters) {
            monsterChallenge.add(monster.getChallenge());
        }
        return monsterChallenge;
    }


    /**
     * function that returns the combat managed by the combat manager class
     * @return combat
     */
    public Combat getCombat() {
        return combat;
    }

    /**
     * Function to set the combat
     * @param combat the combat
     */
    public void setCombat(Combat combat) {
        this.combat = combat;
    }

    /**
     * Function to create a combat
     * @param combatIndex number of combat
     * @param monsters list of the monsters to ve added
     * @param monsterQuantity list of the quantity of each monster
     * @return the combat created
     * @throws FileNotFoundException needed to read the json
     */
    public Combat createCombat(int combatIndex, List<String> monsters, List<Integer> monsterQuantity) throws FileNotFoundException{
        MonsterDAO monsterDAO = new MonsterDAO();
        List<Monster> monsterList = new ArrayList<>();

        for (String monsterName: monsters) {
            monsterList.add(monsterDAO.getMonster(monsterName));
        }
        int[] quantity = new int[monsterQuantity.size()];
        for (int i = 0; i < monsterQuantity.size(); i++) {
            quantity[i] = monsterQuantity.get(i);
        }

        return new Combat(combatIndex, monsterList, quantity);
    }

    /**
     * Function to update the list of monsters and quantity list
     * @param index monster to remove
     * @param monsters list of monsters
     * @param quantity list of quantity of each monster
     */
    public void updateCombat(int index, List<String> monsters, List<Integer> quantity) {
        // 1st Remove monster form List
        combat.getMonsters().remove(index);

        // 2nd Remove monster amount from monsterQuantity[]
        removeElementFromArray(combat, index);

        // update lists
        monsters.remove(index);
        quantity.remove(index);
    }

    /**
     * Function to calculate the initiative value of the adventurer
     *
     * @param character character to calculate the initiative
     * @param characterManager characterManager
     * @return initiative value
     */
    public int calculateIniative(Character character, CharacterManager characterManager) {
        return characterManager.getSpecificCharacter(character).specificInitiative(characterManager,character);
    }

    /**
     * Function to get random number (1-12)
     * @return random number
     */
    private int throwd12(){
        Dice dice = new Dice("D12",12);
        return dice.throwDice();
    }

    /**
     * Function to get a monster in the json with his name
     * @param name name of the monster
     * @return the monster
     * @throws FileNotFoundException needed to read the json
     */
    public Monster getMonsterByName(String name) throws FileNotFoundException {
        MonsterDAO monsterDAO = new MonsterDAO();
        return monsterDAO.getMonster(name);
    }

    /**
     * Function to calculate the attack of the monster
     * @param attacker monster that attacks
     * @return random number depending on the damage dice of the monster
     */
    public int calculateMonsterAttack(Monster attacker) {
        int n = Integer.parseInt(attacker.getDamageDice().substring(1));
        Dice dice = new Dice(attacker.getDamageDice(),n);
        return dice.throwDice();
    }

    /**
     * Function to know the multiplier of the attack
     * @return random number (1-10)
     */
    private int diceCombatAttack() {
        Dice dice = new Dice("d10",10);
        return dice.throwDice();
    }

    /**
     * Function to get random number with number of the party
     * @param size size of the paryy
     * @return random number to know who the monster is attacking
     */
    public int randomAttack(int size) {
        String sDice = "d" + size;
        Dice dice = new Dice(sDice,size);
        return dice.throwDice();
    }

    /**
     * Function to order ALL the participants of the combat depending on initiative
     *
     * @param party            party of the adventure
     * @param combat           the combat
     * @param characterManager
     */
    public void orderAllParticipants(Party party, Combat combat, CharacterManager characterManager) {
        allInitiatives = new ArrayList<>();
        allParticipants = new ArrayList<>();
        for(int j=0;j<party.getPersonatges().length;j++){
            allInitiatives.add(calculateIniative(party.getPersonatges()[j],characterManager));
        }
        for(Character character: party.getPersonatges()){
            allParticipants.add(character.getNomPersonatge());
        }
        for(int j=0;j<combat.getMonsters().size();j++){
            for(int m=0;m<combat.getMonstersQuantity()[j];m++){
                allParticipants.add(combat.getMonsters().get(j).getName());
                allInitiatives.add(combat.getMonsters().get(j).getInitiative());
            }
        }
        int n = allInitiatives.size();
        for (int m = 0; m < n - 1; m++) {
            for (int j = 0; j < n - m - 1; j++) {
                if (allInitiatives.get(j) < allInitiatives.get(j + 1)) {
                    // Swap the integers
                    int tempInt = allInitiatives.get(j);
                    allInitiatives.set(j, allInitiatives.get(j + 1));
                    allInitiatives.set(j + 1, tempInt);

                    // Swap the corresponding strings
                    String tempStr = allParticipants.get(j);
                    allParticipants.set(j, allParticipants.get(j + 1));
                    allParticipants.set(j + 1, tempStr);
                }
            }
        }
    }

    /**
     * Function to get the initiatives
     * @return list of the initiatives
     */
    public List<Integer> getAllInitiatives(){
        return allInitiatives;
    }

    /**
     * Function to get ALL participants of the combat
     * @return list of the participants
     */
    public List<String> getAllParticipants(){
        return allParticipants;
    }

    /**
     * Function to order ONLY the monsters of the combat
     * @param combat the combat
     */
    public void orderMonsters(Combat combat){
        onlyMonsters = new ArrayList<>();
        onlyMonstersInitiatives = new ArrayList<>();
        actualLifeMonsters = new ArrayList<>();
        for(int j=0;j<combat.getMonsters().size();j++){
            for(int m=0;m<combat.getMonstersQuantity()[j];m++){
                onlyMonsters.add(combat.getMonsters().get(j).getName());
                onlyMonstersInitiatives.add(combat.getMonsters().get(j).getInitiative());
                actualLifeMonsters.add(combat.getMonsters().get(j).getHitPoints());
            }
        }
        int t = onlyMonstersInitiatives.size();
        for (int m = 0; m < t - 1; m++) {
            for (int j = 0; j < t - m - 1; j++) {
                if (onlyMonstersInitiatives.get(j) < onlyMonstersInitiatives.get(j + 1)) {
                    // Swap the integers
                    int tempInt = onlyMonstersInitiatives.get(j);
                    onlyMonstersInitiatives.set(j, onlyMonstersInitiatives.get(j + 1));
                    onlyMonstersInitiatives.set(j + 1, tempInt);

                    // Swap the corresponding strings
                    String tempStr = onlyMonsters.get(j);
                    onlyMonsters.set(j, onlyMonsters.get(j + 1));
                    onlyMonsters.set(j + 1, tempStr);

                    int tempInt2 = actualLifeMonsters.get(j);
                    actualLifeMonsters.set(j, actualLifeMonsters.get(j + 1));
                    actualLifeMonsters.set(j + 1, tempInt2);
                }
            }
        }
    }

    /**
     * Function to get the list of the monsters ordered
     * @return list of monsters of the combat
     */
    public List<String> getOnlyMonsters(){
        return onlyMonsters;
    }

    /**
     * Function to get actual life of monsters
     * @return list of the life of each monster of the combat
     */
    public List<Integer> getActualLifeMonsters(){
        return actualLifeMonsters;
    }

    /**
     * Function to calculate the multiplier of the attack
     * @return the multiplier
     */
    public int calculateMultiplier() {
        int mult = 1;
        if(diceCombatAttack() < 2){
            mult = 0;
        } else if (diceCombatAttack() == 10) {
            mult = 2;
        }
        return mult;
    }

    /**
     * Function to get the actual life of a character in the party
     * @param party the party
     * @param attacker character to attack
     * @return actual life
     */
    public int getActualLife(Party party,Character attacker){
        int n = 0;
        for(int i=0;i<party.getPersonatges().length;i++){
            if(party.getPersonatges()[i].getNomPersonatge().equals(attacker.getNomPersonatge())){
                n = i;
            }
        }
        return party.getPersonatges()[n].getActualLifePoints();
    }

    /**
     * Function to get the monster to be attacked(monster with less life)
     * @param actualLifeMonsters actual life of each monster
     * @return index of the monster to be attacked
     */
    public int monsterToBeAttackedMoreLife(List<Integer> actualLifeMonsters) {
        int life=0,index=0;
        for(int i=0;i<actualLifeMonsters.size();i++){
            if(actualLifeMonsters.get(i) > life){
                life = actualLifeMonsters.get(i);
                index = i;
            }
        }
        return index;
    }

    public boolean moreThan3Monsters(List<Integer> actualLifeMonsters){
       int counter = 0;
        for(int i=0;i<actualLifeMonsters.size();i++){
            if(actualLifeMonsters.get(i) > 0){
                counter++;
            }
        }
        return counter >= 3;
    }

    public int monsterToBeAttackedRandom(List<Integer> actualLifeMonsters) {
        int indexMonsterToAttack;
        do{
            indexMonsterToAttack = randomAttack(actualLifeMonsters.size()) - 1;
        }while (actualLifeMonsters.get(indexMonsterToAttack) <= 0);
        return indexMonsterToAttack;
    }

    public Character setHabiliitiesCharacters(Character character, CharacterManager characterManager, Party party) throws IOException {
        character = characterManager.getSpecificCharacter(character);
        character.specificPreparation(character,party,characterManager);
        return character;
    }

    public Character setTotalLifePoints(CharacterManager characterManager, Party party,Character character) {
        character.setTotalLifePoints(characterManager.getSpecificCharacter(character).specificLifePoints(character,characterManager));
        character.setActualLifePoints(characterManager.getSpecificCharacter(character).specificLifePoints(character,characterManager));
        return character;
    }

    public Party makeRestStage(int heal, Character character,Party party,int m) {
        if(Objects.equals(character.getTipusPersonatge(), "paladin")){
            for(int i = 0;i<party.getPersonatges().length;i++){
                party.getPersonatges()[i].setActualLifePoints(party.getPersonatges()[i].getActualLifePoints() + heal);
            }
        }else{
            party.getPersonatges()[m].setActualLifePoints(party.getPersonatges()[m].getActualLifePoints() + heal);

        }

        return party;
    }

    public int getHealRestStage(CharacterManager characterManager, Character character) {
        int heal = characterManager.getSpecificCharacter(character).specificRestStage(character, characterManager);
        return heal;
    }

    public boolean isBoss(Monster attacker) {
        return attacker.getChallenge().equals("Boss");
    }

    public int getDamagePassive(int damageAttack, Monster attacker, Character character) {
        if(character.hasPassive()){
            return character.specificPassive(damageAttack,character,attacker);
        }else{
            return damageAttack;
        }

    }
}
