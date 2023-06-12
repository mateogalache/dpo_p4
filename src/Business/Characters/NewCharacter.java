package Business.Characters;

import Business.CharacterManager;
import Business.Party;

import java.io.IOException;

public class NewCharacter extends Character{
    /**
     * Constructor of the class character
     *
     * @param nomPersonatge    name of the character
     * @param nomJugador       name of the player
     * @param xpPoints         experience of the character
     * @param mind             mind stat
     * @param body             body stat
     * @param spirit           spirit stat
     * @param tipusPersonatge  character class
     * @param actualLifePoints
     * @param totalLifePoints
     */
    public NewCharacter(String nomPersonatge, String nomJugador, int xpPoints, int mind, int body, int spirit, String tipusPersonatge, int actualLifePoints, int totalLifePoints) {
        super(nomPersonatge, nomJugador, xpPoints, mind, body, spirit, tipusPersonatge, actualLifePoints, totalLifePoints);
    }

    @Override
    public String preparationAction() {
        return null;
    }

    @Override
    public int specificAttack(CharacterManager characterManager, Character attacker, Party party, boolean b) {
        return 0;
    }

    @Override
    public void specificPreparation(Character character, Party party, CharacterManager characterManager) throws IOException {

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
        return characterManager.throwD8() + character.getMind();
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
        return null;
    }

    @Override
    public String typeOfDamage() {
        return null;
    }

    @Override
    public String attackAction() {
        return null;
    }

    @Override
    public int specificInitiative(CharacterManager characterManager, Character character) {
        return 0;
    }

    @Override
    public int specificLifePoints(Character character, CharacterManager characterManager) {
        return 0;
    }

    @Override
    public int valueRestStage() {
        return 0;
    }

    @Override
    public void setValueRestStage(int heal) {

    }
}
