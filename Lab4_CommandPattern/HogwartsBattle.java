/**
 * @author: tahacolak
 * @implNote: in this task receiver return back client(Wizard to Wizard)
 */
import java.util.*;

// Command
  //declares an interface for executing an operation.
interface SpellCommand {
    void execute();
    void undo();
}

// Concrete Commands
  //defines a binding between a Receiver object and an action, implements execute by invoking the corresponding operation(s) on Receiver(wizard)
class AvadaKedavra implements SpellCommand {
    Wizard target;
    public AvadaKedavra(Wizard target) { this.target = target; }
    public void execute() { System.out.println("Avada Kedavra!!!"); target.changeHP(-40); }
    public void undo() { target.changeHP(40); }
}

class Crucio implements SpellCommand {
    Wizard target;
    public Crucio(Wizard target) { this.target = target; }
    public void execute() { System.out.println("Crucio!!!"); target.changeHP(-25); }
    public void undo() { target.changeHP(25); }
}

class Stupify implements SpellCommand {
    Wizard target;
    public Stupify(Wizard target) { this.target = target; }
    public void execute() { System.out.println("Stupify!!!"); target.changeHP(-10); }
    public void undo() { target.changeHP(10); }
}

class Episkey implements SpellCommand {
    Wizard target;
    public Episkey(Wizard target) { this.target = target; }
    public void execute() { System.out.println("Episkey!!!"); target.changeHP(20); }
    public void undo() { target.changeHP(-20); }
}

// Macro Command (Multiple Commands Together)
class MacroSpell implements SpellCommand {
    List<SpellCommand> spells = new ArrayList<>();
    public MacroSpell(Wizard target) {
        spells.add(new Stupify(target));
        spells.add(new AvadaKedavra(target));
        spells.add(new Episkey(target));
    }
    public void execute() { spells.forEach(SpellCommand::execute); }
    public void undo() { spells.forEach(SpellCommand::undo); }
}

// Invoker (Wand)
  //asks the command to carry out the request
class Wand {
    private SpellCommand lastSpell;
    public void cast(SpellCommand spell) {
        lastSpell = spell;
        spell.execute();
    }
    public void reverte() {
        if (lastSpell != null) {
            System.out.println("Reverte!!! Undoing last spell...");
            lastSpell.undo();
        }
    }
}

// Client-Receiver (Wizard)
/*
    Client: creates a ConcreteCommand object and sets its receiver.
    Receiver:knows how to perform the operations associated with carrying out a request. Any class may serve as a Receiver.
 */
class Wizard {
    private int hp = 100;
    private Wand wand = new Wand();
    private SpellCommand spell;
    public void changeHP(int hpChange) {
        hp += hpChange;
        System.out.println("Wizard's HP: " + hp);
        if (hp <= 0) {
            hp=0;
            System.out.print("Wizard is dead!\t");

            System.out.println("Vitality is 0");

        }
    }
    public void create(int spellType, Wizard target) {
        switch (spellType) {
            case 1 -> spell = new AvadaKedavra(target);
            case 2 -> spell = new Crucio(target);
            case 3 -> spell = new Stupify(target);
            case 4 -> spell = new Episkey(target);
            case 5 -> spell = new MacroSpell(target);
            default -> throw new IllegalArgumentException("Invalid spell!");

        }

        wand.cast(spell);
    }
    public boolean isAlive() { return hp > 0; }
}

public class HogwartsBattle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Wizard w1 = new Wizard();
        Wizard w2 = new Wizard();

        while (w1.isAlive() && w2.isAlive()) {
            System.out.println("Choose spell for Wizard 1 (1-5):");
            int spell1 = scanner.nextInt();
            w1.create(spell1, w2);
            w1.castSpell();
            if (!w2.isAlive()) break;

            System.out.println("Choose spell for Wizard 2 (1-5):");
            int spell2 = scanner.nextInt();
            w2.create(spell2, w1);
            w2.castSpell();
            if (!w1.isAlive()) break;
        }
        System.out.println("Battle Over!");
        scanner.close();
    }
}
