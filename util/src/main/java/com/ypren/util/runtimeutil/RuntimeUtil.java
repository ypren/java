package com.ypren.util.runtimeutil;

public final class RuntimeUtil {

    public static int getProcessor() {
        return Runtime.getRuntime().availableProcessors();
    }
}
