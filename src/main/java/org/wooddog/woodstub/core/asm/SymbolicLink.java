package org.wooddog.woodstub.core.asm;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 30-04-11
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class SymbolicLink {
    private String name;
    private int reference;

    public SymbolicLink(String name, int reference) {
        this.name = name;
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }
}
