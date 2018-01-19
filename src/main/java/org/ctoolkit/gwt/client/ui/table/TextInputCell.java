package org.ctoolkit.gwt.client.ui.table;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * Same as {@link com.google.gwt.cell.client.TextInputCell} with addition to
 * change class name and placeholder attributes.
 *
 * @author <a href="mailto:pohorelec@comvai.com">Jozef Pohorelec</a>
 */
public class TextInputCell
        extends com.google.gwt.cell.client.TextInputCell
{
    private String className;

    private String placeholder;

    private static Template template;
    private static TemplateNullValue templateNullValue;

    interface TemplateNullValue
            extends SafeHtmlTemplates
    {
        @Template( "<input type=\"text\" class=\"{0}\" placeholder=\"{1}\" tabindex=\"-1\"></input>" )
        SafeHtml input( String className, String placeholder );
    }

    interface Template
            extends SafeHtmlTemplates
    {
        @Template( "<input type=\"text\" class=\"{0}\" value=\"{1}\" placeholder=\"{2}\" tabindex=\"-1\"></input>" )
        SafeHtml input( String className, String value, String placeholder );
    }

    public TextInputCell( String className )
    {
        this( className, "" );
    }

    public TextInputCell( String className, String placeholder )
    {
        this.className = className;
        this.placeholder = placeholder;

        if ( template == null )
        {
            template = GWT.create( Template.class );
        }
        if ( templateNullValue == null )
        {
            templateNullValue = GWT.create( TemplateNullValue.class );
        }
    }

    @Override
    public void render( Context context, String value, SafeHtmlBuilder sb )
    {
        // Get the view data.
        Object key = context.getKey();
        ViewData viewData = getViewData( key );
        if ( viewData != null && viewData.getCurrentValue().equals( value ) )
        {
            clearViewData( key );
            viewData = null;
        }

        String s = ( viewData != null ) ? viewData.getCurrentValue() : value;
        if ( s != null )
        {
            sb.append( template.input( className, s, placeholder ) );
        }
        else
        {
            sb.append( templateNullValue.input( className, placeholder ) );
        }
    }
}
