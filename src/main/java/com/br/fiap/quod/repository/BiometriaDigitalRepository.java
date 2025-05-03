package com.br.fiap.quod.repository;

import com.br.fiap.quod.model.biometriaDigital.BiometriaDigital;
import com.br.fiap.quod.model.biometriafacial.BiometriaFacial;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BiometriaDigitalRepository extends MongoRepository<BiometriaDigital, Long> {
}
