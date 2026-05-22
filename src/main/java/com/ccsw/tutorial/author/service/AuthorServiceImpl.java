package com.ccsw.tutorial.author.service;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.author.model.AuthorSearchDto;
import com.ccsw.tutorial.author.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


/**
 * @author Max Escrivá
 *
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Author> findPage(AuthorSearchDto dto) {

        return this.authorRepository.findAll(dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, AuthorDto data) {

        Author author;

        if (id == null) {
            author = new Author();
        } else {
            author = this.authorRepository.findById(id).orElse(null);
        }

        assert author != null;
        /* La siguiente utilidad nos permite copiar más de un dato de una clase a otra, siempre que las propiedades se llamen igual.
        * En este caso, de AuthorDto (data) a Author.
        * Aquí ignoramos el id para que no nos copie un null a la clase destino. */
        BeanUtils.copyProperties(data, author, "id");

        this.authorRepository.save(author);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if(this.authorRepository.findById(id).orElse(null) == null){
            throw new Exception("Not exists");
        }

        this.authorRepository.deleteById(id);
    }

}