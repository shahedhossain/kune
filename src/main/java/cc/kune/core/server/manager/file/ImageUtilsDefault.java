/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 \*/
package cc.kune.core.server.manager.file;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.services.ImageSize;

public class ImageUtilsDefault {

  public static final Log LOG = LogFactory.getLog(ImageUtilsDefault.class);

  static int calculateCenteredCoordinate(final int size, final int crop) {
    final int i = (size - crop) / 2;
    return i < 0 ? 0 : i;
  }

  static Dimension calculatePropDim(final int origWidth, final int origHeight, final int maxSize) {
    return calculatePropDim(origWidth, origHeight, maxSize, true);
  }

  static Dimension calculatePropDim(final int origWidth, final int origHeight, final int maxSize,
      final boolean toShortest) {
    final boolean higher = origHeight > origWidth;
    final int propHeight = origHeight * maxSize / origWidth;
    final int propWidth = origWidth * maxSize / origHeight;
    final double height = toShortest ? (higher ? propHeight : maxSize) : (higher ? maxSize : propHeight);
    final double width = toShortest ? (!higher ? propWidth : maxSize) : (!higher ? maxSize : propWidth);
    if ((higher && origHeight <= maxSize) || (!higher && origWidth <= maxSize)) {
      return new Dimension(origWidth, origHeight);
    }
    return new Dimension((int) width, (int) height);
  }

  private static void checkExist(final String fileOrig) throws FileNotFoundException {
    final File file = new File(fileOrig);
    if (!file.exists()) {
      throw new FileNotFoundException();
    }
  }

  private static ImageInfo createEmptyImageInfo() throws MagickException {
    final ImageInfo imageInfo = new ImageInfo();
    return imageInfo;
  }

  private static ImageInfo createEmptyImageInfoWithNoPage() throws MagickException {
    final ImageInfo imageInfo = createEmptyImageInfo();
    imageInfo.setPage("0x0+0+0");
    return imageInfo;
  }

  /**
   * http://en.wikipedia.org/wiki/Thumbnail
   * 
   * @throws FileNotFoundException
   * 
   */
  public static void createThumb(final String fileOrig, final String fileDest, final int cropDimension)
      throws MagickException, FileNotFoundException {
    createThumb(fileOrig, fileDest, cropDimension, cropDimension);
  }

  public static void createThumb(final String fileOrig, final String fileDest, final int thumbDimension,
      final int cropDimension) throws MagickException, FileNotFoundException {
    checkExist(fileOrig);
    if (thumbDimension < cropDimension) {
      throw new IndexOutOfBoundsException("Thumb dimension must be bigger than crop dimension");
    }
    final MagickImage imageOrig = readImage(fileOrig);
    final Dimension origDimension = imageOrig.getDimension();
    final int origHeight = origDimension.height;
    final int origWidth = origDimension.width;
    final Dimension proportionalDim = calculatePropDim(origWidth, origHeight, thumbDimension, true);
    final MagickImage scaled = scaleImage(imageOrig, proportionalDim.width, proportionalDim.height);
    final int x = calculateCenteredCoordinate(proportionalDim.width, cropDimension);
    final int y = calculateCenteredCoordinate(proportionalDim.height, cropDimension);
    cropImage(scaled, fileDest, x, y, cropDimension, cropDimension);
  }

  /**
   * convert -density 300 -quality 100 -resize 720x file.pdf result.png
   * 
   * @param pdfFile
   * @param newPngFile
   * @return
   * @throws MagickException
   */
  static public boolean createThumbFromPdf(final String pdfFile, final String newPngFile)
      throws MagickException {
    final MagickImage pdf = readImage(pdfFile);
    final MagickImage pdf1 = pdf.breakFrames()[0];
    return writeImage(pdf1, newPngFile);
  }

  private static boolean cropImage(final MagickImage fileOrig, final String fileDest, final int x,
      final int y, final int width, final int height) throws MagickException {
    final Rectangle rectangle = new Rectangle(x, y, width, height);
    return cropImage(fileOrig, fileDest, rectangle);
  }

  private static boolean cropImage(final MagickImage fileOrig, final String fileDest,
      final Rectangle rectangle) throws MagickException {
    final MagickImage cropped = fileOrig.cropImage(rectangle);
    cropped.setFileName(fileDest);
    final ImageInfo imageInfo = createEmptyImageInfoWithNoPage();
    return cropped.writeImage(imageInfo);
  }

