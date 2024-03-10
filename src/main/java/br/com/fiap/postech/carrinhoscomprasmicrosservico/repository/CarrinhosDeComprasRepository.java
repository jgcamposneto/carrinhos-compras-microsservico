package br.com.fiap.postech.carrinhoscomprasmicrosservico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.postech.carrinhoscomprasmicrosservico.entity.CarrinhoDeCompras;

public interface CarrinhosDeComprasRepository extends JpaRepository<CarrinhoDeCompras, Long> {

}
