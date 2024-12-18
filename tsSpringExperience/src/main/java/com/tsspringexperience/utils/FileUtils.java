package com.tsspringexperience.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public final class FileUtils {

	
    private FileUtils() {
    }

    /**
     * 
     * @param directory the directory to search in
     * @param fileFilter filter to apply when finding files.
     * @return an collection of java.io.File with the matching files
     */
    public static File[] listAllFiles(final File directory, final IOFileFilter fileFilter) {
        final IOFileFilter dirFilter = TrueFileFilter.INSTANCE;
        final Collection<File> listFiles = org.apache.commons.io.FileUtils.listFiles(directory, fileFilter, dirFilter);
        final File[] array = org.apache.commons.io.FileUtils.convertFileCollectionToFileArray(listFiles);
        return array;
    }

    public static String removeExtension(final String filename) {
        return FilenameUtils.removeExtension(filename);
    }

    public static String fileName(final File file) {
        return FileUtils.fileName(file, false);
    }

    public static String fileName(final File file, final boolean removeExtension) {
        final String name = file.getName();
        if (removeExtension) {
            return FileUtils.removeExtension(name);
        } else {
            return name;
        }
    }

    public static String getExtension(final File file) {
        return FileUtils.getExtension(file.getName());
    }

    public static String getExtension(final String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public static File[] listFilesForFolder(final File folder, final String extension) throws IOException {
        final List<File> lstFiles = new ArrayList<File>();
        return listFilesForFolder(folder, extension, false, lstFiles);
    }

    public static File[] listFilesForFolder(final File folder, final String extension, final boolean subDir, final List<File> lstFiles) throws IOException {
        final FileFilter ff = new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                    return false;
                final String name = f.getName().toLowerCase();
                if (name.endsWith(extension.toLowerCase()))
                    return true;
                return false;
            }
        };
        FileUtils.listFilesForFolder(folder, ff, subDir, lstFiles);
        return (File[]) lstFiles.toArray(new File[lstFiles.size()]);
    }

    public static File[] listFilesForFolder(final File folder, final FileFilter fileFilter) throws IOException {
        final List<File> lstFiles = new ArrayList<File>();
        listFilesForFolder(folder, fileFilter, false, lstFiles);
        return (File[]) lstFiles.toArray(new File[lstFiles.size()]);
    }

    public static void listFilesForFolder(final File folder, final FileFilter fileFilter, final boolean subDir, final List<File> files) throws IOException {
        if (folder == null) {
            throw new IllegalArgumentException("'folder' have not to be null!");
        }
        if (!folder.exists()) {
            throw new IOException("'folder' refers to unexisting directory! " + folder.getPath());
        }
        if (!folder.isDirectory()) {
            throw new IOException("'folder' is not a directory! " + folder.getPath());
        }
        final File[] listFiles = folder.listFiles();
        if (listFiles != null) {
            for (final File fileEntry : listFiles) {
                if (fileEntry.isDirectory()) {
                    if (subDir) {
                        listFilesForFolder(fileEntry, fileFilter, subDir, files);
                    }
                } else {
                    if (fileFilter.accept(fileEntry)) {
                        files.add(fileEntry);
                    }
                }
            }
        }
    }
    
    public static void writeIntoFile(String filepath, String fileContent){
    	File file = new File(filepath);
		try (FileOutputStream fop=new FileOutputStream(file,true);) {

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			fop.write(System.lineSeparator().getBytes());
			fop.write(fileContent.getBytes());
			fop.flush();
			fop.close();
		} catch (IOException e) {
		}
    }
    
}