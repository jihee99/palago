package com.ex.ticket.common;

public class PalagoStatic {

	public static final String AUTH_HEADER = "Authorization";
	public static final String BEARER = "Bearer ";
	public static final String WITHDRAW_PREFIX = "DELETED:";
	public static final String TOKEN_ROLE = "role";
	public static final String TOKEN_TYPE = "type";
	public static final String ACCESS_TOKEN = "ACCESS_TOKEN ";
	public static final String REFRESH_TOKEN = "REFRESH_TOKEN ";
	public static final String ACCESS_TOKEN_PREFIX = "Token  ";

	public static final long ACCESS_TOKEN_TIME = 1000 * 60 * 30L; // 30 분 1000ms(=1s) *60=(1min)*30 =(30min)
	public static final long REFRESH_TOKEN_TIME = 1000 * 60 * 60 * 24 * 7L;// 7일

}
