package com.invest.honduras.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.invest.honduras.request.RequestDocumentStep;
import com.invest.honduras.request.RequestStep;
import com.invest.honduras.request.RequestUpdateUser;
import com.invest.honduras.request.RequestUser;
import com.invest.honduras.request.RequestUserCap;
import com.invest.honduras.request.RequestUserRoleProject;
import com.invest.honduras.response.ResponseGeneral;
import com.invest.honduras.response.ResponseGeneralBool;
import com.invest.honduras.response.ResponseGeneralDocument;
import com.invest.honduras.response.ResponseGeneralProject;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Mono;

public interface ApiBlockchainController {
	
    @ApiOperation(
            value = "Init Project",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, response = ResponseGeneral.class,
            httpMethod = "POST" 
            )
    @ApiResponses(value = {    		
   		 @ApiResponse(code = 200, message = "Exito al obtener la lista de usuario"),
            @ApiResponse(code = 401, message = "No autorizado para obtener el recurso"),
            @ApiResponse(code = 403, message = "El acceso al recurso esta prohibido"),
            @ApiResponse(code = 404, message = " El recurso no pudo ser encontrado")
   })
    Mono<ResponseEntity<ResponseGeneral>> initProject(@RequestBody RequestStep request);
    
 @ApiOperation(
            value = "change step",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, response = ResponseGeneral.class,
            httpMethod = "PUT" 
            )
    Mono<ResponseEntity<ResponseGeneral>> changeStep(@RequestBody RequestStep request);
    
    @ApiOperation(
            value = "add document step",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, response = ResponseGeneral.class,
            httpMethod = "POST" 
            )
    Mono<ResponseEntity<ResponseGeneral>> addDocumentStep(@RequestBody RequestDocumentStep request);
    @ApiOperation(
            value = "add user ",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, response = ResponseGeneral.class,
            httpMethod = "POST" 
            )
    Mono<ResponseEntity<ResponseGeneral>> addUser(@RequestBody RequestUser userRequest);
    
    @ApiOperation(
            value = "update role user",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, response = ResponseGeneral.class,
            httpMethod = "PUT" 
            )
    Mono<ResponseEntity<ResponseGeneral>> updateUser(@RequestBody RequestUpdateUser userRequest);

    @ApiOperation(
            value = "set cap user",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, response = ResponseGeneral.class,
            httpMethod = "PUT" 
            )
    Mono<ResponseEntity<ResponseGeneral>> setCap(@RequestBody RequestUserCap userRequest);
    
    @ApiOperation(
            value = " get all user",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            response = ResponseGeneral.class,
            httpMethod = "POST" 
            )    
    Mono<ResponseEntity<ResponseGeneralBool>> checkCap(@RequestBody RequestUserCap userRequest);
    
    @ApiOperation(
            value = " add user role project",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            response = RequestUserRoleProject.class,
            httpMethod = "POST" 
            )    
    Mono<ResponseEntity<ResponseGeneral>> addUserRoleProject(@RequestBody RequestUserRoleProject request);
    
    @ApiOperation(
            value = " remove user role project",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            response = RequestUserRoleProject.class,
            httpMethod = "DELETE" 
            )
    Mono<ResponseEntity<ResponseGeneral>> removeUserRoleProject(@RequestBody RequestUserRoleProject request);
    
    @ApiOperation(
            value = " has role user",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            response = ResponseGeneral.class,
            httpMethod = "GET" 
            )  
    Mono<ResponseEntity<ResponseGeneralBool>> hasRoleUser(@RequestParam String role, @RequestParam String proxy) ;
    
    @ApiOperation(
            value = " has role user project",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            response = ResponseGeneral.class,
            httpMethod = "GET" 
            )  
    Mono<ResponseEntity<ResponseGeneralBool>> hasRoleUserProject(@RequestParam String role, @RequestParam String proxy,
    		@RequestParam String project);
    
    @ApiOperation(
            value = "get comment",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, response = ResponseGeneralDocument.class,
            httpMethod = "GET" 
            )
    Mono<ResponseEntity<ResponseGeneralDocument>> getComment(@RequestParam String hashProject,
    		@RequestParam String typeDocument, @RequestParam String hashComment) ;
   
    @ApiOperation(
            value = "get document",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, response = ResponseGeneralDocument.class,
            httpMethod = "GET" 
            )
    Mono<ResponseEntity<ResponseGeneralDocument>> getDocument(@RequestParam String hashProject,
    		@RequestParam String typeDocument, @RequestParam String hashDocument) ;
  
    @ApiOperation(
            value = "get Project",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, response = ResponseGeneralProject.class,
            httpMethod = "GET" 
            )
    Mono<ResponseEntity<ResponseGeneralProject>> getProject(@RequestParam String hashProject);
   
}
