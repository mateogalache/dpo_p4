package Business.Characters;

import Business.CharacterManager;
import Business.Party;

public class Wizard extends Character{
    private transient String attacktType;
    private transient String typeOfDamage;

    private transient String attackAction;
    private transient int shield;
    private transient int newShield;
    private transient int valueRest;
    /**
     * Constructor of the class adventurer
     * @param nomPersonatge name of the character
     * @param nomJugador name of the player
     * @param xpPoints experience points of the character
     * @param mind mind stat
     * @param body body stat
     * @param spirit spirit stat
     * @param tipusPersonatge character class
     */
    public Wizard(String nomPersonatge, String nomJugador, int xpPoints, int mind, int body, int spirit, String tipusPersonatge,int actualLifePoints,int totalLifePoints) {
        super(nomPersonatge, nomJugador, xpPoints, mind, body, spirit, tipusPersonatge,actualLifePoints,totalLifePoints);
    }

    /**
     * Constructor of the class adventurer when a character is given
     * @param personatge character to create
     */
    public Wizard(Character personatge) {
        super(personatge.getNomPersonatge(), personatge.getNomJugador(), personatge.getXpPoints(), personatge.getMind(), personatge.getBody(), personatge.getSpirit(), personatge.getTipusPersonatge(), personatge.getActualLifePoints(),personatge.getTotalLifePoints());

    }
    @Override
    public String preparationAction() {
        return " uses Mage Shield. Shield recharges to " + newShield + ".";
    }

    @Override
    public int specificAttack(CharacterManager characterManager, Character attacker, Party party, boolean area) {
        if(area){
            attacktType = "attackAll";
            typeOfDamage = "magical";
            attackAction = "Fireball";
            return characterManager.throwD4() + attacker.getMind();
        }else{
            attacktType = "attackOneSpecific";
            attackAction = "Arcane missile";
            typeOfDamage = "magical";
            return characterManager.throwD6() + attacker.getMind();
        }
    }


    @Override
    public void specificPreparation(Character character, Party party, CharacterManager characterManager) {
        int level = character.getXpPoints()/100 - 1;
        newShield = (characterManager.throwD6() + character.getMind()) * level;
        shield = shield  + newShield;
    }

    @Override
    public int getShield(){
        return shield;
    }

    @Override
    public void setShield(int shield0) {
        shield = Math.max(shield0, 0);
    }

    @Override
    public int specificRestStage(Character character,CharacterManager characterManager) {
        return 0;
    }

    @Override
    public String restStageAction() {
        return null;
    }

    @Override
    public String specificPassive() {
        return null;
    }

    @Override
    public boolean hasPassive() {
        return false;
    }

    @Override
    public String typeSpecificAttack() {
        return attacktType;
    }

    @Override
    public String typeOfDamage() {
        return typeOfDamage;
    }

    @Override
    public String attackAction() {
        return attackAction;
    }

    @Override
    public int specificInitiative(CharacterManager characterManager, Character character) {
        return characterManager.throwD20() + character.getMind();

    }

    @Override
    public int specificLifePoints(Character character, CharacterManager characterManager) {
        return (10 + character.getBody()) * (character.getXpPoints()/100-1);
    }

    @Override
    public int valueRestStage() {
        return valueRest;
    }

    @Override
    public void setValueRestStage(int heal) {
        valueRest = heal;
    }
}
