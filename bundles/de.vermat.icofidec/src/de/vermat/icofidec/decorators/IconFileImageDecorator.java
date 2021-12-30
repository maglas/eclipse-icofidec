package de.vermat.icofidec.decorators;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;

public class IconFileImageDecorator extends BasicDecorator implements ILabelDecorator {
	
	public static final String DECORATOR_ID = "de.vermat.icofidec.decorators.IconFileImageDecorator"; //$NON-NLS-1$
	
	@Override
	public Image decorateImage(Image image, Object element) {
		IFile file = Adapters.adapt(element, IFile.class);
		if (file != null 
				&& getPreferences().hasSupportedFileExtension(file) 
				&& getPreferences().isContainedInIncludedImageFolders(file)) {
			return getImage(file);
		}
		return image;
	}

	private Image getImage(IFile file) {
		Device device = PlatformUI.getWorkbench().getDisplay();
		try {
			Image image = new Image(device, file.getRawLocation().toOSString());
			ImageData idata = image.getImageData();
			if (idata.height <= getPreferences().getMaxIconHeightPixels() && idata.width <= getPreferences().getMaxIconWidthPixels()) {
				Point scaled = scaled(idata.width, idata.height);
				if (scaled.x != idata.width || scaled.y != idata.height) {
					image.dispose();
					image = new Image(device, idata.scaledTo(scaled.x, scaled.y));
				}
				return image;
			} else {
				return null;
			}
		} catch (SWTException | SWTError e) {
			// ignore any errors (especially unsupported or unrecognized format)
		}
		return null;
	}

	private Point scaled(int width, int height) {
		Point p = new Point(width, height);
		int scaledWidth = getPreferences().getPreferredIconWidthPixels();
		int scaledHeight = getPreferences().getPreferredIconHeightPixels();
		if (getPreferences().areIconsToBeScaledUp()) {
			p.x = Math.max(p.x, scaledWidth);
			p.y = Math.max(p.y, scaledHeight);
		}
		if (getPreferences().areIconsToBeScaledDown()) {
			p.x = Math.min(p.x, scaledWidth);
			p.y = Math.min(p.y, scaledHeight);
		}
		return p;
	}
	
	@Override
	public String decorateText(String text, Object element) {
		return null;
	}
}