package de.vermat.icofidec;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.vermat.icofidec.preferences.IconFileDecoratorPreferences;

public class IconFileDecoratorPlugin extends AbstractUIPlugin {
	
	public static final String PLUGIN_ID = "de.vermat.icofidec"; //$NON-NLS-1$
	
	private IconFileDecoratorPreferences preferences;
	
	private static IconFileDecoratorPlugin plugin;
	
	public static IconFileDecoratorPlugin getDefault() {
		return plugin;
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		plugin.preferences = new IconFileDecoratorPreferences(plugin.getPreferenceStore());
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin.preferences = null;
		plugin = null;
		super.stop(context);
	}
	
	public IconFileDecoratorPreferences getPreferences() {
		return preferences;
	}
}