package com.queue.demo.service;

import com.queue.demo.model.*;
import com.queue.demo.repository.RepositorioVenta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
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
    @Mock
    private Root root;

    @Mock
    private TypedQuery typedQuery = mock(TypedQuery.class);

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
    void ActualizarVentaYFunca(){
        Venta venta = getListaVentas().get(0);
        given(repositorioVenta.save(venta)).willReturn(venta);

        Venta ventaFinal = ventaService.actualizarVenta(venta.getIdventa(),venta);

        assertNotNull(ventaFinal);
        verify(repositorioVenta).save(any(Venta.class));
    }

    @Test
    void siInvocoVerRegistroVentaResumenYFunca(){
        List<ViewRegistroVentasResumen> registro;
        List<ViewRegistroVentasResumen> mocklist = getViewRegistroVentasResumen();
        //mocklist.add(getViewRegistroVentasResumen());

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewRegistroVentasResumen.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);
        registro = ventaService.verRegistroVentaResumen();

        assertNotNull(registro);
        assertEquals(mocklist.get(0),registro.get(0));
    }
    //aaa
    @Test
    void siInvocoVerRegistroVentaResumenConTipoYFunca(){
        List<ViewRegistroVentasResumen> registro;
        List<ViewRegistroVentasResumen> mocklist = getViewRegistroVentasResumen();


        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewRegistroVentasResumen.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        //when(criteriaQuery.where(criteriaBuilder.equal(root.get("tipoventa"), getListaVentas().get(0).getTipoventa()))).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);
        registro = ventaService.verRegistroVentaResumen();

        assertNotNull(registro);
        assertEquals(mocklist.get(0).getTipoventa(),registro.get(0).getTipoventa());
    }
    @Test
    void siInvocoVerRegistroVentaDetalleYFunca(){
        List<ViewRegistroVentasDetalle> registro;
        List<ViewRegistroVentasDetalle> mocklist = getViewRegistroVentasDetalle();


        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewRegistroVentasDetalle.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);
        registro = ventaService.verRegistroVentaDetalle();

        assertNotNull(registro);
        assertEquals(mocklist.get(0),registro.get(0));
    }
    //aaa
    @Test
    void siInvocoVerRegistroVentaDetalleConTipoYFunca(){
        List<ViewRegistroVentasDetalle> registro;
        List<ViewRegistroVentasDetalle> mocklist = getViewRegistroVentasDetalle();


        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewRegistroVentasDetalle.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("tipoventa"), getListaVentas().get(0).getTipoventa()))).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);
        registro = ventaService.verRegistroVentaDetalle(getListaVentas().get(0).getTipoventa());

        assertNotNull(registro);
        assertEquals(mocklist.get(0),registro.get(0));
    }
    //aaa
    @Test
    void siInvocoVerRegistroVentaResumenDiaYFunca(){
        List<ViewRegistroVentasResumen> registro;
        List<ViewRegistroVentasResumen> mocklist = getViewRegistroVentasResumen();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewRegistroVentasResumen.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("fecha"), getListaVentas().get(0).getFecha()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);

        registro = ventaService.verRegistroVentaResumenDia(getListaVentas().get(0).getFecha());

        assertNotNull(registro);
        assertEquals(mocklist.get(0),registro.get(0));
    }
    //aaa
    @Test
    void siInvocoVerRegistroVentaResumenEntreDiasYFunca(){
        List<ViewRegistroVentasResumen> registro;
        List<ViewRegistroVentasResumen> mocklist = getViewRegistroVentasResumen();


        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewRegistroVentasResumen.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);

        when(criteriaQuery.where(criteriaBuilder.between(root.get("fecha"), getListaVentas().get(0).getFecha(),getListaVentas().get(0).getFecha()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);
        registro = ventaService.verRegistroVentaResumenEntreDias(getListaVentas().get(0).getFecha(), getListaVentas().get(0).getFecha());

        assertNotNull(registro);
        assertEquals(mocklist.get(0),registro.get(0));
    }
    //aaa
    @Test
    void siInvocoVerRegistroVentaDetalleDiaYFunca(){
        List<ViewRegistroVentasDetalle> registro;
        List<ViewRegistroVentasDetalle> mocklist = getViewRegistroVentasDetalle();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewRegistroVentasDetalle.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);

        when(criteriaQuery.where(criteriaBuilder.equal(root.get("fecha"), getListaVentas().get(0).getFecha()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);
        registro = ventaService.verRegistroVentaDetalleDia(getListaVentas().get(0).getFecha());

        assertNotNull(registro);
        assertEquals(mocklist.get(0),registro.get(0));
    }
    //aaa
    @Test
    void siInvocoVerRegistroVentaDetalleEntreDiasYFunca(){
        List<ViewRegistroVentasDetalle> registro;
        List<ViewRegistroVentasDetalle> mocklist = getViewRegistroVentasDetalle();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewRegistroVentasDetalle.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);

        when(criteriaQuery.where(criteriaBuilder.between(root.get("fecha"), getListaVentas().get(0).getFecha(),getListaVentas().get(0).getFecha()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);
        registro = ventaService.verRegistroVentaDetalleEntreDias(getListaVentas().get(0).getFecha(),getListaVentas().get(0).getFecha());

        assertNotNull(registro);
        assertEquals(mocklist.get(0),registro.get(0));
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
    private List<ViewRegistroVentasResumen> getViewRegistroVentasResumen(){
        List<ViewRegistroVentasResumen> viewRegistroVentasResumenList=new ArrayList<>();
        ViewRegistroVentasResumen v1 = new ViewRegistroVentasResumen();
        v1.setIdventa(1);
        v1.setTipoventa("boleta");
        v1.setFecha(Timestamp.valueOf("2020-11-23 00:00:00"));
        viewRegistroVentasResumenList.add(v1);
        ViewRegistroVentasResumen v2 = new ViewRegistroVentasResumen();
        v2.setIdventa(2);
        v2.setTipoventa("factura");
        v2.setFecha(Timestamp.valueOf("2020-11-24 00:00:00"));
        viewRegistroVentasResumenList.add(v2);
        return viewRegistroVentasResumenList;
    }
    private List<ViewRegistroVentasDetalle> getViewRegistroVentasDetalle(){
        List<ViewRegistroVentasDetalle> viewRegistroVentasDetalleList=new ArrayList<>();
        ViewRegistroVentasDetalle v1 = new ViewRegistroVentasDetalle();
        v1.setIdventa(1);
        v1.setTipoventa("boleta");
        v1.setFecha(Timestamp.valueOf("2020-11-23 00:00:00"));
        viewRegistroVentasDetalleList.add(v1);
        ViewRegistroVentasDetalle v2 = new ViewRegistroVentasDetalle();
        v2.setIdventa(2);
        v2.setTipoventa("factura");
        v2.setFecha(Timestamp.valueOf("2020-11-24 00:00:00"));
        viewRegistroVentasDetalleList.add(v2);
        return viewRegistroVentasDetalleList;
    }
}
