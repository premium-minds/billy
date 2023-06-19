/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export.webservice;

import java.util.function.Function;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.premiumminds.billy.portugal.webservices.documents.DeleteInvoiceRequest;
import com.premiumminds.billy.portugal.webservices.documents.DeleteInvoiceResponse;
import com.premiumminds.billy.portugal.webservices.documents.ObjectFactory;
import com.premiumminds.billy.portugal.webservices.documents.RegisterInvoiceRequest;
import com.premiumminds.billy.portugal.webservices.documents.RegisterInvoiceResponse;

@WebService(name = "fatcorewsPort", targetNamespace = "http://factemi.at.min_financas.pt/documents")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        ObjectFactory.class
})
public class StubFatcorewsPort {

    private Function<RegisterInvoiceRequest, RegisterInvoiceResponse> registerInvoiceCallback;
    private Function<DeleteInvoiceRequest, DeleteInvoiceResponse> deleteInvoiceCallback;

    @WebMethod(operationName = "RegisterInvoice")
    @WebResult(name = "RegisterInvoiceResponse", targetNamespace = "http://factemi.at.min_financas.pt/documents", partName = "RegisterInvoiceResponse")
    public RegisterInvoiceResponse registerInvoice(
            @WebParam(name = "RegisterInvoiceRequest", targetNamespace = "http://factemi.at.min_financas.pt/documents", partName = "RegisterInvoiceRequest")
            RegisterInvoiceRequest registerInvoiceRequest) {

        return registerInvoiceCallback.apply(registerInvoiceRequest);
    }

    @WebMethod(operationName = "DeleteInvoice")
    @WebResult(name = "DeleteInvoiceResponse", targetNamespace = "http://factemi.at.min_financas.pt/documents", partName = "DeleteInvoiceResponse")
    public DeleteInvoiceResponse deleteInvoice(
        @WebParam(name = "DeleteInvoiceRequest", targetNamespace = "http://factemi.at.min_financas.pt/documents", partName = "DeleteInvoiceRequest")
        DeleteInvoiceRequest deleteInvoiceRequest){

        return deleteInvoiceCallback.apply(deleteInvoiceRequest);
    }

    void setRegisterInvoiceCallback(final Function<RegisterInvoiceRequest, RegisterInvoiceResponse> callback) {
        this.registerInvoiceCallback = callback;
    }

    void setDeleteInvoiceCallback(final Function<DeleteInvoiceRequest, DeleteInvoiceResponse> callback) {
        this.deleteInvoiceCallback = callback;
    }
}