  public static boolean cropImage(final String fileOrig, final String fileDest, final int x,
      final int y, final int width, final int height) throws MagickException, FileNotFoundException {
    final Rectangle rectangle = new Rectangle(x, y, width, height);
    return cropImage(fileOrig, fileDest, rectangle);
  }

  public static boolean cropImage(final String fileOrig, final String fileDest, final Rectangle rectangle)
      throws MagickException, FileNotFoundException {
    checkExist(fileOrig);
    return cropImage(readImage(fileOrig), fileDest, rectangle);
  }

  public static void generateThumbs(final String absDir, final String filename, final String extension,
      final boolean isPdf, final int resizeWidth, final int thumbSize, final int cropSize,
      final int iconSize) {
    try {
      final String fileOrig = absDir + filename;
      final String withoutExtension = FileUtils.getFileNameWithoutExtension(filename, extension);

      final String resizeName = absDir + withoutExtension + "." + ImageSize.sized + "." + extension;
      final String thumbName = absDir + withoutExtension + "." + ImageSize.thumb + "." + extension;
      final String iconName = absDir + withoutExtension + "." + ImageSize.ico + "." + extension;
      final String previewName = absDir + withoutExtension + "." + extension;

      scaleImageToMax(fileOrig, resizeName, resizeWidth);
      createThumb(fileOrig, thumbName, thumbSize, cropSize);
      createThumb(fileOrig, iconName, iconSize);
      if (isPdf) {
        createThumbFromPdf(fileOrig, previewName);
      }
    } catch (final NumberFormatException e) {
      LOG.error("Image sizes in kune.properties are not integers", e);
    } catch (final MagickException e) {
      LOG.info("Problem generating image thumb for " + filename, e);
    } catch (final FileNotFoundException e) {
      LOG.info("Original image not found generating image thumb for " + filename, e);
    }
  }

  public static Dimension getDimension(final String file) throws MagickException {
    final MagickImage imageOrig = readImage(file);
    return imageOrig.getDimension();
  }

  /**
   * FIXME: Not working, returns null always (bug)
   * 
   */
  public static String getPage(final String file) throws MagickException {
    final ImageInfo imageInfo = new ImageInfo(file);
    new MagickImage(imageInfo);
    return imageInfo.getPage();
  }

  private static MagickImage readImage(final String file) throws MagickException {
    final ImageInfo imageInfo = new ImageInfo(file);
    return new MagickImage(imageInfo);
  }

  private static MagickImage scaleImage(final MagickImage imageOrig, final int width, final int height)
      throws MagickException {
    return imageOrig.scaleImage(width, height);
  }

  private static boolean scaleImage(final MagickImage imageOrig, final String fileDest, final int width,
      final int height) throws MagickException {
    final MagickImage imageDest = scaleImage(imageOrig, width, height);
    return writeImage(imageDest, fileDest);
  }

  public static boolean scaleImage(final String fileOrig, final String fileDest,
      final Dimension dimension) throws MagickException, FileNotFoundException {
    checkExist(fileOrig);
    final MagickImage imageOrig = readImage(fileOrig);
    return scaleImage(imageOrig, fileDest, (int) dimension.getWidth(), (int) dimension.getHeight());
  }

  public static boolean scaleImage(final String fileOrig, final String fileDest, final int width,
      final int height) throws MagickException, FileNotFoundException {
    checkExist(fileOrig);
    final MagickImage imageOrig = readImage(fileOrig);
    return scaleImage(imageOrig, fileDest, width, height);
  }

  public static boolean scaleImageToMax(final String fileOrig, final String fileDest, final int maxSize)
      throws MagickException, FileNotFoundException {
    checkExist(fileOrig);
    final MagickImage imageOrig = readImage(fileOrig);
    final Dimension origDimension = imageOrig.getDimension();
    final int origHeight = origDimension.height;
    final int origWidth = origDimension.width;
    final Dimension proportionalDim = calculatePropDim(origWidth, origHeight, maxSize, false);
    final MagickImage scaled = scaleImage(imageOrig, proportionalDim.width, proportionalDim.height);

    return writeImage(scaled, fileDest);
  }

  private static boolean writeImage(final MagickImage imageDest, final String fileDest)
      throws MagickException {
    imageDest.setFileName(fileDest);
    return imageDest.writeImage(createEmptyImageInfo());
  }
}
