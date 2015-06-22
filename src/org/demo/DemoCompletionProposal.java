package org.demo;

import java.util.Set;
import javax.swing.ImageIcon;
import org.netbeans.modules.csl.api.CompletionProposal;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;
import org.netbeans.modules.csl.api.Modifier;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle.Messages;

public class DemoCompletionProposal implements CompletionProposal {

    static CompletionProposal createDemoItem(DemoDataItem item, int anchorOffset, String prefix) {
        ElementHandle element = new DemoElementHandle(item.getName(), item.getDocumentation(), ElementKind.PROPERTY);
        return new DemoCompletionProposal(item, anchorOffset, element, prefix);
    }

    private final int anchorOffset;
    private final ElementHandle element;
    private final DemoDataItem dataItem;
    private String prefix;

    public DemoCompletionProposal(DemoDataItem item, int anchorOffset, ElementHandle element, String prefix) {
        this.anchorOffset = anchorOffset;
        this.element = element;
        this.dataItem = item;
        this.prefix = prefix;
    }

    @Override
    public int getAnchorOffset() {
        return anchorOffset;
    }

    @Override
    public ElementHandle getElement() {
        return element;
    }

    @Override
    public String getName() {
        return element.getName();
    }

    @Override
    public String getInsertPrefix() {
        return element.getName();
    }

    @Override
    public String getSortText() {
        return getName();
    }

    @Override
    public String getLhsHtml(HtmlFormatter formatter) {
        formatter.reset();
        formatter.appendText(getName());
        if (dataItem.getType() != null) {
            formatter.appendText(": "); //NOI18N
            formatter.type(true);
            formatter.appendText(dataItem.getType());
            formatter.type(false);
        }
        return formatter.getText();
    }

    @Messages("DemoCompletionItem.lbl.demo.framework=Demo")
    @Override
    public String getRhsHtml(HtmlFormatter formatter) {
        return Bundle.DemoCompletionItem_lbl_demo_framework();
    }

    @Override
    public ElementKind getKind() {
        return element.getKind();
    }

    @Override
    public ImageIcon getIcon() {
        return ImageUtilities.loadImageIcon("org/demo/icon.jpg", true);
    }

    @Override
    public Set<Modifier> getModifiers() {
        return element.getModifiers();
    }

    @Override
    public boolean isSmart() {
        return false;
    }

    @Override
    public int getSortPrioOverride() {
        return 22;
    }

    @Override
    public String getCustomInsertTemplate() {
        return getName().replace(prefix, ""); //NOI18N
    }

}
