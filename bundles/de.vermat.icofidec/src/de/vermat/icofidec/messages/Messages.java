package de.vermat.icofidec.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	
	private static final String BUNDLE_NAME = "de.vermat.icofidec.messages.messages"; //$NON-NLS-1$

	public static String IconFileDecoratorPreferencePage_labelDecorationsLinkImageLabel;
	public static String IconFileDecoratorPreferencePage_labelDecorationsLinkInfoLabel;
	public static String IconFileDecoratorPreferencePage_supportedFileExtensionsEditorLabel;
	public static String IconFileDecoratorPreferencePage_includedImageFoldersEditorLabel;	
	public static String IconFileDecoratorPreferencePage_maxIconWidthEditorLabel;
	public static String IconFileDecoratorPreferencePage_maxIconHeightEditorLabel;
	public static String IconFileDecoratorPreferencePage_scaleDownIconsEditorLabel;
	public static String IconFileDecoratorPreferencePage_scaleUpIconsEditorLabel;
	public static String IconFileDecoratorPreferencePage_preferredIconWidthEditorLabel;
	public static String IconFileDecoratorPreferencePage_preferredIconHeightEditorLabel;

	public static String DecoratorsPreferencePageAccess_selectPreferencePageFailedMessage;
	public static String DecoratorsPreferencePageAccess_pluginContributionNotFoundMessage;
	public static String DecoratorsPreferencePageAccess_selectPreferencePageLogMessage;
	public static String DecoratorsPreferencePageAccess_selectLabelDecorationLogMessage;

	public static String IconFileInfoDecorator_imageSizeSuffix;
	public static String IconFileInfoDecorator_noImageSuffix;
	public static String IconFileInfoDecorator_fileSizeSuffix;
	public static String IconFileInfoDecorator_unknownFileSizeSuffix;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}