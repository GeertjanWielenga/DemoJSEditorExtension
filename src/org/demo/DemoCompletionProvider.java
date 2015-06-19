package org.demo;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.modules.csl.api.CodeCompletionContext;
import org.netbeans.modules.csl.api.CompletionProposal;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.javascript2.editor.spi.CompletionContext;
import org.netbeans.modules.javascript2.editor.spi.CompletionProvider;

@CompletionProvider.Registration(priority = 20)
public class DemoCompletionProvider implements CompletionProvider {

    @Override
    public List<CompletionProposal> complete(
            CodeCompletionContext ccc, 
            CompletionContext cc, 
            String string) {
        List<CompletionProposal> result = 
                new ArrayList<CompletionProposal>();
        DemoDataItem ddi = new DemoDataItem("a", "b", "c", "d");
        result.add(DemoCompletionProposal.createDemoItem(ddi, 0));
        return result;
    }

    @Override
    public String getHelpDocumentation(
            ParserResult pr, 
            ElementHandle eh) {
        return "help documentation";
    }
        
}
