package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.Usuario;
import com.queue.demo.model.Venta;
import com.queue.demo.model.ViewRegistroVentasDetalle;
import com.queue.demo.model.ViewRegistroVentasResumen;
import com.queue.demo.service.AuthException;
import com.queue.demo.service.VentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class VentaControllerTest {
    private JacksonTester<Venta> jsonVenta;
    private MockMvc mockMvc;
    @Mock
    private VentaService ventaService;
    @InjectMocks
    private VentaController ventaController;

    @BeforeEach
    void setup(){
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(ventaController).build();
    }

    //list caso exitoso
    @Test
    void siInvocoListDebeMostrarTodasLasVentas() throws Exception{
        List<Venta> ventas = getVentas();
        given(ventaService.buscarTodasLasVentas()).willReturn(ventas);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();


        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    //list con lista vacía
    @Test
    void siInvocoListYNoHayVentasDebeRetornarNotFound() throws Exception{
        List<Venta> ventas = new ArrayList<>();
        given(ventaService.buscarTodasLasVentas()).willReturn(ventas);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();


        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());
    }

    //saveVenta exitoso
    @Test
    void siInvocoSaveVentaDebeAlmacenarYDevolverLaVentaConStatusCreated() throws Exception{
        //Given
        Venta venta = getVenta();
        given(ventaService.guardarVenta(any(Venta.class))).willReturn(venta);

        //When
        MockHttpServletResponse response = mockMvc.perform(post("/ventas/guardarVenta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonVenta.write(venta).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
        assertEquals(jsonVenta.write(venta).getJson(),response.getContentAsString());
    }
    //saveVenta unauthorized
    @Test
    void siInvocoSaveVentaYElUsuarioNoEsAdminDeVentasDebeRetornarStatusUnauthorized() throws Exception{
        Venta venta = getVenta();
        Usuario usuario = getUsuario();
        doThrow(AuthException.class).when(ventaService).guardarVenta(any(Venta.class));

        MockHttpServletResponse response = mockMvc.perform(post("/ventas/guardarVenta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonVenta.write(venta).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.UNAUTHORIZED.value(),response.getStatus());
    }


    //saveVenta bad request
    @Test
    void siInvocoSaveVentaSeDebeDevolverElStatusBadRequest() throws Exception{
        //Given
        Venta venta = getVenta();
        venta.setFecha(null);
        doThrow(Exception.class).when(ventaService).guardarVenta(any(Venta.class));

        //When
        MockHttpServletResponse response = mockMvc.perform(post("/ventas/guardarVenta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonVenta.write(venta).getJson())
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());

    }

    //editarFecha caso exitoso
    @Test
    void siInvocoEditarFechaYNoHayNulosDebeDevolverStatusOk() throws Exception{
        //Given
        Venta venta = getVenta();
        Venta vFinal = venta;
        vFinal.setFecha(Timestamp.valueOf("2005-10-30 00:00:00"));
        given(ventaService.buscarVentaPorId(venta.getIdventa())).willReturn(java.util.Optional.of(venta));
        given(ventaService.actualizarVenta(venta.getIdventa(),venta)).willReturn(vFinal);

        //When
        MockHttpServletResponse response = mockMvc.perform(put("/ventas/{idventa}/cambiarFecha/{fecha}",venta.getIdventa(),Timestamp.valueOf("2005-10-30 00:00:00"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.OK.value(),response.getStatus());

    }

    //editarFecha caso se recibe una fecha en un formato no Timestamp
    @Test
    void siInvocoEditarFechaYLaFechaNoEsFormatoTimestampDebeDevolverStatusBadRequest() throws Exception{
        Venta venta = getVenta();
        given(ventaService.buscarVentaPorId(venta.getIdventa())).willReturn(java.util.Optional.of(venta));
        String noFecha = "Esto claramente no es una fecha";

        mockMvc.perform(put("/ventas/{idventa}/cambiarFecha/{fecha}",venta.getIdventa(),noFecha)).andExpect(status().isBadRequest());

    }

    //editarFecha caso no existe la Venta
    @Test
    void siInvocoEditarFechaYLaVentaNoExisteDebeDevolverStatusBadRequest() throws Exception{
        Venta venta = getVenta();
        given(ventaService.buscarVentaPorId(venta.getIdventa())).willReturn(null);

        mockMvc.perform(put("/ventas/{idventa}/cambiarFecha/{fecha}",venta.getIdventa(),Timestamp.valueOf("2005-10-30 00:00:00"))).andExpect(status().isBadRequest());
    }

    //editarFecha Unauthorized
    @Test
    void siInvocoEditarFechaYElUsuarioNoEsAdminDeVentasDebeRetornarStatusUnauthorized() throws Exception{
        //Given
        Venta venta = getVenta();
        Usuario usuario=getUsuario();
        given(ventaService.buscarVentaPorId(venta.getIdventa())).willReturn(Optional.of(venta));
        doThrow(AuthException.class).when(ventaService).actualizarVenta(eq(venta.getIdventa()),any(Venta.class));
        MockHttpServletResponse response = mockMvc.perform(put("/ventas/{idventa}/cambiarFecha/{fecha}",venta.getIdventa(),Timestamp.valueOf("2005-10-30 00:00:00"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.UNAUTHORIZED.value(),response.getStatus());
    }

    //editarTipo caso OK
    @Test
    void siInvocoEditarTipoYNoHayNulosDebeDevolverStatusOk() throws Exception{
        //Given
        Venta venta = getVenta();
        Venta vFinal = venta;
        vFinal.setTipoventa("boleta");
        given(ventaService.buscarVentaPorId(venta.getIdventa())).willReturn(java.util.Optional.of(venta));
        given(ventaService.actualizarVenta(venta.getIdventa(),venta)).willReturn(vFinal);

        //When
        MockHttpServletResponse response = mockMvc.perform(put("/ventas/{idventa}/cambiarTipoPrueba/{tipo}",venta.getIdventa(),"boleta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }


    //editarTipo caso Unauthorized
    @Test
    void siInvocoEditarTipoYHayNulosDebeDevolverStatusUnaothorized() throws Exception{
        //Given
        Venta venta = getVenta();
        Usuario usuario=getUsuario();
        given(ventaService.buscarVentaPorId(venta.getIdventa())).willReturn(java.util.Optional.of(venta));
        doThrow(AuthException.class).when(ventaService).actualizarVenta(eq(venta.getIdventa()),any(Venta.class));
        //When
        MockHttpServletResponse response = mockMvc.perform(put("/ventas/{idventa}/cambiarTipoPrueba/{tipo}",venta.getIdventa(),"boleta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.UNAUTHORIZED.value(),response.getStatus());
    }

    //editarTipo caso Bad Request
    @Test
    void siInvocoEditarTipoYHayNulosDebeDevolverStatusBadRequest() throws Exception{
        Venta venta = getVenta();
        given(ventaService.buscarVentaPorId(venta.getIdventa())).willReturn(null);

        mockMvc.perform(put("/ventas/{idventa}/cambiarTipoPrueba/{tipo}",venta.getIdventa(),"boleta")).andExpect(status().isBadRequest());

    }


    //editarMetodoPago caso OK
    @Test
    void siInvocoEditarMetodoPagoYNoHayNulosDebeDevolverStatusOK() throws Exception{
        //Given
        Venta venta = getVenta();
        Venta vFinal = venta;
        vFinal.setMetodopago("efectivo");
        given(ventaService.buscarVentaPorId(venta.getIdventa())).willReturn(java.util.Optional.of(venta));
        given(ventaService.actualizarVenta(venta.getIdventa(),venta)).willReturn(vFinal);

        //When
        MockHttpServletResponse response = mockMvc.perform(put("/ventas/{idventa}/cambiarMetodoPagoPrueba/{metodo}",venta.getIdventa(),"efectivo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    //editarMetodoPago caso UNAUTHORIZED
    @Test
    void siInvocoEditarMetodoPagoYHayNulosDebeDevolverStatusUNAUTHORIZED() throws Exception{
        Venta venta = getVenta();
        Usuario usuario= getUsuario();
        given(ventaService.buscarVentaPorId(venta.getIdventa())).willReturn(Optional.of(venta));
        doThrow(AuthException.class).when(ventaService).actualizarVenta(eq(venta.getIdventa()),any(Venta.class));

        mockMvc.perform(put("/ventas/{idventa}/cambiarMetodoPagoPrueba/{metodo}",venta.getIdventa(),"efectivo")).andExpect(status().isUnauthorized());
    }

    //editarMetodoPago caso BAD REQUEST
    @Test
    void siInvocoEditarMetodoPagoYHayNulosDebeDevolverStatusBADREQUEST() throws Exception{
        Venta venta = getVenta();
        given(ventaService.buscarVentaPorId(venta.getIdventa())).willReturn(null);

        mockMvc.perform(put("/ventas/{idventa}/cambiarMetodoPagoPrueba/{metodo}",venta.getIdventa(),"efectivo")).andExpect(status().isBadRequest());
    }

    @Test
    void siInvocoViewRegistroVentaResumenDebeRetornarLista() throws Exception {
        List<ViewRegistroVentasResumen> a = getViewRegistroVentaResumen();
        given(ventaService.verRegistroVentaResumen()).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasResumen")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoViewRegistroVentaResumenDebeRetornarListaVacia() throws Exception {
        List<ViewRegistroVentasResumen> a = new ArrayList<>();
        given(ventaService.verRegistroVentaResumen()).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasResumen")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    void siInvocoViewRegistroVentaResumenTipoDebeRetornarLista() throws Exception {
        List<ViewRegistroVentasResumen> a = getViewRegistroVentaResumen();
        given(ventaService.verRegistroVentaResumen(any(String.class))).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasResumenTipo?tipoventa=boleta")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoViewRegistroVentaResumenTipoDebeRetornarListaVacia() throws Exception {
        List<ViewRegistroVentasResumen> a = new ArrayList<>();
        given(ventaService.verRegistroVentaResumen(any(String.class))).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasResumenTipo?tipoventa=boleta")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    void siInvocoViewRegistroVentaDetalleDebeRetornarLista() throws Exception {
        List<ViewRegistroVentasDetalle> a = getViewRegistroVentaDetalle();
        given(ventaService.verRegistroVentaDetalle()).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasDetalle")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoViewRegistroVentaDetalleDebeRetornarListaVacia() throws Exception {
        List<ViewRegistroVentasDetalle> a = new ArrayList<>();
        given(ventaService.verRegistroVentaDetalle()).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasDetalle")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    void siInvocoViewRegistroVentaDetalleConTipoDebeRetornarLista() throws Exception {
        List<ViewRegistroVentasDetalle> a = getViewRegistroVentaDetalle();
        given(ventaService.verRegistroVentaDetalle(any(String.class))).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasDetalleTipo?tipoventa=boleta")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoViewRegistroVentaDetalleConTipoDebeRetornarListaVacia() throws Exception {
        List<ViewRegistroVentasDetalle> a = new ArrayList<>();
        given(ventaService.verRegistroVentaDetalle(any(String.class))).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasDetalleTipo?tipoventa=boleta")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    void siInvocoVerRegistroVentasResumenDiaDebeRetornarLista() throws Exception {
        List<ViewRegistroVentasResumen> a = getViewRegistroVentaResumen();
        given(ventaService.verRegistroVentaResumenDia(any(Timestamp.class))).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasResumenDia?dia=2020-10-02 00:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoVerRegistroVentasResumenDiaDebeRetornarListaVacia() throws Exception {
        List<ViewRegistroVentasResumen> a = new ArrayList<>();
        given(ventaService.verRegistroVentaResumenDia(any(Timestamp.class))).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasResumenDia?dia=2020-10-02 00:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }
    @Test
    void siInvocoVerRegistroVentaResumenEntreDiasDebeRetornarLista() throws Exception {
        List<ViewRegistroVentasResumen> a = getViewRegistroVentaResumen();
        given(ventaService.verRegistroVentaResumenEntreDias(any(Timestamp.class),any(Timestamp.class))).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasResumenEntreDias?dia1=2020-10-01 00:00:00&dia2=2020-10-03 00:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoVerRegistroVentaResumenEntreDiasDebeRetornarListaVacia() throws Exception {
        List<ViewRegistroVentasResumen> a = new ArrayList<>();
        given(ventaService.verRegistroVentaResumenEntreDias(any(Timestamp.class),any(Timestamp.class))).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasResumenEntreDias?dia1=2020-10-01 00:00:00&dia2=2020-10-03 00:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    void siInvocoVerRegistroVentasDetalleDiaDebeRetornarLista() throws Exception {
        List<ViewRegistroVentasDetalle> a = getViewRegistroVentaDetalle();
        given(ventaService.verRegistroVentaDetalleDia(any(Timestamp.class))).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasDetalleDia?dia=2020-10-02 00:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoVerRegistroVentasDetalleDiaDebeRetornarListaVacia() throws Exception {
        List<ViewRegistroVentasDetalle> a = new ArrayList<>();
        given(ventaService.verRegistroVentaDetalleDia(any(Timestamp.class))).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasDetalleDia?dia=2020-10-02 00:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    void siInvocoVerRegistroVentasDetalleEntreDiasDebeRetornarLista() throws Exception {
        List<ViewRegistroVentasDetalle> a = getViewRegistroVentaDetalle();
        given(ventaService.verRegistroVentaDetalleEntreDias(any(Timestamp.class),any(Timestamp.class))).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasDetalleEntreDias?dia1=2020-10-01 00:00:00&dia2=2020-10-03 00:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

    @Test
    void siInvocoVerRegistroVentasDetalleEntreDiasDebeRetornarListaVacia() throws Exception {
        List<ViewRegistroVentasDetalle> a = new ArrayList<>();
        given(ventaService.verRegistroVentaDetalleEntreDias(any(Timestamp.class),any(Timestamp.class))).willReturn(a);

        MockHttpServletResponse response = mockMvc.perform(get("/ventas/verRegistroVentasDetalleEntreDias?dia1=2020-10-01 00:00:00&dia2=2020-10-03 00:00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    Venta getVenta(){
        Venta venta = new Venta();
        venta.setIdventa(1);
        venta.setRutcliente("123");
        venta.setMetodopago("a");
        venta.setTipoventa("a");
        venta.setFecha(Timestamp.valueOf("2020-10-02 00:00:00"));

        return venta;
    }
    List<Venta> getVentas(){
        Venta venta = new Venta();
        venta.setIdventa(1);
        venta.setRutcliente("123");
        List<Venta> ventas = new ArrayList<>();
        ventas.add(venta);
        venta = new Venta();
        venta.setIdventa(2);
        venta.setRutcliente("543");
        ventas.add(venta);
        return ventas;
    }
    private Usuario getUsuario() {
        Usuario usuario = new Usuario("123","jose","apellido1","tres","ada@gmail.com",Usuario.ADMIN_VENTAS,"producto1");
        return  usuario;
    }

    private List<ViewRegistroVentasResumen> getViewRegistroVentaResumen(){
        List<ViewRegistroVentasResumen> a = new ArrayList<>();
        ViewRegistroVentasResumen viewRegistroVentasResumen = new ViewRegistroVentasResumen();
        viewRegistroVentasResumen.setFecha(Timestamp.valueOf("2020-10-02 00:00:00"));
        viewRegistroVentasResumen.setIdventa(432);
        viewRegistroVentasResumen.setTipoventa("boleta");
        a.add(viewRegistroVentasResumen);
        return a;
    }

    private List<ViewRegistroVentasDetalle> getViewRegistroVentaDetalle(){
        List<ViewRegistroVentasDetalle> a = new ArrayList<>();
        ViewRegistroVentasDetalle v = new ViewRegistroVentasDetalle();
        v.setFecha(Timestamp.valueOf("2020-10-02 00:00:00"));
        v.setIdventa(231);
        v.setTipoventa("boleta");
        a.add(v);
        return a;
    }
}
