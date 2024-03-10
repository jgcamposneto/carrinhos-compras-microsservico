package br.com.fiap.postech.carrinhoscomprasmicrosservico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.postech.carrinhoscomprasmicrosservico.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
