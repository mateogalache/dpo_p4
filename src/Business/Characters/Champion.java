package Business.Characters;

import Business.CharacterManager;
import Business.Monster;
import Business.Party;

import java.io.IOException;

public class Champion extends Character{
    private transient String attacktType;
    private transient String typeOfDamage = "Physical";

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
    public Champion(String nomPersonatge, String nomJugador, int xpPoints, int mind, int body, int spirit, String tipusPersonatge,int actualLifePoints,int totalLifePoints) {
        super(nomPersonatge, nomJugador, xpPoints, mind, body, spirit, tipusPersonatge,actualLifePoints,totalLifePoints);
    }

    /**
     * Constructor of the class adventurer when a character is given
     * @param personatge character to create
     */
    public Champion(Character personatge) {
        super(personatge.getNomPersonatge(), personatge.getNomJugador(), personatge.getXpPoints(), personatge.getMind(), personatge.getBody(), personatge.getSpirit(), personatge.getTipusPersonatge(), personatge.getActualLifePoints(),personatge.getTotalLifePoints());

    }
    @Override
    public String preparationAction() {
        return " uses Motivational speech. Everyone's Spirit increases in +1.";
    }

    @Override
    public int specificAttack(CharacterManager characterManager, Character attacker, Party party, boolean b) {

        attacktType = "attackOneSpecific";
        attackAction = "Improved sword slash";
        return characterManager.throwD10() + attacker.getBody();
    }

    @Override
    public void specificPreparation(Character character, Party party,CharacterManager characterManager) throws IOException {
       for (int i = 0;i<party.getPersonatges().length;i++){
           if(party.getPersonatges()[i].getNomPersonatge().equals(character.getNomPersonatge())){
               break;
           }else{
               if(party.getPersonatges()[i].getTipusPersonatge().equals("champion") || party.getPersonatges()[i].getTipusPersonatge().equals("adventurer") || party.getPersonatges()[i].getTipusPersonatge().equals("warrior")){
                   party.getPersonatges()[i].setSpirit(party.getPersonatges()[i].getSpirit() + 1);
               }
           }
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
        valueRest = character.getTotalLifePoints() - character.getActualLifePoints();
        return valueRest;
    }

    @Override
    public String restStageAction() {
        return "Prayer of self-healing";
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
        return characterManager.throwD12() + character.getSpirit();
    }

    @Override
    public int specificLifePoints(Character character, CharacterManager characterManager) {
        return ((10 + character.getBody()) * (character.getXpPoints()/100-1)) + character.getBody()*(character.getXpPoints()/100-1);
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
