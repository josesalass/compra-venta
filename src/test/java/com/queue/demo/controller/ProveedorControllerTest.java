package com.queue.demo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.model.Proveedor;
import com.queue.demo.service.ProveedorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ProveedorControllerTest {
    private JacksonTester<Proveedor> jsonProveedor;
    private MockMvc mockMvc;

    @Mock
    private ProveedorService proveedorService;
    @InjectMocks
    private ProveedorController proveedorController;

    @BeforeEach
    void setup(){
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc= MockMvcBuilders.standaloneSetup(proveedorController).build();
    }

    @Test
    void SiInvocoFuncionListDebeRetornarTodosLosProveedores() throws Exception {
        List<Proveedor> proveedores = getListaProveedores();
        given(proveedorService.buscarTodosLosProveedores()).willReturn(proveedores);

        MockHttpServletResponse respuesta= mockMvc.perform(get("/proveedores")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertNotNull(respuesta);
        assertEquals(HttpStatus.OK.value(),respuesta.getStatus());

    }

    @Test
    void SiInvocoFuncionListYNoExistenProveedoresDebeRetornarListaVacia() throws Exception {
        List<Proveedor> proveedores = new ArrayList<>();
        given(proveedorService.buscarTodosLosProveedores()).willReturn(proveedores);

        MockHttpServletResponse respuesta= mockMvc.perform(get("/proveedores")
                .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                                .getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(),respuesta.getStatus());

    }

    @Test
    void SiInvocoBuscarPorNombreYExisteUnProveedorRetornarStatusOK() throws Exception {
        Proveedor proveedor=getProveedor();
        given(proveedorService.buscarPorNombre("Iansa")).willReturn(proveedor);

        MockHttpServletResponse respuesta= mockMvc.perform(get("/proveedores/findNombre?nombre=Iansa")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.OK.value(),respuesta.getStatus());
    }

    @Test
    void SiInvocoBuscarPorNombreYNoExisteUnProveedorRetornarStatusNotFound() throws Exception {
        Proveedor proveedor=new Proveedor();
        given(proveedorService.buscarPorNombre("Iansa")).willReturn(null);

        MockHttpServletResponse respuesta= mockMvc.perform(get("/proveedores/findNombre?nombre=Iansa")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(),respuesta.getStatus());
    }

    @Test
    void SiInvocoBuscarPorRutYExisteUnProveedorConEseRutRetornaStatusOK() throws Exception {
        Proveedor proveedor=getProveedor();
        given(proveedorService.buscarPorRut(proveedor.getRutempresa())).willReturn(proveedor);

        MockHttpServletResponse respuesta= mockMvc.perform(get("/proveedores/findRut?rut=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.OK.value(),respuesta.getStatus());
    }

    @Test
    void SiInvocoBuscarPorRutYNoExisteUnProveedorRetornarStatusNotFound() throws Exception {
        Proveedor proveedor=new Proveedor();
        given(proveedorService.buscarPorRut("1")).willReturn(null);

        MockHttpServletResponse respuesta= mockMvc.perform(get("/proveedores/findRut?rut=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(),respuesta.getStatus());
    }

    @Test
    void SiInvocoEliminarPorNombreYExisteElProveedorRetornaStatusOK() throws Exception {
        Proveedor proveedor=getProveedor();
        given(proveedorService.eliminarPorNombre("Iansa")).willReturn(true);
        MockHttpServletResponse respuesta= mockMvc.perform(delete("/proveedores/deleteNombre?nombre=Iansa")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.OK.value(),respuesta.getStatus());
    }

    @Test
    void SiInvocoEliminarPorNombreYNoExisteElProveedorRetornaStatusNotFound() throws Exception {

        given(proveedorService.eliminarPorNombre("Iansa")).willReturn(false);
        MockHttpServletResponse respuesta= mockMvc.perform(delete("/proveedores/deleteNombre?nombre=Iansa")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(),respuesta.getStatus());
    }

    @Test
    void SiInvocoEliminarPorRutYExisteElProveedorRetornaStatusOK() throws Exception {
        Proveedor proveedor=getProveedor();
        given(proveedorService.eliminarPorRut(proveedor.getRutempresa())).willReturn(true);
        MockHttpServletResponse respuesta= mockMvc.perform(delete("/proveedores/deleteRut?rut=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.OK.value(),respuesta.getStatus());
    }

    @Test
    void SiInvocoEliminarPorRutYNoExisteElProveedorRetornaStatusNotFound() throws Exception {
        given(proveedorService.eliminarPorRut("1")).willReturn(false);
        MockHttpServletResponse respuesta= mockMvc.perform(delete("/proveedores/deleteRut?rut=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(),respuesta.getStatus());
    }

    List<Proveedor> getListaProveedores(){
        List<Proveedor> proveedores = new ArrayList<>();

        Proveedor prov1= new Proveedor();
        prov1.setEstado(true);
        prov1.setNombreempresa("Iansa");
        prov1.setRutempresa("1");
        proveedores.add(prov1);


        Proveedor prov2 = new Proveedor();
        prov2.setEstado(true);
        prov2.setNombreempresa("Fini");
        prov2.setRutempresa("2");
        proveedores.add(prov2);

        return proveedores;
    }

    Proveedor getProveedor(){
        return getListaProveedores().get(0);
    }
}
