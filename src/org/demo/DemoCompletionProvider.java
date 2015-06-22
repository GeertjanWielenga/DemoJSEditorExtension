package org.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.csl.api.CodeCompletionContext;
import org.netbeans.modules.csl.api.CompletionProposal;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.javascript2.editor.api.lexer.JsTokenId;
import org.netbeans.modules.javascript2.editor.api.lexer.LexUtilities;
import org.netbeans.modules.javascript2.editor.spi.CompletionContext;
import org.netbeans.modules.javascript2.editor.spi.CompletionProvider;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.modules.InstalledFileLocator;

@CompletionProvider.Registration(priority = 20)
public class DemoCompletionProvider implements CompletionProvider {

    private static final List<File> demoPropertyFiles = new ArrayList<File>();

    private static synchronized List<File> getDataFiles() {
        File demoDataFolder = InstalledFileLocator.getDefault().locate(
                "docs/api/javascript/ui",
                "org.demo",
                false);
        for (FileObject fo : FileUtil.toFileObject(demoDataFolder).getChildren()) {
            String name = fo.getNameExt();
            demoPropertyFiles.add((InstalledFileLocator.getDefault().locate(
                    "docs/api/javascript/ui/" + name,
                    "org.demo",
                    false)));
        }
        return demoPropertyFiles;
    }

    private synchronized static Set<DemoDataItem> getData() {
        return DataLoader.getData(getDataFiles());
    }

    @Override
    public List<CompletionProposal> complete(
            CodeCompletionContext ccContext,
            CompletionContext jsCompletionContext,
            String string) {
        if (jsCompletionContext != CompletionContext.OBJECT_PROPERTY) {
            return Collections.EMPTY_LIST;
        }
        List<CompletionProposal> result = new ArrayList<CompletionProposal>();
        Set<DemoDataItem> data = getData();
        for (DemoDataItem item : data) {
            result.add(DemoCompletionProposal.createDemoItem(item, 0));
        }
        return result;
    }

    @Override
    public String getHelpDocumentation(
            ParserResult pr,
            ElementHandle eh) {
        if (eh != null && eh instanceof DemoElementHandle) {
            return ((DemoElementHandle) eh).getDocumentation();
        }
        return null;
    }

}
