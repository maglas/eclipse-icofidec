package de.vermat.icofidec.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.graphics.Point;

public class ImageUtils {
	
	public static Point getImageDimension(IFile file) {
		Point result = null;
		Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(file.getFileExtension());
		if (iter.hasNext()) {
			ImageReader reader = iter.next();
			try {
				URI uri = file.isLinked() ? file.getRawLocationURI() : file.getLocationURI();
				File javaFile = EFS.getStore(uri).toLocalFile(EFS.CACHE, new NullProgressMonitor());
				try (FileImageInputStream inputStream = new FileImageInputStream(javaFile)) {
					reader.setInput(inputStream);
					int width = reader.getWidth(reader.getMinIndex());
					int height = reader.getHeight(reader.getMinIndex());
					result = new Point(width, height);
				}
			} catch (IOException | CoreException e) {
				// ignore any errors (especially unsupported or unrecognized format)
			} finally {
				reader.dispose();
			}
		}
		return result;
	}
}