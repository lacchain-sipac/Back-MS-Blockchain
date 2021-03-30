package com.invest.honduras.util;

import lombok.Getter;


@Getter
public enum TypeStatusCode {

	OK("00000","Correcto"),
	USER_EMAIL_EXIST("201001","Email ya existe"),
	USER_EMAIL_NOT_EXIST("201002","Usuario no existe"),	
	USER_EMAIL_EMPTY("201003","Email está vacío"),
	USER_EMAIL_NOT_PERMIT("201004","Actualizacion no permitida"),
	USER_ROLE_EMPTY("201005","Rol está vacío"),
	USER_ROLE_NOT_PERMIT("201006","Favor obtener los roles de MS-Parametros , Rol no Permitido "),
	USER_PRIVILEGE_NOT_PERMIT("201007", ""),
	USER_ID_NOT_FOUND("201008", ""),
	USER_PASSWORD_EMPTY("201009", ""),
	USER_TYPE_EMPTY("2010010", ""),
	USER_PASSWORD_NOT_EQUAL("2010011", ""),
	USER_NOT_EXIST("2010012", ""),
	USER_NAME_EMPTY("201013","Los campos surname o fullname no deben estar vacío"),	
	USER_DID_NOT_FOUND("201590","Usuario no está vinculado."),
	USER_DID_EMPTY("201020","Did está vacío"),
	USER_DID_REGISTER("201021","Error en registrar el public key"),
	USER_DID_VALIDATE_PUBLIC("201030","Error en validar el public key"),
	USER_DID_AUD("201021","Audiencia incorrecta"),
	USER_JWT_VALIDATE("201045","Error al validar el public jwt"),
	USER_DID_VALIDATE("201022","Error al validar el public key"),
	USER_STATE_VALIDATE("201046","Estado no encontrado"),
	MESSAGE_USER_TYPE_EMPTY("201014","Tipo vacio o distinto de COMPLETE_PASSWORD o CHANGE_PASSWORD"),	
	MESSAGE_USER_PASSWORD_EMPTY("201014", "Password o la confirmación de password es vacío"),
	MESSAGE_USER_PASSWORD_REGEX("201015", "Password debe contener al menos una mayúscula , una minúscula, caracter especial [!, $, %, @, &] y tamaño entre 8 a 12 caracteres"),
	MESSAGE_USER_PASSWORD_NOT_EQUAL("201016","Password y la confirmación de password son diferentes"),
	MESSAGE_USER_EMAIL_EQUAL_PASSWORD("201017","El email es igual a la contraseña"),
	MESSAGE_USER_EMAIL_NOT_PERMIT("201018","MESSAGE_USER_EMAIL_NOT_PERMIT"),
	MESSAGE_USER_INVALID_VERIFICATION_CODE_2FA("201019","MESSAGE_USER_INVALID_CODE_VERIFICATION_2FA"),
	MESSAGE_ERROR_BLOCKCHAIN("2060001","Mensaje de Error"),
	MESSAGE_ERROR_BLOCKCHAIN_REQUEST_ACCREDITED("2060019","Solicitud ya está acreditado"),
	MESSAGE_ERROR_BLOCKCHAIN_PROJECT_NO_INIT("2060001","Proyecto no está iniciado"),
	MESSAGE_ERROR_BLOCKCHAIN_STEP("2060003","El paso del proyecto está iniciado"),
	MESSAGE_ERROR_BLOCKCHAIN_STEP_NO_EQUAL_DOCUMENT("2060008","El paso actual del proyecto no corresponde con el documento"),
	MESSAGE_ERROR_BLOCKCHAIN_STEP_PREVIUS("2060005","El paso previo no está acreditado"),
	MESSAGE_ERROR_BLOCKCHAIN_DOCUMENT_ACCREDITED("2060009","Este tipo de Documento está acreditado, no permite modificar."),
	MESSAGE_ERROR_BLOCKCHAIN_DOCUMENT_FINISH_ALL("2060010","Faltan acreditar todos los documentos."),
	MESSAGE_ERROR_BLOCKCHAIN_STEP_ACCREDITED("2060010","El paso está acreditado no permite modificar."),
	MESSAGE_ERROR_BLOCKCHAIN_STEP_FILE_NO_PRESENT("2060012","El tipo de documento no está presente en este paso."),
	MESSAGE_ERROR_BLOCKCHAIN_STEP_FLOW_NO_EQUALS("2060013","El tipo contrato de flujo es diferente al actual."),
	MESSAGE_ERROR_BLOCKCHAIN_USER_NO_FOUND_PROJECT("2060014","Usuario no está asignado al proyecto."),
	
	MESSAGE_ERROR_BLOCKCHAIN_COMMENT_NOT_ACCREDITED("3160009","Comentario no acreditado."),
	MESSAGE_ERROR_BLOCKCHAIN_DOCUMENT_NOT_ACCREDITED("3160010","Documento no acreditado."),
	MESSAGE_ERROR_BLOCKCHAIN_PROJECT_NOT_ACCREDITED("3160011","Proyecto no acreditado."),
	
	
	
	
	
	SOLICITUDE_ID_NOT_FOUND ("3001010","Solicitud no encontrado."),
	
	PROCESS_ID_NOT_FOUND ("3101011","Proceso no encontrado."),
	EXECUTE_ID_NOT_FOUND ("3101011","Ejecución no encontrado."),

	PROCESS_ID_ALREADY ("3101013","Proceso ya está inicializado."),
	USER_DISABLED("200001","Usuario deshabilitado"),
	MESSAGE_AUTH_FAIL("200002","Autenticación fallida"),
	MESSAGE_CODE_BAD_REQUEST("200003","Código de Verificación inválido, intente nuevamente"),
	MESSAGE_USER_NOT_ROLE("310009","Usuario no tiene el rol %s"),
	MESSAGE_USER_NOT_ROLE_PROJECT("310009","Usuario no tiene el rol % en el proyecto %s"),
	
	USER_NOT_AUTHORIZED("202001", "User no authorized"),
	
	FILE_NOT_EXISTS("203001", "File not exists"),
	PROJECT_NOT_EXISTS("203001", "PROJECT_NOT_EXISTS"),
	;	
	
	
    
	private String code;
	private String message;

    TypeStatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
