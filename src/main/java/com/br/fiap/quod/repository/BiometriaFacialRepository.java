package com.br.fiap.quod.repository;

import com.br.fiap.quod.model.biometriafacial.BiometriaFacial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BiometriaFacialRepository extends MongoRepository<BiometriaFacial, Long> {
}
