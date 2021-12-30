package de.vermat.icofidec.decorators;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.swt.graphics.Point;

import de.vermat.icofidec.messages.Messages;
import de.vermat.icofidec.utils.ImageUtils;

public class IconFileInfoDecorator extends BasicDecorator implements ILightweightLabelDecorator {
	
	public static final String DECORATOR_ID = "de.vermat.icofidec.decorators.IconFileInfoDecorator"; //$NON-NLS-1$

	@Override
	public void decorate(Object element, IDecoration decoration) {
		IFile file = Adapters.adapt(element, IFile.class);
		if (file != null 
				&& getPreferences().hasSupportedFileExtension(file) 
				&& getPreferences().isContainedInIncludedImageFolders(file)) {
			Point point = ImageUtils.getImageDimension(file);
			decoration.addSuffix(" "); //$NON-NLS-1$
			if (point != null) {
				decoration.addSuffix(MessageFormat.format(Messages.IconFileInfoDecorator_imageSizeSuffix, point.y, point.y));
			} else {
				decoration.addSuffix(Messages.IconFileInfoDecorator_noImageSuffix);
			}
			try {
				decoration.addSuffix(", "); //$NON-NLS-1$
				decoration.addSuffix(MessageFormat.format(Messages.IconFileInfoDecorator_fileSizeSuffix, file.getLocation().toFile().length()));
			} catch (RuntimeException e) {
				decoration.addSuffix(Messages.IconFileInfoDecorator_unknownFileSizeSuffix);
			}
		}
	}
}