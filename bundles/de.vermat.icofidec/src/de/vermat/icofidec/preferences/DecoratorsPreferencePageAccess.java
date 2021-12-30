package de.vermat.icofidec.preferences;

import java.lang.reflect.Field;
import java.text.MessageFormat;

import org.eclipse.core.runtime.ILog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.dialogs.PreferencesUtil;

import de.vermat.icofidec.IconFileDecoratorPlugin;
import de.vermat.icofidec.messages.Messages;

class DecoratorsPreferencePageAccess {

	private final Object page;
	
	private static final String DECORATORS_PREFERENCE_PAGE_ID = "org.eclipse.ui.preferencePages.Decorators";  //$NON-NLS-1$
	private static final String CHECKBOX_VIEWER_FIELD = "checkboxViewer";  //$NON-NLS-1$

	private DecoratorsPreferencePageAccess(Object page) {
		this.page = page;
	}

	public static DecoratorsPreferencePageAccess showPreferencePage(Shell parentShell) {
		DecoratorsPreferencePageAccess pageAccess;
		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(
				parentShell, DECORATORS_PREFERENCE_PAGE_ID, null, null);
		try {
			PreferencePage page = (PreferencePage) dialog.getSelectedPage();
			if (page instanceof IconFileDecoratorPreferencePage) {
				throw new Exception(MessageFormat.format(
						Messages.DecoratorsPreferencePageAccess_selectPreferencePageFailedMessage,
						DECORATORS_PREFERENCE_PAGE_ID));
			}
			pageAccess = new DecoratorsPreferencePageAccess(page);
		} catch (Exception e) {
			pageAccess = null;
			getLog().error(MessageFormat.format(
					Messages.DecoratorsPreferencePageAccess_selectPreferencePageLogMessage, 
					DECORATORS_PREFERENCE_PAGE_ID), e);			
		}
		return pageAccess;
	}
	
	public Object getPage() {
		return page;
	}

	public boolean selectLabelDecoration(String decoratorId) {
		boolean ok;
		try {
			Field field = page.getClass().getDeclaredField(CHECKBOX_VIEWER_FIELD);
			field.setAccessible(true);
			TableViewer viewer = (TableViewer) field.get(page);
			int idx = indexOfPluginContribution(viewer, decoratorId);
			if (idx < 0) {
				throw new Exception(MessageFormat.format(
						Messages.DecoratorsPreferencePageAccess_pluginContributionNotFoundMessage,
						decoratorId,
						field));
			}
			viewer.getTable().setSelection(idx);
			viewer.getTable().showSelection();
			ok = true;			
		} catch (Exception e) {
			ok = false;
			getLog().warn(MessageFormat.format(
					Messages.DecoratorsPreferencePageAccess_selectLabelDecorationLogMessage, 
					decoratorId, DECORATORS_PREFERENCE_PAGE_ID), e);			
		}
		return ok;
	}

	private int indexOfPluginContribution(TableViewer viewer, String decoratorId) {
		int idx = 0;
		while (idx < viewer.getTable().getItemCount()) {
			Object elem = viewer.getElementAt(idx);
			if (elem instanceof IPluginContribution
					&& decoratorId.equals(((IPluginContribution) elem).getLocalId())) {
				break;
			}
			idx++;
		}
		return (idx < viewer.getTable().getItemCount()) ? idx : -1;
	}
	
	private static ILog getLog() {
		return IconFileDecoratorPlugin.getDefault().getLog();
	}
}