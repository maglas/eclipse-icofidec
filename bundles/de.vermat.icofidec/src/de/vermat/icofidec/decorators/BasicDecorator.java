package de.vermat.icofidec.decorators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;

import de.vermat.icofidec.IconFileDecoratorPlugin;
import de.vermat.icofidec.preferences.IconFileDecoratorPreferences;

class BasicDecorator implements IBaseLabelProvider {

	private final IconFileDecoratorPreferences preferences;
	
	private final List<ILabelProviderListener> listeners = new ArrayList<>();
	
	public BasicDecorator() {
		this.preferences = IconFileDecoratorPlugin.getDefault().getPreferences();
	}

	protected IconFileDecoratorPreferences getPreferences() {
		return preferences;
	}
	
	@Override
	public void addListener(ILabelProviderListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		listeners.remove(listener);
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void dispose() {
		listeners.clear();
	}
}