package com.mitocode.Service;

import com.mitocode.model.Archivo;

public interface IArchivoService {

	int guardar(Archivo archivo);

	byte[] leerArchivo(Integer idArchivo);
}
