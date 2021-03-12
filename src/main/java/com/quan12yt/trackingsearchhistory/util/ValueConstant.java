package com.quan12yt.trackingsearchhistory.util;

import java.util.regex.Pattern;

public final class ValueConstant {
    public static final String TOPIC = "search-topic";
    public static final int PROXY_PORT = 9999;
    public static final String BOOTSTRAP_SERVER = "localhost:9092";
    public static final Pattern CONNECT_PATTERN = Pattern.compile("CONNECT (.+):(.+) HTTP/(1\\.[01])", Pattern.CASE_INSENSITIVE);
}
