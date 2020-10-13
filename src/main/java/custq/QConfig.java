package custq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Configuration;


/** 
 * Externalised configuration properties
 * @author regen
 *
 */
@ConfigurationProperties(prefix="queue")
@Configuration
public class QConfig {
	private static Integer MAX_ENTRIES=3;
	private static Integer TIMEOUT;

	public static Integer getMaxEntries() {
		System.out.println("GAVSTER---->returning maxEntries:" + MAX_ENTRIES); 
		return MAX_ENTRIES;
	}

	public void setMaxEntries(Integer maxEntries) {
		System.out.println("GAVSTER---->maxEntries:" + maxEntries); 
		MAX_ENTRIES = maxEntries;
	}

	public static Integer getTimeout() {
		System.out.println("GAVSTER---->returning TIMOUT:" + TIMEOUT); 
		return TIMEOUT;
	}

	public void setTimeout(Integer timeout) {
		System.out.println("GAVSTER---->timeout:" + timeout); 
		TIMEOUT = timeout;
	}
}
