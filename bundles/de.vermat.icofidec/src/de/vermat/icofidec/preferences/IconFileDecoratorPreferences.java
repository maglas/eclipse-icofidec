package de.vermat.icofidec.preferences;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;

public class IconFileDecoratorPreferences {

	public static final String PREF_SUPPORTED_FILE_EXTENSIONS = "prefSupportedFileExtensions"; //$NON-NLS-1$
	public static final String PREF_INCLUDED_IMAGE_FOLDERS = "prefIncludedImageFolders"; //$NON-NLS-1$
	public static final String PREF_MAX_ICON_WIDTH_PIXELS = "prefMaxIconWidthPixels"; //$NON-NLS-1$
	public static final String PREF_MAX_ICON_HEIGHT_PIXELS = "prefMaxIconHeightPixels"; //$NON-NLS-1$
	public static final String PREF_SCALE_DOWN_ICONS = "prefScaleDownIcons"; //$NON-NLS-1$
	public static final String PREF_SCALE_UP_ICONS = "prefScaleUpIcons"; //$NON-NLS-1$
	public static final String PREF_PREFERRED_ICON_HEIGHT_PIXELS = "prefScaledIconHeightPixels"; //$NON-NLS-1$
	public static final String PREF_PREFERRED_ICON_WIDTH_PIXELS = "prefScaledIconWidthPixels"; //$NON-NLS-1$

	private final IPreferenceStore preferenceStore;
	
	public IconFileDecoratorPreferences(IPreferenceStore preferenceStore) {
		Assert.isNotNull(preferenceStore);
		this.preferenceStore = preferenceStore;
	}

	public boolean hasSupportedFileExtension(IFile file) {
		String supportedFileExtensions = getSupportedFileExtensions();
		if (supportedFileExtensions.isEmpty()) {
			return true;
		}
		String fileExtension = file.getFileExtension();
		if (fileExtension == null || fileExtension.trim().isEmpty()) {
			return false;
		}
		if (supportedFileExtensions.contains(fileExtension.toLowerCase())) {
			return true;
		}
		return false;
	}

	public String getSupportedFileExtensions() {
		return preferenceStore.getString(IconFileDecoratorPreferences.PREF_SUPPORTED_FILE_EXTENSIONS).toLowerCase().trim();
	}

	public boolean isContainedInIncludedImageFolders(IFile file) {
		Set<String> includedFolderNames = getIncludedImageFolderNames();
		if (includedFolderNames.isEmpty()) {
			return true;
		}
		String parentFolderName = file.getParent().getLocation().addTrailingSeparator().toString().toLowerCase();
		for (String folderName : includedFolderNames) {
			if (parentFolderName.contains(folderName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	private Set<String> getIncludedImageFolderNames() {
		String s = preferenceStore
				.getString(IconFileDecoratorPreferences.PREF_INCLUDED_IMAGE_FOLDERS)
				.toLowerCase().trim();
		return Pattern.compile(",") //$NON-NLS-1$
				.splitAsStream(s)
				.map(f -> new Path("/").append(new Path(f.trim()).addTrailingSeparator()).toString()) //$NON-NLS-1$
				.collect(Collectors.toSet());
	}

	public int getMaxIconWidthPixels() {
		return preferenceStore.getInt(IconFileDecoratorPreferences.PREF_MAX_ICON_WIDTH_PIXELS);
	}

	public int getMaxIconHeightPixels() {
		return preferenceStore.getInt(IconFileDecoratorPreferences.PREF_MAX_ICON_HEIGHT_PIXELS);
	}

	public boolean areIconsToBeScaledDown() {
		return preferenceStore.getBoolean(IconFileDecoratorPreferences.PREF_SCALE_DOWN_ICONS);
	}

	public boolean areIconsToBeScaledUp() {
		return preferenceStore.getBoolean(IconFileDecoratorPreferences.PREF_SCALE_UP_ICONS);
	}

	public int getPreferredIconWidthPixels() {
		return preferenceStore.getInt(IconFileDecoratorPreferences.PREF_PREFERRED_ICON_WIDTH_PIXELS);
	}

	public int getPreferredIconHeightPixels() {
		return preferenceStore.getInt(IconFileDecoratorPreferences.PREF_PREFERRED_ICON_HEIGHT_PIXELS);
	}
}