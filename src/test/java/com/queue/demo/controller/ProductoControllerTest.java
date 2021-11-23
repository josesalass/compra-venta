package com.queue.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queue.demo.controller.ProductoController;
import com.queue.demo.model.Producto;
import com.queue.demo.service.ProductoServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class ProductoControllerTest {

    private JacksonTester<Producto> jsonProducto;
    private MockMvc mockMvc;

    @Mock
    private ProductoServiceImpl productoService;
    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }


    @Test
    void siInvocoBuscarTodosLosProductosDebeRetornarProductoList() throws Exception {
        Producto producto1 = new Producto(1,4,2,3,5,"hola");
        Producto producto2 = new Producto(2,4,2,3,5,"producto2");
        List<Producto> productos = new ArrayList<>();
        productos.add(producto1);
        productos.add(producto2);
        given(productoService.buscarTodosLosProductos()).willReturn(productos);

        this.mockMvc.perform(get("/producto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(productos.size()));
    }


    @Test
    void siInvocoGuardarProductoDevuelveStatusCreated() throws Exception {
        // Given
        Producto producto = new Producto(2,4,2,3,5,"producto1");
        given(productoService.guardarProducto(any(Producto.class))).willReturn(producto);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/producto/guardarProducto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProducto.write(producto).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
        assertEquals(jsonProducto.write(producto).getJson(),response.getContentAsString());
    }


    @Test
    void SiInvocoReducirStockDebeActualizarElProducto() throws Exception {
        int idproducto = 1;
        Producto producto = new Producto(idproducto,4,2,3,5,"producto1");
        Producto productoeditado = new Producto(idproducto,2,2,3,5,"producto1");
        int cantidadAInsertar = 2;
        given(productoService.buscarProductoPorId(producto.getIdproducto())).willReturn(producto);
        given(productoService.actualizarProducto(idproducto,producto)).willReturn(productoeditado);

        MockHttpServletResponse response = mockMvc.perform( put("/producto/{idproducto}/reducirStock/{cantidad}",idproducto,cantidadAInsertar)
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }


    @Test
    void SiInvocoReducirStockDebeRetornarBadRequest() throws Exception {
        int idproducto = 1;
        Producto producto = new Producto(idproducto,4,2,3,5,"producto1");
        int cantidadAInsertar = 5;
        given(productoService.buscarProductoPorId(producto.getIdproducto())).willReturn(producto);

        MockHttpServletResponse response = mockMvc.perform( put("/producto/{idproducto}/reducirStock/{cantidad}",idproducto,cantidadAInsertar)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());


    }


    @Test
    void SiInvocoAumentoDeStockDebeActualizarElProducto() throws Exception {
        int idproducto = 1;
        Producto producto = new Producto(idproducto,4,2,3,5,"producto1");
        Producto productoeditado = new Producto(idproducto,8,2,3,5,"producto1");
        int cantidadAInsertar = 4;
        given(productoService.buscarProductoPorId(producto.getIdproducto())).willReturn(producto);
        given(productoService.actualizarProducto(idproducto,producto)).willReturn(productoeditado);

        MockHttpServletResponse response = mockMvc.perform( put("/producto/{idproducto}/aumentoDeStock/{cantidad}",idproducto,cantidadAInsertar)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }


    @Test
    void SiInvocoAumentoDeStockDebeRetornarBadRequest() throws Exception {
        int idproducto = 1;
        Producto producto = new Producto(idproducto,4,2,3,5,"producto1");
        int cantidadAInsertar = -1;
        given(productoService.buscarProductoPorId(producto.getIdproducto())).willReturn(producto);

        mockMvc.perform( put("/producto/{idproducto}/aumentoDeStock/{cantidad}",idproducto,cantidadAInsertar)).andExpect(status().isBadRequest());


    }

}
