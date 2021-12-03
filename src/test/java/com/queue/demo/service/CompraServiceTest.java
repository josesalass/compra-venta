package com.queue.demo.service;

import com.queue.demo.model.*;
import com.queue.demo.repository.RepositorioCompra;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {
    @Mock
    private RepositorioCompra repositorioCompra;
    @Mock
    private ProductoServiceImpl productoService;
    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;
    @Mock
    private CriteriaQuery criteriaQuery=mock(CriteriaQuery.class);

    @Mock
    private Root root;

    @Mock
    private TypedQuery typedQuery = mock(TypedQuery.class);

    @InjectMocks
    private CompraServiceImpl compraService;

    //buscarTodasLasCompras caso exitoso
    @Test
    void siInvocoBuscarTodasLasComprasDebeRetornarCompraList(){
        List<Compra> resultado;
        List<Compra> compras = getCompras();
        when(repositorioCompra.findAll()).thenReturn(compras);

        resultado = compraService.buscarTodasLasCompras();

        assertNotNull(resultado);
        assertEquals(compras.size(),resultado.size());
        assertEquals(compras.get(0).getIdcompra(),resultado.get(0).getIdcompra());
        assertEquals(compras.get(1).getIdcompra(),resultado.get(1).getIdcompra());
    }

    //buscarTodasLasCompras caso no existen compras
    @Test
    void siInvocoBuscarTodasLasComprasYNoExistenComprasDebeRetornarCompraListVacia(){
        List<Compra> resultado;
        List<Compra> compras = new ArrayList<>();
        when(repositorioCompra.findAll()).thenReturn(compras);

        resultado = compraService.buscarTodasLasCompras();

        assertNotNull(resultado);
        assertEquals(0,resultado.size());
    }

    //buscarCompraPorId caso exitoso
    @Test
    void siInvocoBuscarCompraPorIdYExisteUnaCompraConEseIdDebeRetornarCompra(){
        Optional<Compra> resultado;
        Compra compra = getCompras().get(0);
        when(repositorioCompra.findById(1)).thenReturn(Optional.of(compra));

        resultado = compraService.buscarCompraPorId(1);

        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
        assertEquals(compra.getRutempresa(),resultado.get().getRutempresa());
    }

    //buscarCompraPorId caso no encuentra compra
    @Test
    void siInvocoBuscarCompraPorIdYNoExisteUnaCompraConEseIdDebeRetornarOptionalVacio(){
        Optional<Compra> resultado;
        when(repositorioCompra.findById(1)).thenReturn(Optional.empty());

        resultado = compraService.buscarCompraPorId(1);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    //borrarCompraPorId caso exitoso
    @Test
    void siInvocoBorrarCompraPorIdYExisteLaCompraDebeBorrarlaYRetornarTrue(){
        Compra compra = getCompras().get(0);
        boolean resultado;
        when(repositorioCompra.getById(1)).thenReturn(compra);

        resultado = compraService.borrarCompraPorId(1);

        assertTrue(resultado);
    }

    //borrarCompraPorId caso no encuentra la compra
    @Test
    void siInvocoBorrarCompraPorIdYNoExisteLaCompraDebeRetornarFalse(){
        boolean resultado;
        doThrow(EntityNotFoundException.class).when(repositorioCompra).getById(1);

        resultado = compraService.borrarCompraPorId(1);

        assertNotNull(resultado);
        assertFalse(resultado);
    }

    //guardarCompra caso exitoso
    @Test
    void siInvocoGuardarCompraDebeRetornarLaCompraGuardada() throws Exception {
        Compra compra = getCompras().get(0);
        Producto producto = getProducto();
        PerteneceACompra perteneceACompra = new PerteneceACompra();
        perteneceACompra.setCompra(compra);
        perteneceACompra.setProducto(producto);
        perteneceACompra.setCantidad(10);
        compra.getCompraproductos().add(perteneceACompra);
        producto.setStock(producto.getStock()+perteneceACompra.getCantidad());
        Compra resultado;

        when(productoService.buscarProductoPorId(1)).thenReturn(Optional.of(producto));
        when(repositorioCompra.save(any(Compra.class))).thenReturn(compra);

        resultado = compraService.guardarCompra(compra);

        assertNotNull(resultado);
        assertEquals(compra.getIdcompra(),resultado.getIdcompra());
        verify(repositorioCompra).save(any(Compra.class));
    }

    //actualizarCompra caso exitoso
    @Test
    void siInvocoActualizarCompraDebeRetornarLaCompraActualizada(){
        Compra compra = getCompras().get(0);
        given(repositorioCompra.save(compra)).willReturn(compra);

        Compra compraFinal = compraService.actualizarCompra(compra.getIdcompra(),compra);

        assertNotNull(compraFinal);
        verify(repositorioCompra).save(any(Compra.class));
    }

    //verRegistroCompraResumen caso exitoso
    @Test
    void siInvocoVerRegistroCompraResumenDebeRetornarViewRegistroComprasResumenList(){
        List<ViewRegistroComprasResumen> registro;
        List<ViewRegistroComprasResumen> mocklist = new ArrayList<>();
        mocklist.add(getViewRegistroComprasResumen());

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewRegistroComprasResumen.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);
        registro = compraService.verRegistroCompraResumen();

        assertNotNull(registro);
        assertEquals(mocklist.get(0),registro.get(0));
    }

    //verRegistroCompraDetalle caso exitoso
    @Test
    void siInvocoVerRegistroCompraDetalleDebeRetornarViewRegistroComprasDetalleList(){
        List<ViewRegistroComprasDetalle> registro;
        List<ViewRegistroComprasDetalle> mocklist = new ArrayList<>();
        mocklist.add(getViewRegistroComprasDetalle());

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewRegistroComprasDetalle.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);
        registro = compraService.verRegistroCompraDetalle();

        assertNotNull(registro);
        assertEquals(mocklist.get(0),registro.get(0));
    }

    private List<Compra> getCompras() {
        List<Compra> compras = new ArrayList<>();
        Compra compra = new Compra();
        compra.setIdcompra(1);
        compra.setRutusuario("12");
        compra.setRutempresa("21");
        compra.setFecha(Timestamp.valueOf("2020-11-23 00:00:00"));
        PerteneceACompra perteneceACompra = new PerteneceACompra();
        compras.add(compra);

        compra.setIdcompra(2);
        compra.setRutusuario("34");
        compra.setRutempresa("43");
        compras.add(compra);

        return compras;
    }
    private Producto getProducto(){
        Producto p = new Producto();
        p.setStock(2);
        p.setStockmin(1);
        p.setDetalleproducto("a");
        p.setIdproducto(1);
        p.setValorcompra(20);
        p.setValorventa(30);
        return p;
    }
    private ViewRegistroComprasResumen getViewRegistroComprasResumen(){
        ViewRegistroComprasResumen v = new ViewRegistroComprasResumen();
        v.setIdCompra(1);
        return v;
    }
    private ViewRegistroComprasDetalle getViewRegistroComprasDetalle(){
        ViewRegistroComprasDetalle v = new ViewRegistroComprasDetalle();
        v.setIdCompra(1);
        return v;
    }

}
