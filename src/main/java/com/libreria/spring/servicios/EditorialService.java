package com.libreria.spring.servicios;

import com.libreria.spring.entidades.Editorial;
import com.libreria.spring.excepciones.MyException;
import com.libreria.spring.repositorios.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorialService {
    private static final String EDITORIAL_PATTERN = "^[A-Za-z0-9&.'\\s-]+$";
    @Autowired
    private EditorialRepository er;
    public void crearEditorial(String nombre, String paisOrigen) throws MyException {
        validar(nombre,paisOrigen);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setPaisOrigen(paisOrigen);
        editorial.setAlta(true);
        er.save(editorial);
    }
    public void validar(String nombre, String paisOrigen) throws MyException{
        if(!nombre.matches(EDITORIAL_PATTERN)){
            throw new MyException("Nombre de editorial inválido. ");
        }
        if(!paisOrigen.matches("^[a-zA-ZÀ-ÿ\\s']*$")){
            throw new MyException("País de origen inválido.");
        }
        boolean nombreRepetido = false;
        List<Editorial> editoriales = er.findAll();
        for(Editorial aux : editoriales){
            if(aux.getNombre().equalsIgnoreCase(nombre)){
                nombreRepetido = true;
                throw new MyException("La editorial ya está registrada.");
            }
        }
    }

    public void eliminarEditorial(String id){
        er.deleteById(id);
    }
    public List<Editorial> listarEditoriales(){
        return er.findAll();
    }
}
