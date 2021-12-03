package com.queue.demo.service;

import com.queue.demo.model.Asociada_Venta;
import com.queue.demo.model.Cliente;
import com.queue.demo.model.Producto;
import com.queue.demo.model.Venta;
import com.queue.demo.repository.RepositorioVenta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {
    @Mock
    private RepositorioVenta repositorioVenta;
    @Mock
    private ProductoServiceImpl productoService;
    @Mock
    private EntityManager entityManager;


    @Mock
    private CriteriaBuilder criteriaBuilder;
    @Mock
    private CriteriaQuery criteriaQuery=mock(CriteriaQuery.class);
    @InjectMocks
    private VentaServiceImpl ventaService;

    @Test
    void buscarTodasLasVentasYFunca(){
        List<Venta> resultado;
        List<Venta> ventas=getListaVentas();
        when(repositorioVenta.findAll()).thenReturn(ventas);

        resultado=ventaService.buscarTodasLasVentas();

        assertNotNull(resultado);
        assertEquals(ventas.size(),resultado.size());
    }
    @Test
    void buscarTodasLasVentasYNoFunca(){
        List<Venta> resultado;
        List<Venta> ventas=new ArrayList<>();
        when(repositorioVenta.findAll()).thenReturn(ventas);

        resultado=ventaService.buscarTodasLasVentas();

        assertNotNull(resultado);
        assertEquals(0,resultado.size());
    }
    //*
    @Test
    void BuscarVentaPorIdYFunca(){
        Optional<Venta> resultado;
        Venta venta=getListaVentas().get(0);
        when(repositorioVenta.findById(1)).thenReturn(Optional.of(venta));

        resultado = ventaService.buscarVentaPorId(1);

        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
    }
    @Test
    void BuscarVentaPorIdYNoFunca(){
        Optional<Venta> resultado;
        when(repositorioVenta.findById(1)).thenReturn(Optional.empty());

        resultado = ventaService.buscarVentaPorId(1);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
    @Test
    void BorrarVentaPorIdYFunca(){
        Venta venta=getListaVentas().get(0);
        boolean resultado;
        when(repositorioVenta.getById(1)).thenReturn(venta);

        resultado=ventaService.borrarVentaPorId(1);

        assertTrue(resultado);
    }
    @Test
    void BorrarVentaPorIdYNoFunca(){
        boolean resultado;
        doThrow(EntityNotFoundException.class).when(repositorioVenta).getById(1);

        resultado=ventaService.borrarVentaPorId(1);

        assertNotNull(resultado);
        assertFalse(resultado);
    }/*
    @Test
    void GuardarVentaYFunca() throws Exception {
        /*Venta venta=getListaVentas();
        when(repositorioVenta.findById(1)).thenReturn(Optional.empty());* /
        Venta venta= getListaVentas().get(0);
        Producto producto= getProducto();
        Asociada_Venta asociadaVenta=new Asociada_Venta();
        asociadaVenta.setProducto(producto);
        asociadaVenta.setVenta(venta);
        asociadaVenta.setCantidad(10);
        venta.getVentaproductos().add(asociadaVenta);
        producto.setStock(producto.getStock()-asociadaVenta.getCantidad());
        Venta resultado;


        when(productoService.buscarProductoPorId(1)).thenReturn(Optional.of(producto));
        when(repositorioVenta.save(any(Venta.class))).thenReturn(venta);

        resultado = ventaService.guardarVenta(venta);

        assertNotNull(resultado);
        assertEquals(venta.getIdventa(),resultado.getIdventa());
        verify(repositorioVenta).save(any(Venta.class));

    }*/
    @Test
    void GuardarVentaYNoFunca(){

    }
    @Test
    void ActualizarVentaYFunca(){
        Venta venta = getListaVentas().get(0);
        given(repositorioVenta.save(venta)).willReturn(venta);

        Venta ventaFinal = ventaService.actualizarVenta(venta.getIdventa(),venta);

        assertNotNull(ventaFinal);
        verify(repositorioVenta).save(any(Venta.class));
    }
    @Test
    void ActualizarVentaYNoFunca(){

    }
    private List<Venta>getListaVentas(){
        List<Venta> ventas=new ArrayList<>();
        Venta venta=new Venta();
        venta.setIdventa(1);
        //venta.setFecha();
        venta.setMetodopago("efectivo");
        venta.setRutusuario("12345678-9");
        venta.setRutcliente("12345678-9");
        venta.setTipoventa("factura");


        //List<Cliente> clientes=new ArrayList<>();
        Cliente cliente=new Cliente();
        cliente.setRutcliente("12345678-9");
        cliente.setNombre("rrgthcfgj");
        cliente.setApellido1("agjhk");
        cliente.setApellido2("jaytsh");
        cliente.setTelefono(987654321);
        cliente.setComuna("gtahjgvd");
        cliente.setCalle("ashfdv");
        cliente.setNumerocalle(321);
        //clientes.add(cliente);

        venta.setCliente(cliente);

        ventas.add(venta);

        return ventas;
    }
    private Producto getProducto() {
        Producto p = new Producto();
        p.setStock(10);
        p.setStockmin(2);
        p.setDetalleproducto("ajashkdb");
        p.setIdproducto(1);
        p.setValorcompra(10);
        p.setValorventa(20);
        return p;
    }
}
