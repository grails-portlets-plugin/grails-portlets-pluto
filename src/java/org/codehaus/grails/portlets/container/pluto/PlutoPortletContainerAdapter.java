package org.codehaus.grails.portlets.container.pluto;

import org.codehaus.grails.portlets.container.PortletContainerAdapter;
import org.codehaus.grails.portlets.container.AbstractPortletContainerAdapter;
import org.apache.pluto.internal.impl.PortletContextImpl;
import org.apache.pluto.internal.impl.PortletConfigImpl;
import org.apache.pluto.internal.impl.PortletRequestImpl;

import javax.portlet.PortletContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kenji Nakamura
 */
public class PlutoPortletContainerAdapter extends AbstractPortletContainerAdapter {

    public ServletContext getServletContext(PortletContext context) throws UnsupportedOperationException {
        return ((PortletContextImpl) context).getServletContext();
    }

    public ServletConfig getServletConfig(PortletConfig config) throws UnsupportedOperationException {
        return ((PortletConfigImpl) config).getServletConfig();
    }

    public HttpServletRequest getHttpServletRequest(PortletRequest portletRequest) throws UnsupportedOperationException {
        HttpServletRequest request = ((PortletRequestImpl)portletRequest).getHttpServletRequest();
        //Make sure we have an underlying http session and that it won't time out - PLUTO BUG
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(-1);
        return request;
    }

    /**
     * Returns the underlying HttpServletResponse.
     *
     * @param portletResponse portlet response
     * @return http servlet request
     * @throws UnsupportedOperationException thrown when the operation is not possible with the underlying portlet container
     */
    public HttpServletResponse getHttpServletResponse(PortletResponse portletResponse) throws UnsupportedOperationException {
        return (HttpServletResponse) portletResponse;
    }


}
