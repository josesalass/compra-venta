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
    private UsuarioService usuarioService;
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
    void buscarTodasLasVentasDebeRetornarVentaList(){
        List<Venta> resultado;
        List<Venta> ventas=getListaVentas();
        when(repositorioVenta.findAll()).thenReturn(ventas);

        resultado=ventaService.buscarTodasLasVentas();

        assertNotNull(resultado);
        assertEquals(ventas.size(),resultado.size());
    }
    @Test
    void buscarTodasLasVentasYNoExistenComprasDebeRetornarVentaListVacia(){
        List<Venta> resultado;
        List<Venta> ventas=new ArrayList<>();
        when(repositorioVenta.findAll()).thenReturn(ventas);

        resultado=ventaService.buscarTodasLasVentas();

        assertNotNull(resultado);
        assertEquals(0,resultado.size());
    }
    @Test
    void BuscarVentaPorIdYExisteUnaVentaConEseIdDebeRetornarVenta(){
        Optional<Venta> resultado;
        Venta venta=getListaVentas().get(0);
        when(repositorioVenta.findById(1)).thenReturn(Optional.of(venta));

        resultado = ventaService.buscarVentaPorId(1);

        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
    }
    @Test
    void BuscarVentaPorIdYNoExisteUnaVentaConEseIdDebeRetornarOptionalVacio(){
        Optional<Venta> resultado;
        when(repositorioVenta.findById(1)).thenReturn(Optional.empty());

        resultado = ventaService.buscarVentaPorId(1);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
    @Test
    void BorrarVentaPorIdYExisteLaVentaDebeBorrarlaYRetornarTrue(){
        Venta venta=getListaVentas().get(0);
        boolean resultado;
        when(repositorioVenta.getById(1)).thenReturn(venta);

        resultado=ventaService.borrarVentaPorId(1);

        assertTrue(resultado);
    }
    @Test
    void BorrarVentaPorIdYNoExisteLaVentaDebeRetornarFalse(){
        boolean resultado;
        doThrow(EntityNotFoundException.class).when(repositorioVenta).getById(1);

        resultado=ventaService.borrarVentaPorId(1);

        assertNotNull(resultado);
        assertFalse(resultado);
    }
    @Test
    void GuardarVentaDebeRetornarLaVentaGuardada() throws Exception {
        Venta venta= getListaVentas().get(0);
        Usuario usuario = getUsuario();
        Producto producto= getProducto();
        Asociada_Venta asociadaVenta=new Asociada_Venta();
        asociadaVenta.setProducto(producto);
        asociadaVenta.setVenta(venta);
        asociadaVenta.setCantidad(10);
        venta.getVentaproductos().add(asociadaVenta);
        producto.setStock(producto.getStock()-asociadaVenta.getCantidad());
        Venta resultado;
        when(usuarioService.buscarUsuarioPorRut(any(String.class))).thenReturn(Optional.of(usuario));
        when(productoService.buscarProductoPorId(1)).thenReturn(Optional.of(producto));
        when(repositorioVenta.save(any(Venta.class))).thenReturn(venta);

        resultado = ventaService.guardarVenta(venta);

        assertNotNull(resultado);
        assertEquals(venta.getIdventa(),resultado.getIdventa());
        verify(repositorioVenta).save(any(Venta.class));

    }

    @Test
    void siInvocoGuardarVentaYElUsuarioNoExisteDebeRetornarException() throws Exception{
        Venta venta= getListaVentas().get(0);
        Producto producto= getProducto();
        Asociada_Venta asociadaVenta=new Asociada_Venta();
        asociadaVenta.setProducto(producto);
        asociadaVenta.setVenta(venta);
        asociadaVenta.setCantidad(10);
        venta.getVentaproductos().add(asociadaVenta);
        producto.setStock(producto.getStock()-asociadaVenta.getCantidad());
        Venta resultado;
        boolean thrown = false;

        when(usuarioService.buscarUsuarioPorRut(any(String.class))).thenReturn(Optional.empty());

        try {
            resultado = ventaService.guardarVenta(venta);
        }catch(Exception e){
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void siInvocoGuardarVentaYElUsuarioNoEsAdminDeVentasDebeRetornarUnauthorized() throws Exception{
        Venta venta= getListaVentas().get(0);
        Usuario usuario = getUsuario();
        usuario.setRolusuario(Usuario.ADMIN_COMPRAS);
        Producto producto= getProducto();
        Asociada_Venta asociadaVenta=new Asociada_Venta();
        asociadaVenta.setProducto(producto);
        asociadaVenta.setVenta(venta);
        asociadaVenta.setCantidad(10);
        venta.getVentaproductos().add(asociadaVenta);
        producto.setStock(producto.getStock()-asociadaVenta.getCantidad());
        Venta resultado;
        boolean thrown = false;


            when(usuarioService.buscarUsuarioPorRut(any(String.class))).thenReturn(Optional.of(usuario));

        try{
            resultado = ventaService.guardarVenta(venta);
        }catch(AuthException e){
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    void siInvocoActualizarVentaDebeRetornarLaVentaActualizada() throws Exception {
        Venta venta = getListaVentas().get(0);
        Usuario usuario=getUsuario();
        when(usuarioService.buscarUsuarioPorRut(any(String.class))).thenReturn(Optional.of(usuario));

        given(repositorioVenta.save(venta)).willReturn(venta);

        Venta ventaFinal = ventaService.actualizarVenta(venta.getIdventa(),venta);

        assertNotNull(ventaFinal);
        verify(repositorioVenta).save(any(Venta.class));
    }
    @Test
    void siInvocoActualizarVentaYElUsuarioNoExisteDebeRetornarException() throws Exception{
        Venta venta= getListaVentas().get(0);
        Usuario usuario = getUsuario();
        Venta resultado;
        boolean thrown = false;

        when(usuarioService.buscarUsuarioPorRut(any(String.class))).thenReturn(Optional.empty());

        try{
            resultado = ventaService.actualizarVenta(venta.getIdventa(),venta);
        }catch(Exception e){
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    void siInvocoActualizarVentaYElUsuarioNoEsAdminDeVentasDebeRetornarUnauthorized() throws Exception{
        Venta venta= getListaVentas().get(0);
        Usuario usuario = getUsuario();
        usuario.setRolusuario(Usuario.ADMIN_COMPRAS);
        Venta resultado;
        boolean thrown = false;

        when(usuarioService.buscarUsuarioPorRut(any(String.class))).thenReturn(Optional.of(usuario));

        try{
            resultado = ventaService.actualizarVenta(venta.getIdventa(),venta);
        }catch(AuthException e){
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    void siInvocoVerRegistroVentaResumenDebeRetornarViewRegistroVentaResumenList(){
        List<ViewRegistroVentasResumen> registro;
        List<ViewRegistroVentasResumen> mocklist = getViewRegistroVentasResumen();

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
    @Test
    void siInvocoVerRegistroVentaResumenConTipoDebeRetornarViewRegistroVentaResumenListFiltradoPorTipo(){
        List<ViewRegistroVentasResumen> registro;
        List<ViewRegistroVentasResumen> mocklist = getViewRegistroVentasResumen();


        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery()).thenReturn(criteriaQuery);
        when(criteriaQuery.from(ViewRegistroVentasResumen.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("tipoventa"), getListaVentas().get(0).getTipoventa()))).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);
        registro = ventaService.verRegistroVentaResumen(getListaVentas().get(0).getTipoventa());

        assertNotNull(registro);
        assertEquals(mocklist.get(0).getTipoventa(),registro.get(0).getTipoventa());
    }
    @Test
    void siInvocoVerRegistroVentaDetalleYFuncaDebeRetornarViewRegistroVentaDetalleList(){
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
    @Test
    void siInvocoVerRegistroVentaDetalleConTipoDebeRetornarViewRegistroVentaDetaleListConFiltradoPorTipo(){
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
    @Test
    void siInvocoVerRegistroVentaResumenDiaDebeRetornarViewRegistroVentaResumenListConFiltradoPorFechaEntregada(){
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
    @Test
    void siInvocoVerRegistroVentaResumenEntreDiasDebeRetornarViewRegistroVentaResumenListConFiltradoPorFechasEntregadas(){
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
    @Test
    void siInvocoVerRegistroVentaDetalleDiaDebeRetornarViewRegistroVentaDetalleListConFiltradoPorFechaEntregada(){
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
    @Test
    void siInvocoVerRegistroVentaDetalleEntreDiasDebeRetornarViewRegistroVentaDetalleListConFiltradoPorFechasEntregadas(){
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
        venta.setFecha(Timestamp.valueOf("2020-11-24 00:00:00"));
        venta.setMetodopago("efectivo");
        venta.setRutusuario("12345678-9");
        venta.setRutcliente("12345678-9");
        venta.setTipoventa("factura");

        
        Cliente cliente=new Cliente();
        cliente.setRutcliente("12345678-9");
        cliente.setNombre("rrgthcfgj");
        cliente.setApellido1("agjhk");
        cliente.setApellido2("jaytsh");
        cliente.setTelefono(987654321);
        cliente.setComuna("gtahjgvd");
        cliente.setCalle("ashfdv");
        cliente.setNumerocalle(321);

        venta.setCliente(cliente);

        ventas.add(venta);

        return ventas;
    }
    private Producto getProducto() {
        Producto p = new Producto();
        p.setStock(Integer.MAX_VALUE);
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
    private Usuario getUsuario() {
        Usuario usuario = new Usuario("123","jose","apellido1","tres","ada@gmail.com",Usuario.ADMIN_VENTAS,"producto1");
        return  usuario;
    }
}
