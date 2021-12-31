package com.queue.demo.service;

import com.queue.demo.model.Proveedor;
import com.queue.demo.model.RepresentanteProveedor;
import com.queue.demo.model.Usuario;
import com.queue.demo.repository.RepositorioProveedor;
import com.queue.demo.repository.RepositorioRepresentanteProveedor;
import com.queue.demo.repository.RepositorioUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProveedorServiceTest {
    @Mock
    private RepositorioProveedor repositorioProveedor;

    @Mock
    private RepositorioRepresentanteProveedor repositorioRepresentanteProveedor;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery criteriaQuery=mock(CriteriaQuery.class);

    @Mock
    private Root<Proveedor> root;

    @Mock
    private TypedQuery typedQuery=mock(TypedQuery.class);

    @InjectMocks
    private ProveedorServiceImpl proveedorServiceImpl;

    @Mock
    private UsuarioServiceImpl repositorioUsuario;

    @Test
    void SiInvocoBuscarPorNombreYExisteUnoDebeRetornarProveedor() {
        //Arrange
        Optional<Proveedor> respuesta;

        List<Proveedor> mocklist = new ArrayList<>();
        mocklist.add(getProveedor());

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Proveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("nombreempresa"), getProveedor().getNombreempresa()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);

        //Act
        respuesta=proveedorServiceImpl.buscarPorNombre(getProveedor().getNombreempresa());

        //Assert
        assertNotNull(respuesta);
        assertEquals(respuesta.get().getRutempresa(),getProveedor().getRutempresa());

    }

    @Test
    void SiInvocoBuscarPorNombreYNoExisteUnoDebeRetornarNull() {
        //Arrange
        Optional<Proveedor> respuesta;

        List<Proveedor> mocklist = new ArrayList<>();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Proveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("nombreempresa"), getProveedor().getNombreempresa()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);

        //Act
        respuesta=proveedorServiceImpl.buscarPorNombre(getProveedor().getNombreempresa());

        //Assert
        assertNotNull(respuesta);
        assertTrue(respuesta.isEmpty());

    }

    @Test
    void SiInvocoBuscarPorRutYExisteUnoDebeRetornarProveedor() {
        //Arrange
        Optional<Proveedor> respuesta;

        List<Proveedor> mocklist = new ArrayList<>();
        mocklist.add(getProveedor());

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Proveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("rutempresa"), getProveedor().getRutempresa()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);

        //Act
        respuesta=proveedorServiceImpl.buscarPorRut(getProveedor().getRutempresa());

        //Assert
        assertNotNull(respuesta);
        assertEquals(respuesta.get().getRutempresa(),getProveedor().getRutempresa());

    }

    @Test
    void SiInvocoBuscarPorRutYNoExisteUnoDebeRetornarNull() {
        //Arrange
        Optional<Proveedor> respuesta;

        List<Proveedor> mocklist = new ArrayList<>();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Proveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("rutempresa"), getProveedor().getRutempresa()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);

        //Act
        respuesta=proveedorServiceImpl.buscarPorRut(getProveedor().getRutempresa());

        //Assert
        assertNotNull(respuesta);
        assertTrue(respuesta.isEmpty());

    }

    @Test
    void SiInvocoBuscarTodosLosProveedoresYExistenProveedoresEntoncesRetornaLista(){
        List<Proveedor> respuesta;

        when(repositorioProveedor.findAll()).thenReturn(getProveedores());

        respuesta=proveedorServiceImpl.buscarTodosLosProveedores();
        assertNotNull(respuesta);
        assertEquals(respuesta.size(),getProveedores().size());
    }

    @Test
    void SiInvocoBuscarTodosLosProveedoresYNoExistenProveedoresRetornarListaVacia(){
        List<Proveedor> respuesta;
        List<Proveedor> mock = new ArrayList<>();

        when(repositorioProveedor.findAll()).thenReturn(mock);

        respuesta=proveedorServiceImpl.buscarTodosLosProveedores();
        assertNotNull(respuesta);
        assertEquals(respuesta.size(),0);
    }

    @Test
    void SiInvocoGuardarProveedorYNoExisteElProveedorLoInsertaYRetornaProveedor() throws Exception {
        Proveedor respuesta;
        Usuario u=getusuario();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Proveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("rutempresa"), getProveedor().getRutempresa()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(new ArrayList<Proveedor>());
        when(repositorioUsuario.buscarUsuarioPorRut(u.getRutusuario())).thenReturn(Optional.of(u));
        when(repositorioProveedor.save(any())).thenReturn(getProveedor());

        //Act
        respuesta=proveedorServiceImpl.guardarProveedor(getProveedor(),u.getRutusuario());

        //Assert
        assertEquals(respuesta.getRutempresa(),getProveedor().getRutempresa());
    }

    @Test
    void SiInvocoGuardarProveedorYYaExisteElProveedorLanzaUnException() throws Exception{

        List<Proveedor> proveedores = new ArrayList<>();
        proveedores.add(getProveedor());
        Usuario u=getusuario();
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Proveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("rutempresa"), getProveedor().getRutempresa()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(proveedores);
        when(repositorioUsuario.buscarUsuarioPorRut(u.getRutusuario())).thenReturn(Optional.of(u));
        Exception exception = assertThrows(Exception.class,
                () -> {proveedorServiceImpl.guardarProveedor(getProveedor(),u.getRutusuario());});

        assertEquals("Ya existente",exception.getMessage());

    }

    @Test
    void SiInvocoGuardarProveedorYElRutNoEstaAutorizadoDebeLanzarException(){
        List<Proveedor> proveedores = new ArrayList<>();
        proveedores.add(getProveedor());
        Usuario u= new Usuario();
        u.setRolusuario(Usuario.ADMIN_VENTAS);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Proveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("rutempresa"), getProveedor().getRutempresa()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(proveedores);
        when(repositorioUsuario.buscarUsuarioPorRut(u.getRutusuario())).thenReturn(Optional.of(u));

        Exception exception = assertThrows(Exception.class,
                () -> {proveedorServiceImpl.guardarProveedor(getProveedor(),u.getRutusuario());});

        assertEquals("Usuario no autorizado para cometer la acción.",exception.getMessage());
    }

    @Test
    void SiInvocoEliminarPorNombreYExisteRetornaTrue(){
        Boolean respuesta;

        List<Proveedor> mocklist = new ArrayList<>();
        mocklist.add(getProveedor());

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Proveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("nombreempresa"), getProveedor().getNombreempresa()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);

        respuesta= proveedorServiceImpl.eliminarPorNombre(getProveedor().getNombreempresa());

        assertTrue(respuesta);
    }

    @Test
    void SiInvocoEliminarPorNombreYNoExisteElProveedorRetornaFalse(){
        Boolean respuesta;

        List<Proveedor> mocklist = new ArrayList<>();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Proveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("nombreempresa"), getProveedor().getNombreempresa()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);

        respuesta=proveedorServiceImpl.eliminarPorNombre(getProveedor().getNombreempresa());

        assertFalse(respuesta);

    }

    @Test
    void SiInvocoEliminarPorRutYExisteRetornaTrue(){
        Boolean respuesta;

        List<Proveedor> mocklist = new ArrayList<>();
        mocklist.add(getProveedor());

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Proveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("rutempresa"), getProveedor().getRutempresa()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);

        respuesta= proveedorServiceImpl.eliminarPorRut(getProveedor().getRutempresa());

        assertEquals(respuesta,true);
    }

    @Test
    void SiInvocoEliminarPorRutYNoExisteElProveedorRetornaFalse(){
        Boolean respuesta;

        List<Proveedor> mocklist = new ArrayList<>();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        when(criteriaBuilder.createQuery(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Proveedor.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(criteriaBuilder.equal(root.get("rutempresa"), getProveedor().getRutempresa()))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mocklist);

        respuesta=proveedorServiceImpl.eliminarPorRut(getProveedor().getRutempresa());

        assertEquals(respuesta,false);

    }


    private List<Proveedor> getProveedores(){
        List<Proveedor> proveedores= new ArrayList<>();

        Proveedor prov1 = new Proveedor();
        prov1.setNombreempresa("Prov1");
        prov1.setRutempresa("1");
        prov1.setEstado(true);

        RepresentanteProveedor rep1 = new RepresentanteProveedor();
        rep1.setEstado(true);
        rep1.setNombre("A");
        rep1.setApellido1("B");
        rep1.setApellido2("C");
        rep1.setRutrep("1");
        rep1.setRutemp(prov1.getRutempresa());


        prov1.setRepresentanteProveedor(rep1);
        proveedores.add(prov1);

        Proveedor prov2 = new Proveedor();
        prov2.setNombreempresa("Prov2");
        prov2.setRutempresa("2");
        prov2.setEstado(true);
        prov2.setRepresentanteProveedor(null);
        proveedores.add(prov2);

        return proveedores;
    }
    private Usuario getusuario(){
        Usuario usuario= new Usuario();
        usuario.setRolusuario(Usuario.ADMIN_COMPRAS);
        usuario.setRutusuario("164536512");


        return usuario;
    }
    private Proveedor getProveedor(){
        return getProveedores().get(0);
    }
}
