import grails.util.GrailsUtil
import org.springframework.core.io.Resource

class PortletsPlutoGrailsPlugin {
    // the plugin version
    def version = "0.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [portlets: "0.2 > *"]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Kenji Nakamura"
    def authorEmail = "kenji_nakamura@diva-america.com"
    def title = "Apache Pluto portlets Plugin"
    def description = '''\\
This plugin generates pluto portal server specific configuration files. 
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/portlets-pluto"

    def watchedResources = ['file:./grails-app/portlets/**/*Portlet.groovy',
            'file:./plugins/*/grails-app/portlets/**/*Portlet.groovy'
    ]

    def doWithSpring = {
      portletContainerAdapter(org.codehaus.grails.portlets.container.pluto.PlutoPortletContainerAdapter)
    }

    def doWithWebDescriptor = {webXml ->
        if (watchedResources.length > 0) {
            log.info("Creating Pluto servlets for ${watchedResources.length} portlets...")
            for (Resource portlet in watchedResources) {
                def portletName = portlet.filename - 'Portlet.groovy'
                def servletElement = webXml.'servlet'
                servletElement = servletElement[servletElement.size() - 1]
                servletElement + {
                    'servlet'
                    {
                        'servlet-name'(portletName)
                        'servlet-class'('org.apache.pluto.core.PortletServlet')
                        'init-param'
                        {
                            'param-name'('portlet-name')
                            'param-value'(portletName)
                        }
                        'load-on-startup'('1')
                    }
                }
                def mappingElement = webXml.'servlet-mapping'
                mappingElement = mappingElement[mappingElement.size() - 1]
                mappingElement + {
                    'servlet-mapping'
                    {
                        'servlet-name'(portletName)
                        'url-pattern'("/PlutoInvoker/${portletName}")
                    }
                }
            }
        }
    }
    def doWithApplicationContext = { applicationContext ->
    }

    def doWithDynamicMethods = { ctx ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}
