package org.skycrawl.wsdemo.localservice.controller;

import java.net.MalformedURLException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.apache.cxf.annotations.WSDLDocumentation;
import org.skycrawl.wsdemo.localservice.view.Certificate;
import org.skycrawl.wsdemo.localservice.view.RegisterResult;
import org.skycrawl.wsdemo.localservice.view.StatsResult;
import org.skycrawl.wsdemo.localservice.view.exceptions.IncorrectArgumentException;
import org.skycrawl.wsdemo.localservice.view.exceptions.SiteNotRegisteredException;
import org.skycrawl.wsdemo.localservice.view.exceptions.TLDNotRegisteredException;

@WebService(targetNamespace = "http://wsdemo.skycrawl.org")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@WSDLDocumentation("A simple service to map internet domains to associated root certificate authorities and retrieve some related statistics.")
public interface AuthorityService
{
    @WebMethod(operationName = "Register")
    @RequestWrapper
    @ResponseWrapper
    @WebResult(name = "NotRegistered")
    @WSDLDocumentation("Operation to register sites to the system.")
    RegisterResult registerSites(
    		@WebParam(name = "Sites", partName = "sites") String[] sites
    );
    
    @WebMethod(operationName = "GetAuthority")
    @RequestWrapper
    @ResponseWrapper
    @WebResult(name = "Authority")
    @WSDLDocumentation("Operation to retrieve root certificate authority for the given registered site/domain.")
    Certificate getRootForSite(
    		@WebParam(name = "Site", partName = "site") String site
    ) throws MalformedURLException, SiteNotRegisteredException;
    
    @WebMethod(operationName = "GetTLDStats")
    @RequestWrapper
    @ResponseWrapper
    @WebResult(name = "TLDStats")
    @WSDLDocumentation("Operation to retrieve all registered root certificate authorities for the given top-level domain. The count of registered domains will also be returned for each authority.")
    StatsResult getRootStatsForTLD(
    		@WebParam(name = "TLD", partName = "tld") String tld
    ) throws TLDNotRegisteredException, IncorrectArgumentException;
    
    @WebMethod(operationName = "GetDomains")
    @RequestWrapper
    @ResponseWrapper
    @WebResult(name = "Domains")
    @WSDLDocumentation("Operation to retrieve all registered domains for the given authority and top-level domain.")
    List<String> getDomainsForRootAndTLD(
    		@WebParam(name = "RootID", partName = "rootID") String rootID,
    		@WebParam(name = "TLD", partName = "tld") String tld
    ) throws TLDNotRegisteredException, IncorrectArgumentException;
}
