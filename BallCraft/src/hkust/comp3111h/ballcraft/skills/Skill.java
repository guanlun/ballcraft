package hkust.comp3111h.ballcraft.skills;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.server.ServerGameState;
import hkust.comp3111h.ballcraft.server.Unit;

import org.jbox2d.dynamics.Body;

/**
 * Define a skill to be casted
 */
public abstract class Skill {

    private static int skillCount = 0;
    
    protected int id;
    protected int skillID;
    protected int player;
    private long time;
    protected long duration;

    public static Skill getSkill(int id) {
        return getSkill(BallCraft.myself, id);
    }

    public static Skill getSkill(int player, int id) {
        switch (id) {
        case BallCraft.Skill.TEST_SKILL_1:
            return new TestSkill1(player, id);
        case BallCraft.Skill.TEST_SKILL_2:
            return new TestSkill2(player, id);
        case BallCraft.Skill.MINE:
            return new Mine(player, id);
        case BallCraft.Skill.MassOverload:
            return new MassOverload(player, id);
        case BallCraft.Skill.Propel:
            return new Propel(player, id);
        }
        return null;
    }

    public void setTime() {
        time = System.currentTimeMillis();
        skillID = skillCount;
        skillCount++;
        init();
    }

    public int getPlayer() {
        return player;
    }

    public abstract void init();
    public abstract void beforeStep();
    public abstract void afterStep();
    public abstract void finish();

    public String getIDs() {
        return id + "," + skillID + "," + player;
    }

    public boolean isActive() {
        return (System.currentTimeMillis() - time < duration || duration == -1)
                && id != BallCraft.Skill.DEACTIVATED;
    }

    public void deactivate() {
        id = BallCraft.Skill.DEACTIVATED;
    }

    public String toSerializedString() {
        return player+ "&" + id;
    }
    
    protected Unit getUnit()
    {
        return ServerGameState.getStateInstance().getUnits().get(player);
    }
    
    protected Unit getUnit(int i)
    {
        return ServerGameState.getStateInstance().getUnits().get(i);
    }
    
    protected Body getBody()
    {
        return ServerGameState.getStateInstance().getUnits().get(player).getBody();
    }
    
    protected Body getBody(int i)
    {
        return ServerGameState.getStateInstance().getUnits().get(i).getBody();
    }

}
