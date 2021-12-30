package de.vermat.icofidec.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.vermat.icofidec.IconFileDecoratorPlugin;
import de.vermat.icofidec.decorators.IconFileImageDecorator;
import de.vermat.icofidec.decorators.IconFileInfoDecorator;
import de.vermat.icofidec.messages.Messages;
import de.vermat.icofidec.preferences.controls.LabelField;
import de.vermat.icofidec.preferences.controls.LinkField;
import de.vermat.icofidec.utils.NumberUtils;

public class IconFileDecoratorPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	public static final String HELP_CONTEXT_ID = IconFileDecoratorPlugin.PLUGIN_ID + ".IconFileDecoratorPreferencePage";
	
	private Text supportedFileExtensionsText;
	private Text includedImageFoldersText;
	private Text maxIconWidthText;
	private Text maxIconHeightEditorText;
	private Button scaleDownButton;
	private Button scaleUpButton;
	private Text preferredIconWidthText;
	private Text preferredIconHeightText;
	
	private boolean decoratorEnabled;
	
	private IWorkbench workbench;
	private IDecoratorManager decoratorManager;
	private ILabelProviderListener decoratorManagerListener;

	
	private static final int MAX_ICON_SIZE_PIXELS_RANGE_MIN = 16;
	private static final int MAX_ICON_SIZE_PIXELS_RANGE_MAX = 256;

	public IconFileDecoratorPreferencePage() {
		super(GRID);
	}
	
	@Override
	public void init(IWorkbench workbench) {
		this.workbench = workbench;
		decoratorManager = workbench.getDecoratorManager();		
		decoratorManagerListener = event -> updateControlEnablementFromDecorator();
		setPreferenceStore(IconFileDecoratorPlugin.getDefault().getPreferenceStore());
	}

	@Override
	public void createFieldEditors() {
		new LinkField(Messages.IconFileDecoratorPreferencePage_labelDecorationsLinkImageLabel, getFieldEditorParent())
			.addLinkSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					DecoratorsPreferencePageAccess pageAccess = DecoratorsPreferencePageAccess.showPreferencePage(getShell());
					if (pageAccess != null) {
						pageAccess.selectLabelDecoration(IconFileImageDecorator.DECORATOR_ID);
					}						
				}
			});
		new LinkField(Messages.IconFileDecoratorPreferencePage_labelDecorationsLinkInfoLabel, getFieldEditorParent())
		.addLinkSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DecoratorsPreferencePageAccess pageAccess = DecoratorsPreferencePageAccess.showPreferencePage(getShell());
				if (pageAccess != null) {
					pageAccess.selectLabelDecoration(IconFileInfoDecorator.DECORATOR_ID);
				}						
			}
		});
		
		LabelField.spacer(getFieldEditorParent());
		
		StringFieldEditor supportedFileExtensionsEditor = new StringFieldEditor(
				IconFileDecoratorPreferences.PREF_SUPPORTED_FILE_EXTENSIONS, 
				Messages.IconFileDecoratorPreferencePage_supportedFileExtensionsEditorLabel, 
				getFieldEditorParent());
		addField(supportedFileExtensionsEditor);
		supportedFileExtensionsText = supportedFileExtensionsEditor.getTextControl(getFieldEditorParent());
		
		StringFieldEditor includedImageFoldersEditor = new StringFieldEditor(
				IconFileDecoratorPreferences.PREF_INCLUDED_IMAGE_FOLDERS, 
				Messages.IconFileDecoratorPreferencePage_includedImageFoldersEditorLabel, 
				getFieldEditorParent());
		addField(includedImageFoldersEditor);
		includedImageFoldersText = includedImageFoldersEditor.getTextControl(getFieldEditorParent());
		
		IntegerFieldEditor maxIconWidthEditor = new IntegerFieldEditor(
				IconFileDecoratorPreferences.PREF_MAX_ICON_WIDTH_PIXELS,
				Messages.IconFileDecoratorPreferencePage_maxIconWidthEditorLabel,
				getFieldEditorParent(),
				NumberUtils.countDigits(MAX_ICON_SIZE_PIXELS_RANGE_MAX));
		maxIconWidthEditor.setValidRange(MAX_ICON_SIZE_PIXELS_RANGE_MIN, MAX_ICON_SIZE_PIXELS_RANGE_MAX);
		addField(maxIconWidthEditor);
		maxIconWidthText= maxIconWidthEditor.getTextControl(getFieldEditorParent());
		
		IntegerFieldEditor maxIconHeightEditor = new IntegerFieldEditor(
				IconFileDecoratorPreferences.PREF_MAX_ICON_HEIGHT_PIXELS,
				Messages.IconFileDecoratorPreferencePage_maxIconHeightEditorLabel,
				getFieldEditorParent(),
				NumberUtils.countDigits(MAX_ICON_SIZE_PIXELS_RANGE_MAX));
		maxIconHeightEditor.setValidRange(MAX_ICON_SIZE_PIXELS_RANGE_MIN, MAX_ICON_SIZE_PIXELS_RANGE_MAX);
		addField(maxIconHeightEditor);
		maxIconHeightEditorText = maxIconHeightEditor.getTextControl(getFieldEditorParent());
		
		BooleanFieldEditor scaleDownIconsEditor = new BooleanFieldEditor(
				IconFileDecoratorPreferences.PREF_SCALE_DOWN_ICONS,
				Messages.IconFileDecoratorPreferencePage_scaleDownIconsEditorLabel,
				getFieldEditorParent());
		addField(scaleDownIconsEditor);
		scaleDownButton = (Button) scaleDownIconsEditor.getDescriptionControl(getFieldEditorParent());
		
		BooleanFieldEditor scaleUpIconsEditor = new BooleanFieldEditor(
				IconFileDecoratorPreferences.PREF_SCALE_UP_ICONS,
				Messages.IconFileDecoratorPreferencePage_scaleUpIconsEditorLabel,
				getFieldEditorParent());
		addField(scaleUpIconsEditor);
		scaleUpButton = (Button) scaleUpIconsEditor.getDescriptionControl(getFieldEditorParent());
		
		IntegerFieldEditor preferredIconWidthEditor = new IntegerFieldEditor(
				IconFileDecoratorPreferences.PREF_PREFERRED_ICON_WIDTH_PIXELS,
				Messages.IconFileDecoratorPreferencePage_preferredIconWidthEditorLabel,
				getFieldEditorParent(),
				NumberUtils.countDigits(MAX_ICON_SIZE_PIXELS_RANGE_MAX));
		maxIconWidthEditor.setValidRange(MAX_ICON_SIZE_PIXELS_RANGE_MIN, MAX_ICON_SIZE_PIXELS_RANGE_MAX);
		addField(preferredIconWidthEditor);
		preferredIconWidthText = preferredIconWidthEditor.getTextControl(getFieldEditorParent());
		
		IntegerFieldEditor preferredIconHeightEditor = new IntegerFieldEditor(
				IconFileDecoratorPreferences.PREF_PREFERRED_ICON_HEIGHT_PIXELS,
				Messages.IconFileDecoratorPreferencePage_preferredIconHeightEditorLabel,
				getFieldEditorParent(),
				NumberUtils.countDigits(MAX_ICON_SIZE_PIXELS_RANGE_MAX));
		maxIconHeightEditor.setValidRange(MAX_ICON_SIZE_PIXELS_RANGE_MIN, MAX_ICON_SIZE_PIXELS_RANGE_MAX);
		addField(preferredIconHeightEditor);
		preferredIconHeightText = preferredIconHeightEditor.getTextControl(getFieldEditorParent());		
	}
	
	@Override
	protected Control createContents(Composite parent) {
		
		Control control = super.createContents(parent);
		
		workbench.getHelpSystem().setHelp(parent, HELP_CONTEXT_ID); //$NON-NLS-1$		

		decoratorEnabled = decoratorManager.getEnabled(IconFileImageDecorator.DECORATOR_ID);
		
		updateControlEnablement();
		
		decoratorManager.addListener(decoratorManagerListener);
		
		SelectionListener selectionListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateControlEnablement();
			}
		};
		scaleUpButton.addSelectionListener(selectionListener);		
		scaleDownButton.addSelectionListener(selectionListener);		
		
		return control;
	}

	private void updateControlEnablementFromDecorator() {
		if (getControl() == null || getControl().isDisposed()) {
			return;
		}		
		boolean newDecoratorEnabled = decoratorManager.getEnabled(IconFileImageDecorator.DECORATOR_ID);
		if (decoratorEnabled != newDecoratorEnabled) {
			decoratorEnabled = newDecoratorEnabled;
			updateControlEnablement();
		}
	}

	private void updateControlEnablement() {
		supportedFileExtensionsText.setEnabled(decoratorEnabled);
		includedImageFoldersText.setEnabled(decoratorEnabled);
		maxIconWidthText.setEnabled(decoratorEnabled);
		maxIconHeightEditorText.setEnabled(decoratorEnabled);
		scaleUpButton.setEnabled(decoratorEnabled);
		scaleDownButton.setEnabled(decoratorEnabled);
		preferredIconWidthText.setEnabled(decoratorEnabled && (scaleDownButton.getSelection() || scaleUpButton.getSelection()));
		preferredIconHeightText.setEnabled(decoratorEnabled && preferredIconWidthText.getEnabled());
	}

	@Override
	public boolean performOk() {
		refreshDecorators();
		boolean ok = super.performOk();
		updateControlEnablement();
		return ok;
	}

	private void refreshDecorators() {		
		if (decoratorManager.getEnabled(IconFileImageDecorator.DECORATOR_ID)) {
			decoratorManager.update(IconFileImageDecorator.DECORATOR_ID);
		}
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		updateControlEnablement();
	}

	@Override
	public void dispose() {
		decoratorManager.removeListener(decoratorManagerListener);
		super.dispose();
	}	
}