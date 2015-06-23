package org.demo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

public class DataLoader {

    private static final Set<DemoDataItem> result = new HashSet<DemoDataItem>();

    public static Set<DemoDataItem> getData(List<File> files, int type) {
        result.clear();
        for (File file : files) {
            try {
                String fileContent = FileUtil.toFileObject(file).asText();
                //Title:
                String titleExpression = "title: [A-Za-z]+";
                Pattern titlePattern = Pattern.compile(titleExpression);
                Matcher titleMatcher = titlePattern.matcher(fileContent);
                //Attribute:
                String attributeExpression = "\\n### [A-Za-z.]+";
                Pattern attributePattern = Pattern.compile(attributeExpression);
                Matcher attributeMatcher = attributePattern.matcher(fileContent);
                //Finders:
                //http://stackoverflow.com/questions/5516119/regular-expression-to-match-characters-at-beginning-of-line-only
                if (titleMatcher.find()) {
                    String formattedTitle = titleMatcher.group().replace("title: ", "kendo");
                    if (type == 1) {
                        result.add(new DemoDataItem(null, formattedTitle, null, escapeHTML(fileContent), null));
                    } else if (type == 2) {
                        while (attributeMatcher.find()) {
                            String attribute = attributeMatcher.group();
//                            System.out.print("title = " + formattedTitle);
//                            System.out.println(" / attribute = " + attribute);
                            String formattedAttribute = attribute.replace("### ", "");
                            result.add(new DemoDataItem(formattedTitle, formattedAttribute, null, null, null));
                        }
                    }
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return result;
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
