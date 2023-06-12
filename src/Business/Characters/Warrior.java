package Business.Characters;

import Business.CharacterManager;
import Business.Party;

public class Warrior extends Character{
    private transient String attacktType;
    private transient String typeOfDamage;

    private transient String attackAction;
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
    public Warrior(String nomPersonatge, String nomJugador, int xpPoints, int mind, int body, int spirit, String tipusPersonatge,int actualLifePoints,int totalLifePoints) {
        super(nomPersonatge, nomJugador, xpPoints, mind, body, spirit, tipusPersonatge,actualLifePoints,totalLifePoints);
    }

    /**
     * Constructor of the class adventurer when a character is given
     * @param personatge character to create
     */
    public Warrior(Character personatge) {
        super(personatge.getNomPersonatge(), personatge.getNomJugador(), personatge.getXpPoints(), personatge.getMind(), personatge.getBody(), personatge.getSpirit(), personatge.getTipusPersonatge(), personatge.getActualLifePoints(),personatge.getTotalLifePoints());

    }
    @Override
    public String preparationAction() {
        return " uses Self-motivated. Their Spirit increases in +1.";
    }

    @Override
    public int specificAttack(CharacterManager characterManager, Character attacker, Party party, boolean b) {
        attacktType = "attackOneSpecific";
        typeOfDamage = "physical";
        attackAction = "Improved sword slash";
        return characterManager.throwD10() + attacker.getBody();
    }
    @Override
    public void specificPreparation(Character character, Party party,CharacterManager characterManager) {
        character.setSpirit(character.getSpirit() + 1);
    }

    @Override
    public int getShield() {
        return 0;
    }

    @Override
    public void setShield(int shield0) {

    }

    @Override
    public int specificRestStage(Character character,CharacterManager characterManager) {
        valueRest =  characterManager.throwD8() + character.getMind();
        return valueRest;
    }

    @Override
    public String restStageAction() {
        return "Bandage time";
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
        return characterManager.throwD12() + character.getSpirit();
    }

    @Override
    public int specificLifePoints(Character character, CharacterManager characterManager) {
        return (10 + character.getBody()) * character.getNivellInicial();
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
