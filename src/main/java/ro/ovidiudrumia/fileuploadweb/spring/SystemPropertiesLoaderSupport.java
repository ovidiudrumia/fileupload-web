package ro.ovidiudrumia.fileuploadweb.spring;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer;

public class SystemPropertiesLoaderSupport extends PreferencesPlaceholderConfigurer {
	
	@Override
	protected Properties mergeProperties() throws IOException {
		Properties p =	super.mergeProperties();
		p.putAll(System.getenv());
		return p;
	}

}
