package com.stackimpact.agent;

import java.util.Random;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.UUID;
import java.lang.management.ManagementFactory;


public class AgentUtils {

    public final static String AGENT_FRAME_PREFIX = "com.stackimpact.agent";


	public static long timestamp() {
		return System.currentTimeMillis() / 1000L;
	}


    public static long millis() {
        return System.currentTimeMillis();
    }


    public static long nanos() {
        return System.nanoTime();
    }


	public static String generateUUID() throws Exception {
        UUID uuid = UUID.randomUUID();
        return generateSHA1(uuid.toString());
	}


    public static String getSystemTempDir() {
        if(System.getProperty("java.io.tmpdir") != null) {
            return System.getProperty("java.io.tmpdir");
        }
        else {
            return "/tmp"; // Unix only
        }
    }


    public static String getPID() {
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        int indexOf = pid.indexOf('@');
        if (indexOf > 0) {
            pid = pid.substring(0, indexOf);
        }
        return pid;
    }


    public static String getOSTag() {
    	String osName = System.getProperty("os.name").toLowerCase();
    	if (osName.indexOf("linux")  >= 0) {
    		return "linux";
    	}
    	else if (osName.indexOf("mac") >= 0) {
    		return "macos";
    	}
    	else if (osName.indexOf("windows") >= 0) {
    		return "win";
    	}

    	return null;
    }


    public static double getJavaVersion() {
        String version = System.getProperty("java.version");
        int pos = version.indexOf('.');
        pos = version.indexOf('.', pos+1);
        return Double.parseDouble(version.substring (0, pos));
    }
    

    public static String generateSHA1(String str) throws Exception {
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(str.getBytes("UTF-8"));

        Formatter formatter = new Formatter();
        for (byte b : crypt.digest()) {
            formatter.format("%02x", b);
        }

        String result = formatter.toString();
        formatter.close();

        return result;
    }


    public static boolean isAgentFrame(String name) {
        return name.startsWith(AGENT_FRAME_PREFIX);
    }

}