package org.ctoolkit.turnonline.gwt.client.ui.feedback;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.web.bindery.event.shared.EventBus;


/**
 * The feedback panel component.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org>Jozef Pohorelec</a>"
 */
public class FeedbackPanel
        extends FlowPanel
{
    private boolean markedForClear;

    private FeedbackRow infoPanel = new FeedbackRow( "feedbackPanelINFO" );

    private FeedbackRow successPanel = new FeedbackRow( "feedbackPanelSUCCESS" );

    private FeedbackRow warnPanel = new FeedbackRow( "feedbackPanelWARNING" );

    private FeedbackRow errorPanel = new FeedbackRow( "feedbackPanelERROR" );

    public FeedbackPanel( EventBus eventBus )
    {
        clear();

        setStyleName( "feedbackPanel" );

        eventBus.addHandler( FeedbackEvent.TYPE, new FeedbackEventHandler()
        {
            @Override
            public void onMessage( FeedbackEvent event )
            {
                log( event.getFeedbackMessage(), event.getFeedbackType() );
            }
        } );

        eventBus.addHandler( ClearFeedbackEvent.TYPE, new ClearFeedbackEventHandler()
        {
            @Override
            public void onClear( ClearFeedbackEvent event )
            {
                clear();
            }
        } );

        eventBus.addHandler( MarkForClearEvent.TYPE, new MarkForClearEventHandler()
        {
            @Override
            public void onMark( MarkForClearEvent event )
            {
                markedForClear = true;
            }
        } );

        add( errorPanel );
        add( warnPanel );
        add( infoPanel );
        add( successPanel );
    }

    public void log( String msg, FeedbackType type )
    {
        if ( markedForClear )
        {
            markedForClear = false;
            clear();
        }

        setVisible( true );

        switch ( type )
        {
            case ERROR:
            {
                errorPanel.addMessage( msg );
                break;
            }
            case WARNING:
            {
                warnPanel.addMessage( msg );
                break;
            }
            case INFO:
            {
                infoPanel.addMessage( msg );
                break;
            }
            case SUCCESS:
            {
                successPanel.addMessage( msg );
                break;
            }
        }
    }

    public void clear()
    {
        infoPanel.clear();
        warnPanel.clear();
        errorPanel.clear();
        successPanel.clear();

        setVisible( false );
    }

    private native void initRowWrapper( Element element ) /*-{
        setTimeout( function ()
        {
            element.classList.add( "fade-out" );
        }, 100 );
    }-*/;

    private class FeedbackRow
            extends FlowPanel
    {
        private FlowPanel messageHolder = new FlowPanel();

        private String styleName;

        private FeedbackRow( String styleName )
        {
            this.styleName = styleName;
        }

        public void clear()
        {
            messageHolder.clear();
            setVisible( false );
        }

        public void addMessage( String message )
        {
            final FlowPanel rowWrapper = new FlowPanel();
            messageHolder.add( rowWrapper );

            rowWrapper.addDomHandler( new MouseOverHandler()
            {
                @Override
                public void onMouseOver( MouseOverEvent event )
                {
                    rowWrapper.removeStyleName( "fade-out" );
                }
            }, MouseOverEvent.getType() );
            rowWrapper.addDomHandler( new MouseOutHandler()
            {
                @Override
                public void onMouseOut( MouseOutEvent event )
                {
                    rowWrapper.addStyleName( "fade-out" );
                }
            }, MouseOutEvent.getType() );

            Button closeButton = new Button();
            closeButton.addClickHandler( new ClickHandler()
            {
                @Override
                public void onClick( ClickEvent event )
                {
                    rowWrapper.removeFromParent();
                }
            } );
            closeButton.setStyleName( "feedbackPanelCloseButton" );
            rowWrapper.add( closeButton );

            rowWrapper.addStyleName( "feedbackPanelWrapper " + styleName );
            add( rowWrapper );

            HTML row = new HTML( message );
            row.setStyleName( "feedbackPanelRow " + styleName );

            rowWrapper.add( row );
            setVisible( true );

            initRowWrapper( rowWrapper.getElement() );
        }
    }

}
