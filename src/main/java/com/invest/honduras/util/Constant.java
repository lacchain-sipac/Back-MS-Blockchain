package com.invest.honduras.util;

public class Constant {

	public static final int STATUS_OK = 00000;
	public static final int STATUS_FAIL = 500;

	public static final String TYPE_NOTIFY_USER_CREATED = "1";
	public static final String HOST_NOTIFY = System.getenv("MS-NOTIFY");
    public static final String HOST_GATEWAY = System.getenv("MS-API-GATEWAY");

	
	public static final String API_URL_USERMAIL = "api/v1/notify/send-mail";
    public static final String API_URL_REFRESH_TOKEN= "api/v1/auth/refresh-token";	
	public static final String API_URL_COMPLETE_PERFIL = "api/v1/notify/send-notify";

	public static final String MESSAGE_USER_EMAIL_EXIST = "Email ya existe";
	public static final String MESSAGE_USER_NOT_EXIST = "Usuario no existe";

	public static final String TYPE_COMPLETE_PASSWORD = "COMPLETE_PASSWORD";
	public static final String TYPE_INVITATION_USER = "INVITATION_USER";
	public static final String TYPE_UPDATE_USER = "UPDATE_USER";
	public static final String TYPE_UPDATE_STATE_USER = "UPDATE_STATE_USER";
	public static final String TYPE_FORGET_PASSWORD = "FORGET_PASSWORD";
	public static final String TYPE_CHANGE_PASSWORD = "CHANGE_PASSWORD";

	public static final String MESSAGE_REFRESH_TOKEN_BAD_REQUEST = "BAD REQUEST REFRESH TOKEN";
	public static final String MESSAGE_REFRESH_TOKEN_UNAUTHORIZED =   "UNAUTHORIZED REFRESH TOKEN";
	public static final String MESSAGE_REFRESH_TOKEN_ERROR = "Internal Error";	
	
	

}
