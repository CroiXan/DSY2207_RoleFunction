package com.grupo8.role.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearchUtil {
    public static List<String> buscarArchivosEnDirectorio(String directorio, String patron) {
        List<String> archivosEncontrados = new ArrayList<>();
        File folder = new File(directorio);
        if (folder.exists() && folder.isDirectory()) {
            File[] archivos = folder.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isFile() && archivo.getName().contains(patron)) {
                        archivosEncontrados.add(archivo.getAbsolutePath());
                    }
                    if (archivo.isDirectory()) {
                        archivosEncontrados.addAll(buscarArchivosEnDirectorio(archivo.getAbsolutePath(), patron));
                    }
                }
            }
        }
        return archivosEncontrados;
    }
}
