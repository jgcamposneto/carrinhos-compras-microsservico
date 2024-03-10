package br.com.fiap.postech.carrinhoscomprasmicrosservico.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.postech.carrinhoscomprasmicrosservico.entity.CarrinhoDeCompras;
import br.com.fiap.postech.carrinhoscomprasmicrosservico.service.CarrinhoDeComprasService;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoDeComprasController {

	@Autowired
	private CarrinhoDeComprasService carrinhoDeComprasService;
	
	public ResponseEntity<?> criarCarrinho(@RequestBody CarrinhoDeCompras carrinho) {
		try {
			CarrinhoDeCompras novoCarrinho = carrinhoDeComprasService.criarCarrinho(carrinho);
			return new ResponseEntity<>(novoCarrinho, HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>("Um ou mais produtos não estão disponíveis.", HttpStatus.BAD_REQUEST);
		}
	}
	
}
