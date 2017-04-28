/*
 * Comvai GWT, Comvai specific GWT components
 * Copyright (C) 2012 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.ctoolkit.turnonline.gwt.client.command;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import org.ctoolkit.turnonline.gwt.client.event.RPCInEvent;
import org.ctoolkit.turnonline.gwt.client.event.RPCOutEvent;


/**
 * <p>BusyIndicatorCallback is extension to {@link AsyncCallback} with extra functionality:</p>
 * <p>On every async call, {@link RPCInEvent} and {@link RPCOutEvent} events are fired which can be handled in busy indicator presenter to show/hide busy indicator component.</p>
 * <p/>
 * <p><b>Example of BusyIndicatorPresenter:</b></p>
 * <pre>
 * public class BusyIndicatorPresenter
 * {
 *    private EventBus eventBus;
 *
 *    private IView view;
 *
 *    public interface IView extends IsWidget
 *    {
 *       void show();
 *
 *       void hide();
 *    }
 *
 *    public BusyIndicatorPresenter( EventBus eventBus, IView view )
 *    {
 *       this.eventBus = eventBus;
 *       this.view = view;
 *    }
 *
 *    public void bind()
 *    {
 *       eventBus.addHandler( RPCInEvent.getType(), new RPCInEventHandler<RPCInEvent>()
 *       {
 *          public void onRPCIn( RPCInEvent event )
 *          {
 *            view.hide();
 *          }
 *        });
 *
 *       eventBus.addHandler( RPCOutEvent.getType(), new RPCOutEventHandler<RPCOutEvent>()
 *       {
 *          public void onRPCOut( RPCOutEvent event )
 *          {
 *             view.show();
 *          }
 *        } );
 *    }
 *
 *    public IView getView()
 *    {
 *       return view;
 *    }
 * }
 * </pre>
 * <p><b>Examplpe of BusyIndicatorView</b></p>
 * <pre>
 * public class BusyIndicatorView extends View implements BusyIndicatorPresenter.IView
 * {
 *    private Label message = new Label( messages.busyText() );
 *
 *    public BusyIndicatorView()
 *    {
 *       message.setStyleName( styles.busyIndicator() );
 *       hide();
 *
 *       initWidget( message );
 *    }
 *
 *    public BusyIndicatorView( String msg )
 *    {
 *       this();
 *       message.setText( msg );
 *    }
 *
 *    public Widget asWidget()
 *    {
 *       return this;
 *    }
 *
 *    public void show()
 *    {
 *    message.setStyleName( styles.busyIndicator() );
 *    }
 *
 *    public void hide()
 *    {
 *      message.addStyleName( styles.busyIndicatorHidden() );
 *    }
 * }
 * </pre>
 * <p/>
 * <p>Client of this library can extends this class to get ability of showing/hiding busy indicator component.</p>
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public abstract class BusyIndicatorCallback<T extends IResponse>
        implements AsyncCallback<T>
{

    protected EventBus eventBus;

    protected BusyIndicatorCallback( EventBus eventBus )
    {
        this.eventBus = eventBus;
        onRPCOut();
    }

    // TODO: Think about introducing an implementation to use the GWT standard onFailure(..) and onSuccess(..) methods.
    @Override
    public void onFailure( Throwable throwable )
    {
        onRPCIn();
        onException( throwable );
    }

    @Override
    public void onSuccess( T t )
    {
        onRPCIn();
        onResponse( t );
    }

    /**
     * This method is called after successful backend response
     *
     * @param response {@link IResponse} from backend
     */
    public abstract void onResponse( T response );

    /**
     * This method is called if exception is thrown in backend
     *
     * @param throwable {@link Throwable}
     */
    public abstract void onException( Throwable throwable );

    /**
     * Fire {@link RPCInEvent} event
     */
    private void onRPCIn()
    {
        eventBus.fireEvent( new RPCInEvent() );
    }

    /**
     * Fire {link RPCOutEvent} event
     */
    private void onRPCOut()
    {
        eventBus.fireEvent( new RPCOutEvent() );
    }
}
