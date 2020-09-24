package custq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** 
 * Externalised configuration properties
 * @author regen
 *
 */
@ConfigurationProperties(prefix="queue")
@Component
public class QConfig {
	private static Integer MAX_ENTRIES;
	private static Integer TIMEOUT;

	public static Integer getMaxEntries() {
		return MAX_ENTRIES;
	}

	public void setMaxEntries(Integer maxEntries) {
		MAX_ENTRIES = maxEntries;
	}

	public static Integer getTimeout() {
		return TIMEOUT;
	}

	public void setTimeout(Integer timeout) {
		TIMEOUT = timeout;
	}
}
