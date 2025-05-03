package com.br.fiap.quod.repository;

import com.br.fiap.quod.model.notificacao.NotificacaoFraude;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoFraudeRepository extends MongoRepository<NotificacaoFraude, String> {
}
