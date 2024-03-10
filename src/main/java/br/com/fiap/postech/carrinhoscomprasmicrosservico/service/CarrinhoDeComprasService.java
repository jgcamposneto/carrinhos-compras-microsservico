package br.com.fiap.postech.carrinhoscomprasmicrosservico.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.postech.carrinhoscomprasmicrosservico.entity.CarrinhoDeCompras;
import br.com.fiap.postech.carrinhoscomprasmicrosservico.repository.CarrinhosDeComprasRepository;

@Service
public class CarrinhoDeComprasService {
	
	@Autowired
	private CarrinhosDeComprasRepository carrinhosDeComprasRepository;
	
	public CarrinhoDeCompras criarCarrinho(CarrinhoDeCompras carrinho) {
		return carrinhosDeComprasRepository.save(carrinho);
	}

}
