package Business.Characters;

import Business.CharacterManager;
import Business.Monster;
import Business.Party;

public class Paladin extends Character{
    private transient String attacktType;
    private transient String typeOfDamage = "Psychical";

    private transient String attackAction;
    private transient int newMind;
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
    public Paladin(String nomPersonatge, String nomJugador, int xpPoints, int mind, int body, int spirit, String tipusPersonatge,int actualLifePoints,int totalLifePoints) {
        super(nomPersonatge, nomJugador, xpPoints, mind, body, spirit, tipusPersonatge,actualLifePoints,totalLifePoints);
    }

    /**
     * Constructor of the class adventurer when a character is given
     * @param personatge character to create
     */
    public Paladin(Character personatge) {
        super(personatge.getNomPersonatge(), personatge.getNomJugador(), personatge.getXpPoints(), personatge.getMind(), personatge.getBody(), personatge.getSpirit(), personatge.getTipusPersonatge(), personatge.getActualLifePoints(),personatge.getTotalLifePoints());

    }
    @Override
    public String preparationAction() {
        return " uses Blessing of good luck. Everyone's Mind increases in " + newMind + ".";
    }

    @Override
    public int specificAttack(CharacterManager characterManager, Character attacker, Party party, boolean b) {
        boolean heal = false;
        for(int i = 0; i<party.getPersonatges().length; i++){
            if(party.getPersonatges()[i].getActualLifePoints() < party.getPersonatges()[i].getTotalLifePoints()/2){
                heal = true;
                break;
            }
        }

        if(heal){
            attacktType = "healAll";
            attackAction = "Prayer of mass healing";
            return characterManager.throwD10() + attacker.getMind();
        }else{
            attacktType = "attackOneRandom";
            attackAction = "Never on my watch";
            return characterManager.throwD8() + attacker.getSpirit();
        }
    }
    @Override
    public void specificPreparation(Character character, Party party, CharacterManager characterManager) {
        newMind = characterManager.throwD3();
        for(int i = 0;i<party.getPersonatges().length;i++){
            party.getPersonatges()[i].setMind(party.getPersonatges()[i].getMind() + newMind);
        }
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
        valueRest = characterManager.throwD10() + character.getMind();
        System.out.println("gfdsgdfgf " + valueRest);
        return valueRest;
    }

    @Override
    public String restStageAction() {
        return "Prayer of mass healing";
    }

    @Override
    public int specificPassive(int damageAttack, Character character, Monster attacker) {
        if(attacker.getDamageType().equals(typeOfDamage)){

            return damageAttack / 2;
        }else{
            return damageAttack;
        }
    }

    @Override
    public boolean hasPassive() {
        return true;
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
        return characterManager.throwD10() + character.getSpirit();
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
