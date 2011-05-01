package org.wooddog.woodstub.core.asm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 30-04-11
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class SymbolicLinkTable {
    List<SymbolicLink> links;

    public SymbolicLinkTable() {
        links = new ArrayList<SymbolicLink>();
    }

    public SymbolicLink getByName(int reference) {
        for (SymbolicLink link : links) {
            if (link.getReference() == reference) {
                return link;
            }
        }

        return null;
    }

    public SymbolicLink getByReference(String name) {
        for (SymbolicLink link : links) {
            if (name.equals(link.getName())) {
                return link;
            }
        }

        return null;
    }

    public void addSymbolicLink(SymbolicLink link) {
        links.add(link);
    }

}
