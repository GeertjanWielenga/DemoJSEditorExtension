package org.demo;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

public class DataLoader {

    private static final Set<DemoDataItem> items = new HashSet<DemoDataItem>();

    public static Set<DemoDataItem> getData(List<File> files) {
        items.clear();
        for (File file : files) {
            String name = file.getName();
            String formattedName
                    = "kendo"
                    + Character.toUpperCase(name.charAt(0)) + name.substring(1)
                    .replace(".md", "");
            String path = file.getPath();
            try {
                String text = FileUtil.toFileObject(file).asText();
                items.add(new DemoDataItem(formattedName, null, escapeHTML(text), null));
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return items;
    }

    //http://stackoverflow.com/questions/9580684/how-to-retrieve-title-of-a-html-with-the-help-of-htmleditorkit
    public static String escapeHTML(String s) {
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            } else {
                out.append(c);
            }
        }
        String addBreak = out.toString().replace("\n", "<br />\n");;
        return addBreak;
    }

}
