package org.demo;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataLoader {

    private static final Set<DemoDataItem> items = new HashSet<DemoDataItem>();
    public static Set<DemoDataItem> getData(List<File> files) {
        items.clear();
        for (File file : files) {
            String name = file.getName();
            String formattedName = 
                    "kendo"+
                    Character.toUpperCase(name.charAt(0)) + name.substring(1)
                    .replace(".md", "");
            String path = file.getPath();
            items.add(new DemoDataItem(formattedName, null, path, null));
        }
        return items;
    }

}
