package org.ctoolkit.gwt.client.ui.table;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * Extension over {@link ButtonCell} - adds possibility to change icon and tooltip of table cell
 *
 * @author <a href="mailto:pohorelec@comvai.com">Jozef Pohorelec</a>
 */
public class IconButtonCell
        extends ButtonCell
{
    private String icon;

    private String tooltip;

    public IconButtonCell( String icon, String tooltip )
    {
        this.icon = icon;
        this.tooltip = tooltip;
    }

    @Override
    public void render( Context context, SafeHtml data, SafeHtmlBuilder sb )
    {
        if ( tooltip != null )
        {
            onBeforeRender( sb );
        }
        sb.appendHtmlConstant( "<button type=\"button\" tabindex=\"-1\" class=\"icon " + icon + "\">" );
        if ( data != null )
        {
            sb.append( data );
        }
        sb.appendHtmlConstant( "</button>" );
        if ( tooltip != null )
        {
            onAfterRender( sb );
        }
    }

    protected void onBeforeRender( SafeHtmlBuilder sb )
    {
        sb.appendHtmlConstant( "<i title='" + tooltip + "'>" );
    }

    protected void onAfterRender( SafeHtmlBuilder sb )
    {
        sb.appendHtmlConstant( "</i>" );
    }
}
