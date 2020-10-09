package br.com.zup.treinocasadocodigo.controllers;

import br.com.zup.treinocasadocodigo.entities.compra.Compra;
import br.com.zup.treinocasadocodigo.entities.compra.CompraRequest;
import br.com.zup.treinocasadocodigo.entities.compra.CompraRetorno;
import br.com.zup.treinocasadocodigo.entities.cupom.Cupom;
import br.com.zup.treinocasadocodigo.repository.CupomRepository;
import br.com.zup.treinocasadocodigo.validators.validarcompras.CompraValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Contagem de carga intrínseca da classe: 8
 */

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    //1
    private CompraValidador compraValidador;

    @Autowired
    //1
    private CupomRepository cupomRepository;

    @PersistenceContext
    private EntityManager manager;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(compraValidador);
    }

    @PostMapping()
    @Transactional
    //1
    public ResponseEntity<Object> cadastroCompra(@RequestBody @Valid CompraRequest novaCompra) {

        //1
        Compra compra = novaCompra.toModelSemCupom(manager);

        //1
        List<Cupom> listaCupom = cupomRepository.findByCodigo(novaCompra.getPedido().getCodigoCupom());
        //1
        if (!listaCupom.isEmpty()){
            compra.setCupom(listaCupom.get(0));
        }

        manager.persist(compra);

        URI location = URI.create(String.format("/compras/%d", compra.getId()));
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    //1
    public ResponseEntity<CompraRetorno> detalhesCompra(@PathVariable("id") Long id) {

        Compra compra = manager.find(Compra.class, id);

        //1
        if (compra == null) {
            return ResponseEntity.notFound().build();
        }
        //O find do manager não está retornando a lista de itens,
        // então chamamos a função que adiciona a lista sozinho
        compra.setListaItens();
        return ResponseEntity.ok(new CompraRetorno(compra));
    }
}
