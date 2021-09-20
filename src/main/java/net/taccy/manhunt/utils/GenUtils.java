package net.taccy.manhunt.utils;

import java.util.concurrent.ThreadLocalRandom;

public class GenUtils {

    public static int generateRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
