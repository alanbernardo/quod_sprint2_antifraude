package com.br.fiap.quod.repository;

import com.br.fiap.quod.model.documentos.Documento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends MongoRepository<Documento, String> {
    List<Documento> findByValidacaoTrue();
}
