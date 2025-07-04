package com.example.backend.constant;

public class SystemConstant {
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";

    public static final int ADMIN_ROLE_ID = 2;
    public static final int USER_ROLE_ID = 1;

    public static final String AUTH = "auth";
    public static final String ACTIVATED = "Đã kích hoạt";
    public static final String LOCKED = "Đã khóa";
    public static final String NOT_AUTHENTICATION = "Chưa kích hoạt";
    public static final String PRE_PAGE = "previousPage";
    public static final String INVOICE_PROCESSING = "Đang xử lý";
    public static final String INVOICE_SHIPPING = "Đang vận chuyển";
    public static final String INVOICE_CHECKOUT = "Đang xác minh";
    public static final String INVOICE_SHIPPED = "Đã giao";

    public static final String INVOICE_ACCEPTED = "Đã nhận";
    public static final String INVOICE_CANCEL = "Đã hủy";
    public static final short STOP_BUSINESS = 0;
    public static final short IN_BUSINESS = 1;
    public static final String USER_KEYS = "user-keys";
    public static final int NUMBER_OF_CANCEL_ORDERS = 3;
//    public static Map<String, Invoice> waitingPayments = new HashMap<>();
}
