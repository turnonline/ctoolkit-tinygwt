package org.ctoolkit.gwt.client.ui.table;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.cellview.client.CellTable;

/**
 * Editable cell table
 *
 * @author <a href="mailto:pohorelec@comvai.com">Jozef Pohorelec</a>
 */
public class EditableCellTable<T> extends CellTable<T>
{
    public EditableCellTable()
    {
        this( Integer.MAX_VALUE, new Table.ResourceProxy());
    }

    public EditableCellTable( int pageSize, Resources resources )
    {
        super( pageSize, resources );
        this.addHandler(tabHandler, KeyDownEvent.getType());
    }

    /**
     * Get the browser type. This is needed to check for IE.
     *
     * @return user agent
     */
    public static native String getUserAgent() /*-{
        return navigator.userAgent.toLowerCase();
    }-*/;

    /**
     * Only for IE8 fixes. IE8 doesn't seem to know about preventDefault.
     *
     * @param event the event which is to be prevented.
     */
    public static native void preventDefaultBehaviour(NativeEvent event) /*-{
        event.returnValue = false;
        $wnd.event.cancelBubble = true;
    }-*/;

    /**
     * stop the currently active execution.
     */
    public static native void halt() /*-{
        throw "stop";
    }-*/;

    /**
     * Key Down event handler to handle all TAB and SHIFT+TAB.
     */
    protected KeyDownHandler tabHandler = new KeyDownHandler() {

        public void onKeyDown(KeyDownEvent event) {

            // find the element on which TAB is triggered.
            Element tabElem = event.getNativeEvent().getEventTarget().cast();
            Element td;

            if (tabElem != null) {
                td = getParentTD(tabElem);

                if (td != null
                        && event.getNativeEvent().getKeyCode() == KeyCodes.KEY_TAB) {

                    // SHIFT + TAB
                    if (event.isShiftKeyDown()) {
                        Element prevSibling = td.getPreviousSiblingElement();
                        if (prevSibling == null) {
                            gotoPrevRow(td, event);
                        } else {
                            setFocus(prevSibling, "backward", event);

                            event.getNativeEvent().preventDefault();
                            event.getNativeEvent().stopPropagation();

                            if (getUserAgent().contains("msie")) {
                                preventDefaultBehaviour(event.getNativeEvent());
                            }
                        }
                    } else { // just TAB
                        Element nextSibling = td.getNextSiblingElement();
                        if (nextSibling == null) {
                            gotoNextRow(td, event);
                        } else {
                            setFocus(nextSibling, "forward", event);

                            event.getNativeEvent().preventDefault();
                            event.getNativeEvent().stopPropagation();

                            if (getUserAgent().contains("msie")) {
                                preventDefaultBehaviour(event.getNativeEvent());
                            }
                        }
                    }
                }
            }
        }

        /**
         * When current TD becomes the last one in the row, and TAB is hit, this
         * will get called. This method will set the focus on the first element
         * in the next row.
         *
         * @param td td the Cell/TD element.
         */
        private void gotoNextRow( Element td, KeyDownEvent event) {

            // find current row
            Element thisTr = td.getParentElement();

            // find next row
            Element nextTr = thisTr.getNextSiblingElement();
            if (nextTr == null) {
                halt();
            } else {
                event.getNativeEvent().preventDefault();
                event.getNativeEvent().stopPropagation();

                if (getUserAgent().contains("msie")) {
                    preventDefaultBehaviour(event.getNativeEvent());
                }
            }

            Element firstTd = nextTr.getFirstChildElement();

            // set focus in the first td's input element
            setFocus(firstTd, "forward", event);
        }

        /**
         * When current TD becomes the first one in the row, and SHIFT+TAB is
         * hit, this will get called. This method will set the focus on the last
         * element in the previous row.
         *
         * @param td the Cell/TD element.
         */
        private void gotoPrevRow( Element td, KeyDownEvent event) {

            // find current row
            Element thisTr = td.getParentElement();

            Element prevTr = thisTr.getPreviousSiblingElement();

            if (prevTr == null) {
                halt();
            } else {
                event.getNativeEvent().preventDefault();
                event.getNativeEvent().stopPropagation();

                if (getUserAgent().contains("msie")) {
                    preventDefaultBehaviour(event.getNativeEvent());
                }
            }

            // get last td inside tr
            Element lastTd = prevTr.getLastChild().cast();

            // set focus in the last td's input element
            setFocus(lastTd, "backward", event);
        }

        /**
         * set focus on the inner input field.
         *
         * @param td Cell/TD tag.
         * @param dir if no input field found, search in which direction?
         */
        private void setFocus( Element td, final String dir, KeyDownEvent event) {
            Element input = td.getFirstChildElement().getFirstChildElement();
            if (input != null
                    && (input.getTagName().equalsIgnoreCase("input")
                    || input.getTagName().equalsIgnoreCase("select")
                    || input.getTagName().equalsIgnoreCase("button"))) {
                input.focus();
            } else {
                /**
                 * if null, go to next element, either forward or backward
                 * direction. This will help if some cells have plain text or
                 * disabled fields or alike.
                 */
                if ("forward".equalsIgnoreCase(dir)) {
                    if (td.getNextSiblingElement() == null) {
                        gotoNextRow(td, event);
                    } else {
                        setFocus(td.getNextSiblingElement(), dir, event);
                    }
                } else {
                    if (td.getPreviousSiblingElement() == null) {
                        gotoPrevRow(td, event);
                    } else {
                        setFocus(td.getPreviousSiblingElement(), dir, event);
                    }
                }
            }
        }

        /**
         * Recursive method to traverse up the DOM tree till TD is found.
         *
         * @param e e child seeking parent TD.
         * @return Element parent TD element.
         */
        private Element getParentTD( Element e) {
            Element parent = e.getParentElement();
            if (parent == null) {
                return null;
            }
            if (parent.getTagName().equalsIgnoreCase("td")) {
                return parent;
            }
            return getParentTD(parent);
        }
    };

    public void focusCell( int row, int cell )
    {
        Element input = ( Element ) getRowElement( row ).getChild( cell ).getChild( 0 ).getChild( 0 );
        input.focus();
        setSelectionRange( input, 0, ( ( InputElement ) input ).getValue().length() );
    }

    public native void setSelectionRange( Element elem, int pos, int length ) /*-{
        try
        {
            elem.setSelectionRange( pos, pos + length );
        }
        catch ( e )
        {
            // Firefox throws exception if TextBox is not visible, even if attached
        }
    }-*/;
}
