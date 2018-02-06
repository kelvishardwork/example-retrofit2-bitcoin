package com.brakeel.exampleretrofit2bitcoin.util;

/**
 * Classes responsavel pelos atributos estaticos fornecidos para a API
 */
public class ApiClient {

    public static final String BASE_URL = "https://www.mercadobitcoin.net/api/";
    public static final int SUCCESS = 200;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int UNPROCESSABLE_ENTITY = 422;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int INTERNET_NOT_AVAILABLE = 901;
}
